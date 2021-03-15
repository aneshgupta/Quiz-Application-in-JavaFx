
package controllers;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import listners.NewScreenListner;
import models.User;


public class UserAllQuizzessScreenFXMLController implements Initializable {

    @FXML
    private JFXButton backButton;
    @FXML
    private StackPane mainContainer;
    
    private User user;

    public void setUser(User user) {
        this.user = user;
        addQuizListScreen();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    private void AddScreenToStackPane(Node node){
        this.mainContainer.getChildren().add(node);
    }
    
    private void addQuizListScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/UserQuizListFXML.fxml"));
        
        try{
            Node node = fxmlLoader.load();  
            UserQuizListFXMLController userQuizListFXMLController = fxmlLoader.getController();
            userQuizListFXMLController.setUser(this.user);
            userQuizListFXMLController.setNewScreenListner(new NewScreenListner() {
                @Override
                public void ChangeScreen(Node node) {
                    AddScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                   mainContainer.getChildren().remove(mainContainer.getChildren().size()-1);
                }

                @Override
                public void handle(Event event) {
                    //TODO
                }
                
            });
            mainContainer.getChildren().add(node);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }

    @FXML
    private void backFunction(ActionEvent event) {
        ObservableList<Node> nodes = this.mainContainer.getChildren();
        if(nodes.size() == 1){
            return;
        }
        else {
            this.mainContainer.getChildren().remove(nodes.size()-1);
        }
    }
    
}
