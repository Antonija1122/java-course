package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This worker implements IWebWorker and it draws circle of dimensions 200x200
 * to clinet in color green.
 * 
 * @author antonija
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void processRequest(RequestContext context) {
		context.setMimeType("image/png");
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		// Draw circle
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.RED);
		g2d.fillArc(0, 0, bim.getWidth(), bim.getHeight(), 0, 360);
		g2d.dispose();
		// Send image to stream
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
