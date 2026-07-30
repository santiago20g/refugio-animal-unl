package ec.edu.unl.refugio.business.dao;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class GenericDAO<T, ID> {

    private final Class<T> entityClass;

    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T find(ID id) {
        return getEntityManager().find(entityClass, id);
    }

    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public List<T> findAll() {
        return getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }
}
