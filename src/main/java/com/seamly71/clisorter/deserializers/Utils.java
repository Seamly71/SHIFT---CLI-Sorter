package com.seamly71.clisorter.deserializers;

import com.seamly71.clisorter.AppException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static Path getPath(String pathString) {
        Path path;
        try {
            path = Paths.get(pathString);
        } catch (InvalidPathException exception) {
            throw new AppException(
                    String.format("Невалидный путь файловой системы: %s", pathString)
            );
        }
        return path;
    }
}
