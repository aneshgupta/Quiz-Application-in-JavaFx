package controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;
import org.controlsfx.control.Notifications;

public class LoginFXMLController implements Initializable {
    
    @FXML
    private TextField adminUsername;
    @FXML
    private TextField adminPassword;
    @FXML
    private Button adminLoginButton;
    @FXML
    private TextField userUsername;
    @FXML
    private TextField userPassword;
    @FXML
    private Button userLoginButton;
    @FXML
    private Tab userTab;
    @FXML
    private Tab adminTab;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginAdmin(ActionEvent event) {
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        
        if(username.trim().equals(constants.AdminUserPass.adminUsername) && password.trim().equals(constants.AdminUserPass.adminPassword)){
            
            try{
                
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreen.fxml"));
                Stage stage = (Stage)adminPassword.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                
                Notifications.create().title("Admin Login Success").position(Pos.CENTER)
                    .hideAfter(Duration.millis(2000)).text("Admin login successfully")
                    .showInformation();
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
            
            System.out.println("Admin Login Success...");
            
        }
        else {
            Notifications.create().title("Admin Login Failed").position(Pos.CENTER)
                    .hideAfter(Duration.millis(2000)).text("Username or Password incorrect...")
                    .showError();
            System.out.println("Admin Login Failed!!!");
            
        }
     
        System.out.println("AdminLoginController.loginAdmin()");
    }

    @FXML
    private void loginUser(ActionEvent event) {
        System.out.println("AdminLoginController.loginUser()");
        
        String userName = userUsername.getText();
        String password = userPassword.getText();
        User user = new User();
        user.setEmail(userName);
        user.setPassword(password);
        
        if(user.userLogin()) {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/fxml/UserHomeScreenFXML.fxml"));
            try{
                Parent root = fXMLLoader.load();
                UserHomeScreenFXMLController controller = fXMLLoader.getController();
                controller.setUser(user);
                Stage stage = (Stage)adminPassword.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                
                Notifications.create().title("User Login Success").position(Pos.CENTER)
                    .hideAfter(Duration.millis(2000)).text("User login successfully")
                    .showInformation();
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
            
        }
        else {
            Notifications.create().title("User Login Failed").position(Pos.CENTER)
                    .hideAfter(Duration.millis(2000)).text("Username or Password incorrect...")
                    .showError();
        }
        
    }
    
}
