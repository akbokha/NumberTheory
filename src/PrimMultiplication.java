
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
        
        for (int i = x.getLength() - 1; i >= 0; i--){
            ArrayList<Integer> temp2 = new ArrayList<>();
            for (int j = y.getLength() - 1; j >= 0; j--){
                int xy = x.getChars()[i] * y.getChars()[j];
                numberOfElemMultiplications++;
                if(temp2.size() <= j + 1 - y.getLength()){
                    temp2.add(0, xy % x.getRadix());
                }
                else{
                    temp2.set(0, temp2.get(0) + xy % x.getRadix());
                    numberOfElemOperations++;
                }
                if(x.getChars()[i] * y.getChars()[j] > x.getRadix()){
                    temp2.add(0, xy - xy % x.getRadix());
                    numberOfElemOperations++;
                }
            }
            temp.add(0, temp2);
        }
        
        
        
        
        return new IntegerRep(0, false, new int[] {0}); // dummy instance
    }
    
    public int getNumberElemOperations() {
        return this.numberOfElemOperations;
    }    
    
    public int getNumberMultiplications() {
        return this.numberOfElemMultiplications;
    }
}
