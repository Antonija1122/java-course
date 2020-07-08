package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;


import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class implements Command 
 * @author antonija
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * Steps of turtle 
	 */
	private double step;

	/**
	 * Constructor initializes this step to input step
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Method moves turtle in forward direction and sets current state but doesn't draw a line
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		Vector2D offset = ctx.getCurrentState().getCourse().scaled(step * ctx.getCurrentState().getUnitLength());

		Vector2D currentPos = ctx.getCurrentState().getPosition();

//		double startX = ctx.getCurrentState().getPosition().getX();
//		double startY = ctx.getCurrentState().getPosition().getY();
		currentPos.translate(offset);

	}

}
