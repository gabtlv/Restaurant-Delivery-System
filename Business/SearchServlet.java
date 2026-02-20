package Business;

import Helper.MenuItem;
import Persistence.MenuItem_CRUD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("queryText");
        List<MenuItem> allItems = MenuItem_CRUD.getMenuList(); // ← fix here
        List<MenuItem> filteredResults = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            for (MenuItem item : allItems) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredResults.add(item);
                }
            }
        } else {
            filteredResults = allItems;
        }

        request.setAttribute("menuResults", filteredResults);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }
}
