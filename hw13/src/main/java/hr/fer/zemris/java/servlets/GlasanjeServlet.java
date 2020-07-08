package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet extends HttpServlet. It reads data from file
 * "/WEB-INF/glasanje-definicija.txt" and registeres all bands mentioned there.
 * Registered atributes are bands ids in a list and their names and links to their
 * songs. This request if forwarded to jsp "/WEB-INF/pages/glasanjeIndex.jsp".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glas", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

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
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			// sortiraj po id-u
			lines = lines.stream().sorted((el1, el2) -> {
				String first = (el1.split("\t"))[0].trim();
				String second = (el2.split("\t"))[0].trim();
				return first.compareTo(second);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> ids = new ArrayList<String>();
		for (String l : lines) {
			String[] parts = l.trim().split("\t");
			ids.add(parts[0]);
			req.getSession().setAttribute(parts[0] + "name", parts[1]);
			req.getSession().setAttribute(parts[0] + "urlSong", parts[2]);
		}

		req.getSession().setAttribute("ids", ids);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
