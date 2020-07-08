package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet extends HttpServlet. It reads number of votes of each band from
 * sessions attributes and creates an excel table that has two columns first
 * column has bands names and second has number of their votes. Bands are sorted
 * descending by number of votes. The table is returned through responses output
 * stream.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "glasXls", urlPatterns = { "/glasanje-xls" })
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

		List<String> bandIDs = (List<String>) req.getSession().getAttribute("ids");
		bandIDs = sort(bandIDs, req.getSession());

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("sheet 1");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((int) 0).setCellValue("Band Name");
			rowhead.createCell((int) 1).setCellValue("Number of votes");
			for (int i = 0; i < bandIDs.size(); i++) {
				HSSFRow row = sheet.createRow((int) (i + 1));
				row.createCell((int) 0).setCellValue((String) req.getSession().getAttribute(bandIDs.get(i) + "name"));
				row.createCell((int) 1).setCellValue((String) req.getSession().getAttribute(bandIDs.get(i) + "votes"));
			}
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=rezultatiGlasanja.xls");
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * This method sorts list of bands ids by number of votes descending. 
	 * @param bands list of ids
	 * @param session input HttpSession
	 * @return sorted list of ids
	 */
	private List<String> sort(List<String> bands, HttpSession session) {
		int n = bands.size();
		String temp;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				int num1 = Integer.parseInt((String) session.getAttribute(bands.get(j - 1) + "votes"));
				int num2 = Integer.parseInt((String) session.getAttribute(bands.get(j) + "votes"));
				if (num1 < num2) {
					temp = bands.get(j - 1);
					bands.set(j - 1, bands.get(j));
					bands.set(j, temp);
				}
			}
		}
		return bands;
	}

}
