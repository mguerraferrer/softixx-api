package softixx.api.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.bean.CommonBean;
import softixx.api.bean.DataLimitBean;
import softixx.api.bean.PageBean;
import softixx.api.bean.QueryableBean;
import softixx.api.bean.SqlBean;
import softixx.api.util.UInteger;
import softixx.api.util.ULong;
import softixx.api.util.UValidator;

/**
 * Abstract class with a basic set of JDBC operations. It simplifies the use of
 * {@link JdbcTemplate} and {@link NamedParameterJdbcTemplate}
 * 
 * @param <T>  - Object type
 * @param <ID> Object id type
 * @author Maikel Guerra Ferrer
 */
@Slf4j
public abstract class GenericCrudDao<T, ID> {
	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;
	
	private PreparedStatement preparedStatement;
	
	private Boolean showQuery;
	private String table;
	private String joinTables;
	private String join;
	private String columns;
	private String columnsReplace;
	private String sentence;
	private String forceSentence;
	private Boolean forceInsertSentence;
	private String conditional;
	private String where;
	private String query;
	private String countQuery;
	private Boolean sanitizeQuery;
	private Object beanId;
	private String idColumnName;
	private String className;
	private String idClassName;
	private String orderBy;
	private String limit;
	private String offset;
	private Pageable pageable;
	private Integer page;
	private Integer pageSize;
	private List<SqlParameter> declaredParameters;
	private MapSqlParameterSource namedParams;
	private RowMapper<T> rowMapper;
	private List<Object> parameters;
	private List<Object> values;
	private List<Object> conditionalsData;
	private Object[] args;
	private Class<?> idClass;

	protected GenericCrudDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.showQuery = false;
	}
	
	/**
	 * Prints query information if showQuery is true. <br>
	 * By default, showQuery is false.
	 * 
	 * @since: 1.1.0
	 */
	protected void showQuery() {
		this.showQuery = true;
	}
	
	/**
	 * Prints query information if showQuery is true. <br>
	 * By default, showQuery is false.
	 * 
	 * @param showQuery Boolean
	 * 
	 * @since: 1.1.0
	 */
	protected void showQuery(boolean showQuery) {
		this.showQuery = showQuery;
	}
	
	/**
	 * Gets PreparedStatement
	 * @param query - String query
	 * @return PreparedStatement
	 */
	protected PreparedStatement getPreparedStatement(final String query) {
		try (val conn = dataSource.getConnection()) {
			
			this.preparedStatement = conn.prepareStatement(query);
			return this.preparedStatement;
			
		} catch (Exception e) {
			log.error("GenericCrudDao#getPreparedStatement error {}", e.getMessage());
		}
		return null;
	}
	
	protected void safeClose() {
		if(this.preparedStatement != null) {
			try {
				val conn = this.preparedStatement.getConnection();
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				log.error("GenericCrudDao#safeClose error {}", e.getMessage());
			}
			
			try {
				this.preparedStatement.close();
			} catch (Exception e) {
				log.error("GenericCrudDao#safeClose error {}", e.getMessage());
			}
		}		
	}
	
	/**
	 * Executes SQL statement
	 * 
	 * @since 1.2.0
	 * @param query String
	 */
	protected void execute(final String query) {
		try {

			if (UValidator.isNotEmpty(query)) {
				this.query = query;
				jdbcTemplate.execute(query);
			}

		} catch (DataAccessException e) {
			log.error("GenericCrudDao#execute error {}", e.getMessage());
		}
	}
	
	/**
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and Object[] args when
	 * SqlBean args are non-null. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when SqlBean namedParams are non-null. <br>
	 * Executes a JdbcTemplate query only with RowMapper when SqlBean args and
	 * namedParams are null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return T or null
	 */
	protected Optional<T> query(final SqlBean<T, ID> bean) {
		try {
			
			this.getSqlBeanData(bean);
			sanitizeQuery();
			
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#query query: {}", this.query);
				log.info("GenericCrudDao#query namedParams: {}", printNamedParameters(this.namedParams));
				log.info("GenericCrudDao#query args: {}", printArgs(this.args));
			}
			
			if(UValidator.isNotEmpty(this.query)) {
				if (UValidator.isNotNullArgs(this.args)) {
					return Optional.of(jdbcTemplate.queryForObject(this.query, this.rowMapper, this.args));
				} else if (UValidator.isNotNullNamedParameters(this.namedParams)) {
					return Optional.of(namedParamJdbcTemplate.queryForObject(this.query, this.namedParams, this.rowMapper));
				} else {
					return Optional.of(jdbcTemplate.queryForObject(query, rowMapper));
				}
			}
			
		} catch (Exception e) {
			log.error("GenericCrudDao#query error {}", e.getMessage());
		}
		return Optional.empty();
	}
	
	/**
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and Object[] args when
	 * SqlBean args are non-null. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when SqlBean namedParams are non-null. <br>
	 * Executes a JdbcTemplate query only with RowMapper when SqlBean args and
	 * namedParams are null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return List<T> of elements
	 */
	protected List<T> queryList(final SqlBean<T, ID> bean) {
		try {
			
			this.getSqlBeanData(bean);
			sanitizeQuery();
			
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#queryList query: {}", this.query);
				log.info("GenericCrudDao#queryList namedParams: {}", printNamedParameters(this.namedParams));
				log.info("GenericCrudDao#queryList args: {}", printArgs(this.args));
			}
			
			if(UValidator.isNotEmpty(this.query)) {
				if (UValidator.isNotNullArgs(this.args)) {
					return jdbcTemplate.query(this.query, this.rowMapper, this.args);
				} else if (UValidator.isNotNullNamedParameters(this.namedParams)) {
					return namedParamJdbcTemplate.query(this.query, this.namedParams, this.rowMapper);
				} else {
					return jdbcTemplate.query(query, rowMapper);
				}
			}
			
		} catch (Exception e) {
			log.error("GenericCrudDao#queryList error {}", e.getMessage());
		}
		return new ArrayList<>();
	}
	
	/**
	 * <b><i>JdbcTemplate#queryForObject</b></i> with RowMapper<T> and @Nullable
	 * Object...
	 * 
	 * @since: 1.0.0
	 * @param query     String
	 * @param rowMapper RowMapper<T>
	 * @param args      @Nullable Object...
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findOne(final String query, final RowMapper<T> rowMapper, final @Nullable Object... args) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findOne query: {}", query);
					log.info("GenericCrudDao#findOne args: {}", printArgs(args));
					log.info("GenericCrudDao#findOne rowMapper: {}", rowMapper.getClass().getName());
				}

				if (UValidator.isNotNullArgs(args)) {
					// return jdbcTemplate.query(query, rowMapper,
					// args).stream().findFirst().orElse(null);
					return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, args));
				} else {
					// return jdbcTemplate.query(query,
					// rowMapper).stream().findFirst().orElse(null);
					return Optional.of(jdbcTemplate.queryForObject(query, rowMapper));
				}
			}

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
		}
		return Optional.empty();
	}

	/**
	 * <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * MapSqlParameterSource and RowMapper<T>
	 * 
	 * @since: 1.1.0
	 * @param query       String
	 * @param namedParams MapSqlParameterSource
	 * @param rowMapper   RowMapper<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findOne(final String query, final MapSqlParameterSource namedParams,
			final RowMapper<T> rowMapper) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findOne query: {}", query);
					log.info("GenericCrudDao#findOne namedParams: {}", printNamedParameters(namedParams));
					log.info("GenericCrudDao#findOne rowMapper: {}", rowMapper.getClass().getName());
				}

				if (UValidator.isNotNullNamedParameters(namedParams)) {
					return Optional.of(namedParamJdbcTemplate.queryForObject(query, namedParams, rowMapper));
				}
			}

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
		}
		return Optional.empty();
	}

	/**
	 * <b><i>JdbcTemplate#queryForObject</b></i> with Class<T> (used by a
	 * BeanPropertyRowMapper<T>) and @Nullable Object...
	 * 
	 * @since: 1.0.0
	 * @param query String
	 * @param clazz Class<T>
	 * @param args  @Nullable Object...
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findOne(final String query, final Class<T> clazz, final @Nullable Object... args) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findOne query: {}", query);
					log.info("GenericCrudDao#findOne args: {}", printArgs(args));
					log.info("GenericCrudDao#findOne beanPropertyRowMapper: {}", clazz.getName());
				}

				if (UValidator.isNotNullArgs(args)) {
					// return jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(clazz),
					// args).stream().findFirst().orElse(null);
					return Optional.of(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<T>(clazz), args));
				} else {
					// return jdbcTemplate.query(query, new
					// BeanPropertyRowMapper<T>(clazz)).stream().findFirst().orElse(null);
					return Optional.of(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<T>(clazz)));
				}
			}
			return Optional.empty();

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
			return Optional.empty();
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
			return Optional.empty();
		}
	}

	/**
	 * <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * MapSqlParameterSource and Class<T> (used by a BeanPropertyRowMapper<T>)
	 * 
	 * @since: 1.1.0
	 * @param query       String
	 * @param namedParams MapSqlParameterSource
	 * @param clazz       Class<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findOne(final String query, final MapSqlParameterSource namedParams, final Class<T> clazz) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findOne query: {}", query);
					log.info("GenericCrudDao#findOne namedParams: {}", printNamedParameters(namedParams));
					log.info("GenericCrudDao#findOne beanPropertyRowMapper: {}", clazz.getName());
				}

				if (UValidator.isNotNullNamedParameters(namedParams)) {
					return Optional.of(namedParamJdbcTemplate.queryForObject(query, namedParams,
							new BeanPropertyRowMapper<T>(clazz)));
				}
			}
			return Optional.empty();

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
			return Optional.empty();
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
			return Optional.empty();
		}
	}

	/**
	 * JdbcTemplate or <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * SqlBean<T>. <br>
	 * Executes a <b><i>JdbcTemplate#queryForObject</b></i> query when SqlBean args
	 * are non-null. <br>
	 * Executes a <b><i>NamedParamJdbcTemplate#queryForObject</b></i> query when
	 * SqlBean namedParams are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findOne(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			this.query = QueryableBean.QUERY_FIND_BY_ID;
			if(UValidator.isNotEmpty(this.join)) {
				this.query = QueryableBean.QUERY_FIND_BY_ID_JOIN;
			}
			sanitizeQuery();

			return find();

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
		}
		return Optional.empty();
	}
	
	/**
	 * JdbcTemplate or <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * SqlBean<T>. <br>
	 * Executes a <b><i>JdbcTemplate#queryForObject</b></i> query when SqlBean args
	 * are non-null. <br>
	 * Executes a <b><i>NamedParamJdbcTemplate#queryForObject</b></i> query when
	 * SqlBean namedParams are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> findBy(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			this.query = QueryableBean.QUERY_WHERE;
			sanitizeQuery();
			
			return find(); 

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#findOne {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findOne error {}", e.getMessage());
		}
		return Optional.empty();
	}
	
	/**
	 * JdbcTemplate or <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * SqlBean<T>. <br>
	 * Executes a <b><i>JdbcTemplate#queryForObject</b></i> query when SqlBean args
	 * are non-null. <br>
	 * Executes a <b><i>NamedParamJdbcTemplate#queryForObject</b></i> query when
	 * SqlBean namedParams are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	protected Optional<T> exists(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			this.query = QueryableBean.QUERY_EXISTS;
			sanitizeQuery();

			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#exists query: {}", this.query);
			}

			return Optional.of(jdbcTemplate.queryForObject(this.query, this.rowMapper));

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#exists {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#exists error {}", e.getMessage());
		}
		return Optional.empty();
	}

	/**
	 * JdbcTemplate query with RowMapper<T> and @Nullable Object...
	 * 
	 * @since: 1.0.0
	 * @param query     String
	 * @param rowMapper RowMapper<T>
	 * @param args      @Nullable Object...
	 * @return List<T> of elements
	 */
	protected List<T> findAll(final String query, final RowMapper<T> rowMapper, final @Nullable Object... args) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findAll query: {}", query);
					log.info("GenericCrudDao#findAll args: {}", printArgs(args));
					log.info("GenericCrudDao#findAll rowMapper: {}", rowMapper.getClass().getName());
				}

				if (UValidator.isNotNullArgs(args)) {
					return jdbcTemplate.query(query, rowMapper, args);
				} else {
					return jdbcTemplate.query(query, rowMapper);
				}
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		}
		return new ArrayList<T>();
	}

	/**
	 * NamedParamJdbcTemplate query with MapSqlParameterSource and RowMapper<T>
	 * 
	 * @since: 1.1.0
	 * @param query       String
	 * @param namedParams MapSqlParameterSource
	 * @param rowMapper   RowMapper<T>
	 * @return List<T> of elements
	 */
	protected List<T> findAll(final String query, final MapSqlParameterSource namedParams, final RowMapper<T> rowMapper) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findAll query: {}", query);
					log.info("GenericCrudDao#findAll namedParams: {}", printNamedParameters(namedParams));
					log.info("GenericCrudDao#findAll rowMapper: {}", rowMapper.getClass().getName());
				}

				if (UValidator.isNotNullNamedParameters(namedParams)) {
					return namedParamJdbcTemplate.query(query, namedParams, rowMapper);
				}
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		}
		return new ArrayList<T>();
	}

	/**
	 * JdbcTemplate query with Class<T> (used by a BeanPropertyRowMapper<T>)
	 * and @Nullable Object...
	 * 
	 * @since: 1.0.0
	 * @param query String
	 * @param clazz Class<T>
	 * @param args  @Nullable Object...
	 * @return List<T> of elements
	 */
	protected List<T> findAll(final String query, final Class<T> clazz, final @Nullable Object... args) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findAll query: {}", query);
					log.info("GenericCrudDao#findAll args: {}", printArgs(args));
					log.info("GenericCrudDao#findAll beanPropertyRowMapper: {}", clazz.getName());
				}

				if (UValidator.isNotNullArgs(args)) {
					return jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(clazz), args);
				} else {
					return jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(clazz));
				}
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		}
		return new ArrayList<T>();
	}

	/**
	 * NamedParamJdbcTemplate query with MapSqlParameterSource and Class<T> (used by
	 * a BeanPropertyRowMapper<T>)
	 * 
	 * @since: 1.1.0
	 * @param query       String
	 * @param namedParams MapSqlParameterSource
	 * @param clazz       Class<T>
	 * @return List<T> of elements
	 */
	protected List<T> findAll(final String query, final MapSqlParameterSource namedParams, final Class<T> clazz) {
		try {

			if (UValidator.isNotEmpty(query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#findAll query: {}", query);
					log.info("GenericCrudDao#findAll namedParams: {}", printNamedParameters(namedParams));
					log.info("GenericCrudDao#findAll beanPropertyRowMapper: {}", clazz.getName());
				}

				if (UValidator.isNotNullNamedParameters(namedParams)) {
					return namedParamJdbcTemplate.query(query, namedParams, new BeanPropertyRowMapper<T>(clazz));
				}
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAll error {}", e.getMessage());
		}
		return new ArrayList<T>();
	}

	/**
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when
	 * SqlBean args are non-null. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when SqlBean namedParams are non-null. <br>
	 * Executes a JdbcTemplate query only with RowMapper when SqlBean args and
	 * namedParams are null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return List<T> of elements
	 */
	protected List<T> findAll(final SqlBean<T, ID> bean) {
		this.getSqlBeanData(bean);

		this.query = QueryableBean.QUERY_ALL;
		sanitizeQuery();

		if (UValidator.isNotNullArgs(this.args)) {
			return this.findAll(this.query, this.rowMapper, this.args);
		} else if (UValidator.isNotNullNamedParameters(this.namedParams)) {
			return this.findAll(this.query, this.namedParams, this.rowMapper);
		} else {
			return this.findAll(this.query, this.rowMapper);
		}
	}

	/**
	 * Find all by sentence or conditional query <br>
	 * This method use a static QUERY_SENTENCE_ORDER_BY for a sentences query and
	 * QUERY_CONDITIONAL_ORDER_BY for conditionals query. <br>
	 * <br>
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a sentence query. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when is a conditional query and SqlBean namedParams are non-null.
	 * <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a conditional query and SqlBean conditionalsData are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return List<T> of elements
	 */
	protected List<T> findAllOrderBy(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			var isConditional = false;

			if (UValidator.isNotEmpty(this.sentence) && !this.values.isEmpty()) {
				this.query = QueryableBean.QUERY_SENTENCE_ORDER_BY;
			} else if (UValidator.isNotEmpty(this.conditional)) {
				this.query = QueryableBean.QUERY_CONDITIONAL_ORDER_BY;
				isConditional = true;
			}

			sanitizeQuery();
			
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#findAllOrderBy query: {}", this.query);
				log.info("GenericCrudDao#findAllOrderBy sentence: {}", this.sentence);
				log.info("GenericCrudDao#findAllOrderBy values: {}", this.values);
				log.info("GenericCrudDao#findAllOrderBy conditional: {}", this.conditional);
				log.info("GenericCrudDao#findAllOrderBy conditionalsData: {}", printData(this.conditionalsData));
				log.info("GenericCrudDao#findAllOrderBy namedParams: {}", printNamedParameters(this.namedParams));
			}

			return this.findAll(isConditional);

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAllOrderBy error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAllOrderBy error {}", e.getMessage());
		}
		
		return new ArrayList<T>();
	}
	
	/**
	 * Find all by sentence or conditional query <br>
	 * This method use a static QUERY_SENTENCE_JOIN_ORDER_BY for a sentences query and
	 * QUERY_CONDITIONAL_JOIN_ORDER_BY for conditionals query. <br>
	 * <br>
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a sentence query. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when is a conditional query and SqlBean namedParams are non-null.
	 * <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a conditional query and SqlBean conditionalsData are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return List<T> of elements
	 */
	protected List<T> findAllJoinOrderBy(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			var isConditional = false;

			if (UValidator.isNotEmpty(this.sentence) && !this.values.isEmpty()) {
				this.query = QueryableBean.QUERY_SENTENCE_JOIN_ORDER_BY;
			} else if (UValidator.isNotEmpty(this.conditional)) {
				this.query = QueryableBean.QUERY_CONDITIONAL_JOIN_ORDER_BY;
				isConditional = true;
			}

			sanitizeQuery();
			
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#findAllOrderBy query: {}", this.query);
				log.info("GenericCrudDao#findAllOrderBy sentence: {}", this.sentence);
				log.info("GenericCrudDao#findAllOrderBy values: {}", this.values);
				log.info("GenericCrudDao#findAllOrderBy conditional: {}", this.conditional);
				log.info("GenericCrudDao#findAllOrderBy conditionalsData: {}", printData(this.conditionalsData));
				log.info("GenericCrudDao#findAllOrderBy namedParams: {}", printNamedParameters(this.namedParams));
			}

			return this.findAll(isConditional);

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#findAllOrderBy error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#findAllOrderBy error {}", e.getMessage());
		}
		
		return new ArrayList<T>();
	}

	/**
	 * Paginate by sentence or conditional query <br>
	 * This method use a static QUERY_SENTENCE_PAGINATE for a sentences query
	 * and QUERY_CONDITIONAL_PAGINATE for conditionals query. <br>
	 * <br>
	 * JdbcTemplate or NamedParamJdbcTemplate query with SqlBean<T>. <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a sentence query. <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper when is a conditional query and SqlBean namedParams are non-null.
	 * <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... when is
	 * a conditional query and SqlBean conditionalsData are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Paginate List<T> of elements
	 */
	protected PageBean<T> paginate(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			var isConditional = false;
			var isWhere = false;

			if(UValidator.isEmpty(this.query)) {
				if (UValidator.isNotEmpty(this.sentence) && !this.values.isEmpty()) {
					this.query = QueryableBean.QUERY_SENTENCE_PAGINATE;
				} else if (UValidator.isNotEmpty(this.conditional)) {
					this.query = QueryableBean.QUERY_CONDITIONAL_PAGINATE;
					isConditional = true;
				}
			} else {
				isWhere = true;
			}

			sanitizeQuery();

			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#paginate query: {}", this.query);
				log.info("GenericCrudDao#paginate countQuery: {}", this.countQuery);
				log.info("GenericCrudDao#paginate sentence: {}", this.sentence);
				log.info("GenericCrudDao#paginate values: {}", this.values);
				log.info("GenericCrudDao#paginate conditional: {}", this.conditional);
				log.info("GenericCrudDao#paginate where: {}", this.where);
				log.info("GenericCrudDao#paginate conditionalsData: {}", printData(this.conditionalsData));
				log.info("GenericCrudDao#paginate namedParams: {}", printNamedParameters(this.namedParams));
			}
			
			val totalRows = jdbcTemplate.queryForObject(this.countQuery, Integer.class);
			
			List<T> content = null;
			if(isWhere) {
				content = queryList(bean);
			} else {
				content = this.findAll(isConditional);
			}
			
			val dataLimit = new DataLimitBean(this.page, this.pageSize, totalRows);
			return new PageBean<>(content, dataLimit);
			
		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#paginate error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#paginate error {}", e.getMessage());
		}
		
		return new PageBean<>();
	}

	/**
	 * Paginate by pageable <br>
	 * This method use a SqlBean pageable data for paginate <br>
	 * <br>
	 * JdbcTemplate query with SqlBean <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object...
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Page<T>
	 */
	protected PageBean<T> pageable(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			sanitizeQuery();

			if (UValidator.isEmpty(this.query)) {
				return new PageBean<T>();
			}

			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#pageable query: {}", this.query);
				log.info("GenericCrudDao#pageable values: {}", this.values);
			}

			val pageableQuery = this.findAll(this.query, this.rowMapper, this.values.toArray());
			return new PageBean<T>(pageableQuery, this.pageable);

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#pageable error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#pageable error {}", e.getMessage());
		}
		return new PageBean<T>();
	}

	/**
	 * Find the last id <br>
	 * JdbcTemplate query with SqlBean <br>
	 * Executes a JdbcTemplate query with Class<T> (for id type) and @Nullable
	 * Object...
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Page<T>
	 */
	protected Object findLast(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			
			this.query = QueryableBean.QUERY_FIND_LAST;
			sanitizeQuery();

			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#findLast query: {}", this.query);
				log.info("GenericCrudDao#findLast idClazz: {}", this.idClass);
				log.info("GenericCrudDao#findLast args: {}", printArgs(this.args));
			}

			if (UValidator.isNotNullArgs(this.args)) {
				return jdbcTemplate.queryForObject(this.query, this.idClass, this.args);
			} else {
				return jdbcTemplate.queryForObject(this.query, this.idClass);
			}

		} catch (Exception e) {
			log.error("GenericCrudDao#findLast error {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Insert using QueryableBean <br>
	 * <b><i>JdbcTemplate#update</b></i> with {@link QueryableBean} <br>
	 * 
	 * @since: 1.0.0
	 * @param query String
	 * @param bean  QueryableBean
	 * @return True if inserted successfully, false or throw otherwise
	 * @see JdbcTemplate
	 * @see QueryableBean
	 */
	protected Boolean insert(final QueryableBean bean) {
		try {

			if (!UValidator.isNull(bean.getQuery())) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#insert query: {}", bean.getQuery());
					log.info("GenericCrudDao#insert values: {}", printArgs(bean.getArgs()));
					log.info("GenericCrudDao#insert queryableBean: {}", bean.getClass().getName());
				}

				val insertedRow = jdbcTemplate.update(bean.getQuery(), bean.getArgs());
				return insertedRow == 1;
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#insert error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#insert error {}", e.getMessage());
		}
		return false;
	}

	/**
	 * Insert using CommonBean <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean <br>
	 * 
	 * @since: 1.0.0
	 * @param bean CommonBean
	 * @return true if inserted successfully, false or throw otherwise
	 */
	protected Boolean insert(final CommonBean<T, ID> bean) {
		try {

			this.table = bean.getTable();
			this.columns = bean.getInsertableColumns();
			this.sentence = bean.getInsertableSentence();
			this.values = bean.getSqlValues();
			if (UValidator.isNotEmpty(this.table) && UValidator.isNotEmpty(this.columns)
					&& UValidator.isNotEmpty(this.sentence) && !this.values.isEmpty()) {
				this.query = QueryableBean.QUERY_INSERT
										  .replaceAll(QueryableBean.REPLACE_TABLE, this.table)
										  .replaceAll(QueryableBean.REPLACE_COLUMNS, this.columns)
										  .replaceAll(QueryableBean.REPLACE_SENTENCE, this.sentence);

				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#insert query: {}", this.query);
					log.info("GenericCrudDao#insert values: {}", printArgs(this.values));
					log.info("GenericCrudDao#insert commonBean: {}", bean.getClass().getName());
				}

				val insertedRow = jdbcTemplate.update(this.query, this.values.toArray());
				return insertedRow == 1;
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#insert error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#insert error {}", e.getMessage());
		}
		return false;
	}

	/**
	 * Update using QueryableBean <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean and @Nullable Object...
	 * <br>
	 * 
	 * @since 1.0.0
	 * @param bean CommonBean
	 * @param args Additionals arguments
	 * @return true if updated successfully, false or throw otherwise
	 */
	protected Boolean update(final QueryableBean bean, final @Nullable Object... args) {
		try {

			this.table = bean.getTable();
			this.columns = bean.getUpdatableColumns();
			this.values = bean.getSqlValues();

			if (UValidator.isNotEmpty(this.table) && UValidator.isNotEmpty(this.columns) && !UValidator.isNull(this.values)) {
				this.query = QueryableBean.QUERY_UPDATE
										  .replaceAll(QueryableBean.REPLACE_TABLE, table)
										  .replaceAll(QueryableBean.REPLACE_COLUMNS, columns);

				if (UValidator.isNotNullArgs(args)) {
					values.addAll(Arrays.asList(args));
				}

				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#update query: {}", this.query);
					log.info("GenericCrudDao#update columns: {}", this.columns);
					log.info("GenericCrudDao#update values: {}", printArgs(this.values));
					log.info("GenericCrudDao#update args: {}", printArgs(args));
					log.info("GenericCrudDao#update commonBean: {}", bean.getClass().getName());
				}

				val updatedRow = jdbcTemplate.update(this.query, this.values.toArray());
				if (updatedRow > 1) {
					log.error("GenericCrudDao#update error More than one record was updated!");
				}
				return updatedRow == 1;
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#update error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#update error {}", e.getMessage());
		}
		return false;
	}
	
	/**
	 * Insert/Update using SqlBean<T> <br><br>
	 * Executes a <b><i>NamedParameterJdbcTemplate#update</b></i> if {@link SqlBean#getNamedParams()} is not null
	 * Executes a <b><i>JdbcTemplate#update</b></i> if {@link SqlBean#getParameters()} is nnot null
	 * 
	 * @since 1.1.0
	 * @param bean SqlBean<T>
	 * @return True if inserted/updated successfully, false or throw
	 *         otherwise
	 */
	protected Boolean persist(final SqlBean<T, ID> bean) {
		try {
			
			this.getSqlBeanData(bean);
			if (UValidator.isNotEmpty(this.query)) {
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#persist query: {}", this.query);
					log.info("GenericCrudDao#persist parameters: {}", printArgs(this.parameters));
					log.info("GenericCrudDao#findAll namedParams: {}", printNamedParameters(this.namedParams));
					log.info("GenericCrudDao#persist sqlBean: {}", this.className);
				}
				
				var rowCount = 0;
				
				if (UValidator.isNotNullNamedParameters(this.namedParams)) {
					rowCount = namedParamJdbcTemplate.update(query, namedParams);
				} else if(!this.parameters.isEmpty()) {
					rowCount = jdbcTemplate.update(this.query, this.parameters.toArray());
				}
				return rowCount == 1;
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#persist error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#persist error {}", e.getMessage());
		}
		return false;
	}

	/**
	 * Insert/Update using SqlBean<T> <br>
	 * This method use a PreparedStatementCreatorFactory and
	 * PreparedStatementCreator with a declaredParameters (List<SqlParameter>) <br>
	 * Executes a <b><i>JdbcTemplate#update</b></i> with PreparedStatementCreator
	 * and GeneratedKeyHolder
	 * 
	 * @since 1.1.0
	 * @param bean SqlBean<T>
	 * @return Optional<T> if inserted/updated successfully, Optional.empty() or throw
	 *         otherwise
	 */
	protected Optional<T> save(final SqlBean<T, ID> bean) {
		try {

			this.getSqlBeanData(bean);
			if (UValidator.isNotEmpty(this.query)) {
				sanitizeQuery();
				
				if (Boolean.TRUE.equals(this.showQuery)) {
					log.info("GenericCrudDao#save query: {}", this.query);
					log.info("GenericCrudDao#save declaredParameters: {}", printDeclaredParameters());
					log.info("GenericCrudDao#save parameters: {}", printArgs(this.parameters));
					log.info("GenericCrudDao#save idColumnName: {}", this.idColumnName);
					log.info("GenericCrudDao#save sqlBean: {}", this.className);
				}
				
				val pscFactory = this.pscFactory(this.query, this.declaredParameters, this.idColumnName);
				val psc = pscFactory.newPreparedStatementCreator(this.parameters);
				
				val kh = new GeneratedKeyHolder();
				val rowCount = jdbcTemplate.update(psc, kh);
				
				if(rowCount == 1) {
					if (Objects.nonNull(kh)) {
						val id = this.getIdByKeyHolder(kh, this.idClassName);
						if (id != null) {
							bean.addArgs(new Object[] { id });
						}
					}
					return this.findOne(bean);
				}
			}

		} catch (DataRetrievalFailureException e) {
			log.error("GenericCrudDao#save error {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#save error {}", e.getMessage());
		}
		return Optional.empty();
	}
	
	/**
	 * Delete by id using CommonBean <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean <br>
	 * 
	 * @since: 1.1.0
	 * @param bean CommonBean
	 * @return true if deleted successfully, false otherwise
	 */
	protected boolean deleteById(final CommonBean<T, ID> bean) {
		this.getDeleteQuery(bean.getTable());

		if (bean.getBeanId() != null) {
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#deleteById query: {}", this.query);
				log.info("GenericCrudDao#deleteById id: {}", bean.getBeanId().toString());
				log.info("GenericCrudDao#deleteById commonBean: {}", bean.getClass().getName());
			}
			return jdbcTemplate.update(this.query, new Object[] { bean.getBeanId() }) == 1;
		}
		return false;
	}

	/**
	 * Delete by id using SqlBean<T> <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean <br>
	 * 
	 * @since: 1.1.0
	 * @param bean CommonBean
	 * @return true if deleted successfully, false otherwise
	 */
	protected boolean deleteById(final SqlBean<T, ID> bean) {
		this.getSqlBeanData(bean);
		this.getDeleteQuery(this.table);

		if (this.beanId != null) {
			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#deleteById query: {}", this.query);
				log.info("GenericCrudDao#deleteById id: {}", printId(this.beanId));
				log.info("GenericCrudDao#deleteById sqlBean: {}", this.className);
			}
			return jdbcTemplate.update(this.query, new Object[] { this.beanId }) == 1;
		}
		return false;
	}

	/**
	 * Delete using CommonBean <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean <br>
	 * 
	 * @since: 1.1.0
	 * @param bean CommonBean
	 * @return true if deleted successfully, false otherwise
	 */
	protected boolean delete(final CommonBean<T, ID> bean) {
		this.query = bean.getQuery();

		if (Boolean.TRUE.equals(this.showQuery)) {
			log.info("GenericCrudDao#delete query: {}", this.query);
			log.info("GenericCrudDao#delete args: {}", printArgs(bean.getArgs()));
			log.info("GenericCrudDao#delete commonBean: {}", bean.getClass().getName());
		}

		return jdbcTemplate.update(this.query, bean.getArgs()) == 1;
	}

	/**
	 * Delete using SqlBean<T> <br>
	 * <b><i>JdbcTemplate#update</b></i> with CommonBean <br>
	 * 
	 * @since: 1.1.0
	 * @param bean CommonBean
	 * @return true if deleted successfully, false otherwise
	 */
	protected boolean delete(final SqlBean<T, ID> bean) {
		this.getSqlBeanData(bean);

		if (Boolean.TRUE.equals(this.showQuery)) {
			log.info("GenericCrudDao#delete query: {}", this.query);
			log.info("GenericCrudDao#delete args: {}", printArgs(this.args));
			log.info("GenericCrudDao#delete sqlBean: {}", this.className);
		}

		return jdbcTemplate.update(this.query, this.args) == 1;
	}

	/**
	 * Find all by sentence or conditional query <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... if
	 * isConditional is false <br>
	 * Executes a NamedParamJdbcTemplate query with MapSqlParameterSource and
	 * RowMapper if isConditional is true and namedParams are non-null <br>
	 * Executes a JdbcTemplate query with RowMapper and @Nullable Object... if
	 * isConditional is true and conditionalsData are non-null <br>
	 * 
	 * @param isConditional boolean
	 * @return List<T> of elements
	 */
	private List<T> findAll(boolean isConditional) {
		if (!isConditional) {
			return this.findAll(this.query, this.rowMapper, this.values.toArray());
		} else {
			if (UValidator.isNotNullNamedParameters(this.namedParams)) {
				return this.findAll(this.query, this.namedParams, this.rowMapper);
			} else if (!this.conditionalsData.isEmpty()) {
				return this.findAll(this.query, this.rowMapper, this.conditionalsData.toArray());
			} else {
				return new ArrayList<T>();
			}
		}
	}
	
	/**
	 * JdbcTemplate or <b><i>NamedParamJdbcTemplate#queryForObject</b></i> with
	 * SqlBean<T>. <br>
	 * Executes a <b><i>JdbcTemplate#queryForObject</b></i> query when SqlBean args
	 * are non-null. <br>
	 * Executes a <b><i>NamedParamJdbcTemplate#queryForObject</b></i> query when
	 * SqlBean namedParams are non-null.
	 * 
	 * @since: 1.1.0
	 * @param bean SqlBean<T>
	 * @return Optional<T> if the element is found, otherwise return
	 *         Optional.empty()
	 */
	private Optional<T> find() {
		try {

			if (Boolean.TRUE.equals(this.showQuery)) {
				log.info("GenericCrudDao#find query: {}", this.query);
				log.info("GenericCrudDao#find args: {}", printArgs(this.args));
				log.info("GenericCrudDao#find namedParams: {}", printNamedParameters(this.namedParams));
				log.info("GenericCrudDao#find rowMapper: {}", this.rowMapper.getClass().getName());
			}

			if (UValidator.isNotNullArgs(this.args)) {
				return Optional.of(jdbcTemplate.queryForObject(this.query, this.rowMapper, this.args));
			} else if (UValidator.isNotNullNamedParameters(this.namedParams)) {
				return Optional.of(namedParamJdbcTemplate.queryForObject(this.query, this.namedParams, this.rowMapper));
			} else {
				return Optional.of(jdbcTemplate.queryForObject(this.query, this.rowMapper));
			}

		} catch (EmptyResultDataAccessException e) {
			log.info("GenericCrudDao#find {}", e.getMessage());
		} catch (Exception e) {
			log.error("GenericCrudDao#find error {}", e.getMessage());
		}
		return Optional.empty();
	}

	/**
	 * Create a new PreparedStatementCreatorFactory with the given SQL and
	 * parameters.
	 * 
	 * @since 1.1.0
	 * @param sql                String
	 * @param declaredParameters List<SqlParameter>
	 * @param keyColumnName      String... keyColumnName
	 * @return PreparedStatementCreatorFactory
	 */
	private PreparedStatementCreatorFactory pscFactory(final String sql,
			final List<SqlParameter> declaredParameters, final String... keyColumnName) {
		return new PreparedStatementCreatorFactory(sql, declaredParameters) {
			{
				String keyColName = UValidator.isNotNullArgs(keyColumnName) ? keyColumnName[0] : "id";
				setReturnGeneratedKeys(true);
				setGeneratedKeysColumnNames(keyColName);
			}
		};
	}

	/**
	 * Evaluate which method to use to get the id value
	 * 
	 * @param kh        GeneratedKeyHolder
	 * @param className String
	 * @return Object with the id value
	 */
	private Object getIdByKeyHolder(final GeneratedKeyHolder kh, final String className) {
		return switch (className) {
		case "java.lang.Integer" -> this.getKeyHolderIdInt(kh);
		case "java.lang.Long", "java.lang.BigInteger" -> this.getKeyHolderIdLong(kh);
		default -> null;
		};
	}

	/**
	 * Gets an id (integer) value from GeneratedKeyHolder
	 * 
	 * @param kh GeneratedKeyHolder
	 * @return Integer value of id
	 */
	private Integer getKeyHolderIdInt(final GeneratedKeyHolder kh) {
		Integer id = null;
		if (kh != null) {
			// ##### With jdk-15 --enable-preview
			/*
			 * if(kh.getKey() instanceof Integer khId) { id = khId; } else { id =
			 * UInteger.value(kh.getKey()); }
			 */
			id = UInteger.value(kh.getKey());
		}
		return id;
	}

	/**
	 * Gets an id (long) value from GeneratedKeyHolder
	 * 
	 * @param kh GeneratedKeyHolder
	 * @return Long value of id
	 */
	private Long getKeyHolderIdLong(final GeneratedKeyHolder kh) {
		Long id = null;
		if (kh != null) {
			// ##### With jdk-15 --enable-preview
			/*
			 * if(kh.getKey() instanceof Long khId) { id = khId; } else { id =
			 * ULong.value(kh.getKey()); }
			 */
			id = ULong.value(kh.getKey());
		}
		return id;
	}
	
	/**
	 * Sanitize SqlBean query
	 * 
	 * @since 1.0.1
	 */
	private void sanitizeQuery() {
		if(this.sanitizeQuery && UValidator.isNotEmpty(this.query)) {
			if(UValidator.isNotEmpty(this.table)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_TABLE, this.table);
				if(UValidator.isNotEmpty(this.countQuery)) {
					this.countQuery = this.countQuery.replaceAll(QueryableBean.REPLACE_TABLE, this.table);
				}
			}
			
			if(UValidator.isNotEmpty(this.columns)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_COLUMNS, this.columns);
				if(UValidator.isNotEmpty(this.columnsReplace)) {
					this.query = this.query.replaceAll(Pattern.quote(QueryableBean.REPLACE_ALL), this.columnsReplace);
				}
			}
			
			if(UValidator.isNotEmpty(this.joinTables)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_INNER_JOIN, this.joinTables);
				if(UValidator.isNotEmpty(this.countQuery)) {
					this.countQuery = this.countQuery.replaceAll(QueryableBean.REPLACE_INNER_JOIN, this.joinTables);
				}
			}			
			
			if(UValidator.isNotEmpty(this.join)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_INNER_JOIN, this.join);
			}
			
			if(UValidator.isNotEmpty(this.sentence)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_SENTENCE, this.sentence);
			}
			
			if(UValidator.isNotEmpty(this.forceSentence)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_SENTENCE, this.forceSentence);
			}
			
			if(UValidator.isNotEmpty(this.conditional)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_CONDITIONAL, this.conditional);
				this.query = this.query.replaceAll(QueryableBean.REPLACE_WHERE, this.where);
				if(UValidator.isNotEmpty(this.countQuery)) {
					this.countQuery = this.countQuery.replaceAll(QueryableBean.REPLACE_WHERE, this.where);
				}
			}
			
			if(UValidator.isNotEmpty(this.orderBy)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_ORDER_BY, this.orderBy);
			}
			
			if(UValidator.isNotEmpty(this.limit)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_LIMIT, this.limit);
			}
			
			if(UValidator.isNotEmpty(this.offset)) {
				this.query = this.query.replaceAll(QueryableBean.REPLACE_OFFSET, this.offset);
			}
		}
	}

	/**
	 * Gets SqlBean<T> data
	 * 
	 * @since 1.0.1
	 * @param bean SqlBean<T>
	 */
	private void getSqlBeanData(final SqlBean<T, ID> bean) {
		if (bean != null) {
			this.query = bean.getQuery();
			this.countQuery = bean.getCountQuery();
			this.table = bean.getTable();
			this.columns = !bean.isUpdateOperation() ? bean.getSelectableColumns() : bean.getUpdatableColumns();
			this.columnsReplace = bean.getSelectableReplaceColumns();
			this.sentence = bean.getUpdatableColumns();
			this.conditional = bean.getConditionals();
			this.where = bean.getWhere();
			this.joinTables = bean.getJoinTables();
			this.join = bean.getJoin();
			this.args = bean.getArgs();
			this.values = bean.getSqlValues();
			this.conditionalsData = bean.getConditionalsData();
			this.declaredParameters = bean.getDeclaredParameters();
			this.parameters = bean.getParameters();
			this.namedParams = bean.getNamedParams();
			this.idColumnName = bean.getIdColumnName();
			this.className = bean.getClazz().getName();
			this.idClass = bean.getIdClazz();
			this.idClassName = bean.getIdClazz().getName();
			this.orderBy = bean.getOrderBy();
			this.limit = bean.getLimit();
			this.offset = bean.getOffset();
			this.pageable = bean.getPageable();
			this.page = bean.getPage();
			this.pageSize = bean.getPageSize();
			this.rowMapper = bean.getRowMapper();
			this.beanId = bean.getBeanId();
			this.sanitizeQuery = bean.isSanitizeQuery();
			
			this.forceInsertSentence = bean.isForceInsertSentence();
			if(Boolean.TRUE.equals(this.forceInsertSentence)) {
				this.forceSentence = bean.getInsertForceSentence();
			}
		}
	}

	/**
	 * Generate the delete query
	 * 
	 * @param tableName String
	 */
	private void getDeleteQuery(final String tableName) {
		this.query = QueryableBean.QUERY_DELETE
								  .replaceAll(QueryableBean.REPLACE_TABLE, tableName);
	}

	/**
	 * Prints MapSqlParameterSource info
	 * 
	 * @param namedParams MapSqlParameterSource
	 * @return String
	 */
	private String printNamedParameters(final MapSqlParameterSource namedParams) {
		if (namedParams != null) {
			return namedParams.getValues().entrySet().stream()
													 .filter(item -> item.getKey() != null && item.getValue() != null)
													 .map(item -> item.getKey() + "=" + item.getValue())
													 .reduce((x, y) -> String.join(", ", x, y))
													 .orElse("[]");
		}
		return "[]";
	}

	/**
	 * Prints Object... info
	 * 
	 * @param args Object...
	 * @return String
	 */
	private String printArgs(final Object... args) {
		if (Objects.nonNull(args)) {
			val argsStr = Arrays.stream(args)
								.map(item -> item != null ? item.toString() : "null")
								.reduce((x, y) -> String.join(", ", x, y))
								.orElse("");
			return argsStr;
		}
		return "[]";
	}

	/**
	 * Prints Object id
	 * 
	 * @param id Object
	 * @return String
	 */
	private String printId(Object id) {
		return !UValidator.isNull(id) ? id.toString() : null;
	}

	/**
	 * Prints List<SqlParameter> info
	 * 
	 * @return String
	 */
	private String printDeclaredParameters() {
		val declaredParameterStr = this.declaredParameters.stream()
														  .map(item -> item.getTypeName() + ": " + item.getSqlType())
														  .reduce((x, y) -> String.join(", ", x, y))
														  .orElse("");
		return "[" + declaredParameterStr + "]";
	}

	/**
	 * Prints List<Object> info
	 * 
	 * @param source List<Object>
	 * @return String
	 */
	private String printData(List<Object> source) {
		val data = source.stream()
						 .map(item -> item != null ? item.toString() : null)
						 .reduce((x, y) -> String.join(", ", x, y))
						 .orElse("");
		return "[" + data + "]";
	}
}