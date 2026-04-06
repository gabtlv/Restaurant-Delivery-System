package com.restaurant.cart.endpoint;

import com.restaurant.cart.helper.MenuItem;
import com.restaurant.cart.persistence.MenuItem_CRUD;
import com.restaurant.cart.util.JWTUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("cart")
public class CartResource {

    private static List<MenuItem> cartItems = new ArrayList<>();

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_XML)
    public String addToCart(@QueryParam("itemName") String itemName,
                            @HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        if (itemName == null || itemName.trim().isEmpty()) {
            return "<response><status>Error: itemName is required</status></response>";
        }

        List<MenuItem> allItems = MenuItem_CRUD.getMenuList();
        MenuItem found = null;
        for (MenuItem m : allItems) {
            if (m.getName().equalsIgnoreCase(itemName.trim())) {
                found = m;
                break;
            }
        }

        if (found == null) {
            return "<response><status>Error: Item not found — " + itemName + "</status></response>";
        }

        cartItems.add(found);
        return "<response><status>Item added to cart: " + found.getName() + "</status></response>";
    }

    @GET
    @Path("view")
    @Produces(MediaType.APPLICATION_XML)
    public String viewCart(@HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        if (cartItems.isEmpty()) {
            return "<cart><message>Cart is empty</message></cart>";
        }

        double grandTotal = 0.0;
        StringBuilder xml = new StringBuilder("<cart>");
        for (MenuItem item : cartItems) {
            grandTotal += item.getPrice();
            xml.append("<item>")
               .append("<n>").append(item.getName()).append("</n>")
               .append("<price>").append(item.getPrice()).append("</price>")
               .append("</item>");
        }
        xml.append("<grandTotal>").append(String.format("%.2f", grandTotal)).append("</grandTotal>");
        xml.append("</cart>");
        return xml.toString();
    }

    @POST
    @Path("order")
    @Produces(MediaType.APPLICATION_XML)
    public String placeOrder(@QueryParam("cardNumber") String cardNumber,
                             @HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        if (cartItems.isEmpty()) {
            return "<response><status>Error: Cart is empty</status></response>";
        }

        if (cardNumber != null && cardNumber.length() == 16) {
            cartItems.clear();
            return "<response><status>Order placed successfully</status></response>";
        }

        return "<response><status>Invalid card number</status></response>";
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_XML)
    public String getCartCount(@HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        return "<count>" + cartItems.size() + "</count>";
    }

    // Helper — checks Authorization header contains a valid JWT
    private boolean isAuthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return JWTUtil.verifyToken(token) != null;
    }
}