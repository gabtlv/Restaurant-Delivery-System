package com.mycompany.lab2exc2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class searchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String query = request.getParameter("queryText");

        List<MenuItem> allItems = (List<MenuItem>) getServletContext().getAttribute("menuItems");
        List<MenuItem> filteredResults = new ArrayList<>();

        if (query != null && allItems != null) {
            for (MenuItem item : allItems) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredResults.add(item);
                }
            }
        }

        request.setAttribute("menuResults", filteredResults);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }
}