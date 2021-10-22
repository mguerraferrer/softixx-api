package softixx.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for timing using {@link StopWatch} API
 * 
 * @author maikel.guerra
 *
 */
@Slf4j
public class ULogging {

	private ULogging() {
		throw new IllegalStateException("Utility class");
	}

	private static final String LOG_INFO = "{} {}";

	private static StopWatch watch;
	private static StopWatch inWatch;

	/**
	 * Starts the stopwatch <br>
	 * This method starts a new timing session, clearing any previous values.
	 * 
	 * @throws IllegalStateException if the StopWatch is already running.
	 */
	public static void start() {
		if (watch == null || !watch.isStarted()) {
			watch = new StopWatch();
			watch.start();
		}
	}

	/**
	 * Creates a started stopwatch for convenience
	 */
	public static void interval() {
		if (inWatch == null || !inWatch.isStarted()) {
			inWatch = StopWatch.createStarted();
		}
	}

	/**
	 * Creates a started stopwatch for convenience
	 * 
	 * @return a new StopWatch
	 */
	public static StopWatch instance() {
		return StopWatch.createStarted();
	}

	/**
	 * Stops the stopwatch <br>
	 * This method ends a new timing session, allowing the time to be retrieved.
	 * 
	 * @throws IllegalStateException if the StopWatch is not running.
	 */
	public static void intervalStop() {
		if (inWatch.isStarted()) {
			inWatch.stop();
		}
	}

	/**
	 * Stops the stopwatch <br>
	 * This method ends a new timing session, allowing the time to be retrieved.
	 * 
	 * @throws IllegalStateException if the StopWatch is not running.
	 */
	public static void stop() {
		if (watch.isStarted()) {
			watch.stop();
		}
	}
	
	/**
	 * Stops the stopwatch <br>
	 * This method ends a new timing session, allowing the time to be retrieved.
	 * 
	 * @throws IllegalStateException if the StopWatch is not running.
	 */
	public static void stop(StopWatch stopWatch) {
		if (stopWatch.isStarted()) {
			stopWatch.stop();
		}
	}

	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void intervalTime() {
		time(StringUtils.EMPTY);
	}

	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void intervalTime(String resource) {
		if (inWatch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#intervalTime";
			}

			stop();
			log.info(LOG_INFO, resource, inWatch.getTime());
		}
	}
	
	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void time() {
		time(StringUtils.EMPTY);
	}

	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void time(String resource) {
		if (watch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#time";
			}

			stop();
			log.info(LOG_INFO, resource, watch.getTime());
		}
	}
	
	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void time(StopWatch stopWatch) {
		time(stopWatch, StringUtils.EMPTY);
	}

	/**
	 * Prints the time (in milliseconds) on the stopwatch. <br>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time betweenstart and stop.
	 */
	public static void time(StopWatch stopWatch, String resource) {
		if (stopWatch != null && stopWatch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#time";
			}

			stop(stopWatch);
			log.info(LOG_INFO, resource, stopWatch.getTime());
		}
	}

	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void intervalSummary() {
		intervalSummary(StringUtils.EMPTY);
	}

	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void intervalSummary(String resource) {
		if (inWatch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#intervalSummary";
			}

			intervalStop();
			log.info(LOG_INFO, resource, inWatch.formatTime());
		}
	}

	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void summary() {
		summary(StringUtils.EMPTY);
	}

	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void summary(String resource) {
		if (watch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#summary";
			}

			stop();
			log.info(LOG_INFO, resource, watch.formatTime());
		}
	}
	
	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void summary(StopWatch stopWatch) {
		summary(stopWatch, StringUtils.EMPTY);
	}

	/**
	 * Prints the elapsed time in ISO 8601 HH:mm:ss.SSS format
	 */
	public static void summary(StopWatch stopWatch, String resource) {
		if (stopWatch != null && stopWatch.isStarted()) {
			if (ObjectUtils.isEmpty(resource)) {
				resource = "ULogging#summary";
			}

			stop(stopWatch);
			log.info(LOG_INFO, resource, stopWatch.formatTime());
		}
	}

}