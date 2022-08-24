package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle(Translator.getTranslation("login"));
        primaryStage.setScene(new Scene(root, 330, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Translator.setLocale(Locale.ENGLISH);
        JDBC.makeConnection();
        launch(args);
    }
}
