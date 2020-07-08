package hr.fer.zemris.java.tecaj_13.web.servlets;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet does action of creating new blog. Text and title are read from
 * request parameters and new blog entry is created and added to database
 * 
 * @author antonija
 */
@WebServlet("/servleti/newBlog")
public class NewServlet extends HttpServlet {

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
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + (String) req.getSession().getAttribute("currentnick"));
			return;
		}
		
		BlogEntry newEntry=new BlogEntry();
		newEntry.setUser(DAOProvider.getDAO().getUser((String) req.getSession().getAttribute("currentnick")));
		newEntry.setCreatedAt(new Date());
		newEntry.setLastModifiedAt(new Date());
		newEntry.setComments(new ArrayList<BlogComment>());
		newEntry.setTitle((String) req.getParameter("title"));
		newEntry.setText((String) req.getParameter("text"));
		DAOProvider.getDAO().newBlogEntry(newEntry);
		newEntry.getUser().getBlogs().add(newEntry);
		
		
		//System.out.println(newEntry.getUser().getBlogs().size());

		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + (String) req.getSession().getAttribute("currentnick"));
	}


}
