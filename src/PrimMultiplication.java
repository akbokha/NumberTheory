
import java.util.ArrayList;

/**
 *
 * @author Abdel K. Bokharouss
 * @author Remco Surtel
 * @author Joris Rombouts
 */
public class PrimMultiplication extends AbstractSolver {

    public PrimMultiplication(IntegerRep x, IntegerRep y) {
        super(x, y);
    }

    @Override
    public IntegerRep compute() {

        // create a double arraylist
        // so you can easily add elements at the beginning of the list later
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>(x.getLength());

        //loop through both [x] and [y]
        //loop from "right to left"
        for (int j = x.getLength() - 1; j >= 0; j--) {
            //list, where each element represents a digit
            ArrayList<Integer> temp2 = new ArrayList<>();
            int c = 0; //carry
            
            for (int k = x.getLength() - 1; k > j; k--) {
                // add zero's when go to "next line"
                temp2.add(0, 0); 
            }
            
            for (int i = y.getLength() - 1; i >= 0; i--) {
                int xy = (x.getChars()[i] * y.getChars()[j]) + c;
                IntegerArithmetic.countNumberMultiplications++;
                IntegerArithmetic.countNumberElemOperations++;
                temp2.add(0, xy % x.getRadix());
                c = (xy - xy % x.getRadix()) / x.getRadix();
                IntegerArithmetic.countNumberElemOperations++;
            }
            //at the end: if carry is not equal to 0, add it at the beginning
            //of the list
            if (c != 0) {
                temp2.add(0, c);
            }
            temp.add(0, temp2); //add the digit list to the temp-list
        }
        //convert list to int arrays
        ArrayList<int[]> intarrays = new ArrayList<>();
        for (int j = 0; j < temp.size(); j++) {
            int[] result = new int[temp.get(j).size()];
            for (int i = 0; i < temp.get(j).size(); i++) {
                result[i] = temp.get(j).get(i);
            }
            intarrays.add(j, result);
        }

        //add items in intarrays
        for (int i = 0; i < intarrays.size() - 1; i++) {
            intarrays.set(0, new Addition(new IntegerRep(x.getRadix(), false, intarrays.get(0)), new IntegerRep(x.getRadix(), false, intarrays.get(i + 1)), false).compute().getChars());
        }

        //create solution
        IntegerRep solution = new IntegerRep(x.getRadix(), false, intarrays.get(0));

        //handle negative inputs
        if (x.isNegative() ^ y.isNegative()) {
            solution.setNegative();
        }

        return solution;
    }
}
