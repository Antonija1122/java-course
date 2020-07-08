package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;

/**
 * This class represent a line
 * 
 * @author antonija
 *
 */
public class Line extends GeometricalObject {

	/**
	 * x coordinate for start
	 */
	private int startX;
	/**
	 * y coordinate for start
	 */
	private int startY;
	/**
	 * x coordinate for end
	 */
	private int endX;
	/**
	 * y coordinate for end
	 */
	private int endY;
	/**
	 * color of this object
	 */
	private Color color;

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		// TODO Auto-generated method stub
		return new LineEditor(this);
	}

	/**
	 * Getter method for startX
	 * 
	 * @return startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Setter method for startX
	 * 
	 * @param startX input startX
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Getter method for startY
	 * 
	 * @return startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Setter method for startY
	 * 
	 * @param startY input startY
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Getter method for endX
	 * 
	 * @return endX
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * Setter method for endX
	 * 
	 * @param endX input endX
	 */
	public void setEndX(int endX) {
		this.endX = endX;
	}

	/**
	 * Getter method for endY
	 * 
	 * @return endY
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * Setter method for endY
	 * 
	 * @param endY input endY
	 */
	public void setEndY(int endY) {
		this.endY = endY;
	}

	/**
	 * Getter method for color
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter method for color
	 * 
	 * @param color input color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {

		return "Line (" + startX + "," + startY + ")-(" + endX + "," + endY + ")";
	}

}
