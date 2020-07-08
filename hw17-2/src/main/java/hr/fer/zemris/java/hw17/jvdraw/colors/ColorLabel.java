package hr.fer.zemris.java.hw17.jvdraw.colors;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * This class implements ColorChangeListener and extends JLabel. This label
 * holds info about currently stored foreground and background color for JVDraw
 * app
 * 
 * @author antonija
 *
 */
public class ColorLabel extends JLabel implements ColorChangeListener {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * reference to foreground color
	 */
	private IColorProvider fgColorArea;

	/**
	 * reference to background color
	 */
	private IColorProvider bgColorArea;

	/**
	 * Public constructor adds this object as a listener to input IColorProvider
	 * providers. So that every time a change happens to selected color this object
	 * knows about it.
	 * 
	 * @param fgColorProvider provider of foreground color
	 * @param bgColorProvider provider of background color
	 */
	public ColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {

		this.bgColorArea = bgColorProvider;
		this.fgColorArea = fgColorProvider;

		bgColorProvider.addColorChangeListener(this);
		fgColorProvider.addColorChangeListener(this);

		setText("Foreground color: (" + fgColorProvider.getCurrentColor().getRed() + ", "
				+ fgColorProvider.getCurrentColor().getGreen() + ", " + fgColorProvider.getCurrentColor().getBlue()
				+ ")," + " background color: (" + bgColorProvider.getCurrentColor().getRed() + ", "
				+ bgColorProvider.getCurrentColor().getGreen() + ", " + bgColorProvider.getCurrentColor().getBlue()
				+ ").");

	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setText("Foreground color: (" + fgColorArea.getCurrentColor().getRed() + ", "
				+ fgColorArea.getCurrentColor().getGreen() + ", " + fgColorArea.getCurrentColor().getBlue() + "),"
				+ " background color: (" + bgColorArea.getCurrentColor().getRed() + ", "
				+ bgColorArea.getCurrentColor().getGreen() + ", " + bgColorArea.getCurrentColor().getBlue() + ").");
	}

}
