import java.io.*;
import java.util.*;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class WorkingWithNumbers {

    List<Double> numbers = new ArrayList<>();

    public void start() {
        System.out.println("Hello! Enter the file name: ");
        Scanner reader = new Scanner(System.in);
        String fileName = reader.nextLine();
        File file = new File(fileName);

        System.out.println("Select mode: \n" +
                "1) read, \n" +
                "2) modify");
        String mode = reader.nextLine();

        try {
            switch (mode.trim()) {
                case "1":
                    readFromFile(file);
                    break;
                case "2":
                    writeToFile(file);
                    break;
                default:
                    break;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void readFromFile(File file) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            Scanner scanner = new Scanner(reader);
            scanner.useLocale(Locale.ENGLISH);

            while (scanner.hasNextDouble()) {
                numbers.add(scanner.nextDouble());
            }

            for(Double d : numbers) {
                System.out.println(d);
            }
        }
    }

    public void writeToFile(File file) throws IOException {

        if (!file.exists()) {
            file.createNewFile();
        }

        readFromFile(file);

        workingWithNumbers();


        String formattedNumbers = "";
        for (Double number : numbers) {
            formattedNumbers += number + "\n";
        }

        System.out.println("Would you like to: \n" +
                "1) Overwrite the file \n" +
                "2) Create new file");
        Scanner reader = new Scanner(System.in);

        // TODO: decise, hot wo make the menu properly
        switch (reader.nextLine().trim()) {
            case "1":
                break;
            case "2":
                System.out.println("Enter the new file name:");
                file = new File(reader.nextLine());
                break;
            default:
                System.out.println("Enter '1' or '2', please.");
                break;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {
            writer.write(formattedNumbers);
            System.out.printf("Numbers were successfully written into: \n%s \n", file.getAbsolutePath());
        }
    }

    private void workingWithNumbers() {
        Scanner reader = new Scanner(System.in);
        reader.useLocale(Locale.ENGLISH);
        ListIterator<Double> it = numbers.listIterator();
        boolean ended = false;

        double newValue;
        while (it.hasNext() && !ended) {
            System.out.println("Number:");
            System.out.println(it.next());

            System.out.println("1) Accept \n" +
                    "2) Replace \n" +
                    "3) Delete \n" +
                    "4) Insert new number \n" +
                    "5) Accept all remainder");
            String userAnswer = reader.nextLine();

            switch (userAnswer.trim()) {
                case "1":
                    break;
                case "2":
                    System.out.println("Enter new number: ");

                    newValue = reader.nextDouble();
                    reader.nextLine();
                    it.set(newValue);
                    System.out.println("Number was successfully changed.");
                    break;
                case "3":
                    System.out.println("Number was successfully deleted.");
                    it.remove();
                    break;
                case "4":
                    System.out.println("Enter new number: ");
                    newValue = reader.nextDouble();
                    reader.nextLine();
                    it.add(newValue);
                    System.out.println("Number was successfully added.");
                    break;
                case "5":
                    System.out.println("All remainder accepted");
                    ended = true;
                    break;
                default:
                    break;

            }
        }
        System.out.println("There are no more numbers here.");

        ended = false;
        while (!ended) {
            System.out.println("1) Add new. 2) Exit");

            String temp = reader.nextLine().trim();
            //reader.skip("[\r\n]+");

            switch (temp) {
                case "1":
                    System.out.println("Enter new number: ");
                    newValue = reader.nextDouble();
                    reader.nextLine();
                    it.add(newValue);
                    System.out.println("Number was successfully added.");
                    break;
                case "2":
                    ended = true;
                    break;
                default:
            }
        }

        System.out.println("Thank you for using the program!");
    }

    public static void main(String[] args) {
        WorkingWithNumbers program = new WorkingWithNumbers();
        program.start();
    }
}
