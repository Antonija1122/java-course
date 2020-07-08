package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class extends class JButton and it is created to represent number
 * buttons for class Calculator.
 * 
 * @author antonija
 *
 */
public class ButtonNumber extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor sets input text to button text and adds new ActionListener
	 * to this instance of ButtonNumber which is adding a digit to current number in calculator model
	 * 
	 * @param br digit that this button represents
	 * @param model model that tracks current state of calculator
	 */
	public ButtonNumber(int br, CalcModel model) {

		ActionListener e = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.insertDigit(br);
			}
		};

		this.setText(Integer.toString(br));
		addActionListener(e);
		setOpaque(true);
		setFont(this.getFont().deriveFont(30f));
	}

}
