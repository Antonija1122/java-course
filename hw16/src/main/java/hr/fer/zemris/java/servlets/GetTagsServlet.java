package hr.fer.zemris.java.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet gets name of input image through HttpServletRequest parameters.
 * It checks if there is a file with that name in thumbnails folder. If it
 * doesn't exist servlet takes full size image of the same name from slike
 * folder and creates that same image in the size 150x150 px in folder
 * thumbnails. Then it takes image from folder and returns image as byte array
 * to user (shows picture to user.)
 * 
 * @author antonija
 *
 */
@WebServlet(urlPatterns = { "/servlets/thumb" })
public class GetTagsServlet extends HttpServlet {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		String name = req.getParameter("name");

		String dirDest = req.getServletContext().getRealPath("") + "/WEB-INF/thumbnails";
		checkIfExist(dirDest);

		String dirOrig = req.getServletContext().getRealPath("") + "/WEB-INF/slike";
		String fileDest = dirDest + "/" + name;

		createResized(dirOrig + "/" + name, fileDest);

		BufferedImage bim = ImageIO.read(new File(fileDest));

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);

		byte[] podaci = bos.toByteArray();

		resp.getOutputStream().write(podaci);

		resp.getOutputStream().flush();
	}

	/**
	 * This method checks if file pathDest exists, if not image of size 150x150 is
	 * created in that file from input image file with the path pathOrig, else method does nothing.
	 * 
	 * @param pathOrig input image path
	 * @param pathDest created image
	 * @throws IOException
	 */
	private void createResized(String pathOrig, String pathDest) throws IOException {
		if (Files.exists(Paths.get(pathDest))) {
			return;
		}
		BufferedImage bim = ImageIO.read(new File(pathOrig));
		BufferedImage bim2 = new BufferedImage(150, 150, bim.getType());
		Graphics2D g2d = bim2.createGraphics();
		g2d.drawImage(bim, 0, 0, 150, 150, null);
		g2d.dispose();

		ImageIO.write(bim2, "png", new File(pathDest));
	}

	/**
	 * This method checks if folder from input path exists, if it doesn't exist. Folder is created 
	 * @param path input folder path
	 */
	private void checkIfExist(String path) {
		if (!Files.exists(Paths.get(path))) {
			File dir = new File(path);
			dir.mkdir();
		}
	}

}
