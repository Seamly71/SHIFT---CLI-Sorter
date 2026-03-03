package com.seamly71.clisorter.sorter.catchers;

import com.seamly71.clisorter.AppException;


public class DoubleCatcher extends TypeCatcher<Double> {
    private static final String filePostfix = "floats.txt";

    @Override
    protected Double toType(String element) {
        try {
            return Double.valueOf(element);
        } catch (NumberFormatException exception) {
            throw new AppException(String.format(
                    "Не смог привести к вещественному: %s", element
            ));
        }
    }

    @Override
    public void printShortSummary() {
        System.out.printf("Количество вещественных элементов: %s%n", elements.size());
    }

    @Override
    public void printFullSummary() {
        double summ = 0;
        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;

        for (double i : convertedElements) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
            summ += i;
        }

        System.out.printf("Количество вещественных элементов: %s%n", elements.size());
        System.out.printf("Минимальный вещественный элемент: %s%n", min);
        System.out.printf("Максимальный вещественный элемент: %s%n", max);
        System.out.printf("Cумма вещественных элементов: %s%n", summ);
        System.out.printf("Cреднее арифметическое вещественных элементов: %s%n",
                summ / elements.size());
        System.out.println();
    }

    @Override
    public String getFilePostfix() {
        return filePostfix;
    }

}
