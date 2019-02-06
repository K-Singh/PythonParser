package com.company;

/**
 * The complexity class represents the Big O complexity of a given CodeBlock. Complexity can be in the form of
 * O(1), O(n), O(log(n)) and various combinations of the latter two.
 *
 * @author Kirat Singh - Kirat.Singh@Stonybrook.edu - ID: 112320621
 */
public class Complexity {
    private int nPower;
    private int logPower;

    /**
     * The empty constructor for the Complexity Class
     */
    public Complexity(){

    }

    /**
     * This constructor assigns the instance variables.
     * @param nNumber
     *      The power that n is raised to.
     * @param logNumber
     *      The power that log(n) is raised to.
     */
    public Complexity(int nNumber, int logNumber){
        nPower = nNumber;
        logPower = logNumber;
    }

    /**
     * @return
     *      Returns the power Log(n) is raised to.
     */
    public int getLogPower() {
        return logPower;
    }

    /**
     * @return
     *      Returns the power n is raised to.
     */
    public int getnPower() {
        return nPower;
    }

    /**
     * Sets the power Log(n) is raised to.
     * @param logNumber
     *      The new exponent for Log(n).
     */
    public void setLogPower(int logNumber) {
        this.logPower = logNumber;
    }

    /**
     * Sets the power n is raised to
     * @param nNumber
     *      The new exponent for n.
     */
    public void setnPower(int nNumber) {
        this.nPower = nNumber;
    }

    /**
     * This method compares two complexities and returns the one with the Higher Order.
     * Higher order complexities involve higher exponents. O(n) complexities also dominate
     * O(log(n)) complexities.
     * @param c1
     *      The first complexity to be compared
     * @param c2
     *      The second complexity to be compared.
     * @return
     *      Returns the c1, or c2, depending on which complexity is of a higher order. c2 is returned if they
     *      are of equal complexity.
     */
    public static Complexity getHigherOrder(Complexity c1, Complexity c2){
        if(c1.getnPower() > c2.getnPower()){
            return c1;
        }else if(c1.getnPower() == 0 && c2.getnPower() == 0){
            if(c1.getLogPower() > c2.getLogPower()){
                return c1;
            }else
                return c2;
        }

        return c2;
    }

    /**
     * This method takes two complexities and adds up their individual components in order to get the total
     * complexity of the two.
     * @param c1
     *      The first complexity to be added.
     * @param c2
     *      The second complexity to be added.
     * @return
     *      The sum of the two complexities.
     */
    public static Complexity getTotalComplexity(Complexity c1, Complexity c2){
        int totalNPower = c1.getnPower() + c2.getnPower();
        int totalLogPower = c1.getLogPower() + c2.getLogPower();
       // System.out.println("N power: " + totalNPower);
      //  System.out.println("Log Power: " + totalLogPower);

        return new Complexity(totalNPower, totalLogPower);
    }

    /**
     * Returns a string representing the Big O Complexity of the CodeBlock.
     * Powers of 0 on either the n power or log power result in that specific component not being shown.
     * Powers of 1 do not have an attatched carrot and exponent.
     * Other powers are shown in the form of n^nPower and log(n)^logPower
     * If both powers are 0, the Complexity is O(1)
     * @return
     *      A string representing the Big O Notation for this complexity.
     */
    public String toString() {
        String s = "O(";
        if(nPower <= 0 && logPower <= 0){
            s += "1";
        }else if(nPower > 1 && logPower <= 0){
            s += "n^"+nPower;
        }else if(nPower <= 0 && logPower > 1){
            s += "log(n)^"+logPower;
        }else if(nPower == 1 && logPower <= 0){
            s += "n";
        }else if(nPower <= 0 && logPower == 1){
            s += "log(n)";
        }else if(nPower == 1 & logPower == 1){
            s += "n * log(n)";
        } else{
            s += "n^"+nPower+" * log(n)^"+logPower;
        }
        s += ")";
        return s;
    }
}
