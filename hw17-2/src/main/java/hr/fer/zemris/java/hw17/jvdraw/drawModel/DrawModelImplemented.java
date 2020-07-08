package hr.fer.zemris.java.hw17.jvdraw.drawModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectListener;

/**
 * This class implements DrawingModel and also GeometricalObjectListener. It
 * registers itself on all objects from this model
 * 
 * @author antonija
 *
 */
public class DrawModelImplemented implements DrawingModel, GeometricalObjectListener {

	/**
	 * List of GeometricalObject in this collection
	 */
	List<GeometricalObject> elements = new ArrayList<>();
	/**
	 * List of DrawingModelListeners in this collection
	 */
	List<DrawingModelListener> listeners = new ArrayList<DrawingModelListener>();
	/**
	 * modified flag for this model
	 */
	private boolean modified = false;

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return elements.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		elements.add(object);
		object.addGeometricalObjectListener(this);
		modified = true;

		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, elements.indexOf(object), elements.indexOf(object));
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		object.removeGeometricalObjectListener(this);
		int index = elements.indexOf(object);
		elements.remove(object);
		modified = true;

		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {

		int currIndex = elements.indexOf(object);
		if (currIndex + offset > elements.size() - 1 || currIndex + offset < 0)
			return;

		elements.remove(object);
		elements.add(currIndex + offset, object);

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, offset, offset);
		}

	}

	@Override
	public int indexOf(GeometricalObject object) {
		return elements.indexOf(object);
	}

	@Override
	public void clear() {
		for (GeometricalObject o : elements) {
			o.removeGeometricalObjectListener(this);
		}
		elements.clear();
		modified = true;
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		modified = true;

		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, elements.indexOf(o), elements.indexOf(o));
		}
	}

}
