import java.io.File;
import java.util.Scanner;
import java.lang.Math;

/**
 * Main class
 * @author Bart van Helvert
 */
public class IntegerArithmetic {
	/**
	 * If the code is meant for hand in of the assignment
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
	private static IntegerRep x;

	/**
	 * Stores the second value of the input
	 */
	private static IntegerRep y;

	/**
	 * The answer of the calculation
	 */
	private static IntegerRep answer;

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
                                System.out.println("x (radix " + radix + "): " + line);
				x = parseWord(line.substring(4)); //remove "[x] "
			} else if (line.startsWith("[y]")) {
                                System.out.println("y (radix " + radix + "): " + line);
				y = parseWord(line.substring(4)); //remove "[y] "
                                
                                //Calculate the solution
                                IntegerRep solutionIntRepresentation = solve();
                                String solutionString = "";
                                if (solutionIntRepresentation.isNegative()) {
                                        solutionString += "-";
                                }
                                for(int i = 0; i < solutionIntRepresentation.getLength(); i++){
                                    solutionString += Integer.toString(solutionIntRepresentation.getChars()[i] , radix);
                                }
                                System.out.println("Solution: " + solutionString);
                                System.out.println("");
			}
		}
		
	}
        
        public static IntegerRep solve(){
            IntegerRep answer = null;
            switch(operation) {
                case ADD:
                	if(x.isNegative() && y.isNegative()) {
						answer = new Addition(x, y, true).compute();
					} else if (x.isNegative() || y.isNegative()) {
                		if(x.isNegative()){
                			x.setPositive();
						}
                		if(y.isNegative()) {
                			y.setPositive();
						}
						answer = new Subtraction(x, y).compute();
					} else {
						answer = new Addition(x, y, false).compute();
					}

                    break;
                case SUBTRACT:
                    answer = new Subtraction(x, y).compute();
                    break;
                case MULTIPLY:
                    answer = new PrimMultiplication(x, y).compute();
                    break;
                case KARATSUBA:
                    answer = new Karatsuba(x, y).compute();
            }
            return answer;
        }

	/**
	 * Parses a word into an integerRepresentation (base, isNegative and characters which make up the word
	 * @param word 		The word to parse
	 */
	private static IntegerRep parseWord(String word) {
            boolean isNegative = false;
                if (word.startsWith("-")){
                    word = word.substring(1);
                    isNegative = true;
                }
                int[] temp = new int[word.length()];
		for(int i = 0; i < word.length(); i++) {
			temp[i] = Integer.parseInt(Character.toString(word.charAt(i)), radix);
		}
		IntegerRep integerRepresentation = new IntegerRep(radix, isNegative, temp);
                return integerRepresentation;
	}
	
	/**
	 * Different operations
	 */
	private enum Operation {
		ADD, SUBTRACT, MULTIPLY, KARATSUBA;
	}
}