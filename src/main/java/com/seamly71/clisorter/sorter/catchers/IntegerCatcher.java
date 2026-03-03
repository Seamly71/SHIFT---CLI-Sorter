package com.seamly71.clisorter.sorter.catchers;

import com.seamly71.clisorter.AppException;


public class IntegerCatcher extends TypeCatcher<Long> {
    private static final String filePostfix = "integers.txt";

    @Override
    protected Long toType(String element) {
        try {
            return Long.valueOf(element);
        } catch (NumberFormatException exception) {
            throw new AppException(String.format(
                    "Не смог привести к целому: %s", element
            ));
        }
    }

    @Override
    public void printShortSummary() {
        System.out.printf("Количество целых элементов: %s%n", elements.size());
    }

    @Override
    public void printFullSummary() {
        long summ = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (long i : convertedElements) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
            summ += i;
        }

        System.out.printf("Количество целых элементов: %s%n", elements.size());
        System.out.printf("Минимальный целый элемент: %s%n", min);
        System.out.printf("Максимальный целый элемент: %s%n", max);
        System.out.printf("Cумма целых элементов: %s%n", summ);
        System.out.printf("Cреднее арифметическое целых элементов: %s%n",
                summ / (double) elements.size());
        System.out.println();
    }

    @Override
    public String getFilePostfix() {
        return filePostfix;
    }
}
