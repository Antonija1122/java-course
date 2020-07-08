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
public class DrawCommand implements Command {

	/**
	 * private variable for turtle step 
	 */
	private double step;

	/**
	 * Constructor initializes internal variable 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}


	/**
	 * {@inheritDoc}
	 * Method draws a line and sets current state 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
	
		Vector2D offset = ctx.getCurrentState().getCourse().scaled(step * ctx.getCurrentState().getUnitLength());

		Vector2D currentPos = ctx.getCurrentState().getPosition();

		double startX = ctx.getCurrentState().getPosition().getX();
		double startY = ctx.getCurrentState().getPosition().getY();

		currentPos.translate(offset);

		Color color = ctx.getCurrentState().getColor();
		painter.drawLine(startX, startY, currentPos.getX(), currentPos.getY(), color, 1f);

	}

	
	


}
