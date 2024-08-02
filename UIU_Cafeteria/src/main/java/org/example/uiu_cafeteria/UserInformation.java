package org.example.uiu_cafeteria;

import java.sql.*;

public class UserInformation {
    private String username;
    private String number;
    private String email;
    private String password;
    private String birthdate;
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/uiu_cafeteria";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "toor";

    public UserInformation() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public UserInformation(String username, String number, String email, String password, String birthdate) {
        this.username = username;
        this.number = number;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public boolean LoginUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user_info WHERE username = ? AND password = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();  // Returns true if there is a result
            }
        }
    }

    public boolean CreateAccount() throws ClassNotFoundException {
        String sql = "INSERT INTO user_info (username, mobile, email, password, birthdate) VALUES (?, ?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, number);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, birthdate);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if rows were affected

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetpass(String email, String newPassword) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "UPDATE user_info SET password = ? WHERE email = ?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newPassword);
        preparedStatement.setString(2, email);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
}