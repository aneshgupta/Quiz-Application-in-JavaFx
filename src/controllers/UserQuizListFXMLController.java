
package controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import listners.NewScreenListner;
import models.Quiz;
import models.User;


public class UserQuizListFXMLController implements Initializable {

    @FXML
    private FlowPane quizListContainer;
    
    private User user;
    Map<Quiz, Integer> data = null;
    Set<Quiz> keys;
    private NewScreenListner newScreenListner;

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setNewScreenListner(NewScreenListner newScreenListner) {
        this.newScreenListner = newScreenListner;
        addQuizCardLayout();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = Quiz.getAllQuizList();
        keys = data.keySet();
    }    

    private void addQuizCardLayout() {
        
        for(Quiz quiz : keys){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/QuizCardLayoutFXML.fxml"));

            try{
                Node node = fxmlLoader.load();
                QuizCardLayoutFXMLController quizCardLayoutFXMLController =  fxmlLoader.getController();
                quizCardLayoutFXMLController.setQuiz(quiz);
                quizCardLayoutFXMLController.setUser(this.user);
                quizCardLayoutFXMLController.setNoqLabel("No of questions: " +data.get(quiz));
                quizCardLayoutFXMLController.setNewScreenListner(this.newScreenListner);
                quizListContainer.getChildren().add(node);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
