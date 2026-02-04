package com.mycompany.lab2exc2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addToCart")
public class AddtoCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String itemName = request.getParameter("itemName");
        HttpSession session = request.getSession();

        List<MenuItem> cartItems = (List<MenuItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        List<MenuItem> menu = (List<MenuItem>) getServletContext().getAttribute("menuItems");

        if (itemName != null && menu != null) {
            for (MenuItem item : menu) {
                if (item.getName().equals(itemName)) {
                    cartItems.add(item);
                    break;
                }
            }
        }

        session.setAttribute("cartItems", cartItems);
        response.sendRedirect("browse");
    }
}