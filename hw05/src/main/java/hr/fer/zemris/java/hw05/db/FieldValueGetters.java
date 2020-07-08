package hr.fer.zemris.java.hw05.db;

/**
 * Class contains public static final variables that implement interface
 * IFieldValueGetter(). Variables have one method each. Each variable returnes
 * different string value from input StudentRecors instance.
 * 
 * @author antonija
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = new IFieldValueGetter() {

		/**
		 * {@inheritDoc}
		 * @return name from record
		 */
		@Override
		public String get(StudentRecord record) {
			return record.getName();
		}

	};
	public static final IFieldValueGetter LAST_NAME = new IFieldValueGetter() {

		/**
		 * {@inheritDoc}
		 * @return last name from record
		 */
		@Override
		public String get(StudentRecord record) {
			return record.getLastName();
		}

	};
	public static final IFieldValueGetter JMBAG = new IFieldValueGetter() {

		/**
		 * {@inheritDoc}
		 * @return jmbag from record
		 */
		@Override
		public String get(StudentRecord record) {
			return record.getJmbag();
		}

	};

}
