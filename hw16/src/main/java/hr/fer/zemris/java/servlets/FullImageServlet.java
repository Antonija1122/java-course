package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet gets name of input image through HttpServletRequest parameters.
 * It finds image in folder database and returns image as byte array to user (shows picture to user.)
 * 
 * @author antonija
 *
 */
@WebServlet(urlPatterns = { "/servlets/fullImage" })
public class FullImageServlet extends HttpServlet {

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

		String dirOrig = req.getServletContext().getRealPath("") + "/WEB-INF/slike/" + name;

		BufferedImage bim = ImageIO.read(new File(dirOrig));

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);

		byte[] podaci = bos.toByteArray();
		resp.getOutputStream().write(podaci);

		resp.getOutputStream().flush();
	}

}
