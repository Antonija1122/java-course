package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.util.StringJoiner;

/**
 * This class represents save visitor. It provides methods for all types of
 * geometric objects so every call of method adds text representation of object
 * to private variable text.
 * 
 * @author antonija
 *
 */
public class GeometricalObjectSaveVisitor implements GeometricalObjectVisitor {

	/**
	 * text representation of model
	 */
	private StringJoiner text;

	/**
	 * Public constructor
	 */
	public GeometricalObjectSaveVisitor() {
		text = new StringJoiner("\n");
	}

	@Override
	public void visit(Line line) {
		text.add("LINE " + line.getStartX() + " " + line.getStartY() + " " + line.getEndX() + " " + line.getEndY() + " "
				+ line.getColor().getRed() + " " + line.getColor().getGreen() + " " + line.getColor().getBlue());
	}

	@Override
	public void visit(Circle circle) {
		text.add("CIRCLE " + circle.getCenterX() + " " + circle.getCenterY() + " " + circle.getRadius() + " "
				+ circle.getColor().getRed() + " " + circle.getColor().getGreen() + " " + circle.getColor().getBlue());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		text.add("FCIRCLE " + filledCircle.getCenterX() + " " + filledCircle.getCenterY() + " "
				+ filledCircle.getRadius() + " " + filledCircle.getColorfg().getRed() + " "
				+ filledCircle.getColorfg().getGreen() + " " + filledCircle.getColorfg().getBlue() + " "
				+ filledCircle.getColorbg().getRed() + " " + filledCircle.getColorbg().getGreen() + " "
				+ filledCircle.getColorbg().getBlue());

	}

	/**
	 * This method is getter method for this text
	 * @return text
	 */
	public String getSaveText() {
		return text.toString();
	}

}
