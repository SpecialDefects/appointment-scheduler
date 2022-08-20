package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCustomerController implements Initializable, LoadableController {
    public Button createButton;
    public Button cancelButton;
    private User userCreating;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createButton.setText(Translator.getTranslation("create"));
        cancelButton.setText(Translator.getTranslation("cancel"));
    }

    @Override
    public void load(User user) {
        userCreating = user;
    }

    @Override
    public void load(Appointment appointment) {

    }

    public void handleCreate(ActionEvent actionEvent) throws Exception {
        UserDao.createCustomer("Jean Grey", "221 Xavier Way", "44920", "419-443-2212", 43, userCreating.getID());
        ViewCreator.createViewWithPayload("mainmenu", "MainMenu", 600, 400, actionEvent, this, userCreating);
    }

    public void handleCancel(ActionEvent actionEvent) throws Exception {
        ViewCreator.createViewWithPayload("mainmenu", "MainMenu", 600, 400, actionEvent, this, userCreating);
    }
}
