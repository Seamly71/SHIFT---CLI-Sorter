package com.seamly71.clisorter.deserializers;

import com.seamly71.clisorter.AppException;
import com.seamly71.clisorter.deserializers.flagdeserializers.AppendOutputDeserializer;
import com.seamly71.clisorter.deserializers.flagdeserializers.FlagDeserializer;
import com.seamly71.clisorter.deserializers.flagdeserializers.FullSummaryDeserializer;
import com.seamly71.clisorter.deserializers.flagdeserializers.OutputDirectoryDeserializer;
import com.seamly71.clisorter.deserializers.flagdeserializers.OutputFilePrefixDeserializer;
import com.seamly71.clisorter.deserializers.flagdeserializers.ShortSummaryDeserializer;
import com.seamly71.clisorter.sorter.Query;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class QueryDeserializer {
    private final Map<String, FlagDeserializer> flags;
    private MutableBoolean isSummaryVerbositySet = new MutableBoolean(false);
    private Map<String, MutableBoolean> isFlagUsed;
    private Deserializer inputFileDeserializer = new InputFileDeserializer();
    private MutableBoolean atLeastOneInput = new MutableBoolean(false);

    public QueryDeserializer() {
        OutputDirectoryDeserializer outputDirectoryDeserializer = new OutputDirectoryDeserializer();
        OutputFilePrefixDeserializer outputFilePrefixDeserializer =
                new OutputFilePrefixDeserializer();
        AppendOutputDeserializer appendOutputDeserializer = new AppendOutputDeserializer();
        ShortSummaryDeserializer shortSummaryDeserializer = new ShortSummaryDeserializer();
        FullSummaryDeserializer fullSummaryDeserializer = new FullSummaryDeserializer();

        this.flags = Map.of(
                outputDirectoryDeserializer.getFlag(), outputDirectoryDeserializer,
                outputFilePrefixDeserializer.getFlag(), outputFilePrefixDeserializer,
                appendOutputDeserializer.getFlag(), appendOutputDeserializer,
                shortSummaryDeserializer.getFlag(), shortSummaryDeserializer,
                fullSummaryDeserializer.getFlag(), fullSummaryDeserializer
        );

        MutableBoolean isSummaryVerbositySet = new MutableBoolean(false);

        this.isFlagUsed = new HashMap<>(Map.of(
                outputDirectoryDeserializer.getFlag(), new MutableBoolean(false),
                outputFilePrefixDeserializer.getFlag(), new MutableBoolean(false),
                appendOutputDeserializer.getFlag(), new MutableBoolean(false),
                shortSummaryDeserializer.getFlag(), isSummaryVerbositySet,
                fullSummaryDeserializer.getFlag(), isSummaryVerbositySet
        ));
    }

    public Query deserialize(String[] words) {
        Query.Builder queryBuilder = new Query.Builder();
        int wordSize = words.length;

        for (int i = 0; i < wordSize; i++) {
            String word = words[i];

            if (flags.containsKey(word)) {
                if (isFlagUsed.get(word).isTrue()) {
                    throw new AppException(String.format(
                            "Конфликт флагов: %s", word
                    ));
                }

                FlagDeserializer deserializer = flags.get(word);
                int argumentCount = deserializer.getArgumentCount();
                if (i + argumentCount >= wordSize) {
                    throw new AppException(String.format(
                            "Для флага %s не хватает аргументов, ожидалось %d",
                            word,
                            argumentCount
                    ));
                }

                String[] args = Arrays.copyOfRange(words, i + 1, i + 1 + argumentCount);
                deserializer.deserialize(queryBuilder, args);

                isFlagUsed.get(word).setTrue();
                i = i + argumentCount;
            } else {
                inputFileDeserializer.deserialize(
                        queryBuilder,
                        Arrays.copyOfRange(words, i, i + 1)
                );
                atLeastOneInput.setTrue();
            }
        }

        if (atLeastOneInput.isFalse()) {
            throw new AppException(
                    "Не было предоставлено ни одного входного файла"
            );
        }

        return queryBuilder.build();
    }
}
