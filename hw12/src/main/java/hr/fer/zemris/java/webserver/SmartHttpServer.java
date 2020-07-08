package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
	/**
	 * On which address server listens
	 */
	private String address;
	/**
	 * domain name of our web server
	 */
	private String domainName;
	/**
	 * On which port server listens
	 */
	private int port;
	/**
	 * How many threads should we use for thread pool
	 */
	private int workerThreads;
	/**
	 * What is the duration of user sessions in seconds? As configured, it is 10
	 * minutes
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * pool of threads used.
	 */
	private ExecutorService threadPool;
	/**
	 * the path to root directory from which we serve files
	 */
	private Path documentRoot;
	/**
	 * Map of IWebWorkers for this server
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Map of sessions for this server
	 */
	private volatile Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random generator for session ids
	 */
	private volatile Random sessionRandom = new Random();

	/**
	 * Public constructor reads properties from input file and sets private
	 * variables to input values from input file and files server.mimeConfig and
	 * server.workers. Constructor creates server thread and calls method start()
	 * and after some given time it calls method stop(time).
	 * 
	 * @param configFileName file to configuration file
	 */
	public SmartHttpServer(String configFileName) throws IOException, InterruptedException, ClassNotFoundException,

			InstantiationException, IllegalAccessException {

		// Initialize server
		initServer(configFileName);
		// Start server
		startServer();
		// Stop server after timeout has been reached define server timeout
		stopServer(sessionTimeout * 10);
	}

	/**
	 * Method stops server after 600*10 seconds.
	 * 
	 * @param serverTimeout input time
	 * @throws InterruptedException
	 */
	private void stopServer(int serverTimeout) throws InterruptedException {
		TimeUnit.SECONDS.sleep(serverTimeout);
		stop();

	}

	/**
	 * Method starts server
	 * 
	 * @throws InterruptedException
	 */
	private void startServer() throws InterruptedException {
		start();
	}

	/**
	 * Method starts serverThread and initializes thread pool. Also every 5 minutes
	 * sessions are checked if they are still valid.
	 */
	protected synchronized void start() throws InterruptedException {

		if (!serverThread.isAlive()) {
			serverThread.setDaemon(true);
			serverThread.start();
			Thread checkSessions = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(5000 * 60);
						for (Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
							SessionMapEntry value = entry.getValue();
							if (value.validUntil <= System.currentTimeMillis() / 1000)
								sessions.remove(entry.getKey());
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			checkSessions.setDaemon(true);
			checkSessions.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Method stops server thread and shuts down thread pool.
	 */
	protected synchronized void stop() {
		threadPool.shutdown();
	}

	/**
	 * Method reads properties from input file and sets private variables to input
	 * values from input file and files server.mimeConfig and server.workers.
	 * 
	 * @param configFileName input configuration property file
	 * @throws IOException
	 */
	private void initServer(String configFileName) throws IOException {

		// Create properties
		Properties propertyServer = new Properties();
		Properties propertyMimeType = new Properties();
		Properties propertyWorker = new Properties();

		// Load server configuration
		propertyServer.load(Files.newInputStream(Paths.get(configFileName)));

		// Configure mimeTypes
		propertyMimeType.load(Files.newInputStream(Paths.get(propertyServer.getProperty("server.mimeConfig"))));
		propertyMimeType.forEach((key, value) -> mimeTypes.put((String) key, (String) value));

		// Configure address and port server listens to, and server default domain name
		address = propertyServer.getProperty("server.address");
		port = Integer.parseInt(propertyServer.getProperty("server.port"));
		domainName = propertyServer.getProperty("server.domainName");

		// Define number of threads that we use for thread pool
		workerThreads = Integer.parseInt(propertyServer.getProperty("server.workerThreads"));

		// Define path to root directory from which we serve files
		documentRoot = Paths.get(propertyServer.getProperty("server.documentRoot"));

		// Duration of user sessions in seconds
		sessionTimeout = Integer.parseInt(propertyServer.getProperty("session.timeout"));

		// Setup workers
		propertyWorker.load(Files.newInputStream(Paths.get(propertyServer.getProperty("server.workers"))));
		setupWorkers(propertyWorker);
		serverThread = new ServerThread();

	}

	/**
	 * This method checks for workers in input property file and adds them to map
	 * workers.
	 * 
	 * @param propertyWorker input property file
	 */
	private void setupWorkers(Properties propertyWorker) {

		workersMap = new HashMap<String, IWebWorker>();
		propertyWorker.forEach((path, fqcn) -> {
			try {
				IWebWorker iww = getIwwInstance((String) fqcn);
				if (workersMap.containsKey(path)) {
					throw new IllegalArgumentException("Multiple lines with the same path in worker.properties");
				}
				workersMap.put((String) path, iww);
			} catch (Exception e) {

			}
		});

	}

	/**
	 * This method creates new IWebWorker
	 * 
	 * @param fqcn fully qualified class name
	 * @return IWebWorker
	 * @throws Exception
	 */
	private IWebWorker getIwwInstance(String fqcn) throws Exception {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass((String) fqcn);
		Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
		return (IWebWorker) newObject;
	}

	/**
	 * This class extends Thread and serves as a server thread which listens for
	 * clients and for every request creates ClientWorker
	 * 
	 * @author antonija
	 *
	 */
	protected class ServerThread extends Thread {
		@SuppressWarnings("resource")
		@Override
		public void run() {

			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));

				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);

					// Generate random identifier
					threadPool.submit(cw);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Private class for handling clients request implements Runnable and
	 * IDispatcher
	 * 
	 * @author antonija
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket for receiving requests information
		 */
		private Socket csocket;
		/**
		 * Used input stream
		 */
		private PushbackInputStream istream;
		/**
		 * output stream to user
		 */
		private OutputStream ostream;
		/**
		 * Version of protocol
		 */
		private String version;
		/**
		 * Method from request (GET, POST...)
		 */
		private String method;
		/**
		 * Host adress
		 */
		private String host;
		/**
		 * map of parameters user entered
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * map of temporary parametars
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * map of permPrams
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * list of cookies for this client
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID
		 */
		private String SID;
		/**
		 * instance of class RequestContext for creating headers for client
		 */
		RequestContext context = null;

		/**
		 * public constructor sets socket to input socket for comunication with user
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {

			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				List<String> headers = readRequest();

				if (headers == null || headers.size() < 1) {
					sendError(400, "Bad request");
					return;
				}

				String[] firstLine = headers.get(0).split(" ");
				if (firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(400, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(400, "HTTP Version Not Supported");
					return;
				}

				host = getHostValue(headers);

				if (checkSession(headers)) {
					SessionMapEntry sessionEntry = sessions.get(tempParams.get("sidCandidate"));
					// If stored object doesn't match host calculated
					if (sessionEntry == null || !(sessionEntry.host).equals(host)) {
						createNewSession();
					} else if (sessionEntry.validUntil <= System.currentTimeMillis() / 1000) {
						sessions.remove(tempParams.get("sidCandidate"));
						createNewSession();
					} else {
						sessionEntry.validUntil = ((long) sessionTimeout + System.currentTimeMillis() / 1000);
						outputCookies.add(new RCCookie("sid", sessionEntry.sid, null, sessionEntry.host, "/"));
						SID = sessionEntry.sid;
					}
				} else {
					createNewSession();
				}

				permPrams = sessions.get(SID).map;

				String path;
				String paramString;

				if (firstLine[1].indexOf("?") != -1) {
					String[] pathInput = firstLine[1].split("\\?");
					path = pathInput[0];
					paramString = pathInput[1];

					parseParameters(paramString);
				} else {
					path = firstLine[1];
				}

				if (path.startsWith("/ext/")) {
					String fqcn = "hr.fer.zemris.java.webserver.workers." + path.substring(path.lastIndexOf("/") + 1);
					createContext("");
					getIwwInstance(fqcn).processRequest(context);
					ostream.flush();
					csocket.close();
					return;
				}

				if (workersMap.containsKey(path)) {
					internalDispatchRequest(path, true);
					ostream.flush();
					csocket.close();
					return;
				}

				Path requestedPath = documentRoot.resolve(path.substring(1));
				if (!requestedPath.normalize().startsWith(documentRoot.normalize())) {
					sendError(403, "File forbidden!");
					csocket.close();
					return;
				}

				if (!Files.exists(requestedPath) || !Files.isRegularFile(requestedPath)
						|| !Files.isReadable(requestedPath)) {
					sendError(404, "File not found");
					csocket.close();
					return;
				}

				// else extract file extension
				String fileName = requestedPath.getFileName().toString();
				createContext(fileName);
				if (getExtension(fileName).equals("smscr")) {
					runScript(requestedPath);

				} else {
					serveFile(requestedPath);
				}
				csocket.close();

			} catch (Exception e) {
			}
		}

		/**
		 * This method creates session and adds appropriate entry to session map and
		 * appropriate cookie to output cookies list
		 * 
		 */
		private void createNewSession() {
			SID = generateIdentifierForClient();
			long timeout = (long) sessionTimeout + System.currentTimeMillis() / 1000;
			Map<String, String> entryMap = new ConcurrentHashMap<String, String>();
			entryMap.put(SID, String.valueOf(timeout));

			SessionMapEntry sesionMapEntry = new SessionMapEntry(SID, host, timeout, entryMap);
			sessions.put(SID, sesionMapEntry);
			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));

		}

		/**
		 * This method checks if there are cookies in input request header. If it does,
		 * this method returns true, false otherwise
		 * 
		 * @param headers input request header
		 */
		private boolean checkSession(List<String> headers) {

			for (String h : headers.subList(1, headers.size())) {

				if ((h.trim()).startsWith("Cookie:")) {
					String cookieValue = (h.substring(h.indexOf(":") + 1)).trim();
					if (cookieValue.indexOf("sid") != -1) {
						// hardcoding
						int startingIndex = cookieValue.indexOf("sid") + 5;
						tempParams.put("sidCandidate", cookieValue.substring(startingIndex, startingIndex + 20));
						return true;
					}
				}

			}
			return false;
		}

		/**
		 * This method creates random upper case string of length 20.
		 * 
		 * @return randomId
		 */
		private String generateIdentifierForClient() {
			int count = 20;
			String latters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			StringBuilder builder = new StringBuilder();
			while (count-- > 0) {
				int index = (int) (sessionRandom.nextInt(latters.length()));
				builder.append(latters.charAt(index));
			}
			return builder.toString();

		}

		/**
		 * This method checks if there is host declaration in request header, if yes
		 * hostValue is set to that value, otherwise host is domainName
		 * 
		 * @param headers input request header
		 * @return
		 */
		private String getHostValue(List<String> headers) {
			String hostValue = domainName;
			l: while (true) {
				for (String h : headers.subList(1, headers.size())) {
					if ((h.trim()).startsWith("Host:")) {
						String hostName = (h.substring(h.indexOf(":") + 1)).trim();
						hostValue = hostName.indexOf(":") != -1 ? hostName.substring(0, hostName.indexOf(":"))
								: hostName;
						break l;
					}
				}
				break;
			}
			return hostValue;
		}

		/**
		 * This method checks for workers. If worker is directly called error is send to
		 * user, otherwise appropriate worker is called to process clients request
		 * 
		 * @param urlPath    input path
		 * @param directCall if direct call is made to worker false , otherwise true
		 * @throws Exception
		 */
		public void internalDispatchRequest(String path, boolean directCall) throws Exception {
			if (directCall && (path.equals("/private") || path.startsWith("/private/"))) {
				sendError(404, "Worker not found");
			}
			if (directCall) {
				createContext("");
				workersMap.get(path).processRequest(context);
				ostream.flush();

			} else {
				runScript(documentRoot.resolve(path.substring(1)));
			}
			csocket.close();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * This method is called if smart script is called by client. Method calls
		 * SmartScriptEngine to run a script and data is sent to client
		 * 
		 * @param requestedPath requested path
		 * @throws IOException
		 */
		private void runScript(Path requestedPath) throws IOException {
			context.setMimeType("text/html");
			String documentBody = Files.readString(requestedPath, StandardCharsets.UTF_8);
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
			ostream.flush();
		}

		/**
		 * This method sends data to user through output stream and RequestContext
		 * 
		 * @param cos           output stream
		 * @param requestedPath input path
		 * @param rc            RequestContext
		 * @throws IOException
		 */
		private void serveFile(Path requestedPath) throws IOException {

			try (InputStream is = new BufferedInputStream(Files.newInputStream(requestedPath))) {

				byte[] buf = new byte[1024];

				while (true) {
					int r = is.read(buf);
					if (r < 1)
						break;
					context.write(buf, 0, r);
				}
				ostream.flush();

			}

		}

		/**
		 * This method parses parameters from input request adds them to params map.
		 * 
		 * @param paramString input request
		 */
		private void parseParameters(String paramString) {

			if (paramString.indexOf("&") != -1) {
				String[] paramStringParts = paramString.split("&");
				for (String p : paramStringParts) {
					String[] singleParamPart = p.split("=");

					if (singleParamPart.length != 2) {
						return;
					}
					params.put(singleParamPart[0], singleParamPart[1]);

				}
			} else {
				String[] singleParamPart = paramString.split("=");
				params.put(singleParamPart[0], singleParamPart[1]);
			}

		}

		/**
		 * Helper method for sending response without body to user. Typically error
		 * response.
		 * 
		 * @param cos        putput stream
		 * @param statusCode output status code
		 * @param statusText error message
		 * @throws IOException
		 */
		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/**
		 * This method reads request and returns list of headers.
		 * 
		 * @return list of headers
		 * @throws IOException
		 */
		private List<String> readRequest() throws IOException {

			byte[] request = getInputByteArray();
			if (request == null) {
				return null;
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);

			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestStr.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;

		}

		/**
		 * Simple automate that reads a header of HTTP request
		 * 
		 * @param is input stream
		 * @return byte[] data of header information
		 * @throws IOException
		 */
		private byte[] getInputByteArray() throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * This method creates RequestContext for current client.
		 * 
		 * @param fileName input file name
		 */
		private void createContext(String fileName) {

			String ex = getExtension(fileName);
			String mimeType = getMimeFromExtension(ex);

			context = new RequestContext(ostream, params, permPrams, outputCookies, new HashMap<String, String>(), this,
					SID);
			context.setMimeType(mimeType);
			context.setStatusCode(200);
		}

		/**
		 * This method sets mime after checking input extension. If there is no
		 * extension found in mime map then mime is set to "aplication/octet-stream"
		 * 
		 * @param ex input extension path
		 * @return mime
		 */
		private String getMimeFromExtension(String ex) {
			return mimeTypes.get(ex) != null ? mimeTypes.get(ex) : "aplication/octet-stream";
		}

		/**
		 * Method retrieves extension from input file name.
		 * 
		 * @param fileName input file name
		 * @return extension from input file name
		 */
		private String getExtension(String fileName) {
			Objects.requireNonNull(fileName);
			return (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != fileName.length() - 1)
					? fileName.substring(fileName.lastIndexOf(".") + 1)
					: fileName;
		}

	}

	/**
	 * This class represents one session for client with duration of 10 minutes from
	 * the moment session is generated
	 * 
	 * @author antonija
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * String Id for session
		 */
		String sid;
		/**
		 * Session host
		 */
		String host;
		/**
		 * Moment from which this session is not longer valid
		 */
		long validUntil;
		/**
		 * Map of clients data
		 */
		Map<String, String> map;

		/**
		 * Public constructor for creating one session
		 * 
		 * @param sid        input identifier for this session
		 * @param host       session host
		 * @param validUntil input validation expiration
		 * @param mao        input map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Server run command expected");
			return;
		}

		try {
			new SmartHttpServer(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
