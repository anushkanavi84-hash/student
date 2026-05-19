import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.ArrayList;

public class SimpleHttpServer {

    static ArrayList<String[]> students = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        HttpServer server =
        HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new MyHandler());

        server.setExecutor(null);

        server.start();

        System.out.println("Server Started at http://localhost:8000");
    }

    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {

            String query = t.getRequestURI().getQuery();

            if(query != null) {

                String params[] = query.split("&");

                String name = params[0].split("=")[1];

                String usn = params[1].split("=")[1];

                String gender = params[2].split("=")[1];

                String marks = params[3].split("=")[1];

                int m = Integer.parseInt(marks);

                String grade;

                if(m >= 90) {
                    grade = "A";
                }

                else if(m >= 75) {
                    grade = "B";
                }

                else if(m >= 50) {
                    grade = "C";
                }

                else {
                    grade = "Fail";
                }

                students.add(new String[]{
                    name, usn, gender, marks, grade
                });
            }

            String tableRows = "";

            for(String s[] : students) {

                tableRows +=

                "<tr>" +

                "<td>" + s[0] + "</td>" +

                "<td>" + s[1] + "</td>" +

                "<td>" + s[2] + "</td>" +

                "<td>" + s[3] + "</td>" +

                "<td>" + s[4] + "</td>" +

                "</tr>";
            }

            String response =

            "<html>" +

            "<head>" +

            "<title>Student Result Generator</title>" +

            "</head>" +

            "<body style='font-family:Arial; background-color:#f0f8ff;'>" +

            "<div style='width:500px; margin:auto; margin-top:50px; " +

            "background:white; padding:40px; border-radius:10px; " +

            "box-shadow:0px 0px 10px gray;'>" +

            "<h1 style='color:blue; text-align:center;'>" +

            "Student Result Generator</h1>" +

            "<form method='GET'>" +

            "<label>Student Name</label><br>" +

            "<input type='text' name='name' " +

            "style='width:100%; height:40px;'><br><br>" +

            "<label>USN</label><br>" +

            "<input type='text' name='usn' " +

            "style='width:100%; height:40px;'><br><br>" +

            "<label>Gender</label><br>" +

            "<select name='gender' " +

            "style='width:100%; height:40px;'>" +

            "<option>Male</option>" +

            "<option>Female</option>" +

            "</select><br><br>" +

            "<label>Marks</label><br>" +

            "<input type='text' name='marks' " +

            "style='width:100%; height:40px;'><br><br>" +

            "<input type='submit' value='Generate Result' " +

            "style='background-color:blue; color:white; width:100%; " +

            "height:45px; border:none; font-size:18px;'>" +

            "</form>" +

            "<br><br>" +

            "<h2 style='color:green;'>Result Table</h2>" +

            "<table border='1' width='100%' cellpadding='10' " +

            "style='border-collapse:collapse; text-align:center;'>" +

            "<tr style='background-color:#dbeafe;'>" +

            "<th>Name</th>" +

            "<th>USN</th>" +

            "<th>Gender</th>" +

            "<th>Marks</th>" +

            "<th>Grade</th>" +

            "</tr>" +

            tableRows +

            "</table>" +

            "</div>" +

            "</body>" +

            "</html>";

            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();

            os.write(response.getBytes());

            os.close();
        }
    }
}