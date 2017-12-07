/**
 *	Djikstra.java
 *	Write a description of your file here
 *	
 *	Eclipse Neon.2 Release (4.6.2), macOS Sierra
 *	Java SE 8 [1.8.0_45]
 *	@author Joshua Kuan
 *	@version 4 Dec 2017
 */

import java.util.*;
import java.util.Map.Entry;

//--- Edge class ------------------------------------------------------
// THIS CLASS DOESN'T ALLOW OUTSIDE ACCESS TO ANY Vertex (ONLY TO DISPLAY OR COMPARE)
// REMEMBER, it's a REQUIREMENT for NO OUTSIDE ACCESS TO a Vertex!!!
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
}

public class Djikstra<E> extends Graph<E> {
	//private PriorityQueue< Edge<E> > edgeHeap; // will add Edges from largest to smallest cost
	
	//private LinkedList<Vertex<E>> solution;
	
	private LinkedQueue<Vertex<E>> solution;
	
	   public Djikstra ()
	   {
	      //edgeHeap = new PriorityQueue< Edge<E> >();
		   //solution.
	   }
	   
	   public void clear()
	   {
	     // edgeHeap.clear();
	   }
	   
	   // algorithms
	   public void applyDjikstra(Vertex<E> src, Vertex<E> dst)
	   {
		   
		   //Iterator<Entry<E, Vertex<E>>> iter;
		   
		   Vertex<E> vert;
		   //LinkedList< HashSet<Pair<Vertex<E>, Double>>> vertexSets = new LinkedList< HashSet<Pair<Vertex<E>, Double>>>();
		   HashMap<E, Vertex<E>> vertsInGraph;
		   //HashSet<Pair<Vertex<E>, Double> > unvisitedSet;
		   
		   vertsInGraph = (HashMap<E, Vertex<E>>)vertexSet.clone(); // refer to Superclass' vertex set
		   Vertex<E> source = null;
		   System.out.println(vertsInGraph.keySet());

		   LinkedQueue<Vertex<E>> lq = new LinkedQueue<>();
		   if(!vertsInGraph.containsKey(src.data))
			   return;
		   unvisitVertices();
		   
		   for (Iterator<Entry<E, Vertex<E>>> iter = vertsInGraph.entrySet().iterator(); iter.hasNext();)
		   {
			   vert = iter.next().getValue(); // grabs vertex
			   
			   System.out.print("Current: " +vert.data);
			   if(vert.equals(src)) // if grabbed vertex is source
			   {
				   vert.setDistanceFromSrc(0); // set distance to 0
				   source = vert;
				   lq.enqueue(source);
			   }
			   else
				   vert.setDistanceFromSrc(Vertex.INFINITY); // else set distance for now to infinity
			   System.out.println("distance to source: " +vert.getDistanceFromSrc());
		   }

		   vertsInGraph.remove(source.data);
		   if(source != null)
		   {
			   
			   System.out.println(vertsInGraph.size());
			   while(!vertsInGraph.isEmpty())
    		   {
    			   vert = lq.dequeue();
    			   vert.showAdjList();
    			   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> ite = vert.iterator(); // iterator for the current vertex's adj list
    			   for(; ite.hasNext(); )
    			   {
    				   System.out.print(ite.next().getValue().first.data +" ");
    			   }
    			   System.out.println();
    			   ite = vert.iterator();
    			   int count = 1;
    			   while(ite.hasNext())
    			   {
    				   
    				   System.out.println("count" +count++);
    				   System.out.println("Current Vert: " +vert.data +"Supposed vert distance: "+vert.distanceFromSrc);
    				   Pair<Vertex<E>, Double> temp = ite.next().getValue(); // Pair which is vertex's neighbour and distance
    				   //System.out.println("Current Vert: " +temp.first.data +"Supposed vert distance: "+temp.first.distanceFromSrc + temp.first.visited);
    				   if(temp.first.equals(vert))
    				   		temp.first.visit();
    				   else
    					   temp.first.unvisit();
    				   
    				   if(!temp.first.equals(vert))//!temp.first.isVisited())
    				   {
    					   System.out.println(vertsInGraph.size());
    					   //System.out.println("I arrived here");
    					   lq.enqueue(temp.first);
        				   System.out.println("Neighbour Vert: " +temp.first.data +"Before Supposed vert distance: "+temp.first.distanceFromSrc);
        				   
        				   //vertsInGraph.remove(temp.first.data);
        				   
        				   double dist = temp.second + vert.distanceFromSrc;
        				   if(dist < temp.first.distanceFromSrc)
        					   temp.first.setDistanceFromSrc(dist);
        				   
        				   System.out.println("vertex: " +temp.first.data +"After supposed vert distance from source: " +temp.first.distanceFromSrc);
        				   temp.first.visit();
    				   }
    			   }
    			   vertsInGraph.remove(vert.data);
    		   }
			   vertsInGraph = (HashMap<E, Vertex<E>>)vertexSet.clone();
			   System.out.println(vertexSet.isEmpty());
			   for (Iterator<Entry<E, Vertex<E>>> iter = vertsInGraph.entrySet().iterator(); iter.hasNext();)
			   {
				   vert = iter.next().getValue(); // grabs vertex
				   
				   System.out.print("Current: " +vert.data);
				   
				   System.out.println("\tdistance to source: " +vert.getDistanceFromSrc());
			   }
		   }
      }
   }
	   