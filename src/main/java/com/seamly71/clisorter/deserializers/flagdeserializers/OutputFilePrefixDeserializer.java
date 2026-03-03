package com.seamly71.clisorter.deserializers.flagdeserializers;

import com.seamly71.clisorter.sorter.Query;


public class OutputFilePrefixDeserializer implements FlagDeserializer {
    public static final String flag = "-p";
    public static final int argumentCount = 1;

    @Override
    public void deserialize(Query.Builder builder, String[] args) {
        assertArgumentCount(args);

        builder.setFilePrefix(args[0]);
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
