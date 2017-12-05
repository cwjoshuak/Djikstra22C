import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *	Driver.java
 *	Write a description of your file here
 *	
 *	Eclipse Neon.2 Release (4.6.2), macOS Sierra
 *	Java SE 8 [1.8.0_45]
 *	@author Joshua Kuan
 *	@version 4 Dec 2017
 */

public class Driver {
	public static Scanner userScanner = new Scanner(System.in);
	
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
        	File file= new File(filename);

        	try{
        		scanner = new Scanner(file);
        	}// end try
        	catch(FileNotFoundException fe){
        	   System.out.println("Can't open input file\n");
       	    return null; // array of 0 elements
        	} // end catch
        	return scanner;
	}
	
	public static void main(String args[])
	{
		// include a loop to allow solving the problem for a different graph more than once in one run
		
		// display a menu to the user for the following options:
		/*
		 * 1. read the graph from a text file where the file name is input from the user (use the openInputFile function)
		 * 2. add an edge to the graph
		 * 3. remove an edge from the graph
		 * 4. undo the previous removal(s) (you MUST use an ArrayStack or LinkedStack from HW#1, and you're allowed to add this as part of the Graph)
		 * 5. display the graph on the screen (give the choices of Depth-First traversal or Breadth-First traversal OR adjacency list of each vertex)
		 * 6. solve the problem for the graph, which would automatically display the results on the screen (NOTE: THERE MAY NOT BE A SOLUTION FOR A GIVEN GRAPH), and ask the user if the results should be saved to a text file (where the file name is input from the user)
		 * 7. write the graph to a text file (where the file name is input from the user) displaying each vertex and its adjacency list 
		 */
		
		Djikstra<String> test = new Djikstra<>();
		test.addEdge("Quickly", "De Anza", 5);
		test.addEdge("De Anza", "Apple", 7);
		test.addEdge("Apple", "Valley fair", 20);
		test.addEdge("Apple", "Quickly", 10);
		test.showAdjTable();
		
	}
}
