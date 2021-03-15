
package controllers;

import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import models.Question;
import models.Quiz;


public class ViewQuizzesFXMLController implements Initializable {

    @FXML
    private JFXTreeView<?> quizzesTreeView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renderTreeView();
    }    

    public void renderTreeView() {
        HashMap<Quiz, List<Question> > quizData = Quiz.getAllQuizzes();
        Set<Quiz> quizzes = quizData.keySet();
        
        TreeItem root = new TreeItem("Quizzes");
        for(Quiz q : quizzes){
            TreeItem quizTreeItem = new TreeItem(q);
            root.getChildren().add(quizTreeItem);
            
            List<Question> questions = quizData.get(q);
            for(Question question : questions){
                TreeItem questionTreeItem = new TreeItem(question);
                questionTreeItem.getChildren().add(new TreeItem("A : " + question.getOption1()));
                questionTreeItem.getChildren().add(new TreeItem("B : " + question.getOption2()));
                questionTreeItem.getChildren().add(new TreeItem("C : " + question.getOption3()));
                questionTreeItem.getChildren().add(new TreeItem("D : " + question.getOption4()));
                questionTreeItem.getChildren().add(new TreeItem("Ans : " + question.getAnswer()));
                
                quizTreeItem.getChildren().add(questionTreeItem);
            }
        }
        root.setExpanded(true);
        this.quizzesTreeView.setRoot(root);
        
    }
    
}
