import java.io.*;
import java.util.*;

public class Main {
    static final int MEMORY_SIZE = 5; // You can change this to your available RAM in records

    public static void main(String[] args) throws IOException {
        // Step 1: Create initial sorted runs (tape simulation)
        createInitialRuns("input.txt", "input1.txt", "input2.txt", MEMORY_SIZE);

        // Step 2: Merge the sorted runs using 2 tapes
        mergeRuns("input1.txt", "input2.txt", "output1.txt", "output2.txt");

        System.out.println("Sorting completed. Check 'output1.txt' or 'output2.txt' for final result.");
    }

    // Step 1: Create sorted runs of size m and write to two output tapes
    static void createInitialRuns(String input, String tape1, String tape2, int m) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(input));
        BufferedWriter[] outputs = {
            new BufferedWriter(new FileWriter(tape1)),
            new BufferedWriter(new FileWriter(tape2))
        };

        String line;
        int turn = 0;
        List<Integer> buffer = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            buffer.add(Integer.parseInt(line));
            if (buffer.size() == m) {
                Collections.sort(buffer);
                for (int num : buffer) {
                    outputs[turn].write(num + "\n");
                }
                buffer.clear();
                turn = 1 - turn;
            }
        }

        if (!buffer.isEmpty()) {
            Collections.sort(buffer);
            for (int num : buffer) {
                outputs[turn].write(num + "\n");
            }
        }

        br.close();
        outputs[0].close();
        outputs[1].close();
    }

    // Step 2: Merge two sorted input tapes into two output tapes
    static void mergeRuns(String in1, String in2, String out1, String out2) throws IOException {
        BufferedReader[] inputs = {
            new BufferedReader(new FileReader(in1)),
            new BufferedReader(new FileReader(in2))
        };
        BufferedWriter[] outputs = {
            new BufferedWriter(new FileWriter(out1)),
            new BufferedWriter(new FileWriter(out2))
        };

        int turn = 0;
        Integer a = readInt(inputs[0]);
        Integer b = readInt(inputs[1]);

        while (a != null || b != null) {
            BufferedWriter out = outputs[turn];

            while (a != null && (b == null || a <= b)) {
                out.write(a + "\n");
                a = readInt(inputs[0]);
            }

            while (b != null && (a == null || b < a)) {
                out.write(b + "\n");
                b = readInt(inputs[1]);
            }

            turn = 1 - turn;
        }

        inputs[0].close();
        inputs[1].close();
        outputs[0].close();
        outputs[1].close();
    }

    static Integer readInt(BufferedReader br) throws IOException {
        String line = br.readLine();
        return line != null ? Integer.parseInt(line) : null;
    }
}
