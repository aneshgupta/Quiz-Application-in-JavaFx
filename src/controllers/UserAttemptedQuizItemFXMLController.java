package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listners.AttempedQuizItemClickListener;
import models.Quiz;
import models.QuizResult;


public class UserAttemptedQuizItemFXMLController implements Initializable {

    @FXML
    private VBox item;
    @FXML
    private Label title;
    
    private Quiz quiz;
    private QuizResult quizResult; 
    private AttempedQuizItemClickListener itemClickListener;
    
    public void setData(Quiz quiz , QuizResult quizResult) {
        this.quiz = quiz;
        this.quizResult = quizResult;
        this.title.setText(this.quiz.getQuizTitle());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setItemClickListener(AttempedQuizItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @FXML
    private void loadData(MouseEvent event) {
        System.out.println("Item Clicked...");
        Integer numberOfAttempedQuestions = quizResult.getNumberOfAttemptedQuestions();
        Integer numberOfQuestions = quiz.getNumberOfQuestions();
        System.out.println(numberOfAttempedQuestions);
        System.out.println(numberOfQuestions);
        itemClickListener.itemClicked(numberOfQuestions , numberOfAttempedQuestions);
    }
    
}
