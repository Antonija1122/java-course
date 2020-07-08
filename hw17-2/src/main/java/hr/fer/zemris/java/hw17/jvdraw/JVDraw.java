package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw17.jvdraw.colors.ColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawModelImplemented;
import hr.fer.zemris.java.hw17.jvdraw.drawModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObjectSaveVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.stateTools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.stateTools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.stateTools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.stateTools.Tool;

/**
 * This class is implementation of JVDraw in java. JVDraw is a drawing document
 * model which has actions in menus, toolbar and status bar. This program is
 * able to create new documents and load and save documents from the disk.
 * Application can draw lines circles and filled circles with this app.
 * 
 * @author antonija
 *
 */
public class JVDraw extends JFrame {

	/**
	 * serial id number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * this apps foreground color
	 */
	IColorProvider fgColorArea = new JColorArea(Color.black);

	/**
	 * this apps background color
	 */
	IColorProvider bgColorArea = new JColorArea(Color.red);

	/**
	 * Main drawing component for this model
	 */
	JDrawingCanvas canvas;

	/**
	 * Reference to opened file path
	 */
	private Path openedFilePath;

	/**
	 * currently selected drawing tool
	 */
	Tool state;
	private LineTool linesTool;
	private CircleTool circlesTool;
	private FilledCircleTool fCirclesTool;

	/**
	 * Drawing model for this app
	 */
	DrawingModel model;

	/**
	 * Color label that shows to user text message about currently selected colors
	 */
	ColorLabel colorLabel;

	/**
	 * list of objects of this model that is showed in JPanel of this app
	 */
	JList<GeometricalObject> list;

	/**
	 * Public constructor defines closing actions, menus, toolbar, labels,
	 * listeners, and starts application
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 800);
		
		JPanel panel=new JPanel(new BorderLayout());
		JPanel panelList=new JPanel(new BorderLayout());
		JPanel panelCanvas=new JPanel(new BorderLayout());
		
		model = new DrawModelImplemented();
		canvas = new JDrawingCanvas(model, state);
		panelCanvas.add(canvas,  BorderLayout.CENTER);
		panelList.add(list = new JList<GeometricalObject>(new DrawingObjectListModel(model)), BorderLayout.PAGE_END);
		panel.add(panelCanvas);
		panel.add(panelList);
		
		fgColorArea = new JColorArea(Color.RED);
		bgColorArea = new JColorArea(Color.BLUE);
		linesTool = new LineTool(model, fgColorArea, canvas);
		circlesTool = new CircleTool(model, fgColorArea, canvas);
		fCirclesTool = new FilledCircleTool(model, fgColorArea, bgColorArea, canvas);
		initGUI();
	}

	/**
	 * Initialization of GUI creates menus, toolbar, labels, listeners etc. and puts
	 * all components in JFrame of JVDraw
	 */
	private void initGUI() {

		setTitle("JVDraw");
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		configureActions();
		createMenus();
		createToolbars();

		JPanel contentPanel = new JPanel(new BorderLayout());
		JPanel drawingPanel = new JPanel(new GridLayout(1, 2));
		drawingPanel.add(canvas, BorderLayout.CENTER);
		drawingPanel.add(list, BorderLayout.LINE_END);

		contentPanel.add(drawingPanel, BorderLayout.CENTER);
		contentPanel.add(colorLabel, BorderLayout.PAGE_END);

		getContentPane().add(contentPanel, BorderLayout.CENTER);

	}

	/**
	 * This method creates Menu bar and adds all actions of this JVDraw application
	 * in this menu bar.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		// Add File menu
		JMenu file = new JMenu("File");
		mb.add(file);

		file.add(new JMenuItem(open));
		file.add(new JMenuItem(save));
		file.add(new JMenuItem(saveAs));
		file.add(new JMenuItem(export));
		file.addSeparator();
		file.add(new JMenuItem(exit));
		setJMenuBar(mb);

	}

	/**
	 * This method creates Tool bar and adds all actions of this JVDraw application
	 * in this toolbar.
	 */
	private void createToolbars() {
		JToolBar tb = new JToolBar();
		addToolListeners();
		addListenerToList();
		JPanel buttonPanel = new JPanel();

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setMaximumSize(new Dimension(5, 1));

		colorLabel = new ColorLabel(fgColorArea, bgColorArea);

		BoxLayout bl = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);

		buttonPanel.setLayout(bl);
		buttonPanel.add((JColorArea) fgColorArea);
		buttonPanel.add((JColorArea) bgColorArea);
		
		buttonPanel.add(linesTool);
		buttonPanel.add(circlesTool);
		buttonPanel.add(fCirclesTool);
		
//		tb.add((JColorArea) fgColorArea);
//		tb.add((JColorArea) bgColorArea);
//		tb.add(linesTool);
//		tb.add(circlesTool);
//		tb.add(fCirclesTool);

		tb.add(buttonPanel);

		ButtonGroup shapes = new ButtonGroup();
		shapes.add(linesTool);
		shapes.add(circlesTool);
		shapes.add(fCirclesTool);

		linesTool.setText("Line");
		circlesTool.setText("Circle");
		fCirclesTool.setText("Filled circle");
		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * This method adds mouse listeners to this apps list of objects. So that user
	 * can change order of the list and also delete objects from the list with keys
	 * and also change objects properties with double click on the object
	 */
	private void addListenerToList() {

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());

					GeometricalObject clicked = model.getObject(index);
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();

					if (JOptionPane.showConfirmDialog(null, editor, "My custom dialog",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Invalid input for editing object");

						}
					}
				}
			}
		});

		list.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex < 0)
						return;
					GeometricalObject gObject = model.getObject(selectedIndex);
					model.remove(gObject);
					list.revalidate();
				}
				if (e.getKeyCode() == KeyEvent.VK_ADD) {
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex < 0)
						return;

					GeometricalObject gObject = model.getObject(selectedIndex);
					model.changeOrder(gObject, 1);
					list.revalidate();
				}
				if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex < 0)
						return;

					GeometricalObject gObject = model.getObject(selectedIndex);
					model.changeOrder(gObject, -1);
					list.revalidate();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});

	}

	/**
	 * This method adds listeners to tool buttons so when user clicks on the button
	 * currently selected tool is changed
	 */
	private void addToolListeners() {

		linesTool.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state = linesTool;
				canvas.setState(state);
			}

		});
		circlesTool.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state = circlesTool;
				canvas.setState(state);
			}

		});
		fCirclesTool.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state = fCirclesTool;
				canvas.setState(state);
			}

		});

	}

	/**
	 * This method configures actions, sets their ACCELERATOR_KEY and MNEMONIC_KEY
	 * and adds window listener to check state of application when user wants to
	 * terminate the program
	 */
	private void configureActions() {
		open.putValue(Action.NAME, "Open");
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		open.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");

		save.putValue(Action.NAME, "Save");
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		save.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");

		saveAs.putValue(Action.NAME, "SaveAs");
		saveAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Save new copy of file to disk");

		export.putValue(Action.NAME, "Export");
		export.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		export.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		export.putValue(Action.SHORT_DESCRIPTION, "Export file.");

		exit.putValue(Action.NAME, "Exit");
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exit.putValue(Action.SHORT_DESCRIPTION, "Terminate aplication.");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (checkStatus()) {
					dispose();
				}
			}
		});

	}

	/**
	 * This method checks status of currently opened document. If document is
	 * modified user is asked if he wants to save it.
	 * 
	 * @return true if model is not modified
	 */
	private boolean checkStatus() {

		if (model.isModified()) {
			int Answer = JOptionPane.showConfirmDialog(this, "Your model is not saved. Do you want to save it?", "Quit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (Answer == 0)
				this.save.actionPerformed(null);
		}
		return true;

	}

	/**
	 * This action opens JFileChooser so user can open a document which is than
	 * loaded to this app
	 */
	private final Action open = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser jfc = new JFileChooser();

			jfc.setDialogTitle("open file");
			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path openedFilePath = jfc.getSelectedFile().toPath();
			if (!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nemate pravo citanja nad odabranom datotekom", "Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String text = null;

			try {
				text = Files.readString(openedFilePath);
				loadDocument(text);

			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Dogodila se pogreska pri citanju datoteke", "Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

		}
	};

	/**
	 * This action opens JFileChooser so user can choose a document in which he
	 * wants to save this model in. If openedFilePathis not null model is saved on
	 * that path
	 */
	private final Action save = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();

				jfc.setDialogTitle("save file");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Nista nije snimljeno", "Informacija",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}

			try {
				GeometricalObjectSaveVisitor visitor = new GeometricalObjectSaveVisitor();

				for (int i = 0; i < model.getSize(); i++) {
					model.getObject(i).accept(visitor);
				}

				Files.writeString(openedFilePath, visitor.getSaveText());
				model.clearModifiedFlag();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Dogodila se pogreska pri snimanju datoteke", "Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Dokument je uredno snimljen", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * This action opens JFileChooser so user can choose a document in which he
	 * wants to save this model in
	 */
	private final Action saveAs = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();

				jfc.setDialogTitle("save file");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Nista nije snimljeno", "Informacija",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}

			try {

				GeometricalObjectSaveVisitor visitor = new GeometricalObjectSaveVisitor();

				for (int i = 0; i < model.getSize(); i++) {
					model.getObject(i).accept(visitor);
				}

				Files.writeString(openedFilePath, visitor.getSaveText());
				model.clearModifiedFlag();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Dogodila se pogreska pri snimanju datoteke", "Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Dokument je uredno snimljen", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * This method loads objects from their string representation in input text of
	 * the method
	 * 
	 * @param inputText string representation of objects
	 */
	private void loadDocument(String inputText) {
		String[] lines = inputText.split("\n");

		for (String l : lines) {

			String[] sLine = l.split(" ");

			if (sLine[0].equals("LINE")) {
				addLineToModel(sLine);
			}
			if (sLine[0].equals("CIRCLE")) {
				addCircleToModel(sLine);
			}
			if (sLine[0].equals("FCIRCLE")) {
				addFCircleToModel(sLine);
			}

		}

	}

	/**
	 * This method adds FilledCircle given from input string array
	 * 
	 * @param sLine input string array with parameters for new objects
	 */
	private void addFCircleToModel(String[] sLine) {
		FilledCircle fcircle = new FilledCircle();

		fcircle.setCenterX(Integer.parseInt(sLine[1]));
		fcircle.setCenterY(Integer.parseInt(sLine[2]));
		fcircle.setRadius(Integer.parseInt(sLine[3]));

		int r = Integer.parseInt(sLine[4]);
		int g = Integer.parseInt(sLine[5]);
		int b = Integer.parseInt(sLine[6]);

		fcircle.setColorfg(new Color(r, g, b));

		r = Integer.parseInt(sLine[7]);
		g = Integer.parseInt(sLine[8]);
		b = Integer.parseInt(sLine[9]);

		fcircle.setColorbg(new Color(r, g, b));

		model.add(fcircle);
	}

	/**
	 * This method adds Circle given from input string array
	 * 
	 * @param sLine input string array with parameters for new objects
	 */
	private void addCircleToModel(String[] sLine) {
		Circle circle = new Circle();

		circle.setCenterX(Integer.parseInt(sLine[1]));
		circle.setCenterY(Integer.parseInt(sLine[2]));
		circle.setRadius(Integer.parseInt(sLine[3]));

		int r = Integer.parseInt(sLine[4]);
		int g = Integer.parseInt(sLine[5]);
		int b = Integer.parseInt(sLine[6]);

		circle.setColor(new Color(r, g, b));
		model.add(circle);

	}

	/**
	 * This method adds Line given from input string array
	 * 
	 * @param sLine input string array with parameters for new objects
	 */
	private void addLineToModel(String[] sLine) {

		Line line = new Line();
		line.setStartX(Integer.parseInt(sLine[1]));
		line.setStartY(Integer.parseInt(sLine[2]));
		line.setEndX(Integer.parseInt(sLine[3]));
		line.setEndY(Integer.parseInt(sLine[4]));

		int r = Integer.parseInt(sLine[5]);
		int g = Integer.parseInt(sLine[6]);
		int b = Integer.parseInt(sLine[7]);
		line.setColor(new Color(r, g, b));

		model.add(line);

	}

	/**
	 * Exit action for this app. User is asked if he wants to save a document.
	 */
	private final Action exit = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkStatus()) {
				dispose();
			}
		}
	};

	private final Action export = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox png = new JCheckBox("png");
			JCheckBox gif = new JCheckBox("gif");
			JCheckBox jpg = new JCheckBox("jpg");

			JPanel exportPanel = new JPanel();
			exportPanel.add(png);
			exportPanel.add(gif);
			exportPanel.add(jpg);

			int result = JOptionPane.showConfirmDialog(null, exportPanel, "Choose export format",
					JOptionPane.PLAIN_MESSAGE);
			while (result == JOptionPane.OK_OPTION
					&& checkResponse(png.isSelected(), gif.isSelected(), jpg.isSelected())) {

				result = JOptionPane.showConfirmDialog(null, exportPanel, "Choose export format",
						JOptionPane.PLAIN_MESSAGE);

			}

			String format;
			if (png.isSelected()) {
				format = "png";
			} else if (gif.isSelected()) {
				format = "gif";
			} else {
				format = "jpg";
			}
			try {
				exportImage(format);
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}
	};
	
	private boolean checkResponse(boolean selected, boolean selected2, boolean selected3) {
		if (selected && !selected2 && !selected3)
			return false;
		if (!selected && selected2 && !selected3)
			return false;
		if (!selected && !selected2 && selected3)
			return false;

		Object[] options = { "OK" };
		int n = JOptionPane.showOptionDialog(JVDraw.this, "You must select only one format for export!", "Error",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		return true;
	}
	
	private void exportImage(String format) throws IOException {

		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Export file");
		if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JVDraw.this, "Nista nije exportano", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		openedFilePath = jfc.getSelectedFile().toPath();

		int size = model.getSize();
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int i = 0; i < size; i++) {
			model.getObject(i).accept(bbcalc);
		}

		Rectangle box = bbcalc.getBoundingBox();

		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = image.createGraphics();

		g.translate(-box.x, -box.y);

		GeometricalObjectPainter gOPainter = new GeometricalObjectPainter(g);
		for (int i = 0; i < size; i++) {
			model.getObject(i).accept(gOPainter);
		}
		g.dispose();
		File file = new File(openedFilePath.toString());
		ImageIO.write(image, format, file);
		JOptionPane.showMessageDialog(JVDraw.this, "Dokument je exportan", "Informacija",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
