package hr.fer.zemris.java.hw17.jvdraw.stateTools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Line;

/**
 * This class implements Tool and it paints a line. First click on a mouse
 * creates start point of a line and second click defines its end point.
 * 
 * @author antonija
 *
 */
public class LineTool extends JToggleButton implements Tool {

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
	 * current line
	 */
	Line current;

	/**
	 * Public constructor saves references to DrawingModel, IColorProvider and
	 * JDrawingCanvas
	 * 
	 * @param model    input DrawingModel
	 * @param provider input IColorProvider
	 * @param canvas   input JDrawingCanvas
	 */
	public LineTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {
		super();
		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
	}

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
			Line newLine = new Line();
			newLine.setColor(provider.getCurrentColor());
			newLine.setStartX(e.getX());
			newLine.setStartY(e.getY());
			current = newLine;
		} else if (brClick == 1) {
			// drugi
			current.setEndX(e.getX());
			current.setEndY(e.getY());
			model.add(current);
			current = null;
			brClick = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (current != null) {
			current.setEndX(e.getX());
			current.setEndY(e.getY());
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
			g2d.drawLine(current.getStartX(), current.getStartY(), current.getEndX(), current.getEndY());
		}

	}
	
	@Override
	public String toString() {
		return "LinesTool";
	}

}
