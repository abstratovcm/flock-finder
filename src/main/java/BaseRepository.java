import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class BaseRepository<T, ID> implements GenericRepository<T, ID> {

    private static SessionFactory sessionFactory = null;
    private final Class<T> entityClass;

    protected BaseRepository(SessionFactory sessionFactory, Class<T> entityClass) {
        BaseRepository.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    protected BaseRepository(Class<T> entityClass) {
        if (sessionFactory == null) {
            try {
                BaseRepository.sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (HibernateException ex) {
                throw new RuntimeException("Failed to initialize UserRepository", ex);
            }
        }
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (session.contains(entity)) {
                throw new IllegalStateException("Entity already saved.");
            }
            session.save(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public T saveOrUpdate(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (!session.contains(entity)) {
                throw new IllegalStateException("Entity doesn't exist.");
            }
            session.update(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public T updateAttributeById(ID id, Consumer<T> updateFunction) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            T entity = session.find(entityClass, id);
            if (entity == null) {
                throw new IllegalStateException("Entity with ID " + id + " doesn't exist.");
            }
            updateFunction.accept(entity);
            session.update(entity);
            transaction.commit();
            return entity;
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(entityClass, id));
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (!session.contains(entity)) {
                throw new IllegalStateException("Entity doesn't exist.");
            }
            session.delete(entity);
            transaction.commit();
        }
    }

    @Override
    public void deleteById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            T entity = session.find(entityClass, id);
            if (!session.contains(entity)) {
                throw new IllegalStateException("Entity doesn't exist.");
            }
            session.delete(entity);
            transaction.commit();
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.select(root);
            return session.createQuery(query).getResultList();
        }
    }

}