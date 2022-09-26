package softixx.api.util;

import lombok.val;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class UZip {
    private static final Logger log = LoggerFactory.getLogger(UZip.class);

    /**
     * Zipping a single file
     * @param sourceFile String - File to zip
     * @param dirCompressed String - Destination path of the file after zipping
     */
    public static void zipFile(final String sourceFile, final String dirCompressed) {
        if (UValidator.isNotNull(sourceFile) && UValidator.isNotNull(dirCompressed)) {
            FileOutputStream fos = null;
            ZipOutputStream zipOut = null;
            FileInputStream fis = null;

            try {

                var dirCompressedZip = "";
                if (dirCompressed.contains(".zip")) {
                    dirCompressedZip = dirCompressed;
                } else {
                    dirCompressedZip = dirCompressed.concat(".zip");
                }

                fos = new FileOutputStream(dirCompressedZip);
                zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceFile);
                fis = new FileInputStream(fileToZip);
                getZipEntry(zipOut, fileToZip, fis);

            } catch (Exception e) {
                log.error("zipDirectory error: {}", e.getMessage());
            } finally {
                try {
                    if (zipOut != null) {
                        zipOut.close();
                    }

                    if (fis != null) {
                        fis.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    log.error("zipFile error: Trowing IOException when trying to close zipOut, fis or fos");
                }
            }
        }
    }

    /**
     * Zipping multiples files
     * @param srcFiles List<String> - List of files to zip
     * @param dirMultiCompressed String - Destination path of the file after zipping
     */
    public static void zipMultipleFiles(final List<String> srcFiles, final String dirMultiCompressed) {
        if (srcFiles != null && !srcFiles.isEmpty() && UValidator.isNotNull(dirMultiCompressed)) {
            FileOutputStream fos = null;
            ZipOutputStream zipOut = null;

            try {

                var dirMultiCompressedZip = "";
                if (dirMultiCompressed.contains(".zip")) {
                    dirMultiCompressedZip = dirMultiCompressed;
                } else {
                    dirMultiCompressedZip = dirMultiCompressed.concat(".zip");
                }

                fos = new FileOutputStream(dirMultiCompressedZip);
                zipOut = new ZipOutputStream(fos);
                for (String srcFile : srcFiles) {
                    val fileToZip = new File(srcFile);
                    val fis = new FileInputStream(fileToZip);
                    getZipEntry(zipOut, fileToZip, fis);
                    fis.close();
                }

            } catch (Exception e) {
                log.error("zipMultipleFiles error: {}", e.getMessage());
            } finally {
                try {

                    if (zipOut != null) {
                        zipOut.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }

                } catch (Exception e) {
                    log.error("zipMultipleFiles error: Trowing IOException when trying to close zipOut, fis or fos");
                }
            }
        }
    }

    /**
     * Zipping a single file
     * @param sourceFile String - Directory (folder) to zip
     * @param dirCompressed String - Destination path of the file after zipping
     */
    public static void zipDirectory(final String sourceFile, final String dirCompressed) {
        if (UValidator.isNotNull(sourceFile) && UValidator.isNotNull(dirCompressed)) {
            FileOutputStream fos = null;
            ZipOutputStream zipOut = null;

            try {

                var dirCompressedZip = "";
                if (dirCompressed.contains(".zip")) {
                    dirCompressedZip = dirCompressed;
                } else {
                    dirCompressedZip = dirCompressed.concat(".zip");
                }

                fos = new FileOutputStream(dirCompressedZip);
                zipOut = new ZipOutputStream(fos);
                val fileToZip = new File(sourceFile);
                val fileName = fileToZip.getName();

                zipFile(fileToZip, fileName, zipOut);

            } catch (Exception e) {
                log.error("zipDirectory error: {}", e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (zipOut != null) {
                        zipOut.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    log.error("zipDirectory error: Trowing IOException when trying to close zipOut or fos");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Zipping a single file with password
     * @param fileToZip String - File to zip
     * @param zipFilePath String - Destination path of the file after zipping
     * @param password String
     */
    public static void zipFileWithPwd(final String fileToZip, final String zipFilePath, final String password) {
        val filesToZip = List.of(fileToZip);
        zipFilesWithPwd(filesToZip, zipFilePath, password);
    }

    /**
     * Zipping a single file with password
     * @param filesToZip List<String> - Files to zip with password
     * @param zipFilePath String - Destination path of the file after zipping
     * @param password String
     */
    public static void zipFilesWithPwd(final List<String> filesToZip, final String zipFilePath, final String password) {
        try {

            List<File> filesToAdd = filesToZip.stream()
                                              .map(File::new)
                                              .collect(Collectors.toList());

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            // Below line is optional. AES 256 is used by default. You can override it to use AES 128. AES 192 is supported only for extracting.
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

            ZipFile zipFile = new ZipFile(zipFilePath, password.toCharArray());
            zipFile.addFiles(filesToAdd, zipParameters);

        } catch (ZipException e) {
            log.error("zipFilesWithPwd error: {} ", e.getMessage());
        }
    }

    /**
     * Extracting from zip
     * @param zipFile String - ZIP file
     * @param destinationPath String - Destination path of the file after unzipping
     */
    public static void unzipFile(final String zipFile, final File destinationPath) {
        ZipInputStream zis = null;
        try {

            byte[] buffer = new byte[1024];
            zis = new ZipInputStream(new FileInputStream(zipFile));
            var zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                val newFile = newFile(destinationPath, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    	safeClose(zis);
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    val parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                    	safeClose(zis);
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    val fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

        } catch (Exception e) {
            log.error("unzipFile error: {}", e.getMessage());
        } finally {
        	safeClose(zis);
        }
    }
    
    private static void safeClose(final ZipInputStream zis) {
    	try {

            if (zis != null) {
                zis.closeEntry();
            }

            if (zis != null) {
                zis.close();
            }

        } catch (Exception e) {
            log.error("unzipFile error: Trowing IOException when trying to close zis");
        }
    }

    /**
     * Extracting a single file from zip which is password protected
     * @param zipFile String - ZIP file
     * @param fileNameInZip String - The file in a ZIP file to be extracted
     * @param destinationPath String - Destination path of the file after unzipping
     * @param password String - Zip file password
     */
    public static void unzipSpecificFileWithPwd(final String zipFile, final String fileNameInZip, final String destinationPath, final String password) {
        try {

            new ZipFile(zipFile, password.toCharArray()).extractFile(fileNameInZip, destinationPath);

        } catch (ZipException e) {
            log.error("unzipFileWithPwd error: {} ", e.getMessage());
        }
    }

    /**
     * Extracting all files from a password protected zip
     * @param zipFile String - ZIP file
     * @param destinationPath String - Destination path of the file after unzipping
     * @param password String - ZIP file password
     */
    public static void unzipFileWithPwd(final String zipFile, final String destinationPath, final String password) {
        try {

            new ZipFile(zipFile, password.toCharArray()).extractAll(destinationPath);

        } catch (ZipException e) {
            log.error("unzipFileWithPwd error: {} ", e.getMessage());
        }
    }

    private static void getZipEntry(final ZipOutputStream zipOut, final File fileToZip, final FileInputStream fis) throws IOException {
        val zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }

    private static void zipFile(final File fileToZip, final String fileName, final ZipOutputStream zipOut) throws IOException {
        FileInputStream fis = null;
        try {

            if (fileToZip.isHidden()) {
                return;
            }

            if (fileToZip.isDirectory()) {
                if (fileName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                }
                zipOut.closeEntry();

                File[] children = fileToZip.listFiles();
                if (children != null) {
                    for (File childFile : children) {
                        zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                    }
                }
                return;
            }

            fis = new FileInputStream(fileToZip);
            val zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

        } catch (Exception e) {
            log.error("zipFile error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private static File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException {
        val destFile = new File(destinationDir, zipEntry.getName());

        val destDirPath = destinationDir.getCanonicalPath();
        val destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
