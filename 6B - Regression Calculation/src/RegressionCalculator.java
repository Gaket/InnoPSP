import java.util.DoubleSummaryStatistics;
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

    public double calculateB1(WorkingWithNumbersFinal x, double avgX, double avgY) {
        double sum = 0;
        for (int i = 0; i < x.getArraysLenght(); i++) {
            sum += x.getNumbers().get(0).get(i) * x.getNumbers().get(1).get(i);
        }
        double numerator = sum - x.getArraysLenght() * avgX * avgY;
        double denominator = 0;
        for(double num : x.getNumbers().get(0)) {
            denominator += num * num;
        }
        denominator -= x.getArraysLenght() * avgX * avgX;
        return numerator/denominator;
    }

    public double calculateEstimatedY(double estimatedX, double b0, double b1) {
        return b0 + b1 * estimatedX;
    }

    public double calculateB0(double avgX, double avgY, double b1) {
        return avgY - b1 * avgX;
    }
}
