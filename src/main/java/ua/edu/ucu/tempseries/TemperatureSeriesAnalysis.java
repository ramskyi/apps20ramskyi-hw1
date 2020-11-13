package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final int ABSOLUTE_ZERO = -273;
    private double[] temperatures;

    public TemperatureSeriesAnalysis() {
        temperatures = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) throws InputMismatchException {
        for (double temperature : temperatureSeries) {
            if (temperature < ABSOLUTE_ZERO) {
                throw new InputMismatchException("Temperature than absolute zero");
            }
        }
        this.temperatures = temperatureSeries.clone();
    }

    public double average() throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }
        double sum = 0;
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            sum += this.temperatures[i];
        }
        return sum / this.temperatures.length;
    }

    public double deviation() throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }

        double mean = average();
        double variance = 0;
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            double temperature = this.temperatures[i];
            variance += (temperature - mean) * (temperature - mean);
        }
        variance /= this.temperatures.length;
        return Math.sqrt(variance);
    }

    public double min() throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }

        double mini = this.temperatures[0];
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            mini = Math.min(mini, this.temperatures[i]);
        }
        return mini;
    }

    public double max() throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }

        double maxi = this.temperatures[0];
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            maxi = Math.max(maxi, this.temperatures[i]);
        }
        return maxi;
    }

    public double findTempClosestToZero() throws IllegalArgumentException {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }

        double closest = this.temperatures[0];
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            double temperature = this.temperatures[i];
            if (Math.abs(temperature - tempValue) < Math.abs(closest - tempValue) ||
                    Math.abs(temperature - tempValue) == Math.abs(closest - tempValue) && temperature > closest) {
                closest = temperature;
            }
        }
        return closest;
    }

    private double[] findLessOrGreaterThen(double tempValue, boolean less) throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }

        int cntLess = 0;
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            double temperature = this.temperatures[i];
            if (less && temperature < tempValue || !less && temperature > tempValue) {
                cntLess++;
            }
        }

        double[] result = new double[cntLess];
        cntLess = 0;
        for (int i = 0; i < this.temperatures.length && this.temperatures[i] > ABSOLUTE_ZERO; i++) {
            double temperature = this.temperatures[i];
            if (less && temperature < tempValue || !less && temperature > tempValue) {
                result[cntLess++] = temperature;
            }
        }
        return result;
    }

    public double[] findTempsLessThan(double tempValue) throws IllegalArgumentException {
        return findLessOrGreaterThen(tempValue, true);
    }

    public double[] findTempsGreaterThan(double tempValue) throws IllegalArgumentException {
        return findLessOrGreaterThen(tempValue, false);
    }

    public TempSummaryStatistics summaryStatistics() throws IllegalArgumentException {
        if (this.temperatures.length == 0) {
            throw new IllegalArgumentException("Empty data container");
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public double addTemps(double... temps) throws IllegalArgumentException {
        int newLen = this.temperatures.length;
        int oldLen = newLen;
        int actualNewLen = newLen + temps.length;
        if (temps.length > newLen) {
            newLen += temps.length;
        } else {
            newLen *= 2;
        }
        double[] newTemperatures = new double[newLen];

        double sum = 0;
        for (int i = 0; i < actualNewLen; i++) {
            if (i < oldLen) {
                newTemperatures[i] = this.temperatures[i];
            } else {
                newTemperatures[i] = temps[i - oldLen];
            }
            sum += newTemperatures[i];
        }
        this.temperatures = newTemperatures;
        return sum;
    }
}
