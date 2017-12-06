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
	   public void applyDjikstra(Vertex<E> src, Vertex<E> dst)
	   {
		   /*
		   
	       
		   Iterator< HashSet<Vertex<E>> > fIter;
		   
		   HashSet<Vertex<E>> singleton, vertSet, vertSetSrc = null, vertSetDst = null;
		   Edge<E> smallestEdge;
		   
		   ArrayList< Edge<E> > neighbours = new ArrayList< Edge<E> >();
		   , numVertsFound;
		   */
		   Iterator<Entry<E, Vertex<E>>> iter;
		   int k;
		   Vertex<E> vert;
		   //LinkedList< HashSet<Pair<Vertex<E>, Double>>> vertexSets = new LinkedList< HashSet<Pair<Vertex<E>, Double>>>();
		   HashMap<E, Vertex<E>> vertsInGraph;
		   HashSet<Pair<Vertex<E>, Double> > unvisitedSet;
		   
		   
		   vertsInGraph = vertexSet; // refer to Superclass' vertex set
		   if(!vertsInGraph.containsKey(src))
			   return;
		   unvisitVertices();
		   for (k = 0, iter = vertsInGraph.entrySet().iterator(); iter.hasNext(); k++)
		   {
			   vert = iter.next().getValue(); // grabs vertex
			   
			   if(vert.equals(src)) // if grabbed vertex is source
				   vert.setDistanceFromSrc(0); // set distance to 0
			   else
				   vert.setDistanceFromSrc(Vertex.INFINITY); // else set distance for now to infinity
			   
			   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> ite = vert.iterator(); // iterator for the current vertex's adj list
			   while(ite.hasNext())
			   {
				   Pair<Vertex<E>, Double> temp = ite.next().getValue(); // Pair which is vertex's neighbour and distance
				   if(!temp.first.isVisited())
					   unvisitedSet.add(temp); // adds all neighbour vertexes to this unvisited set
			   }
			   
			   
			   
			  // vertexSets.add(unvisitedSet);
		   }
		   
		   
		   
		   
		   
			   /*public void calculateShortestDistances() {
    // node 0 as source
    this.nodes[0].setDistanceFromSource(0);
    int nextNode = 0;
    // visit every node
    for (int i = 0; i < this.nodes.length; i++) {
      // loop around the edges of current node
      ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
      for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
        int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
        // only if not visited
        if (!this.nodes[neighbourIndex].isVisited()) {
          int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
          if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
            nodes[neighbourIndex].setDistanceFromSource(tentative);
          }
        }
      }
      // all neighbours checked so node visited
      nodes[nextNode].setVisited(true);
      // next node must be with shortest distance
      nextNode = getNodeShortestDistanced();
   }
  }
  // now we're going to implement this method in next part !
  private int getNodeShortestDistanced() {
    int storedNodeIndex = 0;
    int storedDist = Integer.MAX_VALUE;
    for (int i = 0; i < this.nodes.length; i++) {
      int currentDist = this.nodes[i].getDistanceFromSource();
      if (!this.nodes[i].isVisited() && currentDist < storedDist) {
        storedDist = currentDist;
        storedNodeIndex = i;
      }
    }
    return storedNodeIndex;
  } */
	      }
	   }
	   
}