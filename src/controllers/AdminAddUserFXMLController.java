
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.User;
import org.controlsfx.control.Notifications;

public class AdminAddUserFXMLController implements Initializable {

    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField mobileNo;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXRadioButton radioMale;
    @FXML
    private JFXButton saveUserButton;
    @FXML
    private JFXRadioButton radioFemale;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> firstNameCol;
    @FXML
    private TableColumn<User, String> userIdCol;
    @FXML
    private TableColumn<User, String> lastNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> mobileNoCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, String> genderCol;
    
//    local variables
    private ToggleGroup radioGroup;
    private User user = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       radioSetup();
       renderAll();
    }    
    
    private void radioSetup(){
        radioGroup = new ToggleGroup();
        this.radioMale.setSelected(true);
        this.radioMale.setToggleGroup(radioGroup);
        this.radioFemale.setToggleGroup(radioGroup);
    }

    private void resetForm(){
        this.firstName.clear();
        this.lastName.clear();
        this.email.clear();
        this.mobileNo.clear();
        this.password.clear();
        this.radioMale.setSelected(true);
    }
    
    @FXML
    private void saveUser(ActionEvent event) {
        
        System.out.println("controllers.AdminAddUserFXMLController.saveUser()");
        
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String email = this.email.getText();
        String mobileNo = this.mobileNo.getText();
        String password = this.password.getText();
        String gender = "male";
        Toggle selected = radioGroup.getSelectedToggle();
        if(selected == radioFemale){
            gender = "female";
        } 
        
        String message = null;
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"; 
        Pattern p = Pattern.compile(regex);
            
        if(firstName.length() < 0){
            message = "Please enter valid first name";
        }
        else if(lastName.length() < 0){
            message = "Please enter valid last name";
        }
        else if(!p.matcher(email).matches()){
            message = "Please enter valid email";
        }
        else if(mobileNo.length() != 10){
            message = "Please enter valid mobile no";
        }
        else if(password.length() < 6){
            message = "Password must have at least 6 chars";
        }
        
        if(message!=null){
            Notifications.create().title("Form Error").darkStyle().position(Pos.BOTTOM_LEFT)
                .hideAfter(Duration.millis(2000)).text(message)
                .showError();
           
        }
        else {  
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setMobileNo(mobileNo);
            user.setGender(gender);
            user.setPassword(password);
            
            user = user.saveUser();
            if(user != null){
                Notifications.create().title("Add User Success").darkStyle().position(Pos.CENTER)
                .hideAfter(Duration.millis(5000)).text("User added successfully...")
                .showInformation();
                resetForm();
                
                userTable.getItems().add(1, user);
            }
            else{
                Notifications.create().title("Add User Fail").position(Pos.CENTER)
                .hideAfter(Duration.millis(2000)).text("User not added")
                .showError();
            }
            
        }

        
    }

    private void renderAll() {
        ArrayList<User> users = new ArrayList<>();
        users = User.getUSerDetails();
        
        this.userTable.getItems().clear();
        this.userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        this.firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.mobileNoCol.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        this.genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        this.userTable.getItems().addAll(users);
        
    }
   
}
