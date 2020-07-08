package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class implements Command 
 * @author antonija
 *
 */
public class ScaleCommand implements Command{
	
	/**
	 * private scaler
	 */
	private double factor;

	/**
	 * Constructor initializes this scaler to input scaler
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Method scales step length of turtle to new value
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		System.out.println("usao je u execute scale");

		TurtleState currentState = ctx.getCurrentState();// trenutno stanje
		currentState.setUnitLength(currentState.getUnitLength()*factor);
		
	}

}
