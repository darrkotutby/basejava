package by.tut.darrko.webapp.util;

import java.io.File;
import java.util.Arrays;
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
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files, (o1, o2) -> {
                if (o1.isDirectory() && o2.isDirectory()) {
                    return o1.compareTo(o2);
                }
                if (o1.isDirectory() && !o2.isDirectory()) {
                    return -11;
                }
                if (!o1.isDirectory() && o2.isDirectory()) {
                    return 1;
                }
                return o1.compareTo(o2);
            });
            for (File file : Objects.requireNonNull(files)) {
                if (file.isDirectory()) {
                    recursiveDirPrint(file, ident + "  ");
                } else {
                    System.out.println(ident + "  " + "File: " + file.getName());
                }
            }
        }
    }
}

