package com.smalaca.sda;

import com.mysql.jdbc.Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class ShopApp {
    static final String DB_URL =
            "jdbc:mysql://localhost/java_krk_13";
    static final String USER = "jkrk13";
    static final String PASS = "jkrk13";

    public static void main( String[] args ) {

        //register mysql driver
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
            // we should not continue
        }

        //establish connection
        Connection connection = null;

        try {
            connection =
                    DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //query execution
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        CallableStatement callableStatement = null;

        try {
            callableStatement = connection.prepareCall(
                    "{call get_product_name(?, ?)}");
            callableStatement.setInt(1, 13);
            callableStatement.registerOutParameter(2, Types.CHAR);
            callableStatement.execute();

            System.out.println(callableStatement.getString(2));
//            updateProducts(preparedStatement, connection);
//
//            statement = connection.createStatement();
//            insertProduct(statement);
//            findAllProducts(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //close connection
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProducts(PreparedStatement preparedStatement, Connection connection) {
        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(
                    "UPDATE PRODUCTS " +
                    "SET DESCRIPTION = ? WHERE PRODUCT_ID = ?"
            );

            preparedStatement.setString(1, "bla bla bla");
            preparedStatement.setInt(2, 1);

            int updated = preparedStatement.executeUpdate();

            connection.commit();
            System.out.println(updated + " updated products.");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private static void insertProduct(Statement statement) {
        try {
            int inserted = statement.executeUpdate(
                    "INSERT INTO PRODUCTS VALUES " +
                    "(1, '123456', 'first product', 'some description')"
            );

            System.out.println(inserted + " new products added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findAllProducts(Statement statement) {
        ResultSet resultSet = null;

        try {
            resultSet = statement.
                    executeQuery("SELECT product_id, name, description FROM PRODUCTS");

            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                System.out.println(
                        "Product with id: " + id +
                        " and name: " + name +
                        " and description: " + description + "."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
