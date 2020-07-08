package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;


public class PushCommand implements Command {

	/**
	 * {@inheritDoc}
	 * Method pushes once more current state on the top of stack of ctx
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState peekState = ctx.getCurrentState().copy();
		ctx.pushState(peekState);	
	}
	

}
