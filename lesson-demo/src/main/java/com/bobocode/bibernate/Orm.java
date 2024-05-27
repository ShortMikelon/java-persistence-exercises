package com.bobocode.bibernate;

public interface Orm {
    <T> T findById(Class<T> entityType, Object id);

    void save(Object entity);
}
