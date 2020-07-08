package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Class demonstrates implementation of GUI consisting of two separated parallel
 * lists with button "sljedeći" on the bottom part of window, which pressed
 * prints next prime number on both lists, starting with 1.
 * 
 * @author antonija 
 **/

public class PrimDemo extends JFrame {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	public PrimDemo() {

		setLocation(500, 200);
		setSize(500, 500);
		setTitle("PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();

	}


	/**
	 * Method that initializes GUI - Graphical User Interface
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 1));

		JButton dodaj = new JButton("sljedeći");
		bottomPanel.add(dodaj);

		dodaj.addActionListener(e -> {
			model.next();
		});

		JPanel central = new JPanel(new GridLayout(1, 5));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Method will starts with the program
	 * 
	 * @param args - there is no command line arguments
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
