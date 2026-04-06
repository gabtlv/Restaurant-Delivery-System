package com.restaurant.frontend.endpoint;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * ApplicationConfig — registers FrontendResource as a REST endpoint.
 *
 * Place this file in:
 *   FrontendService/src/main/java/com/restaurant/frontend/endpoint/
 *
 * All REST endpoints in FrontendService are reachable under:
 *   http://localhost:8080/FrontendService/webresources/...
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.restaurant.frontend.endpoint.FrontendResource.class);
        return resources;
    }
}