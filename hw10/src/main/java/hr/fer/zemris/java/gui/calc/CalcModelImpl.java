package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * This class implements CalcModel class that tracks current state of
 * calculator.
 * 
 * @author antonija
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * This variable indicates if current number in calculator is editable
	 */
	private boolean editable;
	/**
	 * This variable indicates if current number in calculator is positive
	 */
	private boolean positive;
	/**
	 * This variable is string representation of current number in calculator
	 */
	private String string;

	/**
	 * current number in calculator
	 */
	private double number;

	/**
	 * stored operand for binary operation
	 */
	private double activeOperand;
	/**
	 * current stored binary operation
	 */
	DoubleBinaryOperator operator;

	/**
	 * Private list of listeners for this model
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	/**
	 * public constructor sets private variables to start state
	 */
	public CalcModelImpl() {
		editable = true;
		positive = true;
		string = "";

		number = 0;
		this.activeOperand = Double.NaN;
		operator = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (string.equals("") || number == 0) {
			return positive ? "0" : "-0";
		}
		if (number > 1 && string.startsWith("0")) {
			while (string.startsWith("0")) {
				string = string.replaceFirst("0", "");
			}
		}

		// return positive ? string : "-" + string;
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null) {
			throw new NullPointerException();
		}
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null) {
			throw new NullPointerException();
		}
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		return number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		this.number = value;
		if (number == Double.NaN) {
			this.string = "NaN";
		} else if (value == Double.POSITIVE_INFINITY) {
			this.string = "Infinity";
		} else if (value == Double.NEGATIVE_INFINITY) {
			this.string = "-Infinity";
		} else {
			this.string = Double.toString(value);
		}
		this.positive = number > 0 ? true : false;
		this.editable = false;
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		this.string = "";
		this.number = 0;
		this.editable = true;
		this.positive = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		editable = true;
		positive = true;
		string = "";
		number = 0;
		this.activeOperand = Double.NaN;
		operator = null;
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable()) {
			throw new CalculatorInputException();
		}
		this.positive = !this.positive;
		number = -number;
		if (number > 0) {
			string.replaceAll("-", "");
		} else {
			string = "-" + string;
		}
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable()) {
			throw new CalculatorInputException();
		}

		if (this.string.contains(".")) {
			throw new CalculatorInputException();
		}
		if (string.equals("")) {
			throw new CalculatorInputException();
		}

		string += ".";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable()) {
			throw new CalculatorInputException();
		}

		String newString = this.string + digit;
		try {
			double number = Double.parseDouble(newString);
			if (number > Double.MAX_VALUE) {
				throw new CalculatorInputException();
			}
			this.number = number;
			this.string = newString;
			valueChanged();
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return !Double.isNaN(activeOperand);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		this.activeOperand = Double.NaN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return operator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		operator = op;
	}

	/**
	 * {@inheritDoc}
	 */
	private void valueChanged() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

}
