package Business;

import Helper.MenuItem;
import Persistence.MenuItem_CRUD; // 1. Import your CRUD class

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/browse")
public class BrowseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 2. Fetch data from DB instead of manually adding items
        List<MenuItem> items = MenuItem_CRUD.getMenuList();

        // 3. Keep your attributes so the JSP can read them
        getServletContext().setAttribute("menuItems", items);
        request.setAttribute("menuItems", items);

        // 4. Forward to the view
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }
}
