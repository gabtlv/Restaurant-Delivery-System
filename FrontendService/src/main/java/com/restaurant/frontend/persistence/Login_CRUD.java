package com.restaurant.frontend.persistence;

import java.sql.*;

public class Login_CRUD {

    private static final String USER = "root";
    private static final String PASS = "student";

    private static Connection getCon() throws Exception {
        String URL = "jdbc:mysql://" + System.getenv("DB_URL") + "/Users_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Validate login credentials
    public static boolean validateLogin(String email, String password) {

        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        boolean isValid = false;

        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DEBUG LOGIN ERROR: " + e.getMessage());
        }
        
        return isValid;
    }
}   