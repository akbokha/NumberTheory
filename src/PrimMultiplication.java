
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdel K. Bokharouss
 */
public class PrimMultiplication extends AbstractSolver {
    
    private int numberOfElemOperations; // track the number of elementary additions/substractions
    private int numberOfElemMultiplications; // track the number of elementart multiplications
    
    public PrimMultiplication (IntegerRep x, IntegerRep y) {
        super(x, y);
        this.numberOfElemOperations = 0;
        this.numberOfElemMultiplications = 0;
    }
    
    @Override
    public IntegerRep compute() {
        
        // compute with x and y
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>(x.getLength());

        for (int j = x.getLength() - 1; j >= 0; j--){
            ArrayList<Integer> temp2 = new ArrayList<>();
            int c = 0;
            for (int k = x.getLength() - 1; k > j; k--) {
                temp2.add(0,0);
            }
            for (int i = y.getLength() - 1; i >= 0; i--){
                int xy = (x.getChars()[i] * y.getChars()[j]) + c;
                numberOfElemMultiplications++;
                temp2.add(0, xy % x.getRadix());
                c = (xy - xy % x.getRadix()) / x.getRadix();
            }
            if (c != 0) {
                temp2.add(0,c);
            }
            temp.add(0, temp2);
        }
        //convert to int arrays
        ArrayList<int[]> intarrays = new ArrayList<>();
        for(int j = 0; j < temp.size(); j++){
            int[] result = new int[temp.get(j).size()];
            for (int i = 0; i < temp.get(j).size(); i++){
                result[i] = temp.get(j).get(i);
            }
            intarrays.add(j, result);
        }
       
        //TODO add items in intarrays
        for(int i = 0; i < intarrays.size() - 1; i++){
            intarrays.set(0, new Addition(new IntegerRep(x.getRadix(), false, intarrays.get(0)), new IntegerRep(x.getRadix(), false, intarrays.get(i+1)), false).compute().getChars());
            numberOfElemOperations++;
        }
        
        //create solution
        IntegerRep solution = new IntegerRep(x.getRadix(), false, intarrays.get(0));
        
        //handle negative inputs
        if(x.isNegative() ^ y.isNegative()){
            solution.setNegative();
        }
        
        return solution;
    }
    
    public int getNumberElemOperations() {
        return this.numberOfElemOperations;
    }    
    
    public int getNumberMultiplications() {
        return this.numberOfElemMultiplications;
    }
}
