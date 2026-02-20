package Business;

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

        // 2. Simple Validation Logic
        boolean success = (email != null && email.contains("@") && 
                           password != null && password.length() >= 4);

        // 3. Session Management
        HttpSession session = request.getSession();

        if (success) {
            // SUCCESS PATH
            // Store data in the SESSION (it persists across the redirect)
            session.setAttribute("loggedIn", true);
            session.setAttribute("customerEmail", email);

            // Redirect tells the browser to make a NEW request to browse.jsp
            // This prevents the "Refresh -> Resubmit Form" bug
            response.sendRedirect("browse.jsp");
        } else {
            // FAILURE PATH
            session.setAttribute("loggedIn", false);
            session.removeAttribute("customerEmail");

            // We use forward here so we can pass error attributes to the JSP
            request.setAttribute("loginSuccess", false);
            request.setAttribute("email", email);
            request.getRequestDispatcher("loginResult.jsp").forward(request, response);
        }
    }
}
