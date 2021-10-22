package softixx.api.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.Base64;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UBase64 {
	private UBase64() {
		throw new IllegalStateException("Utility class");
	}

	public static String b64Encode(final String value) {
		try {

			val data = UValue.str(value);
			if (UValidator.isNotEmpty(data)) {
				return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
			}

		} catch (Exception e) {
			log.error("UBase64#b64Encode error {}", e.getMessage());
		}
		return null;
	}
	
	public static String encodeToString(Blob blob) {
		try {
			
			if (blob != null) {
				val blobAsBytes = blob.getBytes(1, (int) blob.length());
				encodeToString(blobAsBytes);
			}
			
		} catch (Exception e) {
			log.error("UBase64#encodeToString [Blob] error {}", e.getMessage());
		}
		return null;
	}
	
	public static String encodeToString(InputStream is) {
		val bytes = UInputStream.convertToByte(is);
		return encodeToString(bytes);
	}
	
	public static String encodeToString(byte[] value) {
		String str = null;
		try {
			
			if (UValidator.isNotNull(value)) {
				byte[] bytes = Base64.getEncoder().encode(value);
				
				str = new String(bytes);
				str = str.replace("\r\n", "");
				str = str.replace("\n", "");
				str = str.replace("\r", "");
			}

		} catch (Exception e) {
			log.error("UBase64#encodeToString [byte] error {}", e.getMessage());
		}
		return str;
	}

	public static String b64Decode(final String value) {
		try {

			if (UValidator.isNotEmpty(value)) {
				byte[] decodedBytes = Base64.getDecoder().decode(value.getBytes());
				return new String(decodedBytes, StandardCharsets.UTF_8);
			}

		} catch (Exception e) {
			log.error("UBase64#b64Decode error {}", e.getMessage());
		}
		return null;
	}

	public static byte[] stringToByte(String str) {
		try {

			if (str != null && !str.isEmpty()) {
				return str.getBytes(StandardCharsets.UTF_8);
			}

		} catch (Exception e) {
			log.error("UBase64#byteFromBase64 error {}", e.getMessage());
		}
		return null;
	}
	
}