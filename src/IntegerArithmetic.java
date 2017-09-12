import java.io.File;
import java.util.Scanner;
import java.lang.Math;

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
                                int[] solutionArray = solve();
                                String solution = "";
                                /*System.out.print("Solution: ");
                                for(int i = 0; i < solutionArray.length; i++){
                                    System.out.print(solutionArray[i]);
                                }
                                System.out.println("");*/
                                for(int i = 0; i < solutionArray.length; i++){
                                    //System.out.println(Integer.toString(solutionArray[i], radix));
                                    solution += Integer.toString(solutionArray[i], radix);
                                }
                                System.out.println("Solution: " + solution);
                                System.out.println("");
			}
		}
		
	}
        
        public static int[] solve(){
            if (Operation.SUBTRACT == operation){
                if(x.length != y.length){
                    if(x.length > y.length){
                        int[] temp = new int[x.length];
                        int[] zeros = new int [x.length - y.length];
                        for (int i = 0; i < zeros.length; i++){
                            zeros[i] = 0;
                        }
                        System.arraycopy(zeros, 0, temp, 0, zeros.length);
                        System.arraycopy(y, 0, temp, zeros.length, y.length);
                        y = temp;
                    }
                    else{
                        int[] temp = new int[y.length];
                        int[] zeros = new int [y.length - x.length];
                        for (int i = 0; i < zeros.length; i++){
                            zeros[i] = 0;
                        }
                        System.arraycopy(zeros, 0, temp, 0, zeros.length);
                        System.arraycopy(x, 0, temp, zeros.length, x.length);
                        x = temp;
                    }
                }
                int[] solution = new int[x.length];
                //System.out.println("Solution length: " + solution.length);
                for(int i = solution.length - 1; i >= 0; i--){
                    if (x[i] >= y[i]){
                        solution[i] = x[i] - y[i];
                    }
                    else{
                        //TODO: handle negative numbers
                        if (i != 0){
                            x[i-1] = x[i-1] - 1;
                        }
                        x[i] = x[i] + radix;
                        solution[i] = x[i] - y[i];
                    }
                }
                //TODO: remove leading 0's
                /*while(solution[0] == 0){
                    solution.
                }*/
                return solution;
            }
            
            return new int[1];
        }

	/**
	 * Parses a word into ints
	 * @param word 		The word to parse
	 */
	private static int[] parseWord(String word) {
                if (word.startsWith("-")){
                    word = word.substring(1);
                    // TODO: handle negative numbers.
                }
                int[] temp = new int[word.length()];
		for(int i = 0; i < word.length(); i++) {
			temp[i] = Integer.parseInt(Character.toString(word.charAt(i)), radix);
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