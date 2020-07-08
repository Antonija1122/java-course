package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geomObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomObjects.GeometricalObject;

/**
 * This class implements GeometricalObjectEditor for object type FilledCircle.
 * 
 * @author antonija
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * reference to selected object
	 */
	private FilledCircle gObject;

	/**
	 * Parameters given by the user
	 */
	JTextField centerX = new JTextField();
	JTextField centerY = new JTextField();
	JTextField radius = new JTextField();
	JTextField fColor = new JTextField();
	JTextField bColor = new JTextField();
	/**
	 * Public constructor saves reference to selected object
	 * 
	 * @param gObject
	 */
	public FilledCircleEditor(GeometricalObject gObject) {
		super();
		this.gObject = (FilledCircle) gObject;
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
		colorPanel.add(new JLabel("Outline color:"));
		fColor.setText(String.format("#%02X%02X%02X", gObject.getColorfg().getRed(), gObject.getColorfg().getGreen(),
				gObject.getColorfg().getBlue()));
		colorPanel.add(fColor);

		colorPanel.add(new JLabel("Area color:"));
		bColor.setText(String.format("#%02X%02X%02X", gObject.getColorbg().getRed(), gObject.getColorbg().getGreen(),
				gObject.getColorbg().getBlue()));
		colorPanel.add(bColor);
		add(colorPanel);

	}

	@Override
	public void checkEditing() {
		Pattern colorPattern = Pattern.compile("^#([A-Fa-f0-9]{6})$");
		Matcher m1 = colorPattern.matcher(fColor.getText());
		Matcher m2 = colorPattern.matcher(bColor.getText());
		boolean isFColor = m1.matches();
		boolean isBColor = m2.matches();

		if (Integer.parseInt(centerX.getText()) >= 0 && Integer.parseInt(centerY.getText()) >= 0
				&& Integer.parseInt(radius.getText()) >= 0 && isFColor && isBColor) {
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
		gObject.setColorfg(hex2Rgb(fColor.getText()));
		gObject.setColorbg(hex2Rgb(bColor.getText()));
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
