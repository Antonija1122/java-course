package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionsModel;

/**
 * This servlet extends HttpServlet. It reads number of votes of each candidate,
 * for poll with id given in parameters, from database and creates an excel table
 * that has two columns first column has candidates titles and second has number of
 * their votes. Candidates are sorted descending by number of votes. The table is
 * returned through responses output stream.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasXls", urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeExcel extends HttpServlet {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("pollID"));
		List<PollOptionsModel> options = DAOProvider.getDao().getPollOptions(id, "VOTESCOUNT DESC");
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("sheet 1");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((int) 0).setCellValue("Kandidat");
			rowhead.createCell((int) 1).setCellValue("Number of votes");
			for (int i = 0; i < options.size(); i++) {
				HSSFRow row = sheet.createRow((int) (i + 1));
				row.createCell((int) 0).setCellValue(options.get(i).getOptionTitle());
				row.createCell((int) 1).setCellValue(options.get(i).getVotesCount());
			}
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=rezultatiGlasanja.xls");
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
