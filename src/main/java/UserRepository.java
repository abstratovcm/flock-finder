import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import java.util.List;

public class UserRepository {
    private static SessionFactory sessionFactory;
    public UserRepository() {
        try {
            System.out.println("Initializing UserRepository...");
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to initialize UserRepository", ex);
        }

    }

    public void addUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            System.out.println("Adding user: " + user.toString());
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
        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = (User) session.get(User.class, username);
            return user;
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to get user", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void editUser(String username, String newPassword) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = (User) session.get(User.class, username);
            if (user != null) {
                user.setPassword(newPassword);
                session.beginTransaction();
                session.update(user);
                session.getTransaction().commit();
            }
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
            User user = (User) session.get(User.class, username);
            if (user != null) {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
            }
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
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("FROM User");
            List<User> users = query.list();
            return users;
        } catch (HibernateException ex) {
            throw new RuntimeException("Failed to get all users", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}