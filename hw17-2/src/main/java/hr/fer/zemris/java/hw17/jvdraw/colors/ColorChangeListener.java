package hr.fer.zemris.java.hw17.jvdraw.colors;

import java.awt.Color;
/**
 * This interface represents listeners for IColorProvider objects
 * @author antonija
 *
 */
public interface ColorChangeListener {
	
	/**
	 * This method does specified action when new color is selected for IColorProvider
	 * @param source IColorProvider object
	 * @param oldColor old Color
	 * @param newColor new Color selected
	 */
	 public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);


}
