import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserRepository userRepository = new UserRepositoryImpl();

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = userRepository.findById(username);
        if (user.isPresent() && user.get().checkPassword(password)) {
            request.getSession().setAttribute("user", user.get());
            if ("admin".equals(user.get().getRole())) {
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("dashboard.jsp");
            }
        } else {
            request.setAttribute("loginErrorMessage", "Invalid username or password.");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
    }
}