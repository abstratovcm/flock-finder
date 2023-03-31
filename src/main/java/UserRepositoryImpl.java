import org.hibernate.SessionFactory;
public class UserRepositoryImpl extends BaseRepository<User, String> implements UserRepository {
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }
    public UserRepositoryImpl() { super(User.class); }
}