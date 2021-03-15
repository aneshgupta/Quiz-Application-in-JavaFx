
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import models.Question;


public class QuizResultScreenAnswersFXMLController implements Initializable {

    @FXML
    private Label question;
    @FXML
    private Label option1;
    @FXML
    private Label option2;
    @FXML
    private Label option3;
    @FXML
    private Label option4;
    
    private Question ques;
    private String userAnswer;
    public static int qIndex = 1;

    public void setValues(Question ques, String userAnswer) {
        this.ques = ques;
        this.userAnswer = userAnswer;
        setText();
    }
    

    public void setText() {
        this.question.setText("Q."+(this.qIndex++)+") "+this.ques.getQuestion());
        this.option1.setText(this.ques.getOption1());
        this.option2.setText(this.ques.getOption2());
        this.option3.setText(this.ques.getOption3());
        this.option4.setText(this.ques.getOption4());
        
        if(this.option1.getText().trim().equalsIgnoreCase(this.ques.getAnswer())){
            this.option1.setTextFill(Color.GREENYELLOW);
        }
        else if(this.option2.getText().trim().equalsIgnoreCase(this.ques.getAnswer())){
            this.option2.setTextFill(Color.GREENYELLOW);
        }
        else if(this.option3.getText().trim().equalsIgnoreCase(this.ques.getAnswer())){
            this.option3.setTextFill(Color.GREENYELLOW);
        }
        else if(this.option4.getText().trim().equalsIgnoreCase(this.ques.getAnswer())){
            this.option4.setTextFill(Color.GREENYELLOW);
        }
        
        if( this.userAnswer!=null && this.userAnswer.trim().equalsIgnoreCase(this.ques.getAnswer())){
            //Do-Nothing
        }
        else {
            //Mark red the worng answer
            if(this.option1.getText().trim().equalsIgnoreCase(this.userAnswer)){
            this.option1.setTextFill(Color.RED);
            }
            else if(this.option2.getText().trim().equalsIgnoreCase(this.userAnswer)){
                this.option2.setTextFill(Color.RED);
            }
            else if(this.option3.getText().trim().equalsIgnoreCase(this.userAnswer)){
                this.option3.setTextFill(Color.RED);
            }
            else if(this.option4.getText().trim().equalsIgnoreCase(this.userAnswer)){
                this.option4.setTextFill(Color.RED);
            }
        }
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
