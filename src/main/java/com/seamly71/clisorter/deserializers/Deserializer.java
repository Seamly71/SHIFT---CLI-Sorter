package com.seamly71.clisorter.deserializers;

import com.seamly71.clisorter.sorter.Query;

public interface Deserializer {
    void deserialize(Query.Builder builder, String[] args);

    int getArgumentCount();

    default void assertArgumentCount(String[] args) {
        int supposedCount = getArgumentCount();
        int actualCount = args.length;
        if (actualCount != supposedCount) {
            throw new IllegalArgumentException(String.format(
                    "deserializeOutputDirectory принимает строго %d строковый аргумент, передано "
                            + "%d", supposedCount, actualCount
            ));
        }
    }
}