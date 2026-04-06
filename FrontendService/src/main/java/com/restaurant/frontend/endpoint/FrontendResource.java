package com.restaurant.frontend.endpoint;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * FrontendResource — REST endpoint that acts as the orchestrator.
 *
 * Place this file in:
 *   FrontendService/src/main/java/com/restaurant/frontend/endpoint/
 *
 * Base URL: http://localhost:8080/FrontendService/webresources/frontend/...
 *
 * This class calls MenuService and CartService over HTTP.
 * LoginServlet.java already handles login — this handles everything else.
 */
@Path("frontend")
public class FrontendResource {

    // URLs of the two backend services
    private static final String MENU_BASE = "http://localhost:8080/MenuService/webresources";
    private static final String CART_BASE = "http://localhost:8080/CartService/webresources";

    // -----------------------------------------------------------------------
    // ENDPOINT 1 — GET /frontend/browse
    //
    // Called when the user wants to see the menu.
    // Calls MenuService GET /menu/all and returns the XML directly to the JSP.
    //
    // Example: http://localhost:8080/FrontendService/webresources/frontend/browse
    // -----------------------------------------------------------------------
    @GET
    @Path("browse")
    @Produces(MediaType.APPLICATION_XML)
    public String browseMenu() {
        return callGet(MENU_BASE + "/menu/all");
    }

    // -----------------------------------------------------------------------
    // ENDPOINT 2 — POST /frontend/addToCart
    //
    // Called when the user clicks "Add to Cart" on browse.jsp.
    // Form field: itemName
    // Forwards the request to CartService POST /cart/add?itemName=...
    //
    // Example: POST http://localhost:8080/FrontendService/webresources/frontend/addToCart
    //          with form body: itemName=Burger
    // -----------------------------------------------------------------------
    @POST
    @Path("addToCart")
    @Produces(MediaType.APPLICATION_XML)
    public String addToCart(@FormParam("itemName") String itemName) {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target(CART_BASE + "/cart/add")
                .queryParam("itemName", itemName)
                .request(MediaType.APPLICATION_XML)
                .post(Entity.text(""));
        String result = response.readEntity(String.class);
        client.close();
        return result;
    }

    // -----------------------------------------------------------------------
    // ENDPOINT 3 — GET /frontend/viewCart
    //
    // Called when the user clicks "View Cart".
    // Calls CartService GET /cart/view and returns the XML.
    //
    // Example: http://localhost:8080/FrontendService/webresources/frontend/viewCart
    // -----------------------------------------------------------------------
    @GET
    @Path("viewCart")
    @Produces(MediaType.APPLICATION_XML)
    public String viewCart() {
        return callGet(CART_BASE + "/cart/view");
    }

    // -----------------------------------------------------------------------
    // ENDPOINT 4 — POST /frontend/placeOrder
    //
    // Called when the user submits payment on the checkout page.
    // Form field: cardNumber
    // Forwards to CartService POST /cart/order?cardNumber=...
    //
    // Example: POST http://localhost:8080/FrontendService/webresources/frontend/placeOrder
    //          with form body: cardNumber=1234567890123456
    // -----------------------------------------------------------------------
    @POST
    @Path("placeOrder")
    @Produces(MediaType.APPLICATION_XML)
    public String placeOrder(@FormParam("cardNumber") String cardNumber) {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target(CART_BASE + "/cart/order")
                .queryParam("cardNumber", cardNumber)
                .request(MediaType.APPLICATION_XML)
                .post(Entity.text(""));
        String result = response.readEntity(String.class);
        client.close();
        return result;
    }

    // -----------------------------------------------------------------------
    // Private helper: simple GET call, returns raw XML string
    // -----------------------------------------------------------------------
    private String callGet(String url) {
        try {
            Client client = ClientBuilder.newClient();
            String xml = client.target(url)
                               .request(MediaType.APPLICATION_XML)
                               .get(String.class);
            client.close();
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            return "<error>" + e.getMessage() + "</error>";
        }
    }
}