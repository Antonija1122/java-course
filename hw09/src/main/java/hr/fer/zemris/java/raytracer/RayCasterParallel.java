package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
/**
 * This class represents a simplification of a ray-tracer for rendering of 3D
 * scenes. There is one sphere in this model. Image is created through a
 * constructed plane. Point 0 is the viewer and point G is one point from the
 * plane. With this two points vector d is constructed and now program looks if
 * there are any intersections with objects in scene! If an intersection is
 * found, then that is exactly what will determine the color of screen-pixel
 * (x,y). If no intersection is found, the pixel will be rendered black
 * (r=g=b=0). However, if an intersection is found, program determines the color
 * of the pixel. If multiple intersections are found, program chooses the closest
 * one to eye-position since that is what the human observer will see. For
 * coloring is used use Phong’s model which assumes that there is one or more
 * pointlight-sources present in scene. In this example there are two light
 * sources (one green and one red). Each light source is specified with
 * intensities of r, g and b components it radiates.
 * 
 * @author antonija
 *
 */
public class RayCasterParallel {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * This method creates IRayTracerProducer for this example. This
	 * IRayTracerProducer calculates intensities for red, green and blue colors for
	 * every point in the image.
	 * 
	 * @return IRayTracerProducerfor
	 */
	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize(); // provjeri normalize
				Point3D VUV = viewUp.normalize();

				Point3D zAxis = OG;
				Point3D yAxis = VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV))).normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new PosaoIzracuna(eye, width, height, 0, height-1, zAxis, yAxis, xAxis,
						horizontal, vertical, red, green, blue, screenCorner, scene, 0, cancel));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			};
		};
	}

	/**
	 * This method sets environment color and calculates if there is intersection of
	 * input ray with any GraphicalObject in the scene and then calculates other components of light.
	 * 
	 * @param scene input scene 
	 * @param ray current ray 
	 * @param rgb     array with intensities for red, green, and blue color
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {

		// ambijentlna
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			return;
		} else {
			rgb = determineColorFor(closest, scene, ray, rgb);
		}

	}

	/**
	 * This method calculates diffuse and reflective color for current ray depending
	 * of closest intersection and shadows in scene.
	 * 
	 * @param closest closest intersection
	 * @param scene   input scene
	 * @param ray     current ray
	 * @param rgb     array with intensities for red, green, and blue color
	 * @return modified rgb array
	 */
	private static short[] determineColorFor(RayIntersection closest, Scene scene, Ray ray, short[] rgb) {
		List<LightSource> lights = scene.getLights();
		for (LightSource ls : lights) {

			Ray newRay = Ray.fromPoints(ls.getPoint(), closest.getPoint());
			RayIntersection newclosest = findClosestIntersection(scene, newRay);
			
			//0.0001 zbog tolerancije kod upoređivanja dva doubele-a 
			if (newclosest == null || ls.getPoint().sub(newclosest.getPoint()).norm() + 0.0001 <= ls.getPoint()
					.sub(closest.getPoint()).norm()) {
				continue;
			}
			
			// color += diffuse component + reflective component
			Point3D n = newclosest.getNormal();
			Point3D l = ls.getPoint().sub(newclosest.getPoint()).normalize();

			double factor = n.scalarProduct(l) > 0 ? n.scalarProduct(l) : 0;

			rgb[0] += factor * ls.getR() * newclosest.getKdr();
			rgb[1] += factor * ls.getG() * newclosest.getKdg();
			rgb[2] += factor * ls.getB() * newclosest.getKdb();

			// reflective component:r=-l−2(-l⋅n)n
			Point3D v = ray.start.sub(newclosest.getPoint()).normalize();
			Point3D r = l.negate().sub(n.scalarMultiply(2 * l.negate().scalarProduct(n))).normalize();

			factor = r.scalarProduct(v) > 0 ? Math.pow(r.scalarProduct(v), newclosest.getKrn()) : 0;

			rgb[0] += factor * ls.getR() * newclosest.getKrr();
			rgb[1] += factor * ls.getG() * newclosest.getKrg();
			rgb[2] += factor * ls.getB() * newclosest.getKrb();

		}
		return rgb;
	}

	/**
	 * This method finds closest intersection for input ray in input scene
	 * 
	 * @param scene input scene
	 * @param ray   current ray
	 * @return closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection returnItersection = null;
		List<GraphicalObject> objects = scene.getObjects();

		for (GraphicalObject o : objects) {
			RayIntersection intersection = o.findClosestRayIntersection(ray);

			if (returnItersection == null) {
				returnItersection = intersection; // prvi
			}

			if (intersection != null && returnItersection != null
					&& intersection.getDistance() < returnItersection.getDistance()) {
				returnItersection = intersection;
			}

		}

		return returnItersection;
	}


	/**
	 * This class recursively computes rgb array for this programs scene.
	 * @author antonija
	 *
	 */
	public static class PosaoIzracuna extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		/**
		 * current pixel
		 */
		int offset;
		/**
		 * position of viewer
		 */
		Point3D eye;
		/**
		 * width of image
		 */
		int width;
		/**
		 * height of image
		 */
		int height;
		/**
		 * yMin for this job
		 */
		int yMin;
		/**
		 * yMax for this job
		 */
		int yMax;
		/**
		 * Point3D representing i
		 */
		Point3D zAxis;
		/**
		 * Point3D representing j
		 */
		Point3D yAxis;
		/**
		 * Point3D representing z
		 */
		Point3D xAxis;
		/**
		 * horizontal length of plane
		 */
		double horizontal;
		/**
		 * vertical length of plane
		 */
		double vertical;
		/**
		 * data for intensity of red color for every pixel
		 */
		short[] red;
		/**
		 * data for intensity of green color for every pixel
		 */
		short[] green;
		/**
		 * data for intensity of blue color for every pixel
		 */
		short[] blue;
		/**
		 * Beginning of screen 
		 */
		Point3D screenCorner;
		/**
		 * scene for this program
		 */
		Scene scene;
		static final int treshold = 16*16*16;
		AtomicBoolean cancel;

		/**
		 * Public constructor for initializing all private variables to input values.
		 * @param eye input value for eye
		 * @param width input value for width
		 * @param height input value for height
		 * @param yMin input value for yMin
		 * @param yMax input value for yMax
		 * @param zAxis input value for zAxis
		 * @param yAxis input value for yAxis
		 * @param xAxis input value for xAxis
		 * @param horizontal input value for horizontal
		 * @param vertical input value for vertical
		 * @param red input value for red
		 * @param green input value for green
		 * @param blue input value for blue
		 * @param screenCorner input value for screenCorner
		 * @param scene input value for scenne
		 * @param offset input value for offset
		 * @param cancel input value for cancel
		 */
		public PosaoIzracuna(Point3D eye, int width, int height, int yMin, int yMax, Point3D zAxis, Point3D yAxis,
				Point3D xAxis, double horizontal, double vertical, short[] red, short[] green, short[] blue,
				Point3D screenCorner, Scene scene, int offset, AtomicBoolean cancel) {
			super();
			this.offset = offset;
			this.eye = eye;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.zAxis = zAxis;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.cancel = cancel;
		}

		/**
		 * This method calls method computeDirect and also invokes new jobs.
		 */
		public void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new PosaoIzracuna(eye, width, height, yMin, yMin + (yMax - yMin) / 2, zAxis, yAxis, xAxis,
							horizontal, vertical, red, green, blue, screenCorner, scene, yMin * width, cancel),
					new PosaoIzracuna(eye, width, height, yMin, yMin + (yMax + yMin) / 2 + 1, zAxis, yAxis, xAxis,
							horizontal, vertical, red, green, blue, screenCorner, scene, yMin * width, cancel));
		}

		/**
		 * This method computes values for array rgb for trace in this particular job (yMin to yMax)
		 */
		public void computeDirect() {

			short[] rgb = new short[3];
			for (int y = yMin; y <= yMax; y++) {
				if(cancel.get()) break;
				for (int x = 0; x < width; x++) {

					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (double) (width - 1) * horizontal))
							.sub(yAxis.scalarMultiply(y / (double) (height - 1) * vertical));

					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}


		}
	}


}