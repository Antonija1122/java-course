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
@WebServlet("/servleti/newMessage")
public class MessageServlet extends HttpServlet {

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
		long id=Long.parseLong(req.getParameter("id"));
		String nick=DAOProvider.getDAO().getBlogEntry(id).getUser().getNick();
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
		String metoda = req.getParameter("metoda");
		if(!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" +nick + "/" + id);
			return;
		}
		String email=null;
		if(req.getSession().getAttribute("currentid")!=null) {
			email=DAOProvider.getDAO().getUser((String)req.getSession().getAttribute("currentnick")).getEmail();
		} else {
			email=(String) req.getParameter("email");
			if(!isValid(email)) {
				req.setAttribute("blogEntry", blogEntry);
				req.setAttribute("error", "Invalid email form");
				req.setAttribute("comment", (String) req.getParameter("comment"));
				
				req.getRequestDispatcher("/WEB-INF/pages/EID.jsp").forward(req, resp);
				return;
			}
		}
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage((String) req.getParameter("comment"));
		blogComment.setBlogEntry(blogEntry);
		
		blogEntry.getComments().add(blogComment);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/" + id);
	}
	
	private boolean isValid(String email) {
		int l = email.length();
		int p = email.indexOf('@');
		if(l<3 || p==-1 || p==0 || p==l-1) {
			return false;
		}
		return true;
	}


}
