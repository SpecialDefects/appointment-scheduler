package Controller;

import Model.User;
import Model.UserDao;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public TableView customerTable;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostalCode;
    public TableColumn customerPhone;
    public Label userID;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(UserDao.getAllCustomers());
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void loadUser(User userLogin) {
        this.user = userLogin;
        this.userID.setText(userLogin.getID().toString());
    }
}
