package com.bobocode.dao;

import com.bobocode.exception.CompanyDaoException;
import com.bobocode.model.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

public class CompanyDaoImpl implements CompanyDao {
    private EntityManagerFactory entityManagerFactory;

    public CompanyDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Company findByIdFetchProducts(Long id) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.unwrap(Session.class).setDefaultReadOnly(true);

        manager.getTransaction().begin();

        try {
            Company result = manager
                    .createQuery("select c from Company c join fetch c.products where c.id = :id", Company.class)
                    .setParameter("id", id)
                    .getSingleResult();
            manager.getTransaction().commit();
            return result;
        } catch (Exception ex) {
            manager.getTransaction().rollback();
            throw new CompanyDaoException("", ex);
        } finally {
            manager.close();
        }
    }
}
