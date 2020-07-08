package hr.fer.zemris.lsystems.impl;

/**
 * Interface Command has one methode execute. 
 */
import hr.fer.zemris.lsystems.Painter;

public interface Command {
	
	/**
	 * Method executes command. Implemented here as an abstract method
	 * @param ctx
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);

}
