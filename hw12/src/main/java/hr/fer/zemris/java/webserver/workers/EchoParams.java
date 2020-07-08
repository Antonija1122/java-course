package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * EchoParams implements IWebWorker and it creates table of all input parameters
 * from clients request and generates html table document with those parameters
 * and sends that document to client.
 * 
 * @author antonija
 *
 */
public class EchoParams implements IWebWorker {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			StringBuilder sb = new StringBuilder("<html>\r\n" + " <head>\r\n" + " <title>Ispis parametara</title>\r\n"
					+ " </head>\r\n" + " <body>\r\n" + "<h2>Bordered Table</h2>\r\n</h2>"
					+ "<p>Parameters stored in parameters map.\r\n</p>"
					+ " <table border='1'>\r\n");
			
			Set<String> paramNamesSet = context.getParameterNames();
			
			sb.append(" <tr>\r\n<th>").append("Parameter").append("</th>\r\n").append("<th>")
			.append("Value").append("</th>\r\n</tr>\r\n");
			for (String name : paramNamesSet) {
				sb.append(" <tr>\r\n<th>").append(name).append("</th>\r\n").append("<th>")
						.append(context.getParameter(name)).append("</th>\r\n</tr>\r\n");
			}
			sb.append(" </table>\r\n" + " </body>\r\n" + "</html>\r\n");
			// Convert to bytes and send it to client
			byte[] tijeloOdgovora = sb.toString().getBytes(StandardCharsets.UTF_8);
			context.write(tijeloOdgovora);
			
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
