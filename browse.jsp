<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.lab2exc2.MenuItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 50px; text-align: center; margin: 0; }
        table { width: 100%; max-width: 1100px; margin: 0 auto; border-collapse: separate; border-spacing: 15px; }
        td { width: 20%; padding: 15px; vertical-align: top; background-color: white; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        img { width: 100%; max-width: 150px; height: 120px; object-fit: cover; border-radius: 8px; }
        .title { font-weight: bold; display: block; margin-top: 10px; font-size: 1.1em; }
        .desc { font-size: 0.9em; color: #666; display: block; margin-top: 5px; }
        .price { font-weight: bold; color: #27ae60; display: block; margin-top: 5px; }
        button { background-color: green; color: white; border: none; border-radius: 25px; cursor: pointer; padding: 10px; }
        
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

    <table>
        <tr>
            <% 
                List<MenuItem> results = (List<MenuItem>) request.getAttribute("menuResults");
                if (results != null && !results.isEmpty()) {
                    int i = 0;
                    for (MenuItem item : results) {
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

    <a href="browse" class="back-link">Back to Menu</a>

</body>
</html>