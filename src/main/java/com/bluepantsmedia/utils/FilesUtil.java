package com.bluepantsmedia.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class FilesUtil {
    private FilesUtil() {
    }

    public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walk(directory)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
