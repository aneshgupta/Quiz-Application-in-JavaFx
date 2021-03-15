package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Question;
import models.Quiz;
import models.QuizResult;
import models.QuizResultDetails;
import models.User;

public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        createTables();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void createTables() {
        Quiz.createTable();
        Question.createTable();
        User.createTable();
        QuizResult.createTable();
        QuizResultDetails.createTable();
        
    }
    
}
