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
        int remainder;
        int remember = 0;
        int temp;
        for(int i = x.getLength() - 1; i >= 0; i--) {
            temp = x.getChars()[i] + y.getChars()[i];
            remainder = temp % x.getRadix();
            result[x.getLength() - i - 1] = remainder + remember;
            remember = temp / x.getRadix();
        }
        if(remember != 0 ) {
            result[0] = remember;
        }
        return new IntegerRep(x.getRadix(), resultNegative, result);
    }

    
}
