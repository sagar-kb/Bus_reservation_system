import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookticket")
public class BookTicketServlet extends HttpServlet{
	

    private final static String queryInsert = "INSERT INTO bus_routes (source,destination,date,name,gender, email, phone,uid) VALUES (?, ?, ?, ?, ?, ?,?,?)";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // get the values
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String dob = req.getParameter("date");
        String gender = req.getParameter("gender");
        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        String uid = req.getParameter("uid");

        // load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking", "root", "root");
                PreparedStatement psInsert = con.prepareStatement(queryInsert);) {
            
            // Check if the selected table is already booked for the given date
            
            // If the table is available, insert the new booking
            psInsert.setString(1, source);
            psInsert.setString(2, destination);
            psInsert.setString(3, dob);
            psInsert.setString(4, name);
            psInsert.setString(5, gender);
            psInsert.setString(6, email );
            psInsert.setString(7, mobile );
            psInsert.setString(8, uid );

            // Execute the query
            int count = psInsert.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if (count == 1) {
                pw.println("<h2 class='bg-danger text-light text-center'>Ticket Booked Successfully</h2>");
                pw.println("<a href='home.html?id="+uid+"'><button class='btn btn-outline-success'>Home</button></a>");

            } else {
                pw.println("<h2 class='bg-danger text-light text-center'>Failed.....retry again</h2>");
                pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button></a>");

            }
            pw.println("</div>");
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // close the stream
        pw.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
