package hr.fer.zemris.java.hw17.jvdraw.colors;

import java.awt.Color;
/**
 * This interface represents color providers for JVDraws background and foreground colors
 * @author antonija
 *
 */
public interface IColorProvider {

	/**
	 * This method returns currently selected color
	 * @return
	 */
	public Color getCurrentColor();

	/**
	 * This method adds listener to this object
	 * @param l new listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * This method removes listener from this object
	 * @param l old listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
