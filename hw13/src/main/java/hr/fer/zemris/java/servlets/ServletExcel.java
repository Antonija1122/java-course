package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet extends HttpServlet. It reads parameters a, b and n that user
 * gave and creates excel table with n sheets. On i-th sheet first column shows
 * numbers in range [a,b] and second column shows i-th power of those numbers.
 * The table is returned through responses output stream.
 * 
 * @author antonija
 *
 */
@WebServlet(name = "pow", urlPatterns = { "/powers" })
public class ServletExcel extends HttpServlet {

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
		String n = req.getParameter("n");

		Integer varA = null;
		Integer varB = null;
		Integer varN = null;

		try {
			varA = Integer.parseInt(a);
			if (varA < -100 || varA > 100)
				throw new IndexOutOfBoundsException();
		} catch (Exception e) {
			req.setAttribute("error",
					"Invalid input! You didn't enter parametar a or given parametar a is not an integer number in range[-100, 100]");
			req.getRequestDispatcher("/WEB-INF/pages/sendMessage.jsp").forward(req, resp);
		}
		try {
			varB = Integer.parseInt(b);
			if (varB < -100 || varB > 100)
				throw new IndexOutOfBoundsException();
		} catch (Exception e) {
			req.setAttribute("error",
					"Invalid input! You didn't enter parametar b or given parametar b is not an integer number in range[-100, 100]");
			req.getRequestDispatcher("/WEB-INF/pages/sendMessage.jsp").forward(req, resp);
		}
		try {
			varN = Integer.parseInt(n);
			if (varN < 1 || varN > 5)
				throw new IndexOutOfBoundsException();
		} catch (Exception e) {
			req.setAttribute("error",
					"Invalid input! You didn't enter parametar n or given parametar n is not an integer number in range[1, 5]");
			req.getRequestDispatcher("/WEB-INF/pages/sendMessage.jsp").forward(req, resp);
		}

		if (varA > varB) {
			int temp = varA;
			varA = varB;
			varB = temp;
		}

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			for (int i = 1; i <= varN; i++) {
				HSSFSheet sheet = hwb.createSheet("sheet " + i);

				HSSFRow rowhead = sheet.createRow((short) 0);
				rowhead.createCell((short) 0).setCellValue("X");
				rowhead.createCell((short) 1).setCellValue("X^" + i);

				for (int j = varA; j <= varB; j++) {
					HSSFRow row = sheet.createRow((int) (j - varA + 1));
					row.createCell((int) 0).setCellValue(j);
					row.createCell((int) 1).setCellValue(Math.pow(j, i));
				}
			}
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=tablicaPotencija.xls");
			hwb.write(resp.getOutputStream());
			hwb.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

}
