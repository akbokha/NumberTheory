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
        
        //If x and y do not have the same length, add leading 0s to the shortest
        if (x.getLength() != y.getLength()) {
            if (x.getLength() > y.getLength()) {
                int[] temp = new int[x.getLength()];
                int[] zeros = new int[x.getLength() - y.getLength()];
                for (int i = 0; i < zeros.length; i++) {
                    zeros[i] = 0;
                }
                System.arraycopy(zeros, 0, temp, 0, zeros.length);
                System.arraycopy(y.getChars(), 0, temp, zeros.length, y.getLength());
                y.setChars(temp);
            } else {
                int[] temp = new int[y.getLength()];
                int[] zeros = new int[y.getLength() - x.getLength()];
                for (int i = 0; i < zeros.length; i++) {
                    zeros[i] = 0;
                }
                System.arraycopy(zeros, 0, temp, 0, zeros.length);
                System.arraycopy(x.getChars(), 0, temp, zeros.length, x.getLength());
                x.setChars(temp);
            }
        }
        
        int[] result = new int[x.getLength() + 1];
        int remainder;
        int remember = 0;
        int temp;
        for(int i = x.getLength() - 1; i >= 0; i--) {
            temp = x.getChars()[i] + y.getChars()[i];
            remainder = temp % x.getRadix();
            result[i] = remainder + remember;
            remember = temp / x.getRadix();
        }
        if(remember != 0 ) {
            result[0] = remember;
            return new IntegerRep(x.getRadix(), resultNegative, result);
        } else {
            int[] result2 = new int[x.getLength()];
            System.arraycopy(result, 0, result2, 0, x.getLength());
            return new IntegerRep(x.getRadix(), resultNegative, result2);
        }    
    }

    
}
