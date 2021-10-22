package softixx.api.bean;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.val;
import softixx.api.util.UFile;
import softixx.api.util.UStream;
import softixx.api.util.UValidator;

@Getter
public class FileBean {
	private static final String DELIMITER_PIPE = "|";
	private static final String DELIMITER_COMMA = ",";
	private static final String DELIMITER_SEMICOLON = ";";
	
	private static final String SEPARATOR_N = "\\n";
	private static final String SEPARATOR_R = "\\r";
	private static final String SEPARATOR_RN = "\\r\\n";
	
	protected List<String> headers;
	protected InputStream is;
	protected FileInfoBean fileInfo;
	@Getter(AccessLevel.NONE)
	protected String delimiter;
	@Getter(AccessLevel.NONE)
	protected String separator;
	@Getter(AccessLevel.NONE)
	protected Integer skipLines = 1;
	protected List<String[]> entriesAll;
	protected List<String> entries;
	
	public void addHeaders(final List<String> headers) {
		if(headers == null || headers.isEmpty()) {
			throw new Error("The header is required");
		}
		this.headers = headers;
	}
	
	public void addFile(final MultipartFile multipart) {
		if(!UFile.isValid(multipart)) {
			throw new Error("The file is invalid or corrupt");
		}
		
		if(!isValidExtension(multipart)) {
			throw new Error("Invalid extension. The allowed extensions are csv and txt");
		}
		
		try {
			
			this.is = UFile.multipartFileToInputStream(multipart);
			this.addFileInfo(UFile.fileName(multipart));
			
		} catch (Exception e) {
			throw new Error("The file cannot be read");
		}
	}
	
	public void addFile(final File file) {
		if(file == null) {
			throw new Error("The file is invalid or corrupt");
		}
		
		if(!isValidExtension(file)) {
			throw new Error("Invalid extension. The allowed extensions are csv and txt");
		}
		
		try {
			
			this.is = UFile.fileToInputStream(file);
			this.addFileInfo(UFile.fileName(file));
			
		} catch (Exception e) {
			throw new Error("The file cannot be read");
		}
	}
	
	public void addFileInfo(final FileInfoBean fileInfo) {
		this.fileInfo = fileInfo;
	}
	
	public void addDelimiter(final String delimiter) {
		if(delimiter == null) {
			this.delimiter = DELIMITER_COMMA;
		} else {
			val isValidSeparator = UStream.exists(getAllowedDelimiters(), delimiter);
			if(isValidSeparator) {
				this.delimiter = DELIMITER_COMMA;
			} else {
				this.delimiter = delimiter;
			}
		}
	}
	
	public void addSeparator(final String separator) {
		if(separator == null) {
			this.separator = null;
		} else {
			val isValidSeparator = UStream.exists(getAllowedSeparators(), separator);
			if(isValidSeparator) {
				this.separator = SEPARATOR_N;
			} else {
				this.separator = null;
			}
		}
	}
	
	public void addSkipLines(final Integer skipLines) {
		if(skipLines == null || skipLines < 0) {
			this.skipLines = 1;
		}
		this.skipLines = skipLines;
	}
	
	public void addEntries(final List<String> entries) {
		this.entries = entries;
	}
	
	public void addEntriesAll(final List<String[]> entriesAll) {
		this.entriesAll = entriesAll;
	}
	
	public String getDelimiter() {
		if(UValidator.isEmpty(this.delimiter)) {
			this.delimiter = DELIMITER_COMMA;
		}
		return this.delimiter;
	}
	
	public String getSeparator() {
		if(UValidator.isEmpty(this.separator)) {
			this.separator = SEPARATOR_N;
		}
		return this.separator;
	}
	
	public static List<String> getAllowedDelimiters() {
		return List.of(DELIMITER_COMMA, DELIMITER_PIPE, DELIMITER_SEMICOLON);
	}
	
	public static List<String> getAllowedSeparators() {
		return List.of(SEPARATOR_N, SEPARATOR_R, SEPARATOR_RN);
	}
	
	public static List<String> getAllowedExtensions() {
		return List.of("csv", "txt");
	}
	
	private static Boolean isValidExtension(final File file) {
		val ext = UFile.fileExtension(UFile.fileName(file));
		return UFile.isValidExtension(getAllowedExtensions(), ext);
	}
	
	private static Boolean isValidExtension(final MultipartFile multipart) {
		val ext = UFile.fileExtension(multipart.getOriginalFilename());
		return UFile.isValidExtension(getAllowedExtensions(), ext);
	}
	
	private void addFileInfo(final String fileName) {
		val fileInfo = new FileInfoBean();
		fileInfo.setFileName(fileName);
		
		this.fileInfo = fileInfo;
	}
}
