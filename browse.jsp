<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.lab2exc2.MenuItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
    <style>
    body { 
        font-family: Arial, sans-serif; 
        padding: 50px; 
        text-align: center; 
        background-color: #f9f9f9; 
    }
    .result-card { 
        display: inline-block; 
        background: white; 
        padding: 25px; 
        border-radius: 15px; 
        box-shadow: 0 6px 18px rgba(0,0,0,0.1); 
        max-width: 350px; 
        margin-top: 20px; 
    }
    img { 
        width: 100%; 
        max-width: 250px; 
        height: 180px; 
        object-fit: cover; 
        border-radius: 10px; 
    }
    h3 { color: #2c3e50; margin: 15px 0 10px 0; }
    p { color: #7f8c8d; font-size: 0.95em; line-height: 1.4; }
    .error { 
        color: #c0392b; 
        background: #fee; 
        padding: 15px; 
        border-radius: 8px; 
        display: inline-block; 
    }
    .back-link { 
        margin-top: 40px; 
        display: block; 
        color: #3498db; 
        text-decoration: none; 
        font-weight: bold; 
    }
</style>
</head>
<body>

    <%
        // Retrieve the list from the request attribute
        List<MenuItem> results = (List<MenuItem>) request.getAttribute("menuResults");

        if (results != null && !results.isEmpty()) {
            // If items were found, display them
            out.println("<h2>Search Results</h2>");
            for (MenuItem item : results) {
    %>
                <div class="result-card">
                    <img src="<%= item.getImagePath() %>" alt="Food Image">
                    <h3><%= item.getName() %></h3>
                    <p><%= item.getDescription() %></p>
                </div>
    <%
            }
        } else {
            // If no items were found
    %>
            <div class="error">
                <p>No menu items found.</p>
            </div>
    <%
        }
    %>

    <a href="browse.html" class="back-link">Back to Menu</a>

</body>
</html>