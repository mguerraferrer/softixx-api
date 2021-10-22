package softixx.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UMapper<T> {
    private static final Logger log = LoggerFactory.getLogger(UMapper.class);

    public T readValue(final String dataJson, final Class<T> clazz) {
        val mapper = new ObjectMapper();
        try {

            return mapper.readValue(dataJson, clazz);

        } catch (Exception e) {
            log.info("UMapper readValue error: " + e.getMessage());
        }
        return null;
    }
}
