import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserRepository userRepository = new UserRepositoryImpl();

    public RegisterServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            User newUser;
            if ("admin".equals(role)) {
                newUser = new Admin(username, password);
            } else {
                newUser = new LimitedUser(username, password);
            }
            userRepository.save(newUser);
            request.setAttribute("registerSuccessMessage", "User registered successfully. Please log in.");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } else {
            request.setAttribute("registerErrorMessage", "Username is already taken.");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
    }
}