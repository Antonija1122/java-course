package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface of listener for SingleDocumentModel class
 * @author antonija
 *
 */
public interface SingleDocumentListener {

	/**
	 * Method implements action for every modification done to SingleDocumentModel model
	 * @param model input SingleDocumentModel
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method implements action for every modification of path done to SingleDocumentModel model path
	 * @param model input SingleDocumentModel
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}