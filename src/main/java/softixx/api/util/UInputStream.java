package softixx.api.util;

import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UInputStream {

	private UInputStream() {
		throw new IllegalStateException("Utility class");
	}

	public static byte[] convertToByte(InputStream is) {
		try {

			if (is != null) {
				return is.readAllBytes();
			}

		} catch (Exception e) {
			log.error("UInputStream#convertToByte error - {}", e.getMessage());
		}
		return new byte[0];
	}

}