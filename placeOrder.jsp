<%@ page import="java.util.List" %>
<%@ page import="Helper.MenuItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Place Order</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 50px; text-align: center; margin: 0; }
        .login-box { 
          display: inline-block; 
          background-color: white; 
          padding: 30px; width: 100%; 
          max-width: 350px; 
          border-radius: 25px; 
          box-shadow: 0 4px 12px rgba(0,0,0,0.1); 
          text-align: center; 
        }
        h1 { margin: 0; margin-bottom: 10px;}
        h2 { margin: 0; margin-bottom: 10px;}
        h3 { margin: 0; margin-bottom: 40px;}
        input { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 25px; box-sizing: border-box; outline: none; }
        button { width: 100%; padding: 12px; background-color: green; color: white; border: none; border-radius: 25px; cursor: pointer; font-size: 1em; font-weight: bold; margin-top: 10px; }
     
        .back-link {
            margin-top: 30px;
            display: inline-block;
            color: green;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <h1>Burger Buns</h1>
    <h3>Online Delivery Service</h3>
    <div class="login-box">
        <h2>Checkout Details</h2>
        <form action="<%= request.getContextPath() %>/login" method="post">
            <input type="text" name="name" placeholder="Full Name" required>
            <input type="text" name="address" placeholder="Delivery Address" required>
            <input type="text" name="cardnumber" placeholder="9 Digit Card Number" pattern="\d{9}" title="Please enter exactly 9 digits" required>
            <button type="submit">Confirm and Place Order</button>
        </form>

        <a href="<%= request.getContextPath() %>/viewCart" class="back-link">← Back to Cart</a>
    </div>

</body>
</html>
