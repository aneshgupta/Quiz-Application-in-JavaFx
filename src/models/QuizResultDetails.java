
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class QuizResultDetails {
    private int qrdId;
    private QuizResult quizResult;
    private Question question;
    private String rightAnswer;

    public QuizResultDetails() {
    }

    public QuizResultDetails(QuizResult quizResult, Question question, String rightAnswer) {
        this.quizResult = quizResult;
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    public int getQrdId() {
        return qrdId;
    }

    public void setQrdId(int qrdId) {
        this.qrdId = qrdId;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(QuizResult quizResult) {
        this.quizResult = quizResult;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
    
    public static void createTable() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "CREATE TABLE if not EXISTS quiz_result_details ( qrd_id integer PRIMARY key AUTOINCREMENT, quiz_result_id integer not NULL, " +
"                                   question_id integer not NULL, right_answer varchar(1000) NOT NULL, " +
"                                   FOREIGN KEY (quiz_result_id) REFERENCES quiz_results(quiz_result_id), " +
"                                   FOREIGN key (question_id) REFERENCES question(question_id) )";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println(b);
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }  
    }
    
    public boolean saveQuizResultDetails(Map<Question, String > userAnswers) {
        boolean flag = false;
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "INSERT INTO quiz_result_details (quiz_result_id,question_id,right_answer) "
                            + "VALUES ( ?,?,? ) " ;

        Set<Question> questions = userAnswers.keySet();
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            
            for(Question q : questions){
                ps.setInt(1, this.getQuizResult().getQuizResultId());
                ps.setInt(2, q.getQuestionId());
                ps.setString(3, userAnswers.get(q));
                ps.addBatch();
                
            }
            int[] i =  ps.executeBatch(); 
            System.out.println(i.length+" rows updated...");
            if(i.length >0){
                flag = true;
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }    
                      
        return flag;
    } 
    
    
}
