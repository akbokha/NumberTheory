
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
            for(int k = x.getLength() - 1; k > i; k--){
                temp2.add(0,0);
            }
            for (int j = y.getLength() - 1; j >= 0; j--){
                for (int k = y.getLength() - 1; k > j; k--) {
                    temp2.add(0, 0);
                }
                int xy = x.getChars()[i] * y.getChars()[j];
                numberOfElemMultiplications++;
                if(temp2.size() <= j + 1 - y.getLength()){
                    temp2.add(0, xy % x.getRadix());
                }
                else{
                    temp2.set(0, temp2.get(0) + xy % x.getRadix());
                    numberOfElemOperations++;
                }
                if(xy > x.getRadix()){
                    temp2.add(0, (xy - xy % x.getRadix()) / x.getRadix());
                    numberOfElemOperations++;
                }
            }
            temp.add(0, temp2);
            
            //Print digits added
            String itemString = "";
            for (int j = 0; j < temp2.size(); j++){
                itemString += Integer.toString(temp2.get(j), x.getRadix());
            }
            System.out.println("digit: " + itemString);
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
        
        //print items to be added
        for (int i = 0; i < intarrays.size(); i++) {
            String itemString = "";
            for (int j = 0; j < intarrays.get(i).length; j++){
                itemString += Integer.toString(intarrays.get(i)[j], x.getRadix());
            }
            System.out.println("Item: " + itemString);
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
