package hr.fer.zemris.java.tecaj_13.web.servlets;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet koji obavlja akciju /save. Pogledajte slideove za opis: tamo
 * odgovara akciji /update.
 * 
 * @author marcupic
 */
@WebServlet("/servleti/save")
public class SaveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			obradi(req, resp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			obradi(req, resp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, NoSuchAlgorithmException {

		req.setCharacterEncoding("UTF-8");
		
		String metoda = req.getParameter("metoda");
		if(!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
			return;
		}

		FormularForm f = new FormularForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();
		
		if(f.imaPogresaka()) {
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser r = new BlogUser();
		f.popuniURecord(r);
		
		DAOProvider.getDAO().newUser(r);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
	}
}
