package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;

/**
 * This class represent a Circle
 * 
 * @author antonija
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * x coordinate of center
	 */
	private int centerX;
	/**
	 * y coordinate of center
	 */
	private int centerY;
	/**
	 * radius size of this circle
	 */
	private int radius;

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
		return new CircleEditor(this);
	}

	/**
	 * Getter method for centerX
	 * 
	 * @return centerX
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Setter method for centerX
	 * 
	 * @param centerX input centerX
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Getter method for centerY
	 * 
	 * @return centerY
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Setter method for centerY
	 * 
	 * @param centerY input centerY
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
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

	/**
	 * Getter method for radius
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter method for radius
	 * 
	 * @param radius input radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "Circle (" + centerX + "," + centerY + "). " + radius;
	}

}
