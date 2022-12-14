package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * controls the modify appointment view
 */
public class ModifyAppointmentController implements Initializable, LoadableController {

    /** cancel button **/
    public Button cancelButton;
    /** save button **/
    public Button saveButton;

    /** title textfield **/
    public TextField titleTextField;
    /** customer textfield **/
    public TextField customerTextField;
    /** location textfield **/
    public TextField locationTextField;
    /** type textfield **/
    public TextField typeTextField;
    /** id textfield **/
    public TextField idTextField;
    /** user textfield **/
    public TextField userTextField;

    /** description text area **/
    public TextArea descriptionTextArea;

    /** id label **/
    public Label idLabel;
    /** title label **/
    public Label titleLabel;
    /** location label **/
    public Label locationLabel;
    /** type label **/
    public Label typeLabel;
    /** customer label **/
    public Label customerLabel;
    /** user label **/
    public Label userLabel;
    /** description label **/
    public Label descriptionLabel;
    /** contact label **/
    public Label contactLabel;
    /** start date label **/
    public Label startDateLabel;
    /** start time label **/
    public Label startTimeLabel;
    /** end time label **/
    public Label endTimeLabel;

    /** start date picker **/
    public DatePicker startDatePicker;

    /** start time combo box **/
    public ComboBox startTimePicker;
    /** end time combo box **/
    public ComboBox endTimePicker;

    /** contact choice box **/
    public ChoiceBox contactPicker;


    /**
     * initialize create appointment view to default state
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** translate all labels based on user locale **/
        idLabel.setText(Translator.getTranslation("id"));
        titleLabel.setText(Translator.getTranslation("title"));
        typeLabel.setText(Translator.getTranslation("type"));
        customerLabel.setText(Translator.getTranslation("customerid"));
        userLabel.setText(Translator.getTranslation("userid"));
        descriptionLabel.setText(Translator.getTranslation("description"));
        contactLabel.setText(Translator.getTranslation("contact"));
        startDateLabel.setText(Translator.getTranslation("startdate"));
        startTimeLabel.setText(Translator.getTranslation("starttime"));
        endTimeLabel.setText(Translator.getTranslation("endtime"));

        /** translate button text based on user locale **/
        saveButton.setText(Translator.getTranslation("save"));
        cancelButton.setText(Translator.getTranslation("cancel"));

        /** set id prompt to text based on user locale **/
        idTextField.setPromptText(Translator.getTranslation("autogenerated"));

        /** populate contacts choicebox **/
        contactPicker.setItems(UserDao.getAllContacts());

        /** create lists to calculate available appointment times **/
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        hours.addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        minutes.addAll("00", "30");
        ObservableList<String> times = FXCollections.observableArrayList();

        /** adjust times to fit users time zone **/
        LocalDateTime curr = LocalDateTime.now();
        ZonedDateTime buisnessTime = curr.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime userTime = curr.atZone(UserDao.getZone());
        int diffHours = (int) Duration.between(buisnessTime, userTime).toHours();
        int startTime = 8 - diffHours;
        for (int hour : hours) {
            for (String minute : minutes) {
                int offsetHours = (hour - diffHours) % 24;
                if (offsetHours < 10) {
                    times.add("0" + offsetHours + ":" + minute);
                } else {
                    times.add((offsetHours) + ":" + minute);
                }
                if ((offsetHours == (startTime + 14) % 24) ) { break; }
            }
        }
        /** populate time choices **/
        startTimePicker.setItems(times);
        endTimePicker.setItems(times);
    }

    /**
     *
     * @param appointment
     */
    @Override
    public void load(Appointment appointment) {
        /** retrieve appointment information from Appointment **/
        int id = appointment.getId();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = LocalDateTime.parse(appointment.getStart());
        LocalDateTime end = LocalDateTime.parse(appointment.getEnd());
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();

        /** populate fields in view **/

        idTextField.setText(Integer.toString(id));
        titleTextField.setText(title);
        descriptionTextArea.setText(description);
        locationTextField.setText(location);
        typeTextField.setText(type);
        customerTextField.setText(Integer.toString(customerId));
        userTextField.setText(Integer.toString(userId));
        /** find corresponding contact by contact id from items in contact combobox **/
        Contact chosenContact = (Contact) contactPicker.getItems().filtered((contact) -> ((Contact) contact).getId() == contactId).get(0);
        contactPicker.setValue(chosenContact);

        /** format hour and minute properly **/
        String startHour = Integer.toString(start.getHour());
        if (start.getHour() < 10) { startHour = "0" + startHour; }
        String startMinute = Integer.toString(start.getMinute());
        if (startMinute != "30") { startMinute = "00"; }

        String endHour = Integer.toString(end.getHour());
        if (end.getHour() < 10) { endHour = "0" + endHour; }
        String endMinute = Integer.toString(end.getMinute());
        if (endMinute != "30") { endMinute = "00"; }

        startDatePicker.setValue(start.toLocalDate());
        startTimePicker.setValue(startHour + ":" + startMinute);
        endTimePicker.setValue(endHour + ":" + endMinute);


    }

    /**
     *
     * @param customer
     */
    @Override
    public void load(Customer customer) { }

    /**
     * handle creation of new appointment
     * @param actionEvent
     * @throws Exception
     */
    public void handleSave(ActionEvent actionEvent) throws Exception {
        int id;
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
            /** get appointment id **/
            id = Integer.parseInt(idTextField.getText());
            /** get appointment title **/
            try {
                title = titleTextField.getText();
            } catch (Exception e) {
                throw new Exception("titleerror");
            }
            /** if title is empty throw exception **/
            if (title.length() == 0) { throw new Exception("titleblank"); }

            /** get appointment type **/
            try {
                type = typeTextField.getText();
            } catch (Exception e) {
                throw new Exception("typeerror");
            }
            /** if type is empty throw exception **/
            if (type.length() == 0) { throw new Exception("typeblank"); }

            /** get appointment description **/
            try {
                description = descriptionTextArea.getText();
            } catch (Exception e) {
                throw new Exception("descriptionerror");
            }
            /** if description throw exception **/
            if (description.length() == 0) { throw new Exception("descriptionblank"); };

            /** get appointment location **/
            try {
                location = locationTextField.getText();
            } catch (Exception e) {
                throw new Exception("locationerror");
            }
            /** if location is blank throw exception **/
            if (location.length() == 0) { throw new Exception("locationblank"); }

            /** get customer_id for appointment **/
            /** throw exception if not parseable to integer **/
            try {
                customerId = Integer.parseInt(customerTextField.getText());
            } catch (Exception e) {
                throw new Exception("customererror");
            }

            /** get user_id for appointment **/
            /** throw exception if not parseable to integer **/
            try {
                userId = Integer.parseInt(userTextField.getText());
            } catch (Exception e) {
                throw new Exception("usererror");
            }

            /** get Contact for appointment **/
            /** throw exception if no contact is selected **/
            try {
                contact = ((Contact) contactPicker.getValue()).getId();
            } catch (Exception e) {
                throw new Exception("contacterror");
            }

            /** get start time for appointment **/
            /** throw exception if start time isn't valid **/
            try {
                String startDate = startDatePicker.getValue().toString();
                String startTime = startTimePicker.getValue().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                startDateTime = LocalDateTime.parse(startDate + " " + startTime, formatter).atZone(ZoneId.of("UTC")).toLocalDateTime();
            } catch (Exception e) {
                throw new Exception("starttimeerror");
            }
            if (startDateTime.getDayOfWeek().toString() == "SATURDAY" || startDateTime.getDayOfWeek().toString() == "SUNDAY") {
                throw new Exception("starttimeweekend");
            };

            /** get end time for appoint **/
            /** throw exception if end time isn't valid **/
            try {
                String endDate = startDatePicker.getValue().toString();
                String endTime = endTimePicker.getValue().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                endDateTime = LocalDateTime.parse(endDate + " " + endTime, formatter).atZone(ZoneId.of("UTC")).toLocalDateTime();
            } catch (Exception e) {
                throw new Exception("endtimeerror");
            }
            if (endDateTime.getDayOfWeek().toString() == "SATURDAY" || endDateTime.getDayOfWeek().toString() == "SUNDAY") {
                throw new Exception("endtimeweekend");
            };
            if (startDateTime.isEqual(endDateTime)) {
                throw new Exception("timeequal");
            }

            /** throw exception if endDate is before start date **/
            if (endDateTime.isBefore(startDateTime)) {
                throw new Exception("endtimebefore");
            }
            /** create new appointment from user input **/
            Appointment newAppointment = new Appointment(id, title, description, location, type,
                    startDateTime,
                    endDateTime,
                    customerId, userId, contact);
            /** insert new appointment into database **/
            if (isCustomerAppointmentOverlap(newAppointment)) {
                throw new Exception("appointmentoverlap");
            }
            UserDao.updateAppointment(newAppointment);
            /** return to main menu **/
            ViewCreator.createViewWithAppointment("mainmenu", "MainMenu", 900, 500, actionEvent, this, newAppointment);
        } catch (Exception e) {
            PopUpBox.displayError(e.getMessage());
        }
    }

    /**
     * checks to see if appointment has scheduling conflicts
     * @param appointment appointment to check for scheduling conflict
     * @return true if appointment has scheduling conflict, else false
     */
    public boolean isCustomerAppointmentOverlap(Appointment appointment) {
        ObservableList<Appointment> customerAppointments = UserDao.getAllCustomerAppointments(appointment.getCustomerId());
        LocalDateTime toBeScheduledStart = LocalDateTime.parse(appointment.getStart());
        LocalDateTime toBeScheduledEnd = LocalDateTime.parse(appointment.getEnd());
        LocalDateTime scheduledStart;
        LocalDateTime scheduledEnd;
        for (Appointment scheduledAppointment : customerAppointments) {
            if (scheduledAppointment.getId() != appointment.getId()) {
                scheduledStart = LocalDateTime.parse(scheduledAppointment.getStart());
                scheduledEnd = LocalDateTime.parse(scheduledAppointment.getEnd());
                if ((toBeScheduledStart.isBefore(scheduledStart) && toBeScheduledEnd.isAfter(scheduledStart))
                        || (toBeScheduledStart.isEqual(scheduledStart) && toBeScheduledEnd.isEqual(scheduledEnd))
                        || (toBeScheduledStart.isEqual(scheduledStart) && toBeScheduledEnd.isBefore(scheduledEnd))
                        || (toBeScheduledStart.isEqual(scheduledStart) && toBeScheduledEnd.isAfter(scheduledEnd))
                        || (toBeScheduledStart.isAfter(scheduledStart) && toBeScheduledEnd.isBefore(scheduledEnd))
                        || (toBeScheduledStart.isEqual(scheduledEnd) && toBeScheduledEnd.isEqual(scheduledEnd))
                        || (toBeScheduledStart.isBefore(scheduledEnd) && toBeScheduledEnd.isAfter(scheduledEnd))
                        || (toBeScheduledStart.isBefore(scheduledStart) && toBeScheduledEnd.isAfter(scheduledEnd))) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * handle cancel button action, return to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void handleCancel(ActionEvent actionEvent) throws IOException {
        ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
    }
}

