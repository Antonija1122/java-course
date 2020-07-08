package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * CalcLayout is a class that implenets interface LayoutManager2 and it is used
 * in class Calculator to make component layout for display, operations buttons
 * and number buttons.
 * 
 * @author antonija
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * distance between rows and colums
	 */
	private int distance;

	/**
	 * number of rows in layout
	 */
	private final int rows = 5;

	/**
	 * number of columns in layout
	 */
	private final int columns = 7;

	/**
	 * Map that contains all components with values instances of RCPosition class
	 * that has information for number of row and column of each component
	 */
	private Map<Component, RCPosition> components = new HashMap<>();

	/**
	 * Public constant sets distance of rows and columns to input value
	 * 
	 * @param distance between rows and columns
	 */
	public CalcLayout(int distance) {
		this.distance = distance;
	}

	/**
	 * Public constant sets distance of rows and columns to 0
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calcDimension(parent, Component::getPreferredSize);
	}

	/**
	 * Interface with one getter method for sizes of input component
	 * 
	 * @author antonija
	 *
	 */
	private interface SizeGetter {
		Dimension getSize(Component comp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calcDimension(parent, Component::getMinimumSize);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calcDimension(target, Component::getMaximumSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {

		// calculate available height
		Insets parentInsets = parent.getInsets();
		int availableHeight = Math.max(0, parent.getHeight() - distance * 4 - parentInsets.top - parentInsets.bottom);
		int availableWidth = Math.max(0, parent.getWidth() - distance * 6 - parentInsets.left - parentInsets.right);

		// Calculate width array

		int[] widthArray = calculateDimensions(availableWidth, 7);
		int[] heightArray = calculateDimensions(availableHeight, 5);

		int n = parent.getComponentCount();
		for (int i = 0; i < n; i++) {
			Component c = parent.getComponent(i);
			RCPosition currentPosition = components.get(c);
			if (currentPosition.getX() == 1 && currentPosition.getY() == 1) {
				c.setBounds(parentInsets.left, parentInsets.top, getSum(widthArray, 5) + 4 * distance, heightArray[0]);
			} else {

				int horizontalIndex = (currentPosition.getY() - 1);
				int verticalIndex = (currentPosition.getX() - 1);

				int hightOffset = getSum(heightArray, verticalIndex) + verticalIndex * distance;
				int widthOffset = getSum(widthArray, horizontalIndex) + horizontalIndex * distance;

				c.setBounds(parentInsets.left + widthOffset, parentInsets.top + hightOffset,
						widthArray[horizontalIndex], heightArray[verticalIndex]);
			}
		}

	}

	/**
	 * This method returns sum of input array of widths
	 * 
	 * @param widthArray array of heights or widths
	 * @param n          size of array
	 * @return sum of array elements
	 */
	private int getSum(int[] widthArray, int n) {
		int i = 0;
		int sum = 0;
		while (i < n) {
			sum += widthArray[i];
			i++;
		}
		return sum;
	}

	/**
	 * This method returns array of widths or heights. If sum of widths is divisible
	 * by number of colums then all widths are the same size else they are normally distributed.
	 * 
	 * @param available width of height for columns or rows
	 * @param n number of rows or columns
	 * @return  int[] of sizes of rows or columns
	 */
	private static int[] calculateDimensions(int available, int n) {

		int[] dimensionArray = new int[n];
		Arrays.fill(dimensionArray, (int) Math.round((double) available / n));

		if (available == 0 || (dimensionArray[0] * n - available) == 0)
			return dimensionArray;

		int offset = available - dimensionArray[0] * n;
		int leftIndex = n / 2;
		int rightIndex = leftIndex;
		int increment = offset / Math.abs(offset);

		// If the number is odd
		if ((int) Math.abs(offset) % 2 == 1) {
			dimensionArray[leftIndex] += increment;
			leftIndex--;
			rightIndex++;
			offset -= increment;
		}

		while (offset != 0) {
			leftIndex--;
			rightIndex++;
			dimensionArray[leftIndex] += increment;
			dimensionArray[rightIndex] += increment;
			offset -= 2 * increment;
		}
		return dimensionArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {

		if (!(constraints instanceof RCPosition)) {
			throw new CalcLayoutException();
		}
		RCPosition newConstraints = (RCPosition) constraints;
		if (newConstraints.getX() < 1 || newConstraints.getX() > rows) {
			throw new CalcLayoutException();

		}
		if (newConstraints.getY() < 1 || newConstraints.getY() > columns) {
			throw new CalcLayoutException();

		}
		if (newConstraints.getX() == 1 && newConstraints.getY() > 1 && newConstraints.getY() < 6) {
			throw new CalcLayoutException();
		}
		if (components.containsValue(newConstraints)) {
			throw new CalcLayoutException();
		}

		components.put(comp, newConstraints);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * This method calculates dimensions preferred sizes for component
	 * 
	 * @param parent parent component
	 * @param getter instance of class SizeGetter for getting components sizes
	 * @return preferred size of component
	 */
	private Dimension calcDimension(Container parent, SizeGetter getter) {

		Dimension dim = new Dimension(0, 0);
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {

			Component c = entry.getKey();
			Dimension cdim = getter.getSize(c);
			if (cdim != null && !entry.getValue().equals(new RCPosition(1, 1))) {
				dim.width = Math.max(dim.width, cdim.width);
			} else if (entry.getValue().equals(new RCPosition(1, 1))) {
				dim.width = Math.max(dim.width, (cdim.width - 4 * distance) / 5);
			}
			dim.height = Math.max(dim.height, cdim.height);
		}
		dim.width = columns * dim.width + (columns - 1) * distance;
		dim.height = rows * dim.height + (rows - 1) * distance;

		Insets parentInsets = parent.getInsets();
		dim.width += parentInsets.left + parentInsets.right;
		dim.height += parentInsets.top + parentInsets.bottom;

		return dim;
	}

}
