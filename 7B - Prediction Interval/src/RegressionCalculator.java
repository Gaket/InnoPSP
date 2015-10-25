import java.util.List;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class RegressionCalculator {

    public double calculateAvg(WorkingWithNumbersFinal numbers, int line) {
        List<List<Double>> values = numbers.getNumbers();
        double sum = 0;
        for (Double d : values.get(line)) {
            sum += d;
        }
        return sum/values.get(line).size();
    }

    public double calculateB1(WorkingWithNumbersFinal numbers) {
        double avgX = calculateAvg(numbers, 0);
        double avgY = calculateAvg(numbers, 1);
        double sum = 0;
        for (int i = 0; i < numbers.getArraysLenght(); i++) {
            sum += numbers.getNumbers().get(0).get(i) * numbers.getNumbers().get(1).get(i);
        }
        double numerator = sum - numbers.getArraysLenght() * avgX * avgY;
        double denominator = 0;
        for(double num : numbers.getNumbers().get(0)) {
            denominator += num * num;
        }
        denominator -= numbers.getArraysLenght() * avgX * avgX;
        return numerator/denominator;
    }

    public double calculateEstimatedY(double estimatedX, double b0, double b1) {
        return b0 + b1 * estimatedX;
    }

    public double calculateB0(WorkingWithNumbersFinal numbers) {
        double avgX = calculateAvg(numbers, 0);
        double avgY = calculateAvg(numbers, 1);
        double b1 = calculateB1(numbers);
        return avgY - b1 * avgX;
    }

    //Assignment 8
    // TODO: Ask Alex about the best way to write this methods
    public double calculateVariance(WorkingWithNumbersFinal numbers) {
        double variance = 0;
        double sum = 0;
        double b0 = calculateB0(numbers);
        double b1 = calculateB1(numbers);
        for (int i = 0; i < numbers.getSize(); i++) {
            sum += Math.pow(numbers.getNumbers().get(1).get(i) - b0
                    - b1 * numbers.getNumbers().get(0).get(i) , 2);
        }
        variance = (1.0/(numbers.getSize() - 2)) * sum;
        return variance;
    }

    public double calculateStandardDeviation(WorkingWithNumbersFinal numbers) {
        return Math.sqrt(calculateVariance(numbers));
    }

    public double calculateRange(WorkingWithNumbersFinal numbers, double estimatedX, double tValue) {
        double denominator = 0;
        for (int i = 0; i < numbers.getSize(); i++) {
            denominator += Math.pow(numbers.getNumbers().get(0).get(i) - calculateAvg(numbers, 0), 2);
        }
        double enumerator = Math.pow((estimatedX - calculateAvg(numbers, 0)), 2);
        double rangePart = Math.sqrt(1 + 1/numbers.getSize() + enumerator /denominator);
        return tValue * calculateStandardDeviation(numbers) * rangePart;
    }

    // TODO: Should I use range attribute here?
    public double[] calculateUpperAndLowerIntervals(double estimatedY, double range) {
        double lowerPreictionInterval = estimatedY - range;
        double upperPredictionInterval = estimatedY + range;
        return new double[] {lowerPreictionInterval, upperPredictionInterval};
    }

}
