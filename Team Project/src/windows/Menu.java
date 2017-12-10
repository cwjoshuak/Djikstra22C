package windows;

/* @author ShuWen Zhu */
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

import linkedData.*;
import graph.*;

public class Menu extends JFrame{
	Dijkstra<String> graph = null;
	LinkedStack<String> historyS,historyE;
	LinkedStack<Double> historyC;
	LinkedStack<Integer> prevaction; //1 for add route and 2 for remove route 0 for no action performed
	Vector<String> location_names = new Vector<String>();
	String solution; //store route found
	JButton submit_file, save_graph, breathF, deepF, adjL, search_route, add_edge, remove_edge, undo, save_route;
	JPanel msgP, vertexP, displayP;
	JLabel stats, vertxmsg, results;
	JTextField filename, cost;
	JComboBox<String> start_location, end_location;
	JTextArea  texts;
	JScrollPane scroll;
	MessageConsole console;
	public Menu() {
		//window setup
		setTitle("Welcome!");
		setSize(500,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// panel 1
		{
			//Objects for input file read
			submit_file = new JButton("Open");
			submit_file.setBounds(190, 50, 90, 30);
			filename = new JTextField("Please enter Filename");
			filename.setBounds(40,50,150,30);
			filename.addMouseListener(new MouseAction());
			save_graph = new JButton("Save Map");
			save_graph.setBounds(300, 50, 90, 30);
			msgP = new JPanel();
			stats = new JLabel("");
			stats.setBounds(40,20,150,30);
			
			//display object
			breathF = new JButton("Breadth First");
			deepF = new JButton("Depth First");
			adjL = new JButton("Adj List");
			
			breathF.setBounds(40,100, 110, 30);
			deepF.setBounds(180,100, 110, 30);
			adjL.setBounds(320,100, 110, 30);
			
			breathF.setEnabled(false);
			deepF.setEnabled(false);
			adjL.setEnabled(false);
			save_graph.setVisible(false);
			
			breathF.addActionListener(new p1Listener());
			deepF.addActionListener(new p1Listener());
			adjL.addActionListener(new p1Listener());
			save_graph.addActionListener(new p1Listener());
			
			//action for input file read
			submit_file.addActionListener(new p1Listener());
			
			//Panel set for input file read
			msgP.setBounds(0, 0, 500, 150);
			msgP.add(filename);
			msgP.add(submit_file);
			msgP.add(stats);
			msgP.add(breathF);
			msgP.add(deepF);
			msgP.add(adjL);
			msgP.add(save_graph);
		}
		
		// panel 2
		{
			//objects for vertex panel
			location_names.add(" ");
			cost = new JTextField("Edge Cost");
			vertexP = new JPanel();
			vertxmsg = new JLabel("Start point:                                                            End point:");
			start_location = new JComboBox<String>(location_names);
			end_location = new JComboBox<String>(location_names);
			search_route = new JButton("Search!");
			add_edge = new JButton("Add a route");
			remove_edge = new JButton("Remove a route");
			undo = new JButton("Undo");
			save_route = new JButton("Save Solution");
			
			//action for vertex method
			search_route.addActionListener(new p2Listener());
			add_edge.addActionListener(new p2Listener());
			remove_edge.addActionListener(new p2Listener());
			undo.addActionListener(new p2Listener());
			save_route.addActionListener(new p2Listener());
			cost.addMouseListener(new MouseAction());
			
			vertxmsg.setBounds(40, 0, 400, 30);
			start_location.setBounds(40,50,150,30);
			end_location.setBounds(280,50,150,30);
			search_route.setBounds(350, 100, 80, 30);
			add_edge.setBounds(40, 140, 100, 30);
			remove_edge.setBounds(170, 140, 150, 30);
			undo.setBounds(350, 140, 80, 30);
			cost.setBounds(40, 100, 80, 30);
			save_route.setBounds(290,180,140,30);
			
			cost.setEnabled(false);
			search_route.setEnabled(false);
			add_edge.setEnabled(false);
			remove_edge.setEnabled(false);
			undo.setEnabled(false);
			save_route.setEnabled(false);
			
			//Panel set for vertex
			vertexP.setBounds(0, 150, 500, 220);
			vertexP.add(vertxmsg);
			vertexP.add(start_location);
			vertexP.add(end_location);
			vertexP.add(search_route);
			vertexP.add(cost);
			vertexP.add(add_edge);
			vertexP.add(remove_edge);
			vertexP.add(undo);
			vertexP.add(save_route);
		}
		
		{
			//objects for display panel
			displayP = new JPanel();
			results = new JLabel("");
			texts = new JTextArea();
			scroll = new JScrollPane(texts);
			console = new MessageConsole(texts);
			console.redirectOut();
			console.redirectErr(Color.RED,null);
			console.setMessageLines(100);
			
			displayP.setBounds(0, 360, 500, 300);
			results.setBounds(30, 0, 400, 95);
			scroll.setBounds(30, 100, 400, 200);
			
			displayP.add(results);
			displayP.add(scroll);
		}
		
		//main panel set
		setLayout(null);
		msgP.setLayout(null);
		vertexP.setLayout(null);
		displayP.setLayout(null);
		add(msgP);
		add(vertexP);
		add(displayP);

		setVisible(true);
	}
	void reset_history(LinkedStack<?> st)
	{
		while (!st.isEmpty())
			st.pop();
	}
	
	Scanner openInputFile(String _filename)
	{
        Scanner scanner=null;
        if (_filename != null)
        {
	    	File file= new File(_filename);
	
	    	try{
	    		scanner = new Scanner(file);
	    	}// end try
	    	catch(FileNotFoundException fe){
	    	   stats.setText("Can't open input file\n");
	    	   filename.setText("Please enter Filename");
	    	   save_graph.setVisible(graph!=null);
	   	    return null; // array of 0 elements
	    	} // end catch
        }
        save_graph.setVisible(graph!=null);
    	return scanner;
	}
	
	// read from input file
	public void readFromFile(Scanner s)
	{
		if(s == null)
			   return;
	   else
	   {
		   graph = new Dijkstra<String>();
		   location_names.clear();
		   while(s.hasNextLine())
		   {
			   String buffer = s.nextLine();
			   String arr[] = new String[3];
			   if(buffer.isEmpty() && s.hasNextLine())
				   buffer = s.nextLine();
			   
			   if(!buffer.isEmpty())
				   arr = buffer.split(",");
			   else break;
			   
			   if (!location_names.contains(arr[0]))
			   {
				   location_names.add(arr[0]);
			   }
			   if (!location_names.contains(arr[1]))
			   {
				   location_names.add(arr[1]);
			   }
			   graph.addEdge(arr[0], arr[1], Double.valueOf(arr[2]));
		   }
		   s.close();
		   historyS = new LinkedStack<String>();historyE = new LinkedStack<String>();
		   historyC = new LinkedStack<Double>();
		   prevaction = new LinkedStack<Integer>();
		   breathF.setEnabled(true);
		   deepF.setEnabled(true);
		   adjL.setEnabled(true);
		   search_route.setEnabled(true);
		   add_edge.setEnabled(true);
		   remove_edge.setEnabled(true);
		   cost.setEnabled(true);
		   save_graph.setVisible(true);
		   start_location.setSelectedItem(location_names.elementAt(0));
		   end_location.setSelectedItem(location_names.elementAt(0));
		   reset_history(historyS);
			reset_history(historyC);
			reset_history(historyE);
			reset_history(prevaction);
			undo.setEnabled(false);
		   stats.setText("Read!");
	   }
	}
	
	class p1Listener implements ActionListener {
		public void actionPerformed(ActionEvent b)	
		{
			if (b.getActionCommand().equals("Open"))	
			{
				String infilename = filename.getText();
				Scanner infile = openInputFile(infilename);
				readFromFile(infile);
			}
			else if (b.getActionCommand().equals("Breadth First"))	
			{
				VisitorVertex<String> vis = new VisitorVertex<String>();
				System.out.println("Breadth-first Traversal:");
				graph.breadthFirstTraversal((String)start_location.getSelectedItem(), vis);
				System.out.println();
			}
			else if (b.getActionCommand().equals("Depth First"))	
			{
				VisitorVertex<String> vis = new VisitorVertex<String>();
				System.out.println("Depth-first Traversal:");
				graph.depthFirstTraversal((String)start_location.getSelectedItem(), vis);
				System.out.println();
			}
			else if (b.getActionCommand().equals("Adj List"))	
			{
				graph.showAdjTable();
			}
			else if (b.getActionCommand().equals("Save Map"))	
			{
				String outfilename = (String)JOptionPane.showInputDialog(
	                    msgP,
	                    "Please enter the filename:\n",
	                    "Enter file name",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "SavedMap.txt") + "";
				if (outfilename.equals(null + ""))
					return;
				if(!outfilename.equals("" + "") ) 
				{
					File outfile = new File(outfilename);
					PrintWriter pw;
					try {
						pw = new PrintWriter(outfile);
						graph.printToFile(pw);
						pw.close();
						JOptionPane.showMessageDialog(msgP,
							    "Saved!");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(msgP,
					    "Invalid filename!");
			}
		}
	}
	//listener for vertexP
	class p2Listener implements ActionListener {
		public void actionPerformed(ActionEvent b)	
		{
			if (b.getActionCommand().equals("Search!"))
			{
				String sartLocation = (String)start_location.getSelectedItem();
				String endLocation = (String)end_location.getSelectedItem();
				if (!(sartLocation.equals(endLocation)))
				{
					graph._applyDijkstra(sartLocation);
					solution = graph._getSolution(endLocation);
					results.setText("<html><pre>Looking for path between "+sartLocation+
							" and "+ endLocation +"</pre><html>" + 
							String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 290, solution));
					save_route.setEnabled(true);
				}
				else
				{
					results.setText("Unable to find route! Start & End pos is the same!");
					save_route.setEnabled(false);
				}
			}
			else if (b.getActionCommand().equals("Add a route"))
			{
				double time_cost = 0;
				try
				{
					time_cost = Double.parseDouble(cost.getText());
				}
				catch(NumberFormatException e)
				{
				  //not a double
					results.setText("invalid cost input, please enter numbers only!");
					return;
				}
				String sartLocation = (String)start_location.getSelectedItem();
				String endLocation = (String)end_location.getSelectedItem();
				
				if (!(sartLocation.equals(endLocation)) && time_cost > 0)
				{
					if (!graph.existsPair(sartLocation, endLocation))
					{
						historyS.push(sartLocation);
						historyE.push(endLocation);
						historyC.push(time_cost);
						graph.addEdge(sartLocation, endLocation, time_cost);
						graph.showAdjTable();
						prevaction.push(1);
						results.setText("Added edge between " +sartLocation +" --(" +time_cost +")--> " +endLocation);
						cost.setText("Hour cost");
						undo.setEnabled(true);
					}
					else
						results.setText("Unable to add route! Start & End pos pair already exists!");
				}
				else
				{
					String err = "";
					if (sartLocation.equals(endLocation))
						err = "Unable to add route! error in Start/End pos! ";
					if (time_cost <= 0)
						err += "Positive cost value only!";
					
					results.setText(err);
				}
			}
			else if (b.getActionCommand().equals("Remove a route"))
			{
				String sartLocation = (String)start_location.getSelectedItem();
				String endLocation = (String)end_location.getSelectedItem();
				if (!(sartLocation.equals(endLocation)))
				{
					if (graph.existsPair(sartLocation, endLocation))
					{
						historyS.push(sartLocation);
						historyE.push(endLocation);
						historyC.push(graph.getEdgeCost(sartLocation, endLocation));
						graph.remove(sartLocation, endLocation);
						results.setText("Removed edge between "+sartLocation +" and " +endLocation);
						prevaction.push(2);
						undo.setEnabled(true);
					}
					else
					{
						results.setText("Unable to remove. Start & End pair does not exist");
					}
				}
				else
				{
					results.setText("Unable to remove. error in Start/End pos");
				}
				graph.showAdjTable();
			}
			else if (b.getActionCommand().equals("Undo"))
			{
				if (prevaction.size() > 0 && prevaction.peek() == 1)
				{
					graph.remove(historyS.peek(), historyE.peek());
					historyS.pop();
					historyE.pop();
					historyC.pop();
					results.setText("Add Undone");
					prevaction.pop();
					undo.setEnabled(!prevaction.isEmpty());
				}
				else if (prevaction.size() > 0 && prevaction.peek() == 2)
				{
					graph.addEdge(historyS.peek(), historyE.peek(), historyC.peek());
					historyS.pop();
					historyE.pop();
					historyC.pop();
					results.setText("Remove Undone");
					prevaction.pop();
					undo.setEnabled(!prevaction.isEmpty());
				}
				graph.showAdjTable();
				System.out.println(prevaction.size()+" undo(s) remain");
			}
			else if (b.getActionCommand().equals("Save Solution"))
			{
				String outfilename = (String)JOptionPane.showInputDialog(
	                    msgP,
	                    "Please enter the filename:\n",
	                    "Enter file name",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "Solution.txt") + "";
				if (outfilename.equals(null + ""))
					return;
				if(!outfilename.equals("" + "") ) 
				{
					File outfile = new File(outfilename);
					try {
						PrintWriter pw = new PrintWriter(outfile);
						pw.println(solution);
						pw.close();
						JOptionPane.showMessageDialog(vertexP,
							    "Saved!");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(msgP,
					    "Invalid filename!");
				
			}
		}
	}


	class MouseAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource()==filename)
			{
					filename.setText("");
					stats.setText("");
			}
			if (arg0.getSource()==cost)
			{
				cost.setText("");
			}
			else
			{
				cost.setText("Hour cost");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
	}
}
