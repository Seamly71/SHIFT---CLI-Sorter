package com.seamly71.clisorter.deserializers.flagdeserializers;

import com.seamly71.clisorter.sorter.Query;


public class ShortSummaryDeserializer implements FlagDeserializer {
    public static final String flag = "-s";
    public static final int argumentCount = 0;

    @Override
    public void deserialize(Query.Builder builder, String[] args) {
        assertArgumentCount(args);

        builder.setSummaryVerbosity(Query.SummaryVerbosity.SHORT);
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
