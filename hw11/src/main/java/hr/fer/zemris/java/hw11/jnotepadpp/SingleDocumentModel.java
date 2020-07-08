package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This interface represents one document in JNotepad++, it has JTextArea, path and modification status
 * @author antonija
 *
 */
public interface SingleDocumentModel {

	/**
	 * Getter method for JTextArea of this document
	 * @return JTextArea
	 */
	JTextArea getTextComponent();

	/**
	 * Getter method for path of this SingleDocumentModel
	 * @return model's path 
	 */
	Path getFilePath();

	/**
	 * Setter method for this model's path
	 * @param path input path
	 */
	void setFilePath(Path path);

	/**
	 * Getter method for modification status of this model
	 * @return status
	 */
	boolean isModified();

	/**
	 * Setter method for modification status of this model
	 * @param status
	 */
	void setModified(boolean modified);

	/**
	 * Method for registering listener for this model 
	 * @param l input listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Method for unregistering listener for this model 
	 * @param l input listener to be unregistered
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
