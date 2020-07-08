package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * SumWorker implements IWebWorker and it calculates sum of two input parameters
 * from clients request and generates html table document with parameters and
 * result and sends that document to client with appropriate image, depending on
 * the sum result (even or odd).
 * 
 * @author antonija
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public synchronized void processRequest(RequestContext context) {

		try {
			String varA = null;
			String varB = null;
			Integer zbroj;

			if (context.getParameter("a") != null) {
				varA = getValue(context.getParameter("a"));
				context.setTemporaryParameter("varA", varA == null ? "1" : varA);
			} else {
				context.setTemporaryParameter("varA", "1");
			}

			if (context.getParameter("b") != null) {
				varB = getValue(context.getParameter("b"));
				context.setTemporaryParameter("varB", varB == null ? "2" : varB);
			} else {
				context.setTemporaryParameter("varB", "2");
			}
			varA = varA == null ? "1" : varA;
			varB = varB == null ? "1" : varB;
			zbroj = Integer.parseInt(varA) + Integer.parseInt(varB);
			context.setTemporaryParameter("zbroj", String.valueOf(zbroj));
			context.setTemporaryParameter("imgName", zbroj % 2 == 0 ? "/images/jezero.jpg" : "/images/zora.jpg");
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private String getValue(String parameter) {
		try {
			Integer.parseInt(parameter);
			return parameter;
		} catch (NumberFormatException e) {
		}
		return null;
	}
}
