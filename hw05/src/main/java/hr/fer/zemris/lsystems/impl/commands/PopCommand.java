package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


public class PopCommand implements Command {
	

	/**
	 * {@inheritDoc}
	 * Method removes peek state from stack of ctx
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
        ctx.popState();
		
	}
}
