package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class UserDao {

    public static User login(String userName, String password) throws Exception {

        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM users WHERE User_Name = '" + userName + "'";
            JDBC.makePreparedStatement(statement, conn);

            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            results.next();
            if (results.getString("Password").equals(password)) {
                int userID = results.getInt("User_ID");
                Date created = results.getDate("Create_date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdated = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");

                User newUser = new User(userID, userName, created, createdBy, lastUpdated, lastUpdatedBy);
                return newUser;
            }
        } catch (Exception e) {
            throw new Exception(Translator.getTranslation("invalid"));
        }
        throw new Exception(Translator.getTranslation("invalid"));
    }

    public static ObservableList<Customer> getAllCustomers() {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM customers";
            JDBC.makePreparedStatement(statement, conn);
            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while (results.next()) {
                /** load each customer into customers list **/
                String customerName = results.getString("Customer_Name");
                String customerAddress = results.getString("Address");
                String customerPostalCode = results.getString("Postal_Code");
                String customerPhone = results.getString("Phone");
                customers.add(new Customer(customerName, customerAddress, customerPostalCode, customerPhone));
            }
            /** return customer observable list **/
            return customers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FXCollections.observableArrayList();
    }

    public void createCustomer(String name, String address, String postalCode, String Phone, int division, int user) {
        try {
            Connection conn = JDBC.getConnection();
            Date currentTime = (Date) new java.util.Date();
            String statement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                               "Created_By, Last_Update, Last_Updated_By, Divison_ID) " +
                               "VALUES (" + name + "," + address + "," + postalCode + "," + Phone + "," + currentTime + "," + user + "," +
                               currentTime + "," + user + "," + division + ");";
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}