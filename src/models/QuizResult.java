package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class QuizResult {
    private int quizResultId;
    private Quiz quiz;
    private User user;
    private int rightAnswers;
    private Timestamp dateTime;
    
    {
        dateTime = new Timestamp(new Date().getTime());
    }

    public QuizResult() {
    }

    public QuizResult(Quiz quiz, User user, int rightAnswers) {
        this.quiz = quiz;
        this.user = user;
        this.rightAnswers = rightAnswers;
    }
        
    
    public int getQuizResultId() {
        return quizResultId;
    }

    public void setQuizResultId(int quizResultId) {
        this.quizResultId = quizResultId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "QuizResult{" + "quizResultId=" + quizResultId + ", quiz=" + quiz + ", user=" + user + ", rightAnswers=" + rightAnswers + ", dateTime=" + dateTime + '}';
    }
    
    
    public static void createTable(){
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "CREATE TABLE if not EXISTS quiz_results ( quiz_result_id integer PRIMARY key AUTOINCREMENT," +
                        " quiz_id integer not NULL, user_id integer not NULL," +
                            " right_answers integer not NULL, date_time timestamp not NULL," +
                                " FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id)," +
                                    " FOREIGN key (user_id) REFERENCES user(user_id) ) ";
        
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
    
    
    public boolean saveResult(Map<Question, String> userAnswers) {
        boolean flag = false;
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "INSERT INTO quiz_results (quiz_id,user_id,right_answers,date_time) "
                            + "VALUES ( ?,?,?,? ) " ;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, this.quiz.getQuizId());
            ps.setInt(2, this.user.getUserId());
            ps.setInt(3, this.rightAnswers);
            ps.setString(4, this.dateTime.toString());
            
            int i =  ps.executeUpdate(); 
            System.out.println(i+" rows updated...");
            
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                this.setQuizResultId(rs.getInt(1));
                System.out.println(this);
                QuizResultDetails quizResultDetails = new QuizResultDetails();
                quizResultDetails.setQuizResult(this);
                return quizResultDetails.saveQuizResultDetails(userAnswers);
                
            }
            
            conn.close();
            flag = true;
            
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static Map<QuizResult , Quiz> getQuizzes(User user){

        Map<QuizResult ,  Quiz > data = new HashMap<>();
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT quiz_results.quiz_result_id, quiz_results.right_answers, quiz.quiz_id, quiz.quiz_title FROM quiz_results JOIN quiz on quiz_results.quiz_id = quiz.quiz_id where user_id=?";

        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1 , user.getUserId());
            ResultSet result  =  ps.executeQuery();

            while (result.next()){
                QuizResult quizResult = new QuizResult();
                quizResult.setQuizResultId(result.getInt(1));
                quizResult.setRightAnswers(result.getInt(2));

                Quiz quiz = new Quiz();
                quiz.setQuizId(result.getInt(3));
                quiz.setQuizTitle(result.getString(4));

                data.put(quizResult , quiz);
            }


        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return data;
    }
    
    public Integer getNumberOfAttemptedQuestions() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT count(*) FROM quiz_result_details where quiz_result_id =?";
        
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, this.getQuizResultId());
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
    
}
