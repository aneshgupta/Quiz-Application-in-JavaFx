
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Quiz {
    private int quizId;
    private String quizTitle;

    public Quiz() {
    }

    public Quiz(int quizId, String quizTitle) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
    }
    

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    @Override
    public String toString() {
        return this.quizTitle;
    }
    
    public static void createTable() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "CREATE TABLE if NOT EXISTS quiz(quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, quiz_title varchar(20))";
        
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
    
    public int saveTitle(){
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "INSERT INTO quiz(quiz_title) values(?)";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.quizTitle);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            while(keys.next()){
                return keys.getInt(1);
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }
    
    public boolean saveAll(List<Question> questions){
        boolean flag = true;
        this.quizId = this.saveTitle();
        for(Question ques : questions){
            flag =  ques.saveQuestion();
            System.out.println(flag);
        }
        
        return flag;
    }
    
    public static HashMap<Quiz, List<Question> > getAllQuizzes(){
        HashMap<Quiz, List<Question> > quizzes = new HashMap<>();
        Quiz key = new Quiz();
        List<Question> quesVal = null;
        
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT quiz.quiz_id, quiz_title, question_id, question, option1, option2, " +
                            "option3, option4, answer FROM quiz JOIN question " +
                                "ON quiz.quiz_id = question.quiz_id";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt(1));
                quiz.setQuizTitle(rs.getString(2));
                
                Question temp = new Question();
                temp.setQuestionId(rs.getInt(3));
                temp.setQuestion(rs.getString(4));
                temp.setOption1(rs.getString(5));
                temp.setOption2(rs.getString(6));
                temp.setOption3(rs.getString(7));
                temp.setOption4(rs.getString(8));
                temp.setAnswer(rs.getString(9));
                
                
                if(key!= null && key.equals(quiz)){
                    quizzes.get(key).add(temp);
                }
                else {
                    quesVal = new ArrayList<>();
                    quesVal.add(temp);
                    quizzes.put(quiz, quesVal);
                }
                
                key = quiz;
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return quizzes;
    }

    public static HashMap<Quiz, Integer> getAllQuizList(){
        HashMap<Quiz, Integer> quizzes = new HashMap<>();
        
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT quiz.quiz_id, quiz_title, COUNT(*) AS no_of_ques FROM quiz " +
                            "INNER JOIN question on quiz.quiz_id = question.quiz_id " +
                                "GROUP By quiz.quiz_id";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt(1));
                quiz.setQuizTitle(rs.getString(2));
                
                int NOQ = rs.getInt(3);
                
                quizzes.put(quiz, NOQ);
                
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return quizzes;
    }

    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();
        
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT * FROM question where quiz_id = ?";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, this.getQuizId());
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Question question = new Question();
                question.setQuestionId(rs.getInt(1));
                question.setQuestion(rs.getString(2));
                question.setOption1(rs.getString(3));
                question.setOption2(rs.getString(4));
                question.setOption3(rs.getString(5));
                question.setOption4(rs.getString(6));
                question.setAnswer(rs.getString(7));
                question.setQuiz(this);
                
                questions.add(question);
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return questions;
    }
    
    public Integer getNumberOfQuestions() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT count(*) from question WHERE quiz_id = ?";
        
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, this.getQuizId());
            ResultSet result = ps.executeQuery();

            while (result.next()) {
              return result.getInt(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }    
        else if(!(obj instanceof Quiz )) {
            return false;
        }    
        else{
        Quiz o = (Quiz)obj;
        if(this.quizId == o.quizId)
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.quizId;
        hash = 89 * hash + Objects.hashCode(this.quizTitle);
        return hash;
    }
    
}
