package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
 * "/WEB-INF/glasanje-rezultati.txt". Number of voted for each band is stored to
 * sessions attributes. Request is forwarded to jsp "/WEB-INF/pages/glasanjeRez.jsp".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasRez", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String l : lines) {
			String[] parts = l.trim().split("\t");
			req.getSession().setAttribute(parts[0] + "votes", parts[1]);
		}
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

}
