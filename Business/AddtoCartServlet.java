package Business;

import Helper.MenuItem;
import Persistence.MenuItem_CRUD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addToCart")
public class AddtoCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the item name from the form
        String itemName = request.getParameter("itemName");
        
        if (itemName == null || itemName.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/browse");
            return;
        }

        // 2. Find the item. 
        // Optimization: It's better to fetch from the DB only if needed.
        MenuItem found = null;
        List<MenuItem> allItems = MenuItem_CRUD.getMenuList(); 
        
        for (MenuItem m : allItems) {
            if (m.getName().equalsIgnoreCase(itemName)) {
                found = m;
                break;
            }
        }

        if (found != null) {
            // 3. Get/Create the cart in the SESSION
            HttpSession session = request.getSession(true);
            List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cartItems");
            
            if (cart == null) {
                cart = new ArrayList<>();
            }

            // 4. Add the item and save back to session
            cart.add(found);
            session.setAttribute("cartItems", cart);
        }

        // 5. Redirect back to the browse SERVLET to refresh the page
        response.sendRedirect(request.getContextPath() + "/browse");
    }
}
