
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
        if(x.isNegative()){
            for(int i = 0; i < x.getLength(); i++){
                x.getChars()[i] *= -1;
            }
        }
        if(y.isNegative()){
            for(int i = 0; i < y.getLength(); i++){
                y.getChars()[i] *= -1;
            }
        }
        
        
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
        
        //Create the solution
        IntegerRep solution = new IntegerRep(x.getRadix(), false, new int[x.getLength()]);
        
        
        for (int i = solution.getLength() - 1; i >= 0; i--) {
            //If the outcome of this single digit subtraction is positive
            if (x.getChars()[i] >= y.getChars()[i]) {
                solution.getChars()[i] = x.getChars()[i] - y.getChars()[i];
            } 
            
            //If the outcome of this single digit subtraction is negative
            else {
                
                //If this is NOT the last digit
                if (i != 0) {
                    x.getChars()[i - 1] = x.getChars()[i - 1] - 1;
                    x.getChars()[i] = x.getChars()[i] + x.getRadix();
                    solution.getChars()[i] = x.getChars()[i] - y.getChars()[i];
                }
                
                //If this is the last digit
                else{
                    solution = new Subtraction(y, x).compute();
                    solution.setNegative();
                }
            }
        }
        
        //remove leading 0s
        while(solution.getChars()[0] == 0){
            int[] temp = new int[solution.getLength() - 1];
            System.arraycopy(solution.getChars(), 1, temp, 0, temp.length);
            solution.setChars(temp);
        }
        
        return solution;
    }

}
