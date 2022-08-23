package Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;

/**
 * controls MainMenuView
 */
public class MainMenuController implements Initializable, LoadableController {
    /** customer table **/
    public TableView customerTable;
    /** appointment table **/
    public TableView appointmentTable;

    /** name column for customer table **/
    public TableColumn customerName;
    /** address column for customer table **/
    public TableColumn customerAddress;
    /** postal column for customer table **/
    public TableColumn customerPostalCode;
    /** phone column for customer table **/
    public TableColumn customerPhone;

    /** add customer button **/
    public Button addCustomerButton;
    /** modify customer button **/
    public Button modifyCustomerButton;
    /** delete customer button **/
    public Button deleteCustomerButton;

    /** customer table tab **/
    public Tab customersTabLabel;
    /** appointment table tab **/
    public Tab appointmentsTabLabel;

    /** create appointment button **/
    public Button createAppointmentButton;
    /** modify appointment button **/
    public Button modifyAppointmentButton;
    /** delete appointment button **/
    public Button deleteAppointmentButton;

    /** ID column for appointment table **/
    public TableColumn appointmentID;
    /** title column for appointment table **/
    public TableColumn appointmentTitle;
    /** description column for appointment table **/
    public TableColumn appointmentDescription;
    /** location column for appointment table **/
    public TableColumn appointmentLocation;
    /** contact column for appointment table **/
    public TableColumn appointmentContact;
    /** type column for appointment table **/
    public TableColumn appointmentType;
    /** start column for appointment table **/
    public TableColumn appointmentStart;
    /** end column for appointment table **/
    public TableColumn appointmentEnd;
    /** customer column for appointment table **/
    public TableColumn appointmentCustomer;
    /** user column for appoint table **/
    public TableColumn appointmentUser;

    /** all appointments radio button **/
    public RadioButton allAppointments;
    /** month radio button **/
    public RadioButton monthAppointments;
    /** week radio button **/
    public RadioButton weekAppointments;

    /** user ID label **/
    public Label userID;
    /** filter by label **/
    public Label filterByLabel;

    /** main tab pane **/
    public TabPane mainTabPane;

    /**
     * initialize MainMenu view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** populate customers **/
        customerTable.setItems(UserDao.getAllCustomers());
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        /** populate appointments **/
        appointmentTable.setItems(UserDao.getAllAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUser.setCellValueFactory(new PropertyValueFactory<>("userId"));

        /** populate column names based on locale **/
        customerName.setText(Translator.getTranslation("name"));
        customerAddress.setText(Translator.getTranslation("address"));
        customerPostalCode.setText(Translator.getTranslation("postalcode"));
        customerPhone.setText(Translator.getTranslation("phone"));
        appointmentID.setText(Translator.getTranslation("id"));
        appointmentTitle.setText(Translator.getTranslation("title"));
        appointmentDescription.setText(Translator.getTranslation("description"));
        appointmentLocation.setText(Translator.getTranslation("location"));
        appointmentContact.setText(Translator.getTranslation("contact"));
        appointmentType.setText(Translator.getTranslation("type"));
        appointmentStart.setText(Translator.getTranslation("start"));
        appointmentEnd.setText(Translator.getTranslation("end"));
        appointmentCustomer.setText(Translator.getTranslation("customer"));
        appointmentUser.setText(Translator.getTranslation("user"));

        /** populate button text based on locale **/
        addCustomerButton.setText(Translator.getTranslation("create"));
        modifyCustomerButton.setText(Translator.getTranslation("modify"));
        deleteCustomerButton.setText(Translator.getTranslation("delete"));

        /** populate tab text based on locale **/
        customersTabLabel.setText(Translator.getTranslation("customers"));
        appointmentsTabLabel.setText(Translator.getTranslation("appointments"));
        userID.setText(Integer.toString(UserDao.getLoggedInUser().getId()));

        filterByLabel.setText(Translator.getTranslation("filterby"));
        /** populate radio button text based on locale **/
        allAppointments.setText(Translator.getTranslation("all"));
        monthAppointments.setText(Translator.getTranslation("month"));
        weekAppointments.setText(Translator.getTranslation("week"));

        /** set default state of appointment filter selection **/
        allAppointments.setSelected(true);
        allAppointments.setDisable(true);

        /** populate appointment buttons **/
        createAppointmentButton.setText(Translator.getTranslation("create"));
        modifyAppointmentButton.setText(Translator.getTranslation("modify"));
        deleteAppointmentButton.setText(Translator.getTranslation("delete"));

    }

    /**
     *
     * @param appointment
     */
    @Override
    public void load(Appointment appointment) {
        mainTabPane.getSelectionModel().select(appointmentsTabLabel);
    }

    /**
     *
     * @param customer
     */
    @Override
    public void load(Customer customer) {

    }

    /**
     * open CreateCustomer view
     * @param actionEvent
     * @throws Exception
     */
    public void handleCreateCustomer(ActionEvent actionEvent) throws Exception {
        ViewCreator.createView("createcustomer", "CreateCustomer", 600, 400, actionEvent, this);
    }

    /**
     * open modifyCustomer view with selected customer from tableview, if no customer is selected user receives a popup notification
     * @param actionEvent
     * @throws Exception
     */
    public void handleModifyCustomer(ActionEvent actionEvent) throws Exception {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ViewCreator.createViewWithCustomer("modifycustomer", "ModifyCustomer", 600, 400, actionEvent, this, selectedCustomer);
        } else {
            PopUpBox.displayError("unselectedcustomermodify");
        }
    }

    /**
     * delete selected customer, handles deletion of customers current appointments if applicable
     * @param actionEvent
     * @throws SQLException
     */
    public void handleDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> customerAppointments = UserDao.getAllCustomerAppointments(selectedCustomer);
        if (selectedCustomer != null) {
            if (PopUpBox.displayConfirmation("Are you sure you would like to delete " + selectedCustomer.getName() + " from Customers\n\nCustomer has " + customerAppointments.size() + " scheduled appointments")) {
                UserDao.deleteCustomer(selectedCustomer);
                /** repopulate table with newly updated customer table **/
                customerTable.setItems(UserDao.getAllCustomers());
            }
        } else {
            PopUpBox.displayError("unselectedcustomerdelete");
        }
    }

    /**
     *
     * @param event
     */
    public void handleAppointmentTabSelect(Event event) {
    }

    /**
     * open create customer view
     * @param actionEvent
     * @throws IOException
     */
    public void handleCreateAppointment(ActionEvent actionEvent) throws IOException {
        ViewCreator.createView("createappointment", "CreateAppointment", 630, 430, actionEvent, this);
    }

    /**
     *  open modifyCustomer view with selected customer from tableview, if no customer is selected user receives a popup notification
     * @param actionEvent
     */
    public void handleModifyAppointment(ActionEvent actionEvent) {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        ViewCreator.createViewWithAppointment("modifyappointment", "ModifyAppointment", 630, 430, actionEvent, this, selectedAppointment);
    }

    /**
     * delete selected appointment
     * @param actionEvent
     */
    public void handleDeleteAppointment(ActionEvent actionEvent) {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        UserDao.deleteAppointment(selectedAppointment);
        if (allAppointments.isSelected()) {
            appointmentTable.setItems(UserDao.getAllAppointments());
        } else if (monthAppointments.isSelected()) {
            filterAppointmentsMonth();
        } else if (weekAppointments.isSelected()) {
            filterAppointmentsWeek();
        }
    }

    /**
     * show all appointments in tableview
     * @param actionEvent
     */
    public void allAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (allAppointments.isSelected()) {
                allAppointments.setSelected(true);
                monthAppointments.setSelected(false);
                weekAppointments.setSelected(false);
                appointmentTable.setItems(UserDao.getAllAppointments());

                allAppointments.setDisable(true);
                weekAppointments.setDisable(false);
                monthAppointments.setDisable(false);
            }
        } catch (Exception e) {
            PopUpBox.displayError("errorallappointments");
        }
    }

    /**
     * filter appointments by current month
     */
    public void filterAppointmentsMonth() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        ObservableList<Appointment> appointments = UserDao.getAllAppointments();
        appointmentTable.setItems(appointments.filtered(appointment -> {
            LocalDate appointmentDate = LocalDateTime.parse(appointment.getStart()).toLocalDate();
            return (appointmentDate.getMonthValue() == currentMonth) && (appointmentDate.getYear() == currentYear);
        }));
    }

    /**
     * filter appointments by current week
     */
    public void filterAppointmentsWeek() {
        LocalDate currentDate = LocalDate.now();
        int weekOfYear = currentDate.get(WeekFields.ISO.weekOfWeekBasedYear());
        int currentYear = currentDate.getYear();
        ObservableList<Appointment> appointments = UserDao.getAllAppointments();
        appointmentTable.setItems(appointments.filtered(appointment -> {
            LocalDate appointmentDate = LocalDateTime.parse(appointment.getStart()).toLocalDate();
            return (appointmentDate.getYear() == currentYear) && (appointmentDate.get(WeekFields.ISO.weekOfWeekBasedYear()) == weekOfYear);
        }));
    }

    /**
     * activate filter month button in GUI
     * @param actionEvent
     */
    public void monthAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (monthAppointments.isSelected()) {
                allAppointments.setSelected(false);
                monthAppointments.setSelected(true);
                weekAppointments.setSelected(false);

                allAppointments.setDisable(false);
                monthAppointments.setDisable(true);
                weekAppointments.setDisable(false);
                filterAppointmentsMonth();
            }
        } catch (Exception e) {
            PopUpBox.displayError("errormonthappointments");
        }
    }

    /**
     * activate filter week button in GUI
     * @param actionEvent
     */
    public void weekAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (weekAppointments.isSelected()) {
                allAppointments.setSelected(false);
                monthAppointments.setSelected(false);
                weekAppointments.setSelected(true);

                allAppointments.setDisable(false);
                monthAppointments.setDisable(false);
                weekAppointments.setDisable(true);
                filterAppointmentsWeek();
            }
        } catch (Exception e) {
            PopUpBox.displayError("errorweekappointments");
        }
    }
}
