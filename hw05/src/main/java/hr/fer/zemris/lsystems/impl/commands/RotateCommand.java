package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class implements Command
 * 
 * @author antonija
 *
 */
public class RotateCommand implements Command {

	/**
	 * internal angle
	 */
	double angle;

	/**
	 * Constructor initializes angle to input angle in degrees
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Method executes rotation of turtle for angle 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		ctx.getCurrentState().getCourse().rotate(angle * Math.PI / 180);

	}

}
