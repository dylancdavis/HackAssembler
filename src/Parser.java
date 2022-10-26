import java.io.*;
import java.util.ArrayList;

public class Parser {

    ArrayList<String> symbolicLines; // asm w/ variables
    ArrayList<String> numericLines; // asm w/o vars
    ArrayList<String> binaryLines; // binary

    String writeName;

    public Parser() {
        this.symbolicLines = new ArrayList<>();
        this.binaryLines = new ArrayList<>();
    }

    public void createInstructionList(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("//")) {
                    int commentIndex = line.indexOf("//");
                    line = line.substring(0,commentIndex);
                }
                line = line.trim();
                if (line.length() == 0)
                    continue;
                symbolicLines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.writeName = fileName.substring(0,fileName.indexOf('.')) + "Binary.txt";
    }

    public void printSymbolic() { this.print(symbolicLines); }
    public void printNumeric() { this.print(numericLines); }
    public void printBinary() { this.print(binaryLines); }

    private void print(ArrayList<String> arrList) {
        int i=0;
        for (String s : arrList) {
            System.out.println(i + ". " + s);
            i++;
        }
    }

    public void convertSymbolicToNumeric() {
        SymbolHandler sHandler = new SymbolHandler(symbolicLines);
        sHandler.handleSymbols();
        this.numericLines = sHandler.getNumericInstructions();
    }



    public void convertNumericToBinary() {
        for (String s : numericLines) {
            String ret = null;
            if (s.startsWith("@")) {
                ret = handleAInstruction(s);
            } else {
                CInstructionHandler cHandler = new CInstructionHandler(s);
                ret = cHandler.handle();
            }
            binaryLines.add(ret);
        }
    }

    private String handleAInstruction(String instruction) {
      String num = Integer.toBinaryString(Integer.parseInt(instruction.substring(1)));
      if (num.length() < 16) {
          int diff = 16 - num.length();
          for (int i=0; i<diff; i++) {
              num = "0"+num;

          }
      }
      return num;
    };

    public void writeToFile() {
        this.writeToFile(this.writeName);
    }

    public void writeToFile(String fileName) {
        if (this.binaryLines.size()==0) {
            System.out.println("No output lines. convert instructions first");
            return;
        }


        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (String s: binaryLines) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
