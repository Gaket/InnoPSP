import java.io.*;
import java.util.*;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class WorkingWithNumbersFinal {

    private List<List<Double>> numbers = new ArrayList<>();
    private List<Record> records = null;
    private int arraysLenght = 0;

    public static void main(String[] args) {
        WorkingWithNumbersFinal program = new WorkingWithNumbersFinal();
        program.readOrModifyDataFromFile();
        System.out.println("Thank you for using the program!");
    }

    public int getSize() {
        return numbers.get(0).size();
    }

    public void readOrModifyDataFromFile() {
        System.out.println("Hello!");
        File file = fileChoosing();
        Scanner inpReader = new Scanner(System.in);
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

        Scanner fileReader = null;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            fileReader = new Scanner(reader);
            fileReader.useDelimiter("\\n");

            numbers.clear();
            while (fileReader.hasNextLine()) {
                List<Double> numbersLine = new ArrayList<>();
                String[] line = fileReader.nextLine().split("\\s");
                for (int i = 0; i < line.length; i++) {
                    numbersLine.add(Double.parseDouble(line[i]));
                }
                numbers.add(numbersLine);

                if (arraysLenght != 0 && arraysLenght != numbersLine.size()) {
                    throw new InputMismatchException("Line sizes are not equal");
                } else {
                    arraysLenght = numbersLine.size();
                }
            }

            if (numbers.size() == 0) {
                System.out.println("File " + file.getAbsolutePath() + " does not have numbers or " +
                        "it is not well-formatted.");
            }
            else {
                records = createRecordsList();
            }
        }
    }

    public void writeToFile(File file) throws IOException {

        readFromFile(file);
        System.out.println();

        askingTheLenght();
        workingWithNumbers();

        savingTheFile(file);
    }

    private List<Record> createRecordsList() {
        if (numbers == null) {
            throw new IllegalStateException();
        }
        records = new ArrayList<>();
        Record rec;
        for (int i = 0; i < arraysLenght; i++) {
            rec = new Record();
            for (int j = 0; j < numbers.size(); j++) {
                rec.add(numbers.get(j).get(i));
            }
            records.add(rec);
        }
        return records;
    }

    public List<Record> sort(int keyColumn) {
        if (keyColumn > numbers.size() || keyColumn < 0) {
            throw new IllegalArgumentException();
        }
        records = createRecordsList();
        records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                if (o1.getValues().get(keyColumn) < o2.getValues().get(keyColumn)) {
                    return -1;
                } else if (o1.getValues().get(keyColumn) > o2.getValues().get(keyColumn)) {
                    return 1;
                }
                return 0;
            }
        });
        return records;
    }

    public List<List<Double>> getNumbers() {
        return numbers;
    }

    public int getArraysLenght() {
        return arraysLenght;
    }

    public void printNumbers() {
        for (List<Double> line : numbers) {
            for (Double value : line) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printColumns() {
        if (records == null) {
            throw new IllegalStateException();
        }
        for (Record rec : records) {
            System.out.println(rec);
        }
        System.out.println();
    }

    private File fileChoosing() {
        File file;
        Scanner inpReader = new Scanner(System.in);
        String userAnswer;
        do {
            System.out.println("Enter the file name. System will automatically add extension .data ");
            userAnswer = inpReader.nextLine();
            file = new File(userAnswer + ".data");
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

                } else {
                    System.out.println("Then choose another file.");
                }
            }
        }
        while (!file.exists());
        return file;
    }

    private void askingTheLenght() {
        Scanner inputReader = null;
        do {
            System.out.println("What is the lenght of the arrays?");
            inputReader = new Scanner(System.in);
            try {
                arraysLenght = inputReader.nextInt();
                break;
            } catch (NoSuchElementException ex) {
                System.out.println("Error in reading the number. Please, try again.");
            }
        } while (true);
    }

    private void savingTheFile(File file) throws IOException {
        System.out.println("Would you like to: \n" +
                "1) Save into selected file " + file.getAbsolutePath() + "\n" +
                "2) Create new file");
        Scanner inpReader = new Scanner(System.in);
        String userAnswer;

        boolean correctMode;
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
        for (List<Double> line : numbers) {
            for (Double value : line) {
                formattedNumbers += value + " ";
            }
            formattedNumbers += "\n";
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
        inputReader.useDelimiter("\n");
        ListIterator<List<Double>> it = numbers.listIterator();
        boolean ended = false;

        while (it.hasNext() && !ended) {
            System.out.println("Numbers line:");
            List<Double> currentLine = it.next();
            for (Double value : currentLine) {
                System.out.print(value + " ");
            }

            boolean correctMode;
            boolean correctNumber;
            String userAnswer;
            do {
                System.out.println("1) Accept \n" +
                        "2) Replace \n" +
                        "3) Delete \n" +
                        "4) Insert new numbers line \n" +
                        "5) Accept all remainder");
                userAnswer = inputReader.nextLine();

                correctMode = true;
                switch (userAnswer.trim()) {
                    case "1":
                        break;
                    case "2":
                        do {
                            System.out.println("Enter new line: ");
                            correctNumber = true;
                            try {
                                List<Double> values = new ArrayList<>();
                                String[] line = inputReader.nextLine().split("\\s");
                                for (int i = 0; i < arraysLenght; i++) {
                                    values.add(Double.parseDouble(line[i]));
                                }
                                it.set(values);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number line is incorrect. Correct example: '3.1415 -2 0 22.1'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                        } while (!correctNumber);
                        System.out.println("Line was successfully changed.");
                        break;
                    case "3":
                        it.remove();
                        System.out.println("Line was successfully deleted.");
                        break;
                    case "4":
                        do {
                            System.out.println("Enter new line: ");
                            correctNumber = true;
                            try {
                                List<Double> values = new ArrayList<>();
                                String[] line = inputReader.nextLine().split("\\s");
                                for (int i = 0; i < arraysLenght; i++) {
                                    values.add(Double.parseDouble(line[i]));
                                }
                                it.add(values);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number line is incorrect. Correct example: '3.1415 -2 0 22.1'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                            if (inputReader.nextLine().toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                        } while (!correctNumber);
                        System.out.println("Line was successfully added.");
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
        System.out.println("There are no more numbers lines here.");

        ended = false;
        while (!ended) {
            System.out.println("1) Add new line. 2) Save numbers");

            String temp;

            boolean correctMode;
            boolean correctNumber;
            do {
                temp = inputReader.nextLine().trim();
                correctMode = true;
                switch (temp) {
                    case "1":
                        do {
                            // TODO: It needs two enters. Why?
                            System.out.println("Enter new numbers line: ");
                            correctNumber = true;
                            try {
                                List<Double> values = new ArrayList<>();
                                String[] line = inputReader.nextLine().split("\\s");
                                for (int i = 0; i < arraysLenght; i++) {
                                    values.add(Double.parseDouble(line[i]));
                                }
                                it.add(values);
                            } catch (InputMismatchException ex) {
                                correctNumber = false;
                                System.out.println("The number line is incorrect. Correct example: '3.1415 -2 0 22.1'");
                                System.out.println("Try to add another or write 'exit'");
                            }
                            if (inputReader.nextLine().toLowerCase().equals("exit")) {
                                System.exit(0);
                            }
                        } while (!correctNumber);
                        System.out.println("Line was successfully added.");
                        break;
                    case "2":
                        ended = true;
                        records = createRecordsList();
                        break;
                    default:
                        correctMode = false;
                        System.out.println("Please, choose a correct mode number.");
                        break;
                }
            } while (!correctMode);
        }
    }

    private class Record {

        private List<Double> values = new ArrayList<>();

        public List<Double> getValues() {
            return values;
        }

        public void add(Double value) {
            getValues().add(value);
        }

        @Override
        public String toString() {
            StringBuilder line = new StringBuilder();
            for (Double d : values) {
                line.append(d);
                line.append("\t");
            }
            return line.toString();
        }
    }

}
