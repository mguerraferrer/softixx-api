package softixx.api.util;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UFile {
	private UFile() {
		throw new IllegalStateException("Utility class");
	}
	
	public static List<String> fileNameList(final String path) {
		List<String> fileNames = new ArrayList<>();
		try (val stream = Files.walk(Paths.get(path))) {
			stream.forEach(route-> {
				if (Files.isRegularFile(route)) {
					val fileName = route.getFileName().toString();
					fileNames.add(fileName);
				}
			});
			return fileNames;
		} catch (Exception e) {
			log.error("UFile#fileNameList error - {}", e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public static String fileName(final File file) {
		if(file == null) {
			return null;
		}
		return file.getName();
	}
	
	public static String fileName(final MultipartFile multipart) {
		val isValid = isValid(multipart); 
		if(isValid) {
			return multipart.getOriginalFilename();
		}
		return null;
	}
	
	public static String fileName(final String name) {
		String fileName = null;
		if(name != null && !name.isEmpty()) {
			try {
				
				fileName = name.substring(0, name.lastIndexOf("."));
				
			} catch (Exception e) {
				fileName = name;
			}
		}
        return fileName;
    }
	
	public static String fileFullName(final String name) {
		return Paths.get(name).getFileName().toString();
    }
	
	public static String fileExtension(final String fileName) {
		String[] extension = fileName.split("\\.");
		if (extension.length != 0) {
            return extension[extension.length - 1];
        }
		return null;
	}
	
	public static Boolean isValidExtension(final List<String> allowedExtensions, final String fileExt) {
		return UStream.exists(allowedExtensions, fileExt);
	}
	
	public static Boolean isValid(final MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
			return false;
		}
		return !multipartFile.getOriginalFilename().isEmpty();
	}
	
	public static Boolean isValid(final MultipartFile[] multipartFile) {
		if(multipartFile == null) {
			return false;
		}
		
		return Arrays.asList(multipartFile).stream()
										   .filter(f -> f.getOriginalFilename().isEmpty())
										   .count() == 0;
	}
	
	public static Boolean isValid(final MultipartFile multipartFile, final List<String> allowedExtensions) {
		try {
			
			val isValid = isValid(multipartFile);
			if (isValid) {
				val fileName = fileName(multipartFile);
				if(UValidator.isNotEmpty(fileName)) {
					val fileExt = fileExtension(fileName);
					return isValidExtension(allowedExtensions, fileExt);
				}
			}
			
		} catch (Exception e) {
			log.error("UFile#isValid error - {}", e.getMessage());
		}
		return false;
	}
	
	public static File multipartFileToFile(final MultipartFile multipartFile) throws IOException{
		try {
			
			val isValid = isValid(multipartFile);
			if(isValid) {
				val file = new File(multipartFile.getOriginalFilename());
			    file.createNewFile();
			    
			    try (val fos = new FileOutputStream(file))  {
			    	fos.write(multipartFile.getBytes());
			    	return file;
			    }
			}
			
		} catch (Exception e) {
			log.error("UFile#multipartFileToFile error - {}", e.getMessage());
		}
	    return null;
	}
	
	public static InputStream multipartFileToInputStream (final MultipartFile multipart) throws IOException {
		val isValid = isValid(multipart); 
		if(!isValid) {
			return null;
		}
		return multipart.getInputStream();
	}
	
	public static File byteToFile(byte[] data) throws IOException {
		if(data != null) {
			return File.createTempFile("java", "tmp");
		}
		return null;
	}
	
	public static byte[] fileToByte(File file) throws IOException {
        var buffer = new byte[(int) file.length()];
        try (val is = new FileInputStream(file)) {
            if ( is.read(buffer) == -1 ) {
                throw new IOException("EOF reached while trying to read the whole file");
            }        
        }
        return buffer;
    }
	
	public static InputStream fileToInputStream(final File file) throws IOException {
		if(file == null) {
			return null;
		}
		return new FileInputStream(file);
	}
	
	public static InputStream fileToInputStreamWithDIS(final File file) throws IOException {
		if(file == null) {
			return null;
		}
		return new DataInputStream(new FileInputStream(file));
	}
	
	public static InputStream fileToInputStreamWithCIO(final File file) throws IOException {
		if(file == null) {
			return null;
		}
		return FileUtils.openInputStream(file);
	}
	
	public static File inputStreamToFile(InputStream is, String prefix, String suffix) {
		try {
			
			val tempFile = File.createTempFile(prefix, suffix);
	        tempFile.deleteOnExit();
	        
	        try (var out = new FileOutputStream(tempFile)) {
	            IOUtils.copy(is, out);
	        }
	        
	        return tempFile;
	        
		} catch (Exception e) {
			log.error("UFile#inputStreamToFile error - {}", e.getMessage());
		}
		return null;
	}
	
	public static byte[] inputStreamToByte(InputStream is) {
		byte[] bytes;
		try {
			
			bytes = IOUtils.toByteArray(is);
			return bytes;
			
		} catch (Exception e) {
			log.error("UFile#inputStreamToByte error - {}", e.getMessage());
		}
		return null;
	}
	
	public static void writeFile(byte[] fileWrite, String pathWrite) throws IOException {
        //##### Creating buffer object to write a file
        try {

            //##### Backup Physically file path 
            val filePath = new File(pathWrite);

            //##### Validating if path exist
            if (!filePath.getParentFile().exists()) {
            	log.error("UFile#writeFile error - The file path [{}] does not exist", filePath);
                throw new IOException("The file path [" +filePath+ "] does not exist");
            }

            //##### Writing file
            try (val bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
            	bufferedOutputStream.write(fileWrite, 0, fileWrite.length);
            }

        } catch (Exception ex) {
        	log.error("UFile#writeFile error - Error trying to write a file {}", ex.getMessage());
            throw new IOException("Error trying to write a file", ex);
        }
    }
	
	public static File pathToFile(final Path path) {
		try {
			
			return path.toFile();
			
		} catch (Exception e) {
			log.error("UFile#pathToFile error - {}", e.getMessage());
		}
		return null;
	}
	
	public static Path getPath(final String filePath) {
		try {
			
			return Paths.get(filePath);
			
		} catch (Exception e) {
			log.error("UFile#getPath error - {}", e.getMessage());
		}
		return null;
	}
	
	public static String readFileAsString(final String path) {
		try {
			
			return new String(Files.readAllBytes(Paths.get(path)));
			
		} catch (Exception e) {
			log.error("UFile#readFileAsString error - {}", e.getMessage());
		}
		return null;
	}
}