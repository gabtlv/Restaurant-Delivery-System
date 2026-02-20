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

        String itemName = request.getParameter("itemName");
        if (itemName == null || itemName.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/browse");
            return;
        }

        // Find the MenuItem object by name
        MenuItem found = null;
        List<MenuItem> allItems = MenuItem_CRUD.getMenuList(); // or use items from context
        for (MenuItem m : allItems) {
            if (m.getName().equalsIgnoreCase(itemName)) {
                found = m;
                break;
            }
        }

        if (found == null) {
            response.sendRedirect(request.getContextPath() + "/browse");
            return;
        }

        HttpSession session = request.getSession();
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cartItems");
        if (cart == null) cart = new ArrayList<>();

        cart.add(found);
        session.setAttribute("cartItems", cart);

        response.sendRedirect(request.getContextPath() + "/browse");
    }
}
