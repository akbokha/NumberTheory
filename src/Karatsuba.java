/**
 *
 * @author Abdel K. Bokharouss
 */
public class Karatsuba extends AbstractSolver {
    
    private int numberOfElemOperations; // track the number of elementary additions/substractions
    private int numberOfElemMultiplications; // track the number of elementary multiplications
    
    private int radix; // base of the numbers
    
    public Karatsuba (IntegerRep x, IntegerRep y) {
        super(x, y);
        this.numberOfElemOperations = 0;
        this.numberOfElemMultiplications = 0;
    }
    
    @Override
    public IntegerRep compute() {
        addLeadingZeroes(); // add leading zeroes if odd bit length or uneven bit length among the two numbers.
        
        int n = x.getLength(); // bit length of the original numbers
        
        if (x.getRadix() != y.getRadix()) {
            throw new IllegalArgumentException("x and y should have the same base");
        } else {
            radix = x.getRadix(); // x and y should have the same radix.
        }
        
        int[] copy_x = new int[x.getLength()]; // a copy of the int array of x to use in the calculation
        System.arraycopy(x.getChars(), 0, copy_x, 0, x.getLength());
        IntegerRep calc_x = new IntegerRep(radix, false, copy_x);
        int[] copy_y = new int[y.getLength()]; // a copy of the int array of y to use in the calculation
        System.arraycopy(y.getChars(), 0, copy_y, 0, y.getLength());
        IntegerRep calc_y = new IntegerRep(radix, false, copy_y);
        
        IntegerRep result = multiplyKaratsuba(calc_x, calc_y, n); 
        return new IntegerRep(radix, isAnswerNegative(), result.getChars()); // dummy instance
    }
    
    private IntegerRep multiplyKaratsuba(IntegerRep xx, IntegerRep yy, int n) {
        if (n == 1) {
            int [] resultMultiplication = new int[]{xx.getChars()[0] * yy.getChars()[0]};
            numberOfElemMultiplications++;
            return new IntegerRep(radix, false, resultMultiplication);
        } else {
           int new_n = n / 2; // new bit length
           int [] xx_hi_chars = new int[new_n];
           int [] xx_lo_chars = new int[new_n];
           int [] yy_hi_chars = new int[new_n];
           int [] yy_lo_chars = new int[new_n];
        
           for (int i = 0; i < new_n; i++) {
               xx_hi_chars[i] = xx.getChars()[i];
               yy_hi_chars[i] = yy.getChars()[i];
           }
           
           for (int j = xx.getChars().length - 1; j >= new_n; j--) {
               xx_lo_chars[j] = xx.getChars()[j];
               yy_lo_chars[j] = yy.getChars()[j];
           }
           
           IntegerRep xx_hi = new IntegerRep(radix, false, xx_hi_chars);
           IntegerRep xx_lo = new IntegerRep(radix, false, xx_lo_chars);
           IntegerRep yy_hi = new IntegerRep(radix, false, yy_hi_chars);
           IntegerRep yy_lo = new IntegerRep(radix, false, yy_lo_chars);
           
           int [] b_temp = new int[new_n];
           b_temp[new_n - 1] = (int) Math.pow(radix, n);
           IntegerRep b_n = new IntegerRep(10, false, b_temp);
           int [] b2_temp = new int[new_n];
           b2_temp[new_n - 1] = (int) Math.pow(radix, new_n);
           IntegerRep b_n2 = new IntegerRep(10, false, b2_temp);
           
           IntegerRep xx_hi_yy_hi = multiplyKaratsuba(xx_hi, yy_hi, new_n);
           IntegerRep xx_hi_yy_hi_bn = multiplyKaratsuba(xx_hi_yy_hi, b_n, new_n);
           IntegerRep xx_hi_yy_lo = multiplyKaratsuba(xx_hi, yy_lo, new_n);
           IntegerRep xx_lo_yy_hi = multiplyKaratsuba(xx_lo, yy_hi, new_n);
           
           IntegerRep middle_term = new Addition(xx_hi_yy_lo, xx_lo_yy_hi, false).compute();
           IntegerRep middle_term_bn2 = multiplyKaratsuba(middle_term, b_n2, new_n);
           
           IntegerRep xx_lo_yy_lo = multiplyKaratsuba(xx_lo, yy_lo, new_n);
           
           IntegerRep middle_term_xx_lo_yy_lo = new Addition(middle_term_bn2, xx_lo_yy_lo, false).compute();
           IntegerRep final_result = new Addition(xx_hi_yy_hi_bn, middle_term_xx_lo_yy_lo, false).compute();
           
           numberOfElemOperations += 3;
           numberOfElemMultiplications += 6;
           return final_result;
        } 
    }
    
    public int getNumberElemOperations() {
        return this.numberOfElemOperations;
    }    
    
    public int getNumberMultiplications() {
        return this.numberOfElemMultiplications;
    }
    
    private boolean isAnswerNegative() {
        return (this.x.isNegative() && ! (this.y.isNegative())) || (! (this.x.isNegative()) && this.y.isNegative());
    }
    
    private void addLeadingZeroes() {
        int longestBitLength = Math.max(x.getLength(), y.getLength());
        if (longestBitLength % 2 == 1) { // if odd, make even
            longestBitLength += 1;
        }
        if (x.getLength() != longestBitLength) {
            int [] zeroes_x = new int [x.getLength() - longestBitLength];
            int [] x_padded = new int[x.getLength() + zeroes_x.length];
            System.arraycopy(zeroes_x, 0, x_padded, 0, zeroes_x.length);
            System.arraycopy(x.getChars(), 0, x_padded, zeroes_x.length, x.getLength());
            x.setChars(x_padded);
        }
        if (y.getLength() != longestBitLength) {
            int [] zeroes_y = new int [y.getLength() - longestBitLength];
            int [] y_padded = new int[y.getLength() + zeroes_y.length];
            System.arraycopy(zeroes_y, 0, y_padded, 0, zeroes_y.length);
            System.arraycopy(y.getChars(), 0, y_padded, zeroes_y.length, y.getLength());
            y.setChars(y_padded);
        }
    }
    
}
