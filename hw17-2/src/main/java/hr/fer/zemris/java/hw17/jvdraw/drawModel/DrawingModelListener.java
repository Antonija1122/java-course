package hr.fer.zemris.java.hw17.jvdraw.drawModel;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This interface represent listeners to DrawingModel objects
 * 
 * @author antonija
 *
 */
public interface DrawingModelListener {

	/**
	 * This method is called when new {@link GeometricalObject} is added to
	 * {@link DrawingModel}
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 index of added object
	 * @param index1 index of added object
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * This method is called when {@link GeometricalObject} is removed from
	 * {@link DrawingModel}
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 index of removed object
	 * @param index1 index of removed object
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * This method is called when some {@link GeometricalObject}
	 * from{@link DrawingModel} is changed
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 index of changed object
	 * @param index1 index of changed object
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
