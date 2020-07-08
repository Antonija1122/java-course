package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class creates documentNode function implementation.
 * 
 * @author antonija
 *
 */
public class SmartScriptEngine {

	/**
	 * Root node of this node structure
	 */
	private DocumentNode documentNode;
	/**
	 * private requestContext for header
	 */
	private RequestContext requestContext;
	/**
	 * private multistack for forLoopNode
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * private implementation of visitor for this document
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("requestContext's write method failed: " + e.getMessage());
			}
		}

		/**
		 * ForLoopNode visit it pushes onto object stack new instance of variable
		 * defined in ForLoopNode and initializes it with initial value. As long as this
		 * value is less then or equal to end value it makes one pass through
		 * ForLoopNode's direct children and call accept on them.
		 * 
		 * @param node input ForLoopNode
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {

			String[] forLoop = node.getText().trim().split("\\s+");
			String counter = forLoop[0];
			Integer initialValue = Integer.parseInt(forLoop[1]);
			Integer endValue = Integer.parseInt(forLoop[2]);
			Integer increment = Integer.parseInt(forLoop[3]);
			int len = node.numberOfChildren();
			// Push new instance and initialize it with initial value
			multistack.push(counter, new ValueWrapper(initialValue));

			while ((Integer) (multistack.peek(counter).getValue()) <= endValue) {
				for (int index = 0; index < len; index++) {
					node.getChild(index).accept(this);
				}
				multistack.peek(counter)
						.setValue(((Integer) multistack.peek(counter).getValue()).intValue() + increment);
			}

			multistack.pop(counter);

		}

		/**
		 * {@inheritDoc} EchoNode visit creates a temporary stack of objects. Constant
		 * elements are pushed onto stack. If elements are functions or operators method
		 * pops required number of arguments from temporary stack, applies the function
		 * and pushes the result back onto the temporary stack.
		 * 
		 */
		@Override
		public void visitEchoNode(EchoNode node) {

			Stack<Object> temporary = new Stack<>();
			Element[] elements = node.getElements();

			for (Element ele : elements) {
				if (ele instanceof ElementConstantDouble) {

					temporary.push(((ElementConstantDouble) ele).getValue());

				} else if (ele instanceof ElementConstantInteger) {

					temporary.push(((ElementConstantInteger) ele).getValue());

				} else if (ele instanceof ElementString) {

					temporary.push(((ElementString) ele).getValue());

				} else if (ele instanceof ElementVariable) {

					String name = ((ElementVariable) ele).getName();
					ValueWrapper currentValue = multistack.peek(name);
					Objects.requireNonNull(currentValue);
					temporary.push(currentValue.getValue());

				} else if (ele instanceof ElementOperator) {

					String op = ((ElementOperator) ele).getSymbol();
					doOperation(op, temporary);

				} else if (ele instanceof ElementFunction) {

					String funName = ((ElementFunction) ele).getName();
					calculateFunction(funName, temporary);

				}

			}

			Stack<Object> temporaryAssistent = new Stack<Object>();
			while (!temporary.isEmpty()) {
				temporaryAssistent.push(temporary.pop());
			}
			while (!temporaryAssistent.isEmpty()) {
				try {
					requestContext.write(String.valueOf(temporaryAssistent.pop()));
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Method applies function to peek stack elements and pushes result on stack
		 * 
		 * @param temporary echo temporary stack
		 * @param funName   input function
		 */
		private void calculateFunction(String funName, Stack<Object> temporary) {
			Object x;
			Object y;
			String value;
			String name;
			String f;

			switch (funName) {
			case "sin":
				x = temporary.pop();
				temporary.push((x instanceof Double) ? Math.sin(((Double) x) * Math.PI / 180)
						: Math.sin(((Integer) x) * Math.PI / 180));
				break;
			case "decfmt":
				f = String.valueOf(temporary.pop());
				x = temporary.pop();
				NumberFormat formatter = new DecimalFormat(f);
				if (x instanceof String) {
					temporary.push(formatter.format(Double.parseDouble((String) x)));
				} else {
					temporary.push(formatter.format((Double) x));
				}
				break;
			case "dup":
				temporary.push(temporary.peek());
			case "swap":
				x = temporary.pop();
				y = temporary.pop();
				temporary.push(x);
				temporary.push(y);
				break;
			case "setMimeType":
				requestContext.setMimeType((String) temporary.pop());
				break;
			case "paramGet":
				x = temporary.pop();
				name = String.valueOf(temporary.pop());
				value = requestContext.getParameter(name);
				temporary.push(value == null ? x : value);
				break;
			case "pparamGet":
				x = temporary.pop();
				name = String.valueOf(temporary.pop());
				value = requestContext.getPersistentParameter(name);
				temporary.push(value == null ? x : value);
				break;
			case "pparamSet":
				name = String.valueOf(temporary.pop());
				value = String.valueOf(temporary.pop());
				requestContext.setPersistentParameter(name, value);
				break;
			case "pparamDel":
				name = String.valueOf(temporary.pop());
				requestContext.removePersistentParameter(name);
				break;
			case "tparamGet":
				x = temporary.pop();
				name = String.valueOf(temporary.pop());
				value = requestContext.getTemporaryParameter(name);
				temporary.push(value == null ? x : value);
				break;
			case "tparamSet":
				name = String.valueOf(temporary.pop());
				value = String.valueOf(temporary.pop());
				requestContext.setTemporaryParameter(name, value);
				break;
			case "tparamDel":
				name = String.valueOf(temporary.pop());
				requestContext.removeTemporaryParameter(name);
				break;
			}

		}

		/**
		 * Method applies operation to peek stack elements and pushes result on stack
		 * 
		 * @param temporary stack
		 * @param op        input operator
		 */
		private void doOperation(String op, Stack<Object> temporary) {

			ValueWrapper firstArg = new ValueWrapper(temporary.pop());
			ValueWrapper secondArg = new ValueWrapper(temporary.pop());

			switch (op) {

			case "+":
				firstArg.add(secondArg.getValue());
				temporary.push(firstArg.getValue());
				break;
			case "-":
				firstArg.subtract(secondArg.getValue());
				temporary.push(firstArg.getValue());
				break;
			case "*":
				firstArg.multiply(secondArg.getValue());
				temporary.push(firstArg.getValue());
				break;
			default:
				firstArg.divide(secondArg.getValue());
				temporary.push(firstArg.getValue());

			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int index = 0, len = node.numberOfChildren() - 1; index <= len; index++) {
				node.getChild(index).accept(this);
			}

		}

	};
	/**
	 * Public constructor sets private values to input values
	 * 
	 * @param documentNode   input documentNode
	 * @param requestContext input requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	/**
	 * This method calls method for root document
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
