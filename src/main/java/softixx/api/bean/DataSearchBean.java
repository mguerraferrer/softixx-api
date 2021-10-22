package softixx.api.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import softixx.api.enums.EConditional;
import softixx.api.enums.EOperator;

@Data
@NoArgsConstructor
public class DataSearchBean {
	protected List<DataSearch> data;
	protected DataOrderByBean orderBy;
	@Getter(AccessLevel.NONE)
	protected List<DataOrderByBean> orderByList;
	protected DataLimitBean dataLimitBean;

	@Data
	@NoArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	public static class DataSearch extends DataConditionalBean {
		public DataSearch(final EOperator operator) {
			super(operator);
		}
		
		public DataSearch(final EOperator operator, final String columnName, final EConditional conditional) {
			this.operator = operator;
			this.columnName = columnName;
			this.conditional = conditional;
		}
		
		public DataSearch(final String columnName, final EConditional conditional, final Object sqlValue) {
			this.columnName = columnName;
			this.conditional = conditional;
			this.sqlValue = sqlValue;
		}

		public DataSearch(final EOperator operator, final String columnName, final EConditional conditional,
				final Object sqlValue) {
			this.operator = operator;
			this.columnName = columnName;
			this.conditional = conditional;
			this.sqlValue = sqlValue;
		}
		
		public DataSearch(final String columnName, final EConditional conditional,
				final Object sqlValue, final Integer sqlType) {
			this.columnName = columnName;
			this.conditional = conditional;
			this.sqlValue = sqlValue;
			this.sqlType = sqlType;
		}
		
		public DataSearch(final EOperator operator, final String columnName, final EConditional conditional,
				final Object sqlValue, final Integer sqlType) {
			this.operator = operator;
			this.columnName = columnName;
			this.conditional = conditional;
			this.sqlValue = sqlValue;
			this.sqlType = sqlType;
		}
	}
	
	public List<DataOrderByBean> getOrderByList() {
		if(orderByList == null) {
			orderByList = new ArrayList<>();
		}
		return this.orderByList;
	}
	
}
