
/**
 *
 * @author Abdel K. Bokharouss
 */
public class Subtraction extends AbstractSolver {

    public Subtraction(IntegerRep x, IntegerRep y) {
        super(x, y);
    }

    @Override
    public IntegerRep compute() {
        
        //handle negative x and y
        //if x is negative
        if(x.isNegative()){
            //make all its digits negative, and then make x positive
            for(int i = 0; i < x.getLength(); i++){
                x.getChars()[i] *= -1;
            }
            x.setPositive();
        }
        //if y is negative
        if(y.isNegative()){
            //make all its digits negative, and then make y positive
            for(int i = 0; i < y.getLength(); i++){
                y.getChars()[i] *= -1;
            }
            y.setPositive();
        }
        
        
        //If x and y do not have the same length, add leading 0s to the shortest
        //if x and y do not have the same length
        if (x.getLength() != y.getLength()) {
            //if x is longer than y
            if (x.getLength() > y.getLength()) {
                //add leading 0s to y to make it the same length as x
                int[] temp = new int[x.getLength()];
                int[] zeros = new int[x.getLength() - y.getLength()];
                for (int i = 0; i < zeros.length; i++) {
                    zeros[i] = 0;
                }
                System.arraycopy(zeros, 0, temp, 0, zeros.length);
                System.arraycopy(y.getChars(), 0, temp, zeros.length, y.getLength());
                y.setChars(temp);
            } 
            //if y is longer than x
            else {
                //add leading 0s to x to make it the same length as y
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
        
        //if y is greater than x, flip them around and re-compute with negative outcome
        for(int i = 0; i < x.getLength(); i++){
            if(x.getChars()[i] > y.getChars()[i]){
                break;
            }
            else if(x.getChars()[i] < y.getChars()[i]){
                //restart with x and y switched around, and the solution negative
                IntegerRep solution = new Subtraction(y, x).compute();
                solution.setNegative();
                return solution;
            }
        }
        
        //Create a solution with the appropriate radix and length
        IntegerRep solution = new IntegerRep(x.getRadix(), false, new int[x.getLength()]);
        
        //for all digits in the solution
        for (int i = solution.getLength() - 1; i >= 0; i--) {
            //If the outcome of this single digit subtraction (x[i] - y[i]) is positive
            if (x.getChars()[i] >= y.getChars()[i]) {
                //then digit i in the solution is x[i] - y[i]
                solution.getChars()[i] = x.getChars()[i] - y.getChars()[i];
                IntegerArithmetic.countNumberElemOperations++;
            } 
            
            //If the outcome of this single digit subtraction is negative
            else {
                    //decrement x[i-1]
                    x.getChars()[i - 1] = x.getChars()[i - 1] - 1;
                    //add the radix to x[i] (this is what we just got from x[i-1])
                    x.getChars()[i] = x.getChars()[i] + x.getRadix();
                    //digit i in the solution is x[i] - y[i]
                    solution.getChars()[i] = x.getChars()[i] - y.getChars()[i];
                    IntegerArithmetic.countNumberElemOperations += 3;
            }
        }
        
        //remove leading 0s
        //while the first digit of the solution is a 0 and the solution has more
        //than one digit
        while(solution.getChars()[0] == 0 && solution.getLength() > 1){
            //remove the first digit from the solution
            int[] temp = new int[solution.getLength() - 1];
            System.arraycopy(solution.getChars(), 1, temp, 0, temp.length);
            solution.setChars(temp);
        }
        
        //return the final solution
        return solution;
    }

}
