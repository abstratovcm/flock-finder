import javax.persistence.Entity;

@Entity
public class Admin extends User {
    protected Admin() {
        super();
    }
    public Admin(String username, String password) {
        super(username, password, "admin");
    }
}