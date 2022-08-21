package Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, LoadableController {
    public TableView customerTable;
    public TableView appointmentTable;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostalCode;
    public TableColumn customerPhone;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Tab customersTabLabel;
    public Tab appointmentsTabLabel;
    public Label userID;
    public Button createAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public TableColumn appointmentID;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDescription;
    public TableColumn appointmentLocation;
    public TableColumn appointmentContact;
    public TableColumn appointmentType;
    public TableColumn appointmentStart;
    public TableColumn appointmentEnd;
    public TableColumn appointmentCustomer;
    public TableColumn appointmentUser;
    public RadioButton allAppointments;
    public RadioButton monthAppointments;
    public RadioButton weekAppointments;
    public Label filterByLabel;

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
        userID.setText(Integer.toString(UserDao.getLoggedInUser().getID()));

        filterByLabel.setText(Translator.getTranslation("filterby"));
        /** populate radio button text based on locale **/
        allAppointments.setText(Translator.getTranslation("all"));
        monthAppointments.setText(Translator.getTranslation("month"));
        weekAppointments.setText(Translator.getTranslation("week"));

        allAppointments.setDisable(true);

        /** populate appointment buttons **/
        createAppointmentButton.setText(Translator.getTranslation("create"));
        modifyAppointmentButton.setText(Translator.getTranslation("modify"));
        deleteAppointmentButton.setText(Translator.getTranslation("delete"));

    }

    @Override
    public void load(Appointment appointment) {

    }

    @Override
    public void load(Customer customer) {

    }

    public void handleCreateCustomer(ActionEvent actionEvent) throws Exception {
        ViewCreator.createView("createcustomer", "CreateCustomer", 600, 400, actionEvent, this);
    }

    public void handleModifyCustomer(ActionEvent actionEvent) throws Exception {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ViewCreator.createViewWithCustomer("modifycustomer", "ModifyCustomer", 600, 400, actionEvent, this, selectedCustomer);
        } else {
            PopUpBox.displayError("You must select a customer to modify");
        }
    }

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
            PopUpBox.displayError("You must select a customer to delete");
        }
    }

    public void handleAppointmentTabSelect(Event event) {
    }

    public void handleCreateAppointment(ActionEvent actionEvent) throws IOException {
        ViewCreator.createView("createappointment", "CreateAppointment", 630, 430, actionEvent, this);
    }

    public void handleModifyAppointment(ActionEvent actionEvent) {
    }

    public void handleDeleteAppointment(ActionEvent actionEvent) {
    }

    public void allAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (allAppointments.isSelected()) {
                allAppointments.setSelected(true);
                monthAppointments.setSelected(false);
                weekAppointments.setSelected(false);
                appointmentTable.setItems(UserDao.getAllAppointments());
                allAppointments.setDisable(true);
            }
        } catch (Exception e) {
            PopUpBox.displayError("Unable to show All appointments");
        }
    }

    public void monthAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (monthAppointments.isSelected()) {
                allAppointments.setSelected(false);
                monthAppointments.setSelected(true);
                weekAppointments.setSelected(false);

                allAppointments.setDisable(false);
                monthAppointments.setDisable(true);
                weekAppointments.setDisable(false);
                int currentMonth = LocalDate.now().getMonthValue();
                int currentYear = LocalDate.now().getYear();
                ObservableList<Appointment> appointments = UserDao.getAllAppointments();
                appointmentTable.setItems(appointments.filtered(appointment -> {
                    LocalDate appointmentDate = LocalDate.parse(appointment.getStart());
                    return (appointmentDate.getMonthValue() == currentMonth) && (appointmentDate.getYear() == currentYear);
                }));
            }
        } catch (Exception e) {
            PopUpBox.displayError("Unable to filter appointments by Month");
        }
    }

    public void weekAppointmentsButton(ActionEvent actionEvent) {
        try {
            if (weekAppointments.isSelected()) {
                allAppointments.setSelected(false);
                monthAppointments.setSelected(false);
                weekAppointments.setSelected(true);

                allAppointments.setDisable(false);
                monthAppointments.setDisable(false);
                weekAppointments.setDisable(true);
                Calendar currentDate = Calendar.getInstance();
                Calendar appointmentDate = Calendar.getInstance();
                currentDate.setTime(java.sql.Date.valueOf((LocalDate.now().toString())));
                int currentYear = currentDate.getWeekYear();
                int weekOfYear = currentDate.get(currentDate.WEEK_OF_YEAR);
                System.out.println(weekOfYear);
                ObservableList<Appointment> appointments = UserDao.getAllAppointments();
                appointmentTable.setItems(appointments.filtered(appointment -> {
                    appointmentDate.setTime(java.sql.Date.valueOf((appointment.getStart())));
                    return (appointmentDate.getWeekYear() == currentYear) && (appointmentDate.get(appointmentDate.WEEK_OF_YEAR) == weekOfYear);
                }));
            }
        } catch (Exception e) {
            PopUpBox.displayError("Unable to filter appointments by Week");
        }
    }
}
