package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {

	private Object value;

	public ValueWrapper(Object initialValue) {
		super();
		value = initialValue;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void add(Object incValue) {
		incValue = checkAndPrepare(incValue);
		if ((value instanceof Integer))
			value = (Integer) value + (Integer) incValue;
		else
			value = (Double) value + (Double) incValue;

	}

	public void subtract(Object decValue) {
		decValue = checkAndPrepare(decValue);
		if ((value instanceof Integer))
			value = (Integer) value - (Integer) decValue;
		else
			value = (Double) value - (Double) decValue;
	}

	public void multiply(Object mulValue) {
		mulValue = checkAndPrepare(mulValue);
		if ((value instanceof Integer))
			value = (Integer) value * (Integer) mulValue;
		else
			value = (Double) value * (Double) mulValue;
	}

	public void divide(Object divValue) {
		divValue = checkAndPrepare(divValue);
		if ((value instanceof Integer))
			value = (Integer) value / (Integer) divValue;
		else
			value = (Double) value / (Double) divValue;
	}

	public int numCompare(Object withValue) {
		withValue = checkAndPrepare(withValue);
		if ((value instanceof Integer))
			return ((Integer) value).compareTo((Integer) withValue);
		else
			return ((Double) value).compareTo((Double) withValue);
	}

	private Object checkAndPrepare(Object incValue) {
		isValid(value);
		isValid(incValue);
		incValue = extract(incValue);
		value = extract(value);
		return convertType(incValue);
	}

	private void isValid(Object value) {
		if (value == null)
			return;

		if (value instanceof Integer || value instanceof Double || value instanceof String) {
		} else {
			String argType = value.getClass().getName();
			argType = argType.substring(argType.lastIndexOf('.') + 1).toLowerCase();
			throw new RuntimeException("throws, since the argument value is " + argType);
		}
	}

	private Object convertType(Object incValue) {
		if (value instanceof Double || incValue instanceof Double) {
			value = value instanceof Integer ? (double) (((Integer) value).intValue()) : value;
			return incValue instanceof Integer ? (double) (((Integer) incValue).intValue()) : incValue;
		}
		return incValue;
	}

	private Object extract(Object incValue) {
		if (incValue == null)
			return Integer.valueOf(0);
		if (incValue instanceof String) {
			if (((String) incValue).indexOf('.') != -1 || ((String) incValue).indexOf('E') != -1) {
				try {
					incValue = Double.parseDouble((String) incValue);
				} catch (Exception eInt) {
				}
			} else {
				try {
					incValue = Integer.parseInt((String) incValue);
				} catch (Exception eDouble) {
					throw new RuntimeException();
				}
			}

		}
		return incValue;
	}
}
