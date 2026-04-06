package com.restaurant.frontend.business;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@WebServlet("/addToCart")
public class AddtoCartServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String CART_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/add";
        // Get JWT token from session
        String token = getTokenFromCookies(request);
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String itemName = request.getParameter("itemName");

        try {
            Client client = ClientBuilder.newClient();
            client.target(CART_URL)
                  .queryParam("itemName", itemName)
                  .request(MediaType.APPLICATION_XML)
                  .header("Authorization", "Bearer " + token)
                  .post(Entity.text(""));
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/browse");
    }
    private String getTokenFromCookies(HttpServletRequest request) {
    javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie c : cookies) {
                if (c.getName().equals("jwtToken")) {
                    return c.getValue();
                }
            }
        }
    return null;
    }
}
