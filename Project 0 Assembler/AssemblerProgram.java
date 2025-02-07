import java.io.*;
import java.util.*;

public class AssemblerProgram {
    private static final HashMap<String, Integer> opcodeMap = new HashMap<>();
    private static final HashMap<String, Integer> symbols = new HashMap<>();
    private static int locationCounter = 0;

    static {
        opcodeMap.put("HLT", 0);
        opcodeMap.put("LDR", 1);
        opcodeMap.put("STR", 2);
        opcodeMap.put("LDA", 3);
        opcodeMap.put("LDX", 4);
        opcodeMap.put("STX", 5);
        opcodeMap.put("JZ", 6);
        opcodeMap.put("JNE", 7);
        opcodeMap.put("JCC", 8);
        opcodeMap.put("JMA", 9);
        opcodeMap.put("JSR", 10);
        opcodeMap.put("RFS", 11);
        opcodeMap.put("SOB", 12);
        opcodeMap.put("JGE", 13);
        opcodeMap.put("IN", 14);
        opcodeMap.put("OUT", 15);
    }

    public static List<String> parseInstructions(List<String> instructionList) {
        List<String> machineCodeList = new ArrayList<>();
        parseSymbols(instructionList);
        locationCounter = 0;

        for (String instruction : instructionList) {
            instruction = instruction.split(";")[0].trim(); // Remove comments
            if (instruction.isEmpty()) continue;

            String[] parts = instruction.split("\\s+");
            if (opcodeMap.containsKey(parts[0])) {
                int opcode = opcodeMap.get(parts[0]) << 10;
                int operand = parts.length > 1 ? parseOperand(parts[1]) : 0;
                machineCodeList.add(String.format("%06o\t%06o", locationCounter++, opcode | operand));
            } else if (instruction.startsWith("LOC")) {
                locationCounter = Integer.parseInt(parts[1]);
            } else if (instruction.contains("Data")) {
                int dataValue = parseOperand(parts[1]);
                machineCodeList.add(String.format("%06o\t%06o", locationCounter++, dataValue));
            }
        }
        return machineCodeList;
    }

    private static void parseSymbols(List<String> instructionList) {
        locationCounter = 0;
        for (String instruction : instructionList) {
            if (instruction.contains(":")) {
                String label = instruction.split(":")[0].trim();
                symbols.put(label, locationCounter);
            }
            locationCounter++;
        }
    }

    private static int parseOperand(String operand) {
        if (symbols.containsKey(operand)) {
            return symbols.get(operand);
        }
        try {
            return Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java AssemblerProgram <input file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = "Output.txt";
        String listingFile = "Listing File.txt";

        List<String> inputLines = FileController.readLinesFromFile(inputFile);
        List<String> outputLines = parseInstructions(inputLines);

        FileController.writeLinesToFile(outputFile, outputLines);
        FileController.writeLinesToFile(listingFile, generateListingFile(inputLines, outputLines));

        System.out.println("Assembling complete! Files generated: Output.txt, Listing File.txt");
    }

    private static List<String> generateListingFile(List<String> sourceLines, List<String> outputLines) {
        List<String> listing = new ArrayList<>();
        for (int i = 0; i < Math.max(sourceLines.size(), outputLines.size()); i++) {
            String source = (i < sourceLines.size()) ? sourceLines.get(i) : "";
            String output = (i < outputLines.size()) ? outputLines.get(i) : "";
            listing.add(String.format("%-40s %s", output, source));
        }
        return listing;
    }
}

class FileController {
    public static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLinesToFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
