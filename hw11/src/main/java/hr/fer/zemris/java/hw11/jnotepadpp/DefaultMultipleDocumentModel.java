package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Implementation of MultipleDocumentModel interface for JNotepadPP and
 * extension of JTabbedPane.
 * 
 * @author antonija
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 28031991L;

	/**
	 * previously current document
	 */
	private SingleDocumentModel previousDocument;
	/**
	 * current document
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * collection of single document models
	 */
	private List<SingleDocumentModel> singleDocumentCollection = new ArrayList<>();
	/**
	 * collection of listeners for this multiple document model
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	/**
	 * Heigth of icons for document tabs
	 */
	private final int iconHeight = 15;
	/**
	 * Width of icons for document tabs
	 */
	private final int iconWidth = 15;
	/**
	 * subpackage in this project that holds images for tabs
	 */
	private final String subpackage = "icons/";
	/**
	 * red icon for modified documents
	 */
	private final ImageIcon redIcon = getIcon(subpackage + "red.png");
	/**
	 * green icon for unmodified documents
	 */
	private final ImageIcon greenIcon = getIcon(subpackage + "green.png");
	/**
	 * ILocalizationProvider used for translating messages to user
	 */
	private ILocalizationProvider flp;

	/**
	 * Public constructor sets previousDocument and currentDocument to null, saves
	 * translator and adds listener to track change of current document
	 * 
	 * @param flp ILocalizationProvider used for translation
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider flp) {

		super();
		this.previousDocument = null;
		this.currentDocument = null;
		this.flp = flp;

		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				currentDocument = getSelectedIndex() != -1 ? getDocument(getSelectedIndex()) : null;
				listeners.forEach(dTab -> dTab.documentAdded(currentDocument));
				listeners.forEach(dTab -> dTab.currentDocumentChanged(previousDocument, currentDocument));
			}

		});

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleDocumentCollection.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {

		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		// Add document listener
		addSingleDocumentListener(newDocument);
		// Add document to internal collection
		singleDocumentCollection.add(newDocument);
		// Create tab with green icon
		addTab(flp.getString("(unnamed)"), greenIcon, new JScrollPane(newDocument.getTextComponent()));
		// Update previous and modify current document
		previousDocument = currentDocument;
		currentDocument = newDocument;
		// Change Active tab, always the last one
		setSelectedIndex(getNumberOfDocuments() - 1);
		setToolTipTextAt(getNumberOfDocuments() - 1, flp.getString("(unnamed)"));
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {

		try {
			if (path == null)
				throw new NullPointerException();
			int index = exists(path);
			if (index != -1) {
				setSelectedIndex(index);
				return currentDocument;
			}
			String text = Files.readString(path, StandardCharsets.UTF_8);
			SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, text);

			// Add document listener
			addSingleDocumentListener(newDocument);

			if (!singleDocumentCollection.contains(newDocument)) {
				// Add document to internal collection
				singleDocumentCollection.add(newDocument);
				// Create tab with green icon
				addTab(path.getFileName().toString(), greenIcon, new JScrollPane(newDocument.getTextComponent()));
				// Modify current document
				currentDocument = newDocument;
			}

		} catch (IOException | NullPointerException e) {
			System.out.println(e.getMessage());
		}

		setSelectedIndex(getNumberOfDocuments() - 1);
		setToolTipTextAt(getNumberOfDocuments() - 1, currentDocument.getFilePath().toString());
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		if (newPath == null) {
			JFileChooser jfc = new JFileChooser();

			jfc.setDialogTitle("save file");
			if (jfc.showSaveDialog(model.getTextComponent()) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(model.getTextComponent(), flp.getString("DMnothingrecInfo"),
						flp.getString("information"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
			if (exists(newPath) != -1) {
				JOptionPane.showMessageDialog(model.getTextComponent(), flp.getString("DMnotAllowedInfo"),
						flp.getString("information"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		try {

			Files.writeString(newPath, model.getTextComponent().getText(), StandardCharsets.UTF_8);

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(model.getTextComponent(), flp.getString("DMErrorRecording"),
					flp.getString("error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(model.getTextComponent(), flp.getString("DMdocumentRecorded"),
				flp.getString("Information"), JOptionPane.INFORMATION_MESSAGE);

		model.setFilePath(newPath);
		setIcon(model, greenIcon);
		model.setModified(false);
		listeners.forEach(cTab -> cTab.documentAdded(currentDocument));
		setToolTipTextAt(getNumberOfDocuments() - 1, currentDocument.getFilePath().toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		singleDocumentCollection.remove(model);
		listeners.forEach(mdoc -> mdoc.documentRemoved(model));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return singleDocumentCollection.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocumentCollection.get(index);
	}

	/**
	 * Method adds SingleDocumentListener to input SingleDocumentModel that modifies
	 * icon at tab depending on the state of model
	 * 
	 * @param newDocument SingleDocumentModel model that listener is added to
	 */
	private void addSingleDocumentListener(SingleDocumentModel newDocument) {

		newDocument.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIcon(model, redIcon);
				} else {
					setIcon(model, greenIcon);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTabName(model);
			}

		});

	}

	/**
	 * Method sets input icon to input models tab
	 * 
	 * @param model input model
	 * @param icon  method puts to models tab
	 */
	private void setIcon(SingleDocumentModel model, ImageIcon icon) {
		this.setIconAt(singleDocumentCollection.indexOf(model), icon);
	}

	/**
	 * Method sets name to tab of input model
	 * 
	 * @param model input model
	 */
	private void setTabName(SingleDocumentModel model) {
		this.setTitleAt(singleDocumentCollection.indexOf(model), model.getFilePath().getFileName().toString());
	}

	/**
	 * Method creates ImageIcon for icon named as input string and returns it
	 * 
	 * @param name name of image in package
	 * @return ImageIcon of input image
	 */
	private ImageIcon getImageStream(String name) {

		InputStream is = this.getClass().getResourceAsStream(name);
		if (is == null) {
			JOptionPane.showMessageDialog(this, flp.getString("DMErrorLoading"), flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);

			return null;
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
		} catch (IOException e) {

			JOptionPane.showMessageDialog(this, flp.getString("DMErrorLoading"), flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
		}
		return new ImageIcon(bytes);
	}

	/**
	 * Method sets dimensions of input image to input dimensions
	 * 
	 * @param srcImg input image
	 * @param w      input width
	 * @param h      input height
	 * @return Image with input dimensions
	 */
	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	/**
	 * Method creates ImageIcon from input image string and sets wanted dimensions
	 * to it
	 * 
	 * @param subPath input path of image
	 * @return ImageIcon
	 */
	private ImageIcon getIcon(String subPath) {
		ImageIcon icon = getImageStream(subPath);
		Image image = getScaledImage(icon.getImage(), iconWidth, iconHeight);
		return new ImageIcon(image);
	}

	/**
	 * Method checks if there is a document model of input path already in
	 * collection and returns index of it.
	 * 
	 * @param path input path of document
	 * @return index of input document in private collection
	 */
	private int exists(Path path) {
		for (SingleDocumentModel document : singleDocumentCollection) {
			if ((document.getFilePath() != null) && document.getFilePath().equals(path)) {
				return singleDocumentCollection.indexOf(document);
			}
		}
		return -1;
	}

}
