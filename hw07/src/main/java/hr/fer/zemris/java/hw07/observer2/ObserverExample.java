package hr.fer.zemris.java.hw07.observer2;

/**
 * Example of use of observer2 
 * @author antonija
 *
 */
public class ObserverExample {

	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(7));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
