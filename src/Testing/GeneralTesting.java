package Testing;
import java.util.Date;
import java.util.Timer;

public class GeneralTesting {

    public static void main(String [] args){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < 0.5*60*1000) {
            System.out.println("ciao");
            elapsedTime = (new Date()).getTime() - startTime;
        }


    }

}
