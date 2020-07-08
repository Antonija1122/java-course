package hr.fer.zemris.lsystems.impl;

/**
 * Class defines state of turtle. 
 */
import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

public class TurtleState {

	/**
	 * Current position of turtle
	 */
	private Vector2D position; 

	/**
	 * Current course of turtle
	 */
	private Vector2D course; // tren smjer

	/**
	 * Current color of drawing
	 */
	private Color color;

	/**
	 * Current size of turtle step 
	 */
	private double unitLength;

	/**
	 * Constructor initializes private variables to input values 
	 * @param position
	 * @param course
	 * @param color
	 * @param unitLength
	 */
	public TurtleState(Vector2D position, Vector2D course, Color color, double unitLength) {
		this.position = position;
		this.course = course;
		this.color = color;
		this.unitLength = unitLength;
	}

	/**
	 * Getter method for current position 
	 * @return current position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Getter method for current course 
	 * @return current course
	 */
	public Vector2D getCourse() {
		return course;
	}

	/**
	 * Getter method for current color 
	 * @return current color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter method for current step size 
	 * @return current step size
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Setter method for current position 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Setter method for current course 
	 * @param course
	 */
	public void setCourse(Vector2D course) {
		this.course = course;
	}

	/**
	 * Setter method for current color 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Setter method for current unitLength 
	 * @param unitLength
	 */
	public void setUnitLength(double unitLength) {
		this.unitLength = unitLength;
	}
	
	/**
	 * Method copies current state and returns it 
	 */
	public TurtleState copy() {
		TurtleState turtleState = new TurtleState(position.copy(), course.copy(), new Color(color.getRGB()), unitLength);
		return turtleState;
	}

}
