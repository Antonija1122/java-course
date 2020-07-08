package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Public interface for Node visitor.
 * 
 * @author antonija
 *
 */
public interface INodeVisitor {
	/**
	 * TextNode visit does action for text node.
	 * 
	 * @param node input TextNode
	 */
	public void visitTextNode(TextNode node);

	/**
	 * ForLoopNode visit does visit to all children nodes.
	 * 
	 * @param node input ForLoopNode
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * EchoNode visit does action for echo node.
	 * 
	 * @param node input EchoNode
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * DocumentNode visit does visit to all children nodes.
	 * 
	 * @param node input DocumentNode
	 */
	public void visitDocumentNode(DocumentNode node);

}
