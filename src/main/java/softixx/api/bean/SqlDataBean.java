package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SqlDataBean extends DataBean {
	// sqlType : java.sql.types -> Types.INTEGER, Types.String...
	protected Integer sqlType;

	public SqlDataBean(final String columnName, final Object sqlValue, final int sqlType) {
		this.columnName = columnName;
		this.sqlValue = sqlValue;
		this.sqlType = sqlType;
	}
	
	public SqlDataBean(final String columnName, final String paramName, final Object sqlValue, final int sqlType) {
		this.columnName = columnName;
		this.paramName = paramName;
		this.sqlValue = sqlValue;
		this.sqlType = sqlType;
	}
}
