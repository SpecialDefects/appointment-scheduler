package Model;

import Controller.LoadableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewCreator {

    public static void createView(String title, String viewName, int width, int height, ActionEvent actionEvent, Initializable currentController) throws IOException {
        Parent root = FXMLLoader.load(currentController.getClass().getResource("/View/" + viewName + ".fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, width, height);
        stage.setTitle(Translator.getTranslation(title));
        stage.setScene(scene);
        stage.show();
    }

    public static void createViewWithCustomer(String title, String viewName, int width, int height, ActionEvent actionEvent, Initializable currentController, Customer customer) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(currentController.getClass().getResource("/View/" + viewName + ".fxml"));
            Parent root = loader.load();
            LoadableController controller = loader.getController();
            /** pass part and index to modify controller **/
            controller.load(customer);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, width, height);
            stage.setTitle(Translator.getTranslation(title));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            PopUpBox.displayError("Unable to load customer");
        }
    }
}
