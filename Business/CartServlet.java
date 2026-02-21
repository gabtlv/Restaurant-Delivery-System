package Business;

import Helper.MenuItem;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewCart")
public class CartServlet extends HttpServlet {
    
    // Existing GET logic for viewing the cart
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cartItems");
        
        double grandTotal = 0.0;
        if (cart != null) {
            for (MenuItem item : cart) {
                grandTotal += item.getPrice();
            }
        }

        request.setAttribute("grandTotal", grandTotal);
        request.getRequestDispatcher("viewCart.jsp").forward(request, response);
    }

    // NEW POST logic for placing the order
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String cardNumber = request.getParameter("cardnumber");
        
        // Check if card is exactly 16 digits
        if (cardNumber != null && cardNumber.length() == 16) {
            // Logic to clear the cart since order is placed
            HttpSession session = request.getSession();
            session.removeAttribute("cartItems");
            
            // Forward to viewStatus.jsp
            request.getRequestDispatcher("viewStatus.jsp").forward(request, response);
        } else {
            // If validation fails, send back to checkout with an error (optional)
            response.sendRedirect(request.getContextPath() + "/placeOrder.jsp?error=invalidcard");
        }
    }
}
