package com.restaurant.frontend.business;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

@WebServlet("/viewCart")
public class CartServlet extends HttpServlet {

    // GET — display the cart
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String CART_VIEW_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/view";
        String CART_ORDER_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/order";
       // Get JWT token from cookie instead of session
        String token = getTokenFromCookies(request);
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        List<String[]> cartItems = new ArrayList<>();
        String grandTotal = "0.00";

        try {
            Client client = ClientBuilder.newClient();
            String xml = client.target(CART_VIEW_URL)
                               .request(MediaType.APPLICATION_XML)
                               .header("Authorization", "Bearer " + token)
                               .get(String.class);
            client.close();

            DocumentBuilder db = DocumentBuilderFactory.newInstance()
                                                       .newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));
            NodeList nodes = doc.getElementsByTagName("item");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element el = (Element) nodes.item(i);
                String name  = getTag(el, "n");
                String price = getTag(el, "price");
                cartItems.add(new String[]{name, price});
            }

            NodeList total = doc.getElementsByTagName("grandTotal");
            if (total.getLength() > 0) {
                double totalValue = Double.parseDouble(
                    total.item(0).getTextContent());
                grandTotal = String.format("%.2f", totalValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("grandTotal", grandTotal);
        request.getRequestDispatcher("viewCart.jsp").forward(request, response);
    }

    // POST — place the order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String CART_VIEW_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/view";
        String CART_ORDER_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/order";
        // Get JWT token from session
        String token = (String) request.getSession().getAttribute("jwtToken");
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String cardNumber = request.getParameter("cardNumber");
        String orderStatus = "";

        try {
            Client client = ClientBuilder.newClient();
            Response res = client.target(CART_ORDER_URL)
                                 .queryParam("cardNumber", cardNumber)
                                 .request(MediaType.APPLICATION_XML)
                                 .header("Authorization", "Bearer " + token)
                                 .post(Entity.text(""));
            orderStatus = res.readEntity(String.class);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            orderStatus = "<response><status>Error placing order</status></response>";
        }

        request.setAttribute("orderStatus", orderStatus);
        request.getRequestDispatcher("orderStatus.jsp").forward(request, response);
    }

    private String getTag(Element el, String tag) {
        NodeList nl = el.getElementsByTagName(tag);
        return (nl.getLength() > 0) ? nl.item(0).getTextContent() : "";
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