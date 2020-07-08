package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Line;

/**
 * This class implements GeometricalObjectEditor for object type Line.
 * 
 * @author antonija
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * reference to selected object
	 */
	private Line gObject;

	/**
	 * Parameters given by the user
	 */
	JTextField startX = new JTextField();
	JTextField startY = new JTextField();
	JTextField endX = new JTextField();
	JTextField endY = new JTextField();
	JTextField color = new JTextField();

	/**
	 * Public constructor saves reference to selected object
	 * 
	 * @param gObject
	 */
	public LineEditor(GeometricalObject gObject) {
		super();
		this.gObject = (Line) gObject;
		createEditorPanel();

	}

	/**
	 * This method creates editor panel for user to give parameters which are then
	 * saved to private variables
	 */
	private void createEditorPanel() {

		JPanel panelStart = new JPanel();

		panelStart.add(new JLabel("Start point   x:"));
		startX.setText(String.valueOf(gObject.getStartX()));
		panelStart.add(startX);

		panelStart.add(new JLabel("y:"));
		startY.setText(String.valueOf(gObject.getStartY()));
		panelStart.add(startY);
		add(panelStart, BorderLayout.PAGE_START);

		JPanel endPanel = new JPanel();

		endPanel.add(new JLabel("End point   x:"));
		endX.setText(String.valueOf(gObject.getEndX()));
		endPanel.add(endX);

		endPanel.add(new JLabel("End y:"));
		endY.setText(String.valueOf(gObject.getEndY()));
		endPanel.add(endY);
		add(endPanel, BorderLayout.CENTER);

		JPanel colorPanel = new JPanel();
		colorPanel.add(new JLabel("Color:"));
		color.setText(String.format("#%02X%02X%02X", gObject.getColor().getRed(), gObject.getColor().getGreen(),
				gObject.getColor().getBlue()));
		colorPanel.add(color);
		add(colorPanel);

	}

	@Override
	public void checkEditing() {
		Pattern colorPattern = Pattern.compile("^#([A-Fa-f0-9]{6})$");
		Matcher m = colorPattern.matcher(color.getText());
		boolean isColor = m.matches();

		if (Integer.parseInt(endY.getText()) >= 0 && Integer.parseInt(endX.getText()) >= 0
				&& Integer.parseInt(startX.getText()) >= 0 && Integer.parseInt(startY.getText()) >= 0 && isColor) {
			acceptEditing();
			return;
		}

		throw new NumberFormatException("Krivi unos parametara za liniju!");
	}

	@Override
	public void acceptEditing() {
		gObject.setStartX(Integer.parseInt(startX.getText()));
		gObject.setStartY(Integer.parseInt(startY.getText()));
		gObject.setEndX(Integer.parseInt(endX.getText()));
		gObject.setEndY(Integer.parseInt(endY.getText()));
		gObject.setColor(hex2Rgb(color.getText()));
		gObject.notifyAllListeners();
	}

	/**
	 * This method creates new Color object from string representation given in
	 * input parameters
	 * 
	 * @param colorStr input string representation for color
	 * @return Color
	 */
	public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

}
