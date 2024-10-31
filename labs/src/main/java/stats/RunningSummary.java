package stats;

import java.util.List;

public class RunningSummary implements StatSummary {
    int len = 0;
    double sum = 0;
    double mean;
    double standardDeviation;
    int maxCapacity = 10;
    int squaresSum = 0;

    @Override
    public double mean() {
        if (len == 0) {
            throw new NotEnoughDataException("Not enough data to perform the operation.");
        }
        return (sum/len);
    }

    @Override
    public int n() {
        return len;
    }

    @Override
    public double sum() {
        return sum;
    }

    @Override
    public double standardDeviation() {
        if (len < 2) {
            throw new NotEnoughDataException("Not enough data to perform the operation.");
        }
        return Math.sqrt((squaresSum - (sum * sum / len)) / (len - 1));
    }

    @Override
    public StatSummary add(double value) {
        len++;
        sum += value;
        squaresSum += value * value;
        return null;
    }

    @Override
    public StatSummary add(Number value) {
        return this.add(value.doubleValue());
    }

    @Override
    public StatSummary add(List<? extends Number> values) {
        for (Number value : values) {
            this.add(value.doubleValue());
        }
        return null;
    }
}
