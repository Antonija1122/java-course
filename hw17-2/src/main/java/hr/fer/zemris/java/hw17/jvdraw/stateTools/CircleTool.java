package hr.fer.zemris.java.hw17.jvdraw.stateTools;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Circle;

/**
 * This class implements Tool and it paints a Circle. First click on a mouse
 * creates center of a circle and second click defines its radius.
 * 
 * @author antonija
 *
 */
public class CircleTool extends JToggleButton implements Tool {

	/**
	 * Public constructor saves references to DrawingModel, IColorProvider and JDrawingCanvas
	 * @param model input DrawingModel
	 * @param provider input IColorProvider
	 * @param canvas input JDrawingCanvas
	 */
	public CircleTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {
		super();
		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
	}

	/**
	 * private reference to DrawingModel
	 */
	private DrawingModel model;

	/**
	 * color provider 
	 */
	private IColorProvider provider;

	/**
	 * reference to main JComponent of JVDraw gui
	 */
	private JDrawingCanvas canvas;

	/**
	 * times mouse was clicked
	 */
	int brClick = 0;

	/**
	 * current circle
	 */
	Circle current;

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (brClick == 0) {
			// first click
			brClick++;
			Circle newCircle = new Circle();
			newCircle.setColor(provider.getCurrentColor());
			newCircle.setCenterX(e.getX());
			newCircle.setCenterY(e.getY());
			current = newCircle;
		} else if (brClick == 1) {
			// drugi
			current.setRadius((int) Math
					.sqrt(Math.pow(current.getCenterX() - e.getX(), 2) + Math.pow(current.getCenterY() - e.getY(), 2)));
			model.add(current);
			current = null;
			brClick = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (current != null) {
			current.setRadius((int) Math
					.sqrt(Math.pow(current.getCenterX() - e.getX(), 2) + Math.pow(current.getCenterY() - e.getY(), 2)));
			canvas.repaint();

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void paint(Graphics2D g2d) {
		if(current!=null) {
			g2d.setColor(provider.getCurrentColor());
			g2d.drawOval(current.getCenterX() - current.getRadius(), current.getCenterY() - current.getRadius(),
					current.getRadius()*2, current.getRadius()*2);
		}

	}
	

}
