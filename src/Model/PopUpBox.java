package Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class PopUpBox {
    public static void displayError(String message) {

        ButtonType okButton = new ButtonType(Translator.getTranslation("ok"), ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "", okButton);
        alert.setHeaderText(Translator.getTranslation("warning"));
        alert.setTitle(Translator.getTranslation("warning"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean displayConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Translator.getTranslation("confirmation"));
        alert.setContentText(message);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
