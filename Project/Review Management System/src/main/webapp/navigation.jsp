<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        /* Your CSS styles go here */
        nav {
            background-color: #f4f4f4;
            padding: 10px;
            text-align: center; /* Center the contents */
        }
        nav div {
            display: flex;
            justify-content: space-between; /* Adjusted alignment */
            align-items: center; /* Vertically center items */
        }
        nav a {
            text-decoration: none;
            color: #333;
            padding: 5px 10px;
        }
        nav a:hover {
            background-color: #ccc;
        }
        button {
            padding: 5px 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .user-details {
            display: flex;
            flex-direction: column;
            text-align: right;
        }
    </style>
</head>
<body>

<%
    HttpSession session1 = request.getSession();
    String rollNumber = (String) session.getAttribute("roll_number");
    String username = (String) session.getAttribute("username");
    String fullName = ""; // Initialize full name variable

    // Database connection parameters
    String url = "jdbc:mysql://localhost:3306/reviewdb";
    String dbUsername = "root"; // Replace with your database username
    String dbPassword = "21vv1A12@44"; // Replace with your database password

    try {
        // Establish connection to the database
        Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
        
        // Prepare and execute SQL query to fetch user details
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE roll_number = ?");
        statement.setString(1, rollNumber);
        ResultSet resultSet = statement.executeQuery();

        // Process the result set
        if (resultSet.next()) {
            fullName = resultSet.getString("full_name");
        }

        // Close the resources
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<nav>
    <div>
        <a href="CreateorUpdate.jsp">Update Account</a>
        <a href="addReview.jsp">Add new review</a>
        <a href="deleteAccount.jsp">Delete Account</a>
        <a href="showData.jsp">My Details</a>
        <a href="viewAccounts.jsp">Show All Details</a>
    </div>
    <div class="user-details">
        
        <p>Full Name: <%= fullName %></p>
        <p>Roll Number: <%= rollNumber %></p>
    </div>
    <form action="Logout" method="post">
        <button type="submit">Logout</button>
    </form>
</nav>

</body>
</html>
