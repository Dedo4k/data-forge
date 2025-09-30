package dev.vlxd.dataforge.scylla.model.util;

import java.nio.file.Path;

public class FileUtils {

    public static String removeExtension(Path path) {
        String filename = path.toString();
        int index = filename.lastIndexOf('.');
        return index > 0 ? filename.substring(0, index) : "";
    }

    public static String getExtension(Path path) {
        String filename = path.getFileName().toString();
        int index = filename.lastIndexOf('.');
        return index > 0 ? filename.substring(index + 1).toLowerCase() : "";
    }
}
