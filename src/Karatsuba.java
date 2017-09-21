import java.util.Arrays;

/**
 *
 * @author Abdel K. Bokharouss
 */
public class Karatsuba extends AbstractSolver {
    
    private int radix; // base of the numbers
    
    public Karatsuba (IntegerRep x, IntegerRep y) {
        super(x, y);
    }
    
    @Override
    public IntegerRep compute() {
        radix = this.x.getRadix(); // this.x.getRadix() == this.y.getRadix()
        IntegerRep result = multiplyKaratsuba(this.x, this.y); // calculate x * y using the method of Karatsuba
        result.setChars(removeLeadingZeroes(result.getChars())); //remove leading zeroes
        return new IntegerRep(radix, isAnswerNegative(this.x, this.y), result.getChars()); // return x * y 
    }
    
    /**
     * Multiplication by the method of Karatsuba (using recursion)
     * @param xx first integer
     * @param yy second integer
     * @return an integer representation (IntegerRep) of the multiplication of the two numbers
     */
    private IntegerRep multiplyKaratsuba(IntegerRep xx, IntegerRep yy) {
        boolean resultIsNegative = isAnswerNegative(xx, yy); 
        
        if (xx.getLength() == 1 && yy.getLength() == 1) { // base case
            int result = xx.getChars()[0] * yy.getChars()[0];
            IntegerArithmetic.countNumberMultiplications++;
            int [] resultChars;
            if (result >= radix) {
                resultChars = new int[]{(result / radix), (result % radix)};
            } else {
                resultChars = new int[]{result};
            }
            return new IntegerRep(radix, resultIsNegative, resultChars);
        } else {
            int longest_length = Math.max(xx.getLength(), yy.getLength());
            if (longest_length % 2 == 1) { // the bit length has to be even
                longest_length++;
            }
            // add leading zero if the bit length and array length do not match
            int [] xx_chars = addLeadingZeroes(xx, longest_length);
            int [] yy_chars = addLeadingZeroes(yy, longest_length);
            
            // splitting the (array representation) of the integers 
            IntegerRep xxHi = new IntegerRep(radix, false, Arrays.copyOfRange(xx_chars, 0, (longest_length / 2)));
            IntegerRep yyHi = new IntegerRep(radix, false, Arrays.copyOfRange(yy_chars, 0, (longest_length / 2)));
            IntegerRep xxLo = new IntegerRep(radix, false, Arrays.copyOfRange(xx_chars, (longest_length / 2), longest_length));
            IntegerRep yyLo = new IntegerRep(radix, false, Arrays.copyOfRange(yy_chars, (longest_length / 2), longest_length));
            
            IntegerRep xxHi_x_yyHi = multiplyKaratsuba(xxHi, yyHi);
            IntegerRep xxLo_x_yyLo = multiplyKaratsuba(xxLo, yyLo);
            IntegerRep xxHi_plus_xxLo = new Addition(xxHi, xxLo, false).compute();
            IntegerRep yyHi_plus_yyLo = new Addition(yyHi, yyLo,false).compute();
            
            IntegerRep xxHixxLo_x_yyHiyyLo = multiplyKaratsuba(xxHi_plus_xxLo, yyHi_plus_yyLo);
            IntegerRep minus_xxHi_x_yyHi = new Subtraction(xxHixxLo_x_yyHiyyLo, xxHi_x_yyHi).compute();
            IntegerRep minus_xxLo_x_yyLo = new Subtraction(minus_xxHi_x_yyHi, xxLo_x_yyLo).compute();
            
            // do the necessary bit shifting (because of b^n and b^(n/2), where b = this.radix and n = longest_length, in the Karatsuba formula
            IntegerRep shift_xxHi_x_yyHi = shift(xxHi_x_yyHi, longest_length);
            IntegerRep shift_minus_xxLo_x_yyLo = shift(minus_xxLo_x_yyLo, (longest_length/2));
            
            IntegerRep add_results = new Addition(shift_xxHi_x_yyHi, shift_minus_xxLo_x_yyLo, false).compute();
            IntegerRep final_result = new Addition(add_results, xxLo_x_yyLo, false).compute();
            if (resultIsNegative) {
                final_result.setNegative(); // needed for the correct output
            }
            return final_result;
        } 
    }
    
    /**
     * Add leading zeroes to the int (array) if necessary
     * @param num the integer that possibly needs leading zeroes
     * @param longest_len the bit length we need to reach (or already have)
     * @return an array with length = longest_len
     */
    private int [] addLeadingZeroes(IntegerRep num, int longest_len) {
        if (num.getLength() < longest_len) {
            int [] result = new int[longest_len];
            int [] zeroes = new int[longest_len - num.getLength()];
            System.arraycopy(zeroes, 0, result, 0, zeroes.length);
            System.arraycopy(num.getChars(), 0, result, zeroes.length, num.getLength());
            return result;
        } else {
            return num.getChars();
        }
    }
    
    /**
     * Remove leading zeroes from the array in question
     * @param input the array from which leading zeroes need to be removed
     * @return the input array without leading zeroes
     */
    private int [] removeLeadingZeroes(int [] input) {
        int count_zeroes = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] != 0) {
                break;
            }
            count_zeroes++;
        }
        int [] result = new int[input.length - count_zeroes];
        System.arraycopy(input, count_zeroes, result, 0, (input.length - count_zeroes));
        return result;
    }
    
    /**
     * Shift the array found in the IntegerRep k bits
     * @param num the integer that needs bit shifting
     * @param k the number of bits we need to shift
     * @return the same integer with the necessary shifting
     */
    private IntegerRep shift (IntegerRep num, int k) {
        int [] padded_array = new int[num.getLength() + k];
        System.arraycopy(num.getChars(), 0, padded_array, 0, num.getLength());
        return new IntegerRep(radix, false, padded_array);
    }
    
    /**
     * Determines whether the answer of the multiplication num1 * num2 should be negative
     * @param num1 the first integer
     * @param num2 the second integer
     * @return true if answer should be negative
     */
    private boolean isAnswerNegative(IntegerRep num1, IntegerRep num2) {
        return (num1.isNegative() && ! (num2.isNegative())) || (! (num1.isNegative()) && num2.isNegative());
    }
}
