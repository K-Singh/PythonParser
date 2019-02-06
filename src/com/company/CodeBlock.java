package com.company;

/**
 * The CodeBlock class represents a block of python code being read from the file. A CodeBlock
 * can be one of six types as defined by the enum BLOCK_TYPES.
 *
 * @author Kirat Singh - Kirat.Singh@Stonybrook.edu - ID: 112320621
 */
public class CodeBlock {
    public enum BLOCK_TYPES {DEF, FOR, WHILE, IF, ELIF, ELSE}

    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String name;
    private String loopVariable;

    /**
     * The CodeBlock constructor. It assigns the instance variables to each new CodeBlock.
     * @param bC
     *      The block complexity of the CodeBlock
     * @param sC
     *      The highest sub complexity of the CodeBlock
     * @param bName
     *      The name of the CodeBlock.
     * @param lVar
     *      The loop variable of the CodeBlock.
     */
   public CodeBlock(Complexity bC, Complexity sC, String bName, String lVar){
       blockComplexity = bC;
       highestSubComplexity = sC;
       name = bName;
       loopVariable = lVar;
   }

    /**
     * @return
     *   Returns the Block Complexity of the Block.
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * @return
     *      Returns the Highest Sub Complexity of the Block.
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * @return
     *      Returns the name of the block.
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     *      Returns the loop variable of the block.
     */
    public String getLoopVariable(){
       return loopVariable;
    }

    /**
     * Sets the name of the block.
     * @param name
     *      The new name of the block.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the block complexity
     * @param blockComplexity
     *      The new Block Complexity.
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * Sets the highest Sub complexity
     * @param highestSubComplexity
     *      The new sub complexity.
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }


    /**
     * Sets the loop variable
     * @param loopVariable
     *      The new loop variable.
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }
}
