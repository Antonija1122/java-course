package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet extends HttpServlet. It reads parameters a and b that user
 * gave and adds to request attributes every number in range [a,b] and vales of
 * sin and cos functions of all those numbers. This request if forwarded to jsp
 * "/WEB-INF/pages/trigonometric.jsp".
 * 
 * @author antonija
 *
 */
@WebServlet(name = "trig", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String a = req.getParameter("a");
		String b = req.getParameter("b");

		Integer varA = 0;
		Integer varB = 360;
		try {
			if (a != null) {
				varA = Integer.parseInt(a);
			}
		} catch (NumberFormatException exc) {
		}

		try {
			if (b != null) {
				varB = Integer.parseInt(b);
			}
		} catch (NumberFormatException exc) {
		}

		if (varA > varB) {
			int temp = varA;
			varA = varB;
			varB = temp;
		}

		if (varB > varA + 720) {
			varB = varA + 720;
		}

		req.setAttribute("varA", varA);
		req.setAttribute("varB", varB);

		NumberFormat formatter = new DecimalFormat("#0.000");
		for (int i = varA; i <= varB; i++) {
			req.setAttribute("sin" + Integer.toString(i), formatter.format(Math.sin(i * Math.PI / 180)));
			req.setAttribute("cos" + Integer.toString(i), formatter.format(Math.cos(i * Math.PI / 180)));
		}

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
