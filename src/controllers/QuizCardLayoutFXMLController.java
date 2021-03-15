
package controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import listners.NewScreenListner;
import models.Quiz;
import models.User;


public class QuizCardLayoutFXMLController implements Initializable {

    @FXML
    private Label quizTitleLabel;
    @FXML
    private Label noqLabel;
    @FXML
    private JFXButton startQuizButton;

    private NewScreenListner newScreenListner;
    private Quiz quiz;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
    
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.quizTitleLabel.setText(this.quiz.getQuizTitle());
    }
    
    
    public void setNewScreenListner(NewScreenListner newScreenListner) {
        this.newScreenListner = newScreenListner;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setQuizTitleLabel(String title) {
        this.quizTitleLabel.setText(title);
    }

    public void setNoqLabel(String noq) {
        this.noqLabel.setText(noq);
    }
    
    @FXML
    private void startQuiz(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/QuestionScreenFXML.fxml"));

        try{
            Node node = fxmlLoader.load();
            QuestionScreenFXMLController questionScreenFXMLController =  fxmlLoader.getController();
            questionScreenFXMLController.setQuiz(quiz);
            questionScreenFXMLController.setUser(this.user);
            questionScreenFXMLController.setNewScreenListner(this.newScreenListner);
            this.newScreenListner.ChangeScreen(node);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
