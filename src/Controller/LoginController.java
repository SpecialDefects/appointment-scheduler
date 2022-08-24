package Controller;

import Model.*;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * control login view
 */
public class LoginController implements Initializable, LoadableController {
    /** username textfield **/
    public TextField usernameField;
    /** password textfield **/
    public TextField passwordField;

    /** login button **/
    public Button loginButton;

    /** user location label **/
    public Label userLocation;

    /**
     * initialize login view to default state
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /** translate all text according to locale **/
        usernameField.setPromptText(Translator.getTranslation("username"));
        passwordField.setPromptText(Translator.getTranslation("password"));
        loginButton.setText(Translator.getTranslation("login"));


        /** set userLocation label to users zone id **/
        ZoneId zone = ZoneId.systemDefault();

        userLocation.setText(zone.getId());
    }

    /**
     * handle user login,
     * logs login success or failure
     * @param actionEvent
     * @throws Exception
     */
    public void handleLogin(ActionEvent actionEvent) throws Exception {
        try {
            ZoneId zone = ZoneId.systemDefault();
            String username = usernameField.getText();
            String password = passwordField.getText();
            /** attempts to authenticate username and password
             * if there is no match, an error is thrown and caught **/
            UserDao.login(username, password, zone);
            Log.loginSuccess(username);
            ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
        }
        catch (Exception e) {
            Log.loginAttempt(usernameField.getText());
            PopUpBox.displayError("invalid");
        }
    }

    /**
     *
     * @param appointment
     */
    @Override
    public void load(Appointment appointment) { }

    /**
     *
     * @param customer
     */
    @Override
    public void load(Customer customer) { }
}
