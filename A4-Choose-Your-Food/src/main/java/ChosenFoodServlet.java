import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "chosenFoodServlet", value = "/chosen-food")
public class ChosenFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!verifyPassword(request, response)) return;

        response.setContentType("text/html");

        Map<String, String> preparedTableContent = prepareTable();
        String html;
        if(preparedTableContent == null){
            html = generateHtmlMessage();
        }else{
            html = generateHtml(preparedTableContent);
        }
        PrintWriter out = response.getWriter();
        out.println(html);
    }

    private Map<String, String> prepareTable() {
        if (this.getServletContext().getAttribute("monday") == null)
            return null;

        Map<String, String> preparedTableContent = new HashMap<>();
        String[] daysOfWeek = {"monday", "tuesday", "wednesday", "thursday", "friday"};

        for (String day : daysOfWeek) {
            Map<String, Integer> dailyMenu = (Map<String, Integer>) this.getServletContext().getAttribute(day);
            StringBuilder tableRowsForDay = new StringBuilder();
            int foodItemNumber = 1;

            for (Map.Entry<String, Integer> entry : dailyMenu.entrySet()) {
                String tableRow = generateTableRow(foodItemNumber, entry.getKey(), entry.getValue());
                tableRowsForDay.append(tableRow);
                foodItemNumber++;
            }

            preparedTableContent.put(day, tableRowsForDay.toString());
        }

        return preparedTableContent;
    }

    private String generateTableRow(int foodItemNumber, String foodItem, int quantity) {
        return "<tr>\n" +
                "<td>" + foodItemNumber + "</td>\n" +
                "<td>" + foodItem + "</td>\n" +
                "<td>" + quantity + "</td>\n" +
                "<tr>\n";
    }

    private String generateHtmlMessage(){
        return "<html><head></head><body>" +
                "<h1>No data yet.</h1>" +
                "</body></html>";
    }
    private String generateHtml(Map<String, String> preparedTableContent) {

        String tableHeader = """
                <tr>
                <th>*</th>
                <th>Dish</th>
                <th>Quantity</th>
                </tr>
                """;

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Chosen food</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css\" integrity=\"sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Chosen food</h1>" +
                "<form method=\"POST\" action=\"/\">\n" +
                "    <input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Clear\"/>\n" +
                "</form>" +
                "<h2>Monday</h2>\n" +
                "<table name=\"monday\" id=\"monday\">\n" +
                tableHeader +
                preparedTableContent.get("monday") +
                "</table>\n" +
                "\n" +
                "<h2>Tuesday</h2>\n" +
                "<table name=\"tuesday\" id=\"tuesday\">\n" +
                tableHeader +
                preparedTableContent.get("tuesday") +
                "</table>\n" +
                "\n" +
                "<h2>Wednesday</h2>\n" +
                "<table name=\"wednesday\" id=\"wednesday\">\n" +
                tableHeader +
                preparedTableContent.get("wednesday") +
                "</table>\n" +
                "\n" +
                "<h2>Thursday</h2>\n" +
                "<table name=\"thursday\" id=\"thursday\">\n" +
                tableHeader +
                preparedTableContent.get("thursday") +
                "</table>\n" +
                "\n" +
                "<h2>Friday</h2>\n" +
                "<table name=\"friday\" id=\"friday\">\n" +
                tableHeader +
                preparedTableContent.get("friday") +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    private boolean verifyPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userPassword = request.getParameter("password");
        String requiredPassword = readPasswordFromFile();
        System.out.println("Required password: " + requiredPassword);

        if(userPassword == null || userPassword.isEmpty())
            return incorrectPassword("Password is empty", response);
        if (!userPassword.equals(requiredPassword))
            return incorrectPassword("Invalid password", response);

        return true;
    }

    private String readPasswordFromFile() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("password.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
        return br.readLine().trim();
    }

    private boolean incorrectPassword(String message, HttpServletResponse response) throws IOException {
        response.setStatus(403);
        response.getOutputStream().println("<html><body><h1>" + message + "</h1></body></html>");
        return false;
    }


}
