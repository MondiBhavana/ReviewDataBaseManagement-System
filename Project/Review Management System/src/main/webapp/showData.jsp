<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Account Data</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="navigation.jsp" />
        <h1>Show Enrollment Data</h1>
        <% 
            // Retrieve username from session
            HttpSession session1 = request.getSession();
            String roll_number = (String) session1.getAttribute("roll_number");

            // Database connection and query
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/reviewdb";
                String dbUsername = "root";
                String dbPassword = "21vv1A12@44";

                Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM customer WHERE roll_number = ?");
                ps.setString(1, roll_number);

                ResultSet rs = ps.executeQuery();

                // Display student data in a table
                if (rs.next()) {
        %>
        <table>
            <tr>
                <th>user ID</th>
                <th>User Name</th>
              
                <th>roll_number</th>
                <th>full Name</th>
                  <th>password</th>
            </tr>
            <tr>
                <td><%= rs.getInt("id") %></td>
                <td><%= rs.getString("username") %></td>
                
                <td><%= rs.getString("roll_number") %></td>
                <td><%= rs.getString("full_name") %></td>
                <td><%= rs.getString("password") %></td>
                
            </tr>
        </table>
        <%
            } else {
        %>
        <p class="error">No enrollment data found for roll_number: <%= roll_number %></p>
        <%
            }

                // Close resources
                rs.close();
                ps.close();
                conn.close();
            } catch (ClassNotFoundException | SQLException e) {
        %>
        <p class="error">Error: <%= e.getMessage() %></p>
        <%
            }
        %>
    </div>
</body>
</html>
