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

        Scanner userInput = new Scanner(System.in);
        userInput.useLocale(Locale.ENGLISH);

        // Sorting test
        System.out.println("Unsorted:");
        xAndY.printColumns();

        System.out.println("Sorted by 1 column");
        xAndY.sort(0);
        xAndY.printColumns();

        StatisticsCalculator calculator = new StatisticsCalculator();
        calculator.setRecords(xAndY.getNumbers().get(0));
        calculator.print();

        System.out.println("Sorted by 2 column");
        xAndY.sort(1);
        xAndY.printColumns();

        // Calculating statistics:

        calculator.setRecords(xAndY.getNumbers().get(1));
        calculator.print();
    }
}
