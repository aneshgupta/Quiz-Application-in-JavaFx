
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import models.Question;
import models.Quiz;
import org.json.JSONArray;
import org.json.JSONObject;


public class DataCollector {
    public static void main(String[] args) throws Exception{
        readAndSaveData();
    }
    
    public static void readAndSaveData() throws Exception {
        String folderPath = "src/util/sampleData";
        File folder = new File(folderPath);
        String[] fileNames = folder.list();
        
        for(String fileName : fileNames) {
            File file = new File(folderPath+"/"+fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder builder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            System.out.println(builder);


            JSONObject jSONObject = new JSONObject(builder.toString());
            JSONArray result = jSONObject.getJSONArray("results");
            Quiz quiz = new Quiz();
            List<Question> questions = new ArrayList<>();
            for(int i=0;i<result.length();i++){
                String obj = result.get(i).toString();
                JSONObject object = new JSONObject(obj);

                quiz.setQuizTitle(object.getString("category"));
                Question question = new Question();
                question.setQuestion(object.get("question").toString());
                JSONArray incorrectOptions = object.getJSONArray("incorrect_answers");
                question.setOption1(incorrectOptions.get(0).toString());
                question.setOption2(incorrectOptions.get(1).toString());
                question.setOption3(incorrectOptions.get(2).toString());
                question.setOption4(object.get("correct_answer").toString());
                question.setAnswer(object.get("correct_answer").toString());
                question.setQuiz(quiz);
                questions.add(question);

            }
            quiz.saveAll(questions);
        }
          
    }
    
}
