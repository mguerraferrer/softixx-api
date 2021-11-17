package softixx.api.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

import lombok.val;

public class UCrypto {
	private static final Logger log = LoggerFactory.getLogger(UCrypto.class);
	
	private static final String CRYPT = "s0ft1xx*crypt@G3n3r4t0r";
	private static final String SALT = "8a62385a03342f68";
	public static final String PGP_SYM_CRYPT_KEY = "C0!5mnTr4nsf0rm3r@CrYpT";

	// ##### Default uses ECB PKCS5Padding
	private static final String ALGORITHM_AES = "AES";
	public static final String ENCODED_BASE64_KEY = encodeKey("6e2343b394b942f6");
	
	private static final String ALGORITHM_DES = "DES";
	private static SecretKey secretKey;
	private static Cipher ecipher;
    private static Cipher dcipher;
	

	public static String salt() {
		try {
			// BCrypt.gensalt()
			return KeyGenerators.string().generateKey();

		} catch (Exception e) {
			log.error("UCrypto#salt error - {}", e.getMessage());
		}
		return null;
	}

	public static boolean checkPassword(final String plainTextPasswd, final String hashedPasswd) {
		return BCrypt.checkpw(plainTextPasswd, hashedPasswd);
	}

	public static String idEncode(final Object id) {
		try {
			
			if(UValidator.isNotNull(id)) {
				val idStr = UValue.str(id.toString());
				return UBase64.b64Encode(idStr);
			}
			
		} catch (Exception e) {
			log.error("UCrypto#idEncode error - {}", e.getMessage());
		}
	    return null;
	}
	
	public static String idDecode(final String idStr) {
		try {
		
			if(UValidator.isNotEmpty(idStr)) {
				return UBase64.b64Decode(idStr);
			}
			
		} catch (Exception e) {
			log.error("UCrypto#idDecode error - {}", e.getMessage());
		}
	    return null;
	}
	
	public static Long idDecodeLong(final String idStr) {
		try {
		
			if(UValidator.isNotEmpty(idStr)) {
				val id = idDecode(idStr);
				return ULong.value(id);
			}
			
		} catch (Exception e) {
			log.error("UCrypto#idDecode error - {}", e.getMessage());
		}
	    return null;
	}
	
	public static Integer idDecodeInteger(final String idStr) {
		try {
		
			if(UValidator.isNotEmpty(idStr)) {
				val id = idDecode(idStr);
				return UInteger.value(id);
			}
			
		} catch (Exception e) {
			log.error("UCrypto#idDecode error - {}", e.getMessage());
		}
	    return null;
	}
	
	public static String textEncode(final String token) {
		try {
			
			if(UValidator.isNotEmpty(token)) {
				return UCrypto.encryptWithDES(UValue.str(token));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	public static String textDecode(final String token) {
		try {
		
			if(UValidator.isNotEmpty(token)) {
			    return UCrypto.decryptWithDES(token);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	public static String textEncryptor(String value) {
		try {

			return Encryptors.text(CRYPT, SALT).encrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#textEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String textEncryptor(String salt, String value) {
		try {

			return Encryptors.text(CRYPT, salt).encrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#textEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String textDecryptor(String value) {
		try {

			return Encryptors.text(CRYPT, SALT).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#textDecryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String textDecryptor(String salt, String value) {
		try {

			return Encryptors.text(CRYPT, salt).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#textDecryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String deluxEncryptor(String value) {
		try {

			return Encryptors.delux(CRYPT, SALT).encrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#deluxEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String deluxEncryptor(String salt, String value) {
		try {

			return Encryptors.delux(CRYPT, salt).encrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#deluxEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String deluxDecryptor(String value) {
		try {

			return Encryptors.delux(CRYPT, SALT).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#deluxDecryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String deluxDecryptor(String salt, String value) {
		try {

			return Encryptors.delux(CRYPT, salt).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#deluxDecryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static byte[] bytesEncryptor(String value) {
		try {

			return Encryptors.standard(CRYPT, SALT).encrypt(value.getBytes(StandardCharsets.UTF_8));

		} catch (Exception e) {
			log.error("UCrypto#bytesEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static byte[] bytesEncryptor(String salt, String value) {
		try {

			return Encryptors.standard(CRYPT, salt).encrypt(value.getBytes(StandardCharsets.UTF_8));

		} catch (Exception e) {
			log.error("UCrypto#bytesEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static byte[] bytesEncryptor(byte[] value) {
		try {

			return Encryptors.standard(CRYPT, SALT).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#bytesEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static byte[] bytesEncryptor(String salt, byte[] value) {
		try {

			return Encryptors.standard(CRYPT, salt).decrypt(value);

		} catch (Exception e) {
			log.error("UCrypto#bytesEncryptor error - {}", e.getMessage());
		}
		return null;
	}

	public static String encrypt(final String data, final String secret) {
		try {
			
			val key = generateKey(secret);
			val cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] encVal = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encVal);
			
		} catch (Exception e) {
			log.error("UCrypto#decrypt error 'Error while crypting' {}", e.toString());
		}
		return null;
	}

	public static String decrypt(final String strToDecrypt, final String secret) {
		try {

			val key = generateKey(secret);
			val cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);

		} catch (Exception e) {
			log.error("UCrypto#decrypt error 'Error while decrypting' {}", e.toString());
		}
		return null;
	}

	private static Key generateKey(final String secret) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
		Key key = new SecretKeySpec(decoded, ALGORITHM_AES);
		return key;
	}

	public static String decodeKey(final String str) {
		byte[] decoded = Base64.getDecoder().decode(str.getBytes());
		return new String(decoded);
	}

	public static String encodeKey(final String str) {
		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
		return new String(encoded);
	}
	
	private static void loadKey() {
		if(secretKey == null) {
			try {
				
				secretKey = KeyGenerator.getInstance(ALGORITHM_DES).generateKey();
				
			} catch (NoSuchAlgorithmException e) {
				log.error("UCrypto#loadKey error - {}", e.getMessage());
			}
		}
	}
	
	public static String encryptWithDES(final String str) {
		try {

			loadKey();
			
			if(ecipher == null) {
				ecipher = Cipher.getInstance(ALGORITHM_DES);
				ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}

			// ##### Encode the string into a sequence of bytes using the named charset
			byte[] utf8 = str.getBytes("UTF8");

			// ##### Storing the result into a new byte array.
			byte[] enc = ecipher.doFinal(utf8);

			// ##### Encode to base64
			enc = BASE64EncoderStream.encode(enc);

			return new String(enc);
			
		} catch (Exception e) {
			log.error("UCrypto#encryptWithDES error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String decryptWithDES(final String str) {
		try {
			
			loadKey();
			
			if(dcipher == null) {
				dcipher = Cipher.getInstance(ALGORITHM_DES);
				dcipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			
			//##### Decode with base64 to get bytes
			byte[] dec = BASE64DecoderStream.decode(str.getBytes());

			// ##### Storing the result into a new byte array.
			byte[] utf8 = dcipher.doFinal(dec);

			//##### Create new string based on the specified charset
			return new String(utf8, "UTF8");

		} catch (Exception e) {
			log.error("UCrypto#decryptWithDES error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String encryptWithJasypt(final Object value) {
		try {
			
			if(UValidator.isNotNull(value)) {
				val textEncryptor = new BasicTextEncryptor();
				textEncryptor.setPasswordCharArray(CRYPT.toCharArray());
				
				val data = value.toString();
				return textEncryptor.encrypt(data);
			}
			
		} catch (Exception e) {
			log.error("UCrypto#encryptWithJasypt error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String decryptWithJasypt(final String value) {
		try {
			
			val textEncryptor = new BasicTextEncryptor();
			textEncryptor.setPasswordCharArray(CRYPT.toCharArray());
			return textEncryptor.decrypt(value);
			
		} catch (Exception e) {
			log.error("UCrypto#decryptWithJasypt error - {}", e.getMessage());
		}
		return null;
	}

	public static void main(String args[]) throws Exception {
		/*
		 * Secret Key must be in the form of 16 byte like,
		 *
		 * private static final byte[] secretKey = new byte[] { ‘m’, ‘u’, ‘s’, ‘t’, ‘b’,
		 * ‘e’, ‘1’, ‘6’, ‘b’, ‘y’, ‘t’,’e’, ‘s’, ‘k’, ‘e’, ‘y’};
		 *
		 * below is the direct 16byte string we can use
		 */
		String secretKey = "mustbe16byteskey";
		String encodedBase64Key = encodeKey(secretKey);
		System.out.println("EncodedBase64Key = " + encodedBase64Key); // This need to be share between client and server

		// To check actual key from encoded base 64 secretKey
		String toDecodeBase64Key = decodeKey(encodedBase64Key);
		System.out.println("toDecodeBase64Key = " + toDecodeBase64Key);

		String toEncrypt = "Please encrypt this crazy méssÁge!";
		System.out.println("Plain text = " + toEncrypt);

		// AES Encryption based on above secretKey
		String encrStr = encrypt(toEncrypt, encodedBase64Key);
		System.out.println("Cipher Encryption = " + encrStr);

		// AES Decryption based on above secretKey
		String decrStr = decrypt(encrStr, encodedBase64Key);
		System.out.println("Cipher Decryption = " + decrStr);

		// Delux encryp/decrypt
		String deluxEncryption = deluxEncryptor(toEncrypt);
		System.out.println("deluxEncryption = " + deluxEncryption);

		String deluxDecryption = deluxDecryptor(deluxEncryption);
		System.out.println("deluxDecryption = " + deluxDecryption);

		// Text encryp/decrypt
		String textEncryption = textEncryptor(toEncrypt);
		System.out.println("textEncryption = " + textEncryption);

		String textDecryption = textDecryptor(textEncryption);
		System.out.println("textDecryption = " + textDecryption);
		
		textEncryption = textEncryptor("10");
		System.out.println("textEncryption = " + textEncryption);
		
		textDecryption = textDecryptor(textEncryption);
		System.out.println("textDecryption = " + textDecryption);

		TextEncryptor encryptor = Encryptors.noOpText();
		String textEncryptor = encryptor.encrypt("text");
		System.out.println("textEncryptor = " + textEncryptor);

		String textDecryptor = encryptor.decrypt("text");
		System.out.println("textDecryptor = " + textDecryptor);
		
		String encryptedWithDES1 = encryptWithDES(toEncrypt);
		System.out.println("encryptedWithDES1: " + encryptedWithDES1);
		String decryptedWithDES1 = decryptWithDES(encryptedWithDES1);
		System.out.println("decryptedWithDES1: " + decryptedWithDES1);
		
		String encryptedWithDES2 = encryptWithDES(toEncrypt);
		System.out.println("encryptedWithDES2: " + encryptedWithDES2);
		String decryptedWithDES2 = decryptWithDES(encryptedWithDES2);
		System.out.println("decryptedWithDES2: " + decryptedWithDES2);
		
		// Jsypt encryp/decrypt
		String jsyptEncryption = encryptWithJasypt(10);
		System.out.println("jsyptEncryption = " + jsyptEncryption);

		String jasyptDecryption = decryptWithJasypt(jsyptEncryption);
		System.out.println("jasyptDecryption = " + jasyptDecryption);
	}
}
