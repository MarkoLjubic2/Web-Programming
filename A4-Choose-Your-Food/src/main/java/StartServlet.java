import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "startServlet", value = "/")
public class StartServlet extends HttpServlet {

    private final Map<String, List<String>> menu = new ConcurrentHashMap<>();

    public void init() {
        try {
            readMenuFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doGet in StartServlet");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(generateHtml());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doPost in StartServlet");

        for (String day : menu.keySet()) {
            request.getSession().setAttribute(day, null);
            request.getServletContext().setAttribute(day, null);
        }

        request.getServletContext().setAttribute("restart", "true");

        response.sendRedirect("/");
    }

    private void readMenuFromFile() throws Exception {
        String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};

        for (String day : days) {
            List<String> menuList = new ArrayList<>();
            InputStream is = StartServlet.class.getClassLoader().getResourceAsStream(day + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            String line;
            while ((line = br.readLine()) != null)
                menuList.add(line);
            menu.put(day, menuList);
        }
    }

    private String generateHtml(){

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Choose your food</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css\" integrity=\"sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<h1>Choose your lunch</h1>\n" +
                "<br>\n" +
                "<form method=\"POST\" action=\"/confirm\">\n" +
                "    <label for=\"monday\">Monday</label><br>\n" +
                "    <select id=\"monday\" name=\"monday\">\n" +
                loadOptionsMenu("monday") +
                "    </select><br><br>\n" +
                "\n" +
                "    <label for=\"tuesday\">Tuesday</label><br>\n" +
                "    <select id=\"tuesday\" name=\"tuesday\">\n" +
                loadOptionsMenu("tuesday") +
                "    </select><br><br>\n" +
                "\n" +
                "    <label for=\"wednesday\">Wednesday</label><br>\n" +
                "    <select id=\"wednesday\" name=\"wednesday\">\n" +
                loadOptionsMenu("wednesday") +
                "    </select><br><br>\n" +
                "\n" +
                "    <label for=\"thursday\">Thursday</label><br>\n" +
                "    <select id=\"thursday\" name=\"thursday\">\n" +
                loadOptionsMenu("thursday") +
                "    </select><br><br>\n" +
                "\n" +
                "    <label for=\"friday\">Friday</label><br>\n" +
                "    <select id=\"friday\" name=\"friday\">\n" +
                loadOptionsMenu("friday") +
                "    </select><br><br>\n" +
                "    <input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit\"/>\n" +
                "</form>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    private String loadOptionsMenu(String day){
        StringBuilder sb = new StringBuilder();
            for (String menuItem : menu.get(day)) {
                sb.append("<option>");
                sb.append(menuItem);
                sb.append("</option>\n");
            }
    return sb.toString();
    }
}


