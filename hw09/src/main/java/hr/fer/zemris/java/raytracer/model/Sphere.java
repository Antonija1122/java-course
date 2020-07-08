package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents Sphere with variables: radius, center, and constants for coloring with red, green and blue color. 
 * @author antonija
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * center is Point3D of center of this sphere
	 */
	private Point3D center;
	/**
	 * length of radius of this sphere
	 */
	private double radius;
	/**
	 * Constant k for diffusion component of light for red color
	 */
	private double kdr;
	/**
	 * Constant k for diffusion component of light for green color
	 */
	private double kdg;
	/**
	 * Constant k for diffusion component of light for blue color
	 */
	private double kdb;
	/**
	 * Constant k for reflective component of light for red color
	 */
	private double krr;
	/**
	 * Constant k for reflective component of light for green color
	 */
	private double krg;
	/**
	 * Constant k for reflective component of light for blue color
	 */
	private double krb;
	/**
	 * Constant n for reflective component
	 */
	private double krn;

	/**
	 * Public constructor initializes all private variables to input values 
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D d = ray.direction;
		Point3D Ts = ray.start;
		// a = d*d=1
		double a = d.scalarProduct(d);
		
		// b = 2*d * (Ts-C)
		double b = 2 * d.scalarProduct(Ts.sub(this.center));
		
		// c=(Ts-C)*(Ts-C)-r*r
		double c = Ts.sub(this.center).scalarProduct(Ts.sub(this.center)) - this.radius * this.radius;
		
		// diskriminanta b*b-4*c a=1
		double diskrim = Math.pow(b, 2.0) - 4 * c * a; // a=1

		if (diskrim < 0) {
			return null;
		}
		double lambda1 = (-b + Math.sqrt(diskrim)) / (2 * a);
		double lambda2 = (-b - Math.sqrt(diskrim)) / (2 * a);

		if (!(lambda1 < 0 && lambda2 < 0)) {
			double lambda;
			if (lambda1 < lambda2) {
				lambda = lambda1;
			} else {
				lambda = lambda2;
			}
			Point3D intersection = Ts.add(d.scalarMultiply(lambda));

			return new RayIntersection(intersection, lambda, intersection.sub(Ts).norm() > radius) {

				@Override
				public Point3D getNormal() {
					return intersection.sub(center).normalize();
				}

				@Override
				public double getKrr() {
					return krr;
				}

				@Override
				public double getKrn() {
					return krn;
				}

				@Override
				public double getKrg() {
					return krg;
				}

				@Override
				public double getKrb() {
					return krb;
				}

				@Override
				public double getKdr() {
					return kdr;
				}

				@Override
				public double getKdg() {
					return kdg;
				}

				@Override
				public double getKdb() {
					return kdb;
				}
			};

		} else {
			//oba su iza promatraca
			return null;
		}
	}

}
