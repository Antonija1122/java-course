package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet extends HttpServlet. It reads data from file
 * "/WEB-INF/glasanje-rezultati.txt". If file doesn't exist it is created and
 * all bands are given 0 votes. Votes are updated with this request. Request is
 * redirected to "/glasanje-rezultati".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasGlas", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String idVote = (String) req.getParameter("id");
		List<String> lines = Collections.emptyList();

		Path file = Paths.get(fileName);
		if (!Files.exists(file)) {
			fillwidthZeros(file, req);
		}

		try {
			lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		for (String l : lines) {
			String[] parts = l.trim().split("\t");
			if (idVote.equals(parts[0])) {
				parts[1] = Integer.toString(Integer.parseInt(parts[1]) + 1);
			}
			sb.append(parts[0] + "\t" + parts[1] + "\n");
		}
		Files.write(file, (sb.toString()).getBytes());
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}

	/**
	 * Method reads all bands from input file "/WEB-INF/glasanje-definicija.txt" and
	 * all bends are given zero votes. This data is stored to input file
	 * 
	 * @param file input file path
	 * @param req  HttpServletRequest request of this servlet
	 * @throws IOException
	 */
	private void fillwidthZeros(Path file, HttpServletRequest req) throws IOException {
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (String l : lines) {
			String[] parts = l.trim().split("\t");
			sb.append(parts[0] + "\t" + "0" + "\n");
		}
		Files.write(file, (sb.toString()).getBytes());
	}

}
