package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.val;
import softixx.api.enums.EConditional;
import softixx.api.enums.EOperator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataConditionalBean extends SqlDataBean {
	protected EOperator operator;
	protected EConditional conditional;
	protected String namedParam1;
	protected String namedParam2;
	protected Object param1Value;
	protected Object param2Value;
	
	public DataConditionalBean(DataBean bean, final EConditional conditional) {
		this.columnName = bean.getColumnName();
		this.conditional = conditional;
		this.sqlValue = bean.getSqlValue();
	}
	
	public DataConditionalBean(final EOperator operator, DataBean bean, final EConditional conditional) {
		this.operator = operator;
		this.columnName = bean.getColumnName();
		this.conditional = conditional;
		this.sqlValue = bean.getSqlValue();
	}
	
	public DataConditionalBean(final EOperator operator) {
		this.operator = operator;
	}

	public DataConditionalBean(final String columnName, final EConditional conditional) {
		this.columnName = columnName;
		this.conditional = conditional;
	}

	public DataConditionalBean(final String columnName, final EConditional conditional, final Object sqlValue) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.sqlValue = sqlValue;
	}
	
	public DataConditionalBean(final String columnName, final EConditional conditional, final Object sqlValue, final Integer sqlType) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.sqlValue = sqlValue;
		this.sqlType = sqlType;
	}
	
	public DataConditionalBean(final String columnName, final EConditional conditional, final Object sqlValue, final Object dataValue, final Integer sqlType) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.sqlValue = sqlValue;
		this.dataValue = dataValue;
		this.sqlType = sqlType;
	}
	
	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional, final Object sqlValue, final Integer sqlType) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
		this.sqlValue = sqlValue;
		this.sqlType = sqlType;
	}

	public DataConditionalBean(final String columnName, final EConditional conditional, final String namedParam1) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
	}

	public DataConditionalBean(final String columnName, final EConditional conditional, final String namedParam1,
			final String namedParam2) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
		this.namedParam2 = namedParam2;
	}

	public DataConditionalBean(final String columnName, final EConditional conditional, final String namedParam1,
			final String namedParam2, final Object param1Value, final Object param2Value) {
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
		this.namedParam2 = namedParam2;
		this.param1Value = param1Value;
		this.param2Value = param2Value;
	}

	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
	}

	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional,
			final Object sqlValue) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
		this.sqlValue = sqlValue;
	}

	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional,
			final String namedParam1) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
	}

	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional,
			final String namedParam1, final String namedParam2) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
		this.namedParam2 = namedParam2;
	}
	
	public DataConditionalBean(final EOperator operator, final String columnName, final EConditional conditional,
			final String namedParam1, final String namedParam2, final Object param1Value, final Object param2Value) {
		this.operator = operator;
		this.columnName = columnName;
		this.conditional = conditional;
		this.namedParam1 = namedParam1;
		this.namedParam2 = namedParam2;
		this.param1Value = param1Value;
		this.param2Value = param2Value;
	}

	@Override
	public String toString() {
		val sb = new StringBuilder();
		sb.append("softixx.api.bean.DataConditionalBean(");
		sb.append("operator=" + operator);
		sb.append(", columnName=" + columnName);
		sb.append(", paramName=" + paramName);
		sb.append(", conditional=" + conditional);
		sb.append(", namedParam1=" + namedParam1);
		sb.append(", param1Value=" + param1Value != null ? param1Value.toString() : null);
		sb.append(", namedParam2=" + namedParam2);
		sb.append(", param2Value=" + param2Value != null ? param2Value.toString() : null);
		sb.append(", sqlValue=" + sqlValue != null ? sqlValue.toString() : null);
		sb.append(", dataValue=" + dataValue != null ? dataValue.toString() : null);
		sb.append(")");
		return sb.toString();
	}
}
