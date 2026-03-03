package com.seamly71.clisorter.deserializers.flagdeserializers;

import static com.seamly71.clisorter.deserializers.Utils.getPath;

import com.seamly71.clisorter.AppException;
import com.seamly71.clisorter.sorter.Query;
import java.nio.file.Files;
import java.nio.file.Path;


public class OutputDirectoryDeserializer implements FlagDeserializer {
    public static final String flag = "-o";
    public static final int argumentCount = 1;

    @Override
    public void deserialize(Query.Builder builder, String[] args) {
        assertArgumentCount(args);

        Path path = getPath(args[0]);

        if (!Files.isDirectory(path)) {
            throw new AppException(
                    String.format("Путь не ведет к директории: %s", path)
            );
        }

        if (!Files.isWritable(path)) {
            throw new AppException(
                    String.format("Нет доступа на запись в папку: %s", path)
            );
        }

        builder.setOutputDirectory(path);
    }

    @Override
    public int getArgumentCount() {
        return argumentCount;
    }

    @Override
    public String getFlag() {
        return flag;
    }
}
