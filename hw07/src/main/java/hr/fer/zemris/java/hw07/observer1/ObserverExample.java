package hr.fer.zemris.java.hw07.observer1;

/**
 * Example of use of observer1 
 * @author antonija
 *
 */
public class ObserverExample {

	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		//first example
//		istorage.removeObserver(observer);
//		istorage.addObserver(new ChangeCounter());
//		istorage.addObserver(new DoubleValue(5));
		
		//second example
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
