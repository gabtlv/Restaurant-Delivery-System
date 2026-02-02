package com.mycompany.lab2exc2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "BrowseServlet", urlPatterns = {"/browse"})
public class BrowseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Login check (optional, keep if required in your lab) ---
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && Boolean.TRUE.equals(session.getAttribute("loggedIn")));

        if (!loggedIn) {
            request.setAttribute("error", "Please log in before browsing the menu.");
            RequestDispatcher rd = request.getRequestDispatcher("login.html");
            rd.forward(request, response);
            return;
        }

        // --- Get both inputs ---
        String queryText = request.getParameter("queryText");
        String querySelect = request.getParameter("querySelect");

        // Decide which query to use (text has priority)
        String query = "";
        boolean usedDropdown = false;

        if (queryText != null && !queryText.trim().isEmpty()) {
            query = queryText.trim();
        } else if (querySelect != null && !querySelect.trim().isEmpty()) {
            query = querySelect.trim();
            usedDropdown = true;
        }

        // --- Menu items ---
        List<String> menuItems = new ArrayList<>();
        menuItems.add("Leilo Burger");
        menuItems.add("Gab Burger");
        menuItems.add("Faezah Fries");
        menuItems.add("Jaundel Juice");
        menuItems.add("Obehi Onion Rings");
        menuItems.add("Tuff Truffle");

        // --- Filter results ---
        List<String> filtered = new ArrayList<>();
        for (String item : menuItems) {
            if (query.isEmpty()) {
                // nothing entered/selected -> show all
                filtered.add(item);
            } else if (usedDropdown) {
                // dropdown selection -> exact match
                if (item.equalsIgnoreCase(query)) {
                    filtered.add(item);
                }
            } else {
                // text search -> partial match
                if (item.toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(item);
                }
            }
        }

        // --- Send to JSP ---
        request.setAttribute("query", query);
        request.setAttribute("menuItems", filtered);

        RequestDispatcher rd = request.getRequestDispatcher("browse.jsp");
        rd.forward(request, response);
    }
}
