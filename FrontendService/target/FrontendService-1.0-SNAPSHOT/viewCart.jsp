<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Cart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 50px; text-align: center; margin: 0; }
        .cart-box { display: inline-block; background-color: white; padding: 30px; width: 100%; max-width: 500px; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { padding: 10px; border-bottom: 1px solid #eee; text-align: left; }
        .total { font-weight: bold; font-size: 1.2em; margin-bottom: 20px; }
        input { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 25px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: green; color: white; border: none; border-radius: 25px; cursor: pointer; font-size: 1em; }
        a { display: inline-block; margin-top: 15px; color: green; text-decoration: none; }
    </style>
</head>
<body>
    <div class="cart-box">
        <h2>Your Cart</h2>
        <%
            List<String[]> cartItems = (List<String[]>) request.getAttribute("cartItems");
            String grandTotal = (String) request.getAttribute("grandTotal");
        %>
        <table>
            <tr><th>Item</th><th>Price</th></tr>
            <%
                if (cartItems != null && !cartItems.isEmpty()) {
                    for (String[] item : cartItems) {
            %>
                    <tr>
                        <td><%= item[0] %></td>
                        <td>$<%= item[1] %></td>
                    </tr>
            <%
                    }
                } else {
            %>
                    <tr><td colspan="2">Cart is empty</td></tr>
            <%
                }
            %>
        </table>
        <div class="total">Total: $<%= grandTotal %></div>
        <form action="<%= request.getContextPath() %>/viewCart" method="post">
            <input type="text" name="cardNumber" placeholder="Enter 16-digit card number" required>
            <button type="submit">Place Order</button>
        </form>
        <a href="<%= request.getContextPath() %>/browse">Back to Menu</a>
    </div>
</body>
</html>