package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This class implements GeometricalObjectEditor for object type Circle.
 * 
 * @author antonija
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * reference to selected object
	 */
	private Circle gObject;

	/**
	 * Parameters given by the user
	 */
	JTextField centerX = new JTextField();
	JTextField centerY = new JTextField();
	JTextField radius = new JTextField();
	JTextField color = new JTextField();

	/**
	 * Public constructor saves reference to selected object
	 * 
	 * @param gObject
	 */
	public CircleEditor(GeometricalObject gObject) {
		super();
		this.gObject = (Circle) gObject;
		createEditorPanel();
	}

	/**
	 * This method creates editor panel for user to give parameters which are then
	 * saved to private variables
	 */
	private void createEditorPanel() {

		// JPanel panelStart=new JPanel();

		JPanel panelCenter = new JPanel();

		panelCenter.add(new JLabel("Center point   x:"));
		centerX.setText(String.valueOf(gObject.getCenterX()));
		panelCenter.add(centerX);

		panelCenter.add(new JLabel("y:"));
		centerY.setText(String.valueOf(gObject.getCenterY()));
		panelCenter.add(centerY);
		add(panelCenter, BorderLayout.PAGE_START);

		JPanel radiusPanel = new JPanel();

		radiusPanel.add(new JLabel("Radius: "));
		radius.setText(String.valueOf(gObject.getRadius()));
		radiusPanel.add(radius);
		add(radiusPanel, BorderLayout.CENTER);

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

		if (Integer.parseInt(centerX.getText()) >= 0 && Integer.parseInt(centerY.getText()) >= 0
				&& Integer.parseInt(radius.getText()) >= 0 && isColor) {
			acceptEditing();
			return;
		}

		throw new NumberFormatException("Krivi unos parametara za liniju!");
	}

	@Override
	public void acceptEditing() {
		gObject.setCenterX(Integer.parseInt(centerX.getText()));
		gObject.setCenterY(Integer.parseInt(centerY.getText()));
		gObject.setRadius(Integer.parseInt(radius.getText()));
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
