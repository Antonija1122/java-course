package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import hr.fer.zemris.java.webserver.IDispatcher;

/**
 * The class RequestContext has following private properties OutputStream
 * outputStream and Charset charset; following public write-only properties
 * String encoding (defaults to "UTF-8"), int statusCode (defaults to 200),
 * String statusText (defaults to "OK"), String mimeType (defaults to
 * "text/html"), Long contentLength (defaults to null); following private
 * collections Map<String,String> parameters, Map<String,String>
 * temporaryParameters, Map<String,String> persistentParameters, List<RCCookie>
 * outputCookies and private property boolean headerGenerated (deafults to
 * false). After the moment the header is created and written, all attempts to
 * change any of properties encoding, statusCode, statusText, mimeType,
 * outputCookies, contentLength must throw RuntimeException;
 * 
 * @author antonija
 *
 */
public class RequestContext {
	/**
	 * Write only property outputStream
	 */
	private OutputStream outputStream;
	/**
	 * Write only property charset
	 */
	private Charset charset;
	/**
	 * Write only property encoding
	 */
	private String encoding;
	/**
	 * Write only property statusCode
	 */
	private int statusCode;
	/**
	 * Write only property statusText
	 */
	private String statusText;
	/**
	 * Write only property mimeType
	 */
	private String mimeType;
	/**
	 * Write only property contentLength
	 */
	private Long contentLength;
	/**
	 * The map parameters are read-only maps.
	 */
	private Map<String, String> parameters;
	/**
	 * temporaryParameters and persistentParameters are readable and writable.
	 */
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	/**
	 * List of cookies
	 */
	private List<RCCookie> outputCookies;
	/**
	 * variable that shows if header is generated or not
	 */
	private boolean headerGenerated;
	/**
	 * Private dispatcher
	 */
	private IDispatcher dispatcher;
	/**
	 * Session id
	 */
	private String SID;

	/**
	 * Public constructor initializes private variables and maps to input maps and
	 * values. If input maps are null then maps are initialized to new empty maps
	 * 
	 * @param outputStream         input outputstream
	 * @param parameters           input parameters
	 * @param persistentParameters input persistentParameters
	 * @param outputCookies        input outputCookies
	 * @param temporaryParameters  input temporaryParameters
	 * @param dispatcher           input dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {

		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.SID = sid;
		// this.temporaryParameters = temporaryParameters == null ? new HashMap<>() :
		// temporaryParameters;
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		;
		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.contentLength = null;
		this.headerGenerated = false;
		this.dispatcher = dispatcher;

	}

	/**
	 * Public constructor initializes private variables and maps to input maps and
	 * values. If input maps are null then maps are initialized to new empty maps
	 * 
	 * @param outputStream         input outputstream
	 * @param parameters           input parameters
	 * @param persistentParameters input persistentParameters
	 * @param outputCookies        input outputCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null, null);

	}

	/**
	 * Getter method for dispatcher
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists).
	 * 
	 * @param name input key
	 * @return value from parameters map associated with name
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map (note, this
	 * set is read-only).
	 * 
	 * @return Set<String> of parameters
	 */
	public Set<String> getParameterNames() {
		Set<String> set = parameters.entrySet().stream().map(p -> p.getKey()).collect(Collectors.toSet());
		return Collections.unmodifiableSet(set);

	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 * 
	 * @param name input key
	 * @return value from parameters map associated with name
	 */
	public String getPersistentParameter(String name) {
		Objects.requireNonNull(name);
		return persistentParameters.get(name);

	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters map
	 * (note, this set is readonly).
	 * 
	 * @return Set<String> of persistentParameters
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> set = persistentParameters.entrySet().stream().map(p -> p.getKey()).collect(Collectors.toSet());
		return Collections.unmodifiableSet(set);
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name input key
	 * @return value from persistentParameters map associated with name
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name key for removing persistentParameters
	 */
	public void removePersistentParameter(String name) {
		Objects.requireNonNull(name);
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name input key
	 * @return value from temporaryParameters map associated with name
	 */
	public String getTemporaryParameter(String name) {
		Objects.requireNonNull(name);
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map
	 * (note, this set is readonly).
	 * 
	 * @param name key for removing temporaryParameters
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> set = temporaryParameters.entrySet().stream().map(p -> p.getKey()).collect(Collectors.toSet());
		return Collections.unmodifiableSet(set);
	}

	/**
	 * method that retrieves an identifier which is unique for current user session
	 * (for now, implemented to return empty string):
	 * 
	 * @return empty string
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * Method that stores a value to temporaryParameters map:
	 * 
	 * @param name  input key
	 * @param value input value
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map:
	 * 
	 * @param name input key for removing temporaryParameter
	 */
	public void removeTemporaryParameter(String name) {
		Objects.requireNonNull(name);
		temporaryParameters.remove(name);
	}

	/**
	 * Setter method for encoding
	 * 
	 * @param encoding input encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException();
		this.encoding = encoding;
	}

	/**
	 * Setter method for statusCode
	 * 
	 * @param statusCode input statusCode
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException();
		this.statusCode = statusCode;
	}

	/**
	 * Setter method for statusText
	 * 
	 * @param statusText input statusText
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException();
		this.statusText = statusText;
	}

	/**
	 * Setter method for mimeType
	 * 
	 * @param mimeType input mimeType
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException();
		this.mimeType = mimeType;
	}

	/**
	 * Setter method for contentLength
	 * 
	 * @param contentLength input contentLength
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated)
			throw new RuntimeException();
		this.contentLength = contentLength;
	}

	/**
	 * Method writes input data to output stream. If header is not yet generated
	 * this method generates it
	 * 
	 * @param data input data
	 * @return this RequestCon text
	 * @throws IOException if method can not write to output stream
	 */
	public RequestContext write(byte[] data) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Method writes input data to output stream. If header is not yet generated
	 * this method generates it
	 * 
	 * @param data   input data
	 * @param offset index of first byte to write in output stream
	 * @param len    number of bytes to write in output stream
	 * @return this RequestCon text
	 * @throws IOException if method can not write to output stream
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Method writes input data to output stream. If header is not yet generated
	 * this method generates it
	 * 
	 * @param text input text
	 * @return this RequestCon text
	 * @throws IOException if method can not write to output stream
	 */
	public RequestContext write(String text) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * This method generates header for this request and sets headergenerated value
	 * to true
	 * 
	 * @throws IOException
	 */
	private synchronized void generateHeader() throws IOException {

		if (headerGenerated)
			return;

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");

		sb.append("Content-Type: " + getMimeType() + "\r\n");
		if (contentLength != null) {
			sb.append("Content-Length:" + contentLength + "\r\n");
		}

		if (!outputCookies.isEmpty()) {
			outputCookies.forEach(cookie -> {
				sb.append(cookie + "\r\n");
			});
		}
		sb.append("\r\n");

		outputStream.write((sb.toString()).getBytes(StandardCharsets.ISO_8859_1));
		// outputStream.flush();
		headerGenerated = true;
		charset = Charset.forName(encoding);

	}

	/**
	 * Getter method for mime type
	 * 
	 * @return mime type
	 */
	private String getMimeType() {
		return mimeType.startsWith("text/") ? mimeType + "; charset=" + encoding : mimeType;
	}

	/**
	 * Method for contentLength
	 * 
	 * @param contentLength input contentLength
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (headerGenerated)
			throw new RuntimeException();

		Objects.requireNonNull(rcCookie);
		outputCookies.add(rcCookie);
	}

	/**
	 * RCCookie class has read-only String properties name, value, domain and path
	 * and read-only Integer property maxAge.
	 * 
	 * @author antonija
	 *
	 */
	public static class RCCookie {
		/**
		 * String name for this cookie
		 */
		private String name;
		/**
		 * String value for this cookie
		 */
		private String value;
		/**
		 * String domain for this cookie
		 */
		private String domain;
		/**
		 * String path for this cookie
		 */
		private String path;
		/**
		 * Integer maxAge for this cookie
		 */
		private Integer maxAge;

		/**
		 * Public constructor sets cookie values to initial input values
		 * 
		 * @param name   input name
		 * @param value  input value
		 * @param maxAge input maxAge
		 * @param domain input domain
		 * @param path   input path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter method for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter method for value
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter method for domain
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter method for path
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter method for maxAge
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Set-Cookie: " + name + "=" + "\"" + value + "\"");
			sb.append(domain != null ? "; " + "Domain=" + domain : "");
			sb.append(path != null ? "; " + "Path=" + path : "");
			sb.append(maxAge != null ? "; " + "Max-Age=" + maxAge : "");
			sb.append("; HttpOnly");
			return sb.toString();
		}

	}

}
