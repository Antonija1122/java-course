package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Calculator extends JFrame and uses CalcLayout to implement CalcModelImpl in a
 * frame to interact with user
 * 
 * @author antonija
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 4317531828516724459L;

	/**
	 * container for this calculator
	 */
	private Container cp;

	/**
	 * model of this calculator
	 */
	CalcModelImpl model;

	/**
	 * stack for push and pop operations
	 */
	Stack<Double> stack = new Stack<>();

	/**
	 * display of this calculator
	 */
	JLabel ekran;
	/**
	 * JCheckBox for setting normal or inverted versions of operations
	 */
	JCheckBox inv;

	List<ButtonOperator> buttons = new ArrayList<>();

	/**
	 * Public constructor sets size and initiates gui for this calculator
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		initGUI();
	}

	/**
	 * This method adds buttons to calculator and adds their actions to calculate
	 * results of operations that user selected
	 */
	private void initGUI() {

		cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		model = new CalcModelImpl();
		JLabel ekran = l("0");
		this.ekran = ekran;
		ekran.setHorizontalAlignment(JTextField.RIGHT);
		ekran.setFont(ekran.getFont().deriveFont(20f));
		model.addCalcValueListener(model -> ekran.setText(model.toString()));

		cp.add(ekran, new RCPosition(1, 1));

		setButtonNumbers();
		addOperators();
		addInversableOperations();

		this.inv = new JCheckBox("Inv");
		inv.addActionListener(e -> {
			changeGui(inv.isSelected());

		});
		cp.add(inv, new RCPosition(5, 7));

	}

	private void changeGui(boolean selected) {

		if (selected) {
			for(ButtonOperator b: buttons) {
				b.setInvName();
			}
		} else {
			for(ButtonOperator b: buttons) {
				b.setName();
			}
		}

	}

	/**
	 * This method adds buttons and their actions depending on state of inv box. If
	 * box is selected actions are performing inverted operations.
	 * 
	 * @param position position of input button
	 * @param action   that this button performs
	 * @param inverted inverted action
	 * @param name     of operation
	 */
	private void setInversed(RCPosition position, Function<Double, Double> action, Function<Double, Double> inverted,
			String name, String invName) {

		ActionListener actionAdd = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (inv.isSelected()) {
					model.setValue(inverted.apply(model.getValue()));
				} else {
					model.setValue(action.apply(model.getValue()));
				}
			}
		};
		ButtonOperator button = new ButtonOperator(name, invName, actionAdd);
		cp.add(button, position);
		buttons.add(button);

	}

	/**
	 * This method adds all invertible operations to this calculator
	 */
	private void addInversableOperations() {

		setInversed(new RCPosition(2, 1), e -> Math.pow(e, -1), e -> Math.pow(e, -1), "1/x", "x^(1/n)");
		setInversed(new RCPosition(3, 1), e -> Math.pow(10, e), Math::log10, "log", "10^x");
		setInversed(new RCPosition(4, 1), Math::log, Math::exp, "ln", "e^x");
		setInversed(new RCPosition(2, 2), Math::sin, Math::asin, "sin", "arcsin");
		setInversed(new RCPosition(3, 2), Math::cos, Math::acos, "cos", "arccos");
		setInversed(new RCPosition(4, 2), Math::tan, Math::atan, "tan", "arctan");
		setInversed(new RCPosition(5, 2), e -> 1 / Math.tan(e), e -> Math.atan(1 / e), "ctan", "arcctg");

		ActionListener multiply = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setActiveOperand(model.getValue());
				if (inv.isSelected()) {
					DoubleBinaryOperator d = (x, y) -> Math.pow(x, 1 / y);
					model.setPendingBinaryOperation(d);
				} else {
					DoubleBinaryOperator d = (x, y) -> Math.pow(x, y);
					model.setPendingBinaryOperation(d);
				}
				model.clear();
			}
		};
		
		ButtonOperator button=new ButtonOperator("x^n", "x^(1/n)", multiply);
		cp.add(button, new RCPosition(5, 1));
		buttons.add(button);
	}

	private void addMainOperators(RCPosition position, String name, DoubleBinaryOperator op) {
		ActionListener operator = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {

					double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue());
					model.setValue(result);
					model.clearActiveOperand();
					model.setPendingBinaryOperation(null);
				}

				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(op);
				model.clear();
			}
		};
		cp.add(new ButtonOperator(name, null, operator), position);
	}

	/**
	 * This method adds all buttons for non invertible operations to this calculator
	 */
	private void addOperators() {

		addMainOperators(new RCPosition(5, 6), "+", Double::sum);
		addMainOperators(new RCPosition(3, 6), "*", (x, y) -> x * y);
		addMainOperators(new RCPosition(2, 6), "/", (x, y) -> x / y);
		addMainOperators(new RCPosition(4, 6), "-", (x, y) -> x - y);

		ActionListener equals = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
			}
		};
		cp.add(new ButtonOperator("=", null, equals), new RCPosition(1, 6));

		ActionListener swap = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.swapSign();
			}
		};
		cp.add(new ButtonOperator("+/-", null, swap), new RCPosition(5, 4));

		ActionListener dot = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.insertDecimalPoint();
			}
		};
		cp.add(new ButtonOperator(".", null, dot), new RCPosition(5, 5));

		ActionListener clear = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
				ekran.setText("0");
			}
		};
		cp.add(new ButtonOperator("clr", null, clear), new RCPosition(1, 7));

		ActionListener reset = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearAll();
			}
		};
		cp.add(new ButtonOperator("reset", null, reset), new RCPosition(2, 7));

		ActionListener push = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stack.push(model.getValue());
			}
		};
		cp.add(new ButtonOperator("push", null, push), new RCPosition(3, 7));

		ActionListener pop = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stack.isEmpty()) {
					System.out.println("Stack is empty. There are no values saved.");
				} else {
					model.setValue(stack.pop());
				}
			}
		};
		cp.add(new ButtonOperator("pop", null, pop), new RCPosition(4, 7));
	}

	/**
	 * This method adds all number buttons in this calculator
	 */
	private void setButtonNumbers() {
		cp.add(new ButtonNumber(0, model), new RCPosition(5, 3));
		int br = 1;
		for (int i = 4; i >= 2; i--) {
			for (int j = 3; j <= 5; j++) {
				cp.add(new ButtonNumber(br, model), new RCPosition(i, j));
				br++;
			}
		}
	}

	/**
	 * This method creates and names label and returns it
	 * 
	 * @param text current text in the label
	 * @return created label
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		l.setOpaque(true);
		return l;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

}
