package com.seamly71.clisorter.deserializers.flagdeserializers;

import com.seamly71.clisorter.sorter.Query;


public class AppendOutputDeserializer implements FlagDeserializer {
    public static final String flag = "-a";
    public static final int argumentCount = 0;

    @Override
    public void deserialize(Query.Builder builder, String[] args) {
        assertArgumentCount(args);

        builder.setOutputWritePolicy(Query.OutputWritePolicy.APPEND);
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
