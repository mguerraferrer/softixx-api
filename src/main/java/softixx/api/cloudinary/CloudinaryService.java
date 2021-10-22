package softixx.api.cloudinary;

import java.io.File;

import org.springframework.stereotype.Service;

import lombok.val;

/**
 * Service that manages the Cloudinary's operations
 *
 * @since 1.2.0
 * @see GenericCloudinary
 * @author Maikel Guerra Ferrer
 */
@Service
public class CloudinaryService extends GenericCloudinary {
	/**
	 * Cloudinary configuration data initialization
	 *
	 * @param publicKey - (String) Conekta public key <br>
	 * @param privateKey - (String) Conekta private key <br>
	 * @param version - (String) API version <br>
	 * @param liveMode - (Boolean) If true, means it is a production payment
	 * @since 1.2.0
	 * @see CloudinaryConfiguration
	 */
	public void cloudinaryConfiguration(final String apiKey, final String apiSecret, final String cloudUrl, final String cloudName,
			final String cloudFolder) {
		val cloudinaryConfiguration = CloudinaryConfiguration.instance(apiKey, apiSecret, cloudUrl, cloudName, cloudFolder);
		super.cloudinaryConfig(cloudinaryConfiguration);
	}
	
	@Override
	public String uploadFile(final File uploadedFile, final String fileName) {
		return super.uploadFile(uploadedFile, fileName);
	}

	@Override
	public Boolean existsFile(final String publicId) {
		return super.existsFile(publicId);
	}
	
	@Override
	public String deleteFile(final String publicId) {
		return super.deleteFile(publicId);
	}
	
	@Override
	public String cloudinaryUrl() {
		return super.cloudinaryUrl();
	}

	@Override
	public String cloudinaryFolder() {
		return super.cloudinaryFolder();
	}

	@Override
	public void showLogs() {
		super.showLogs();
	}
}