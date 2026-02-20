package com.mycompany.lab2exc2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/browse")
public class BrowseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Leilo's Burger", "Juicy beef patty with cheese.", "images/leiloburger.png", 5.0));
        items.add(new MenuItem("Gab's Burger", "Juicier beef with cheesier cheese.", "images/gabburger.png", 5.0));
        items.add(new MenuItem("French Fries", "Basket of golden fries.", "images/fries.png", 3.0));
        items.add(new MenuItem("Fountain Drink", "Soda with ice.", "images/drink.png", 2.0));
        items.add(new MenuItem("Ice Cream Cone", "Sweet and creamy soft serve on a waffle cone.", "images/icecream.png", 3.0));

        getServletContext().setAttribute("menuItems", items);
        request.setAttribute("menuItems", items);
        

        request.getRequestDispatcher("browseMenu.jsp").forward(request, response);
    }
}
