package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.stateTools.Tool;

/**
 * This class implements Jcomponent that is the main component of JVDraw GUI
 * application. It has references to current state(selected Tool) and
 * DrawingModel. This component renders all components from DrawingModel to user
 * on this component. This component also serves as a mouse listener to send
 * drawing information to current tool so new components can be added to
 * DrawingModel. Also this component serves as a DrawingModelListener so every
 * change in DrawingModel objects can be repainted.
 * 
 * @author antonija
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener, MouseListener, MouseMotionListener {

	/**
	 * current tool selected
	 */
	private Tool state;

	/**
	 * reference to DrawingModel
	 */
	private DrawingModel model;

	/**
	 * Public constructor saves references of input variables
	 * 
	 * @param model DrawingModel
	 * @param state current Tool
	 */
	public JDrawingCanvas(DrawingModel model, Tool state) {
		this.state = state;
		this.model = model;
		model.addDrawingModelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		GeometricalObjectVisitor v = new GeometricalObjectPainter((Graphics2D) g);
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(v);
		}
		Graphics2D g2D = (Graphics2D) g;
		if (state != null) {
			g2D.setStroke(new BasicStroke(2));
			state.paint(g2D);
		}
		g2D.dispose();

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	////////////////////////////////

	@Override
	public void mouseClicked(MouseEvent e) {
		if (state != null)
			state.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (state != null)
			state.mouseMoved(e);
	}

	/**
	 * Getter for current state Tool
	 * 
	 * @return state
	 */
	public Tool getState() {
		return state;
	}

	/**
	 * Setter for current state
	 * 
	 * @param state input state
	 */
	public void setState(Tool state) {
		this.state = state;
	}


}
