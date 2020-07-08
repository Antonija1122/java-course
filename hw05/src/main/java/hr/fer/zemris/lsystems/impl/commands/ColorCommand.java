package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class implements Command 
 * @author antonija
 *
 */
public class ColorCommand implements Command {
	
	/**
	 * internal color of class Color
	 */
	Color color;
	
	/**
	 * Constructor initializes color of this instance 
	 * @param color
	 */
	public ColorCommand(Color color) {
		if(!(color instanceof Color)) {
			throw new IllegalArgumentException("Illegal color instance");
		}
		this.color=color;
	}
	
	/**
	 * {@inheritDoc}
	 * Method sets current states color to this color
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState currentState = ctx.getCurrentState();// trenutno stanje
		currentState.setColor(color);			
	}



}
