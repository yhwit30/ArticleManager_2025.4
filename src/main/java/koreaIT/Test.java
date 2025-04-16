package koreaIT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void test(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formatedNow = now.format(formatter);
        System.out.println(formatedNow);



    }

}
