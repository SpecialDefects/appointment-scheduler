package Controller;

import Model.Appointment;
import Model.Customer;
import Model.Translator;
import Model.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAppointmentController implements Initializable, LoadableController {

    public Button cancelButton;
    public Button createButton;

    public TextField phoneTextField;
    public TextField titleTextField;
    public TextField addressTextField;
    public TextField postalTextField;
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
    }

    @Override
    public void load(Appointment appointment) {

    }

    @Override
    public void load(Customer customer) {

    }

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
