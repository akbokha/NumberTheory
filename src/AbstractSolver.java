/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Solver that does an operation on 2 integers with the same length
 *
 * @author Abdel K. Bokharouss
 * @author Bart van Helvert
 */
public abstract class AbstractSolver {
    /**
     * First number of the computation
     */
    IntegerRep x;

    /**
     * Second number of the computation
     */
    IntegerRep y;
    
    public AbstractSolver(IntegerRep x, IntegerRep y) {
        this.x = x;
        this.y = y; 
    }
    
    /**
     * Return an integer-representation of the answer based on the parsed computation method
     * and the values of this.x and this.y of the particular object
     *
     * @return integer representation of the result of the computation
     */
    public abstract IntegerRep compute(); 
    
}
