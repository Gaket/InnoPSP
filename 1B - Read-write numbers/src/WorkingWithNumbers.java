import java.io.*;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class WorkingWithNumbers {

    public void start() {
        System.out.println("Hello! Enter the file name: ");
        Scanner reader = new Scanner(System.in);
        String fileName = reader.nextLine();
        File file = new File(fileName);

        System.out.println("What mode do you like: 1) read, 2) write");
        String mode = reader.nextLine();

        try {
            switch (mode) {
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
                System.out.println(scanner.nextDouble());
            }
        }
    }

    public void writeToFile(File file) throws IOException {

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            System.out.println("Enter the count of numbers that you want to write:");
            Scanner reader = new Scanner(System.in);
            reader.useLocale(Locale.ENGLISH);
            int totalNumber = reader.nextInt();
            String numbersToWrite = "";

            for (int i = 0; i < totalNumber; i++) {
                System.out.println("Enter the number to write it into the file:");
                numbersToWrite += reader.nextDouble();
                numbersToWrite += "\n";
            }

            writer.write(numbersToWrite);

        }
    }

    public static void main(String[] args) {

        WorkingWithNumbers program = new WorkingWithNumbers();
        program.start();
    }
}
