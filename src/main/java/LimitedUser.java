import javax.persistence.Entity;

@Entity
public class LimitedUser extends User {
    protected LimitedUser() {
        super();
    }
    public LimitedUser(String username, String password) {
        super(username, password, "limited");
    }
}