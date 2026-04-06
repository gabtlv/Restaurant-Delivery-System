package com.restaurant.menu.endpoint;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * ApplicationConfig — registers MenuResource as a REST endpoint.
 *
 * Place this file in:
 *   MenuService/src/main/java/com/restaurant/menu/endpoint/
 *
 * All REST endpoints in MenuService are reachable under:
 *   http://localhost:8080/MenuService/webresources/...
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.restaurant.menu.endpoint.MenuResource.class);
        return resources;
    }
}