import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CInstructionHandler {
    String instruction;
    ArrayList<String> jumpCodes = new ArrayList<>(Arrays.asList(null,"JGT","JEQ","JGE","JLT","JNE","JLE","JMP"));

    HashMap<String,String> operationMap;

    public CInstructionHandler(String instruction) {
        this.instruction = instruction;
        this.operationMap = new HashMap<>();

        operationMap.put("0","101010");
        operationMap.put("1","111111");
        operationMap.put("-1","111010");
        operationMap.put("D","001100");
        operationMap.put("A","110000");
        operationMap.put("!D","001101");
        operationMap.put("!A","110001");
        operationMap.put("-D","001111");
        operationMap.put("-A","110011");
        operationMap.put("D+1","011111");
        operationMap.put("A+1","110111");
        operationMap.put("D-1","001110");
        operationMap.put("A-1","110010");
        operationMap.put("D+A","000010");
        operationMap.put("D-A","010011");
        operationMap.put("A-D","000111");
        operationMap.put("D&A","000000");
        operationMap.put("D|A","010101");
    }

    public String handle() {
        String operation = instruction;

        String destBits = "000";
        if (instruction.contains("=")) {
            int equalIndex = instruction.indexOf("=");
            destBits = getDestBits(instruction.substring(0, equalIndex));
            operation = operation.substring(equalIndex+1);
        }

        String jumpBits = "000";
        if (operation.contains(";")) {
            int colonIndex = operation.indexOf(";");
            String jumpCode = operation.substring(colonIndex+1);
            jumpBits = Integer.toBinaryString(jumpCodes.indexOf(jumpCode));
            while (jumpBits.length()<3) {
                jumpBits = "0" + jumpBits;
            }
            operation = operation.substring(0,colonIndex).trim();
        }

        String compBits = getCompBits(operation);
        return "111" + compBits + destBits + jumpBits;
    }

    private String getCompBits(String operation) {
        String aBit = "0";
        if (operation.contains("M")) {
            aBit = "1";
            operation = operation.replace('M','A');
        }
        String comp = operationMap.get(operation);

        return aBit + comp;
    }

    private String getDestBits(String substring) {
        char[] destBits = {'0','0','0'};
        if (substring.contains("A"))
            destBits[0]='1';
        if (substring.contains("D"))
            destBits[1]='1';
        if (substring.contains("M"))
            destBits[2]='1';

        return new String(destBits);
    }


}
