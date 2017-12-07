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
		
		Djikstra<String> graph = null;
		Scanner s = openInputFile();
		
		graph = readFromFile(s, graph);
		graph.showAdjTable();
		
		graph.applyDjikstra(new Vertex<String>("UCD") , new Vertex<String>("UCB"));
		
		/*
		Current: UCLA	distance to source: 6.0
        Current: UCB	distance to source: 1.0
        Current: UCR	distance to source: 8.0
        Current: UCD	distance to source: 0.0
        Current: UCSD	distance to source: 9.0
        Current: UCSC	distance to source: 3.0
        Current: UCSB	distance to source: 12.0
        Current: UCI	distance to source: 7.0
        Current: UCM	distance to source: 3.0
		 */
		
		graph.applyDjikstra(new Vertex<String>("UCI") , new Vertex<String>("UCB"));
		/*
		Current: UCLA 	distance to source: 7.0
        Current: UCB 	distance to source: 0.0
        Current: UCR	distance to source: 9.0
        Current: UCD	distance to source: 1.0
        Current: UCSD	distance to source: 10.0
        Current: UCSC	distance to source: 2.0
        Current: UCSB	distance to source: 7.0
        Current: UCI	distance to source: 8.0
        Current: UCM	distance to source: 4.0
		 */
		
		
		graph.applyDjikstra(new Vertex<String>("A building") , new Vertex<String>("UCB"));
	}
	
	public static Djikstra<String> readFromFile(Scanner s, Djikstra<String> g)
	{
		if(s == null)
			   return null;
		   else
		   {
			   g = new Djikstra<String>();
			   while(s.hasNextLine())
			   {
				   String arr[] = s.nextLine().split(",");
				   g.addEdge(arr[0], arr[1], Double.valueOf(arr[2]));
			   }
			   return (Djikstra<String>)g;
		   }
	}
}
