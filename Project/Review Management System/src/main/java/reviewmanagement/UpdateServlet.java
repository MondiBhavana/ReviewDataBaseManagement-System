package reviewmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rollNumber = request.getParameter("studentId");
        int review_id = Integer.parseInt(request.getParameter("review_id"));
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        int customer_id = Integer.parseInt(request.getParameter("customer_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        String url = "jdbc:mysql://localhost:3306/reviewdb";
        String username = "root";
        String password = "21vv1A12@44";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE review SET review_id=?, product_id=?, customer_id=? , rating=? ,comment=? WHERE review_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, review_id);
            ps.setInt(2, product_id);
            ps.setInt(3, customer_id);
            ps.setInt(4, rating);
            ps.setString(5, comment);
            ps.setInt(6, review_id); // Added to specify which review_id to update

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("viewAccounts.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
