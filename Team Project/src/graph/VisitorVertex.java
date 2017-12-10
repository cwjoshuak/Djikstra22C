package graph;

/**
 *	Visitor.java
 *	Visitor that prints visited vertex to console
 *	
 *	Java SE 8 [1.8.0_45]
 *	@version 10 Dec 2017
 */

public class VisitorVertex<T> implements Visitor<T>
{
	@Override
	public void visit(T obj) {
		System.out.println(obj.toString());
	}
}