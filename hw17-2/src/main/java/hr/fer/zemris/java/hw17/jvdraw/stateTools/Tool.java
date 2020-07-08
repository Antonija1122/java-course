package hr.fer.zemris.java.hw17.jvdraw.stateTools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This interface represents Tool from JVDraw toolbar
 * 
 * @author antonija
 *
 */
public interface Tool {

	/**
	 * This method reacts to button pressing of users mouse
	 * 
	 * @param e MouseEvent info
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * This method reacts to button release of users mouse
	 * 
	 * @param e MouseEvent info
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * This method reacts to button clicking of users mouse. On first click new
	 * {@link GeometricalObject} is created on second click all information is
	 * present so object is added to DrawingModel
	 * 
	 * @param e MouseEvent info
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * This method reacts to moving of users mouse
	 * 
	 * @param e MouseEvent info
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * This method reacts to button dragging of users mouse
	 * 
	 * @param e MouseEvent info
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * This method paints added 
	 * @param g2d
	 */
	public void paint(Graphics2D g2d);
}