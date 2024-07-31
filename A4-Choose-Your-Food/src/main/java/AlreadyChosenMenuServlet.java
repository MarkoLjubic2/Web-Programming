import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "alreadyChosenMenuServlet", value = "/already-chosen-menu")
public class AlreadyChosenMenuServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(getChosenMenu(request));
    }

    private String getChosenMenu(HttpServletRequest request){

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Chosen Menu</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css\" integrity=\"sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>You have already chosen the menu for the next week!</h1>" +
                "<h2>Monday:</h2>" +
                "<h3>" +
                request.getSession().getAttribute("monday") +
                "</h3><br>" +
                "<h2>Tuesday:</h2>" +
                "<h3>" +
                request.getSession().getAttribute("tuesday") +
                "</h3><br>" +
                "<h2>Wednesday:</h2>" +
                "<h3>" +
                request.getSession().getAttribute("wednesday") +
                "</h3><br>" +
                "<h2>Thursday:</h2>" +
                "<h3>" +
                request.getSession().getAttribute("thursday") +
                "</h3><br>" +
                "<h2>Friday:</h2>" +
                "<h3>" +
                request.getSession().getAttribute("friday") +
                "</h3><br>" +
                "</body>\n" +
                "</html>";
    }
}
