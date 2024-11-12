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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final static String query = "select * from user WHERE email = ? AND password = ?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // get the values
        String name = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.print(name+""+password);
        
        // load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking", "root", "Abhi@1234");
                PreparedStatement ps = con.prepareStatement(query)) {
            // set the values
            ps.setString(1, name);
            ps.setString(2, password);
            System.out.println(query);
            // execute the query
            ResultSet rs = ps.executeQuery();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if (rs.next()) {
                pw.println("<h2 class='bg-success text-light text-center'>Login Successful</h2>");
                pw.println("<a href='home.html?id="+rs.getInt(1)+"'><button class='btn btn-outline-success'>Home</button></a>");

            } else {
                pw.println("<h2 class='bg-danger text-light text-center'>Login Failed</h2>");
                pw.println("<a href='login.html?'><button class='btn btn-outline-success'>Retry</button></a>");

            }
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }//"book.html?date=" + date + "&source=" + source + "&destination=" + destination
        pw.println("</div>");
        // close the stream
        pw.close();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
