import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CattleWeightServlet")
public class CattleWeightServlet extends HttpServlet {

    public CattleWeightServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cattleId = request.getParameter("cattleId");
        double weight = Double.parseDouble(request.getParameter("weight"));
        DatabaseManager.insertData(cattleId, weight);
        List<CattleWeight> cattleWeights = DatabaseManager.getData();
        request.setAttribute("cattleWeights", cattleWeights);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CattleWeight> cattleWeights = DatabaseManager.getData();

        request.setAttribute("cattleWeights", cattleWeights);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
