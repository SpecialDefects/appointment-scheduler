package Model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;

import static javafx.collections.FXCollections.*;

public class UserDao {

    static User loggedInUser;

    public static void login(String userName, String password) throws Exception {

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

                loggedInUser = new User(userID, userName, created, createdBy, lastUpdated, lastUpdatedBy);
            } else {
                throw new Exception(Translator.getTranslation("invalid"));
            }
        } catch (Exception e) {
            throw new Exception(Translator.getTranslation("invalid"));
        }
    }

    public static ObservableList<Customer> getAllCustomers() {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM customers";
            JDBC.makePreparedStatement(statement, conn);
            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            ObservableList<Customer> customers = observableArrayList();
            while (results.next()) {
                /** load each customer into customers list **/
                int customerId = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                String customerAddress = results.getString("Address");
                String customerPostalCode = results.getString("Postal_Code");
                String customerPhone = results.getString("Phone");
                int customerDivision = results.getInt("Division_ID");
                customers.add(new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhone, customerDivision));
            }
            /** return customer observable list **/
            return customers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableArrayList();
    }

    public static void createCustomer(String name, String address, String postalCode, String Phone, int division, int user) {
        try {
            Connection conn = JDBC.getConnection();
            Date currentTime = new java.sql.Date(new java.util.Date().getTime());
            String statement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES ('" + name + "', '" + address + "', '" + postalCode + "', '" + Phone + "', '" + currentTime + "'," + user + ", '" +
                    currentTime + "'," + user + "," + division + ");";
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ObservableList<Division> getAllDivisions() throws SQLException {
        Connection conn = JDBC.getConnection();
        String statement = "SELECT * FROM first_level_divisions";
        JDBC.makePreparedStatement(statement, conn);
        ResultSet results = JDBC.getPreparedStatement().executeQuery();
        ObservableList<Division> divisions = observableArrayList();
        while (results.next()) {
            /** load each division into divisions list **/
            int id = results.getInt("Division_ID");
            String name = results.getString("Division");
            int countryId = results.getInt("Country_ID");
            divisions.add(new Division(id, name, countryId));
        }
        return divisions;
    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        Connection conn = JDBC.getConnection();
        String statement = "SELECT * FROM countries";
        JDBC.makePreparedStatement(statement, conn);
        ResultSet results = JDBC.getPreparedStatement().executeQuery();
        ObservableList<Country> countries = observableArrayList();
        while (results.next()) {
            /** load each division into divisions list **/
            int id = results.getInt("Country_ID");
            String name = results.getString("Country");
            countries.add(new Country(id, name));
        }
        return countries;
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        Connection conn = JDBC.getConnection();
        /** check if customer has scheduled appointments **/
        ObservableList<Appointment> appointments = getAllCustomerAppointments(customer);
        /** delete appointments for customer **/
        if (!appointments.isEmpty()) {
            String statement = "DELETE FROM appointments WHERE Customer_ID = " + customer.getId();
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        }

        /** delete customer **/
        String statement = "DELETE FROM customers WHERE Customer_ID = " + customer.getId();
        JDBC.makePreparedStatement(statement, conn);
        JDBC.getPreparedStatement().executeUpdate();
    }

    public static ObservableList<Appointment> getAllCustomerAppointments(Customer customer) {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM appointments WHERE Customer_ID = " + customer.getId();
            JDBC.makePreparedStatement(statement, conn);
            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            ObservableList<Appointment> appointments = observableArrayList();
            while (results.next()) {
                /** load each customer into customers list **/
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = results.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = results.getTimestamp("End").toLocalDateTime();
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");
                int contactId = results.getInt("Contact_ID");
                appointments.add(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
            }
            /** return customer observable list **/
            return appointments;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableArrayList();
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = observableArrayList();
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM appointments";
            JDBC.makePreparedStatement(statement, conn);
            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            while (results.next()) {
                /** load each division into divisions list **/
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = results.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = results.getTimestamp("End").toLocalDateTime();
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");
                int contactId = results.getInt("Contact_ID");
                appointments.add(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
            }
            return appointments;
        } catch (Exception e) {
            PopUpBox.displayError("invalid");
        }
        return appointments;
    }

    static public void updateCustomer(int id, String name, String address, String postal, int division) throws SQLException {
        Connection conn = JDBC.getConnection();
        Timestamp currentTime = Timestamp.from(Instant.now());
        String statement = "UPDATE customers " +
                "SET Customer_Name='" + name + "', " +
                "Address='" + address + "', " +
                "Postal_Code='" + postal + "', " +
                "Division_ID=" + division + ", " +
                "Last_Update='" + currentTime + "', " +
                "Last_Updated_By=" + loggedInUser.getID() + " " +
                "WHERE Customer_ID=" + id + ";";
        System.out.println(statement);
        JDBC.makePreparedStatement(statement, conn);
        JDBC.getPreparedStatement().executeUpdate();
    }

    static public ObservableList<Contact> getAllContacts() {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM contacts;";
            JDBC.makePreparedStatement(statement, conn);
            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            ObservableList<Contact> contacts = observableArrayList();
            while (results.next()) {
                /** load each customer into customers list **/
                int id = results.getInt("Contact_ID");
                String name = results.getString("Contact_Name");
                String email = results.getString("Email");
                contacts.add(new Contact(id, name, email));
            }
            /** return customer observable list **/
            return contacts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableArrayList();
    }

    public static void createAppointment(Appointment appointment) {
        try {
            Connection conn = JDBC.getConnection();
            Timestamp currentTime = Timestamp.from(Instant.now());
            String statement = "INSERT INTO appointments (Title, Description, Location, Type, Start," +
                    "End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES ('" + appointment.getTitle() + "', '" + appointment.getDescription() + "', '" + appointment.getLocation() +
                    "', '" + appointment.getType() + "', '" + appointment.getStart() + "', '" + appointment.getEnd() + "', '" +
                    currentTime + "', " + loggedInUser.getID() + ", '" + currentTime + "', " + loggedInUser.getID() + ", " + appointment.getCustomerId() +
                    ", " + appointment.getUserId() + ", " + appointment.getContactId() + ");";
            System.out.println(statement);
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment) {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getId();
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        } catch (Exception e) {
            PopUpBox.displayError("invalid");
        }
    }

    static public User getLoggedInUser() {
        return loggedInUser;
    }
}