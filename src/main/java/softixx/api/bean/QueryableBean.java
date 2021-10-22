package softixx.api.bean;

import java.io.Serializable;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import softixx.api.dao.GenericCrudDao;
import softixx.api.enums.EConditional;
import softixx.api.enums.EOperator;
import softixx.api.enums.EOrderBy;
import softixx.api.util.UValidator;
import softixx.api.util.UValue;

/**
 * Abstract bean thats provide properties inheritable and methods used by
 * {@link GenericCrudDao}
 * 
 * @see GenericCrudDao
 * @author Maikel Guerra Ferrer
 */
@Getter
@NoArgsConstructor
public abstract class QueryableBean implements Serializable {
	private static final long serialVersionUID = 6882585375521622761L;
	
	public static final String QUERY_EXISTS = "SELECT id FROM --TABLE-- WHERE --WHERE--";
	public static final String QUERY_COUNT = "SELECT COUNT(id) FROM --TABLE--";
	public static final String QUERY_COUNT_WHERE = "SELECT COUNT(id) FROM --TABLE-- WHERE --WHERE--";
	public static final String QUERY_COUNT_WHERE_JOIN = "SELECT COUNT(--TABLE--.id) FROM --TABLE-- --INNER JOIN-- WHERE --WHERE--";
	public static final String QUERY_FIND_BY_ID = "SELECT * FROM --TABLE-- WHERE id = ?";
	public static final String QUERY_FIND_BY_ID_JOIN = "SELECT * FROM --TABLE-- --INNER JOIN-- WHERE --TABLE--.id = ?";
	public static final String QUERY_FIND_LAST = "SELECT MAX(id) FROM --TABLE--";
	public static final String QUERY_ALL = "SELECT * FROM --TABLE--";
	public static final String QUERY_ALL_ORDER_BY = "SELECT * FROM --TABLE-- --ORDER BY--";
	/*LIMIT*/
	public static final String QUERY_LIMIT = "SELECT --COLUMNS-- FROM --TABLE-- --LIMIT--";
	public static final String QUERY_JOIN_LIMIT = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- --LIMIT--";
	/*WHERE*/
	public static final String QUERY_WHERE = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --WHERE--";
	public static final String QUERY_WHERE_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --WHERE-- --ORDER BY--";
	public static final String QUERY_WHERE_JOIN = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --WHERE--";
	public static final String QUERY_WHERE_JOIN_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --WHERE-- --ORDER BY--";
	public static final String QUERY_WHERE_JOIN_PAGINATE = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --WHERE-- --ORDER BY-- --LIMIT-- --OFFSET--";
	public static final String QUERY_WHERE_PAGINATE = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --WHERE-- --ORDER BY-- --LIMIT-- --OFFSET--";
	public static final String QUERY_WHERE_LIMIT = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --WHERE-- --LIMIT--";
	/*SENTENCE*/
	public static final String QUERY_SENTENCE = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --SENTENCE--";
	public static final String QUERY_SENTENCE_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --SENTENCE-- --ORDER BY--";
	public static final String QUERY_SENTENCE_JOIN = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --SENTENCE--";
	public static final String QUERY_SENTENCE_JOIN_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --SENTENCE-- --ORDER BY--";
	public static final String QUERY_SENTENCE_PAGINATE = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --SENTENCE-- --ORDER BY-- --LIMIT-- --OFFSET--";
	/*CONDITIONAL*/
	public static final String QUERY_CONDITIONAL = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --CONDITIONAL--";
	public static final String QUERY_CONDITIONAL_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- WHERE --CONDITIONAL-- --ORDER BY--";
	public static final String QUERY_CONDITIONAL_JOIN = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --CONDITIONAL--";
	public static final String QUERY_CONDITIONAL_JOIN_ORDER_BY = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --CONDITIONAL-- --ORDER BY--";
	public static final String QUERY_CONDITIONAL_PAGINATE = "SELECT --COLUMNS-- FROM --TABLE-- --INNER JOIN-- WHERE --CONDITIONAL-- --ORDER BY-- --LIMIT-- --OFFSET--";
	/*INSERT*/
	public static final String QUERY_INSERT = "INSERT INTO --TABLE-- (--COLUMNS--) VALUES (--SENTENCE--)";
	/*UPDATE*/
	public static final String QUERY_UPDATE = "UPDATE --TABLE-- SET --COLUMNS-- WHERE id = ?";
	public static final String QUERY_UPDATE_CONDITIONAL = "UPDATE --TABLE-- SET --COLUMNS-- WHERE --CONDITIONAL--";
	/*DELETE*/
	public static final String QUERY_DELETE = "DELETE FROM --TABLE-- WHERE id = ?";
	public static final String QUERY_DELETE_CONDITIONAL = "DELETE FROM --TABLE-- WHERE --CONDITIONAL--";
	
	public static final String REPLACE_ALL = "*";
	public static final String REPLACE_TABLE = "--TABLE--";
	public static final String REPLACE_COLUMNS = "--COLUMNS--";
	public static final String REPLACE_INNER_JOIN = "--INNER JOIN--";
	public static final String REPLACE_WHERE = "--WHERE--";
	public static final String REPLACE_SENTENCE = "--SENTENCE--";
	public static final String REPLACE_CONDITIONAL = "--CONDITIONAL--";
	public static final String REPLACE_ORDER_BY = "--ORDER BY--";
	public static final String REPLACE_LIMIT = "--LIMIT--";
	public static final String REPLACE_OFFSET = "--OFFSET--";

	protected String query;
	protected String countQuery;
	protected String table;
	protected String idColumnName;
	protected Object beanId;
	protected Object beanValue;
	protected Object[] args;
	protected List<String> columns = new ArrayList<>();
	protected List<String> columnsReplace = new ArrayList<>();
	protected Map<String, DataBean> fields = new LinkedHashMap<>();
	protected List<DataJoinBean> joins = new ArrayList<>();
	protected String join;
	protected List<DataConditionalBean> conditionals = new ArrayList<>();
	protected Map<String, DataBean> conditionalsData = new LinkedHashMap<>();
	protected MapSqlParameterSource namedParams = new MapSqlParameterSource();
	protected List<DataOrderByBean> orders = new ArrayList<>();

	/**
	 * Returns the names of the columns <b><i>(separated by comma)</i></b> used in
	 * the <b><i>SQL SELECT</i></b> clause.
	 * 
	 * @return The names of the columns <b><i>(separated by comma)</i></b>
	 */
	public String getSelectableColumns() {
		return columns.stream()
					  .reduce((x, y) -> String.join(", ", x, y))
					  .orElse("");
	}
	
	public String getSelectableReplaceColumns() {
		return columnsReplace.stream()
					  		 .reduce((x, y) -> String.join(", ", x, y))
					  		 .orElse("");
	}

	public String getColumnNames(final Set<String> keys) {
		return keys.stream()
				   .reduce((x, y) -> String.join(", ", x, y))
				   .orElse("");
	}

	public String getInsertableColumns() {
		return fields.keySet().stream()
							  .reduce((x, y) -> String.join(", ", x, y))
							  .orElse("");
	}
	
	public String getInsertableSentence() {
		String sentence = null;
		if(fields != null && !fields.keySet().isEmpty()) {
			sentence = fields.keySet().stream()
					  		 .map(key -> "?")
					  		 .collect(Collectors.joining(", "));
		} else if(namedParams != null && !namedParams.getValues().isEmpty()) {
			sentence = namedParams.getValues().entrySet().stream()
														 .filter(item -> item.getKey() != null && item.getValue() != null)
														 .map(item -> " :" + item.getKey())
														 .reduce((x, y) -> String.join(", ", x, y))
														 .orElse("");
		}
		return sentence;
	}
	
	public String getInsertableNamedParamSentence() {
		return fields.values().stream()
							  .map(e -> " :" + e.getParamName())
							  .reduce((x, y) -> String.join(", ", x, y))
							  .orElse("");
	}
	
	public String getUpdatableColumns() {
		return fields.keySet().stream()
							  .map(key -> key + " = ?")
							  .reduce((x, y) -> String.join(", ", x, y))
							  .orElse("");
	}
	
	public String getUpdatableNamedParamColumns() {
		return fields.values().stream()
							  .map(e -> e.getColumnName() + " = :" + e.getParamName())
							  .reduce((x, y) -> String.join(", ", x, y))
							  .orElse("");
	}
	
	public List<Object> getSqlValues() {
		return fields.values().stream()
							  .map(DataBean::getSqlValue)
							  .collect(Collectors.toList());
	}

	public String getJoinTables() {
		return joins.stream().map(item -> item.getTypeJoin() + " " + item.getTableJoin() + " ON " + item.getTableJoinOn() + " = " + item.getTableJoinColumn())
							 .reduce((x, y) -> String.join(" ", x, y))
							 .orElse("");
	}
	
	public String getConditionals() {
		String condition = "";
		for (val item : conditionals) {
			var newCondition = "";
			if (!UValidator.isNull(item.getOperator())) {
				newCondition = newCondition.concat(item.getOperator().value());
			}

			if (UValidator.isNotEmpty(item.getColumnName())) {
				newCondition = newCondition.concat(item.getColumnName());
			}

			if (!UValidator.isNull(item.getConditional())) {
				if (UValidator.isNotEmpty(item.getNamedParam1()) && UValidator.isNotEmpty(item.getNamedParam2())) {
					newCondition = newCondition.concat(item.getConditional().value())
											   .concat(item.getNamedParam1())
											   .concat(" AND :")
											   .concat(item.getNamedParam2());
				} else if (UValidator.isNotEmpty(item.getNamedParam1())) {
					newCondition = newCondition.concat(item.getConditional().value())
											   .concat(item.getNamedParam1());
				} else {
					newCondition = newCondition.concat(item.getConditional().value());
				}
			}

			condition = condition.concat(newCondition);
		}
		return condition;
	}
	
	public String getWhere() {
		String where = "";
		for (val item : conditionals) {
			var newCondition = "";
			if (!UValidator.isNull(item.getOperator())) {
				newCondition = newCondition.concat(item.getOperator().value());
			}

			if (UValidator.isNotEmpty(item.getColumnName())) {
				newCondition = newCondition.concat(item.getColumnName());
			}

			if (!UValidator.isNull(item.getConditional())) {
				if (UValidator.isNotEmpty(item.getNamedParam1()) && UValidator.isNotEmpty(item.getNamedParam2())) {
					newCondition = newCondition.concat(item.getConditional().value())
											   .concat(item.getParam1Value().toString())
											   .concat(" AND ")
											   .concat(item.getParam2Value().toString());
				} else if (UValidator.isNotEmpty(item.getNamedParam1())) {
					newCondition = newCondition.concat(item.getConditional().value())
											   .concat(item.getParam1Value().toString());
				} else {
					var value = UValue.NULL_SQL;
					if(UValidator.isNotNull(item.getSqlValue())) {
						if(UValidator.isNotNull(item.getSqlType())) {
							value = switch (item.getSqlType()) {
										case 1, 12, -1, 91, 92, 93 -> (!item.getSqlValue().toString().startsWith("lower"))
												? "\'" + item.getSqlValue() + "\'"
												: item.getSqlValue().toString();
										default -> item.getSqlValue().toString();
									};
						} else {
							value = item.getSqlValue().toString();
						}
						newCondition = newCondition.concat(item.getConditional().value())
								   				   .concat(value);
					} else {
						newCondition = newCondition.concat(item.getConditional().value());
					}
				}
			}

			where = where.concat(newCondition);
		}
		return where;
	}

	public List<Object> getConditionalsData() {
		return conditionals.stream()
						   .filter(item -> item.getSqlValue() != null)
						   .map(DataBean::getSqlValue)
						   .collect(Collectors.toList());
	}
	
	public List<Object> getConditionalsDataValues() {
		return conditionals.stream()
						   .filter(item -> item.getDataValue() != null)
						   .map(DataBean::getDataValue)
						   .collect(Collectors.toList());
	}

	public String getOrderBy() {
		return orders.stream()
					 .map(item -> "ORDER BY " + item.getColumnName() + " " + item.getDirection().name())
					 .reduce((x, y) -> String.join(", ", x, y))
					 .orElse("");
	}

	public String getOrderBy(final Pageable pageable) {
		if (pageable == null || pageable.getSort().isEmpty()) {
			return "ORDER BY id ASC";
		}

		final String orderBy = pageable.getSort().stream()
												 .map(item -> item.getProperty() + " " + item.getDirection().name())
												 .collect(Collectors.joining(","));
		return orderBy;
	}
	
	public void addQuery(final String squery) {
		query = squery;
	}
	
	public void addCountQuery(final String cquery) {
		countQuery = cquery;
	}
	
	public void addTable(final String tableName) {
		table = tableName;
	}
	
	public void addColumns(final List<String> columnsName) {
		columns = new ArrayList<>();
		columns.addAll(columnsName);
	}
	
	public void addColumn(final String columnName) {
		if(columns == null) {
			columns = new ArrayList<>();
		}
		columns.add(columnName);
	}
	
	public void addColumnsReplace(final List<String> columnsName) {
		columnsReplace = new ArrayList<>();
		columnsReplace.addAll(columnsName);
	}
	
	public void addColumnReplace(final String columnName) {
		if(columnsReplace == null) {
			columnsReplace = new ArrayList<>();
		}
		columnsReplace.add(columnName);
	}
	
	public void addIdColumnName(final String idColName) {
		idColumnName = idColName;
	}
	
	public void addBeanId(final Object bId) {
		beanId = bId;
	}
	
	public void addBeanValue(final Object bValue) {
		beanValue = bValue;
	}
	
	public void addArgs(final Object[] objArgs) {
		args = objArgs;
	}

	public void addData(final String columnName) {
		fields.put(columnName, new DataBean(columnName, ""));
	}
	
	public void addData(final String columnName, final String paramName, final Object value) {
		fields.put(columnName, new DataBean(columnName, paramName, value));
	}

	public void addData(final String columnName, final Object value) {
		fields.put(columnName, new DataBean(columnName, value));
	}
	
	public void addData(final String columnName, final String paramName) {
		fields.put(columnName, new DataBean(columnName, paramName));
	}
	
	public void addData(final List<DataBean> data) {
		data.forEach(item -> {
			this.addData(item.getColumnName(), item.getSqlValue());
		});
	}
	
	public void addTableJoin(final String tableJoin, final String tableJoinOn, final String tableJoinColumn) {
		joins.add(new DataJoinBean(tableJoin, tableJoinOn, tableJoinColumn));
	}
	
	public void addTableJoin(final String typeJoin, final String tableJoin, final String tableJoinOn, final String tableJoinColumn) {
		joins.add(new DataJoinBean(typeJoin, tableJoin, tableJoinOn, tableJoinColumn));
	}
	
	public void addJoin(final String join) {
		this.join = join;
	}

	public void addConditional(final List<DataConditionalBean> dataConditionals) {
		conditionals.addAll(dataConditionals);
	}
	
	public void addConditional(final EOperator operator) {
		conditionals.add(new DataConditionalBean(operator));
	}

	public void addConditional(final String columnName, final EConditional conditional) {
		conditionals.add(new DataConditionalBean(columnName, conditional));
	}

	public void addConditional(final String columnName, final EConditional conditional, String namedParam) {
		conditionals.add(new DataConditionalBean(columnName, conditional, namedParam));
	}

	public void addConditional(final String columnName, final EConditional conditional, String namedParam1,
			String namedParam2) {
		conditionals.add(new DataConditionalBean(columnName, conditional, namedParam1, namedParam2));
	}

	public void addConditional(final EOperator operator, final String columnName, final EConditional conditional) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional));
	}

	public void addConditional(final EOperator operator, final String columnName, final EConditional conditional,
			String namedParam) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional, namedParam));
	}

	public void addConditional(final EOperator operator, final String columnName, final EConditional conditional,
			String namedParam1, String namedParam2) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional, namedParam1, namedParam2));
	}

	public void addConditionalData(final EOperator operator, final String columnName, final EConditional conditional,
			final Object value) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional, value));
	}

	public void addConditionalData(final EOperator operator, final String columnName, final EConditional conditional, final Object value, final Integer sqlType) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional, value, sqlType));
	}
	
	public void addConditionalData(final String columnName, final EConditional conditional, final Object value) {
		conditionals.add(new DataConditionalBean(columnName, conditional, value));
	}
	
	public void addConditionalData(final String columnName, final EConditional conditional, final Object value, final Integer sqlType) {
		conditionals.add(new DataConditionalBean(columnName, conditional, value, sqlType));
	}
	
	public void addConditionalData(final String columnName, final EConditional conditional, final Object sqlValue, final Object dataValue, final Integer sqlType) {
		conditionals.add(new DataConditionalBean(columnName, conditional, sqlValue, dataValue, sqlType));
	}
	
	public void addConditionalData(final EOperator operator, final String columnName, final EConditional conditional) {
		conditionals.add(new DataConditionalBean(operator, columnName, conditional, null, Types.NULL));
	}
	
	public void addConditionalData(DataSearchBean dsb) {
		dsb.getData().forEach(data -> {
    		if(UValidator.isNotNull(data.getOperator())) {
    			addConditionalData(data.getOperator(), data.getColumnName(), data.getConditional(), data.getSqlValue(), data.getSqlType());
    		} else {
    			addConditionalData(data.getColumnName(), data.getConditional(), data.getSqlValue(), data.getSqlType());
    		}
    	});
	}
	
	public void addOrderBy(final String columnName) {
		orders.add(new DataOrderByBean(columnName, EOrderBy.ASC));
	}
	
	public void addOrderBy(final String columnName, final EOrderBy direction) {
		orders.add(new DataOrderByBean(columnName, direction));
	}
	
	public void addOrderBy(final DataOrderByBean bean) {
		orders.add(bean);
	}
	
	public void addOrderBy(DataSearchBean dsb) {
		dsb.getOrderByList().forEach(data -> {
			addOrderBy(data.getColumnName(), data.getDirection());
		});
		
		val singleOrderBy = dsb.getOrderBy();
		if(UValidator.isNotNull(singleOrderBy)) {
			addOrderBy(singleOrderBy.getColumnName(), singleOrderBy.getDirection());
		}
	}
}