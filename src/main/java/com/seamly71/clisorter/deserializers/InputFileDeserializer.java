package com.seamly71.clisorter.deserializers;

import static com.seamly71.clisorter.deserializers.Utils.getPath;

import com.seamly71.clisorter.AppException;
import com.seamly71.clisorter.sorter.Query;
import java.nio.file.Files;
import java.nio.file.Path;


public class InputFileDeserializer implements Deserializer {
    public static final int argumentCount = 1;

    @Override
    public void deserialize(Query.Builder builder, String[] args) {
        assertArgumentCount(args);

        Path path = getPath(args[0]);

        if (!Files.isRegularFile(path)) {
            throw new AppException(
                    String.format("Путь не ведет к файлу: %s", path)
            );
        }

        if (!Files.isReadable(path)) {
            throw new AppException(
                    String.format("Нет доступа на чтение файла: %s", path)
            );
        }

        builder.addTargetFilePath(path);
    }

    @Override
    public int getArgumentCount() {
        return argumentCount;
    }
}
