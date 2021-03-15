
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class AdminHomeScreenController implements Initializable {

    @FXML
    private TabPane adminTabPane;
    @FXML
    private Tab addUserTab;
    @FXML
    private Tab addQuizTab;
    @FXML
    private Tab viewQuizzesTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
             Parent addQuizNode = FXMLLoader.load(getClass().getResource("/fxml/AddQuizFXML.fxml"));
             addQuizTab.setContent(addQuizNode);
             
             Parent addUserNode = FXMLLoader.load(getClass().getResource("/fxml/AdminAddUserFXML.fxml"));
             addUserTab.setContent(addUserNode);
             
             Parent viewQuizzesNode = FXMLLoader.load(getClass().getResource("/fxml/viewQuizzesFXML.fxml"));
             viewQuizzesTab.setContent(viewQuizzesNode);
             
        } catch (Exception e) {
            e.printStackTrace();
                    
        }
        
       
        
    }    
    
}
