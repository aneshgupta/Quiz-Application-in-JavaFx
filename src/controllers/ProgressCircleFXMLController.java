
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ProgressCircleFXMLController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private Label quesNumber;

    public void setQuesNumber(Integer quesNumber) {
        this.quesNumber.setText(quesNumber.toString());
    }
    
    public void setDefaultColor() {
        this.circle.setFill(Color.valueOf("#DAE0E2"));
        this.quesNumber.setTextFill(Color.BLACK);
    }
   
    public void setCurrentColor() {
        this.circle.setFill(Color.valueOf("#4BCFFA"));
        this.quesNumber.setTextFill(Color.WHITE);
    }
    public void setRightAnswerColor() {
        this.circle.setFill(Color.valueOf("#2ecc72"));
        this.quesNumber.setTextFill(Color.WHITE);
    }
    
    public void setWrongAnswerColor() {
        this.circle.setFill(Color.valueOf("#E44236"));
        this.quesNumber.setTextFill(Color.WHITE);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
