package Controller;

import Model.*;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public void load(Appointment appointment) {

    }

    @Override
    public void load(Customer customer) {
        try {
            ObservableList<Division> divisions = UserDao.getAllDivisions();
            ObservableList<Country> countries = UserDao.getAllCountries();
            Division customerDivision = divisions.filtered(division -> division.getId() == customer.getDivisionId()).get(0);
            Country customerCountry = countries.filtered(country -> country.getId() == customerDivision.getCountryId()).get(0);
            idTextField.setText(Integer.toString(customer.getId()));
            nameTextField.setText(customer.getName());
            addressTextField.setText(customer.getAddress());
            postalTextField.setText(customer.getPostalCode());
            phoneTextField.setText(customer.getPhone());
            countryPicker.setItems(countries);
            divisionPicker.setItems(divisions.filtered(division -> division.getCountryId() == customerCountry.getId()));
            countryPicker.setValue(customerCountry);
            divisionPicker.setValue(customerDivision);
        } catch (Exception e) {
            PopUpBox.displayError("Modified Customer failed to load");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postal = postalTextField.getText();
        int division = ((Division) divisionPicker.getValue()).getId();
        UserDao.updateCustomer(id, name, address, postal, division);
    }

    public void handleCountryPicker(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
    }
}
