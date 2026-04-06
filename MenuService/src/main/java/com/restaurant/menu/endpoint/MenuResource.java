package com.restaurant.menu.endpoint;

import com.restaurant.menu.helper.MenuItem;
import com.restaurant.menu.persistence.MenuItem_CRUD;
import com.restaurant.menu.util.JWTUtil;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("menu")
public class MenuResource {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public String getAllMenuItems(@HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        List<MenuItem> items = MenuItem_CRUD.getMenuList();
        if (items.isEmpty()) {
            return "<menu><message>No items available</message></menu>";
        }

        StringBuilder xml = new StringBuilder("<menu>");
        for (MenuItem item : items) {
            xml.append("<item>")
               .append("<n>").append(item.getName()).append("</n>")
               .append("<description>").append(item.getDescription()).append("</description>")
               .append("<price>").append(item.getPrice()).append("</price>")
               .append("<imagePath>").append(item.getImagePath()).append("</imagePath>")
               .append("</item>");
        }
        xml.append("</menu>");
        return xml.toString();
    }

    @GET
    @Path("search/{query}")
    @Produces(MediaType.APPLICATION_XML)
    public String searchMenuItems(@PathParam("query") String query,
                                  @HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return "<error>Unauthorized — please log in</error>";
        }

        List<MenuItem> allItems = MenuItem_CRUD.getMenuList();
        StringBuilder xml = new StringBuilder("<menu>");
        for (MenuItem item : allItems) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                xml.append("<item>")
                   .append("<n>").append(item.getName()).append("</n>")
                   .append("<description>").append(item.getDescription()).append("</description>")
                   .append("<price>").append(item.getPrice()).append("</price>")
                   .append("<imagePath>").append(item.getImagePath()).append("</imagePath>")
                   .append("</item>");
            }
        }
        xml.append("</menu>");
        return xml.toString();
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