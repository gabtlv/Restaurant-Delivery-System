package com.mycompany.lab2exc2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean success = (email != null && email.contains("@")
                && password != null && password.length() >= 4);

        HttpSession session = request.getSession();
        session.setAttribute("loggedIn", success);

        if (success) {
            session.setAttribute("customerEmail", email);
        } else {
            session.removeAttribute("customerEmail");
        }

        request.setAttribute("loginSuccess", success);
        request.setAttribute("email", email);

        RequestDispatcher rd = request.getRequestDispatcher("loginResult.jsp");
        rd.forward(request, response);
    }
}
