package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This interface represents multiple document model in JNotepad++, it has collection of SingleDocumentModels
 * @author antonija
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Method creates new blank SingleDocumentModel and adds it to collection of SingleDocumentModels
	 * @return new blank SingleDocumentModel
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter method for current SingleDocumentModel
	 * @return current SingleDocumentModel
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Method loads SingleDocumentModel from input path and adds it to collection of SingleDocumentModels
	 * @param path input path for SingleDocumentModel
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Method saves SingleDocumentModel model to newPath, if newPath==null then model is saved to it's old path
	 * @param model input SingleDocumentModel model
	 * @param newPath new path for SingleDocumentModel model
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Method closes tab of input model and removes input model from it's collection.
	 * @param model input SingleDocumentModel model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Method for registering listener for this model 
	 * @param l input listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Method for unregistering listener for this model 
	 * @param l input listener to be unregistered
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Method returns number of single document in the model
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Method returns SingleDocumentModel model at index index from this models collection.
	 * @param index of wanted document
	 * @return document at input index
	 */
	SingleDocumentModel getDocument(int index);
	
}

