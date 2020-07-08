package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface of listener for MultipleDocumentModel class
 * @author antonija
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Method implements action for every change of current document of this model
	 * @param previousModel previous current SingleDocumentModelof this model
	 * @param currentModel current SingleDocumentModelof this model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method implements action for every added document to this model
	 * @param SingleDocumentModel model added to this model
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method implements action for every removed document of this model
	 * @param SingleDocumentModel model removed from listeners colections of this model
	 */
	void documentRemoved(SingleDocumentModel model);
}
