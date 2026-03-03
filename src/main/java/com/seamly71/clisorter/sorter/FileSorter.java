package com.seamly71.clisorter.sorter;

import com.seamly71.clisorter.AppException;
import com.seamly71.clisorter.sorter.catchers.DoubleCatcher;
import com.seamly71.clisorter.sorter.catchers.IntegerCatcher;
import com.seamly71.clisorter.sorter.catchers.StringCatcher;
import com.seamly71.clisorter.sorter.catchers.TypeCatcher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSorter {

    private List<String> elements = new ArrayList<String>();
    private static final List<TypeCatcher<?>> catchers = List.of(
            new IntegerCatcher(),
            new DoubleCatcher(),
            new StringCatcher()
    );

    public void sort(Query query) {
        for (TypeCatcher<?> catcher : catchers) {
            catcher.clear();
        }

        captureElements(query);

        for (String element : elements) {
            for (TypeCatcher<?> catcher : catchers) {
                if (catcher.capture(element)) {
                    break;
                }
            }
        }

        for (TypeCatcher<?> catcher : catchers) {
            if (!catcher.isEmpty()) {
                catcher.dumpToFile(
                        query.getOutputDirectory(),
                        query.getFilePrefix(),
                        query.getOutputWritePolicy()
                );
            }
        }

        Query.SummaryVerbosity verbosity = query.getSummaryVerbosity();
        if (verbosity.equals(Query.SummaryVerbosity.FULL)) {
            for (TypeCatcher<?> catcher : catchers) {
                if (!catcher.isEmpty()) {
                    catcher.printFullSummary();
                }
            }
        } else if (verbosity.equals(Query.SummaryVerbosity.SHORT)) {
            for (TypeCatcher<?> catcher : catchers) {
                if (!catcher.isEmpty()) {
                    catcher.printShortSummary();
                }
            }
        }
    }

    public void captureElements(Query query) {
        elements.clear();

        for (Path inPath : query.getTargetFilePaths()) {
            try {
                elements.addAll(Files.readAllLines(inPath));
            } catch (IOException exception) {
                throw new AppException(String.format(
                        "Не смог считать файл %s, подробнее: %s",
                        inPath,
                        exception.getMessage()
                ));
            }
        }
    }
}
