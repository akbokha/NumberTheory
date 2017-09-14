/**
 * Calculates the addition of either 2 negative numbers or 2 non-negative numbers
 * @author Bart van Helvert
 */
public class Addition extends AbstractSolver {

    /**
     * If the result should be negative
     */
    private final boolean resultNegative;
    
    public Addition (IntegerRep x, IntegerRep y, boolean resultNegative) {
        super(x, y);
        this.resultNegative = resultNegative;
    }
    
    @Override
    public IntegerRep compute() {
        int[] result = new int[x.getLength() + 1];
        int remember = 0;
        int indexNumber;

        //Run the algorithm
        for(int i = x.getLength() - 1; i >= 0; i--) {
            indexNumber = x.getChars()[i] + y.getChars()[i] + remember;
            result[i + 1] = indexNumber % x.getRadix();
            remember = indexNumber / x.getRadix();
        }

        //Add most significant word if necessary
        if(remember != 0 ) {
            result[0] = remember;
            return new IntegerRep(x.getRadix(), resultNegative, result);
        } else {
            int[] resultWithoutRemember = new int[x.getLength()];
            System.arraycopy(result, 1, resultWithoutRemember, 0, x.getLength());
            return new IntegerRep(x.getRadix(), resultNegative, resultWithoutRemember);
        }

    }

    
}
