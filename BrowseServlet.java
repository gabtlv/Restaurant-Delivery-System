package com.mycompany.lab2exc2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BrowseServlet", urlPatterns = {"/browse"})
public class BrowseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the search input from browse.html
        String query = request.getParameter("queryText");
        List<MenuItem> results = new ArrayList<>();

        // 2. The master list of MenuItem objects
        List<MenuItem> fullMenu = new ArrayList<>();
        fullMenu.add(new MenuItem("Leilo Burger", "Juicy beef with secret sauce.", "images/burger1.jpg"));
        fullMenu.add(new MenuItem("Gab Burger", "Double cheese delight.", "images/burger2.jpg"));
        fullMenu.add(new MenuItem("Faezah Fries", "Crispy golden potato fries.", "images/fries.jpg"));
        fullMenu.add(new MenuItem("Jaundel Juice", "Freshly squeezed fruit.", "images/juice.jpg"));
        fullMenu.add(new MenuItem("Obehi Onion Rings", "Crunchy battered rings.", "images/rings.jpg"));
        fullMenu.add(new MenuItem("Tuff Truffle", "Luxury mushroom pasta.", "images/truffle.jpg"));

        // 3. Comparison Logic
        if (query != null && !query.trim().isEmpty()) {
            for (MenuItem item : fullMenu) {
                // Exact character string comparison (case-insensitive)
                if (item.getName().equalsIgnoreCase(query.trim())) {
                    results.add(item);
                }
            }
        }

        // 4. Send the result list to the JSP
        request.setAttribute("menuResults", results);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }
}