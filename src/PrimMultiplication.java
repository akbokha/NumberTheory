/**
 *
 * @author Abdel K. Bokharouss
 */
public class PrimMultiplication extends AbstractSolver {
    
    public PrimMultiplication (IntegerRep x, IntegerRep y) {
        super(x, y);
    }
    
    @Override
    public IntegerRep compute() {
        // compute with x and y
        return new IntegerRep(0, false, new int[] {0}); // dummy instance
    }
    
}
