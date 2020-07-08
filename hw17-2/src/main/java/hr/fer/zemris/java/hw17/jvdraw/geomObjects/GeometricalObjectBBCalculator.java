package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.awt.Rectangle;


/**
 * This class implements GeometricalObjectVisitor and each visit method
 * calculates necessary boundaries for input object
 * 
 * @author antonija
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	public GeometricalObjectBBCalculator() {
		super();
		this.xMin = Integer.MAX_VALUE;
		this.yMin = Integer.MAX_VALUE;
		this.xMax = Integer.MIN_VALUE;
		this.yMax = Integer.MIN_VALUE;
	}

	@Override
	public void visit(Line line) {
		xMin = Math.min(Math.min(xMin, line.getStartX()), line.getEndX());
		yMin = Math.min(Math.min(yMin, line.getStartY()), line.getEndY());
		xMax = Math.max(Math.max(xMax, line.getStartX()), line.getEndX());
		yMax = Math.max(Math.max(yMax, line.getStartY()), line.getEndY());

	}

	@Override
	public void visit(Circle circle) {
		xMin = Math.min(xMin, circle.getCenterX() - circle.getRadius());
		yMin = Math.min(yMin, circle.getCenterY() - circle.getRadius());
		xMax = Math.max(xMax, circle.getCenterX() + circle.getRadius());
		yMax = Math.max(yMax, circle.getCenterY() + circle.getRadius());
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
	};

	@Override
	public void visit(FilledCircle filledCircle) {
		xMin = Math.min(xMin, filledCircle.getCenterX() - filledCircle.getRadius());
		yMin = Math.min(yMin, filledCircle.getCenterY() - filledCircle.getRadius());
		xMax = Math.max(xMax, filledCircle.getCenterX() + filledCircle.getRadius());
		yMax = Math.max(yMax, filledCircle.getCenterY() + filledCircle.getRadius());

	}

}
