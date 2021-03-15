
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;


public class AddQuizFXMLController implements Initializable {

    @FXML
    private JFXTextField quizTitle;
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXTextArea question;
    @FXML
    private JFXTextField option1;
    @FXML
    private JFXTextField option2;
    @FXML
    private JFXTextField option3;
    @FXML
    private JFXTextField option4;
    @FXML
    private JFXRadioButton radioOption1;
    @FXML
    private JFXRadioButton radioOption2;
    @FXML
    private JFXRadioButton radioOption3;
    @FXML
    private JFXRadioButton radioOption4;
    @FXML
    private JFXButton addNextQuestion;
    @FXML
    private JFXButton submitQuiz;

    private ToggleGroup radioGroup;
    private Quiz quiz = null;
    private ArrayList<Question> questions = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioButtonSetup();
    }    
    
    private void radioButtonSetup() {
        radioGroup = new ToggleGroup();
        radioOption1.setToggleGroup(radioGroup);
        radioOption2.setToggleGroup(radioGroup);
        radioOption3.setToggleGroup(radioGroup);
        radioOption4.setToggleGroup(radioGroup);
    }

    @FXML
    private void setQuizTitle(ActionEvent event) {
        String title = quizTitle.getText().trim();
        quiz = new Quiz();
        if(title.trim().isEmpty()){
            Notifications.create().hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER).title("Quiz Title")
                    .text("Enter Valid Title").showError();
            
        }else{
            quizTitle.setEditable(false);
            System.out.println("Save title...");
            quiz.setQuizTitle(title);
        }
        
        System.out.println("controllers.AddQuizFXMLController.setQuizTitle()");
    }
    
    private boolean validateFields(){
        
        if(this.quiz == null){
            Notifications.create().hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER).title("Quiz")
                    .text("Enter Valid Title").showError();
            return false;   
        }
        
        String ques = question.getText();
        String op1 = option1.getText();
        String op2 = option2.getText();
        String op3 = option3.getText();
        String op4 = option4.getText();
        Toggle selectedRadio = radioGroup.getSelectedToggle();
                
        if(ques.trim().isEmpty() || op1.trim().isEmpty() || op2.trim().isEmpty() 
                || op3.trim().isEmpty() || op4.trim().isEmpty()){
            
            Notifications.create().title("Question Fields")
                    .text("All fields required...\n [Question, Option1, Option2, Option3, Option4]")
                    .darkStyle().hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT).showError();
            return false;
        }
        else if(selectedRadio == null){
            
            Notifications.create().title("Question Fields")
                    .text("Please select a correct option...")
                    .darkStyle().hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT).showError();
            return false;
        }
        else{
            return true;
        }
    }
    
    private void addQuestion(){
        boolean validate = validateFields();
        Question question = new Question();
        if(validate){
            question.setQuestion(this.question.getText().trim());
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());
            
            Toggle selected = radioGroup.getSelectedToggle();
            String ans = null;
            if(selected == radioOption1){
                ans = option1.getText();
            }
            else if(selected == radioOption2){
                ans = option2.getText();
            }
            else if(selected == radioOption3){
                ans = option3.getText();
            }
            else{
                ans = option4.getText();
            }
            question.setAnswer(ans);
            
            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            
            question.setQuiz(quiz);
            
            questions.add(question);
            
            System.out.println(questions);
            System.out.println("controllers.AddQuizFXMLController.addNextQuestion()");
            
        }
    }

    @FXML
    private void addNextQuestion(ActionEvent event) {
        addQuestion();
    }

    @FXML
    private void submitQuiz(ActionEvent event) {
        if(validateFields()){
            addQuestion();
            if(quiz.saveAll(questions)) {
                Notifications.create().hideAfter(Duration.millis(5000))
                        .position(Pos.CENTER).title("Quiz Submit")
                        .text("Quiz saved successfully").showInformation();
                this.quizTitle.clear();
                quizTitle.setEditable(true);
            }
            else {
                Notifications.create().hideAfter(Duration.millis(2000))
                        .position(Pos.CENTER).title("Quiz Fail")
                        .text("Quiz not saved...Please try again").showError();
            }
        }
        
    }
    
}
