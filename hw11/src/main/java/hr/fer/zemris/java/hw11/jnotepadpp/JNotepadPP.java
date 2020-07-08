package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * This class is implementation of Notepad++ in java. JNotepadPP is a multiple
 * document model which has actions in menus, toolbar and status bar. This
 * program is able to create new documents and load and save documents from the
 * disk. Application supports the use of English, Croatian and German language.
 * 
 * @author antonija
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * private MultipleDocumentModel for supporting multiple document editing
	 */
	private DefaultMultipleDocumentModel editor;
	/**
	 * Listener that updates status bar
	 */
	private DocumentListener documentListener;
	/**
	 * Listener for updating information about current position of caret in status
	 * bar
	 */
	private CaretListener caretListener;
	/**
	 * Listener that tracks if there is selected part in current document
	 */
	private ChangeListener changeListener;

	/**
	 * Panel that holds all other panels and labels for status bar
	 */
	JPanel statusPanel;
	/**
	 * Label that holds information about length of current document
	 */
	private JLabel length;
	/**
	 * Label that holds information about current caret position
	 */
	private JLabel caretPosition;
	/**
	 * Label for clock
	 */
	private JLabel dateTime;
	/**
	 * Timer for tracking time
	 */
	Timer t;
	/**
	 * Local variable for current language, serves for comparing strings
	 */
	Locale locale = new Locale("en");

	/**
	 * ILocalizationProvider serving as s translator for this application
	 */
	private ILocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Formatter for date and time
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Public constructor defines closing actions, menus, toolbar, labels,
	 * listeners, and starts application
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 800);
		initGUI();
	}

	/**
	 * Initialization of GUI creates menus, toolbar, labels, listeners etc. and puts
	 * all components in JFrame of JNotepad++
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		setLocationRelativeTo(null);

		JPanel notepadPanel = new JPanel(new BorderLayout());
		JPanel statusbar = new JPanel(new GridLayout(1, 1));
		statusbar.setMaximumSize(new Dimension(20, 20));

		// Configure statusbar
		JPanel docInfo = new JPanel(new GridLayout(1, 1));
		docInfo.add(length = new JLabel("length : 0", SwingConstants.LEFT));
		docInfo.add(caretPosition = new JLabel("details", SwingConstants.LEFT));

		// docInfo.setBorder(BorderFactory.createMatteBorder(2, 1, 0, 1, Color.GRAY));
		JPanel Clock = new JPanel(new GridLayout(1, 1));
		Clock.add(dateTime = new JLabel("Clock", SwingConstants.RIGHT));

		// Cofigure borders
		length.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));
		caretPosition.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));
		dateTime.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));
		statusbar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));

		statusbar.add(docInfo);
		statusbar.add(Clock);
		
		// Configure editor
		editor = new DefaultMultipleDocumentModel(flp);
		editor.createNewDocument();
		editor.addMultipleDocumentListener(createNewMultipleDocumentListener());

		// Add editor and statusbar to panel
		notepadPanel.add(editor, BorderLayout.CENTER);
		notepadPanel.add(statusbar, BorderLayout.PAGE_END);
		// Set default title
		setTitle("(unnamed) - JNotepad++");

		// Add panel to frame
		cp.add(notepadPanel, BorderLayout.CENTER);

		configureActions();
		createMenus();
		createToolbars();

		documentListener = createDocumentListener();
		editor.getCurrentDocument().getTextComponent().getDocument().addDocumentListener(documentListener);
		caretListener = createCarretListener();
		editor.getCurrentDocument().getTextComponent().addCaretListener(caretListener);
		changeListener = createChangeListener();
		editor.getCurrentDocument().getTextComponent().getCaret().addChangeListener(changeListener);
		updateStats();
		updateCase();
		Sat();

	}

	/**
	 * This method creates Menu bar and adds all actions of this Notepad application
	 * in this menu bar.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		// Add File menu
		JMenu file = new LJmenu("file", flp);
		mb.add(file);
		file.add(new JMenuItem(createNewDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(close));
		file.addSeparator();
		file.add(new JMenuItem(exitAplication));
		// Add Edit menu
		JMenu edit = new LJmenu("edit", flp);
		mb.add(edit);
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(paste));
		// Add View menu
		JMenu view = new LJmenu("view", flp);
		mb.add(view);
		view.add(new JMenuItem(statistics));

		JMenu languages = new LJmenu("languages", flp);
		languages.add(new JMenuItem(en));
		languages.add(new JMenuItem(hr));
		languages.add(new JMenuItem(de));
		mb.add(languages);

		JMenu tools = new LJmenu("tools", flp);
		JMenu changeCase = new LJmenu("changeCase", flp);
		changeCase.add(new JMenuItem(toLowercase));
		changeCase.add(new JMenuItem(toUppercase));
		changeCase.add(new JMenuItem(invertCase));
		tools.add(changeCase);

		JMenu sort = new LJmenu("sort", flp);
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));

		tools.add(sort);
		tools.add(new JMenuItem(unique));
		mb.add(tools);

		setJMenuBar(mb);

	}

	/**
	 * This method creates Tool bar and adds all actions of this Notepad application
	 * in this toolbar.
	 */
	private void createToolbars() {
		JToolBar tb = new JToolBar();
		tb.add(new JButton(createNewDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(close));
		tb.add(new JButton(cut));
		tb.add(new JButton(copy));
		tb.add(new JButton(paste));
		tb.add(new JButton(statistics));
		tb.add(new JButton(exitAplication));
//		tb.add(new JButton(toLowercase));
//		tb.add(new JButton(toUppercase));
//		tb.add(new JButton(invertCase));
		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * This method configures actions, sets their ACCELERATOR_KEY and MNEMONIC_KEY
	 * and adds window listener to check state of application when user wants to
	 * terminate the program
	 */
	private void configureActions() {

		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);

		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

		exitAplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		statistics.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statistics.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		en.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		en.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		hr.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		hr.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		de.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		de.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);

		descending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		descending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);

		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (checkStatus()) {
					dispose();
					t.stop();
				}
			}

		});

	}

	/**
	 * This method asks user if he wants to save unsaved files, and to confirm if he
	 * wants to exit application
	 * 
	 * @return
	 */
	private boolean checkStatus() {
		editor.forEach(t -> {
			if (t.isModified()) {
				saveFileOption(t);
			}
		});
		int Answer = JOptionPane.showConfirmDialog(editor.getParent(), flp.getString("checkStatusQ"),
				flp.getString("quit"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		return Answer == 0;
	}

	/**
	 * This method as user if he wants to save input file, if yes document is saved.
	 * 
	 * @param file
	 */
	private void saveFileOption(SingleDocumentModel file) {
		String name = (file.getFilePath() != null) ? file.getFilePath().getFileName().toString() : "(unname)";

		int Answer = JOptionPane.showConfirmDialog(editor.getParent(), flp.getString("saveFileQ") + name + "? ", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (Answer == 0)
			editor.saveDocument(file, file.getFilePath());

	}

	/**
	 * Action sets current language to English
	 */
	private final Action en = new LocalizableAction("en", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
			locale.setDefault(new Locale("en"));
		}
	};

	/**
	 * Action sets current language to Croatian
	 */
	private final Action hr = new LocalizableAction("hr", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
			locale.setDefault(new Locale("hr"));
		}
	};

	/**
	 * Action sets current language to German
	 */
	private final Action de = new LocalizableAction("de", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
			locale.setDefault(new Locale("de"));
		}
	};

	/**
	 * This action offers user to choose which document he wants to load to
	 * application and adds that document to editor
	 */
	private final Action openDocument = new LocalizableAction("open", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();

			jfc.setDialogTitle("open file");
			if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path openedFilePath = jfc.getSelectedFile().toPath();
			if (!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("canReadQ"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			editor.loadDocument(openedFilePath);

		}
	};

	/**
	 * This action creates new blank document in editor
	 */
	private final Action createNewDocument = new LocalizableAction("new", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.createNewDocument();
		}

	};

	/**
	 * This action saves currently opened document in editor
	 */
	private final Action saveDocument = new LocalizableAction("save", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.saveDocument(editor.getCurrentDocument(), editor.getCurrentDocument().getFilePath());
		}

	};

	/**
	 * This action saves currently opened document in editor to location chosen by
	 * user
	 */
	private final Action saveAsDocument = new LocalizableAction("saveAs", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.saveDocument(editor.getCurrentDocument(), null);
		}

	};

	/**
	 * This action asks user to confirm exit and exits the program
	 */
	private final Action exitAplication = new LocalizableAction("exit", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkStatus()) {
				dispose();
				t.stop();
			}
		}
	};

	/**
	 * This action closes current document
	 */
	private final Action close = new LocalizableAction("close", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getCurrentDocument() != null && editor.getCurrentDocument().isModified())
				saveFileOption(editor.getCurrentDocument());
			editor.closeDocument(editor.getCurrentDocument());

		}
	};

	/**
	 * This action cuts selected part of text in current document
	 */
	private final Action cut = new LocalizableAction("cut", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().cut();
		}
	};

	/**
	 * This action copies selected part of text in current document
	 */
	private final Action copy = new LocalizableAction("copy", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().copy();
		}
	};

	/**
	 * This action pastes copied part to current position of caret in text in
	 * current document
	 */
	private final Action paste = new LocalizableAction("paste", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().paste();
		}
	};

	/**
	 * This actions shows information message to user about length and other
	 * information about the text in current document
	 */
	private final Action statistics = new LocalizableAction("statistics", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			statisticalInfo();
		}

	};

	/**
	 * This action converts selected part of text in to upper case letters
	 */
	private final Action toUppercase = new LocalizableAction("toUppercase", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("toUpper");
		}

	};

	/**
	 * This action converts selected part of text in to lower case letters
	 */
	private final Action toLowercase = new LocalizableAction("toLowercase", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("toLower");
		}
	};

	/**
	 * This action inverts case of selected part of text
	 */
	private final Action invertCase = new LocalizableAction("invertCase", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("invert");
		}

	};

	/**
	 * This action sorts selected lines in ascending, according to string comparator
	 */
	private final Action ascending = new LocalizableAction("ascending", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sort("ascending");
		}

	};

	/**
	 * This action sorts selected lines in descending, according to string
	 * comparator
	 */
	private final Action descending = new LocalizableAction("descending", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sort("descending");

		}

	};

	/**
	 * This action removes all duplicate lines in current document
	 */
	private final Action unique = new LocalizableAction("unique", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {

			Document doc = editor.getCurrentDocument().getTextComponent().getDocument();
			int position = editor.getCurrentDocument().getTextComponent().getCaretPosition();
			int[] rowIndexes = getRowIndexes();

			if (rowIndexes == null) {
				return;
			}
			try {
				String[] lines = doc.getText(0, doc.getLength()).replaceAll("\\s+$", "").split("\r\n|\r|\n");
				if (lines.length == 0) {
					return;
				}
				String[] newArray = Arrays.copyOfRange(lines, rowIndexes[0], rowIndexes[1] + 1);

				StringBuilder resultBuilder = new StringBuilder();
				Set<String> alreadyPresent = new HashSet<String>();

				boolean first = true;
				for (String line : newArray) {
					if (!alreadyPresent.contains(line)) {
						if (first)
							first = false;
						else
							resultBuilder.append("\n");
						if (!alreadyPresent.contains(line))
							if (line == null) {
								resultBuilder.append("\n");
							} else {
								resultBuilder.append(line);
							}
					}
					alreadyPresent.add(line);
				}
				String result = "";
				for (int i = 0; i < rowIndexes[0]; i++) {
					result += lines[i] + "\n";
				}
				result += resultBuilder.toString() + "\n";
				for (int i = rowIndexes[1] + 1; i < lines.length; i++) {
					result += lines[i] + "\n";
				}
				doc.remove(0, doc.getLength());
				doc.insertString(0, result, null);
				// vraca kursor
				editor.getCurrentDocument().getTextComponent().getCaret().setDot(position);
			} catch (BadLocationException e1) {
			}
		}
	};

	/**
	 * This method sorts selected lines in current document in descending or
	 * ascending order (depending on input string), according to string comparator
	 * 
	 * @param sorting "ascending" or "descending"
	 */
	private void sort(String sorting) {

		Document doc = editor.getCurrentDocument().getTextComponent().getDocument();
		Collator col = Collator.getInstance(locale);
		int position = editor.getCurrentDocument().getTextComponent().getCaretPosition();
		int[] rowIndexes = getRowIndexes();
		if (rowIndexes == null) {
			return;
		}
		try {
			String[] lines = doc.getText(0, doc.getLength()).replaceAll("\\s+$", "").split("\r\n|\r|\n");
			if (lines.length == 0) {
				return;
			}
			String[] newArray = Arrays.copyOfRange(lines, rowIndexes[0], rowIndexes[1] + 1);
			List<String> list = new LinkedList<String>(Arrays.asList(newArray));
			while (list.remove(null)) {
			}
			if (sorting.equals("ascending")) {
				Collections.sort(list, col);
			} else {
				Collections.sort(list, col.reversed());
			}
			newArray = list.toArray(new String[list.size()]);
			int br = 0;
			String text = "";
			for (int i = 0; i < lines.length; i++) {
				if (i >= rowIndexes[0] && i <= rowIndexes[1]) {
					text += newArray[br] + "\n";
					br++;
				} else {
					text += lines[i] + "\n";
				}
			}
			doc.remove(0, doc.getLength());
			doc.insertString(0, text, null);
			// vraca kursor
			editor.getCurrentDocument().getTextComponent().getCaret().setDot(position);

		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method inverts case of input and returns new string
	 * 
	 * @param text input string
	 * @return inverted string
	 */
	private String toggleText(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			}
		}
		return new String(chars);
	}

	/**
	 * Method returns numbers of rows in which are start and end of selected text in
	 * current document
	 * 
	 * @return indexes of rows of start and end of selected text
	 */
	protected int[] getRowIndexes() {
		Document doc = editor.getCurrentDocument().getTextComponent().getDocument();

		Caret caret = editor.getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getDot(), caret.getMark());
		int end = Math.max(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());
		if (len == 0)
			return null;

		Element root = doc.getDefaultRootElement();
		int rowFirst = root.getElementIndex(start);
		int rowLast = root.getElementIndex(end);

		return new int[] { rowFirst, rowLast };
	}

	/**
	 * This method changes cases for all letter characters in selected part of
	 * current document text, depending on input string. If input string is
	 * "toUpper" characters are converted to upper case, if "toLower" characters are
	 * converted to lower case, otherwise characters cases are inverted.
	 * 
	 * @param caseString
	 */
	private void changeCase(String caseString) {
		Document doc = editor.getCurrentDocument().getTextComponent().getDocument();

		Caret caret = editor.getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());
		if (len == 0)
			return;

		String text;
		try {
			text = doc.getText(start, len);
			if (caseString.equals("toLower")) {
				text = text.toLowerCase();
			} else if (caseString.equals("toUpper")) {
				text = text.toUpperCase();
			} else {
				text = toggleText(text);
			}
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * This method shows user message with informations about current document.
	 */
	private void statisticalInfo() {
		int numberOfCharacters = getNumberOfCharacters();
		int numberOfNonBlankCharacters = getNumberOfNonBlankCharacters();
		int numberOfLines = getNumberOfLines();

		JOptionPane.showMessageDialog(editor.getParent(),
				flp.getString("stat1") + " " + numberOfCharacters + " " + flp.getString("stat2") + " "
						+ numberOfNonBlankCharacters + " " + flp.getString("stat3") + " " + numberOfLines + " "
						+ flp.getString("stat4"));
	}

	/**
	 * This method counts and returns number of lines in current document
	 * 
	 * @return number of lines in current document
	 */
	private int getNumberOfLines() {
		return editor.getCurrentDocument().getTextComponent().getText().split("\r\n|\r|\n").length;
	}

	/**
	 * This method returns number of characters in current document
	 * 
	 * @return number of lines in current document
	 */
	private int getNumberOfCharacters() {
		return editor.getCurrentDocument().getTextComponent().getText().length();
	}

	/**
	 * This method returns number of non-blank characters in current document
	 * 
	 * @return number of non-blank lines in current document
	 */
	private int getNumberOfNonBlankCharacters() {
		return editor.getCurrentDocument().getTextComponent().getText().replaceAll("\\s+", "").replaceAll("\n+", "")
				.replaceAll("\t+", "").length();
	}

	/**
	 * Method creates new document MultipleDocumentListener and returns it. This
	 * listener tracks changes to selection of current document, adding new
	 * documents and removing documents
	 * 
	 * @return
	 */
	private MultipleDocumentListener createNewMultipleDocumentListener() {
		return new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				int i = editor.getSelectedIndex();
				if (i != -1) {
					editor.remove(i);
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				updateTitle(model);
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null && currentModel != null) {
					// Unregister old
					previousModel.getTextComponent().getDocument().removeDocumentListener(documentListener);
					previousModel.getTextComponent().removeCaretListener(caretListener);
					previousModel.getTextComponent().getCaret().removeChangeListener(changeListener);
				}
				// ako se dodaje prvi dokument stvori nove listenere
				documentListener = createDocumentListener();
				caretListener = createCarretListener();
				changeListener = createChangeListener();
				// register new
				currentModel.getTextComponent().getDocument().addDocumentListener(documentListener);
				currentModel.getTextComponent().addCaretListener(caretListener);
				currentModel.getTextComponent().getCaret().addChangeListener(changeListener);
				// Update stats
				updateStats();
				updateCase();

			}

		};
	}

	/**
	 * This method creates new DocumentListener that tracks information about
	 * current document statistics. This informations are shown in status bar of
	 * this editor
	 * 
	 * @return new DocumentListener
	 */
	private DocumentListener createDocumentListener() {

		return new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateStats();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateStats();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateStats();
			}

		};
	}

	/**
	 * This method creates new CaretListener that tracks current position of caret
	 * and shows this informations in status bar of this editor
	 * 
	 * @return new CaretListener
	 */
	private CaretListener createCarretListener() {

		return new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				updateStats();
			}
		};
	}

	/**
	 * This method creates new ChangeListener that tracks if there is selected part
	 * of text in current document. If there is selected this listener calls method
	 * to enable the use of actions toLowercase, toUppercase, invertCase, ascending,
	 * descending and unique
	 * 
	 * @return new ChangeListener
	 */
	private ChangeListener createChangeListener() {

		return new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateCase();
			}
		};
	}

	/**
	 * If there is selected part in current document text this method enables the
	 * use of actions toLowercase, toUppercase, invertCase, ascending, descending
	 * and unique.
	 */
	private void updateCase() {
		boolean imaSelekcije = editor.getCurrentDocument().getTextComponent().getCaret().getDot() != editor
				.getCurrentDocument().getTextComponent().getCaret().getMark();
		toLowercase.setEnabled(imaSelekcije);
		toUppercase.setEnabled(imaSelekcije);
		invertCase.setEnabled(imaSelekcije);
		ascending.setEnabled(imaSelekcije);
		descending.setEnabled(imaSelekcije);
		unique.setEnabled(imaSelekcije);

	}

	/**
	 * This method creates new timer that writes and updates time in status bar
	 */
	private void Sat() {
		t = new Timer(100, (e) -> dateTime.setText(sdf.format(new Date())));
		t.start();
	}

	/**
	 * This method updates information on status bar. Informations are: length,
	 * caret position and selected text length
	 */
	private void updateStats() {
		length.setText(flp.getString("length") + " " + getNumberOfCharacters() + " ".repeat(15));
		// ln: 18 Col: 27 Sel:11

		int rowNum = 1;
		int colNum = 1;
		int sel = 0;

		try {
			sel = Math.abs(editor.getCurrentDocument().getTextComponent().getCaret().getDot()
					- editor.getCurrentDocument().getTextComponent().getCaret().getMark());
			int position = editor.getCurrentDocument().getTextComponent().getCaretPosition();

			rowNum = editor.getCurrentDocument().getTextComponent().getLineOfOffset(position);
			colNum = position - editor.getCurrentDocument().getTextComponent().getLineStartOffset(rowNum);
			rowNum++;
			colNum++;

		} catch (BadLocationException e) {

		}

		caretPosition.setText(
				" Ln: " + rowNum + " " + flp.getString("col") + " " + colNum + " " + flp.getString("sel") + " " + sel);
	}

	/**
	 * Ever time current document changes this method changes editor's title to the
	 * name of current document
	 * 
	 * @param model current model
	 */
	private void updateTitle(SingleDocumentModel model) {
		if (model == null && model == null || model.getFilePath() == null) {
			setTitle(flp.getString("(unnamed)") + " - JNotepad++");
		} else {
			setTitle(model.getFilePath().toString() + " - JNotepad++");
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
