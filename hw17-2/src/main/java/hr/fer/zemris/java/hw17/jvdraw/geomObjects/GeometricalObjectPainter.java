package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 * This class implements GeometricalObjectVisitor and for each visit method it
 * paints input object
 * 
 * @author antonija
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Public constructor gets graphics object 
	 * @param g2d
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		super();
		this.g2d = g2d;
		g2d.setStroke(new BasicStroke(2));
	}

	/**
	 * reference to graphics object
	 */
	private Graphics2D g2d;

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(),
				circle.getRadius()*2, circle.getRadius()*2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		
		g2d.setColor(filledCircle.getColorfg());
		g2d.drawOval(filledCircle.getCenterX() - filledCircle.getRadius(), filledCircle.getCenterY() - filledCircle.getRadius(),
				filledCircle.getRadius()*2, filledCircle.getRadius()*2);
		g2d.setColor(filledCircle.getColorbg());
		g2d.fillOval(filledCircle.getCenterX() - filledCircle.getRadius(), filledCircle.getCenterY() - filledCircle.getRadius(),
				filledCircle.getRadius()*2, filledCircle.getRadius()*2);
	}

}
