package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * base class for all graph nodes
 * @author antonija
 *
 */
public class Node {
	
	/**
	 * private ArrayIndexedCollection children 
	 */
	private ArrayIndexedCollection children;
	/**
	 *  adds given child to an internally managed collection of children
	 */
	public void addChildNode(Node child) {
		if(child == null) {
			throw new IllegalArgumentException();
		}
		if(children==null) {
			children=new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	public int numberOfChildren() {
		if(children==null) {
			return 0;
		}
		return children.size();
	}
	
	public Node getChild(int index) {
		if(index<0 || index>=children.size()) {
			throw new IndexOutOfBoundsException("index out of range.");
		}
		Object newNode=children.get(index);
		return (Node) newNode;
	}

	
	public String getText() {
		return null;
	}

}
