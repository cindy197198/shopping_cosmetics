package vn.edu.vinaenter.defines;

import org.apache.commons.io.FilenameUtils;

public class FileDefine {
    public static final String DIR_UPLOAD = "WEB-INF/files";

    public static String rename(String fileName) {
        return FilenameUtils.getBaseName(fileName) + "-" + System.nanoTime() + "-" + FilenameUtils.getExtension(fileName);
    }
}
