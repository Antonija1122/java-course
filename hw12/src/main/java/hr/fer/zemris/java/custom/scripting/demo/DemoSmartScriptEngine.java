package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo program for SmartScriptEngine for cmart scripts: osnovni.smscr,
 * zbrajanje.smscr, brojPoziva.smscr, fibonacci.smscr
 * 
 * @author antonija
 *
 */
public class DemoSmartScriptEngine {

	public static void main(String[] args) {

		executeDemo1("osnovni.smscr");
		System.out.println();
		executeDemo2("zbrajanje.smscr");
		System.out.println();
		executeDemo3("brojPoziva.smscr");
		System.out.println();
		executeDemo4("fibonacci.smscr");
		System.out.println();
		executeDemo5("fibonacci.smscr");
	}

	/**
	 * Demo program for fibonaccih.smscr script
	 * 
	 * @param string input script
	 * @throws IOException 
	 */
	private static void executeDemo5(String string) {
		String documentBody = readFromDisk("fibonaccih.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Demo program for fibonacci.smscr script
	 * 
	 * @param string input script
	 */
	private static void executeDemo4(String string) {
		String documentBody = readFromDisk("fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Demo program for brojPoziva.smscr script
	 * 
	 * @param string input script
	 */
	private static void executeDemo3(String string) {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Demo program for zbrajanje.smscr script
	 * 
	 * @param string input script
	 */
	private static void executeDemo2(String string) {
		String documentBody = readFromDisk("zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Demo program for osnovni.smscr script
	 * 
	 * @param string input script
	 */
	private static void executeDemo1(String string) {
		String documentBody = readFromDisk("osnovni.smscr");

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Method reads string from input filename
	 * 
	 * @param fileName name of file in this projects directory
	 * @return loaded string
	 */
	private static String readFromDisk(String fileName) {

		Path path = Paths.get("./webroot/scripts/" + fileName);
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			System.out.println("Unable to find document");
			System.exit(-1);
		}
		return docBody;
	}

}
