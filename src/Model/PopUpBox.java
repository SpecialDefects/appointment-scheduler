package Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Represents an informational dialog box
 * Utilizes Translator for message translation
 */
public class PopUpBox {
    /** display an error dialog box with provided message **/
    public static void displayError(String message) {
        ButtonType okButton = new ButtonType(Translator.getTranslation("ok"), ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "", okButton);
        alert.setHeaderText(Translator.getTranslation("warning"));
        alert.setTitle(Translator.getTranslation("warning"));
        alert.setContentText(Translator.getTranslation(message));
        alert.showAndWait();
    }

    /** display a confirmation box with provided message **/
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
