package numbers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main class
 * @author Bart van Helvert
 */
public class IntegerArithmetic {
	/**
	 * The radix used
	 */
	private static int radix;
	
	/**
	 * The operation used
	 */
	private static Operation operation;
	
	/**
	 * First value to be used
	 */
	private static ArrayList<Integer> x;
	
	/**
	 * Second value to be used
	 */
	private static ArrayList<Integer> y;
	
	/**
	 * Scanner for reading input
	 */
	private static Scanner scanner;
	
	
	/**
	 * Main function
	 * 
	 * @param args input parameters
	 */
	public static void main(String[] args) throws Exception {
		scanner = new Scanner(System.in);
		radix = readRadix();
		if(radix <= 0) {
			throw new IOException("Radix should be greater than 0");
		} else {
			System.out.println("Radix: " + radix);
		}
		
		operation = readOperation(scanner.nextLine());
		if(operation == null) {
			throw new IOException("Operation not supported");
		} else {
			System.out.println("Operation: " + operation.getStringOperation());
		}
		readIntegers();
		
	}
	
	/**
	 * Reads the radix
	 */
	public static int readRadix() {
		return scanner.nextInt();
	}
	
	/**
	 * Reads the operation
	 */
	public static Operation readOperation(String line) throws IOException {
		return Operation.getOperation(line.replaceAll("[]", "")).get();
	}
	
	/**
	 * Reads the input integers
	 */
	public static void readIntegers() {
		
	}
	
	/**
	 * Different operations
	 * @author Bart van Helvert
	 */
	private enum Operation {
		ADD("add"), SUBTRACT("subtract"), MULTIPLY("multiply"), KARATSUBA("karatsuba");
		
		private String stringOperation;
		
		Operation(String operation) {
			this.stringOperation = operation;
		}
		
		public String getStringOperation() {
			return stringOperation;
		}
		
		
		public static Optional<Operation> getOperation(String operation) {
			return Arrays.stream(Operation.values()).filter(op -> op.getStringOperation().equals(operation)).findAny();
		}
	}

}