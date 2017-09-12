/**
 * Representation of an integer in 2 <= base <= 16
 * @author Abdel K. Bokharouss
 */
public class IntegerRep {
    
     private int radix; // the base of the number
     private boolean isNegative = false; // word negative or not
     private int[] chars; // the characters which make up the word
    
    public IntegerRep(int radix, boolean isNegative, int[] chars) {
        this.radix = radix;
        this.isNegative = isNegative;
        this.chars = chars;
    }
    
    public int getRadix() {
        return this.radix;
    }
    
    public boolean isNegative() {
        return this.isNegative;
    }
    
    public int [] getChars() {
        return this.chars;
    }
    
    public int getLength() {
        return this.chars.length;
    }
    
}
