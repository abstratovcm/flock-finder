import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;
import org.hibernate.query.Query;

import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private Query query;
    private Admin admin;
    private LimitedUser limitedUser;

    @BeforeEach
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        query = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        userRepository = new UserRepository(sessionFactory);

        // Create instances of Admin and LimitedUser
        admin = new Admin("admin", "admin123");
        limitedUser = new LimitedUser("limited", "limited123");

        // Set up mock behavior to return Admin and LimitedUser instances when session.get() is called
        when(session.get(User.class, admin.getUsername())).thenReturn(admin);
        when(session.get(User.class, limitedUser.getUsername())).thenReturn(limitedUser);

        when(session.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testAddUser_Admin() {
        Admin admin = new Admin("admin2", "admin123");

        userRepository.addUser(admin);

        verify(session).save(admin);
        verify(transaction).commit();
    }

    @Test
    public void testAddUser_LimitedUser() {
        LimitedUser limitedUser = new LimitedUser("limited2", "limited123");

        userRepository.addUser(limitedUser);

        verify(session).save(limitedUser);
        verify(transaction).commit();
    }

    @Test
    public void testGetUser_Admin() {
        String username = admin.getUsername();

        User result = userRepository.getUser(username);

        assertSame(admin, result);
        verify(session).get(User.class, username);
    }

    @Test
    public void testGetUser_LimitedUser() {
        String username = limitedUser.getUsername();

        User result = userRepository.getUser(username);

        assertSame(limitedUser, result);
        verify(session).get(User.class, username);
    }

    @Test
    public void testEditUser() {
        String username = admin.getUsername();
        String newPassword = "newAdminPassword";

        assertNotEquals(newPassword, admin.getPassword());

        userRepository.editUser(username, newPassword);

        verify(session).get(User.class, username);
        verify(session).beginTransaction();
        verify(session).update(admin);
        verify(transaction).commit();

        assertEquals(newPassword, admin.getPassword());
    }

    @Test
    public void testRemoveUser() {
        String username = admin.getUsername();

        userRepository.removeUser(username);

        verify(session).get(User.class, username);
        verify(session).beginTransaction();
        verify(session).delete(admin);
        verify(transaction).commit();
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = Arrays.asList(admin, limitedUser);
        when(session.createQuery("FROM User", User.class)).thenReturn(query);
        when(query.list()).thenReturn(expectedUsers);

        List<User> result = userRepository.getAllUsers();

        assertSame(expectedUsers, result);
        verify(session).createQuery("FROM User", User.class);
        verify(query).list();
    }

    @Test
    public void testAddUser_ExistingUser() {
        User duplicateAdmin = new Admin("admin", "admin456");

        assertThrows(RuntimeException.class, () -> userRepository.addUser(duplicateAdmin));
    }

    @Test
    public void testGetUser_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";

        assertNull(userRepository.getUser(nonExistingUsername));
        verify(session).get(User.class, nonExistingUsername);
    }

    @Test
    public void testEditUser_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";
        String newPassword = "newNonExistingPassword";

        assertThrows(RuntimeException.class, () -> userRepository.editUser(nonExistingUsername, newPassword));
    }

    @Test
    public void testRemoveUser_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";

        assertThrows(RuntimeException.class, () -> userRepository.removeUser(nonExistingUsername));
    }

    @Test
    public void testEditUser_NullPassword() {
        String username = admin.getUsername();

        assertThrows(RuntimeException.class, () -> userRepository.editUser(username, null));
    }

    @Test
    public void testEditUser_NullUsername() {
        assertThrows(RuntimeException.class, () -> userRepository.editUser(null, "admin123"));
    }

}
