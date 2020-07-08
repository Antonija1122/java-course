package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This class represents a list of objects for DrawingModel in JVDraw app. It
 * implements DrawingModelListener and ListModel that holds GeometricalObject
 * objects
 * 
 * @author antonija
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
		implements DrawingModelListener, ListModel<GeometricalObject> {

	/**
	 * serial id number
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to DrawingModel
	 */
	DrawingModel model;

	/**
	 * Public constructor saves reference to DrawingModel and adds itself as a
	 * listener to that model.
	 * 
	 * @param model input DrawingModel
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
