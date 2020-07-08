package hr.fer.zemris.java.hw17.jvdraw.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * This class implements IColorProvider and extends JComponent.
 * 
 * @author antonija
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * reference to currently picked color
	 */
	Color selectedColor;
	/**
	 * List of listeners of this object
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Public constructor saves reference to initial color. Moue listener is added
	 * to this component so every time user clicks on this component color chooser
	 * is opened for them to choose a new color which is again stored in variable
	 * selectedColor
	 * 
	 * @param initialColor
	 */
	public JColorArea(Color initialColor) {
		this.selectedColor = initialColor;

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Select new color", selectedColor);
				if (newColor != null) {
					setModifiedColor(newColor);
				}

			}

		});

	}

	/**
	 * This method sets selectedColor to input color and fires message to listeners
	 * 
	 * @param color input color
	 */
	protected void setModifiedColor(Color color) {
		Color oldColor = selectedColor;
		selectedColor = color;
		listeners.forEach(doc -> doc.newColorSelected(this, oldColor, selectedColor));
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(selectedColor);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.dispose();

	}

}
