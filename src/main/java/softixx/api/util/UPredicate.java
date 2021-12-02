package softixx.api.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Predicate utility class
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class UPredicate implements Serializable {
	private static final long serialVersionUID = 1455746374365112108L;

	private UPredicate() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Consider <b>distinct</b> to be a <i>stateful filter</i>. This function
	 * returns a predicate that maintains state about what it's seen previously, and
	 * that returns whether the given element was seen for the first time
	 * <p>
	 * Usage: {@code persons.stream().filter(distinctByKey(Person::getName))}
	 * </p>
	 * <p>
	 * Note that if the stream is ordered and is run in parallel, this will preserve
	 * an <i>arbritary</i> element for among the duplicates, insted of the first one,
	 * as distinct does
	 * </p>
	 * 
	 * @param <T> Object type
	 * @param keyExtractor Function
	 * @return true if the element can be added to the set, false otherwise
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> s = ConcurrentHashMap.newKeySet();
		return t -> s.add(keyExtractor.apply(t));
	}

}