package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
/**
 * This is a demo program TreeWriter that implements private simple INodeVisitor
 * and visits all nodes parsed with SmartScriptParser from input file
 * 
 * @author antonija
 *
 */
public class TreeWriter {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Single argument expected - file name");
			return;
		}


		String fileString = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
		
		SmartScriptParser parser = new SmartScriptParser(fileString);

		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);

	}

	/**
	 * Simple INodeVisitor for printing nodes elements to console
	 * 
	 * @author antonija
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {

			System.out.printf("{$ FOR" + node.getText() + " $}");
			for (int index = 0, len=node.numberOfChildren() - 1; index <=len; index++) {
				node.getChild(index).accept(this);
			}
			System.out.printf("{$ END $}");

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf("{$= " + node.getText() + " $}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int index = 0, len=node.numberOfChildren() - 1; index <=len; index++) {
				node.getChild(index).accept(this);
			}

		}

	}
}
