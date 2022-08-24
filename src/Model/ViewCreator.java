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

/**
 * represents a tool for creating views
 */
public class ViewCreator {

    /**
     * create view
     * @param title view title
     * @param viewName view file name
     * @param width width of view
     * @param height height of view
     * @param actionEvent
     * @param currentController
     * @throws IOException
     */
    public static void createView(String title, String viewName, int width, int height, ActionEvent actionEvent, Initializable currentController) throws IOException {
        Parent root = FXMLLoader.load(currentController.getClass().getResource("/View/" + viewName + ".fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, width, height);
        stage.setTitle(Translator.getTranslation(title));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * create a view with customer payload
     * @param title view title
     * @param viewName view file name
     * @param width view width
     * @param height view height
     * @param actionEvent
     * @param currentController
     * @param customer customer to load into new view
     * @throws Exception
     */
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

    /**
     * create view with appointment payload
     * @param title view title
     * @param viewName view file name
     * @param width view width
     * @param height view height
     * @param actionEvent
     * @param currentController
     * @param appointment appointment to load into new view
     */
    public static void createViewWithAppointment(String title, String viewName, int width, int height, ActionEvent actionEvent, Initializable currentController, Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(currentController.getClass().getResource("/View/" + viewName + ".fxml"));
            Parent root = loader.load();
            LoadableController controller = loader.getController();
            /** pass part and index to modify controller **/
            controller.load(appointment);

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
