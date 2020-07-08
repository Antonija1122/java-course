package hr.fer.zemris.java.hw17.jvdraw.drawModel;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This interface represents drawing model for app JVDraw and its objects hold
 * information about all {@link GeometricalObject} objects held in the model
 * 
 * @author antonija
 *
 */
public interface DrawingModel {

	/**
	 * This method returns size of objects list
	 * 
	 * @return number of objects in the model
	 */
	public int getSize();

	/**
	 * This method returns objects at index index from this model
	 * 
	 * @param index index of wanted object
	 * @return
	 */
	public GeometricalObject getObject(int index);

	/**
	 * This method adds given object in this collection
	 * 
	 * @param object
	 */
	public void add(GeometricalObject object);

	/**
	 * This method removed object from the model
	 * 
	 * @param object removed object
	 */
	public void remove(GeometricalObject object);

	/**
	 * This method changes index of given object in this models collection for given
	 * offset
	 * 
	 * @param object given object
	 * @param offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * This method returns index of given object from this collection
	 * 
	 * @param object given object
	 * @return index of given object
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * This method clears all collection
	 */
	public void clear();

	/**
	 * This method clears modification flag of this model
	 */
	public void clearModifiedFlag();

	/**
	 * This method returns value of modified flag of this model
	 * 
	 * @return value of modified flag of this model
	 */
	public boolean isModified();

	/**
	 * This method adds listener to this collection
	 * 
	 * @param l input listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * This method removes listener from this collection
	 * 
	 * @param l removed listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}