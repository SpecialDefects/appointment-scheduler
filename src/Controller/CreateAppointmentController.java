package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateAppointmentController implements Initializable, LoadableController {

    public Button cancelButton;
    public Button createButton;

    public TextField titleTextField;
    public TextField customerTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextField idTextField;
    public TextField userTextField;

    public TextArea descriptionTextArea;

    public Label idLabel;
    public Label titleLabel;
    public Label locationLabel;
    public Label typeLabel;
    public Label customerLabel;
    public Label userLabel;
    public Label descriptionLabel;
    public Label contactLabel;
    public Label startDateLabel;
    public Label endDateLabel;
    public Label startTimeLabel;
    public Label endTimeLabel;

    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    public ChoiceBox startTimePicker;
    public ChoiceBox endTimePicker;
    public ChoiceBox contactPicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idLabel.setText(Translator.getTranslation("id"));
        titleLabel.setText(Translator.getTranslation("title"));
        typeLabel.setText(Translator.getTranslation("type"));
        customerLabel.setText(Translator.getTranslation("customerid"));
        userLabel.setText(Translator.getTranslation("userid"));
        descriptionLabel.setText(Translator.getTranslation("description"));
        contactLabel.setText(Translator.getTranslation("contact"));
        startDateLabel.setText(Translator.getTranslation("startdate"));
        startTimeLabel.setText(Translator.getTranslation("starttime"));
        endDateLabel.setText(Translator.getTranslation("enddate"));
        endTimeLabel.setText(Translator.getTranslation("endtime"));

        createButton.setText(Translator.getTranslation("create"));
        cancelButton.setText(Translator.getTranslation("cancel"));

        contactPicker.setItems(UserDao.getAllContacts());

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        hours.addAll("08", "09", "10", "11", "12", "13", "14", "15", "16");
        minutes.addAll("00", "15", "30", "45");
        ObservableList<String> times = FXCollections.observableArrayList();
        for (String hour : hours) {
            for (String minute : minutes) {
                times.add(hour + ":" + minute);
            }
        }
        startTimePicker.setItems(times);
        endTimePicker.setItems(times);
    }

    @Override
    public void load(Appointment appointment) {

    }

    @Override
    public void load(Customer customer) {

    }

    public void handleCreate(ActionEvent actionEvent) throws Exception {
        String title;
        String type;
        String description;
        String location;
        int customerId;
        int userId;
        int contact;
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            title = titleTextField.getText();
        } catch (Exception e) {
            throw new Exception("titleerror");
        }
        try {
            type = typeTextField.getText();
        } catch (Exception e) {
            throw new Exception("typeerror");
        }
        try {
            description = descriptionTextArea.getText();
        } catch (Exception e) {
            throw new Exception("descriptionerror");
        }
        try {
            location = locationTextField.getText();
        } catch (Exception e) {
            throw new Exception("locationerror");
        }
        try {
            customerId = Integer.parseInt(customerTextField.getText());
        } catch (Exception e) {
            throw new Exception("customererror");
        }
        try {
            userId = Integer.parseInt(userTextField.getText());
        } catch (Exception e) {
            throw new Exception("usererror");
        }
        try {
            contact = ((Contact) contactPicker.getValue()).getId();
        } catch (Exception e) {
            throw new Exception("contacterror");
        }
        try {
            String startDate = startDatePicker.getValue().toString();
            String startTime = startTimePicker.getValue().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            startDateTime = LocalDateTime.parse(startDate + " " + startTime, formatter);
        } catch (Exception e) {
            throw new Exception("starttimeerror");
        }
        try {
            String endDate = endDatePicker.getValue().toString();
            String endTime = endTimePicker.getValue().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            endDateTime = LocalDateTime.parse(endDate + " " + endTime, formatter);
        } catch (Exception e) {
            throw new Exception("endtimeerror");
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new Exception("endtimebefore");
        }
        Appointment newAppointment = new Appointment(title, description, location, type,
                                         startDateTime,
                                         endDateTime,
                                         customerId, userId, contact);
        UserDao.createAppointment(newAppointment);
        ViewCreator.createViewWithAppointment("mainmenu", "MainMenu", 900, 500, actionEvent, this, newAppointment);
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
    }
}
