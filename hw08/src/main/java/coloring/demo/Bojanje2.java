package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * This class demonstrates implemented algoritam for coloring pictures. Methods
 * for filling are: bfs, dfs, bfsv.
 * 
 * @author antonija
 *
 */
public class Bojanje2 {

	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfsv));
	}

	/**
	 * private static final FillAlgorithm bfs uses bfs method from class
	 * SubspaceExploreUtil for going through pixels in picture.
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {

		/**
		 * This method returns name of this algorithm
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		/**
		 * This method fills area in which the user clicked with input color
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}
	};

	/**
	 * private static final FillAlgorithm dfs uses dfs method from class
	 * SubspaceExploreUtil for going through pixels in picture.
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {

		/**
		 * This method returns name of this algorithm
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		/**
		 * This method fills area in which the user clicked with input color
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}
	};

	/**
	 * private static final FillAlgorithm bfsv uses bfsv method from class
	 * SubspaceExploreUtil for going through pixels in picture.
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {

		/**
		 * This method returns name of this algorithm
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfsv!";
		}

		/**
		 * This method fills area in which the user clicked with input color
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}
	};

}
