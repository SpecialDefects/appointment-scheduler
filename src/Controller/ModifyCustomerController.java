package Controller;

import Model.*;
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

/**
 * controls modifyCustomerView
 */
public class ModifyCustomerController implements Initializable, LoadableController {
    /** save button **/
    public Button saveButton;
    /** cancel button **/
    public Button cancelButton;

    /** country choice box **/
    public ChoiceBox countryPicker;
    /** division choice box **/
    public ChoiceBox divisionPicker;

    /** phone textfield **/
    public TextField phoneTextField;
    /** name textfield **/
    public TextField nameTextField;
    /** address textfield **/
    public TextField addressTextField;
    /** postal textfield **/
    public TextField postalTextField;
    /** id textfield **/
    public TextField idTextField;

    /** id label **/
    public Label idLabel;
    /** name label **/
    public Label nameLabel;
    /** address label **/
    public Label addressLabel;
    /** postal label **/
    public Label postalLabel;
    /** phone label **/
    public Label phoneLabel;
    /** country label **/
    public Label countryLabel;
    /** division label **/
    public Label divisionLabel;

    /** list of countries **/
    public ObservableList<Country> countries;
    /** list of divisions **/
    public ObservableList<Division> divisions;

    /**
     *
     * @param appointment
     */
    @Override
    public void load(Appointment appointment) {

    }

    /**
     * load selected customer information from previous view
     * @param customer
     */
    @Override
    public void load(Customer customer) {
        try {
            divisions = UserDao.getAllDivisions();
            countries = UserDao.getAllCountries();
            Division customerDivision = divisions.filtered(division -> division.getId() == customer.getDivisionId()).get(0);
            Country customerCountry = countries.filtered(country -> country.getId() == customerDivision.getCountryId()).get(0);
            idTextField.setText(Integer.toString(customer.getId()));
            nameTextField.setText(customer.getName());
            addressTextField.setText(customer.getAddress());
            postalTextField.setText(customer.getPostalCode());
            phoneTextField.setText(customer.getPhone());
            countryPicker.setItems(countries);
            divisionPicker.setItems(divisions.filtered(division -> division.getCountryId() == customerCountry.getId()));
            divisionPicker.setValue(customerDivision);
            countryPicker.setValue(customerCountry);
            Country pickedCountry = (Country) countryPicker.getSelectionModel().getSelectedItem();
            if (pickedCountry.getId() == 1) {
                divisionLabel.setText(Translator.getTranslation("state"));
            } else if (pickedCountry.getId() == 2) {
                divisionLabel.setText(Translator.getTranslation("nation"));
            }
            else {
                divisionLabel.setText(Translator.getTranslation("province"));
            }

        } catch (Exception e) {
            PopUpBox.displayError("errormodifiedcustomer");
        }
    }

    /**
     * initialize modifyCustomerView to default state
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveButton.setText(Translator.getTranslation("save"));
        cancelButton.setText(Translator.getTranslation("cancel"));
    }

    /**
     * handle save of new customer
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void handleSave(ActionEvent actionEvent) throws SQLException, IOException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postal = postalTextField.getText();
        int division = ((Division) divisionPicker.getValue()).getId();
        UserDao.updateCustomer(id, name, address, postal, division);

        ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
    }

    /**
     * handle user input of country selection, filter divisions based on country selection
     * @param actionEvent
     */
    public void handleCountryPicker(ActionEvent actionEvent) {
        Country pickedCountry = (Country) countryPicker.getSelectionModel().getSelectedItem();
        if (pickedCountry != null) {
            if (pickedCountry.getId() == 1) {
                divisionLabel.setText(Translator.getTranslation("state"));
            } else if (pickedCountry.getId() == 2) {
                divisionLabel.setText(Translator.getTranslation("nation"));
            }
            else {
                divisionLabel.setText(Translator.getTranslation("province"));
            }
            divisionPicker.setItems(divisions.filtered(division -> division.getCountryId() == pickedCountry.getId()));
        }
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
