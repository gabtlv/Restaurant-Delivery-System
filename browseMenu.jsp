<%@ page import="java.util.List" %>
<%@ page import="Helper.MenuItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Menu Browser</title>
    <style>
        body {
            font-family: Arial,
                sans-serif;
            background-color: #f9f9f9;
            padding-top: 50px;
            text-align: center;
            margin: 0;
        }

        .search-box {
            margin-bottom: 30px;
            display: inline-block;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        button {
            background-color: green;
            color: white;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            padding: 10px;
        }

        table {
            width: 100%;
            max-width: 1100px;
            margin: 0 auto;
            border-collapse: separate;
            border-spacing: 15px;
        }

        td {
            width: 20%;
            padding: 15px;
            vertical-align: top;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        img {
            width: 100%;
            max-width: 150px;
            height: 120px;
            object-fit: cover;
            border-radius: 8px;
        }

        .title {
            font-weight: bold;
            display: block;
            margin-top: 10px;
            font-size: 1.1em;
        }

        .desc {
            font-size: 0.9em;
            color: #666;
            display: block;
            margin-top: 5px;
        }

        .back-link {
            margin-top: 30px;
            display: inline-block;
            color: green;
        }

        h1 {
            margin: 0;
            margin-bottom: 10px;
        }

        h3 {
            margin: 0;
            margin-bottom: 40px
        }
    </style>
</head>

<body>

    <h1>Burger Buns</h1>
    <h3>Online Delivery Service</h3>

    <div class="search-box">
        <form action="search" method="get">
            <input type="text" name="queryText" placeholder="Enter food name..." required>
            <button type="submit">Search</button>
        </form>
    </div>

    <div class="cart-box">
        <% 
            List<String> cart = (List<String>) session.getAttribute("cartItems");
            int cartCount = (cart != null) ? cart.size() : 0; 
        %>
        <form action="viewCart.jsp" method="get">
            <button type="submit">Go to Cart (<%= cartCount %>)</button>
        </form>
    </div>

    <table>
        <tr>
            <% 
                List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
                if (menuItems != null && !menuItems.isEmpty()) {
                    int i = 0;
                    for (MenuItem item : menuItems) {
                        if (i > 0 && i % 5 == 0) { %>
                            </tr><tr>
                        <% } %>
                        <td>
                            <img src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
                            <span class="title"><%= item.getName() %></span>
                            <span class="desc"><%= item.getDescription() %></span>
                            <span class="price">$<%= item.getPrice() %></span>

                            <form action="addToCart" method="post" style="margin-top: 10px;">
                                <input type="hidden" name="itemName" value="<%= item.getName() %>">
                                <button type="submit">Add to Cart</button>
                            </form>
                        </td>
                        <% i++; 
                    } 
                } else { %>
                    <td colspan="5">No items found. Please try a different search.</td>
                <% } %>
        </tr>
    </table>

    <a href="login.html" class="back-link">Back to Login</a>

</body>

</html>
