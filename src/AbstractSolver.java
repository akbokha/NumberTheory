/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdel K. Bokharouss
 */
public abstract class AbstractSolver {
    IntegerRep x; // first number of the calculation
    IntegerRep y; // second number of the calculation
    
    public AbstractSolver(IntegerRep x, IntegerRep y) {
        this.x = x;
        this.y = y; 
    }
    
    /**
     * Return an integer-representation of the answer based on the parsed computation method
     * and the values of this.x and this.y of the particular object
     * @return 
     */
    public abstract IntegerRep compute(); 
    
}
