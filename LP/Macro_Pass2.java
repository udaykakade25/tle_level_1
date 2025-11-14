import java.io.*;
import java.util.*;

class Macro_Pass2 {
    public static void main(String[] args) {
        // ----- Macro Definition Table (MDT) -----
        List<String> MDT = new ArrayList<>();
        MDT.add("ADD #0,#1");
        MDT.add("L #0");
        MDT.add("ST #1");
        MDT.add("MEND");
        MDT.add("FIND #0");
        MDT.add("N #0");
        MDT.add("MEND");

        // ----- Macro Name Table (MNT) -----
        Map<String, Integer> MNT = new LinkedHashMap<>();
        MNT.put("ADD", 0);
        MNT.put("FIND", 4);

        // ----- Argument List Array (ALA) -----
        Map<String, List<String>> ALA = new LinkedHashMap<>();

        // ALA for ADD
        ALA.put("ADD", Arrays.asList("a1", "a2"));

        // ALA for FIND
        ALA.put("FIND", Arrays.asList("a4"));

        // ----- Input code (main program after macros defined) -----
        List<String> sourceCode = Arrays.asList(
            "START 200",
            "ADD a1,a2",
            "FIND a4",
            "END"
        );

        System.out.println("---- Macro Pass-2 Output ----");
        for (String line : sourceCode) {
            String[] tokens = line.split("\\s+|,");
            String macroName = tokens[0];

            // Check if it’s a macro call
            if (MNT.containsKey(macroName)) {
                int mdtIndex = MNT.get(macroName);
                List<String> actualArgs = new ArrayList<>();

                // Collect actual arguments from line
                for (int i = 1; i < tokens.length; i++) {
                    actualArgs.add(tokens[i]);
                }

                // Expand macro
                for (int i = mdtIndex; i < MDT.size(); i++) {
                    String mLine = MDT.get(i);
                    if (mLine.equalsIgnoreCase("MEND"))
                        break;

                    // Replace #n with actual argument
                    String expandedLine = mLine;
                    for (int j = 0; j < actualArgs.size(); j++) {
                        expandedLine = expandedLine.replace("#" + j, actualArgs.get(j));
                    }

                    // Print the expanded instruction
                    System.out.println(expandedLine);
                }
            } else {
                // Normal instruction (not a macro call)
                System.out.println(line);
            }
        }
    }
}
