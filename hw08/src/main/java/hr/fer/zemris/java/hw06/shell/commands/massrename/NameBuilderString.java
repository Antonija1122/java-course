package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * NameBuilderString implements NameBuilder
 * @author antonija
 *
 */
public class NameBuilderString implements NameBuilder{
	
	/**
	 * Part of string that has to be appended to new file name
	 */
	private String part; 
	
	/**
	 * Public constructor gives String part to this instance of NameBuilderString 
	 * @param part
	 */
	public NameBuilderString(String part) {
		this.part=part; 
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method appends part of name to input StringBuilder
	 */
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(part);
	}
	

}
