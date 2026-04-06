<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Status</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 100px; text-align: center; }
        .box { display: inline-block; background-color: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        a { display: inline-block; margin-top: 20px; color: green; text-decoration: none; }
    </style>
</head>
<body>
    <div class="box">
        <h2>Order Status</h2>
        <p><%= request.getAttribute("orderStatus") %></p>
        <a href="<%= request.getContextPath() %>/browse">Back to Menu</a>
    </div>
</body>
</html>