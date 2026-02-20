package Persistence;

import Helper.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItem_CRUD {

    private static final String URL = "jdbc:mysql://localhost:3306/RDS?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "student";

    private static Connection getCon() throws Exception {
        // Explicitly load the driver to ensure the Servlet container sees it
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Get ALL menu items
    public static List<MenuItem> getMenuList() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT itemName, description, price, imagePath FROM MenuItem";
        
        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                String name      = rs.getString("itemName");
                String desc      = rs.getString("description");
                double price     = rs.getDouble("price");
                String imagePath = rs.getString("imagePath");

                if (imagePath == null || imagePath.isEmpty()) {
                    imagePath = "images/default.png";
                }
                
                items.add(new MenuItem(name, desc, imagePath, price));
            }
        } catch (Exception e) {
            System.out.println("DEBUG DB ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    // Get one menu item by ID
    public static MenuItem getMenuItem(int menuID) {
        String sql = "SELECT itemName, description, price, imagePath FROM MenuItem WHERE MenuID = ?";
        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, menuID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name      = rs.getString("itemName");
                    String desc      = rs.getString("description");
                    double price     = rs.getDouble("price");
                    String imagePath = rs.getString("imagePath");
                    
                    if (imagePath == null) imagePath = "images/default.png";
                    return new MenuItem(name, desc, imagePath, price);
                }
            }
        } catch (Exception e) {
            System.out.println("DEBUG DB ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
