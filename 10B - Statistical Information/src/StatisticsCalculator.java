import java.io.*;
import java.util.List;

/**
 * @author Artur Badretdinov
 *         Innopolis University
 *         Summer School 2015
 *         17.08.2015
 */
public class StatisticsCalculator {

    private List<Double> records = null;

    public double min() {
        if (records == null) {
            throw new IllegalStateException();
        }
        double min = Double.MAX_VALUE;
        for (double d : records) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    public double max() {
        if (records == null) {
            throw new IllegalStateException();
        }
        double max = Double.MIN_VALUE;
        for (double d : records) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public double avg() {
        if (records == null) {
            throw new IllegalStateException();
        }
        double sum = 0;
        for (double d : records) {
            sum += d;
        }
        return sum / records.size();
    }

    public double standardDeviation() {
        if (records == null) {
            throw new IllegalStateException();
        }
        double avg = avg();
        double variation = 0;
        for (double d : records) {
            variation += (d - avg) * (d - avg);
        }
        variation /= records.size();
        double deviation = Math.sqrt(variation);
        return deviation;
    }

    /**
     * @return doubles array: min, max, avg
     */
    public double[] calculateAll() {
        return new double[] {min(), max(), avg(), standardDeviation()};
    }

    // TODO: Where "save" needs to be?
    public void save(File file) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {
            double[] stats = calculateAll();
            writer.write("Min: " + stats[0] + ", max: " + stats[1] + ", avg: " + stats[2] + ", deviation: " + stats[3]);
            System.out.printf("Numbers were successfully written into: \n%s \n", file.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void print() {
        double[] stats = calculateAll();
        System.out.println("Min: " + stats[0] + ", max: " + stats[1] + ", avg: " + stats[2] + ", deviation: " + stats[3]);
    }

    public void setRecords(List<Double> records) {
        this.records = records;
    }

    public List<Double> getRecords() {
        return records;
    }

}
