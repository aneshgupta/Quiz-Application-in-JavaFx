
package start;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Test {
    static long hr, min, sec, totalTime = 100 * 60; //300
    
    public static void convertTime() {
        min = TimeUnit.SECONDS.toMinutes(totalTime);
        sec = totalTime - (min*60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr*60);
                
        System.out.println(format(hr)+" : "+format(min)+" : "+format(sec));   
        totalTime--;
    }
    
    public static String format(long value){
        if(value<10){
            return "0"+value;
        }
        else{
            return value+"";
        }
    }
    
    public static void main(String[] args) {
        
//        QuizResult qResult = new QuizResult(new Quiz(), new User(), 20);
//        System.out.println(qResult.saveResult());
//        System.out.println(qResult.getDateTime().getTime());
//        
//        QuizResultDetails quizResultDetails = new QuizResultDetails();
//        quizResultDetails.createTable();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("After 1 sec....");
                
                convertTime();
            }
        };
        
        timer.schedule(timerTask, 0, 1000);
    }
}
