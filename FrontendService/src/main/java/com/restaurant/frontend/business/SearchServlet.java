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
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String MENU_SEARCH_URL = "http://" + System.getenv("menuService") + "/MenuService/webresources/menu/search/";
        String CART_COUNT_URL = "http://" + System.getenv("cartService") + "/CartService/webresources/cart/count";
        // Get JWT token from session
        String token = getTokenFromCookies(request);
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String query = request.getParameter("queryText");
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/browse");
            return;
        }

        List<String[]> menuItems = new ArrayList<>();
        String cartCount = "0";

        // Call MenuService search with JWT token
        try {
            Client client = ClientBuilder.newClient();
            String xml = client.target(MENU_SEARCH_URL + query)
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
                String desc  = getTag(el, "description");
                String price = getTag(el, "price");
                String img   = getTag(el, "imagePath");
                menuItems.add(new String[]{name, desc, price, img});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Call CartService count with JWT token
        try {
            Client client = ClientBuilder.newClient();
            String countXml = client.target(CART_COUNT_URL)
                                    .request(MediaType.APPLICATION_XML)
                                    .header("Authorization", "Bearer " + token)
                                    .get(String.class);
            client.close();

            DocumentBuilder db = DocumentBuilderFactory.newInstance()
                                                       .newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(countXml)));
            NodeList nodes = doc.getElementsByTagName("count");
            if (nodes.getLength() > 0) {
                cartCount = nodes.item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("menuItems", menuItems);
        request.setAttribute("cartCount", cartCount);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
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