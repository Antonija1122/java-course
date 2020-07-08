package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet extends HttpServlet. It reads parameter user gave as a chosen
 * color and adds name of that color to sessions attributes so every html page
 * in this session can read that attribute and set it's background to that
 * color.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "setColor", urlPatterns = { "/setcolor" })
public class ServletSetColor extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String color = req.getParameter("color");

		if (color == null) {
			req.getSession().setAttribute("pickedBgCol", "white");
		} else if (color.equals("red")) {
			req.getSession().setAttribute("pickedBgCol", "red");
		} else if (color.equals("green")) {
			req.getSession().setAttribute("pickedBgCol", "green");
		} else if (color.equals("cyan")) {
			req.getSession().setAttribute("pickedBgCol", "cyan");
		} else {
			req.getSession().setAttribute("pickedBgCol", "white");
		}

		req.getRequestDispatcher("/color.jsp").forward(req, resp);
	}

}
