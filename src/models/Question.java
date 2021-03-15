
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Question {
    private Quiz quiz;
    private int questionId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    public Question() {
    }

    public Question(Quiz quiz, int questionId, String question, String option1, String option2, String option3, String option4, String answer) {
        this.quiz = quiz;
        this.questionId = questionId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }
    
    

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return this.question;
    }
    
    
    
    public static void createTable() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "CREATE TABLE if NOT EXISTS question ( question_id INTEGER PRIMARY KEY AUTOINCREMENT, question VARCHAR(1000),\n" +
"                       option1 varchar(100), option2 varchar(100), option3 varchar(100),\n" +
"                       option4 varchar(100), answer varchar(100), quiz_id integer, FOREIGN KEY(quiz_id) \n" +
"                       REFERENCES quiz(quiz_id)   )";
 
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
    
    public boolean saveQuestion() {
        boolean flag = false;
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "INSERT INTO question(question,option1,option2,option3,option4,answer,quiz_id) values(?,?,?,?,?,?,?)";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, this.question);
            ps.setString(2, this.option1);
            ps.setString(3, this.option2);
            ps.setString(4, this.option3);
            ps.setString(5, this.option4);
            ps.setString(6, this.answer);
            ps.setInt(7, this.quiz.getQuizId());
            
            int i =  ps.executeUpdate();
            System.out.println(i+" rows updated...");
            
            conn.close();
            flag = true;
            
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
}
