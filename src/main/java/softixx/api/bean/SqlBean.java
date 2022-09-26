package softixx.api.bean;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import softixx.api.util.UValidator;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Extension of {@link CommonBean} to provide additional properties and methods
 * to the {@link CommonBean} objects. <br>
 * Uses {@link Pageable}, {@link SqlParameter} and {@link RowMapper}
 *
 * @param <T>  Object type
 * @param <ID> Object id type
 * @see CommonBean
 * @see Pageable
 * @see SqlParameter
 * @see RowMapper
 * @author Maikel Guerra Ferrer
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SqlBean<T, ID> extends CommonBean<T, ID> implements Serializable {
	private static final long serialVersionUID = 2301393501919868946L;
	
	protected Pageable pageable;
	protected DataLimitBean limitValue;
	protected List<SqlParameter> declaredParameters = new ArrayList<>();
	protected List<Object> parameters = new ArrayList<>();
	@Getter(AccessLevel.NONE)
	protected RowMapper<T> rowMapper;
	protected RowMapper<T> rowMapperId;
	protected RowMapper<T> rowMapperCustom;
	
	/**
	 * Set it to true to force replace --SENTENCE-- in {@link QueryableBean#QUERY_INSERT} query
	 */
	protected boolean forceInsertSentence = false;
	/**
	 * Set it to true to indicate that this is an update operation
	 */
	protected boolean updateOperation = false;
	/**
	 * Set it to false if you don't want to sanitize the query
	 */
	protected boolean sanitizeQuery = true;
	/**
	 * Set it to true if you want to use column names instead of * in findOne query
	 */
	protected boolean useColumnsOnFindOneQuery = false;

	private void sqlBeanInstance() {
		query = null;
		countQuery = null;
		table = null;
		idColumnName = null;
		idClazz = null;
		beanId = null;
		beanValue = null;
		rowMapper = null;
		rowMapperId = null;
		rowMapperCustom = null;
		args = null;
		columns = new ArrayList<>();
		fields = new LinkedHashMap<>();
		joins = new ArrayList<>();
		conditionals = new ArrayList<>();
		conditionalsData = new LinkedHashMap<>();
		namedParams = new MapSqlParameterSource();
		orders = new ArrayList<>();
		declaredParameters = new ArrayList<>();
		parameters = new ArrayList<>();
		namedParams = new MapSqlParameterSource();
		limitValue = new DataLimitBean();
		pageable = PageRequest.of(limitValue.offset, limitValue.limit);
		forceInsertSentence = false;
		updateOperation = false;
		sanitizeQuery = true;
		useColumnsOnFindOneQuery = false;
	}

	public void sqlBeanInstance(final String tableName, final String idColumnName, final Class<ID> idClazz,
			final Class<T> clazz, final RowMapper<T> rowMapper) {
		this.sqlBeanInstance();

		addTable(tableName);
		addIdColumnName(idColumnName);
		addIdClazz(idClazz);
		addClazz(clazz);
		addRowMapper(rowMapper);
	}
	
	public void sqlBeanInstanceId(final String tableName, final String idColumnName, final Class<ID> idClazz,
			final Class<T> clazz, final RowMapper<T> rowMapperId) {
		this.sqlBeanInstance();

		addTable(tableName);
		addIdColumnName(idColumnName);
		addIdClazz(idClazz);
		addClazz(clazz);
		addRowMapperId(rowMapperId);
	}
	
	public void sqlBeanInstanceCustom(final String tableName, final String idColumnName, final Class<ID> idClazz,
			final Class<T> clazz, final RowMapper<T> rowMapperCustom) {
		this.sqlBeanInstance();

		addTable(tableName);
		addIdColumnName(idColumnName);
		addIdClazz(idClazz);
		addClazz(clazz);
		addRowMapperCustom(rowMapperCustom);
	}
	
	public RowMapper<T> getRowMapper() {
		if (this.rowMapper == null) {
			if (this.rowMapperId != null) {
				this.rowMapper = this.rowMapperId;
			} else if (this.rowMapperCustom != null) {
				this.rowMapper = this.rowMapperCustom;
			}
		}
		return this.rowMapper;
	}
	
	public void addRowMapper(final RowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
		this.rowMapperId = null;
		this.rowMapperCustom = null;
	}
	
	public void addRowMapperId(final RowMapper<T> rowMapperId) {
		this.rowMapperId = rowMapperId;
		this.rowMapper = null;
		this.rowMapperCustom = null;
	}
	
	public void addRowMapperCustom(final RowMapper<T> rowMapperCustom) {
		this.rowMapperCustom = rowMapperCustom;
		this.rowMapperId = null;
		this.rowMapper = null;
	}
	
	public void addPageable(final Pageable pageable) {
		this.pageable = pageable;
	}

	public List<SqlParameter> getDeclaredParameters() {
		return declaredParameters;
	}

	public List<Object> getParameters() {
		return parameters;
	}
	
	public String getOffset() {
		if (limitValue == null) {
			limitValue = new DataLimitBean();
		}
		return "OFFSET " + limitValue.getOffset();
	}
	
	public String getLimit() {
		if (limitValue == null) {
			limitValue = new DataLimitBean();
		}
		return "LIMIT " + limitValue.getLimit();
	}
	
	public Integer getPage() {
		if (limitValue == null) {
			limitValue = new DataLimitBean();
		}
		return limitValue.getPage();
	}
	
	public Integer getPageSize() {
		if (limitValue == null) {
			limitValue = new DataLimitBean();
		}
		return limitValue.getLimit();
	}
	
	public String getInsertForceSentence() {
		var sentence = "";
		
		if (!declaredParameters.isEmpty()) {
			sentence = declaredParameters.stream()
							  			 .map(item -> "?")
							  			 .reduce((x, y) -> String.join(", ", x, y))
							  			 .orElse("");
		}
		
		if (UValidator.isEmpty(sentence) && namedParams != null && !namedParams.getValues().isEmpty()) {
			sentence = namedParams.getValues().entrySet().stream()
														 .filter(item -> item.getKey() != null && item.getValue() != null)
														 .map(item -> " :" + item.getKey())
														 .reduce((x, y) -> String.join(", ", x, y))
														 .orElse("");
		}
		
		return sentence;
	}
	
	public void addDeclaredParameter(final SqlDataBean bean) {
		val sqlParameter = new SqlParameter(bean.getSqlType(), bean.getColumnName());
		declaredParameters.add(sqlParameter);
		parameters.add(bean.getSqlValue());
	}

	public void addDeclaredParameters(final List<SqlDataBean> source) {
		declaredParameters = source.stream()
								   .map(bean -> new SqlParameter(bean.getSqlType(), bean.getColumnName()))
								   .collect(Collectors.toList());
		
		parameters = source.stream()
						   .map(DataBean::getSqlValue)
						   .collect(Collectors.toList());
	}
	
	public void addParams(final List<DataBean> source) {
		source.forEach(item -> {
			this.addData(item.getColumnName(), item.getParamName());
		});
		
		parameters = source.stream()
						   .map(DataBean::getSqlValue)
						   .collect(Collectors.toList());
	}

	public void addNamedParam(final String paramName, final Object value) {
		namedParams.addValue(paramName, value);
	}
	
	public void addNamedParams(final List<DataBean> source) {
		source.forEach(item -> {
			this.addData(item.getColumnName(), item.getParamName());
			namedParams.addValue(item.getParamName(), item.getSqlValue());
		});
		
	}

	public void addLimit(final int limit) {
		limitValue = new DataLimitBean(limit);
	}
	
	public void addLimit(final int offset, final int limit) {
		limitValue = new DataLimitBean(offset, limit);
	}
	
	public void addLimit(final DataLimitBean dl) {
		limitValue = dl;
	}
	
	public void addLimit(final DataSearchBean dsb) {
		limitValue = dsb.getDataLimitBean();
	}
	
	public void forceInsertSentence(final Boolean val) {
		forceInsertSentence = val;
	}
	
	public void updateOperation(final Boolean val) {
		updateOperation = val;
	}
	
	public void sanitizeQuery(final Boolean val) {
		sanitizeQuery = val;
	}
	
	public void useColumnsOnFindOneQuery(final Boolean val) {
		useColumnsOnFindOneQuery = val;
	}
}