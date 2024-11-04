package reviewmanagement;

import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roll_number = request.getParameter("roll_number");
        String password = request.getParameter("password");

        if (authenticateUser(roll_number, password, request.getSession())) {
            // Authentication successful, set session attribute
            request.getSession().setAttribute("roll_number", roll_number);
            response.sendRedirect("index.jsp");
        } else {
            // Authentication failed
            response.sendRedirect("index.html?error=1");
        }
    }

    private boolean authenticateUser(String roll_number, String password, HttpSession session) {
        String url = "jdbc:mysql://localhost:3306/reviewdb";
        String dbUsername = "root";
        String dbPassword = "21vv1A12@44";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customer WHERE roll_number=? AND password=?")) {
            stmt.setString(1, roll_number);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            boolean userExists = rs.next(); // true if user exists with provided credentials

            // Store authentication status in session if user exists
            if (userExists) {
                session.setAttribute("authenticated", true);
            }

            return userExists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of any SQL exception
        }
    }
}
