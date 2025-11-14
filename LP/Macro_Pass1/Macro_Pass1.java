import java.io.*;
import java.util.*;

class MDT {
    String value;

    MDT(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

public class Macro_Pass1 {
    public static void main(String[] args) {
        List<MDT> mdtTable = new ArrayList<>();
        Map<String, Integer> mntTable = new LinkedHashMap<>();
        Map<String, List<String>> alaTable = new LinkedHashMap<>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("input.txt"));
            String line;
            boolean insideMacro = false;
            boolean capturedHeader = false;

            String currentMacro = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.equalsIgnoreCase("MACRO")) {
                    insideMacro = true;
                    capturedHeader = false;
                    continue;
                }

                if (insideMacro && !capturedHeader) {
                    String[] tokens = line.split("\\s+", 2);
                    currentMacro = tokens[0];

                   List<String> ala = new ArrayList<>();
                    if (tokens.length > 1) {
                        String[] params = tokens[1].split(",");
                        for (String param : params) {
                            ala.add(param.trim().replaceAll("&", ""));
                        }
                    }

                    // Replace parameters in macro header with #n
                    String replacedLine = currentMacro;
                    for (int i = 0; i < ala.size(); i++) {
                        replacedLine += (i == 0 ? " " : ",") + "#" + i;
                    }

                    mntTable.put(currentMacro, mdtTable.size());
                    alaTable.put(currentMacro, ala);
                    mdtTable.add(new MDT(replacedLine));
                    capturedHeader = true;
                    continue;
                }

                if (line.equalsIgnoreCase("MEND")) {
                    mdtTable.add(new MDT("MEND"));
                    insideMacro = false;
                    currentMacro = null;
                    continue;
                }

                if (insideMacro && currentMacro != null) {
                    // Replace parameters in macro body using ALA
                    List<String> ala = alaTable.get(currentMacro);
                    for (int i = 0; i < ala.size(); i++) {
                        line = line.replaceAll("&" + ala.get(i), "#" + i);
                    }
                    mdtTable.add(new MDT(line));
                }
            }

            // Display MDT
            System.out.println("Macro Definition Table (MDT):");
            for (int i = 0; i < mdtTable.size(); i++) {
                System.out.println(i + "\t" + mdtTable.get(i));
            }

            // Display MNT
            System.out.println("\nMacro Name Table (MNT):");
            for (Map.Entry<String, Integer> entry : mntTable.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }

            // Display ALA Table
            System.out.println("\nArgument List Array (ALA):");
            for (Map.Entry<String, List<String>> entry : alaTable.entrySet()) {
                System.out.println("ALA for Macro: " + entry.getKey());
                List<String> ala = entry.getValue();
                for (int i = 0; i < ala.size(); i++) {
                    System.out.println("#" + i + " = " + ala.get(i));
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}