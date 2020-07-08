package hr.fer.zemris.java.hw05.db;

/**
 * Class contains public static final variables that implement interface
 * IComparisonOperator(). Variables have one method each. 
 * 
 * 
 * @author antonija
 *
 */
public class ComparisonOperators {

	public static final IComparisonOperator LESS = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1<value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) < 0;
		}
	};

	public static final IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1<=value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) <= 0;
		}
	};

	public static final IComparisonOperator GREATER = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1>value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) > 0;
		}
	};

	public static final IComparisonOperator GREATER_OR_EQUALS = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1>=value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) >= 0;
		}
	};

	public static final IComparisonOperator EQUALS = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1=value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.equals(value2);
		}
	};

	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1!=value2
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			return !value1.equals(value2);
		}
	};

	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		/**
		 * {@inheritDoc}
		 * @return true if value1 Like value2 example: Beslic Like B*ic
		 * 
		 */
		@Override
		public boolean satisfied(String value1, String value2) {

			if (value2.contains("*")) {
				int br = 0;

				for (int i = 0; i < value2.length(); i++) {
					if (value2.charAt(i) == '*') {
						br++;
					}
				}

				if (br != 1) {
					throw new IllegalArgumentException("There should only be one occurance of * in string");
				} else {
					String helper = value2.replace("*", "(.*)");
					return value1.matches(helper);
				}

			} else {
				return value1.equals(value2);
			}
		}

	};

}
