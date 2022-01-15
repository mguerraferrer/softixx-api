package softixx.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.util.UMessage.CustomMessage;

@Slf4j
public class UDateTime {
	public static final String DATE_RANGE_SEPARATOR = " - ";
	public static final String LAST_TIME_OF_DATE = "23:59:59";

	public static Integer CURRENT_DAY = currentDay();
	public static Integer CURRENT_MONTH = currentMonth();
	public static Integer CURRENT_YEAR = currentYear();
	public static String CURRENT_YEAR_2D = currentYear2D();
	public static String CURRENT_TIMESTAMP = currentTimestamp(Formatter.DATE_TIME_FULL_FORMAT);
	
	private static final String LOG_LOCAL_TIME_ERROR = "UDateTime#localTime error - {}";

	public enum Formatter {
		/**
		 * Format: <b>dd/MM/yyyy</b>
		 */
		DATE_SIMPLE_FORMAT("dd/MM/yyyy"),
		/**
		 * Format: <b>dd.MM.yyyy</b>
		 */
		DATE_SIMPLE_DOT_FORMAT("dd.MM.yyyy"),
		/**
		 * Format: <b>yyyy-MM-dd</b>
		 */
		DATE_DB_FORMAT("yyyy-MM-dd"),
		/**
		 * Format: <b>yyyy-MM-dd HH:mm</b>
		 */
		DATE_TIME_SIMPLE_FORMAT("yyyy-MM-dd HH:mm"),
		/**
		 * Forat: <b>yyyy-MM-dd HH:mm:ss</b>
		 */
		DATE_TIME_FULL_FORMAT("yyyy-MM-dd HH:mm:ss"),
		/**
		 * Format: <b>yyyy-MM-dd'T'HH:mm:ss</b>
		 */
		DATE_TIME_T_FORMAT("yyyy-MM-dd'T'HH:mm:ss"),
		/**
		 * Format: <b>dd/MM/yyyy HH:mm:ss</b>
		 */
		DATE_TIME_FORMAT("dd/MM/yyyy HH:mm:ss"),
		/**
		 * Format: <b>dd/MM/yyyy hh:mm:ss a</b>
		 */
		DATE_TIME_MERIDIAN_FORMAT("dd/MM/yyyy hh:mm:ss a"),
		/**
		 * Format: <b>HH:mm:ss</b>
		 */
		TIME("HH:mm:ss"),
		/**
		 * Format: <b>hh:mm a</b>
		 */
		T12H("hh:mm a"),
		/**
		 * Format: <b>hh:mm:ss a</b>
		 */
		T12H_FULL("hh:mm:ss a"),
		/**
		 * Format: <b>HH:mm</b>
		 */
		T24H("HH:mm"),
		/**
		 * Format: <b>HH:mm:ss</b>
		 */
		T24H_FULL("HH:mm:ss");

		private String format;

		Formatter(final String value) {
			format = value;
		}

		public String getFormat() {
			return format;
		}
	}

	/**
	 * Returns current day
	 * 
	 * @return The day-of-month, from 1 to 31
	 */
	private static Integer currentDay() {
		val ld = LocalDate.now();
		return ld.getDayOfMonth();
	}

	/**
	 * Returns current month
	 * 
	 * @return The month-of-year, from 1 to 12
	 */
	private static Integer currentMonth() {
		val ld = LocalDate.now();
		return ld.getMonthValue();
	}

	/**
	 * Returns current year
	 * 
	 * @return The actual year
	 */
	private static Integer currentYear() {
		val ld = LocalDate.now();
		return ld.getYear();
	}

	/**
	 * Returns current year with 2 digits
	 * 
	 * @return The actual year with 2 digits
	 */
	private static String currentYear2D() {
		return Year.now().format(DateTimeFormatter.ofPattern("yy"));
	}

	/**
	 * Returns a current timestamp in String format. <br>
	 * <b>Allows only the following formats:</b> <br>
	 * Formatter.DATE_TIME_FORMAT and Formatter.DATE_TIME_FULL_FORMAT
	 * 
	 * @param formatter {@link Formatter}
	 * @return Current timestamp in String format
	 * @see Formatter
	 */
	public static String currentTimestamp(final Formatter formatter) {
		try {

			String timestamp = null;
			switch (formatter) {
			case DATE_TIME_FORMAT, DATE_TIME_FULL_FORMAT:
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter.format);
				timestamp = dtf.format(LocalDateTime.now());
			default:
				break;
			}
			return timestamp;

		} catch (Exception e) {
			log.error("UDateTime#currentTimeStamp error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Convert Date (java.util.date) to Calendar (java.util.calendar)
	 * 
	 * @param date - java.util.Date
	 * @return A calendar from date
	 */
	public static Calendar dateToCalendar(final Date date) {
		try {

			if (date != null) {
				val calendar = Calendar.getInstance();
				calendar.setTime(date);

				return calendar;
			}

		} catch (Exception e) {
			log.error("UDateTime#dateToCalendar error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Convert Calendar (java.util.Calendar) to Date (java.util.Date)
	 * 
	 * @param calendar - Calendar (java.util.Calendar)
	 * @return A date from calendar
	 */
	public static Date calendarToDate(final Calendar calendar) {
		try {

			if (calendar != null) {
				return calendar.getTime();
			}

		} catch (Exception e) {
			log.error("UDateTime#calendarToDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Format a date (new java.util.Date) with specific formatter
	 * 
	 * @param formatter - {@link Formatter}
	 * @return A string date formatted from the Date
	 * @see Formatter
	 */
	public static String formatDate(final Formatter formatter) {
		try {
			
			if (formatter != null) {
				return formatDate(new Date(), formatter);
			}

		} catch (Exception e) {
			log.error("UDateTime#formatDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Formats a date with a specific format
	 * 
	 * @param date - java.util.Date
	 * @param formatter - {@link Formatter}
	 * @return A string date formatted from the Date
	 * @see Formatter
	 */
	public static String formatDate(final Date date, final Formatter formatter) {
		try {

			if (date != null && formatter != null) {
				val sdf = new SimpleDateFormat(formatter.format);
				return sdf.format(date).toLowerCase();
			}

		} catch (Exception e) {
			log.error("UDateTime#formatDate error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Formats a date with a specific format
	 * 
	 * @param localDate - java.time.LocalDate
	 * @param formatter - {@link Formatter}
	 * @return A string date formatted from the LocalDate
	 * @see Formatter
	 */
	public static String formatDate(final LocalDate localDate, final Formatter formatter) {
		try {

			if (localDate != null) {
				return localDate.format(DateTimeFormatter.ofPattern(formatter.format));
			}

		} catch (Exception e) {
			log.error("UDateTime#formatDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Formats a date with a specific format
	 * 
	 * @param localDateTime - java.time.DateTimeFormatter
	 * @param formatter - {@link Formatter}
	 * @return A string date formatted from the DateTimeFormatter
	 * @see Formatter
	 */
	public static String formatDate(final LocalDateTime localDateTime, final Formatter formatter) {
		try {

			if (localDateTime != null) {
				return localDateTime.format(DateTimeFormatter.ofPattern(formatter.format));
			}

		} catch (Exception e) {
			log.error("UDateTime#formatDate error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Formats a date with a specific format
	 * 
	 * @param localDateTime - java.time.DateTimeFormatter
	 * @param formatter - String formatter
	 * @return A string date formatted from the DateTimeFormatter
	 * @see Formatter
	 */
	public static String formatDate(final LocalDateTime localDateTime, final String formatter) {
		try {

			if (localDateTime != null) {
				return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
			}

		} catch (Exception e) {
			log.error("UDateTime#formatDate error > {}", e.getMessage());
		}
		return null;
	}
	
	public static String formatDate(final java.sql.Date sqlDate, Formatter formatter) {
		String dateStr = null;
		if (sqlDate != null && formatter != null) {
			try {

				val sdf = new SimpleDateFormat(formatter.format);
				dateStr = sdf.format(sqlDate);

			} catch (Exception e) {
				log.error("UDateTime#formatDate error > {}", e.getMessage());
			}
		}
		return dateStr;
	}
	
	/**
	 * Parses a date with a specific format
	 * 
	 * @param dateStr - String date
	 * @param formatter - {@link Formatter}
	 * @return A Date parsed from the string
	 * @see Formatter
	 */
	public static Date parseDate(final String dateStr, final Formatter formatter) {
		if (formatter != null) {
			return parseDate(dateStr, formatter.format);
		}
		return null;
	}
	
	/**
	 * Parses a date with a specific format
	 * 
	 * @param dateStr   String date
	 * @param format 	Formatter to be used
	 * @return A Date parsed from the string
	 * @see Formatter
	 */
	public static Date parseDate(final String dateStr, final String format) {
		try {

			if (UValidator.isNotEmpty(dateStr) && UValidator.isNotEmpty(format)) {
				val sdf = new SimpleDateFormat(format);
				return sdf.parse(dateStr);
			}

		} catch (ParseException e) {
			log.error("--- UDateTime#parseDate error - {}", e);
		}
		return null;
	}

	public static Date parseDateTime(final String dateStr, Formatter formatter, Boolean useLastTime) {
		try {
			
			if(UValidator.isNotEmpty(dateStr) && formatter != null) {
				val date = parseDate(dateStr, formatter);
				val time = (useLastTime) ? parseDate(LAST_TIME_OF_DATE, formatter) : new Date();
				val ldt = localDateTime(date, time);
				return date(ldt);
			}

		} catch (Exception e) {
			log.error("UDateTime#parseDateTime error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the day of month
	 * 
	 * @param date - java.util.Date
	 * @return The day of month (from 1 to 31)
	 */
	public static Integer day(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				return ld.getDayOfMonth();
			}

		} catch (Exception e) {
			log.error("UDateTime#day error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns the day of month of the given date
	 * 
	 * @param date - java.util.Date
	 * @return The month of te year (from 1 to 12)
	 */
	public static Integer month(final Date date) {
		try {
			
			if(date != null) {
				val ldt = localDate(date);
				return ldt.getMonthValue();
			}
			
		} catch (Exception e) {
			log.error("UDateTime#month error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the text (from properties) that represents the name of the month or the abbreviated month name
	 * 
	 * @param date - java.util.Date
	 * @param abbreviation - Boolean
	 * @return The name of the month or the abbreviated month name
	 */
	public static String month(final Date date, final Boolean abbreviation) {
		try {

			if (date != null && abbreviation != null) {
				val month = month(date);
				return month(month, abbreviation);
			}

		} catch (Exception e) {
			log.error("UDateTime#month error > {}", e.getMessage());
		}
		return null;
	}

	public static String month(final Integer month, final Boolean showAbbreviation) {
		return switch (month) {
			case 1 -> !showAbbreviation ? "date.text.month.january" : "date.text.month.january.short";
			case 2 -> !showAbbreviation ? "date.text.month.february" : "date.text.month.february.short";
			case 3 -> !showAbbreviation ? "date.text.month.march" : "date.text.month.march.short";
			case 4 -> !showAbbreviation ? "date.text.month.april" : "date.text.month.april.short";
			case 5 -> !showAbbreviation ? "date.text.month.may" : "date.text.month.may.short";
			case 6 -> !showAbbreviation ? "date.text.month.june" : "date.text.month.june.short";
			case 7 -> !showAbbreviation ? "date.text.month.july" : "date.text.month.july.short";
			case 8 -> !showAbbreviation ? "date.text.month.august" : "date.text.month.august.short";
			case 9 -> !showAbbreviation ? "date.text.month.september" : "date.text.month.september.short";
			case 10 -> !showAbbreviation ? "date.text.month.october" : "date.text.month.october.short";
			case 11 -> !showAbbreviation ? "date.text.month.november" : "date.text.month.november.short";
			case 12 -> !showAbbreviation ? "date.text.month.december" : "date.text.month.december.short";
			default -> null;
		};
	}

	/**
	 * Returns java.time.Month that representing of month name
	 * 
	 * @param monthName - String
	 * @return A month of the year, such as 'July' 
	 */
	public static Month month(final String monthName) {
		try {

			if (UValidator.isNotEmpty(monthName)) {
				val monthNameUpperCase = monthName.toUpperCase();
				return switch (monthNameUpperCase) {
					case "ENERO", "JANUARY", "ENE", "JAN" -> Month.JANUARY;
					case "FEBRERO", "FEBRUARY", "FEB" -> Month.FEBRUARY;
					case "MARZO", "MARCH", "MAR" -> Month.MARCH;
					case "ABRIL", "APRIL", "ABR", "APR" -> Month.APRIL;
					case "MAYO", "MAY" -> Month.MAY;
					case "JUNIO", "JUNE", "JUN" -> Month.JUNE;
					case "JULIO", "JULY", "JUL" -> Month.JULY;
					case "AGOSTO", "AUGUST", "AUG" -> Month.AUGUST;
					case "SEPTIEMBRE", "SEPTEMBER", "SEP" -> Month.SEPTEMBER;
					case "OCTUBRE", "OCTOBER", "OCT" -> Month.OCTOBER;
					case "NOVIEMBRE", "NOVEMBER", "NOV" -> Month.NOVEMBER;
					case "DICIEMBRE", "DECEMBER", "DIC", "DEC" -> Month.DECEMBER;
					default -> null;
				};
			}

		} catch (Exception e) {
			log.error("UDateTime#month error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the month number from 1 to 12
	 * 
	 * @param monthName - String
	 * @return A month of the year 
	 */
	public static Integer monthNumber(final String monthName) {
		try {

			if (UValidator.isNotEmpty(monthName)) {
				val monthNameUpperCase = monthName.toUpperCase();
				val month = month(monthNameUpperCase);
				if (month != null) {
					return month.getValue();
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#monthNumber error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns de year of the given date
	 * @param date - java.util.Date
	 * @return The year
	 */
	public static Integer year(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				return ld.getYear();
			}

		} catch (Exception e) {
			log.error("UDateTime#year error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns de year (two digits) of the given date
	 * @param date - java.util.Date
	 * @return The year (two digits)
	 */
	public static String year2D(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				return ld.format(DateTimeFormatter.ofPattern("yy"));
			}

		} catch (Exception e) {
			log.error("UDateTime#year2D error > {}", e.getMessage());
		}
		return null;
	}

	public static CustomMessage fullDate() {
		return fullDate(new Date());
	}
	
	public static CustomMessage fullDate(final LocalDateTime ldt) {
		val date = date(ldt);
		return fullDate(date);
	}
	
	public static CustomMessage fullDate(final Date date) {
		val localDate = localDate(date);
		return fullDate(localDate);
	}
	
	public static CustomMessage fullDate(final LocalDate localDate) {
		try {
			
			if (localDate != null) {
				String day = null;
				val dayOfWeek = localDate.getDayOfWeek().name();
				switch (dayOfWeek) {
				case "MONDAY":
					day = "date.text.day.monday";
					break;
				case "TUESDAY":
					day = "date.text.day.tuesday";
					break;
				case "WEDNESDAY":
					day = "date.text.day.wednesday";
					break;
				case "THURSDAY":
					day = "date.text.day.thursday";
					break;
				case "FRIDAY":
					day = "date.text.day.friday";
					break;
				case "SATURDAY":
					day = "date.text.day.saturday";
					break;
				case "SUNDAY":
					day = "date.text.day.sunday";
					break;
				}
				
				String month = null;
				val monthOfYear = localDate.getMonth().name();
				switch (monthOfYear) {
				case "JANUARY":
					month = "date.text.month.january";
					break;
				case "FEBRUARY":
					month = "date.text.month.february";
					break;
				case "MARCH":
					month = "date.text.month.march";
					break;
				case "APRIL":
					month = "date.text.month.april";
					break;
				case "MAY":
					month = "date.text.month.may";
					break;
				case "JUNE":
					month = "date.text.month.june";
					break;
				case "JULY":
					month = "date.text.month.july";
					break;
				case "AUGUST":
					month = "date.text.month.august";
					break;
				case "SEPTEMBER":
					month = "date.text.month.september";
					break;
				case "OCTOBER":
					month = "date.text.month.october";
					break;
				case "NOVEMBER":
					month = "date.text.month.november";
					break;
				case "DECEMBER":
					month = "date.text.month.december";
					break;
				}
				
				Integer dayOfMonth = localDate.getDayOfMonth();
				Integer year = localDate.getYear();
				
				val params = new String[] { UMessage.getMessage(day), dayOfMonth.toString(), UMessage.getMessage(month),
						year.toString() };
				val customMessage = CustomMessage.builder().key("date.text.date.full").params(params).build();
				return customMessage;
			}
			
		} catch (Exception e) {
			log.error("UDateTime#fullDate error > {}", e.getMessage());
		}
		return null;
	}

	public static CustomMessage shortDate() {
		val localDate = localDate(new Date());

		Integer day = localDate.getDayOfMonth();
		val dayOfMonth = day >= 10 ? day.toString() : "0" + day;

		Integer month = localDate.getMonthValue();
		val monthStr = month >= 10 ? month.toString() : "0" + month;

		Integer year = localDate.getYear();

		val params = new String[] { dayOfMonth, monthStr, year.toString() };
		val customMessage = CustomMessage.builder().key("date.text.date.short").params(params).build();
		return customMessage;
	}

	public static DayOfWeek dayOfWeek(final String day) {
		try {

			if (UValidator.isNotEmpty(day)) {
				val dayUpperCase = day.toUpperCase();
				switch (dayUpperCase) {
				case "LUNES":
					return DayOfWeek.MONDAY;
				case "MONDAY":
					return DayOfWeek.MONDAY;
				case "MARTES":
					return DayOfWeek.TUESDAY;
				case "TUESDAY":
					return DayOfWeek.TUESDAY;
				case "MIERCOLES":
					return DayOfWeek.WEDNESDAY;
				case "MIÉRCOLES":
					return DayOfWeek.WEDNESDAY;
				case "WEDNESDAY":
					return DayOfWeek.WEDNESDAY;
				case "JUEVES":
					return DayOfWeek.THURSDAY;
				case "THURSDAY":
					return DayOfWeek.THURSDAY;
				case "VIERNES":
					return DayOfWeek.FRIDAY;
				case "FRIDAY":
					return DayOfWeek.FRIDAY;
				case "SABADO":
					return DayOfWeek.SATURDAY;
				case "SÁBADO":
					return DayOfWeek.SATURDAY;
				case "SATURDAY":
					return DayOfWeek.SATURDAY;
				case "DOMINGO":
					return DayOfWeek.SUNDAY;
				case "SUNDAY":
					return DayOfWeek.SUNDAY;
				default:
					return null;
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#dayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static String dayOfWeek(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				return localDate.getDayOfWeek().name();
			}

		} catch (Exception e) {
			log.error("UDateTime#dayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static DayOfWeek getDayOfWeek(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				return localDate.getDayOfWeek();
			}

		} catch (Exception e) {
			log.error("UDateTime#getDayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static Date dayOfWeek(final Date date, final String type, final String day) {
		try {

			if (date != null && UValidator.isNotEmpty(type) && UValidator.isNotEmpty(day)) {
				val localDate = localDate(date);
				return dayOfWeek(localDate, type, day);
			}

		} catch (Exception e) {
			log.error("UDateTime#dayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static Date dayOfWeek(final LocalDate localDate, final String type, final String day) {
		try {

			if (localDate != null && UValidator.isNotEmpty(type) && UValidator.isNotEmpty(day)) {
				if (type.equalsIgnoreCase("First")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))));
				} else if (type.equalsIgnoreCase("Second")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))).plusDays(7));
				} else if (type.equalsIgnoreCase("Third")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))).plusDays(14));
				} else if (type.equalsIgnoreCase("Last")) {
					return date(localDate.with(TemporalAdjusters.lastInMonth(dayOfWeek(day))));
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#dayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate firstDayOfMonth() {
		try {

			val month = YearMonth.from(localDateTime(new Date()));
			return month.atDay(1);

		} catch (Exception e) {
			log.error("UDateTime#firstDayOfMonth error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate lastDayOfMonth() {
		try {

			val month = YearMonth.from(localDateTime(new Date()));
			return month.atEndOfMonth();

		} catch (Exception e) {
			log.error("UDateTime#lastDayOfMonth error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate lastDayOfMonth(final Integer year, final Integer month) {
		try {

			if (year != null && month != null) {
				val ld = LocalDate.of(year, month, 1);
				return ld.with(TemporalAdjusters.lastDayOfMonth());
			}

		} catch (Exception e) {
			log.error("UDateTime#lastDayOfMonth error > {}", e.getMessage());
		}
		return null;
	}

	public static Integer getLastDayOfMonth(final Integer year, final Integer month) {
		try {

			if (year != null && month != null) {
				val ld = LocalDate.of(year, month, 1);
				return ld.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
			}

		} catch (Exception e) {
			log.error("UDateTime#getLastDayOfMonth error > {}", e.getMessage());
		}
		return null;
	}

	public static String firstLastDayOfMonthRange() {
		return firstLastDayOfMonthRange(Formatter.DATE_SIMPLE_FORMAT.format, DATE_RANGE_SEPARATOR);
	}

	public static String firstLastDayOfMonthRange(final String format, final String separator) {
		try {

			if (UValidator.isNotEmpty(format) && UValidator.isNotEmpty(separator)) {
				val sdf = new SimpleDateFormat(format);

				val firstDayOfMonth = firstDayOfMonth();
				val firstDay = date(firstDayOfMonth);
				val firstDayFormatted = sdf.format(firstDay);

				val lastDayOfMonth = lastDayOfMonth();
				val lastDay = date(lastDayOfMonth);
				val lastDayFormatted = sdf.format(lastDay);

				val dateMonthRange = firstDayFormatted + separator + lastDayFormatted;
				return dateMonthRange;
			}

		} catch (Exception e) {
			log.error("UDateTime#firstLastDayOfMonthRange error > {}", e.getMessage());
		}
		return null;
	}

	public static String firstActualDayOfMonthRange() {
		return firstActualDayOfMonthRange(Formatter.DATE_SIMPLE_FORMAT.format, DATE_RANGE_SEPARATOR);
	}

	public static String firstActualDayOfMonthRange(final String format, final String separator) {
		try {

			if (UValidator.isNotEmpty(format) && UValidator.isNotEmpty(separator)) {
				val sdf = new SimpleDateFormat(format);

				val firstDayOfMonth = firstDayOfMonth();
				val firstDay = date(firstDayOfMonth);
				val firstDayFormatted = sdf.format(firstDay);

				val todayOfMonth = localDate(new Date());
				val today = date(todayOfMonth);
				val lastDayFormatted = sdf.format(today);

				val dateMonthRange = firstDayFormatted + separator + lastDayFormatted;
				return dateMonthRange;
			}

		} catch (Exception e) {
			log.error("UDateTime#firstActualDayOfMonthRange error > {}", e.getMessage());
		}
		return null;
	}

	//###########################################
	//##### LocalDate (java.time.LocalDate) #####
	//###########################################
	
	/**
	 * Returns a LocalDate (java.time.LocalDate)
	 * @param stringDate - String date
	 * @param pattern - Formatter ({@link Formatter})
	 * @return LocalDate
	 */
	public static LocalDate localDate(final String stringDate, final Formatter pattern) {
		try {

			if (UValidator.isNotEmpty(stringDate) && pattern != null) {
				val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern.format);
				return LocalDate.parse(stringDate, dateTimeFormatter);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate)
	 * @param year - Year of date (Integer)
	 * @param month - Month of date (Integer)
	 * @param day - Day of date (Integer)
	 * @return LocalDate
	 */
	public static LocalDate localDate(final Integer year, final Integer month, final Integer day) {
		try {

			if (year != null && month != null && day != null) {
				return LocalDate.of(year, month, day);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate) from Date (java.util.Date) 
	 * @param date - java.util.Date
	 * @return LocalDate
	 */
	public static LocalDate localDate(final Date date) {
		try {

			if (date != null) {
				val fDate = formatDate(date, Formatter.DATE_SIMPLE_FORMAT);
				val dtf = DateTimeFormatter.ofPattern(Formatter.DATE_SIMPLE_FORMAT.format);
				return LocalDate.parse(fDate, dtf);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate) from LocalDateTime (java.time.LocalDateTime) 
	 * @param ldt - LocalDateTime (java.time.LocalDateTime)
	 * @return LocalDate
	 */
	public static LocalDate localDate(final LocalDateTime ldt) {
		if (ldt != null) {
			return ldt.toLocalDate();
		}
		return null;
	}

	public static LocalTime localTime(final Date date) {
		try {

			if (date != null) {
				val calendar = dateToCalendar(date);
				return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
						calendar.get(Calendar.SECOND));
			}

		} catch (Exception e) {
			log.error("UDateTime#localTime error > {}", e.getMessage());
		}
		return null;
	}
	
	public static String sanitizeDate(final String dateStr) {
		if(UValidator.isNotEmpty(dateStr)) {
			return dateStr.toUpperCase();
		}
		return UValue.EMPTY;
	}
	
	public static String sanitizeTime(final String dateStr) {
		try {
			
			if(UValidator.isNotEmpty(dateStr)) {
				val timeStr = dateStr.toUpperCase();
				val hour = timeStr.split(Pattern.quote(":"))[0];
				if(hour.length() < 2) {
					return "0" + timeStr;
				}
				return timeStr;
			}
			
		} catch (Exception e) {
			log.error("UDateTime error - {}", e);
		}
		return UValue.EMPTY;
	}
	
	public static LocalTime localTime(final String dateStr, final Formatter formatter) {
		try {
			
			if(UValidator.isNotEmpty(dateStr) && UValidator.isNotEmpty(formatter)) {
				val format = DateTimeFormatter.ofPattern(formatter.format);
				return LocalTime.parse(sanitizeTime(dateStr), format);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime() {
		return localDateTime(new Date());
	}

	public static LocalDateTime localDateTime(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				val localTime = localTime(date);
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDateTime error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDateTime localDateTime(final Date date, final Date time) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				val localTime = localTime(time);
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDateTime error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDateTime localDateTime(final String date, final Formatter formatter) {
		try {

			if (UValidator.isNotEmpty(date) && formatter != null) {
				val format = DateTimeFormatter.ofPattern(formatter.format);
				val ldt = LocalDateTime.parse(date, format);
				return ldt;
			}

		} catch (Exception e) {
			log.error("UDateTime#localDateTime error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDateTime localDateTime(final LocalDate localDate, final LocalTime localTime) {
		try {

			if (localDate != null && localTime != null) {
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#localDateTime error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDateTime localDateTime(final LocalDate localDate) {
		try {

			if (localDate != null) {
				return localDate.atTime(LocalTime.now());
			}

		} catch (Exception e) {
			log.error("UDateTime#localDateTime error > {}", e.getMessage());
		}
		return null;
	}

	public static Date date(final Integer year, final Integer month, final Integer day) {
		try {

			if (year != null && month != null && day != null) {
				val localDate = LocalDate.of(year, month, day);
				return date(localDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#date error > {}", e.getMessage());
		}
		return null;
	}

	public static Date date(final LocalDate localDate) {
		try {

			if (localDate != null) {
				val instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				return Date.from(instant);
			}

		} catch (Exception e) {
			log.error("UDateTime#date error > {}", e.getMessage());
		}
		return null;
	}

	public static Date date(final LocalDateTime localDateTime) {
		try {

			if (localDateTime != null) {
				val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
				return Date.from(instant);
			}

		} catch (Exception e) {
			log.error("UDateTime#date error > {}", e.getMessage());
		}
		return null;
	}

	public static boolean isBeforeLocalDate(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldOne = localDate(dateOne);
				val ldTwo = localDate(dateTwo);
				return isBeforeLocalDate(ldOne, ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDate error > {}", e.getMessage());
		}
		return false;
	}
	
	public static boolean isBeforeLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return ldOne.isBefore(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterLocalDate(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val dateOneLocalDate = localDate(dateOne);
				val dateTwoLocaldate = localDate(dateTwo);
				return dateOneLocalDate.isAfter(dateTwoLocaldate);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return ldOne.isAfter(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isEqualLocalDate(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val dateOneLocalDate = localDate(dateOne);
				val dateTwoLocaldate = localDate(dateTwo);
				return dateOneLocalDate.isEqual(dateTwoLocaldate);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isEqualLocalDate(final LocalDate dateOneLocalDate, final LocalDate dateTwoLocaldate) {
		try {

			if (dateOneLocalDate != null && dateTwoLocaldate != null) {
				return dateOneLocalDate.isEqual(dateTwoLocaldate);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDate(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val dateOneLocalDate = localDate(dateOne);
				val dateTwoLocaldate = localDate(dateTwo);
				return dateOneLocalDate.isBefore(dateTwoLocaldate) || dateOneLocalDate.isEqual(dateTwoLocaldate);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			return ldOne.isBefore(ldTwo) || ldOne.isEqual(ldTwo);

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalDate(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val dateOneLocalDate = localDate(dateOne);
				val dateTwoLocaldate = localDate(dateTwo);
				if (dateOneLocalDate != null && dateTwoLocaldate != null) {
					return dateOneLocalDate.isAfter(dateTwoLocaldate) || dateOneLocalDate.isEqual(dateTwoLocaldate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}
	
	public static boolean isAfterOrEqualLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return ldOne.isAfter(ldTwo) || ldOne.isEqual(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalDate error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val timeOne = localTime(dateOne);
				val timeTwo = localTime(dateTwo);
				return timeOne.isBefore(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val timeOne = localTime(dateOne);
				val timeTwo = localTime(dateTwo);
				return timeOne.isAfter(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isEqualLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val timeOne = localTime(dateOne);
				val timeTwo = localTime(dateTwo);
				return timeOne.equals(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val timeOne = localTime(dateOne);
				val timeTwo = localTime(dateTwo);
				return (timeOne.isBefore(timeTwo) || timeOne.equals(timeTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val timeOne = localTime(dateOne);
				val timeTwo = localTime(dateTwo);
				return (timeOne.isAfter(timeTwo) || timeOne.equals(timeTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeLocalDateTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldtOne = localDateTime(dateOne);
				val ldtTwo = localDateTime(dateTwo);
				return ldtOne.isBefore(ldtTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeLocalDateTime(final LocalDateTime ldtOne, LocalDateTime ldtTwo) {
		try {

			if (ldtOne != null && ldtTwo != null) {
				return ldtOne.isBefore(ldtTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterLocalDateTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldtOne = localDateTime(dateOne);
				val ldtTwo = localDateTime(dateTwo);
				return ldtOne.isAfter(ldtTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterLocalDateTime(final LocalDateTime ldtOne, LocalDateTime ldtTwo) {
		try {

			if (ldtOne != null && ldtTwo != null) {
				return ldtOne.isAfter(ldtTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldtOne = localDateTime(dateOne);
				val ldtTwo = localDateTime(dateTwo);
				return ldtOne.equals(ldtTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldtOne = localDateTime(dateOne);
				val ldtTwo = localDateTime(dateTwo);
				return (ldtOne.isBefore(ldtTwo) || ldtOne.equals(ldtTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDateTime(final LocalDateTime ldtOne, LocalDateTime ldtTwo) {
		try {

			if (ldtOne != null && ldtTwo != null) {
				return (ldtOne.isBefore(ldtTwo) || ldtOne.equals(ldtTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val ldtOne = localDateTime(dateOne);
				val ldtTwo = localDateTime(dateTwo);
				return (ldtOne.isAfter(ldtTwo) || ldtOne.equals(ldtTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalDateTime error > {}", e.getMessage());
		}
		return false;
	}

	public static boolean isAfterOrEqualLDT(final LocalDateTime ldtOne, LocalDateTime ldtTwo) {
		try {

			if (ldtOne != null && ldtTwo != null) {
				return (ldtOne.isAfter(ldtTwo) || ldtOne.equals(ldtTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLDT error > {}", e.getMessage());
		}
		return false;
	}

	//####################
	//##### Duration #####
	//####################
	/**
	 * Returns a duration between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two dates
	 */
	public static Duration duration(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return Duration.between(localDateTime(dateOne), localDateTime(dateTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#duration error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration between two dates
	 * @param ldOne - From
	 * @param ldTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Duration duration(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return Duration.between(ldOne, ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#duration error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in days between two dates
	 * @param ldOne - From
	 * @param ldTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInDates(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				val duration = Duration.between(ldOne, ldTwo);
				Long durationInDays = duration.toDays();
				return durationInDays.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInDates error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in days between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInDates(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				Long durationInDays = duration.toDays();
				return durationInDays.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInDates error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in days between two dates without weekends
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInDatesWithoutWeekend(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				var start = localDate(dateOne);
				val stop = localDate(dateTwo);

				var count = 0;

				Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
				while (start.isBefore(stop)) {
					if (!weekend.contains(start.getDayOfWeek())) {
						//##### It is not weekend
						count++;
					}

					//##### Increment a day
					start = start.plusDays(1);
				}
				return (int) count;
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInDatesWithoutWeekend error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in hours between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInHours(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				Long durationInHours = duration.toHours();
				return durationInHours.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInHours error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in minutes between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInMinutes(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				Long durationInMinutes = duration.toMinutes();
				return durationInMinutes.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInMinutes error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in seconds between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInSeconds(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				Long durationInMinutes = duration.toSeconds();
				return durationInMinutes.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInSeconds error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a duration (int) in milliseconds between two dates
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @return A java.time.Duration between two date
	 */
	public static Integer durationInMilliseconds(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				Long durationInMilliseconds = duration.toMillis();
				return durationInMilliseconds.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInMilliseconds error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the duration (int) between a given date and current date
	 * @param date - Date from
	 * @param time - Time from
	 * @param type - (String) Has to be either 'days', 'hours', 'minutes', 'seconds', 'millis' or 'nanos'
	 * @return A java.time.Duration between two date
	 */
	public static Integer elapsedTime(final Date date, final Date time, final String type) {
		try {

			if (date != null && time != null && UValidator.isNotEmpty(type)) {
				val ldt = localDateTime(localDate(date), localTime(time));
				val duration = Duration.between(ldt, localDateTime(new Date()));

				Long durationValue = switch (type) {
					case "days" -> duration.toDays();
					case "hours" -> duration.toHours();
					case "minutes" -> duration.toMinutes();
					case "seconds" -> duration.toSeconds();
					case "millis" -> duration.toMillis();
					case "nanos" -> duration.toNanos();
					default -> null;
				};
				return durationValue.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#elapsedTime error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns the duration (int) between a given date and current date
	 * @param ldt - LocalDateTime from
	 * @param type - (String) Has to be either 'days', 'hours', 'minutes', 'seconds', 'millis' or 'nanos'
	 * @return A java.time.Duration between LocalDateTime and current date (now)
	 */
	public static Integer elapsedTime(final LocalDateTime ldt, final String type) {
		try {

			if (ldt != null && UValidator.isNotEmpty(type)) {
				val duration = Duration.between(ldt, localDateTime(new Date()));

				Long durationValue = switch (type) {
					case "days" -> duration.toDays();
					case "hours" -> duration.toHours();
					case "minutes" -> duration.toMinutes();
					case "seconds" -> duration.toSeconds();
					case "millis" -> duration.toMillis();
					case "nanos" -> duration.toNanos();
					default -> null;
				};
				return durationValue.intValue();
			}

		} catch (Exception e) {
			log.error("UDateTime#elapsedTime error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the duration (long) between two dates using java.time.temporal.ChronoUnit
	 * @param dateOne - From
	 * @param dateTwo - To
	 * @param chronoUnit - A chronoUnit value
	 * @return A long value that represents the difference between two dates determined by the chronoUnit value provided
	 */
	public static Long elapsedTime(final Date dateOne, final Date dateTwo, final ChronoUnit chronoUnit) {
		try {

			if (dateOne != null && dateTwo != null) {
				return elapsedTime(localDateTime(dateOne), localDateTime(dateTwo), chronoUnit);
			}

		} catch (Exception e) {
			log.error("UDateTime#elapsedTime error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the duration (long) between two dates using java.time.temporal.ChronoUnit
	 * @param from - Date from
	 * @param to - Date to
	 * @param chronoUnit - A chronoUnit value
	 * @return A long value that represents the difference between two dates determined by the chronoUnit value provided
	 */
	public static Long elapsedTime(final LocalDateTime from, final LocalDateTime to, final ChronoUnit chronoUnit) {
		try {

			if (from != null && to != null) {
				return switch (chronoUnit) {
					case YEARS -> ChronoUnit.YEARS.between(from, to);
					case MONTHS -> ChronoUnit.MONTHS.between(from, to);
					case WEEKS -> ChronoUnit.WEEKS.between(from, to);
					case DAYS -> ChronoUnit.DAYS.between(from, to);
					case HOURS -> ChronoUnit.HOURS.between(from, to);
					case MINUTES -> ChronoUnit.MINUTES.between(from, to);
					case SECONDS -> ChronoUnit.SECONDS.between(from, to);
					case MILLIS -> ChronoUnit.MILLIS.between(from, to);
					case NANOS -> ChronoUnit.NANOS.between(from, to);
					default -> null;
				};
			}

		} catch (Exception e) {
			log.error("UDateTime#elapsedTime error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns the UTC date
	 * @return the UTC date
	 */
	public static Date dateUTC() {
		try {

			val zdt = ZonedDateTime.now(ZoneOffset.UTC);
			val ldt = zdt.toLocalDateTime();
			return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

		} catch (Exception e) {
			log.error("UDateTime#dateUTC error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns the UTC {@code LocalDateTime}
	 * @return the UTC {@code LocalDateTime}
	 */
	public static LocalDateTime localDateTimeUTC() {
		try {

			val zdt = ZonedDateTime.now(ZoneOffset.UTC);
			return zdt.toLocalDateTime();

		} catch (Exception e) {
			log.error("UDateTime#localDateTimeUTC error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns the UTC {@code LocalDate}
	 * @return the UTC {@code LocalDate}
	 */
	public static LocalDate localDateUTC() {
		try {

			val zdt = ZonedDateTime.now(ZoneOffset.UTC);
			return zdt.toLocalDate();

		} catch (Exception e) {
			log.error("UDateTime#localDateUTC error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns the UTC {@code LocalDate}
	 * @return the UTC {@code LocalDate}
	 */
	public static LocalTime localTimeUTC() {
		try {

			val zdt = ZonedDateTime.now(ZoneOffset.UTC);
			return zdt.toLocalTime();

		} catch (Exception e) {
			log.error("UDateTime#localTimeUTC error > {}", e.getMessage());
		}
		return null;
	}
	
	public static Date convertToUTC(Date date) {
		val ldt = localDateTime(date);
		if (ldt != null) {
			val utc = convertToUTC(ldt);
			if (utc != null) {
				return date(utc);
			}
		}
		return null;
	}
	
	public static LocalDateTime convertToUTC(LocalDateTime ldt) {
		if (ldt != null) {
			return ldt.atZone(ZoneId.systemDefault())
					  .withZoneSameInstant(ZoneOffset.UTC)
					  .toLocalDateTime();
		}
		return null;
	}
	
	public static LocalDate convertToUTC(LocalDate ld) {
		if (ld != null) {
			val ldt = localDateTime(ld);
			if (ldt != null) {
				return ldt.atZone(ZoneId.systemDefault())
						  .withZoneSameInstant(ZoneOffset.UTC)
						  .toLocalDate();
			}
		}
		return null;
	}
	
	public static Date convertFromUTC(Date date) {
		val timeZone = getTimeZone(formatDate(Formatter.DATE_TIME_T_FORMAT));
		return convertFromUTC(date, timeZone);
	}
	
	public static Date convertFromUTC(Date date, String timeZone) {
		val ldt = localDateTime(date);
		if (ldt != null && timeZone != null) {
			return Date.from(ldt.atZone(ZoneOffset.UTC)
					   .withZoneSameInstant(ZoneId.of(timeZone))
					   .toInstant());
		}
		return null;
	}
	
	public static LocalDate convertFromUTC(LocalDate ld) {
		val timeZone = getTimeZone(formatDate(Formatter.DATE_TIME_T_FORMAT));
		return convertFromUTC(ld, timeZone);
	}
	
	public static LocalDate convertFromUTC(LocalDate ld, String timeZone) {
		val ldt = localDateTime(ld);
		if (ldt != null && timeZone != null) {
			return ldt.atZone(ZoneOffset.UTC)
					  .withZoneSameInstant(ZoneId.of(timeZone))
					  .toLocalDate();
		}
		return null;
	}
	
	public static LocalDateTime convertFromUTC(LocalDateTime ldt) {
		val timeZone = getTimeZone(formatDate(Formatter.DATE_TIME_T_FORMAT));
		return convertFromUTC(ldt, timeZone);
	}
	
	public static LocalDateTime convertFromUTC(LocalDateTime ldt, String timeZone) {
		if (ldt != null && timeZone != null) {
			return ldt.atZone(ZoneOffset.UTC)
					  .withZoneSameInstant(ZoneId.of(timeZone))
					  .toLocalDateTime();
		}
		return null;
	}
	
	public static String getTimeZone(final String date) {
		try {
			
			val sanitizedDate = sanitizeDate(date);
			if (UValidator.isNotEmpty(sanitizedDate)) {
				
				val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
				val zonedDateTime = ZonedDateTime.parse(sanitizedDate, formatter);
				val timeZone = TimeZone.getTimeZone(zonedDateTime.getZone());
				return Arrays.stream(TimeZone.getAvailableIDs(timeZone.getRawOffset()))
							 .findFirst()
							 .map(item -> item)
							 .orElse(UValue.EMPTY);
			}
			
		} catch (Exception e) {
			log.error("UDateTime#getTimeZone error > {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns true if the given year is a leap year
	 * @param year - Four-number year
	 * @return True/False
	 */
	public static Boolean isLeapYear(final Integer year) {
		try {

			if (year != null) {
				return Year.isLeap(year);
			}

		} catch (Exception e) {
			log.error("UDateTime#isLeapYear error > {}", e.getMessage());
		}
		return false;
	}

	/**
	 * Add years to a date
	 * @param date - Given date
	 * @param years - (Long) years
	 * @return The given date plus the indicated years
	 */
	public static Date plusYear(final Date date, final Long years) {
		try {

			if (date != null && years != null) {
				val currentTime = localDate(date);
				val nextDate = currentTime.plusYears(years);
				return date(nextDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusYear error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add years to current date
	 * @param years - (Long) years
	 * @return The current date plus the indicated years
	 */
	public static Date plusYear(final Long years) {
		try {

			if (years != null) {
				return plusYear(new Date(), years);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusYear error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts years from a date
	 * @param years - (Long) years
	 * @return The given date minus the indicated years
	 */
	public static Date minusYear(final Date date, final Long years) {
		try {

			if (date != null && years != null) {
				val currentTime = localDate(date);
				val beforeDate = currentTime.minusYears(years);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusYear error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts years from current date
	 * @param years - (Long) years
	 * @return The current date minus the indicated years
	 */
	public static Date minusYear(final Long years) {
		try {

			if (years != null) {
				return minusYear(new Date(), years);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusYear error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add months to a date
	 * @param date - Given date
	 * @param months - (Long) months
	 * @return The given date plus the indicated months
	 */
	public static Date plusMonth(final Date date, final Long months) {
		try {

			if (date != null && months != null) {
				val currentTime = localDate(date);
				val nextDate = currentTime.plusMonths(months);
				return date(nextDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMonth error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Adds months to current date
	 * @param months - (Long) months
	 * @return The current date plus the indicated months
	 */
	public static Date plusMonth(final Long months) {
		try {

			if (months != null) {
				return plusMonth(new Date(), months);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMonth error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts months from a date
	 * @param months - (Long) months
	 * @return The given date minus the indicated months
	 */
	public static Date minusMonth(final Date date, final Long months) {
		try {

			if (date != null && months != null) {
				val currentTime = localDate(date);
				val beforeDate = currentTime.minusMonths(months);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMonth error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts months from current date
	 * @param months - (Long) months
	 * @return The current date minus the indicated months
	 */
	public static Date minusMonth(final Long months) {
		try {

			if (months != null) {
				return minusMonth(new Date(), months);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMonth error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add days to a date
	 * @param date - Given date
	 * @param days - (Long) days
	 * @return The given date plus the indicated days
	 */
	public static Date plusDays(final Date date, final Long days) {
		try {

			if (date != null && days != null) {
				val currentTime = localDateTime(date);
				val nextDate = currentTime.plusDays(days);
				return date(nextDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusDays error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add days to current date
	 * @param days - (Long) days
	 * @return The current date plus the indicated days
	 */
	public static Date plusDays(final Long days) {
		try {

			if (days != null) {
				return plusDays(new Date(), days);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusDays error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts days from a date
	 * @param days - (Long) days
	 * @return The given date minus the indicated days
	 */
	public static Date minusDays(final Date date, final Long days) {
		try {

			if (date != null && days != null) {
				val currentTime = localDate(date);
				val beforeDate = currentTime.minusDays(days);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusDays error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts days from current date
	 * @param days - (Long) days
	 * @return The current date minus the indicated days
	 */
	public static Date minusDays(final Long days) {
		try {

			if (days != null) {
				minusDays(new Date(), days);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusDays error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add hours to a date
	 * @param date - Given date
	 * @param hours - (Long) hours
	 * @return The given date plus the indicated hours
	 */
	public static Date plusHours(final Date date, final Long hours) {
		try {

			if (date != null && hours != null) {
				val currentTime = localDateTime(date);
				val nextTime = currentTime.plusHours(hours);
				return date(nextTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusHours error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add hours to current date
	 * @param hours - (Long) hours
	 * @return The current date plus the indicated hours
	 */
	public static Date plusHours(final Long hours) {
		try {

			if (hours != null) {
				val currentTime = localDateTime(new Date());
				val nextTime = currentTime.plusHours(hours);
				return date(nextTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusHours error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts hours from a date
	 * @param hours - (Long) hours
	 * @return The given date minus the indicated hours
	 */
	public static Date minusHours(final Date date, final Long hours) {
		try {

			if (date != null && hours != null) {
				val currentTime = localDateTime(date);
				val beforeDate = currentTime.minusHours(hours);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusHours error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts hours from current date
	 * @param hours - (Long) hours
	 * @return The current date minus the indicated hours
	 */
	public static Date minusHours(final Long hours) {
		try {

			if (hours != null) {
				return minusHours(new Date(), hours);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusHours error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add minutes to a date
	 * @param date - Given date
	 * @param minutes - (Long) minutes
	 * @return The given date plus the indicated minutes
	 */
	public static Date plusMinutes(final Date date, final Long minutes) {
		try {

			if (date != null && minutes != null) {
				val currentTime = localDateTime(date);
				val nextTime = currentTime.plusMinutes(minutes);
				return date(nextTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMinutes error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add minutes to current date
	 * @param minutes - (Long) minutes
	 * @return The current date plus the indicated minutes
	 */
	public static Date plusMinutes(final Long minutes) {
		try {

			if (minutes != null) {
				return plusMinutes(new Date(), minutes);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMinutes error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts minutes from a date
	 * @param minutes - (Long) minutes
	 * @return The given date minus the indicated minutes
	 */
	public static Date minusMinutes(final Date date, final Long minutes) {
		try {

			if (date != null && minutes != null) {
				val currentTime = localDateTime(date);
				val beforeDate = currentTime.minusMinutes(minutes);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMinutes error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts minutes from current date
	 * @param minutes - (Long) minutes
	 * @return The current date minus the indicated minutes
	 */
	public static Date minusMinutes(final Long minutes) {
		try {

			if (minutes != null) {
				return minusMinutes(new Date(), minutes);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMinutes error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add seconds to a date
	 * @param date - Given date
	 * @param seconds - (Long) seconds
	 * @return The given date plus the indicated seconds
	 */
	public static Date plusSeconds(final Date date, final Long seconds) {
		try {

			if (date != null && seconds != null) {
				val currentTime = localDateTime(date);
				val nextTime = currentTime.plusSeconds(seconds);
				return date(nextTime);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusSeconds error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Add seconds to current date
	 * @param seconds - (Long) seconds
	 * @return The current date plus the indicated seconds
	 */
	public static Date plusSeconds(final Long seconds) {
		try {

			if (seconds != null) {
				return plusSeconds(new Date(), seconds);
			}

		} catch (Exception e) {
			log.error("UDateTime#plusSeconds error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts seconds from a date
	 * @param seconds - (Long) seconds
	 * @return The given date minus the indicated seconds
	 */
	public static Date minusSeconds(final Date date, final Long seconds) {
		try {

			if (date != null && seconds != null) {
				val currentTime = localDateTime(date);
				val beforeDate = currentTime.minusSeconds(seconds);
				return date(beforeDate);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusSeconds error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Subtracts seconds from current date
	 * @param seconds - (Long) seconds
	 * @return The current date minus the indicated seconds
	 */
	public static Date minusSeconds(final Long seconds) {
		try {

			if (seconds != null) {
				return minusSeconds(new Date(), seconds);
			}

		} catch (Exception e) {
			log.error("UDateTime#minusSeconds error > {}", e.getMessage());
		}
		return null;
	}

	//##################
	//##### Period #####
	//##################
	public static Period period(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return Period.between(localDate(dateOne), localDate(dateTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#period error > {}", e.getMessage());
		}
		return null;
	}

	public static Period period(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return Period.between(ldOne, ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#period error > {}", e.getMessage());
		}
		return null;
	}

	public static Integer periodMonthsInt(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return Period.between(localDate(dateOne), localDate(dateTwo)).getMonths();
			}

		} catch (Exception e) {
			log.error("UDateTime#periodMonthsInt error > {}", e.getMessage());
		}
		return null;
	}
	
	public static Integer periodYearsInt(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return Period.between(localDate(dateOne), localDate(dateTwo)).getYears();
			}

		} catch (Exception e) {
			log.error("UDateTime#periodYearsInt error > {}", e.getMessage());
		}
		return null;
	}

	//######################
	//##### ChronoUnit #####
	//######################
	public static Long periodYearsLong(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return ChronoUnit.YEARS.between(localDate(dateOne), localDate(dateTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#periodYearsLong error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Sorts a list of date
	 * @param dateList
	 * @return
	 */
	public static List<Date> sortDate(List<Date> dateList) {
		try {

			if (!dateList.isEmpty()) {
				dateList.sort((ld1, ld2) -> ld1.compareTo(ld2));
				return dateList;
			}

		} catch (Exception e) {
			log.error("UDateTime#sortDate error > {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Returns true if the date represents a weekend
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
				return weekend.contains(localDate.getDayOfWeek());
			}

		} catch (Exception e) {
			log.error("UDateTime#isWeekend error > {}", e.getMessage());
		}
		return false;
	}

	public static Integer hour(final Date date) {
		try {

			if (date != null) {
				val time = formatDate(date, Formatter.T12H);
				val splitted = time.split(":");
				return UInteger.value(splitted[0]);
			}

		} catch (Exception e) {
			log.error("UDateTime#hour error > {}", e.getMessage());
		}
		return null;
	}

	public static String[] daysOfWeek() {
		val monday = "date.text.day.monday";
		val tuesday = "date.text.day.tuesday";
		val wednesday = "date.text.day.wednesday";
		val thursday = "date.text.day.thursday";
		val friday = "date.text.day.friday";
		val saturday = "date.text.day.saturday";
		val sunday = "date.text.day.sunday";
		return new String[] { monday, tuesday, wednesday, thursday, friday, saturday, sunday };
	}

	public static LocalDate nextWeek(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				val dayOfWeek = localDate.getDayOfWeek().getValue();

				LocalDate nextWeek = null;
				switch (dayOfWeek) {
				case 1:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
					break;
				case 2:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
					break;
				case 3:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
					break;
				case 4:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
					break;
				case 5:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
					break;
				case 6:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
					break;
				case 7:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					break;
				default:
					break;
				}
				return nextWeek;
			}

		} catch (Exception e) {
			log.error("UDateTime#nextWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate previousDay(final Date date, final DayOfWeek dayOfWeek) {
		try {

			if (date != null && dayOfWeek != null) {
				val localDate = localDate(date);
				return localDate.with(TemporalAdjusters.previous(dayOfWeek));
			}

		} catch (Exception e) {
			log.error("UDateTime#previousDay error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate nextDay(final Date date, final DayOfWeek dayOfWeek) {
		try {

			if (date != null && dayOfWeek != null) {
				val localDate = localDate(date);
				return localDate.with(TemporalAdjusters.next(dayOfWeek));
			}

		} catch (Exception e) {
			log.error("UDateTime#nextDay error > {}", e.getMessage());
		}
		return null;
	}

	public static Integer compareDayOfWeek(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val dowOne = getDayOfWeek(dateOne);
				val dowTwo = getDayOfWeek(dateTwo);
				return compareDayOfWeek(dowOne, dowTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#compareDayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	public static Integer compareDayOfWeek(final DayOfWeek dowOne, final DayOfWeek dowTwo) {
		try {

			return dowOne.compareTo(dowTwo);

		} catch (Exception e) {
			log.error("UDateTime#compareDayOfWeek error > {}", e.getMessage());
		}
		return null;
	}

	//######################################################################################
	//##### Date (java.sql.Date), Time (java.sql.Time), Timestamp (java.sql.Timestamp) #####
	//######################################################################################
	public static String formatDate(final java.sql.Timestamp timestamp, Formatter formatter) {
		String dateStr = null;
		if (timestamp != null && formatter != null) {
			try {
				
				/*val ldt = localDateTime(timestamp);
				dateStr = ldt.format(DateTimeFormatter.ofPattern(formatter.format));*/
				val sdf = new SimpleDateFormat(formatter.format);
				dateStr = sdf.format(timestamp);

			} catch (Exception e) {
				log.error("UDateTime#formatDate(java.sql.Timestamp) error > {}", e.getMessage());
			}
		}
		return dateStr;
	}
	
	public static java.sql.Date sqlDate(final Date date) {
		try {
			
			if (date != null) {
				return new java.sql.Date(date.getTime());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#sqlDate(Date) error > {}", e.getMessage());
		}
		return null;
	}

	public static Date date(final java.sql.Date sqlDate) {
		try {
			
			if (sqlDate != null) {
				return new Date(sqlDate.getTime());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#date(java.sql.Date) error > {}", e.getMessage());
		}
		return null;
	}
	
	public static Date date(final java.sql.Time sqlTime) {
		try {
			
			if (sqlTime != null) {
				return new Date(sqlTime.getTime());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#date(java.sql.Time) error > {}", e.getMessage());
		}
		return null;
	}

	public static java.sql.Time sqlTime(final Date date) {
		try {
			
			if (date != null) {
				return new java.sql.Time(date.getTime());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#sqlTime(Date) error > {}", e.getMessage());
		}
		return null;
	}

	public static java.sql.Timestamp timestamp(final Date javaDate) {
		try {
			
			if (javaDate != null) {
				val calendar = UDateTime.dateToCalendar(javaDate);
				return new java.sql.Timestamp(calendar.getTimeInMillis());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#timestamp(Date) error > {}", e.getMessage());
		}
		return null;
	}
	
	public static java.sql.Timestamp timestamp(final LocalDate javaLd) {
		try {
			
			if (javaLd != null) {
				val timestamp = java.sql.Timestamp.valueOf(javaLd.atStartOfDay()); 
				return timestamp;
			}
			
		} catch (Exception e) {
			log.error("UDateTime#timestamp(LocalDate) error > {}", e.getMessage());
		}
		return null;
	}
	
	public static java.sql.Timestamp timestamp(final LocalDateTime javaLdt) {
		try {
			
			if (javaLdt != null) {
				val timestamp = java.sql.Timestamp.valueOf(javaLdt); 
				return timestamp;
			}
			
		} catch (Exception e) {
			log.error("UDateTime#timestamp(LocalDateTime) error > {}", e.getMessage());
		}
		return null;
	}

	public static Date date(final java.sql.Timestamp timestamp) {
		try {
			
			if (timestamp != null) {
				return new Date(timestamp.getTime());
			}
			
		} catch (Exception e) {
			log.error("UDateTime#date(Timestamp) error > {}", e.getMessage());
		}
		return null;
	}

	public static LocalDate localDate(final java.sql.Timestamp timestamp) {
		if (timestamp != null) {
			try {
				
				/*val date = date(timestamp);
				return localDate(date);*/
				return timestamp.toLocalDateTime().toLocalDate();
				
			} catch (Exception e) {
				log.error("UDateTime#localDate(Timestamp) error > {}", e.getMessage());
			}
		}
		return null;
	}

	public static LocalDateTime localDateTime(final java.sql.Timestamp timestamp) {
		if (timestamp != null) {
			try {
				
				/*val date = date(timestamp);
				return localDateTime(date);*/
				return timestamp.toLocalDateTime();
				
			} catch (Exception e) {
				log.error("UDateTime#localDateTime(Timestamp) error > {}", e.getMessage());
			}
		}
		return null;
	}

}