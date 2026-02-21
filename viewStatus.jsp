<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Status</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 50px; text-align: center; margin: 0; }
        .status-box { 
            display: inline-block; 
            background-color: white; 
            padding: 40px; 
            width: 80%; 
            max-width: 450px; 
            border-radius: 25px; 
            box-shadow: 0 4px 12px rgba(0,0,0,0.1); 
        }
        .status-badge {
            background-color: #e8f5e9;
            color: green;
            padding: 15px;
            border-radius: 50px;
            font-weight: bold;
            display: inline-block;
            margin: 20px 0;
            border: 2px solid green;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: green;
            text-decoration: none;
            font-weight: bold;
        }
        h1 { margin: 0; margin-bottom: 10px;}
        h3 { margin: 0; margin-bottom: 40px;}
    </style>
</head>
<body>

    <h1>Burger Buns</h1>
    <h3>Online Delivery Service</h3>

    <div class="status-box">
        <h2>Thank you for your order!</h2>
        <p>We've received your details and our chefs are getting to work.</p>
        
        <div class="status-badge">
            Current Status: Order in Progress
        </div>
        
        <br>
        <a href="<%= request.getContextPath() %>/browse" class="back-link">Order more food?</a>
    </div>

</body>
</html>
