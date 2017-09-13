/**
 *
 * @author Abdel K. Bokharouss
 */
public class Karatsuba extends AbstractSolver {
    
    private int numberOfElemOperations; // track the number of elementary additions/substractions
    private int numberOfElemMultiplications; // track the number of elementart multiplications
    
    public Karatsuba (IntegerRep x, IntegerRep y) {
        super(x, y);
        this.numberOfElemOperations = 0;
        this.numberOfElemMultiplications = 0;
    }
    
    @Override
    public IntegerRep compute() {
        // compute with x and y
        return new IntegerRep(0, false, new int[] {0}); // dummy instance
    }
    
    public int getNumberElemOperations() {
        return this.numberOfElemOperations;
    }    
    
    public int getNumberMultiplications() {
        return this.numberOfElemMultiplications;
    }
    
}
