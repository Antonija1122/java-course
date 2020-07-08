package hr.fer.zemris.java.hw17.jvdraw.geomObjects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.editors.*;

/**
 * This interface represents geometrical object for JVDraw app
 * 
 * @author antonija
 *
 */
public abstract class GeometricalObject {

	/**
	 * list of GeometricalObjectListener listeners
	 */
	List<GeometricalObjectListener> listeners = new ArrayList<>();

	// ...
	/**
	 * This method calls accept method from GeometricalObjectVisitor that takes as a
	 * input parameter this object
	 * 
	 * @param v GeometricalObjectVisitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * This method creates GeometricalObjectEditor for this type of objects and
	 * returns it
	 * 
	 * @return GeometricalObjectEditor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * This method adds new listener to this list of listeners
	 * 
	 * @param l new listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	};

	/**
	 * This method removes input GeometricalObjectListener from this list of
	 * listeners
	 * 
	 * @param l input listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	public void notifyAllListeners() {
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	// ...
}