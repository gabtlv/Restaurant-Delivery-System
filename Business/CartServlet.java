import Helper.MenuItem;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewCart")
public class CartServlet extends HttpServlet {
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

        // Store the calculated total in the request to pass it to the JSP
        request.setAttribute("grandTotal", grandTotal);
        
        // Forward the request to the JSP file
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
