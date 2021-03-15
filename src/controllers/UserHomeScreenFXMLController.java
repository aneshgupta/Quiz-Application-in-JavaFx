
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import models.User;


public class UserHomeScreenFXMLController implements Initializable {

    @FXML
    private Tab allQuizTab;
    @FXML
    private Tab attemptedQuizTab;
    
    private User user;

    public void setUser(User user) {
        this.user = user;
        setAllQuizTab();
        setAttemptedQuizTab();
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 

    public void setAllQuizTab() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/fxml/UserAllQuizzessScreenFXML.fxml"));
            Parent allQuizNode = fXMLLoader.load();
            UserAllQuizzessScreenFXMLController controller = fXMLLoader.getController();
            controller.setUser(this.user);
            this.allQuizTab.setContent(allQuizNode);
        } 
        catch (IOException ex) {
            Logger.getLogger(UserHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void setAttemptedQuizTab() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/fxml/UserAttemptedQuizScreenFXML.fxml"));
            Parent attemptedQuizNode = fXMLLoader.load();
            UserAttemptedQuizScreenFXMLController controller = fXMLLoader.getController();
            controller.setUser(user);
            this.attemptedQuizTab.setContent(attemptedQuizNode);
        } 
        catch (IOException ex) {
            Logger.getLogger(UserHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
