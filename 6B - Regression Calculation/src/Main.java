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
        double b1 = calc.calculateB1(xAndY, avgX, avgY);
        double b0 = calc.calculateB0(avgX, avgY, b1);

        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the estimated X:");
        double estimatedX = userInput.nextDouble();
        double estimatedY = calc.calculateEstimatedY(estimatedX, b0, b1);

        System.out.println("Estimated Y: " + estimatedY);
        System.out.println(b0);
        System.out.println(b1);
    }
}
