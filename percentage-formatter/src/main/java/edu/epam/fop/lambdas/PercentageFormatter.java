package edu.epam.fop.lambdas;

import java.text.DecimalFormat;
import java.util.function.DoubleFunction;

public interface PercentageFormatter {

  DoubleFunction<String> INSTANCE = (number) -> number == ((int)(number*100.0)) / 100.0 ? (int)(number*100) + " %" : new DecimalFormat("0.0 %").format(number);
}
