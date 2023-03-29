import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Admin admin;
    private LimitedUser limitedUser;

    @BeforeEach
    void setUp() {
        admin = new Admin("admin", "admin123");
        limitedUser = new LimitedUser("limited", "limited123");
    }

    @Test
    void testAdminCreation() {
        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
        assertEquals("admin", admin.getRole());
        assertTrue(admin.checkPassword("admin123"));
    }

    @Test
    void testLimitedUserCreation() {
        assertNotNull(limitedUser);
        assertEquals("limited", limitedUser.getUsername());
        assertEquals("limited", limitedUser.getRole());
        assertTrue(limitedUser.checkPassword("limited123"));
    }

    @Test
    void testSetPassword() {
        admin.setPassword("newAdmin123");
        assertTrue(admin.checkPassword("newAdmin123"));

        limitedUser.setPassword("newLimited123");
        assertTrue(limitedUser.checkPassword("newLimited123"));
    }

    @Test
    void testToString() {
        String expectedAdminString = "User{username=admin, password='admin123', role=admin}";
        assertEquals(expectedAdminString, admin.toString());

        String expectedLimitedUserString = "User{username=limited, password='limited123', role=limited}";
        assertEquals(expectedLimitedUserString, limitedUser.toString());
    }

    @Test
    void testCheckPassword() {
        assertTrue(admin.checkPassword(admin.getPassword()));
        assertTrue(limitedUser.checkPassword(limitedUser.getPassword()));
    }

    @Test
    void testCheckPassword_InvalidPassword() {
        assertFalse(admin.checkPassword("wrongPassword"));
        assertFalse(limitedUser.checkPassword("wrongPassword"));
    }

    @Test
    void testUsernameCaseSensitivity() {
        assertNotEquals("Admin", admin.getUsername());
        assertNotEquals("Limited", limitedUser.getUsername());
    }

    @Test
    void testSetUsername() {
        admin.setUsername("newAdmin");
        limitedUser.setUsername("newLimited");

        assertEquals("newAdmin", admin.getUsername());
        assertEquals("newLimited", limitedUser.getUsername());
    }

    @Test
    void testSetUsername_EmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> admin.setUsername(""));
        assertThrows(IllegalArgumentException.class, () -> limitedUser.setUsername(""));
    }

    @Test
    void testSetPassword_EmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> admin.setPassword(""));
        assertThrows(IllegalArgumentException.class, () -> limitedUser.setPassword(""));
    }

    @Test
    void testSetUsername_NullUsername() {
        assertThrows(NullPointerException.class, () -> admin.setUsername(null));
        assertThrows(NullPointerException.class, () -> limitedUser.setUsername(null));
    }

    @Test
    void testSetPassword_NullPassword() {
        assertThrows(NullPointerException.class, () -> admin.setPassword(null));
        assertThrows(NullPointerException.class, () -> limitedUser.setPassword(null));
    }
    @Test
    public void testUserConstructor_NullPassword() {
        assertThrows(NullPointerException.class, () -> new Admin("admin", null));
        assertThrows(NullPointerException.class, () -> new LimitedUser("limited", null));
    }
    @Test
    public void testUserConstructor_NullUsername() {
        assertThrows(NullPointerException.class, () -> new Admin(null, "admin123"));
        assertThrows(NullPointerException.class, () -> new LimitedUser(null, "limited123"));
    }
    @Test
    public void testUserConstructor_EmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Admin("admin", ""));
        assertThrows(IllegalArgumentException.class, () -> new LimitedUser("limited", ""));
    }
    @Test
    public void testUserConstructor_EmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Admin("", "admin123"));
        assertThrows(IllegalArgumentException.class, () -> new LimitedUser("", "limited123"));
    }
}