package by.tut.darrko.webapp.util;

import java.io.File;
import java.util.Objects;

public class FileUtil {
    public static void recursiveDirPrint(File directory, String ident) {
        Objects.requireNonNull(directory, "Path must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable");
        }
        System.out.println(ident + "Directory: " + directory.getName());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                recursiveDirPrint(file, ident + "  ");
            } else {
                System.out.println(ident + "  " + "File: " + file.getName());
            }
        }
    }
}