import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextInitializerServlet implements ServletContextListener {

    public ContextInitializerServlet() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String rootPath = sce.getServletContext().getRealPath("/");

        DatabaseManager.setServletContext(rootPath);
        DatabaseManager.createTable();

        System.setProperty("rootPath", rootPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up resources if necessary
    }
}