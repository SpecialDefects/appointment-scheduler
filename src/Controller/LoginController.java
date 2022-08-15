package Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField username;
    public TextField password;
    public Button loginButton;
    public Label userLocationLabel;
    public Label userLocation;
    private ArrayList<String> languages = new ArrayList<String>(Arrays.asList("en", "fr"));
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale currentLocale = Locale.FRANCE;
        String language = currentLocale.getLanguage();
        if (languages.contains("language")) {
            System.out.println("Translation exists");
        }
        ResourceBundle translator = ResourceBundle.getBundle("ResourceBundles/Language_" + currentLocale.getLanguage(), currentLocale);


        username.setPromptText(translator.getString("username"));
        password.setPromptText(translator.getString("password"));
        loginButton.setText(translator.getString("login"));
        userLocationLabel.setText(translator.getString("location"));

        ZoneId zone = ZoneId.systemDefault();
        Locale.getDefault().toString();
        userLocation.setText(zone.toString());
    }
}
