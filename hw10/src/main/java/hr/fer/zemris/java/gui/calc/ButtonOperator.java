package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class extends class JButton and it is created to represent operation
 * buttons for class Calculator.
 * 
 * @author antonija
 *
 */
public class ButtonOperator extends JButton {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String invName;

	

	/**
	 * Public constructor sets input text to button text and adds new ActionListener
	 * to this instance of ButtonNumber which is performing an operation represented
	 * by this button in calculator model
	 * 
	 * @param name    name of operation that this button represents
	 * @param l action to be added to this button
	 */
	public ButtonOperator(String name, String invName, ActionListener l) {
		this.setText(name);
		this.name=name;
		this.invName=invName;
		addActionListener(l);
		setOpaque(true);
	}
	
	public void setInvName() {
		setText(invName);
	}
	
	public void setName() {
		setText(name);
	}

}
