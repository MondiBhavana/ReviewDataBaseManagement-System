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

@WebServlet("/AddReviewServlet")
public class AddReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        int customer_id = Integer.parseInt(request.getParameter("customer_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        String url = "jdbc:mysql://localhost:3306/reviewdb";
        String username = "root";
        String password = "21vv1A12@44";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO review (product_id, customer_id, rating, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, product_id);
            ps.setInt(2, customer_id);
            ps.setInt(3, rating);
            ps.setString(4, comment);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                request.setAttribute("message", "Review added successfully!");
                request.getRequestDispatcher("viewAccounts.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to add review.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
