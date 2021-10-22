package softixx.api.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.val;
import softixx.api.util.UDateTime.Formatter;
import softixx.api.util.UMessage.CustomMessage;

public class UAge {
	private static final Logger log = LoggerFactory.getLogger(UAge.class);
	
	public static String age(LocalDate birthDate) {
		String age = "-";
		if(birthDate != null) {
			Period period = period(birthDate);
			
			String years = null; 
			if(period.getYears() >= 1) {
				years = (period.getYears() == 1) ? "1 año" : period.getYears() + " años";
			}
			
			String months = null;
			if(period.getMonths() >= 1) {
				months = (period.getMonths() == 1) ? "1 mes" : period.getMonths() + " meses";
			}
			
			String days = null;
			if(period.getDays() == 0) {
				if(years == null && months == null) {
					days = "Recién nacido";
				}
			} else if(period.getDays() == 1) {
				days = "1 día";
			} else {
				days = period.getDays() + " días";
			}
			
			if(years != null) {
				return years;
			} else if(months != null) {
				return months;
			} else if(days != null) {
				return days;
			}
		}
		return age;
	}
	
	public static String ageDetailed(LocalDate birthDate) {
		String age = "-";
		if(birthDate != null) {
			Period period = period(birthDate);
			
			String years = null; 
			if(period.getYears() >= 1) {
				years = (period.getYears() == 1) ? "1 año" : period.getYears() + " años";
			}
			
			String months = null;
			if(period.getMonths() >= 1) {
				months = (period.getMonths() == 1) ? "1 mes" : period.getMonths() + " meses";
			}
			
			String days = null;
			if(period.getDays() == 0) {
				if(years == null && months == null) {
					days = "Recién nacido";
				}
			} else if(period.getDays() == 1) {
				days = "1 día";
			} else {
				days = period.getDays() + " días";
			}
			
			if(years != null) {
				if(months != null && days != null) {
					age = years + ", " + months + " y " + days;
				} else if(months != null && days == null) {
					age = years + " y " + months;
				} else if(months == null && days != null) {
					age = years + " y " + days;
				} else {
					age = years;
				}
			} else {
				if(months != null && days != null) {
					age = months + " y " + days;
				} else if(months != null && days == null) {
					age = months;
				} else if(months == null && days != null) {
					age = days;
				}
			}
		}
		return age;
	}
	
	public static Integer age(final String date) {
		try {

			if (UValidator.isNotEmpty(date)) {
				val birthDate = UDateTime.parseDate(date, Formatter.DATE_SIMPLE_FORMAT);
				return age(birthDate);
			}

		} catch (Exception e) {
			log.error("UAge#age error > {}", e.getMessage());
		}
		return null;
	}

	public static Integer age(final Date birthDate) {
		try {

			if (birthDate != null) {
				return UDateTime.periodYearsInt(birthDate, new Date());
			}

		} catch (Exception e) {
			log.error("UAge#age error > {}", e.getMessage());
		}
		return null;
	}

	public static CustomMessage age(final String date, final boolean detailed) {
		try {

			if (UValidator.isNotEmpty(date)) {
				val bdate = UDateTime.parseDate(date, Formatter.DATE_SIMPLE_FORMAT);
				val birthDate = UDateTime.localDate(bdate);
				return age(birthDate, detailed);
			}

		} catch (Exception e) {
			log.error("UAge#age error > {}", e.getMessage());
		}
		return null;
	}

	public static CustomMessage age(final Date date, final boolean detailed) {
		try {

			if (date != null) {
				val birthDate = UDateTime.localDate(date);
				return age(birthDate, detailed);
			}

		} catch (Exception e) {
			log.error("UAge#age error > {}", e.getMessage());
		}
		return null;
	}

	private static CustomMessage age(final LocalDate birthDate, final boolean detailed) {
		CustomMessage age = null;
		try {

			val currentDate = LocalDate.now();
			val period = UDateTime.period(birthDate, currentDate);

			Integer years = null;
			if (period.getYears() >= 1) {
				years = period.getYears();
			}

			Integer months = null;
			if (period.getMonths() >= 1) {
				months = period.getMonths();
			}

			Integer days = null;
			if (period.getDays() == 0) {
				if (years == null && months == null) {
					days = 0;
				}
			} else if (period.getDays() >= 1) {
				days = period.getDays();
			}

			if (detailed) {
				if (years != null) {
					if (months != null && days != null) {
						val params = new Object[] { years, months, days };
						age = CustomMessage.builder().key("date.text.age.ymd").params(params).build();
					} else if (months != null && days == null) {
						val params = new Object[] { years, months };
						age = CustomMessage.builder().key("date.text.age.ym").params(params).build();
					} else if (months == null && days != null) {
						val params = new Object[] { years, days };
						age = CustomMessage.builder().key("date.text.age.yd").params(params).build();
					} else {
						val params = new Object[] { years };
						age = CustomMessage.builder().key("date.text.age.y").params(params).build();
					}
				} else {
					if (months != null && days != null) {
						val params = new Object[] { months, days };
						age = CustomMessage.builder().key("date.text.age.md").params(params).build();
					} else if (months != null && days == null) {
						val params = new Object[] { months };
						age = CustomMessage.builder().key("date.text.age.m").params(params).build();
					} else if (months == null && days != null) {
						val params = new Object[] { days };
						age = CustomMessage.builder().key("date.text.age.d").params(params).build();
					}
				}
			} else {
				if (years != null) {
					val params = new Object[] { years };
					return age = CustomMessage.builder().key("date.text.age.y").params(params).build();
				} else if (months != null) {
					val params = new Object[] { months };
					return age = CustomMessage.builder().key("date.text.age.m").params(params).build();
				} else if (days != null) {
					val params = new Object[] { days };
					return age = CustomMessage.builder().key("date.text.age.d").params(params).build();
				}
			}

		} catch (Exception e) {
			log.error("UAge#age error > {}", e.getMessage());
		}
		return age;
	}
	
	private static Period period(LocalDate birthDate) {
		LocalDate currentDate = LocalDate.now();
	    return Period.between(birthDate, currentDate);
	}
}