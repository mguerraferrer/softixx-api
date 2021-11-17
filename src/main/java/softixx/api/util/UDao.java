package softixx.api.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.val;

public class UDao {
	private static final Logger log = LoggerFactory.getLogger(UDao.class);
	
	public static void close(Statement stm, ResultSet rs) {
		try {
			
			if(rs != null) {
				rs.close();
			}
			
			if(stm != null) {
				stm.close();
			}
			
		} catch (Exception e) {
			log.error("UDao#close error - {}", e.getMessage());
		}
	}
	
	public static void showMetadata(ResultSet rs) {
		try {
			
			if(rs != null) {
				rs.setFetchDirection(ResultSet.FETCH_FORWARD);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				while(rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						val columnValue = rs.getString(i);
						System.out.println(rsmd.getColumnName(i) + " ["+rsmd.getColumnType(i)+" | "+rsmd.getColumnTypeName(i)+"] " + columnValue);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("UDao#showMetadata error - {}", e.getMessage());
		}
	}
	
	public static String getString(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			String val = rs.getString(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getString [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static String getString(ResultSet rs, String columnName) throws SQLException {
		try {
			
			String val = rs.getString(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getString [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setString(PreparedStatement stm, int index, String value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.VARCHAR);
		} else {
			stm.setString(index, value);
		}
	}
	
	public static Boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			boolean val = rs.getBoolean(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return Boolean.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getBoolean [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
		try {
			
			boolean val = rs.getBoolean(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return Boolean.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getBoolean [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setBoolean(PreparedStatement stm, int index, Boolean value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.BOOLEAN);
		} else {
			stm.setBoolean(index, value);
		}
	}
	
	public static Long getLong(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			long val = rs.getLong(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return Long.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getLong [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Long getLong(ResultSet rs, String columnName) throws SQLException {
		try {
			
			long val = rs.getLong(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return Long.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getLong [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static Long getLongOrNull(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			if(rs.getObject(columnIndex) == null) {
				return null;
			}
			return getLong(rs, columnIndex);
			
		} catch (Exception e) {
			log.warn("UDao#getLong [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Long getLongOrNull(ResultSet rs, String columnName) throws SQLException {
		try {
			
			if(rs.getObject(columnName) == null) {
				return null;
			}
			return getLong(rs, columnName);
			
		} catch (Exception e) {
			log.warn("UDao#getLong [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setLong(PreparedStatement stm, int index, Long value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.INTEGER);
		} else {
			stm.setObject(index, value);
		}
	}
	
	public static Double getDouble(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			double val = rs.getDouble(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return Double.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getDouble [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Double getDouble(ResultSet rs, String columnName) throws SQLException {
		try {
			
			double val = rs.getDouble(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return Double.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getDouble [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static Double getDoubleOrNull(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			if(rs.getObject(columnIndex) == null) {
				return null;
			}
			return getDouble(rs, columnIndex);
			
		} catch (Exception e) {
			log.warn("UDao#getDouble [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Double getDoubleOrNull(ResultSet rs, String columnName) throws SQLException {
		try {
			
			if(rs.getObject(columnName) == null) {
				return null;
			}
			return getDouble(rs, columnName);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("UDao#getDouble [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setDouble(PreparedStatement stm, int index, Double value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.DOUBLE);
		} else {
			stm.setObject(index, value);
		}
	}
	
	public static Integer getInteger(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			int val = rs.getInt(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return Integer.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getInteger [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Integer getInteger(ResultSet rs, String columnName) throws SQLException {
		try {
			
			int val = rs.getInt(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return Integer.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getInteger [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static Integer getIntegerOrNull(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			if(rs.getObject(columnIndex) == null) {
				return null;
			}
			return getInteger(rs, columnIndex);
			
		} catch (Exception e) {
			log.warn("UDao#getInteger [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Integer getIntegerOrNull(ResultSet rs, String columnName) throws SQLException {
		try {
			
			if(rs.getObject(columnName) == null) {
				return null;
			}
			return getInteger(rs, columnName);
			
		} catch (Exception e) {
			log.warn("UDao#getInteger [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setInteger(PreparedStatement stm, int index, Integer value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.INTEGER);
		} else {
			stm.setObject(index, value);
		}
	}
	
	public static Float getFloat(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			float val = rs.getFloat(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return Float.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getFloat [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Float getFloat(ResultSet rs, String columnName) throws SQLException {
		try {
			
			float val = rs.getFloat(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return Float.valueOf(val);
			
		} catch (Exception e) {
			log.warn("UDao#getFloat [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static Float getFloatOrNull(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			if(rs.getObject(columnIndex) == null) {
				return null;
			}
			return getFloat(rs, columnIndex);
			
		} catch (Exception e) {
			log.warn("UDao#getFloat [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Float getFloatOrNull(ResultSet rs, String columnName) throws SQLException {
		try {
			
			if(rs.getObject(columnName) == null) {
				return null;
			}
			return getFloat(rs, columnName);
			
		} catch (Exception e) {
			log.warn("UDao#getFloat [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setFloat(PreparedStatement stm, int index, Float value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.FLOAT);
		} else {
			stm.setObject(index, value);
		}
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			BigDecimal val = rs.getBigDecimal(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getBigDecimal [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, String columnName) throws SQLException {
		try {
			
			BigDecimal val = rs.getBigDecimal(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getBigDecimal [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static BigDecimal getBigDecimalOrNull(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			if(rs.getObject(columnIndex) == null) {
				return null;
			}
			return getBigDecimal(rs, columnIndex);
			
		} catch (Exception e) {
			log.warn("UDao#getBigDecimal [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static BigDecimal getBigDecimalOrNull(ResultSet rs, String columnName) throws SQLException {
		try {
			
			if(rs.getObject(columnName) == null) {
				return null;
			}
			return getBigDecimal(rs, columnName);
			
		} catch (Exception e) {
			log.warn("UDao#getBigDecimal [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setBigDecimal(PreparedStatement stm, int index, BigDecimal value) throws SQLException {
		if(value == null) {
			stm.setNull(index, Types.NUMERIC);
		} else {
			stm.setObject(index, value);
		}
	}
	
	public static Date getDate(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			Date val = rs.getDate(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getDate [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Date getDate(ResultSet rs, String columnName) throws SQLException {
		try {
			
			Date val = rs.getDate(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getDate [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setDate(PreparedStatement stm, int index, Date sqlDate) throws SQLException {
		if(sqlDate == null) {
			stm.setNull(index, Types.DATE);
		} else {
			stm.setDate(index, sqlDate);
		}
	}
	
	public static Time getTime(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			Time val = rs.getTime(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getTime [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Time getTime(ResultSet rs, String columnName) throws SQLException {
		try {
			
			Time val = rs.getTime(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getTime [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setTime(PreparedStatement stm, int index, Time sqlTime) throws SQLException {
		if(sqlTime == null) {
			stm.setNull(index, Types.TIME);
		} else {
			stm.setTime(index, sqlTime);
		}
	}
	
	public static Timestamp getTimestamp(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			Timestamp val = rs.getTimestamp(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getTimestamp [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Timestamp getTimestamp(ResultSet rs, String columnName) throws SQLException {
		try {
			
			Timestamp val = rs.getTimestamp(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return val;
			
		} catch (Exception e) {
			log.warn("UDao#getTimestamp [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static void setTimestamp(PreparedStatement stm, int index, java.util.Date javaDate) throws SQLException {
		if(javaDate == null) {
			stm.setNull(index, Types.TIMESTAMP);
		} else {
			Timestamp timestamp = UDateTime.timestamp(javaDate);
			stm.setTimestamp(index, timestamp);
		}
	}
	
	public static Calendar getCalendar(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			Calendar calendar = null;
			Timestamp timestamp = rs.getTimestamp(columnIndex); 
			if(rs.wasNull()) {
				return null;
			}
			
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp.getTime());
			return calendar;
			
		} catch (Exception e) {
			log.warn("UDao#getCalendar [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static Calendar getCalendar(ResultSet rs, String columnName) throws SQLException {
		try {
			
			Calendar calendar = null;
			Timestamp timestamp = rs.getTimestamp(columnName); 
			if(rs.wasNull()) {
				return null;
			}
			
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp.getTime());
			return calendar;
			
		} catch (Exception e) {
			log.warn("UDao#getCalendar [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
	public static java.util.Date getJavaDate(ResultSet rs, int columnIndex) throws SQLException {
		try {
			
			Date slqDate = rs.getDate(columnIndex);
			if(rs.wasNull()) {
				return null;
			}
			return UDateTime.date(slqDate);
			
		} catch (Exception e) {
			log.warn("UDao#getJavaDate [WARNING] ColumnIndex {} not found", columnIndex);
		}
		return null;
	}
	
	public static java.util.Date getJavaDate(ResultSet rs, String columnName) throws SQLException {
		try {
			
			Date slqDate = rs.getDate(columnName);
			if(rs.wasNull()) {
				return null;
			}
			return UDateTime.date(slqDate);
			
		} catch (Exception e) {
			log.warn("UDao#getJavaDate [WARNING] Column {} not found", columnName);
		}
		return null;
	}
	
}