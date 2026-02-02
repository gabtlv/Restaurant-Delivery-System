<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Browse Results</title>
</head>
<body>
    <h2>Browse Menu Results</h2>

    <%
        List<String> menuItems = (List<String>) request.getAttribute("menuItems");
        String query = (String) request.getAttribute("query");
        if (query == null) query = "";
    %>

    <p><strong>Search term:</strong> <%= query %></p>

    <%
        if (menuItems == null || menuItems.isEmpty()) {
    %>
        <p>No menu items found.</p>
    <%
        } else {
    %>
        <ul>
            <%
                for (String item : menuItems) {
            %>
                <li><%= item %></li>
            <%
                }
            %>
        </ul>
    <%
        }
    %>

    <br>
    <a href="browse.html">Search Again</a>
    &nbsp;|&nbsp;
    <a href="login.html">Back to Login</a>
</body>
</html>
