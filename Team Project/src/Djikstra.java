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
	private PriorityQueue< Edge<E> > edgeHeap; // will add Edges from largest to smallest cost

	   public Djikstra ()
	   {
	      edgeHeap = new PriorityQueue< Edge<E> >();
	   }

	   public void clear()
	   {
	      edgeHeap.clear();
	   }
	   
	   // algorithms
	   
	   
}