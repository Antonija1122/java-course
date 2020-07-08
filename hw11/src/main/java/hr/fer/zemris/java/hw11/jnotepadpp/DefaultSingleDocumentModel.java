package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of SingleDocumentModel interface for JNotepadPP
 * 
 * @author antonija
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * path of this document
	 */
	private Path filePath;
	/**
	 * text editor of this document
	 */
	private JTextArea editor;
	/**
	 * variable holds value true if document is modified since last saved, false
	 * otherwise
	 */
	private boolean modified;
	/**
	 * listeners of this document
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Public constructor sets initial values to private values and adds document
	 * listener to track if document is modified.
	 * 
	 * @param filePath    input path
	 * @param textContent reference to text editor
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		super();
		this.filePath = filePath;
		editor = new JTextArea(textContent);
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}

		});

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		if (path == null) {
			throw new NullPointerException("Path cannot be null");
		} else {
			filePath = path;
			listeners.forEach(doc -> doc.documentFilePathUpdated(this));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		listeners.forEach(doc -> doc.documentModifyStatusUpdated(this));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
