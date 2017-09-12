import java.io.File;
import java.util.Scanner;

/**
 * Main class
 * @author Bart van Helvert
 */
public class IntegerArithmetic {
	/**
	 * If the code is meant for handin
	 */
	private static boolean HANDIN = false;

	/**
	 * The radix of the input
	 */
	private static int radix;

	/**
	 * The type of operation
	 */
	private static Operation operation;

	/**
	 * Stores the first value of the input
	 */
	private static int[] x;

	/**
	 * Stores the second value of the input
	 */
	private static int[] y;

	/**
	 * The answer of the calculation
	 */
	private static int[] answer;

	/**
	 * Main function
	 * 
	 * @param args input parameters
	 */
	public static void main(String[] args) throws Exception {
		//Storing data and add scanner
		File file = new File("./resources/example.txt");
		Scanner scanner;
		if(HANDIN) {
			scanner = new Scanner(System.in);
		} else {
			scanner = new Scanner(file);
		}

		while(scanner.hasNextLine()) {
			String line  = scanner.nextLine();
			if(line.startsWith("#")) {
				if(line.startsWith("# [answer]")) {
					//TODO: Check answer
				}
			} else if (line.startsWith("[radix]")) {
				radix = Integer.parseInt(line.replaceAll("[\\D]", ""));
                                System.out.println("Radix: " + radix);
			} else if (line.startsWith("[add]")) {
				operation = Operation.ADD;
                                System.out.println("Operation: add");
			} else if (line.startsWith("[subtract]")) {
				operation = Operation.SUBTRACT;
                                System.out.println("Operation: subtract");
			} else if (line.startsWith("[multiply]")) {
				operation = Operation.MULTIPLY;
                                System.out.println("Operation: multiply");
			} else if (line.startsWith("[karatsuba]")) {
				operation = Operation.KARATSUBA;
                                System.out.println("Operation: karatsuba");
			} else if (line.startsWith("[x]")) {
                                System.out.println("x (radix " + radix + "): " + line.replace("[x] ", ""));
				x = parseWord(line.replaceAll("[\\D]", ""));
                                System.out.println("x (int array): " + x);
			} else if (line.startsWith("[y]")) {
                                System.out.println("y (radix " + radix + "): " + line.replace("[y] ", ""));
				y = parseWord(line.replaceAll("[\\D]", ""));
                                System.out.println("y (int array): " + y);
				//TODO: Calculate answer
			}
		}
		
	}

	/**
	 * Parses a word into ints
	 * @param word 		The word to parse
	 */
	private static int[] parseWord(String word) {
		int[] temp = new int[word.length()];
		for(int i = 0; i < word.length(); i++) {
			temp[i] = Integer.parseInt(Character.toString(word.charAt(i)));
		}
		return temp;
	}
	
	/**
	 * Different operations
	 */
	private enum Operation {
		ADD, SUBTRACT, MULTIPLY, KARATSUBA;
	}

}