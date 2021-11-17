package softixx.api.reader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import lombok.val;
import softixx.api.bean.FileDataBean;
import softixx.api.bean.FileInfoBean;
import softixx.api.util.UList;

public abstract class FileBeanRW<T> extends FileRW {
	private static final Logger log = LoggerFactory.getLogger(FileBeanRW.class);
	
	protected FileDataBean<T> parseFile(final FileDataBean<T> fileData) {
		String[] CSV_HEADER = UList.toArray(fileData.getHeaders());
		Reader fileReader = null;
		CsvToBean<T> csvToBean = null;
		
		try {
			
			ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>();
			mappingStrategy.setType(fileData.getClazz());
			mappingStrategy.setColumnMapping(CSV_HEADER);
	
			fileReader = new InputStreamReader(fileData.getIs());
			csvToBean = new CsvToBeanBuilder<T>(fileReader)
								.withMappingStrategy(mappingStrategy)
								.withSkipLines(1)
								.withIgnoreLeadingWhiteSpace(true)
								.build();
	
			val source = csvToBean.parse();
			fileData.addSource(source);
			
			val fileInfo = new FileInfoBean();
			fileInfo.setTotalLines(source.size());
			fileData.addFileInfo(fileInfo);
			
			return fileData;
			
		} catch (Exception e) {
			log.error("UFileReader#parseFile error - {}", e.getMessage());
		} finally {
			try {
				
				fileReader.close();
				
			} catch (IOException e) {
				log.error("UFileReader#parseFile fileReader.close error - {}", e.getMessage());
			}
		}
		return null;
	}
	
	protected void dataToFile(Writer writer, final FileDataBean<T> fileData) {
		String[] CSV_HEADER = UList.toArray(fileData.getHeaders());
	    StatefulBeanToCsv<T> beanToCsv = null;
	 
	    try {
	      
	    	//##### Write List of Objects
	    	ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>();
	      
	    	mappingStrategy.setType(fileData.getClazz());
	    	mappingStrategy.setColumnMapping(CSV_HEADER);
	      
	    	beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
	    		  				.withMappingStrategy(mappingStrategy)
	    		  				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	    		  				.build();
	 
	    	beanToCsv.write(fileData.getSource());
	      
	    } catch (Exception e) {
	    	log.error("UFileReader#dataToFile error - {}", e.getMessage());
	    	e.printStackTrace();
	    }
	}
	
}