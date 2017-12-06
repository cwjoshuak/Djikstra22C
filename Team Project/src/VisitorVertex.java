/**
 *	Visitor.java
 *	Write a description of your file here
 *	
 *	Eclipse Neon.2 Release (4.6.2), macOS Sierra
 *	Java SE 8 [1.8.0_45]
 *	@author Joshua Kuan
 *	@version 5 Dec 2017
 */

public class VisitorVertex<T> implements Visitor<T>
{
	@Override
	public void visit(T obj) {
		System.out.println(obj.toString());
	}
}