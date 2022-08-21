package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginController implements Initializable, LoadableController {
    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Label userLocation;

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

    public void handleLogin(ActionEvent actionEvent) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            UserDao.login(username, password);
            ViewCreator.createView("mainmenu", "MainMenu", 900, 500, actionEvent, this);
        }
        catch (Exception e) {
            PopUpBox.displayError("invalid");
        }
    }

    @Override
    public void load(Appointment appointment) {

    }

    @Override
    public void load(Customer customer) {

    }
}
