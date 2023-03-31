import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query<User> query;
    @Mock
    private CriteriaBuilder builder;
    @Mock
    private CriteriaQuery<User> criteriaQuery;
    @Mock
    private Root<User> root;

    private Admin admin;
    private LimitedUser limitedUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        userRepository = new UserRepositoryImpl(sessionFactory);

        // Create instances of Admin and LimitedUser
        admin = new Admin("admin", "admin123");
        limitedUser = new LimitedUser("limited", "limited123");

        // Set up mock behavior to return Admin and LimitedUser instances when session.get() is called
        when(session.get(User.class, admin.getUsername())).thenReturn(admin);
        when(session.get(User.class, limitedUser.getUsername())).thenReturn(limitedUser);
        when(session.find(User.class, admin.getUsername())).thenReturn(admin);
        when(session.find(User.class, limitedUser.getUsername())).thenReturn(limitedUser);
        when(session.contains(admin)).thenReturn(true);
        when(session.contains(limitedUser)).thenReturn(true);


        when(session.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testSave_Admin() {
        Admin admin = new Admin("admin2", "admin123");

        userRepository.save(admin);

        verify(session).save(admin);
        verify(transaction).commit();
    }

    @Test
    public void testSave_LimitedUser() {
        LimitedUser limitedUser = new LimitedUser("limited2", "limited123");

        userRepository.save(limitedUser);

        verify(session).save(limitedUser);
        verify(transaction).commit();
    }

    @Test
    public void testSave_ExistingUser() {
        User duplicateAdmin = new Admin("admin", "admin456");

        assertThrows(RuntimeException.class, () -> userRepository.save(duplicateAdmin));
    }

    @Test
    public void testSave_ExistingLimitedUser() {
        User duplicateLimited = new Admin("limited", "admin456");

        assertThrows(RuntimeException.class, () -> userRepository.save(duplicateLimited));
    }


    @Test
    public void testFindById_Admin() {
        String username = admin.getUsername();

        Optional<User> result = userRepository.findById(username);

        if (result.isPresent()) {
            assertSame(admin, result.get());
            verify(session).find(User.class, username);
        } else {
            fail("User with username " + username + " not found");
        }
    }

    @Test
    public void testFindById_LimitedUser() {
        String username = limitedUser.getUsername();

        Optional<User> result = userRepository.findById(username);

        if (result.isPresent()) {
            assertSame(limitedUser, result.get());
            verify(session).find(User.class, username);
        } else {
            fail("User with username " + username + " not found");
        }
    }

    @Test
    public void testFindById_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";
        Optional<User> user = userRepository.findById(nonExistingUsername);
        assertFalse(user.isPresent());
        verify(session).find(User.class, nonExistingUsername);
    }

    @Test
    public void testUpdateAttributeById() {
        String username = admin.getUsername();
        String newPassword = "newAdminPassword";

        assertNotEquals(newPassword, admin.getPassword());

        userRepository.updateAttributeById(username, user -> user.setPassword(newPassword));

        verify(session).find(User.class, username);
        verify(session).beginTransaction();
        verify(session).update(admin);
        verify(transaction).commit();

        assertEquals(newPassword, admin.getPassword());
    }

    @Test
    public void updateAttributeById_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";
        String newPassword = "newNonExistingPassword";

        assertThrows(RuntimeException.class, () -> {
            userRepository.updateAttributeById(nonExistingUsername, user ->
            {
                user.setPassword(newPassword);
            });
        });
    }

    @Test
    public void updateAttributeById_NullPassword() {
        String username = admin.getUsername();

        assertThrows(RuntimeException.class, () -> {
            userRepository.updateAttributeById(username, user -> user.setPassword(null));
        });
    }

    @Test
    public void updateAttributeById_NullUsername() {
        assertThrows(RuntimeException.class, () -> {
            userRepository.updateAttributeById(null, user->{
                user.setPassword("admin123");
            });
        });
    }

    @Test
    public void testDeleteById() {
        String username = admin.getUsername();

        userRepository.deleteById(username);

        verify(session).find(User.class, username);
        verify(session).beginTransaction();
        verify(session).delete(admin);
        verify(transaction).commit();

        Optional<User> deletedAdmin = userRepository.findById(admin.getPassword());
        assertFalse(deletedAdmin.isPresent());
    }

    @Test
    public void testDeleteById_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";

        assertThrows(RuntimeException.class, () -> userRepository.deleteById(nonExistingUsername));
    }

    @Test
    public void testFindAll() {
        List<User> expectedUsers = Arrays.asList(admin, limitedUser);
        when(session.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery(User.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(User.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        List<User> result = userRepository.findAll();

        assertSame(expectedUsers, result);
    }

}
