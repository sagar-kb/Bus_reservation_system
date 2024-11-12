import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/reservations")
public class ReservationServlet extends HttpServlet {
    private final static String query = "select bid,source, destination, date, name, gender, email, phone from bus_routes where uid=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //link the bootstrap
        Integer id = Integer.parseInt(req.getParameter("id"));

        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<div style='margin:auto;width:500px;margin-top:50px;'>");

        pw.println("<marquee><h2 class='text-primary'>Recent Ticket Bookings</h2></marquee>");
        pw.println("</div>");

        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking","root","Abhi@1234");
                PreparedStatement ps = con.prepareStatement(query);){
            //resultSet
        	ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            pw.println("<div style='margin:auto;width:900px;margin-top:50px;'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<th>BID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Mobile No</th>");
            pw.println("<th>Date</th>");
            pw.println("<th>Source</th>");
            pw.println("<th>Destination</th>");
            pw.println("<th>gender</th>");

            pw.println("</tr>");
            while(rs.next()) {
                pw.println("<tr>");
                pw.println("<td>"+rs.getInt(1)+"</td>");
                pw.println("<td>"+rs.getString(5)+"</td>");
                pw.println("<td>"+rs.getString(7)+"</td>");
                pw.println("<td>"+rs.getString(8)+"</td>");
                pw.println("<td>"+rs.getString(4)+"</td>");
                pw.println("<td>"+rs.getString(2)+"</td>");
                pw.println("<td>"+rs.getString(3)+"</td>");
                pw.println("<td>"+rs.getString(6)+"</td>");

                pw.println("</tr>");
            }
            pw.println("</table>");
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<button class='btn btn-outline-success d-block'><a href='home.html?id="+id+"'>Back</a></button>");
        pw.println("</div>");
        //close the stream
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
