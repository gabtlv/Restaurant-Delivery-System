package com.restaurant.frontend.business;

import com.restaurant.frontend.persistence.Login_CRUD;
import com.restaurant.frontend.util.JWTUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Extract parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validate using DB (instead of simple checks)
        boolean success = Login_CRUD.validateLogin(email, password);

        // 3. Session Management
        HttpSession session = request.getSession();

        if (success) {
    String token = JWTUtil.createToken(email);

    // Store token in a cookie instead of session
    javax.servlet.http.Cookie jwtCookie = new javax.servlet.http.Cookie("jwtToken", token);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setMaxAge(3600); // 1 hour
    jwtCookie.setPath("/FrontendService");
    response.addCookie(jwtCookie);

    session.setAttribute("loggedIn", true);
    session.setAttribute("customerEmail", email);

    response.sendRedirect(request.getContextPath() + "/browse");
}
        else {
            // FAILURE PATH
            session.setAttribute("loggedIn", false);
            session.removeAttribute("customerEmail");

            request.setAttribute("loginSuccess", false);
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", "Invalid email or password.");

            request.getRequestDispatcher("loginResult.jsp").forward(request, response);
        }
    }
}