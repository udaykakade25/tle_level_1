import java.io.*;
import java.util.*;

public class ASS_Pass1 {
    static int LC = 0;
    static LinkedHashMap<String, Integer> SYMTAB = new LinkedHashMap<>();
    static ArrayList<String> LITTAB = new ArrayList<>();
    static ArrayList<Integer> LITADDR = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.asm"));
        BufferedWriter lcBW = new BufferedWriter(new FileWriter("LC.txt"));
        BufferedWriter symBW = new BufferedWriter(new FileWriter("SYMTAB.txt"));
        BufferedWriter litBW = new BufferedWriter(new FileWriter("LITTAB.txt"));
        BufferedWriter icBW = new BufferedWriter(new FileWriter("IC.txt"));

        String line;
        boolean startFound = false;

        while ((line = br.readLine()) != null) {
            String tokens[] = line.trim().split("\\s+");

            // START directive
            if (tokens[0].equalsIgnoreCase("START")) {
                LC = Integer.parseInt(tokens[1]);
                startFound = true;
                lcBW.write("LC initialized to: " + LC + "\n");
                icBW.write("(AD,01) (C," + LC + ")\n"); // START
                continue;
            }

            if (!startFound) continue;
            if (tokens[0].equalsIgnoreCase("END")) {
                icBW.write("(AD,02)\n");
                break;
            }

            // --------------------
            // Symbol Table handling
            // --------------------
            if (!tokens[0].equalsIgnoreCase("MOVER") &&
                !tokens[0].equalsIgnoreCase("MOVEM") &&
                !tokens[0].equalsIgnoreCase("ADD") &&
                !tokens[0].equalsIgnoreCase("SUB") &&
                !tokens[0].equalsIgnoreCase("MULT") &&
                !tokens[0].equalsIgnoreCase("COMP") &&
                !tokens[0].equalsIgnoreCase("BC") &&
                !tokens[0].equalsIgnoreCase("STOP") &&
                !tokens[0].equalsIgnoreCase("ORIGIN") &&
                !tokens[0].equalsIgnoreCase("END")) {
                if (!SYMTAB.containsKey(tokens[0])) {
                    SYMTAB.put(tokens[0], LC);
                }
            }

            // --------------------
            // Literal Table handling
            // --------------------
            for (String tok : tokens) {
                if (tok.startsWith("='") && !LITTAB.contains(tok)) {
                    LITTAB.add(tok);
                    LITADDR.add(LC);
                }
            }

            // --------------------
            // Write LC
            // --------------------
            lcBW.write(String.format("%-25s -> LC=%d\n", line, LC));

            // --------------------
            // Generate Intermediate Code with addresses
            // --------------------
            StringBuilder icLine = new StringBuilder();
            icLine.append(LC).append(" : ");

            // Simple opcode mapping
            Map<String, String> MOT = new HashMap<>();
            MOT.put("STOP", "(IS,00)");
            MOT.put("ADD", "(IS,01)");
            MOT.put("SUB", "(IS,02)");
            MOT.put("MULT", "(IS,03)");
            MOT.put("MOVER", "(IS,04)");
            MOT.put("MOVEM", "(IS,05)");
            MOT.put("COMP", "(IS,06)");
            MOT.put("BC", "(IS,07)");

            Map<String, String> REG = new HashMap<>();
            REG.put("AREG", "(R,1)");
            REG.put("BREG", "(R,2)");
            REG.put("CREG", "(R,3)");
            REG.put("DREG", "(R,4)");

            Map<String, String> COND = new HashMap<>();
            COND.put("LT", "(C,1)");
            COND.put("LE", "(C,2)");
            COND.put("EQ", "(C,3)");
            COND.put("GT", "(C,4)");
            COND.put("GE", "(C,5)");
            COND.put("ANY", "(C,6)");

            int startToken = 0;
            // If first token is a label, skip it
            if (!MOT.containsKey(tokens[0].toUpperCase()) &&
                !tokens[0].equalsIgnoreCase("ORIGIN") &&
                !tokens[0].equalsIgnoreCase("DC") &&
                !tokens[0].equalsIgnoreCase("DS")) {
                startToken = 1;
            }

            for (int i = startToken; i < tokens.length; i++) {
                String tok = tokens[i];
                tok = tok.replaceAll(",", ""); // remove comma
                if (MOT.containsKey(tok.toUpperCase())) {
                    icLine.append(MOT.get(tok.toUpperCase())).append(" ");
                } else if (REG.containsKey(tok.toUpperCase())) {
                    icLine.append(REG.get(tok.toUpperCase())).append(" ");
                } else if (COND.containsKey(tok.toUpperCase())) {
                    icLine.append(COND.get(tok.toUpperCase())).append(" ");
                } else if (tok.startsWith("='")) {
                    int index = LITTAB.indexOf(tok);
                    icLine.append("(L,").append(index + 1).append(") ");
                } else if (SYMTAB.containsKey(tok)) {
                    icLine.append("(S,").append(tok).append(") ");
                } else if (tok.matches("\\d+")) {
                    icLine.append("(C,").append(tok).append(") ");
                }
            }

            icBW.write(icLine.toString().trim() + "\n");

            // --------------------
            // Update LC
            // --------------------
            if (tokens.length > 1 && tokens[1].equalsIgnoreCase("DS")) {
                LC += Integer.parseInt(tokens[2]);
            } else if (tokens.length > 1 && tokens[1].equalsIgnoreCase("DC")) {
                LC++;
            } else if (tokens[0].equalsIgnoreCase("ORIGIN")) {
                if (tokens.length > 1) {
                    if (tokens[1].contains("+")) {
                        String[] parts = tokens[1].split("\\+");
                        int base = SYMTAB.getOrDefault(parts[0], 0);
                        LC = base + Integer.parseInt(parts[1]);
                    } else if (tokens[1].contains("-")) {
                        String[] parts = tokens[1].split("\\-");
                        int base = SYMTAB.getOrDefault(parts[0], 0);
                        LC = base - Integer.parseInt(parts[1]);
                    } else {
                        LC = SYMTAB.getOrDefault(tokens[1], 0);
                    }
                }
            } else {
                LC++;
            }
        }

        // --------------------
        // Write Symbol Table
        // --------------------
        symBW.write("SYMBOL\tADDRESS\n");
        for (Map.Entry<String,Integer> e : SYMTAB.entrySet()) {
            symBW.write(e.getKey() + "\t" + e.getValue() + "\n");
        }

        // --------------------
        // Write Literal Table
        // --------------------
        litBW.write("INDEX\tLITERAL\tADDRESS\n");
        for (int i = 0; i < LITTAB.size(); i++) {
            litBW.write((i+1) + "\t" + LITTAB.get(i) + "\t" + LITADDR.get(i) + "\n");
        }

        // --------------------
        // Close all streams
        // --------------------
        br.close();
        lcBW.close();
        symBW.close();
        litBW.close();
        icBW.close();

        System.out.println("Pass-1 completed. Files generated: LC.txt, SYMTAB.txt, LITTAB.txt, IC.txt");
    }
}
