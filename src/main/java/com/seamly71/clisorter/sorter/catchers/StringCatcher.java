package com.seamly71.clisorter.sorter.catchers;


public class StringCatcher extends TypeCatcher<String> {
    private static final String filePostfix = "strings.txt";

    @Override
    protected String toType(String element) {
        return element;
    }

    @Override
    public void printShortSummary() {
        System.out.printf("Количество строчных элементов: %s%n", elements.size());
    }

    @Override
    public void printFullSummary() {
        int minLength = Integer.MAX_VALUE;
        int maxLength = 0;

        for (String i : convertedElements) {
            int currentLength = i.length();
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
            if (currentLength < minLength) {
                minLength = currentLength;
            }
        }

        System.out.printf("Количество строчных элементов: %s%n", elements.size());
        System.out.printf("Минимальная длинна строчных элементов: %s%n", minLength);
        System.out.printf("Максимальная длинна строчных элементов: %s%n", maxLength);
        System.out.println();
    }

    @Override
    public String getFilePostfix() {
        return filePostfix;
    }
}
