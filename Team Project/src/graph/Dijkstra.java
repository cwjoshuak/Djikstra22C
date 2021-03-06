package graph;

import java.util.*;
import java.util.Map.Entry;
import linkedData.*;

//--- Edge class ------------------------------------------------------
// THIS CLASS DOESN'T ALLOW OUTSIDE ACCESS TO ANY Vertex (ONLY TO DISPLAMENT for NO OUTSIDE ACCESS TO a Vertex!!!
class Edge<E> implements Comparable< Edge<E> >
{
	 Vertex<E> source, dest;
	 double cost;

	 Edge( Vertex<E> src, Vertex<E> dst, Double cst)
	 {
	    source = src;
	    dest = dst;
	    cost = cst;
	 }

	 Edge( Vertex<E> src, Vertex<E> dst, Integer cst)
	 {
	    this (src, dst, cst.doubleValue());
	 }

	 Edge()
	 {
	    this(null, null, 1.);
	 }

	 public String toString(){ return "Edge: "+source.getData() + " to " + dest.getData()
			 + ", distance: " + cost;
	 }

	 public int compareTo( Edge<E> rhs )
	 {
	    return (cost < rhs.cost? -1 : cost > rhs.cost? 1 : 0);
	 }
} // end Edge class


public class Dijkstra<E> extends Graph<E> {
	
	private Map<Vertex<E>, Vertex<E>> sol = new HashMap<Vertex<E>, Vertex<E>>(); // holds solution from src to any dst 
	// contains start -> end vertex in K,V pair, K:src V:null
	
	   public Dijkstra ()
	   {
		   super();
		   sol = new HashMap<Vertex<E>,Vertex<E>>();
	   }
	   
	   /* ShuWen Zhu
	    * Method call will apply Dijkstra's algorithm and save solutions to sol
	    */
	   public void _applyDijkstra(E src)
	   {
		   Vertex<E> newS = new Vertex<E>(src);
		   applyDijkstra(newS);
	   }
	   public void clear()
	   {
	      sol.clear();
	   }
	   
	   /* @author ShuWen Zhu
	    * Helper method for Menu
	    */
	   public String _getSolution(E dst)
	   {
		   Vertex<E> newD = new Vertex<E>(dst);
		   return getSolution(newD);
	   }
	   
	   /* Joshua Kuan
	    * returns solution for a particular destination
	    * PRE-REQUISITE: SHOULD HAVE CALLED applyDijkstra(Vertex<E> src) FIRST
	    */
	   public String getSolution(Vertex<E> dst)
	   {
		   StringBuilder sb = new StringBuilder("");
		   if(dst == null)
			   return sb.toString();
		   
		   Vertex<E> temp = vertexSet.get(dst.data);
		   LinkedStack<Vertex<E>> stack = new LinkedStack<>();
		   
		   stack.push(temp);
		   while(temp != null)
		   {
			   temp = sol.get(temp);
			   if(temp== null)
				   break;
			   stack.push(temp);
		   }
		   
		   while(!stack.isEmpty())
		   {
			   temp = stack.pop();
			   sb.append(temp.data);
			   if(stack.size() != 0)
			   {
				   double cost = temp.adjList.get(stack.peek().data).second;
				   sb.append(" --(" +cost +")--> ");
			   }
		   }
		   sb.append("\nTotal Cost: " +temp.distanceFromSrc);
		   return sb.toString();
	   }
	   
	   /* Joshua Kuan
	    * applies Dijkstra's algorithm on graph starting from given source vertex
	    */
	   public void applyDijkstra(Vertex<E> src)
	   {
		   sol.clear(); // clears solution map
		   Vertex<E> vert; // temporary variable to hold current vertex for which the algorithm is using
		   HashMap<E, Vertex<E>> vertsInGraph = (HashMap<E, Vertex<E>>)vertexSet.clone(); // clones Superclass' vertex set
		   Vertex<E> source = null; // temporary variable to hold algorithm's source vertex
		   LinkedQueue<Vertex<E>> lq = new LinkedQueue<>(); // holds all adjacent/neighbour vertices for vert
		   if(!vertsInGraph.containsKey(src.data)) // if graph does not contain start, then return
			   return;
		   unvisitVertices(); // makes all vertices unvisited
		   
		   /* This loop goes through each vertice and sets the distance from source to INFINITY or 0 if source */
		   for (Iterator<Entry<E, Vertex<E>>> iter = vertsInGraph.entrySet().iterator(); iter.hasNext();)
		   {
			   vert = iter.next().getValue(); // grabs vertex
			   
			   if(vert.equals(src)) // if grabbed vertex is source
			   {
				   vert.setDistanceFromSrc(0); // set distance to 0
				   source = vert; // assigns reference 'location' to source
				   lq.enqueue(source); // enqueues to list for later use
			   }
			   else
				   vert.setDistanceFromSrc(Vertex.INFINITY); // else set distance for now to infinity
		   } // end for

		   vertsInGraph.remove(source.data); // removes source node from vertices in graph (basically counter-1 or removing it from the set of "unvisited")
		   if(source != null) //
		   {
			   while(!vertsInGraph.isEmpty()) // for all remaining vertices, do the following
    		   {
    			   vert = lq.dequeue(); // dequeues vertex from front of queue
    			   
    			   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> ite = vert.iterator(); // iterator for the current vertex's adj list
    			   
    			   while(ite.hasNext()) // iterates through all neighbours of current vertex
    			   {
    				   Pair<Vertex<E>, Double> temp = ite.next().getValue(); // Pair which is vertex's neighbour and distance
    				   
    				   if(!temp.first.equals(vert)) // since this is an undirected graph, ignore itself
    				   {
    					   lq.enqueue(temp.first); // enqueues neighbour of current vertex
        				   double dist = temp.second + vert.distanceFromSrc; // calculates distance between source and vertex
        				   if(dist < temp.first.distanceFromSrc)
        					   temp.first.setDistanceFromSrc(dist);
        				   temp.first.visit(); // visits
    				   }
    			   }
    			   vertsInGraph.remove(vert.data); // removes current vertex from being iterated again
    		   }
			   
			   vertsInGraph = (HashMap<E, Vertex<E>>)vertexSet.clone();
			   for (Iterator<Entry<E, Vertex<E>>> iter = vertsInGraph.entrySet().iterator(); iter.hasNext();)
			   {
				   vert = iter.next().getValue(); // grabs vertex
				   if(vert.distanceFromSrc == 0) // if grabbed vertex is source
					   sol.put(vert, null);
				   else
				   {
					   Vertex<E> lowest = null;
					   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> ite = vert.iterator();
					   Map.Entry<E, Pair<Vertex<E>, Double>> holder = null;
					   while(ite.hasNext())
					   {
						   holder = ite.next();
						   if(vert.distanceFromSrc - (holder.getValue().first.getDistanceFromSrc()+holder.getValue().second) == 0)
							   lowest = holder.getValue().first;
					   }
						   sol.put(vert, lowest);
				   } // end if-else
			   } // end for
		   }
      }// end method
   } // end class
	   