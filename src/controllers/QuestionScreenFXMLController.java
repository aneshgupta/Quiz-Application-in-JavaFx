
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import listners.NewScreenListner;
import models.Question;
import models.Quiz;
import models.QuizResult;
import models.User;
import org.controlsfx.control.Notifications;
import static start.Test.convertTime;


public class QuestionScreenFXMLController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label timingLabel;
    @FXML
    private JFXRadioButton option1;
    @FXML
    private ToggleGroup options;
    @FXML
    private JFXRadioButton option2;
    @FXML
    private JFXRadioButton option3;
    @FXML
    private JFXRadioButton option4;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXButton submitButton;
    @FXML
    private Label questionLabel;
    @FXML
    private FlowPane progressPane;
    
    private Quiz quiz;
    private User user;
    private NewScreenListner newScreenListner;
    private List<Question> questionList;
    private Question currentQuestion;
    private int qIndex = 0;
    private Map<Question, String> questionAnswer = new HashMap<>();
    private int nofRightAnswers = 0;
    
    private long hr, min, sec, totalTime;
    private Timer timer = new Timer();
    
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.titleLabel.setText(this.quiz.getQuizTitle());
        this.getData();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNewScreenListner(NewScreenListner newScreenListner) {
        this.newScreenListner = newScreenListner;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.option1.setSelected(true);
    }   
    
    private void getData(){
        if(quiz != null){
            this.questionList = quiz.getAllQuestions();
            Collections.shuffle(this.questionList);
            renderProgress();
            setnextQuestion();
            setTimer();
            
        }  
    }
    
    private void setnextQuestion(){
        if(this.qIndex < questionList.size()){
            Node node = this.progressPane.getChildren().get(qIndex);
            ProgressCircleFXMLController controller = (ProgressCircleFXMLController)node.getUserData();
            controller.setCurrentColor();
            
            this.currentQuestion = this.questionList.get(this.qIndex);
            List<String> options = new ArrayList<>();
            options.add(this.currentQuestion.getOption1());
            options.add(this.currentQuestion.getOption2());
            options.add(this.currentQuestion.getOption3());
            options.add(this.currentQuestion.getOption4());
            Collections.shuffle(options);
            
            this.questionLabel.setText(this.currentQuestion.getQuestion());
            this.option1.setText(options.get(0));
            this.option2.setText(options.get(1));
            this.option3.setText(options.get(2));
            this.option4.setText(options.get(3));
            
            this.qIndex++;
            
        }
        else {
            hideNextButton();
        }
    }
    
    private void hideNextButton(){
        this.nextButton.setVisible(false);
    }
    
    private void renderProgress() {
        for(int i=0;i<this.questionList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/ProgressCircleFXML.fxml"));

            try{
                Node node = fxmlLoader.load();
                ProgressCircleFXMLController progressCircleFXMLController = 
                        fxmlLoader.getController();
                progressCircleFXMLController.setQuesNumber(i+1);
                progressCircleFXMLController.setDefaultColor();
                this.progressPane.getChildren().add(node);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void checkAnswerChangeColor(){
        Boolean isRight = false;
        JFXRadioButton selectedOption = (JFXRadioButton)options.getSelectedToggle();
        String userAnswer = selectedOption.getText().trim();
        String correctAnswer = this.currentQuestion.getAnswer().trim();
        
        if(userAnswer.equalsIgnoreCase(correctAnswer)){
            isRight = true;
        }
        
        Node node = this.progressPane.getChildren().get(qIndex-1);
        ProgressCircleFXMLController controller = (ProgressCircleFXMLController)node.getUserData();
        
        if(isRight){
            controller.setRightAnswerColor();
            this.nofRightAnswers++;
        }
        else {
            controller.setWrongAnswerColor();
            
        }
        
        questionAnswer.put(currentQuestion, userAnswer);
    }
    
    public void convertTime() {
        min = TimeUnit.SECONDS.toMinutes(totalTime);
        sec = totalTime - (min*60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr*60);
                
        this.timingLabel.setText(format(hr)+" : "+format(min)+" : "+format(sec));   
        totalTime--;
    }
    
    public String format(long value){
        if(value<10){
            return "0"+value;
        }
        else{
            return value+"";
        }
    }
    
    public void setTimer(){
        this.totalTime = questionList.size() * 30;
        
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("After 1 sec....");
                
                        convertTime();
                        if(totalTime <=0){
                            timer.cancel();
                            timingLabel.setText("00 : 00 : 00");
                            Notifications.create().title("Quiz Timer Message").hideAfter(Duration.millis(2000))
                                .position(Pos.CENTER).text("Time Up!!!")
                                    .showError();
                            nextButton.setVisible(false);
                            submitQuiz(null);
                            
                        }
                    }
                });
            };
        };
        
        timer.schedule(timerTask, 0, 1000);
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
        checkAnswerChangeColor();
        setnextQuestion();
    }

    @FXML
    private void submitQuiz(ActionEvent event) {
        System.out.println(questionAnswer);
        System.out.println(this.user);
        QuizResult quizResult = new QuizResult(quiz, this.user, this.nofRightAnswers);
        boolean result =  quizResult.saveResult(questionAnswer);
        
        if(result) {
            Notifications.create().title("Quiz Submit Message").hideAfter(Duration.millis(5000))
                    .position(Pos.CENTER).text("Quiz submitted succsessfully...")
                    .showInformation();
            this.timer.cancel();
            openResultScreen();
            
        }
        else {
            Notifications.create().title("Quiz Submit Message").hideAfter(Duration.millis(2000))
                    .position(Pos.CENTER).text("Something went wrong!!!")
                    .showError();
            
        }
    }
    
    private void openResultScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/QuizResultScreenFXML.fxml"));

        try{
            Node node = fxmlLoader.load();
            QuizResultScreenFXMLController controller =  fxmlLoader.getController();
            controller.setValues(questionAnswer, questionList, quiz, nofRightAnswers);
            this.newScreenListner.removeTopScreen();
            this.newScreenListner.ChangeScreen(node);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
