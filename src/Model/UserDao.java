package Model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.*;

/** Data Access Object for User **/
public class UserDao {

    /** logged in user **/
    static User loggedInUser;
    /** users time zone id**/
    static ZoneId userZone;

    /**
     * performs username and password authentication by checking database for matches
     * @param userName username
     * @param password password
     * @param zone users zone id
     * @throws Exception
     */
    public static void login(String userName, String password, ZoneId zone) throws Exception {

        try {
            Connection conn = JDBC.getConnection();
            /** look for username in users **/
            String statement = "SELECT * FROM users WHERE User_Name = '" + userName + "'";
            JDBC.makePreparedStatement(statement, conn);

            ResultSet results = JDBC.getPreparedStatement().executeQuery();
            /** check results **/
            results.next();
            /** if username is found and username password matches,
             * get user information **/
            if (results.getString("Password").equals(password)) {
                int userID = results.getInt("User_ID");
                Date created = results.getDate("Create_date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdated = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                /** set userZone **/
                userZone = zone;
                /** set user logged in **/
                loggedInUser = new User(userID, userName, created, createdBy, lastUpdated, lastUpdatedBy);
                /** check if user has any upcoming appointments **/
                checkForAppointment(loggedInUser);
            } else {
                throw new Exception(Translator.getTranslation("invalid"));
            }
        } catch (Exception e) {
            throw new Exception(Translator.getTranslation("invalid"));
        }
    }

    /**
     * checks if user has any appointments within the next 15 minutes of their local time
     * @param user user to check appointments for
     * @return true if user has an upcoming appointment, else false
     */
    public static boolean checkForAppointment(User user) {
        ObservableList<Appointment> userAppointments = UserDao.getAllUserAppointments(user);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusMinutes(15);
        for (Appointment appointment : userAppointments) {
            LocalDateTime appointmentTime = LocalDateTime.parse(appointment.getStart());
            if (appointmentTime.getDayOfYear() == currentTime.getDayOfYear() && appointmentTime.getHour() >= currentTime.getHour()) {
                if (appointmentTime.isBefore(futureTime) || appointmentTime.isEqual(futureTime)) {
                    PopUpBox.displayConfirmation("                         UPCOMING APPOINTMENT\n " +
                            "Appointment ID: " + appointment.getId() + "     Date: " + appointmentTime.getYear() +
                            "/" + appointmentTime.getMonthValue() + "/" + appointmentTime.getDayOfMonth() + "     Time: " + appointmentTime.getHour() + ":" + (appointmentTime.getMinute() == 30 ? "30" : "00"));
                    return true;
                }
            };
        }
        PopUpBox.displayConfirmation("No Upcoming Appointments");
        return false;
    }

    /**
     * @return users time zone id
     */
    public static ZoneId getZone() {
        return userZone;
    }

    /**
     * @return observable list of all customers
     */
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

    /**
     * inserts customer into database
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param Phone customer phone
     * @param division customer division
     * @param user user id of user that created the customer
     */
    public static void createCustomer(String name, String address, String postalCode, String Phone, int division, int user) {
        try {
            Connection conn = JDBC.getConnection();
            Timestamp currentTime = Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant());
            String statement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES ('" + name + "', '" + address + "', '" + postalCode + "', '" + Phone + "', '" + currentTime + "'," + user + ", '" +
                    currentTime + "'," + user + "," + division + ");";
            System.out.println(statement);
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @return Observable List of all Divisions
     * @throws SQLException
     */
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

    /**
     * @return observable list of all countries
     * @throws SQLException
     */
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

    /**
     * removes customer from database
     * @param customer customer to remove from database
     * @throws SQLException
     */
    public static boolean deleteCustomer(Customer customer) throws SQLException {
        try {
            Connection conn = JDBC.getConnection();
            /** check if customer has scheduled appointments **/
            ObservableList<Appointment> appointments = getAllCustomerAppointments(customer.getId());
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * retrieves all of a given customers appointments
     * @param cid customer id whose appointments to retrieve
     * @return
     */
    public static ObservableList<Appointment> getAllCustomerAppointments(int cid) {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM appointments WHERE Customer_ID = " + cid;
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = ZonedDateTime.of(LocalDateTime.parse(results.getString("Start"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
                LocalDateTime end = ZonedDateTime.of(LocalDateTime.parse(results.getString("End"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
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

    /**
     * @return observable list of all appointments
     */
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = ZonedDateTime.of(LocalDateTime.parse(results.getString("Start"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
                LocalDateTime end = ZonedDateTime.of(LocalDateTime.parse(results.getString("End"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
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

    /**
     * updates a given customer
     * @param id customer id to update
     * @param name customer name to update
     * @param address customer address to update
     * @param postal customer postal code to update
     * @param division customer division to update
     * @throws SQLException
     */
    static public void updateCustomer(int id, String name, String address, String postal, int division) throws SQLException {
        Connection conn = JDBC.getConnection();
        Timestamp currentTime = Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant());
        String statement = "UPDATE customers " +
                "SET Customer_Name='" + name + "', " +
                "Address='" + address + "', " +
                "Postal_Code='" + postal + "', " +
                "Division_ID=" + division + ", " +
                "Last_Update='" + currentTime + "', " +
                "Last_Updated_By=" + loggedInUser.getId() + " " +
                "WHERE Customer_ID=" + id + ";";
        System.out.println(statement);
        JDBC.makePreparedStatement(statement, conn);
        JDBC.getPreparedStatement().executeUpdate();
    }

    /**
     * retrieves all contacts from database
     * @return observable list of all contacts
     */
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

    /**
     * inserts a new appointment into database
     * @param appointment appointment to insert into database
     */
    public static void createAppointment(Appointment appointment) {
        try {
            Connection conn = JDBC.getConnection();
            Timestamp currentTime = Timestamp.from(Instant.now().atZone(ZoneId.of("UTC")).toInstant());
            LocalDateTime startDateUtc = ZonedDateTime.of(LocalDateTime.parse(appointment.getStart()), userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime endDateUtc = ZonedDateTime.of(LocalDateTime.parse(appointment.getEnd()), userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            String statement = "INSERT INTO appointments (Title, Description, Location, Type, Start," +
                    "End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES ('" + appointment.getTitle() + "', '" + appointment.getDescription() + "', '" + appointment.getLocation() +
                    "', '" + appointment.getType() + "', '" + startDateUtc + "', '" + endDateUtc + "', '" +
                    currentTime + "', " + loggedInUser.getId() + ", '" + currentTime + "', " + loggedInUser.getId() + ", " + appointment.getCustomerId() +
                    ", " + appointment.getUserId() + ", " + appointment.getContactId() + ");";
            System.out.println(statement);
            JDBC.makePreparedStatement(statement, conn);
            JDBC.getPreparedStatement().executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * delete a given appointment from database
     * @param appointment appointment to delete from database
     */
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

    /**
     * @return user that is currently logged in
     */
    static public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * retrieve all appointments for a given user from database
     * @param user user to retrieve appointments from database
     * @return observable list of appointments
     */
    public static ObservableList<Appointment> getAllUserAppointments(User user) {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM appointments WHERE User_ID = " + user.getId();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = ZonedDateTime.of(LocalDateTime.parse(results.getString("Start"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
                LocalDateTime end = ZonedDateTime.of(LocalDateTime.parse(results.getString("End"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
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

    /**
     * updates an appointment within the database
     * @param appointment appointment to update
     * @throws SQLException
     */
    public static void updateAppointment(Appointment appointment) throws SQLException {
        Connection conn = JDBC.getConnection();
        Timestamp currentTime = Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant());
        LocalDateTime startDateUtc = ZonedDateTime.of(LocalDateTime.parse(appointment.getStart()), userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endDateUtc = ZonedDateTime.of(LocalDateTime.parse(appointment.getEnd()), userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String statement = "UPDATE appointments " +
                "SET Title='" + appointment.getTitle() + "', " +
                "Description='" + appointment.getDescription() + "', " +
                "Location='" + appointment.getLocation() + "', " +
                "Type='" + appointment.getType() + "', " +
                "Start='" + startDateUtc + "', " +
                "End='" + endDateUtc + "', " +
                "Last_Update='" + currentTime + "', " +
                "Last_Updated_By=" + loggedInUser.getId() + ", " +
                "Customer_ID=" + appointment.getCustomerId() + ", " +
                "User_ID=" + appointment.getUserId() + ", " +
                "Contact_ID=" + appointment.getContactId() + " " +
                "WHERE Appointment_ID=" + appointment.getId() + ";";
        System.out.println(statement);
        JDBC.makePreparedStatement(statement, conn);
        JDBC.getPreparedStatement().executeUpdate();
    }

    /**
     * returns all appointments for a given contact
     * @return observable list of appointments for contact
     */
    public static ObservableList<Appointment> getAllContactAppointments(Contact contact) {
        try {
            Connection conn = JDBC.getConnection();
            String statement = "SELECT * FROM appointments WHERE Contact_ID = " + contact.getId();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = ZonedDateTime.of(LocalDateTime.parse(results.getString("Start"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
                LocalDateTime end = ZonedDateTime.of(LocalDateTime.parse(results.getString("End"), formatter), ZoneId.of("UTC")).withZoneSameInstant(userZone).toLocalDateTime();
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
}