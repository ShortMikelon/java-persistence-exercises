package com.bobocode.dao;

import com.bobocode.exception.AccountDaoException;
import com.bobocode.model.Account;
import com.bobocode.util.ExerciseNotCompletedException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.h2.engine.Session;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AccountDaoImpl implements AccountDao {
    private EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Account account) {
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));
    }

    @Override
    public Account findById(Long id) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Account.class, id));
    }

    @Override
    public Account findByEmail(String email) {
        return performReturningWithinPersistenceContext(entityManager -> {
            String sql = "SELECT a FROM Account a WHERE a.email = :email";
            return entityManager.createQuery(sql, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        });
    }

    @Override
    public List<Account> findAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("SELECT a FROM Account a", Account.class)
                        .getResultList()
        );

    }

    @Override
    public void update(Account account) {
        performWithinPersistenceContext(entityManager -> entityManager.merge(account));
    }

    @Override
    public void remove(Account account) {
        performWithinPersistenceContext(entityManager -> {
            Account mergedAccount = entityManager.merge(account);
            entityManager.remove(mergedAccount);
        });
    }

    private void performWithinPersistenceContext(Consumer<EntityManager> entityManagerConsumer) {
        performReturningWithinPersistenceContext(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }

    private <T> T performReturningWithinPersistenceContext(Function<EntityManager, T> block) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            T result = block.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new AccountDaoException("", ex);
        } finally {
            entityManager.close();
        }
    }

}

