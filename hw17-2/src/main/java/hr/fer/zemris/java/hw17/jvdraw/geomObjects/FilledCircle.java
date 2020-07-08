package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;

/**
 * This class represent a filled circle
 * 
 * @author antonija
 *
 */
public class FilledCircle extends GeometricalObject {
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
	 * color of this object outline
	 */
	private Color colorfg;
	/**
	 * color of this object fill
	 */
	private Color colorbg;

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
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

	/**
	 * Getter method for colorfg
	 * 
	 * @return colorfg
	 */
	public Color getColorfg() {
		return colorfg;
	}

	/**
	 * Setter method for colorfg
	 * 
	 * @param colorfg input colorfg
	 */
	public void setColorfg(Color colorfg) {
		this.colorfg = colorfg;
	}

	/**
	 * Getter method for colorfg
	 * 
	 * @return colorfg
	 */
	public Color getColorbg() {
		return colorbg;
	}

	/**
	 * Setter method for colorbg
	 * 
	 * @param colorbg input colorbg
	 */
	public void setColorbg(Color colorbg) {
		this.colorbg = colorbg;
	}

	@Override
	public String toString() {
		return "Filled circle (" + centerX + "," + centerY + "), " + radius + ", " + String.format("#%02X%02X%02X", colorbg.getRed(), colorbg.getGreen(), colorbg.getBlue());
	}

}
