package com.restaurant.order.persistence;

import com.restaurant.order.helper.Order;
import java.sql.*;

public class Orders_CRUD {

    private static final String USER = "root";
    private static final String PASS = "student";

    private static Connection getCon() throws Exception {
        String URL = "jdbc:mysql://" + System.getenv("DB_URL") + "/Orders_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static int insertOrder(Order order) {
        // CHANGE table/columns to match your DB
        String sql = "INSERT INTO Orders (customerName, total, status) VALUES (?, ?, ?)";

        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getCustomerName());
            ps.setDouble(2, order.getTotal());
            ps.setString(3, order.getStatus());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
