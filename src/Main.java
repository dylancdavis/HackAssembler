import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final String FILE_PATH = "testLines.txt";
        final boolean MANUAL_INPUT = false;

        Scanner input = new Scanner(System.in);

        String fileName;

        if (MANUAL_INPUT) {
            System.out.println("Please enter the path to the file you want to assemble:");
            fileName = input.nextLine();
        } else {
            System.out.println("Manual input disabled, using: " + FILE_PATH);
            fileName = FILE_PATH;
        }
        System.out.println();

        Parser parser = new Parser();
        parser.createInstructionList(fileName);

        System.out.println("Extracted Assembly Instructions:");
        parser.printSymbolic();
        System.out.println();

        parser.convertSymbolicToNumeric();
        System.out.println("Numeric Assembly Instructions");
        parser.printNumeric();
        System.out.println();

        parser.convertNumericToBinary();
        System.out.println("Converted Binary Instructions:");
        parser.printBinary();

        parser.writeToFile();

    }
}