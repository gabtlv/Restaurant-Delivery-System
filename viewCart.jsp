<%@ page import="java.util.List" %>
<%@ page import="Helper.MenuItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Cart</title>
    <style>
        /* CSS remains the same as your original code */
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; text-align: center; padding-top: 50px; }
        .cart-container { display: inline-block; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 80%; max-width: 500px; }
        .item-row { padding: 10px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; }
        .back-link { margin-top: 30px; display: inline-block; color: green; }
        .empty-msg { color: #888; font-style: italic; margin-bottom: 15px; }
        button { background-color: green; color: white; border: none; border-radius: 25px; cursor: pointer; padding: 10px 20px; font-size: 1em; }
        h1 { margin: 0; margin-bottom: 10px;}
        h3 { margin: 0; margin-bottom: 40px;}
    </style>
</head>
<body>

    <h1>Burger Buns</h1>
    <h3>Online Delivery Service</h3>

    <div class="cart-container">
        <h1>Your Shopping Cart</h1>
        
        <% 
            List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cartItems");
            Double grandTotal = (Double) request.getAttribute("grandTotal");

            if (cart != null && !cart.isEmpty()) {
                for (MenuItem item : cart) {
        %>
                <div class="item-row">
                    <span><%= item.getName() %></span>
                    <span>$<%= String.format("%.2f", item.getPrice()) %></span>
                </div>
        <% 
                }
        %>
            <div class="total" style="margin-top: 20px; font-weight: bold; color: green; text-align: right;">
                Total: $<%= String.format("%.2f", grandTotal) %>
            </div>
            
            <form action="placeOrder.jsp" method="get" style="margin-top: 20px;">
                <button type="submit">Place Order</button>
            </form>
        <% 
            } else { 
        %>
            <p class="empty-msg">Your cart is currently empty.</p>
        <% 
            }
        %>

        <a href="browse" class="back-link">Back to Menu</a>
    </div>

</body>
</html>
