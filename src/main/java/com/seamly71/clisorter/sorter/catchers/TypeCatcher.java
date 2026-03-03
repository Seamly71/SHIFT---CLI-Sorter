package com.seamly71.clisorter.sorter.catchers;

import com.seamly71.clisorter.AppException;
import com.seamly71.clisorter.sorter.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public abstract class TypeCatcher<T> {
    protected List<T> convertedElements = new ArrayList<T>();
    protected List<String> elements = new ArrayList<String>();

    public boolean capture(String element) {
        T convertedElement;
        try {
            convertedElement = toType(element);
        } catch (AppException exception) {
            return false;
        }
        convertedElements.add(convertedElement);
        elements.add(element);
        return true;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void clear() {
        elements.clear();
        convertedElements.clear();
    }

    public void dumpToFile(
            Path directory,
            String prefix,
            Query.OutputWritePolicy policy
    ) {
        Path outputPath;
        try {
            outputPath = Paths.get(
                    directory.toString(),
                    prefix + getFilePostfix()
            );
        } catch (InvalidPathException exception) {
            throw new AppException(String.format(
                    "Невалидный путь для выходного файла: %s", exception
            ));
        }

        if (policy.equals(Query.OutputWritePolicy.APPEND)) {
            if (!Files.exists(outputPath)) {
                throw new AppException(String.format(
                        "Невозможно добавить вывод в файл. Файл не существует: %s", outputPath
                ));
            }

            if (!Files.isWritable(outputPath)) {
                throw new AppException(String.format(
                        "Невозможно добавить вывод в файл. Нет доступа на запись: %s", outputPath
                ));
            }

            try {
                Files.write(outputPath, elements, StandardOpenOption.APPEND);
            } catch (IOException exception) {
                throw new AppException(String.format(
                        "Не смог выполнить запись в файл: %s", exception
                ));
            }
        } else if (policy.equals(Query.OutputWritePolicy.REWRITE)) {
            try {
                Files.write(
                        outputPath,
                        elements,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.CREATE
                );
            } catch (IOException exception) {
                throw new AppException(String.format(
                        "Не смог выполнить запись в файл: %s", exception
                ));
            }
        }
    }

    protected abstract T toType(String element);

    public abstract void printShortSummary();

    public abstract void printFullSummary();

    public abstract String getFilePostfix();
}
