package Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable, LoadableController {
    public Button saveButton;
    public Button cancelButton;
    public ChoiceBox countryPicker;
    public ChoiceBox divisionPicker;
    public TextField phoneTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public Label idLabel;
    public TextField idTextField;
    public Label nameLabel;
    public Label addressLabel;
    public Label postalLabel;
    public Label phoneLabel;
    public Label countryLabel;
    public Label divisionLabel;

    @Override
    public void load(User user) {
        
    }

    @Override
    public void load(Appointment appointment) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleCountryPicker(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
