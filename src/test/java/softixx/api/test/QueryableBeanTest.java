package softixx.api.test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import softixx.api.bean.DataBean;
import softixx.api.util.UValidator;

public class QueryableBeanTest {

	private static Map<String, DataBean> fields = new HashMap<>();
	
	public static void main(String[] args) {
		fields.put("field1", new DataBean("column1", "value1", String.class));
		fields.put("field2", new DataBean("column2", "value2", String.class));
		fields.put("field3", new DataBean("column3", "value3", String.class));
		fields.put("field4", new DataBean("column4", "value4", String.class));
		
		String query = "INSERT INTO table (<columns>) VALUES (<sentence>)";
		
		val columns = getInsertableColumns();
		if(!UValidator.isNull(columns)) {
			query = query.replaceAll("<columns>", columns);
		}
		
		val values = getInsertableValues();
		if(!UValidator.isNull(values)) {
			query = query.replaceAll("<sentence>", values);
		}
		
		System.out.println("query: " + query);
	}
	
	private static String getInsertableColumns() {
		return fields.keySet().stream()
							  .map(key -> key)
							  .collect(Collectors.joining(", "));
	}
	
	private static String getInsertableValues() {
		return fields.keySet().stream()
				  			  .map(key -> "?")
				  			  .collect(Collectors.joining(", "));
	}

}
