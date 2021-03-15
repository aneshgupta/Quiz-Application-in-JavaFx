package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import listners.AttempedQuizItemClickListener;
import models.Quiz;
import models.QuizResult;
import models.User;


public class UserAttemptedQuizScreenFXMLController implements Initializable {

    @FXML
    private VBox list;
    @FXML
    private Label total;
    @FXML
    private Label Aq;
    @FXML
    private Label ra;
    @FXML
    private Label wa;
    @FXML
    private PieChart attempedChart;
    @FXML
    private PieChart rightWrongChart;
    
    private static User user;

    public void setUser(User user) {
        this.user = user;
        
        setList();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    
    
    public void setList(){
        Map<QuizResult, Quiz> map = QuizResult.getQuizzes(user);
        Set<QuizResult> quizzesData = map.keySet();

        for(QuizResult quizResult : quizzesData){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/UserAttemptedQuizItemFXML.fxml"));
            try {
                Parent root = fxmlLoader.load();
                UserAttemptedQuizItemFXMLController attempedQuizListItemFXML = fxmlLoader.getController();
            attempedQuizListItemFXML.setItemClickListener(new AttempedQuizItemClickListener() {
                @Override
                public void itemClicked(Integer nof, Integer noa) {

                    int attemped = noa;
                    int nonAtemped = nof - attemped ;
                    int right = quizResult.getRightAnswers() ;
                    int wrong = attemped - right ;

                    total.setText(nof.toString());
                    Aq.setText(attemped + "");
                    ra.setText(right+"");
                    wa.setText((noa - quizResult.getRightAnswers()) + "");


                    ObservableList<PieChart.Data> attempedData  = attempedChart.getData();
                    attempedData.clear();
                    attempedData.add(new PieChart.Data("Attemped Questions ("+attemped+")" , attemped));
                    attempedData.add(new PieChart.Data("Not Attemped Questions ("+nonAtemped+")" , nonAtemped));


                    ObservableList<PieChart.Data> answerData  = rightWrongChart.getData();
                    answerData.clear();
                    answerData.add(new PieChart.Data("Right Answers ("+right+")" , right));
                    answerData.add(new PieChart.Data("Wrong Answers ("+wrong+")" , wrong));


                }
            });
                attempedQuizListItemFXML.setData(map.get(quizResult) , quizResult);
                this.list.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
}
