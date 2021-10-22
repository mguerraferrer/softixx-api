package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBean {
	protected String columnName;
	protected String columnNameAs;
	protected String paramName;
	protected Object sqlValue;
	protected Object dataValue;

	public DataBean(final String columnName, final Object sqlValue) {
		this.columnName = columnName;
		this.sqlValue = sqlValue;
	}
	
	public DataBean(final String columnName, final String columnNameAs, final Object sqlValue) {
		this.columnName = columnName;
		this.columnNameAs = columnNameAs;
		this.sqlValue = sqlValue;
	}

	public DataBean(final String columnName, final String paramName) {
		this.columnName = columnName;
		this.paramName = paramName;
	}
	
	public DataBean(final String columnName, final String columnNameAs, final String paramName) {
		this.columnName = columnName;
		this.columnNameAs = columnNameAs;
		this.paramName = paramName;
	}
	
	public DataBean(final String columnName, final Object sqlValue, final Object dataValue) {
		this.columnName = columnName;
		this.sqlValue = sqlValue;
		this.dataValue = dataValue;
	}
	
	public DataBean(final String columnName, final String columnNameAs, final Object sqlValue, final Object dataValue) {
		this.columnName = columnName;
		this.columnNameAs = columnNameAs;
		this.sqlValue = sqlValue;
		this.dataValue = dataValue;
	}

	public DataBean(final String columnName, final String columnNameAs, final String paramName, final Object dataValue) {
		this.columnName = columnName;
		this.columnNameAs = columnNameAs;
		this.paramName = paramName;
		this.dataValue = dataValue;
	}
	
}