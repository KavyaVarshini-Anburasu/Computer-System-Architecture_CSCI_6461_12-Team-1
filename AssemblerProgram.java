 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 

 public class AssemblerProgram {
 
     
     public static final HashMap<String, Integer> opcodeMisMap = new HashMap<>();
     public static final HashMap<String, Integer> opcodeLsMap = new HashMap<>();
     public static final HashMap<String, Integer> opcodeCalMap = new HashMap<>();
     public static final HashMap<String, Integer> opcodeSrMap = new HashMap<>();
     public static final HashMap<String, Integer> opcodeIoMap = new HashMap<>();
     public static final HashMap<String, Integer> opcodeFMap = new HashMap<>();
     public static final String locationString = "LOC";
     public static final String dataString = "Data";
     public static final String haltString = "HLT";
     public static final String trapString = "TRAP";
     public static final String loadRegString = "LDR";
     public static final String storeRegString = "STR";
     public static final String loadAddrString = "LDA";
     public static final String loadIdxString = "LDX";
     public static final String storeIdxString = "STX";
     public static final String setCceString = "SETCCE";
     public static final String jumpZeroString = "JZ";
     public static final String jumpNotEqualString = "JNE";
     public static final String jumpCcString = "JCC";
     public static final String jumpMemAddrString = "JMA";
     public static final String jumpSubroutineString = "JSR";
     public static final String returnFromSubroutineString = "RFS";
     public static final String subtractOneBranchString = "SOB";
     public static final String jumpGreaterEqualString = "JGE";
     public static final String addMemRegString = "AMR";
     public static final String subMemRegString = "SMR";
     public static final String addImmRegString = "AIR";
     public static final String subImmRegString = "SIR";
     public static final String multiplyString = "MLT";
     public static final String divideString = "DVD";
     public static final String testRegRegString = "TRR";
     public static final String andString = "AND";
     public static final String orString = "ORR";
     public static final String notString = "NOT";
     public static final String shiftRotateCtrlString = "SRC";
     public static final String rotateRightCtrlString = "RRC";
     public static final String inputString = "IN";
     public static final String outputString = "OUT";
     public static final String checkString = "CHK";
     public static final String floatAddString = "FADD";
     public static final String floatSubString = "FSUB";
     public static final String vectorAddString = "VADD";
     public static final String vectorSubString = "VSUB";
     public static final String convertString = "CNVRT";
     public static final String loadFloatRegString = "LDFR";
     public static final String storeFloatRegString = "STFR";
 
     
     static {
         opcodeMisMap.put(haltString, 000);
         opcodeMisMap.put(trapString, 045);
         opcodeLsMap.put(loadRegString, 001);
         opcodeLsMap.put(storeRegString, 002);
         opcodeLsMap.put(loadAddrString, 003);
         opcodeLsMap.put(loadIdxString, 004);
         opcodeLsMap.put(storeIdxString, 005);
         opcodeLsMap.put(setCceString, 044);
         opcodeLsMap.put(jumpZeroString, 006);
         opcodeLsMap.put(jumpNotEqualString, 007);
         opcodeLsMap.put(jumpCcString, 010);
         opcodeLsMap.put(jumpMemAddrString, 011);
         opcodeLsMap.put(jumpSubroutineString, 012);
         opcodeLsMap.put(returnFromSubroutineString, 013);
         opcodeLsMap.put(subtractOneBranchString, 014);
         opcodeLsMap.put(jumpGreaterEqualString, 015);
         opcodeLsMap.put(addMemRegString, 016);
         opcodeLsMap.put(subMemRegString, 017);
         opcodeLsMap.put(addImmRegString, 020);
         opcodeLsMap.put(subImmRegString, 021);
         opcodeCalMap.put(multiplyString, 022);
         opcodeCalMap.put(divideString, 023);
         opcodeCalMap.put(testRegRegString, 024);
         opcodeCalMap.put(andString, 025);
         opcodeCalMap.put(orString, 026);
         opcodeCalMap.put(notString, 027);
         opcodeSrMap.put(shiftRotateCtrlString, 030);
         opcodeSrMap.put(rotateRightCtrlString, 031);
         opcodeIoMap.put(inputString, 032);
         opcodeIoMap.put(outputString, 033);
         opcodeIoMap.put(checkString, 034);
         opcodeFMap.put(floatAddString, 035);
         opcodeFMap.put(floatSubString, 036);
         opcodeFMap.put(vectorAddString, 037);
         opcodeFMap.put(vectorSubString, 040);
         opcodeFMap.put(convertString, 041);
         opcodeFMap.put(loadFloatRegString, 042);
         opcodeFMap.put(storeFloatRegString, 043);
     }
 
     private int locationCounter = 0;
     private final HashMap<String, Integer> symbols = new HashMap<>();
 
     /**
      * Parses symbols and updates the location counter.
      * @param instructions List of assembly language instructions.
      */

     // Code for parsing symbols and updating location counter

     void parseSymbols(ArrayList<String> instructionList) {
         int symbolIndex;
         for (String instruction : instructionList) {
             if (instruction.startsWith(locationString)) {
                 locationCounter = Integer.parseInt(instruction.substring(locationString.length()).trim());
                 continue;
             }
             symbolIndex = instruction.indexOf(':');
             if (symbolIndex != -1) {
                 symbols.put(instruction.substring(0, symbolIndex), locationCounter);
                 instruction = instruction.substring(symbolIndex + 1).trim();
             }
             if (!instruction.isEmpty()) {
                 locationCounter++;
             }
         }
     }
 
     // Parse instructions to machine code
     public ArrayList<String> parseInstructions(ArrayList<String> instructionList) {
         parseSymbols(instructionList);
         locationCounter = 0;
 
         ArrayList<String> machineCodeList = new ArrayList<>();
         int symbolIndex;
         for (String instruction : instructionList) {
             // This is `LOC` instruction   
             if (instruction.startsWith(locationString)) {
                 locationCounter = Integer.parseInt(instruction.substring(locationString.length()).trim());
                 machineCodeList.add(String.format("%06o\t%06o", locationCounter, 0)); // Initial memory location with zero value
                 continue;
             }
 
             symbolIndex = instruction.indexOf(':'); // Prefix symbol deletion and pass the empty instruction
             if (symbolIndex != -1) {
                 instruction = instruction.substring(symbolIndex + 1).trim();
             }
 
             if (instruction.isEmpty()) {
                 continue;
             }
             
/**
 * Analyzes the first word of an assembly language instruction to identify its type and calls the appropriate parsing method to produce machine code.
 * @param instr The assembly language instruction to be parsed.
 * @return A list of machine code representations corresponding to the specified instruction.
 */
             String[] instructionComponents = instruction.split("\\s+", 2);
             switch (instructionComponents[0]) {
                 case dataString:
                     machineCodeList.add(parseDataInstruction(instructionComponents));
                     break;
                 case haltString:
                 case trapString:
                     machineCodeList.add(parseMisInstruction(instructionComponents));
                     break;
                 case loadRegString:
                 case storeRegString:
                 case loadAddrString:
                 case loadIdxString:
                 case storeIdxString:
                 case setCceString:
                 case jumpZeroString:
                 case jumpNotEqualString:
                 case jumpCcString:
                 case jumpMemAddrString:
                 case jumpSubroutineString:
                 case returnFromSubroutineString:
                 case subtractOneBranchString:
                 case jumpGreaterEqualString:
                 case addMemRegString:
                 case subMemRegString:
                 case addImmRegString:
                 case subImmRegString:
                     machineCodeList.add(parseLsInstruction(instructionComponents));
                     break;
                 case multiplyString:
                 case divideString:
                 case testRegRegString:
                 case andString:
                 case orString:
                 case notString:
                     machineCodeList.add(parseCalInstruction(instructionComponents));
                     break;
                 case shiftRotateCtrlString:
                 case rotateRightCtrlString:
                     machineCodeList.add(parseSrInstruction(instructionComponents));
                     break;
                 case inputString:
                 case outputString:
                 case checkString:
                     machineCodeList.add(parseIoInstruction(instructionComponents));
                     break;
                 case floatAddString:
                 case floatSubString:
                 case vectorAddString:
                 case vectorSubString:
                 case convertString:
                 case loadFloatRegString:
                 case storeFloatRegString:
                     machineCodeList.add(parseFInstruction(instructionComponents));
                     break;
                 default:
                     machineCodeList.add("ERROR: Unknown instruction!");
                     break;
             }
             locationCounter++;
         }
         return machineCodeList;
     }

/**
 * Analyzes a data instruction and produces its machine code.
 * @param words The individual components of the data instruction.
 * @return The machine code representation of the data instruction.
 */

     String parseDataInstruction(String[] instructionComponents) {
         int dataValue;
         try {      // Will be integer
             dataValue = Integer.parseInt(instructionComponents[1]);
         } catch (NumberFormatException e) {
             dataValue = symbols.get(instructionComponents[1]);
         }
         return String.format("%06o\t%06o", locationCounter, dataValue);
     }
 
/**
 * Analyzes Load/Store and branch instructions to generate their machine code.
 * @param words An array containing the components of the instruction.
 * @return The machine code representation of the instruction.
 */
  
    String parseLsInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeLsMap.get(instructionComponents[0]);
    
        // Split and trim the operand list (assuming operands are comma-separated)
        String[] operands = instructionComponents[1].split(",");
        Arrays.setAll(operands, i -> operands[i].trim());
    
        int r, x, addr, i;
    
        // Switch statement to handle different types of instructions
        switch (instructionComponents[0]) {
            case "LDR":
            case "STR":
            case "LDA":
            case "JCC":
            case "SOB":
            case "JGE":
            case "AMR":
            case "SMR":
                r = Integer.parseInt(operands[0]);
                x = Integer.parseInt(operands[1]);
                addr = Integer.parseInt(operands[2]);
                i = (operands.length > 3) ? Integer.parseInt(operands[3]) : 0;  // Optional operand
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (r << 8) | (x << 6) | (i << 5) | addr);
    
            case "LDX":
            case "STX":
            case "JZ":
            case "JNE":
            case "JMA":
            case "JSR":
                x = Integer.parseInt(operands[0]);
                addr = Integer.parseInt(operands[1]);
                i = (operands.length > 2) ? Integer.parseInt(operands[2]) : 0;  // Optional operand
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (x << 6) | (i << 5) | addr);
    
            case "SETCCE":
                r = Integer.parseInt(operands[0]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (r << 8));
    
            case "RFS":
                addr = Integer.parseInt(operands[0]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | addr);
    
            case "AIR":
            case "SIR":
                r = Integer.parseInt(operands[0]);
                addr = Integer.parseInt(operands[1]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (r << 8) | addr);
    
            default:
                return "ERROR: Unknown or invalid instruction!";
        }
    }
/**
 * Analyzes I/O instructions to produce their machine code.
 * @param words An array containing the components of the instruction.
 * @return The machine code representation of the instruction.
 */

     String parseIoInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeIoMap.get(instructionComponents[0]);
    
        // Split and trim the operand list (assuming operands are comma-separated)
        String[] operands = instructionComponents[1].split(",");
        Arrays.setAll(operands, i -> operands[i].trim());
    
        int r, devId;
    
        // Switch statement to handle different types of instructions
        switch (instructionComponents[0]) {
            case "IN":
            case "OUT":
            case "CHK":
                r = Integer.parseInt(operands[0]);
                devId = Integer.parseInt(operands[1]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (r << 8) | devId);
    
            default:
                return "ERROR: Unknown or invalid instruction!";
        }
    }

/**
 * Analyzes arithmetic and logical instructions to generate their machine code.
 * @param words An array containing the components of the instruction.
 * @return The machine code representation of the instruction.
 */

    String parseCalInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeCalMap.get(instructionComponents[0]);
    
        // Split and trim the operand list (assuming operands are comma-separated)
        String[] operands = instructionComponents[1].split(",");
        Arrays.setAll(operands, i -> operands[i].trim());
    
        int rx, ry;
    
        // Switch statement to handle different types of instructions
        switch (instructionComponents[0]) {
            case "MLT":
            case "DVD":
            case "TRR":
            case "AND":
            case "ORR":
                rx = Integer.parseInt(operands[0]);
                ry = Integer.parseInt(operands[1]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (rx << 8) | (ry << 6));
    
            case "NOT":
                rx = Integer.parseInt(operands[0]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (rx << 8));
    
            default:
                return "ERROR: Unknown or invalid instruction!";
        }
    }
    String parseMisInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeMisMap.get(instructionComponents[0]);
    
        // Switch statement to handle specific instructions like HLT and TRAP
        switch (instructionComponents[0]) {
            case "HLT":    // Halt instruction has no operand, return machine code with 0
                return String.format("%06o\t%06o", locationCounter, 0);
            case "TRAP":   // Trap instruction has an additional operand, combine opcode and operand
                // Ensure there's a second component for the operand
                if (instructionComponents.length > 1) {
                    int operand = Integer.parseInt(instructionComponents[1]);
                    return String.format("%06o\t%06o", locationCounter, (opcode << 10) | operand);
                } else {
                    return "ERROR: Missing operand for TRAP instruction!";
                }
            default:       // Handle unknown instructions
                // Check if the opcode exists, if not return an error
                if (opcode == 0) {
                    return "ERROR: Unknown instruction!";
                }
                return String.format("%06o\t%06o", locationCounter, opcode);
        }
    }
    
/**
 * Analyzes floating-point instructions to produce their machine code.
 * @param words An array containing the components of the instruction.
 * @return The machine code representation of the instruction.
 */

     String parseFInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeFMap.get(instructionComponents[0]);
    
        // Split and trim the operand list (assuming operands are comma-separated)
        String[] operands = instructionComponents[1].split(",");
        Arrays.setAll(operands, i -> operands[i].trim());
    
        int fr, i, x, addr;
    
        // Switch statement to handle different types of instructions
        switch (instructionComponents[0]) {
            case "FADD":
            case "FSUB":
            case "VADD":
            case "VSUB":
            case "CNVRT":
            case "LDFR":
            case "STFR":
                fr = Integer.parseInt(operands[0]);
                x = Integer.parseInt(operands[1]);
                addr = Integer.parseInt(operands[2]);
                i = (operands.length > 3) ? Integer.parseInt(operands[3]) : 0;  // Optional operand
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (fr << 8) | (i << 7) | (x << 5) | addr);
    
            default:
                return "ERROR: Unknown or invalid instruction!";
        }
    }
            
/**
 * Analyzes shift and rotate instructions to generate their machine code.
 * @param words An array containing the components of the instruction.
 * @return The machine code representation of the instruction.
 */

    String parseSrInstruction(String[] instructionComponents) {
        // Get the opcode from the map
        int opcode = opcodeSrMap.get(instructionComponents[0]);
    
        // Split and trim the operand list (assuming operands are comma-separated)
        String[] operands = instructionComponents[1].split(",");
        Arrays.setAll(operands, i -> operands[i].trim());
    
        int r, count, lr, al;
    
        // Switch statement to handle different types of instructions
        switch (instructionComponents[0]) {
            case "SRC":
            case "RRC":
                r = Integer.parseInt(operands[0]);
                count = Integer.parseInt(operands[1]);
                lr = Integer.parseInt(operands[2]);
                al = Integer.parseInt(operands[3]);
                return String.format("%06o\t%06o", locationCounter, (opcode << 10) | (r << 8) | (al << 7) | (lr << 6) | count);
    
            default:
                return "ERROR: Unknown or invalid instruction!";
        }
    }
 }
 