import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    protected User() {
    }
    @Id
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        if (username == null) { throw new NullPointerException("Username cannot be null"); }
        if (password == null) { throw new NullPointerException("Password cannot be null"); }
        if (role == null) { throw new NullPointerException("Role cannot be null"); }
        if (username.isEmpty()) { throw new IllegalArgumentException("Username cannot be empty"); }
        if (password.isEmpty()) { throw new IllegalArgumentException("Password cannot be empty"); }
        if (role.isEmpty()) { throw new IllegalArgumentException("Role cannot be empty"); }
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String newPassword) {
        if (newPassword == null) {
            throw new NullPointerException("Password cannot be null");
        }
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = newPassword;
    }

    public void setUsername(String newUsername) {
        if (newUsername == null) {
            throw new NullPointerException("Username cannot be null");
        }
        if (newUsername.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = newUsername;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return "User{" +
                "username=" + username +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}