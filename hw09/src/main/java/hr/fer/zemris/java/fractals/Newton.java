package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents Newton-Raphson iteration-based fractal viewer. After
 * starting the program user must enter at least two roots for polynomial. After
 * that, program generates iterations until it reaches a predefined number of
 * iterations (for example 16) or until module |zn+1-zn| becomes adequately
 * small (for example, convergence threshold 1E-3). Once stopped, program will
 * find the closest function root for final point zn, and color the point c
 * based on index of that root (root indexes start from 1). However, if program
 * stopped on a zn that is further than predefined threshold from all roots,
 * program will color the point c with a color associated with index 0. The
 * result is graphic representation of this fractal. User can again call this
 * program by selecting part of the image and program will recreate graphic
 * representation for that specific part of the image.
 * 
 * @author antonija
 *
 */
public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int numberOfRoots = 1;
		Scanner sc = new Scanner(System.in);
		String kraj = "done";
		List<Complex> roots = new ArrayList<>();
		while (true) {
			System.out.printf("Root " + numberOfRoots + ">");
			if (sc.hasNext()) {
				String c = sc.nextLine();
				if (c.equals(kraj)) {
					break;
				}
				try {
					String[] com = Utility.parsing(c);
					roots.add(new Complex(Double.parseDouble(com[0]), Double.parseDouble(com[1])));
					numberOfRoots++;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input for complex number root.");
				}
			}
		}
		if (roots.size() <= 2) {
			System.out.println("Invalid input for complex roots. You have to enter at least two roots");
			System.exit(-1);
		}

		Complex[] array = roots.toArray(new Complex[roots.size()]);
		ComplexRootedPolynomial rootsPoly = new ComplexRootedPolynomial(Complex.ONE, array);
		ComplexPolynomial polynom = rootsPoly.toComplexPolynom();

		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MyProducer(rootsPoly, polynom));

		sc.close();
	}

	/**
	 * This class represents a part of the job for Newton program. This clas
	 * calculates data for specific part of the image. More precisely it calculates
	 * color for pixels in range x: 0-width and y: yMin+yMax
	 * 
	 * @author antonija
	 *
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		/**
		 * minimal value for graphic representation of real part of complex number
		 */
		double reMin;
		/**
		 * max value for graphic representation of real part of complex number
		 */
		double reMax;
		/**
		 * minimal value for graphic representation of imaginary part of complex number
		 */
		double imMin;
		/**
		 * max value for graphic representation of imaginary part of complex number
		 */
		double imMax;
		/**
		 * Width of graphical image
		 */
		int width;
		/**
		 * height of graphical image
		 */
		int height;
		/**
		 * minimal value of iteration of y for this job
		 */
		int yMin;
		/**
		 * Max value of iteration of y for this job
		 */
		int yMax;
		/**
		 * Index of current data(color for pixel)
		 */
		int offset;
		/**
		 * data array represents numbers for colors for each pixel
		 */
		short[] data;
		/**
		 * minimal value for convergence of module of roots in iteration
		 */
		private static double treshold = 1E-3;
		/**
		 * Acceptable root-distance
		 */
		private static double rootTreshold = 2 * 1E-3;
		/**
		 * Maximal number of iterations for creating graphical image
		 */
		private static int maxiter = 16 * 16 * 16;

		/**
		 * ComplexRootedPolynomial for entered roots for this polynomial
		 */
		ComplexRootedPolynomial roots;

		/**
		 * ComplexPolynomial for entered roots for this polynomial
		 */
		ComplexPolynomial polynom;
		/**
		 * AtomicBoolean cancel enables to cancel drawing
		 */
		AtomicBoolean cancel;

		/**
		 * Public constructor instances new job and initializes all private variables
		 * for this job.
		 * 
		 * @param reMin   input value for reMin
		 * @param reMax   input value for reMax
		 * @param imMin   input value for imMin
		 * @param imMax   input value for imMin
		 * @param width   input value for width
		 * @param height  input value for height
		 * @param yMin    input value for yMin
		 * @param yMax    input value for yMax
		 * @param data    input value for data
		 * @param roots   input value for roots
		 * @param polynom input value for polynom
		 * @param cancel  input value for cancel
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, ComplexRootedPolynomial roots, ComplexPolynomial polynom,
				AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.offset = yMin * width;
			this.data = data;
			this.polynom = polynom;
			this.roots = roots;
			this.cancel = cancel;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Void call() {

			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y, width, yMin, yMax, reMin, reMax, imMin, imMax, height);
					int iter = 0;
					double module;
					do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = polynom.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;

					} while (module > treshold && iter < maxiter);
					short index = (short) roots.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}
			return null;
		}

		/**
		 * This method creates new instance of Complex number. Method is called by
		 * method call() of this class and this method calculates next iteration of zn
		 * and returns it.
		 */
		private Complex mapToComplexPlain(int x, int y, int width, int yMin, int yMax, double reMin, double reMax,
				double imMin, double imMax, double height) {
			double real = (double) x / (width - 1) * (reMax - reMin) + reMin;
			double imaginary = (double) (height - 1 - y) / (height - 1) * (imMax - imMin) + imMin;

			return new Complex(real, imaginary);
		}
	}

	/**
	 * This class implements IFractalProducer and represents a producer of jobs for
	 * PosaoIzracuna class.
	 * 
	 * @author antonija
	 *
	 */
	public static class MyProducer implements IFractalProducer {

		/**
		 * ComplexRootedPolynomial for entered roots for this polynomial
		 */
		ComplexRootedPolynomial roots;

		/**
		 * ComplexPolynomial for entered roots for this polynomial
		 */
		ComplexPolynomial polynom;

		/**
		 * List of result of each produced and called jobs
		 */
		private static List<Future<Void>> rezultati;

		/**
		 * Pool is private pool of threads for parallel job executing 
		 */
		static ExecutorService pool;

		/**
		 * Public constructor initializes all private variables and creates ExecutorService pool 
		 * @param roots input ComplexRootedPolynomial roots
		 * @param polynom input ComplexPolynomial polynom
		 */
		public MyProducer(ComplexRootedPolynomial roots, ComplexPolynomial polynom) {
			this.roots = roots;
			this.polynom = polynom;
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), t -> {
				Thread thread = new Thread(t);
				thread.setDaemon(true);
				return thread;
			});
			rezultati = new ArrayList<>();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");

			short[] data = new short[width * height];
			final int brojTraka = 8 * Runtime.getRuntime().availableProcessors();
			int brojYPoTraci = height / brojTraka;

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == brojTraka - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						roots, polynom, cancel);
				rezultati.add(pool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}

	}

}
