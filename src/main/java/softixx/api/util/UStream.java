package softixx.api.util;

import java.util.Enumeration;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UStream {
    public static Stream<String> streamFromEnumeration(final Enumeration<String> enumeration) {
        Stream<String> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(enumeration.asIterator(), Spliterator.ORDERED),false);
        return stream;
    }
    
    public static Boolean exists(final List<?> source, final Object value) {
		return source.stream()
					 .map(val -> String.valueOf(value))
					 .filter(val -> val.equalsIgnoreCase(String.valueOf(value)))
					 .findFirst()
					 .isPresent();
	}
}
