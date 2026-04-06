package com.restaurant.order.persistence;

import com.restaurant.order.helper.OrderItem;
import java.sql.*;

public class OrderItem_CRUD {

    private static final String USER = "root";
    private static final String PASS = "student";

    private static Connection getCon() throws Exception {
        String URL = "jdbc:mysql://" + System.getenv("DB_URL") + "/Orders_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Insert one order item linked to an order
    public static void insertOrderItem(OrderItem item, int orderID) {
        String sql = "INSERT INTO OrderItem (OrderID, MenuID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.setInt(2, item.getMenuID());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getItemPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}