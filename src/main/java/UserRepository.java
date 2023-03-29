import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import java.util.List;

public class UserRepository {
    private static SessionFactory sessionFactory = null;
    public UserRepository() {
        if (sessionFactory == null) {
            try {
                UserRepository.sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (HibernateException ex) {
                throw new RuntimeException("Failed to initialize UserRepository", ex);
            }
        }
    }

    public UserRepository(SessionFactory sessionFactory) {
        try {
            UserRepository.sessionFactory = sessionFactory;
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to initialize UserRepository", ex);
        }
    }

    public void addUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            // Check if a user with the same username already exists
            User existingUser = session.get(User.class, user.getUsername());
            if (existingUser != null) {
                throw new RuntimeException("User with username " + user.getUsername() + " already exists");
            }

            session.save(user);
            System.out.println("Adding user: " + user);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to add user", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public User getUser(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, username);
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to get user", ex);
        }
    }

    public void editUser(String username, String newPassword) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, username);
            if (user == null) {
                throw new RuntimeException("User with username " + username + " not found");
            }
            user.setPassword(newPassword);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to edit user", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void removeUser(String username) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, username);
            if (user == null) {
                throw new RuntimeException("User with username " + username + " not found");
            }
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to remove user", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to get all users", ex);
        }
    }
}