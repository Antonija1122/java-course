package hr.fer.zemris.java.hw17.jvdraw.editors;

import javax.swing.JPanel;

/**
 * This interface represents editors for GeometricalObjects
 * 
 * @author antonija
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * This method checks if given parameters are in a correct form , if so method
	 * acceptEditing() is called
	 */
	public abstract void checkEditing();

	/**
	 * This method changes given object with given parameters and saves changes in
	 * DrawingModel
	 */
	public abstract void acceptEditing();
}