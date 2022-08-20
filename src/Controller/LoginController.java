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

    public void handleLogin(ActionEvent actionEvent) throws SQLException {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User userLogin = UserDao.login(username, password);
            ViewCreator.createViewWithPayload("mainmenu", "MainMenu", 600, 400, actionEvent, this, userLogin);
        }
        catch (Exception e) {
            PopUpBox.displayError(e.getMessage());
        }
    }

    @Override
    public void load(User user) {

    }

    @Override
    public void load(Appointment appointment) {

    }
}
