package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class is used as entry in ObjectMultistack. This class has one private
 * value Object value, 4 arithmetic methods: add, subtract, multiply, divide,
 * and one numerical comparison method: numCompare.
 * 
 * @author antonija
 *
 */
public class ValueWrapper {

	/**
	 * Private Object value for this wrapper
	 */
	private Object value;

	/**
	 * Public constructor for initialization of private Object value
	 * 
	 * @param initValue
	 */
	public ValueWrapper(Object initValue) {
		this.value = initValue;
	}

	/**
	 * Setter for value
	 * 
	 * @param value input value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Getter method for this value
	 * 
	 * @return this value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * This method adds incValue to this value
	 * 
	 * @param incValue input increment
	 */
	public void add(Object incValue) {
		operate("add", this.value, incValue);
	}

	/**
	 * This method subtracts decValue from this value
	 * 
	 * @param decValue input decrement
	 */
	public void subtract(Object decValue) {
		operate("sub", this.value, decValue);
	}

	/**
	 * This method multiplies this value by mulValue.
	 * 
	 * @param mulValue input multiplier
	 */
	public void multiply(Object mulValue) {
		operate("mul", this.value, mulValue);
	}

	/**
	 * This method divides this value by divValue
	 * 
	 * @param divValue
	 */
	public void divide(Object divValue) {
		operate("div", this.value, divValue);
	}

	/**
	 * This method does numerical comparison of this value and withValue.
	 * 
	 * @param withValue
	 * @return positive number if value>withValue , zero if value=withValue and
	 *         negative number if value<withValue
	 */
	public int numCompare(Object withValue) {
		Object saved = this.value;
		operate("numCompare", this.value, withValue);
		int result = (int) this.value;
		this.value = saved;
		return result;
	}

	/**
	 * This function calls checking for arguments value2 and inputValue and after
	 * that calls method doOperations() which performs operation "operation".
	 * 
	 * @param operation
	 * @param value2
	 * @param inputValue
	 */
	private void operate(String operation, Object value2, Object inputValue) {
		value2 = checkRules(value2);
		inputValue = checkRules(inputValue);
		doOperations(operation, value2, inputValue);
	}

	/**
	 * This method checks that input value is String, Double, Integer or null. If it
	 * is null it becomes Integer.valueOf(0). If it is String method calls function
	 * checkString() and checks if value represents a number that is Integer or
	 * Double. Lastly if it is Integer or Double appropriate casting is performed.
	 * 
	 * @param value input value
	 * @return casted value
	 * 
	 */
	private Object checkRules(Object value) {
		if (!(value instanceof Integer) && !(value instanceof Double) && !(value instanceof String) && value != null) {
			throw new RuntimeException(
					"Input value must be instance of Integer, Double or String classes or it can be null.");
		} else if (value == null) {
			value = Integer.valueOf(0);
		} else if (value instanceof String) {
			value = parsString((String) value);
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		return value;
	}

	/**
	 * Method checks if input value can represents Double or Integer value. If it
	 * does appropriate casting is performed, else RuntimeException() is thrown.
	 * 
	 * @param incValue input value
	 * @return casted input value
	 */
	private Object parsString(String incValue) {
		try {
			Integer num = Integer.parseInt(incValue);
			return num;
		} catch (NumberFormatException e) {
			try {
				Double num = Double.parseDouble(incValue);
				return num;
			} catch (NumberFormatException e2) {
				throw new RuntimeException("Input string literal must be parsable to double or integer.");
			}
		}
	}

	/**
	 * This method synchronizes casts of two input Object arguments. If both are
	 * Integer they stay Integer, otherwise they both are converted to double. 
	 * After casting calculateValue() method is called
	 * @param operation operation that will be performed on two other input values.
	 * @param arg1 first input argument
	 * @param arg2 second input argument
	 */
	private void doOperations(String operation, Object arg1, Object arg2) {
		if (arg1 instanceof Double || arg2 instanceof Double) {
			arg1 = arg1 instanceof Integer ? (double) (((Integer) arg1).intValue()) : arg1;
			arg2 = arg2 instanceof Integer ? (double) (((Integer) arg2).intValue()) : arg2;
		}
		calculateValue(operation, arg1, arg2);
	}

	/**
	 * This method calculates result of input operation and sets this value to result with appropriate casting. 
	 * @param operation operation that is performed on two other input values.
	 * @param arg1 first input argument
	 * @param arg2 second input argument
	 */
	private void calculateValue(String operation, Object arg1, Object arg2) {
		switch (operation) {
		case "add":
			if (arg1 instanceof Integer && arg2 instanceof Integer)
				this.value = (Integer) arg1 + (Integer) arg2;
			else
				this.value = (Double) arg1 + (Double) arg2;
			break;
		case "sub":
			if (arg1 instanceof Integer && arg2 instanceof Integer)
				this.value = (Integer) arg1 - (Integer) arg2;
			else
				this.value = (Double) arg1 - (Double) arg2;
			break;
		case "div":
			if (arg1 instanceof Integer && arg2 instanceof Integer)
				this.value = (Integer) arg1 / (Integer) arg2;
			else
				this.value = (Double) arg1 / (Double) arg2;
			break;
		case "mul":
			if (arg1 instanceof Integer && arg2 instanceof Integer)
				this.value = (Integer) arg1 * (Integer) arg2;
			else
				this.value = (Double) arg1 * (Double) arg2;
			break;
		case "numCompare":
			if (arg1 instanceof Integer && arg2 instanceof Integer)
				this.value = ((Integer) arg1).compareTo((Integer) arg2);
			else
				this.value = ((Double) arg1).compareTo((Double) arg2);
			break;
		}
	}

}
