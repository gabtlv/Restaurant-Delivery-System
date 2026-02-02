<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Result</title>
    </head>
    <body>
        <h2>Login Result</h2>
        <%
            Boolean success = (Boolean) request.getAttribute("loginSuccess");
            String email = (String) request.getAttribute("email");
        %>
        
        <% if (success != null && success) { %>
        <p> Login successful! </p>
        <p>Welcome, <%= email %></p>
        <p>Session email: <%= session.getAttribute("customerEmail") %></p>
        
        <a href="browse.html">Browse Restaurants</a>
        <%} else { %>
        <p> Login failed. </p>
        <p>Please check your email and password.</p>
        
        <a href="login.html">Try Again</a>
        <% } %>
        
    </body>
</html>
