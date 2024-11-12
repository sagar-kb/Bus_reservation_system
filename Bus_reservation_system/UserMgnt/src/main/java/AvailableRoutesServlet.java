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

@WebServlet("/available_routes")
public class AvailableRoutesServlet extends HttpServlet {
    private final static String query ="SELECT COUNT(*) FROM bus_routes WHERE date = ? AND source = ? AND destination = ? ";
    private int id=0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        
        id = Integer.parseInt(req.getParameter("id"));

        
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<title>Available Routes</title>");
        pw.println("<style>");
        pw.println(".form-container {");
        pw.println("    max-width: 400px;");
        pw.println("    margin: 0 auto;");
        pw.println("}");
        pw.println("select {");
        pw.println("    width: 100%;");
        pw.println("    padding: 10px;");
        pw.println("    margin-bottom: 20px;");
        pw.println("    border-radius: 5px;");
        pw.println("}");
        pw.println("input[type='submit'] {");
        pw.println("    width: 100%;");
        pw.println("    padding: 10px;");
        pw.println("    background-color: #007bff;");
        pw.println("    color: white;");
        pw.println("    border: none;");
        pw.println("    border-radius: 5px;");
        pw.println("    cursor: pointer;");
        pw.println("}");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div style='margin:auto;width:200px;margin-top:1px;'>");
        pw.println("<marquee><h2 class='text-primary'>Available Routes</h2></marquee>");
        pw.println("</div>");       
        pw.println("<div class='form-container'>");
        pw.println("<form method='post' onsubmit='return validateForm();'>");
        pw.println("<label for='source'>Source:</label>");
        pw.println("<select id='source' name='source'>");
        pw.println("<option value='Banglore'>Banglore</option>");
        pw.println("<option value='Mumbai'>Mumbai</option>");
        pw.println("<option value='Bellary'>Bellary</option>");
        pw.println("<option value='Mysore'>Mysore</option>");
        pw.println("<option value='Delhi'>Delhi</option>");
        pw.println("<option value='Bijapur'>Bijapur</option>");
        pw.println("<option value='Chennai'>Chennai</option>");
        pw.println("<option value='Hyderabad'>Hyderabad</option>");
        pw.println("<option value='Goa'>Goa</option>");
        pw.println("<option value='Hubli'>Hubli</option>");        pw.println("</select>");
        pw.println("<label for='destination'>Destination:</label>");
        pw.println("<select id='destination' name='destination'>");
        pw.println("<option value='Banglore'>Banglore</option>");
        pw.println("<option value='Mumbai'>Mumbai</option>");
        pw.println("<option value='Bellary'>Bellary</option>");
        pw.println("<option value='Mysore'>Mysore</option>");
        pw.println("<option value='Delhi'>Delhi</option>");
        pw.println("<option value='Bijapur'>Bijapur</option>");
        pw.println("<option value='Chennai'>Chennai</option>");
        pw.println("<option value='Hyderabad'>Hyderabad</option>");
        pw.println("<option value='Goa'>Goa</option>");
        pw.println("<option value='Hubli'>Hubli</option>");        pw.println("</select>");
        pw.println("<label for='bookingDate'>Date:</label>");
        pw.println("<input type='date' id='bookingDate' name='date' class='form-control' required>");
        pw.println("<input type='submit' value='Submit'>");
        pw.println("</form>");
        pw.println("</div>");
        pw.println("<script>");
        pw.println("function validateForm() {");
        pw.println("    var source = document.getElementById('source').value;");
        pw.println("    var destination = document.getElementById('destination').value;");
        pw.println("    if (source === destination) {");
        pw.println("        alert('Source and destination cannot be the same.');");
        pw.println("        return false;");
        pw.println("    }");
        pw.println("    var selectedDate = new Date(document.getElementById('bookingDate').value);");
        pw.println("    var currentDate = new Date();");
        pw.println("    currentDate.setHours(0, 0, 0, 0); // Set time to midnight for comparison");
        pw.println("    if (selectedDate < currentDate) {");
        pw.println("        alert('Please select a date that is not in the past.');");
        pw.println("        return false;");
        pw.println("    }");
        pw.println("    return true;");
        pw.println("}");
        pw.println("</script>");
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        String date = req.getParameter("date");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        int availableSeats =0; 

        
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking","root","Abhi@1234");
                PreparedStatement ps = con.prepareStatement(query);){
            //resultSet
        	
        	ps.setString(2, source);
            ps.setString(3, destination);
            ps.setString(1, date);
            System.out.println(query);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                availableSeats = rs.getInt(1);
            }
        }
            catch (SQLException e) {
                e.printStackTrace();
            }

            
        // Database connection and query to count available seats
        System.out.println(availableSeats+" "+source+" "+destination+" "+date);

        if (availableSeats < 30 ) {
            // Allow booking
            res.sendRedirect("book.html?date=" + date + "&source=" + source + "&destination=" + destination+"&id="+Integer.parseInt(req.getParameter("id"))); // Redirect to booking page
        } else {
            // Notify user that seats are full
            PrintWriter pw = res.getWriter();
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
            pw.println("<style>");
            pw.println(".form-container {");
            pw.println("    max-width: 400px;");
            pw.println("    margin: 0 auto;");
            pw.println("}");
            pw.println("select {");
            pw.println("    width: 100%;");
            pw.println("    padding: 10px;");
            pw.println("    margin-bottom: 20px;");
            pw.println("    border-radius: 5px;");
            pw.println("}");
            pw.println("input[type='submit'] {");
            pw.println("    width: 100%;");
            pw.println("    padding: 10px;");
            pw.println("    background-color: #007bff;");
            pw.println("    color: white;");
            pw.println("    border: none;");
            pw.println("    border-radius: 5px;");
            pw.println("    cursor: pointer;");
            pw.println("}");
            pw.println("</style>");
            pw.println("<body>");
            pw.println("<div style='margin:auto;width:400px;margin-top:1px;'>");
            pw.println("<h2 class='bg-danger text-light text-center'>Sorry, seats are full for the selected route and date. Please choose another date or route.</h2>");
            pw.println("</div>");
            pw.println("</body>");
            
            pw.println("</head>");
            pw.close();
        }
    }
    
}
