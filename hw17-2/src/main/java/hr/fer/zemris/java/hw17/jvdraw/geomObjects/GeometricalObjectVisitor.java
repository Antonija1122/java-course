package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

/**
 * This interface represents visitor for GeometricalObject
 * 
 * @author antonija
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * This method is visit method for GeometricalObject type Line
	 * 
	 * @param line input Line object
	 */
	public abstract void visit(Line line);

	/**
	 * This method is visit method for GeometricalObject type Circle
	 * 
	 * @param line input Circle circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * This method is visit method for GeometricalObject type FilledCircle
	 * 
	 * @param line input FilledCircle filledCircle
	 */
	public abstract void visit(FilledCircle filledCircle);
}
