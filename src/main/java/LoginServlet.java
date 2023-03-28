import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserRepository userRepository = new UserRepository();

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userRepository.getUser(username);
        if (user != null && user.checkPassword(password)) {
            request.getSession().setAttribute("user", user);
            if ("admin".equals(user.getRole())) {
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