import java.util.Locale;
import java.util.Scanner;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 *         10.08.2015
 */
public class Main {

    public static void main(String[] args) {
        WorkingWithNumbersFinal xAndY = new WorkingWithNumbersFinal();
        xAndY.readOrModifyDataFromFile();

        RegressionCalculator calc = new RegressionCalculator();
        double avgX = calc.calculateAvg(xAndY, 0);
        double avgY = calc.calculateAvg(xAndY, 1);
        double b1 = calc.calculateB1(xAndY);
        double b0 = calc.calculateB0(xAndY);

        Scanner userInput = new Scanner(System.in);
        userInput.useLocale(Locale.ENGLISH);
        System.out.println("Enter the estimated X:");
        double estimatedX = userInput.nextDouble();
        double estimatedY = calc.calculateEstimatedY(estimatedX, b0, b1);

        System.out.println("Estimated Y: " + estimatedY);
        System.out.println("Beta values:");
        System.out.println(b0);
        System.out.println(b1);

        System.out.println("Enter the t value from the table: ");
        double t = userInput.nextDouble();
        System.out.println("Prediction Interval:");
        double range = calc.calculateRange(xAndY, estimatedX, t);
        double[] intervals = calc.calculateUpperAndLowerIntervals(estimatedY, range);
        System.out.println("Lower interval: " + intervals[0] + ", upper interval: " + intervals[1]);
        System.out.println("Deviation " + calc.calculateStandardDeviation(xAndY));
    }
}
