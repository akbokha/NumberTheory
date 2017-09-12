/**
 *
 * @author Abdel K. Bokharouss
 */
public class Subtraction extends AbstractSolver {
    
    public Subtraction (IntegerRep x, IntegerRep y) {
        super(x, y);
    }
    
    @Override
    public IntegerRep compute() {
        if(x.getLength() != y.getLength()){
            if(x.getLength() > y.getLength()){
                int[] temp = new int[x.getLength()];
                int[] zeros = new int [x.getLength() - y.getLength()];
                for (int i = 0; i < zeros.length; i++){
                    zeros[i] = 0;
                }
                System.arraycopy(zeros, 0, temp, 0, zeros.length);
                System.arraycopy(y, 0, temp, zeros.length, y.getLength());
                y = temp;
            }
            else{
                int[] temp = new int[y.getLength()];
                int[] zeros = new int [y.getLength() - x.getLength()];
                for (int i = 0; i < zeros.length; i++){
                    zeros[i] = 0;
                }
                System.arraycopy(zeros, 0, temp, 0, zeros.length);
                System.arraycopy(x, 0, temp, zeros.length, x.getLength());
                x = temp;
            }
        }
        int[] solution = new int[x.getLength()]; //System.out.println("Solution length: " + solution.length);
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
        return new IntegerRep(0, false, new int[] {0}); // dummy solution instance
    }
    
}
