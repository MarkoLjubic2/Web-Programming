import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "confirmServlet", value = "/confirm")
public class ConfirmServlet extends HttpServlet {

    private final Map<String, Map<String, Integer>> food = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "You have successfully selected food" + "</h1>");

        String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};
        for (String day : days) {
            String selectedFood = (String) request.getSession().getAttribute(day);
            out.println("<p>" + day + ": " + selectedFood + "</p>");
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getSession().getAttribute("monday") != null &&
                request.getServletContext().getAttribute("restart") != "true"){
            response.sendRedirect("/already-chosen-menu");
            return;
        }

        String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};
        this.fillFoodMap(request, days);
        this.updateServletContextAttributes(request, days);
        this.food.clear();
        response.sendRedirect( "/confirm");
    }

    private void updateServletContextAttributes(HttpServletRequest request, String[] days) {

        for(String day: days){
            request.getSession().setAttribute(day, request.getParameter(day));

            if(request.getServletContext().getAttribute(day) == null){
                request.getServletContext().setAttribute(day, this.food.get(day));
            }
            else{
                Map<String, Integer> map = (Map<String, Integer>) request.getServletContext().getAttribute(day);
                for(String k: this.food.get(day).keySet()){
                    map.put(k, map.getOrDefault(k, 0) + 1);
                }
                request.getServletContext().setAttribute(day, map);
            }
        }
        request.getServletContext().setAttribute("restart", "false");
    }

    private void fillFoodMap(HttpServletRequest request, String[] days) {

        for (String day: days) {
            Map<String, Integer> dailyMenu = new ConcurrentHashMap<>();
            dailyMenu.put(request.getParameter(day), 1);
            this.food.put(day, dailyMenu);
        }
    }
}