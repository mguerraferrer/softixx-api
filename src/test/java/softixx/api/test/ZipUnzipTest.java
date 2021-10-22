package softixx.api.test;

import lombok.val;
import softixx.api.util.UZip;

import java.io.File;
import java.util.List;

public class ZipUnzipTest {
    public static void main(String[] args) {
        // Zip de archivo
        zipFileTest();
        // Zip de multiples archivos
        zipMultipleFilesTest();
        // Zip de directorio
        zipDirectoryTest();
        // Unzip archivo o drectorio
        unzipFileTest();
        // Zip de archivo con contraseña
        zipFileWithPwdTest();
        // Zip de varios arhivos con contraseña
        zipFilesWithPwdTest();
        // Unzip archivo especifico dentro de un archivo Zip con contraseña
        unzipSpecificFileWithPwdTest();
        // Unzip archivo(s) con contraseña
        unzipFileWithPwdTest();
    }

    private static void zipFileTest() {
        val sourceFile = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_es_MX.properties";
        val dirCompressed = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-file-test.zip";
        UZip.zipFile(sourceFile, dirCompressed);
        System.out.println("--- zipFileTest");
    }

    private static void zipMultipleFilesTest() {
        val file1 = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_en_US.properties";
        val file2 = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_es_MX.properties";
        List<String> srcFiles = List.of(file1, file2);
        String dirMultiCompressed = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-multiple-files-test.zip";
        UZip.zipMultipleFiles(srcFiles, dirMultiCompressed);
        System.out.println("--- zipMultipleFilesTest");
    }

    private static void zipDirectoryTest() {
        val sourceFile = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources";
        val dirCompressed = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-directory-test.zip";
        UZip.zipDirectory(sourceFile, dirCompressed);
        System.out.println("--- zipDirectoryTest");
    }

    private static void zipFileWithPwdTest() {
        val sourceFile = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_es_MX.properties";
        val dirCompressed = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-file-with-password-test.zip";
        val password = "kun";
        UZip.zipFileWithPwd(sourceFile, dirCompressed, password);
        System.out.println("--- zipFileWithPwdTest");
    }

    private static void zipFilesWithPwdTest() {
        val file1 = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_en_US.properties";
        val file2 = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\SOFTIXX_API\\src\\main\\resources\\api-messages_es_MX.properties";
        List<String> srcFiles = List.of(file1, file2);
        val dirCompressed = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-files-with-password-test.zip";
        val password = "kun";
        UZip.zipFilesWithPwd(srcFiles, dirCompressed, password);
        System.out.println("--- zipFilesWithPwdTest");
    }

    private static void unzipFileTest() {
        String fileZip = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-directory-test.zip";
        File destDir = new File("E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\unzip-directory-test");
        UZip.unzipFile(fileZip, destDir);
        System.out.println("--- unzipFileTest");
    }

    private static void unzipSpecificFileWithPwdTest() {
        val zipFile = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-file-with-password-test.zip";
        val fileNameInZip = "api-messages_es_MX.properties";
        val destDir = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\unzip-specific-file-with-pwd-test";
        val password = "kun";
        UZip.unzipSpecificFileWithPwd(zipFile, fileNameInZip, destDir, password);
        System.out.println("--- unzipSpecificFileWithPwdTest");
    }

    private static void unzipFileWithPwdTest() {
        val zipFile = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\zip-files-with-password-test.zip";
        val destDir = "E:\\DEVELOPMENT\\PERSONAL_WORK\\PROJECTS\\SOFTIXX\\TMP\\unzip-file-with-pwd-test";
        val password = "kun";
        UZip.unzipFileWithPwd(zipFile, destDir, password);
        System.out.println("--- unzipFileWithPwdTest");
    }
}
