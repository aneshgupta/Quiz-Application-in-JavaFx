
package controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import models.Question;
import models.Quiz;


public class QuizResultScreenFXMLController implements Initializable {

    @FXML
    private PieChart totalQuestionsPie;
    @FXML
    private PieChart attemptedQuestionsPie;
    @FXML
    private VBox questionsContiner;
    
    private Map<Question, String> userAnswers;
    private List<Question> questionsList;
    private Quiz quiz;
    private int nofRightAnswers = 0;
    private int attemptedQuestions = 0;
    private int notAttemptedQuestions = 0;
    

    public void setValues(Map<Question, String> userAnswers, List<Question> questionsList, 
            Quiz quiz, int nofRightAnswers) {
        this.userAnswers = userAnswers;
        this.questionsList = questionsList;
        this.quiz = quiz;
        this.nofRightAnswers = nofRightAnswers;
        
        this.attemptedQuestions = userAnswers.keySet().size();
        this.notAttemptedQuestions = this.questionsList.size() - attemptedQuestions;
        renderPieChart();
        setAnswers();
        
    }
    
    private void renderPieChart() {
        ObservableList<PieChart.Data> totalData =  this.totalQuestionsPie.getData();
        totalData.add(new PieChart.Data(String.format("Attempted(%d)", this.attemptedQuestions),
                this.attemptedQuestions));
        totalData.add(new PieChart.Data(String.format("Not Attempted(%d)", this.notAttemptedQuestions),
                this.notAttemptedQuestions));
        
        ObservableList<PieChart.Data> attemptedData = this.attemptedQuestionsPie.getData();
        attemptedData.add(new PieChart.Data(String.format("RightAnswers(%d)", this.nofRightAnswers),
                this.nofRightAnswers));
        attemptedData.add(new PieChart.Data(String.format("WrongAnswers(%d)", this.attemptedQuestions - this.nofRightAnswers),
                this.attemptedQuestions - this.nofRightAnswers));

        
    }
    
    private void setAnswers() {
        for(Question ques : questionsList){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/QuizResultScreenAnswersFXML.fxml"));

            try{
                Node node = fxmlLoader.load();
                QuizResultScreenAnswersFXMLController controller =  fxmlLoader.getController();
                controller.setValues(ques, userAnswers.get(ques));
                this.questionsContiner.getChildren().add(node);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        QuizResultScreenAnswersFXMLController.qIndex = 1;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
