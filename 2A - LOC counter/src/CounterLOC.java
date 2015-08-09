import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 */
public class CounterLOC {

    final int MIN_COMMENT_LEN = 10;

    public void start() throws IOException {
        System.out.println("Hello! Enter the file name: ");
        Scanner reader = new Scanner(System.in);
        String fileName = reader.nextLine();
        File file = new File(fileName);
        int loc = countLinesOfCode(file);
        System.out.println("File \"" + fileName + "\"consists of " + loc + " lines of code.");
    }

    public int countLinesOfCode(File file) throws IOException {

        double numberOfLines = 0;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            Scanner scanner = new Scanner(reader);
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                line = line.trim();

                // There are no statements that takes less than 3 symbols, even: i++, a=b
                if(line.length() < 3) {
                    continue;
                }

                // Usual try, without resources. If resources will be counted in the next line,
                // "else" clause will work correctly
                else if (line.contains("try") && !line.contains("(")) {
                    continue;
                }

                // Catching one-line comments and relatively long parts of the multi-line comments
                // Size limitations for expelling some of generated comment lines
                else if (line.startsWith("//") ||
                        line.startsWith("*") && line.length() > MIN_COMMENT_LEN) {
                    numberOfLines += 0.5;
                }
                else {
                    numberOfLines++;
                }
            }
        }
        return (int) numberOfLines;
    }

    public static void main(String[] args) {

        CounterLOC loc = new CounterLOC();
        try {
            loc.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
