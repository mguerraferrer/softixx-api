package softixx.api.reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.val;
import softixx.api.bean.FileBean;
import softixx.api.bean.FileInfoBean;
import softixx.api.util.UValidator;
import softixx.api.util.UValue;

public abstract class FileRW {
	private static final Logger log = LoggerFactory.getLogger(FileRW.class);
	
	protected FileBean parseFile(final FileBean fileBean) {
		BufferedReader fileReader = null;
		CSVParser parser = null;
		CSVFormat format = CSVFormat.DEFAULT.withTrim().withDelimiter(UValue.toChar(fileBean.getDelimiter()));
		FileInfoBean fileInfo = fileBean.getFileInfo();
		
		try {
			
			if (fileBean.getHeaders().isEmpty()) {
				format = format.withFirstRecordAsHeader().withIgnoreHeaderCase();
			}
			
			if (UValidator.isNotEmpty(fileBean.getSeparator())) {
				format = format.withRecordSeparator(fileBean.getSeparator());
			}
			
			fileReader = new BufferedReader(new InputStreamReader(fileBean.getIs(), StandardCharsets.UTF_8));
			parser = new CSVParser(fileReader, format);
			val entries = parser.getRecords().stream()
								.map(entry -> entry.toString())
								.collect(Collectors.toList());
			
			fileBean.addEntries(entries);
			fileInfo.setTotalLines(entries.size());
			
		} catch (Exception e) {
			log.error("FileRW#parseFile error - {}", e.getMessage());
			fileInfo.addMessage(fileInfo.getFileName(), e.getMessage(), FileInfoBean.FAIL);
		} finally {
			try {
				
				if (fileReader != null) {
					fileReader.close();
				}
				
				if (parser != null) {
					parser.close();
				}
				
			} catch (Exception e1) {
				log.error("FileRW#parseFile error - {}", e1.getMessage());
				fileInfo.addMessage(fileInfo.getFileName(), e1.getMessage(), FileInfoBean.FAIL);
			}
		}
		
		fileBean.addFileInfo(fileInfo);
		return fileBean;
	}
}
