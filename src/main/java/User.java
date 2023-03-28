import javax.persistence.*;
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
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
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
}