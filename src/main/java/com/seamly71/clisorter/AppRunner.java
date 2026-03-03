package com.seamly71.clisorter;


import com.seamly71.clisorter.deserializers.QueryDeserializer;
import com.seamly71.clisorter.sorter.FileSorter;
import com.seamly71.clisorter.sorter.Query;

public class AppRunner {
    private static QueryDeserializer queryDeserializer = new QueryDeserializer();
    private static FileSorter fileSorter = new FileSorter();

    public static void main(String[] args) {
        try {
            Query query = queryDeserializer.deserialize(args);
            fileSorter.sort(query);
        } catch (AppException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
