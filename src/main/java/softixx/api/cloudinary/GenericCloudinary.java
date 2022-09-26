package softixx.api.cloudinary;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.Builder;
import lombok.Data;
import lombok.val;
import softixx.api.util.UValidator;

/**
 * 
 * Manage Cloudinary Cloud Resources <br>
 * Require {@link Cloudinary} API
 * 
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
public abstract class GenericCloudinary {
	private static final Logger log = LoggerFactory.getLogger(GenericCloudinary.class);

	private Cloudinary cloudinary;
	private CloudinaryConfiguration cloudinaryConfiguration;
	private Boolean showLogs;
	private Boolean showStackTrace;

	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}
	
	/**
	 * Prints stack trace information if showStackTrace is true. By default, showStackTrace is false.
	 */
	protected void showStackTrace() {
		this.showStackTrace = true;
	}

	/**
	 * Cloudinary configuration data initialization
	 * 
	 * @param cloudinaryConfiguration - {@link CloudinaryConfiguration}
	 * @since 1.2.0
	 */
	protected void cloudinaryConfig(final CloudinaryConfiguration cloudinaryConfiguration) {
		this.cloudinaryConfiguration = cloudinaryConfiguration;
		this.showLogs = false;
		this.showStackTrace = false;

		val config = new HashMap<String, String>();
		config.put("api_key", this.cloudinaryConfiguration.getApiKey());
		config.put("api_secret", this.cloudinaryConfiguration.getApiSecret());
		config.put("cloud_name", this.cloudinaryConfiguration.getCloudName());
		this.cloudinary = new Cloudinary(config);
	}

	/**
	 * Cloudinary upload file
	 * 
	 * @param uploadedFile - {@link File} to be uploaded
	 * @param fileName - (String) Name of the file (without extension)
	 * @return The URL of the uploaded file
	 * @since 1.2.0
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected String uploadFile(final File uploadedFile, final String fileName) {
		try {
			
			if (UValidator.isNotNull(uploadedFile) && UValidator.isNotEmpty(fileName)) {
				val publicId = cloudinaryFolder() + "/" + fileName;
				Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.asMap("public_id", publicId));
				return uploadResult.get("url").toString();
			}

		} catch (Exception e) {
			if (this.showStackTrace) {
				e.printStackTrace();
			}
			if (this.showLogs) {
				log.error("Cloudinary upload file error: {}", e.getMessage());
			}
		}
		return null;
	}

	/**
	 * @param publicId - (String) The public_id of the resource in Cloudinary (without extension)
	 * @return 'ok' if the resource was deleted
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected String deleteFile(final String publicId) {
		try {
			
			if (UValidator.isNotEmpty(publicId)) {
				if (existsFile(publicId)) {
					val cloudPublicId = cloudinaryPublicId(publicId);
					Map result = cloudinary.uploader().destroy(cloudPublicId, ObjectUtils.emptyMap());
					return result.get("result").toString();
				} else {
					return "Resource doesn't exists";
				}
			}

		} catch (Exception e) {
			if (this.showStackTrace) {
				e.printStackTrace();
			}
			if (this.showLogs) {
				log.error("Cloudinary delete file error: {}", e.getMessage());
			}
		}
		return "error";
	}

	/**
	 * Checks if a resource exists in Cloudinary
	 * 
	 * @param publicId - (String) The public_id of the resource in Cloudinary (without extension)
	 * @return True if the resource exists
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected Boolean existsFile(final String publicId) {
		try {
			
			if (UValidator.isNotEmpty(publicId)) {
				val cloudPublicId = cloudinaryPublicId(publicId);
				Map result = cloudinary.api().resource(cloudPublicId, ObjectUtils.emptyMap());
				val url = result.get("url").toString();
				return url != null;
			}

		} catch (Exception e) {
			if (this.showStackTrace) {
				e.printStackTrace();
			}
			if (this.showLogs) {
				log.error("Cloudinary exists file error: {}", e.getMessage());
			}
		}
		return false;
	}

	protected String cloudinaryUrl() {
		return cloudinaryConfiguration.getCloudUrl();
	}

	protected String cloudinaryFolder() {
		return cloudinaryConfiguration.getCloudFolder();
	}

	private String cloudinaryPublicId(final String pid) {
		return cloudinaryFolder() + "/" + pid;
	}

	/**
	 * Cloudinary configuration data <br>
	 * 
	 * {@value} <b>apiKey</b> - (String) Cloudinary api key <br>
	 * {@value} <b>apiSecret</b> - (String) Cloudinary api secret <br>
	 * {@value} <b>cloudUrl</b> - (String) Cloudinary cloud url <br>
	 * {@value} <b>cloudName</b> - (String) Cloudinary cloud name <br>
	 * {@value} <b>cloudFolder</b> - (String) Cloudinary folder name <br>
	 * @since 1.2.0
	 */
	@Data
	@Builder
	protected static class CloudinaryConfiguration {
		private String apiKey;
		private String apiSecret;
		private String cloudUrl;
		private String cloudName;
		private String cloudFolder;

		/**
		 * A static instance of CloudinaryConfiguration <br>
		 *  
		 * @param apiKey - (String) Cloudinary api key
		 * @param apiSecret - (String) Cloudinary api secret
		 * @param cloudUrl - (String) Cloudinary cloud url
		 * @param cloudName - (String) Cloudinary cloud name
		 * @param cloudFolder - (String) Cloudinary folder name
		 * @return A new instance of CloudinaryConfiguration
		 */
		public static CloudinaryConfiguration instance(final String apiKey, final String apiSecret,
				final String cloudUrl, final String cloudName, final String cloudFolder) {
			return CloudinaryConfiguration
					.builder()
					.apiKey(apiKey)
					.apiSecret(apiSecret)
					.cloudUrl(cloudUrl)
					.cloudName(cloudName)
					.cloudFolder(cloudFolder)
					.build();
		}
	}
}
