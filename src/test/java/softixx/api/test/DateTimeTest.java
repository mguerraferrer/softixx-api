package softixx.api.test;

import lombok.val;
import softixx.api.util.UDateTime;
import softixx.api.util.UDateTime.Formatter;

public class DateTimeTest {
	
    public static void main(String[] args) {
    	monthTest();
        currentTimestampTest();
    }

    private static void monthTest() {
    	System.out.println("=== monthTest ===");
    	
    	val monthName = "Marzo";
    	val month = UDateTime.month(monthName);
    	
    	System.out.println("monthTest: " + month.toString());
    	System.out.println("monthTest value: " + month.getValue());
    	System.out.println();
    	
    }
    
    private static void currentTimestampTest() {
    	System.out.println("=== currentTimestampTest ===");
    	
        var timestamp = UDateTime.currentTimestamp(Formatter.DATE_TIME_FORMAT);
        System.out.println("currentTimestampTest: " + timestamp);

        timestamp = UDateTime.currentTimestamp(Formatter.DATE_TIME_FULL_FORMAT);
        System.out.println("currentTimestampTest: " + timestamp);

        //##### This variant returns null because the only allowed formats are
        //##### Formatter.DATE_TIME_FORMAT and Formatter.DATE_TIME_FULL_FORMAT
        timestamp = UDateTime.currentTimestamp(Formatter.DATE_TIME_SIMPLE_FORMAT);
        System.out.println("currentTimestampTest: " + timestamp);
        
        System.out.println();
    }
}
