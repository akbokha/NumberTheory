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
			} else if (line.startsWith("[add]")) {
				operation = Operation.ADD;
			} else if (line.startsWith("[subtract]")) {
				operation = Operation.SUBTRACT;
			} else if (line.startsWith("[multiply]")) {
				operation = Operation.MULTIPLY;
			} else if (line.startsWith("[karatsuba]")) {
				operation = Operation.KARATSUBA;
			} else if (line.startsWith("[x]")) {
				x = parseWord(scanner.nextLine());
			} else if (line.startsWith("[y]")) {
				y = parseWord(scanner.nextLine());
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