package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * This class represents Coloring algorithm that implements : Consumer<Pixel>,
 * Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>
 * 
 * @author antonija
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	/**
	 * private pixel instance
	 */
	private Pixel reference;
	/**
	 * picture represents picture that pixels are taken from
	 */
	private Picture picture;

	/**
	 * Color that pixels that are accepted have to be colored with
	 */
	private int fillColor;

	/**
	 * Color of all pixels that have to be accepted
	 */
	private int refColor;

	/**
	 * Public constructor gives reference pixel, picture and fillColor so private
	 * values can be initialized.
	 * 
	 * @param reference pixel which is tested to be accepted
	 * @param picture that i colored
	 * @param fillColor color that accepted pixels have to be colored
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pixel get() {
		return reference;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Pixel p) {
		return picture.getPixelColor(p.x, p.y) == this.refColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Pixel> apply(Pixel p) {

		LinkedList<Pixel> newList = new LinkedList<Pixel>();
		if (isValid(p.x - 1, p.y))
			newList.add(new Pixel(p.x - 1, p.y));

		if (isValid(p.x + 1, p.y))
			newList.add(new Pixel(p.x + 1, p.y));

		if (isValid(p.x, p.y + 1))
			newList.add(new Pixel(p.x, p.y + 1));

		if (isValid(p.x, p.y - 1))
			newList.add(new Pixel(p.x, p.y - 1));

		return newList;

	}

	private boolean isValid(int x, int y) {
		if (x < 0 || x > picture.getWidth() - 1)
			return false;
		if (y < 0 || y > picture.getHeight() - 1)
			return false;

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Pixel p) {
		picture.setPixelColor(p.x, p.y, fillColor);
	}

}
