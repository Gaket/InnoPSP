import java.io.*;
import java.util.*;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class WorkingWithNumbers2 {

    List<Double> numbers = new ArrayList<>();

    public void start() {
        System.out.println("Hello!");
        File file;
        Scanner inpReader;
        String userAnswer;
        do {
            System.out.println("Enter the file name: ");
            inpReader = new Scanner(System.in);
            userAnswer = inpReader.nextLine();
            file = new File(userAnswer);
            if (!file.exists()) {
                System.out.println("File not exist");
                System.out.println("Do you want to create new file with this name? (y/n/exit)");
                userAnswer = inpReader.nextLine();
                if (userAnswer.toLowerCase().equals("exit")) {
                    System.exit(0);
                }
                if (userAnswer.toLowerCase().equals("y")) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        System.out.println("Error in creating the file: " + ex.getMessage());
                        System.out.println("Let's try to create or open another file.");
                    }

                }
            }
        }
        while (!file.exists());

        boolean correctMode;
        do {
            correctMode = true;
            System.out.println("Select mode: \n" +
                    "1) Read numbers from file \n" +
                    "2) Modify existing file \n" +
                    "0) Exit");
            String answer = inpReader.nextLine();
            if (answer.toLowerCase().equals("exit") || answer.equals("0")) {
                System.exit(0);
            }
            try {
                switch (answer.trim()) {
                    case "1":
                        readFromFile(file);
                        break;
                    case "2":
                        writeToFile(file);
                        break;
                    default:
                        correctMode = false;
                        System.out.println("Error. Choose one of existing modes' number or write 'exit'.");
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } while (!correctMode);

    }

    public void readFromFile(File file) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            Scanner scanner = new Scanner(reader);
            scanner.useLocale(Locale.ENGLISH);

            if (!scanner.hasNextDouble()) {
                System.out.println("File " + file.getAbsolutePath() + "does not have numbers or " +
                        "it is not well-formatted.");
            }

            while (scanner.hasNextDouble()) {
                numbers.add(scanner.nextDouble());
            }

            for (Double d : numbers) {
                System.out.println(d);
            }
        }
    }

    public void writeToFile(File file) throws IOException {

        readFromFile(file);
        System.out.println();

        workingWithNumbers();

        savingTheFile(file);
    }

    private void savingTheFile(File file) throws IOException {
        System.out.println("Would you like to: \n" +
                "1) Overwrite the file \n" +
                "2) Create new file");
        Scanner inpReader = new Scanner(System.in);
        String userAnswer;

        boolean correctMode = false;
        do {
            correctMode = true;
            switch (inpReader.nextLine().trim()) {
                case "1":
                    break;
                case "2":
                    do {
                        do {
                            System.out.println("Enter the new file name. Otherwise, enter 'exit'");
                            userAnswer = inpReader.nextLine();
                            if (userAnswer.toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                            file = new File(userAnswer);
                            if (file.exists()) {
                                System.out.println("The file already exists. Choose another file.");
                            }
                        }
                        while (file.exists());
                        try {
                            file.createNewFile();
                        } catch (IOException ex) {
                            System.out.println("Error in file creating: " + ex.getMessage());
                            System.out.println("Let's try to create another file.");
                        }
                    }
                    while (!file.exists());
                    break;
                default:
                    correctMode = false;
                    System.out.println("Please, choose a correct mode number.");
                    break;
            }
        }
        while (!correctMode);

        String formattedNumbers = "";
        for (Double number : numbers) {
            formattedNumbers += number + "\n";
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {
            writer.write(formattedNumbers);
            System.out.printf("Numbers were successfully written into: \n%s \n", file.getAbsolutePath());
        }
    }

    private void workingWithNumbers() {
        Scanner inputReader = new Scanner(System.in);
        inputReader.useLocale(Locale.ENGLISH);
        ListIterator<Double> it = numbers.listIterator();
        boolean ended = false;

        double newValue = 0;
        while (it.hasNext() && !ended) {
            System.out.println("Number:");
            System.out.println(it.next());

            boolean correctMode;
            boolean correctNumber;
            String userAnswer;
            do {
                System.out.println("1) Accept \n" +
                        "2) Replace \n" +
                        "3) Delete \n" +
                        "4) Insert new number \n" +
                        "5) Accept all remainder");
                userAnswer = inputReader.nextLine();

                correctMode = true;
                switch (userAnswer.trim()) {
                    case "1":
                        break;
                    case "2":
                        do {
                            System.out.println("Enter new number: ");
                            correctNumber = true;
                            try {
                                newValue = inputReader.nextDouble();
                                it.set(newValue);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number is incorrect. Correct example: '3.1415'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                            if (inputReader.nextLine().toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                        } while (!correctNumber);
                        System.out.println("Number " + newValue + " was successfully changed.");
                        break;
                    case "3":
                        System.out.println("Number was successfully deleted.");
                        it.remove();
                        break;
                    case "4":
                        do {
                            System.out.println("Enter new number: ");
                            correctNumber = true;
                            try {
                                newValue = inputReader.nextDouble();
                                it.add(newValue);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number is incorrect. Correct example: '3.1415'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                            if (inputReader.nextLine().toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                        } while (!correctNumber);
                        System.out.println("Number was successfully added.");
                        break;
                    case "5":
                        System.out.println("All remainder accepted");
                        ended = true;
                        break;
                    default:
                        System.out.println("Please, choose a correct mode number.");
                        correctMode = false;
                        break;
                }
            } while (!correctMode);

        }
        System.out.println("There are no more numbers here.");

        ended = false;
        while (!ended) {
            System.out.println("1) Add new. 2) Save numbers");

            String temp = inputReader.nextLine().trim();
            //reader.skip("[\r\n]+");

            boolean correctMode;
            boolean correctNumber;
            do {
                correctMode = true;
                switch (temp) {
                    case "1":
                        do {
                            System.out.println("Enter new number: ");
                            correctNumber = true;
                            try {
                                newValue = inputReader.nextDouble();
                                it.add(newValue);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number is incorrect. Correct example: '3.1415'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                            if (inputReader.nextLine().toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                        } while (!correctNumber);
                        System.out.println("Number was successfully added.");
                        break;
                    case "2":
                        ended = true;
                        break;
                    default:
                        correctMode = false;
                        System.out.println("Please, choose a correct mode number.");
                        break;
                }
            } while (!correctMode);
        }
        System.out.println("Thank you for using the program!");
    }

    public static void main(String[] args) {
        WorkingWithNumbers2 program = new WorkingWithNumbers2();
        program.start();
    }
}
