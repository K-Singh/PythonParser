package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * The driver class, PythonTracer asks the user for a file name and returns back the Big O complexity
 * of that file.
 *
 * @author Kirat Singh - Kirat.Singh@Stonybrook.edu - ID: 112320621
 */
public class PythonTracer {
    private static final int SPACE_COUNT = 4;

    /**
     * This method traces the given file and returns the functions Big O complexity.
     * Complexity is determined by going through all parts of the file and adding various CodeBlocks
     * to a stack. When a certain CodeBlock has finished, parts of the stack are popped and to the total
     * Complexity is calculated.
     * @param fileName
     *      The name of the file to be used.
     * @return
     *      The Complexity of the entire file.
     * @throws
     *      IOException if file was not found
     */
    public static Complexity traceFile(String fileName) throws IOException{
        try {
            Complexity totalComplexity = new Complexity();
            Stack<CodeBlock> stack = new Stack<CodeBlock>();
            FileInputStream fis;
            InputStreamReader inStream;
            BufferedReader reader;

            fis = new FileInputStream(fileName);
            inStream = new InputStreamReader(fis);
            reader = new BufferedReader(inStream);
            String currentLine = null;
            String blockString = "";
            int currentLevel = 0;
            ArrayList<Integer> blockNames = new ArrayList<Integer>();


            while ((currentLine = reader.readLine()) != null) {

           //    System.out.println("CurrentString: " + blockString);
           //     System.out.println("CurrentLevel: " + currentLevel);
             //   System.out.println(blockNames.size());
              //  System.out.println("Stack size: " + stack.size());
              //  System.out.println(currentLine);
                if(!currentLine.isEmpty() && !currentLine.contains("#")){
                    int indents = (currentLine.length() - currentLine.trim().length())/SPACE_COUNT;
                    currentLevel = indents;
                    while(indents < stack.size()){
                        String s = blockString.substring(0,blockString.length()-1);
                        currentLevel = indents;
                       // currentLevel--;
                       // System.out.println("meme");
                      //  System.out.println(blockNames.size());
                      //  System.out.println(currentLevel);
                        if(blockNames.size() >= currentLevel+2){
                            blockNames.remove(currentLevel+1);
                            blockString = blockString.substring(0,blockString.length()-2);
                           // System.out.println("uh oh");
                        }

                        if(indents == 0){
                            reader.close();
                           System.out.println("Return block Comp");
                            return Complexity.getTotalComplexity(stack.peek().getBlockComplexity(),
                                    stack.peek().getHighestSubComplexity());

                        }else{
                            CodeBlock oldBlock = stack.pop();
                            Complexity oldTopComplexity = Complexity.getTotalComplexity(oldBlock.getBlockComplexity(),
                                    oldBlock.getHighestSubComplexity());
                           // System.out.println("Highest subComp" + stack.peek().getHighestSubComplexity().toString());
                           // System.out.println("oldTopComplex: " + oldTopComplexity.toString());
                            if(Complexity.getHigherOrder(oldTopComplexity,
                                    stack.peek().getHighestSubComplexity()) == oldTopComplexity){

                           //     System.out.println("Setting higher order: " + oldTopComplexity.toString());
                                stack.peek().setHighestSubComplexity(oldTopComplexity);
                                System.out.println("Leaving BLOCK "+ s + ", updating BLOCK " + blockString.substring(0, blockString.length()-1));
                                System.out.println(String.format("%-25s%-45s%-20s",
                                        "BLOCK " + blockString.substring(0,blockString.length()-1) +" - ",
                                        "Block Complexity: " + stack.peek().getBlockComplexity(),
                                        "Highest Sub Complexity: " + stack.peek().getHighestSubComplexity()));
                                System.out.println("");
                            }else{
                                System.out.println("Leaving BLOCK "+ s + ", nothing to update");
                                System.out.println(String.format("%-25s%-45s%-20s",
                                        "BLOCK " + blockString.substring(0,blockString.length()-1) +" - ",
                                        "Block Complexity: " + stack.peek().getBlockComplexity(),
                                        "Highest Sub Complexity: " + stack.peek().getHighestSubComplexity()));
                                System.out.println();
                            }
                        }

                    }
                    for(CodeBlock.BLOCK_TYPES keyword : CodeBlock.BLOCK_TYPES.values()) {
                        if (currentLine.toLowerCase().contains(keyword.name().toLowerCase() + " ")) {

                            //   currentLevel++;
                            String word = keyword.name().toLowerCase();
                            if (currentLevel != 0 && currentLevel == blockNames.size() - 1) {
                                blockNames.set(currentLevel - 1, blockNames.get(currentLevel - 1) + 1);
                                //  System.out.println("Set from string");
                                blockString = blockString.substring(0, blockString.length() - 2);
                                blockString += blockNames.get(currentLevel - 1) + ".";
                            } else {
                                blockNames.add(1);
                                //   System.out.println("Add to string");
                                blockString += "1.";
                            }
                            System.out.println("Entering BLOCK " + blockString.substring(0, blockString.length() - 1) +
                                    " ~ " + keyword.name());

                            if (word.contains("for")) {
                                Complexity c;
                                if (currentLine.trim().contains("log_N")) {
                                    c = new Complexity(0, 1);
                                    //       System.out.println("Log");
                                } else {
                                    c = new Complexity(1, 0);
                                    //       System.out.println("N");
                                }
                                Complexity zero = new Complexity(0, 0);
                                CodeBlock cb = new CodeBlock(c, zero, word, null);
                                stack.push(cb);
                            } else if (word.contains("while")) {
                                //    System.out.println("while thing");
                                String s = currentLine.trim().substring(6);
                                String var = s.substring(0, s.indexOf(" "));
                                Complexity c = new Complexity(0, 0);
                                Complexity zero = new Complexity(0, 0);
                                CodeBlock cb = new CodeBlock(c, zero, word, var);
                                stack.push(cb);

                            } else {
                                // System.out.println("Entering "+ word +" block");
                                Complexity c = new Complexity(0, 0);
                                Complexity zero = new Complexity(0, 0);
                                CodeBlock cb = new CodeBlock(c, zero, word, null);
                                stack.push(cb);
                            }
                            System.out.println(String.format("%-25s%-45s%-20s",
                                    "BLOCK " + blockString.substring(0,blockString.length()-1) +" - ",
                                    "Block Complexity: " + stack.peek().getBlockComplexity(),
                                    "Highest Sub Complexity: " + stack.peek().getHighestSubComplexity()));
                            System.out.println("");
                            break;
                        } else if (!stack.isEmpty() && stack.peek().getLoopVariable() != null) {
                            if (currentLine.contains(stack.peek().getLoopVariable())) {
                                  System.out.println("Modifying BLOCK " + blockString.substring(0,blockString.length()-1)
                                  +" with loop variable");
                                Complexity c;
                                if (currentLine.contains("-=")) {
                                    c = new Complexity(1, 0);
                                    //        System.out.println("N power shit");
                                } else {
                                    c = new Complexity(0, 1);
                                    //    System.out.println("Log power shit");
                                }
                                stack.peek().setBlockComplexity(c);
                                System.out.println(String.format("%-25s%-45s%-20s",
                                        "BLOCK " + blockString.substring(0,blockString.length()-1) +" - ",
                                        "Block Complexity: " + stack.peek().getBlockComplexity(),
                                        "Highest Sub Complexity: " + stack.peek().getHighestSubComplexity()));
                                System.out.println("");
                                break;
                            }


                        }
                    }

                }else
                    continue;
            }
            while(stack.size() > 1){
              //  System.out.println("Reached end");
                CodeBlock oldTop = stack.pop();
                Complexity oldTopComplexity = Complexity.getTotalComplexity(oldTop.getBlockComplexity(),
                        oldTop.getHighestSubComplexity());
                if(Complexity.getHigherOrder(oldTopComplexity,
                        stack.peek().getHighestSubComplexity())==oldTopComplexity){
                    stack.peek().setHighestSubComplexity(oldTopComplexity);
                //    System.out.println("Setting higher order: " + oldTopComplexity.toString());
                }
            }
          //  System.out.println(stack.size());
            totalComplexity = Complexity.getTotalComplexity(stack.peek().getBlockComplexity(), stack.peek().getHighestSubComplexity());
            stack.pop();
            return totalComplexity;
        } catch (IOException e) {
            System.out.println("That file could not be found!");
            return null;
        }
    }

    /**
     * The main method drives the application. It asks whether users would like to input a file or quit.
     * If a file is inputted, its complexity is returned to the user.
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String operation = "";
        while(!operation.equalsIgnoreCase("quit")) {
            System.out.print("Please enter a filename or \'quit\' in order to terminate the program: ");
            operation = s.nextLine();
            if (operation.equalsIgnoreCase("quit")) {
                System.out.println("Program terminated.");
            } else if (operation.contains(".")) {
                Complexity c = null;
                try {
                    c = traceFile(operation.trim());
                }catch(IOException e){
                    System.out.println("That file could not be found!");
                }
                if(c != null)
                  System.out.println("The total complexity of " + operation + " is " + c.toString());
            } else {
                System.out.println("That option doesn't exist!(files should contain .py in their names)");
            }
        }
    }
}
