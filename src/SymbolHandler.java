import java.util.ArrayList;
import java.util.HashMap;

public class SymbolHandler {

    private ArrayList<String> symbolicInstructions;
    private ArrayList<String> numericInstructions;
    private HashMap<String,Integer> variables;
    public final String labelMatch = "\\(.+\\)";

    int firstFreeRegister;

    public SymbolHandler(ArrayList<String> instructions) {
        this.symbolicInstructions = instructions;
        this.numericInstructions = new ArrayList<>();
        this.variables = new HashMap<>();
        this.firstFreeRegister = 16;

        for (int i=0; i<16; i++) { //put R0-R15
            variables.put("R"+i,i);
        }
        variables.put("SCREEN",16384);
        variables.put("KBD",24576);
        variables.put("SP",0);
        variables.put("LCL",1);
        variables.put("ARG",2);
        variables.put("THIS",3);
        variables.put("THAT",4);

    }

    public void handleSymbols() {
        identifyLabels();
        populateNumeric();
    }


    private void identifyLabels() {
        int i = 1; // start at line 0, but we want to assign to the next line at each line
        for (String s : symbolicInstructions) {

            if (s.matches(labelMatch)) { // match any char in parentheses
                variables.put(s.substring(1,s.length()-1),i);
            } else {
                i++; // if we match, we don't increment. label variables aren't lines of code
            }

        }
        // TODO: add to hashmap proper variables
        // loop through instructions
        // isolate variable names from instructions
        // isolate label symbols
        // add to hashmap
    }

    private void populateNumeric() {
        for (String s : symbolicInstructions) {
            if (s.matches("^@[A-Za-z].*$")) { // check if non-numeric
                String vbl = s.substring(1); // isolate variable name
                if (!variables.containsKey(vbl)) { // if not found in table, add it
                    variables.put(vbl,firstFreeRegister);
                    firstFreeRegister++; // and update next register
                }
                numericInstructions.add("@"+variables.get(vbl)); // either way, add to numeric
            } else if (!s.matches(labelMatch)) {
                numericInstructions.add(s);
            }
        }
    }

    public ArrayList<String> getNumericInstructions() {
        return numericInstructions;
    }
}
