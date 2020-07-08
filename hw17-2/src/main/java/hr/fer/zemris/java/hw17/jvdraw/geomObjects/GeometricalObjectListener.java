package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

/**
 * This interface represents listener for GeometricalObject
 * 
 * @author antonija
 *
 */
public interface GeometricalObjectListener {
	/**
	 * This method does specific action when a change happens to GeometricalObject
	 * this object listens to
	 * 
	 * @param o input GeometricalObject
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
