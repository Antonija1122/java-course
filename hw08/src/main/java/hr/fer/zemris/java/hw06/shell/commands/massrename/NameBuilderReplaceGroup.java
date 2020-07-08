package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * NameBuilderReplaceGroup implements NameBuilder
 * 
 * @author antonija
 *
 */
public class NameBuilderReplaceGroup implements NameBuilder {

	/**
	 * Index of group that has to be appended to sb as a part of new name for file
	 */
	private int group;

	/**
	 * First char of this string represents character which will format appended
	 * string to wanted length (chars are '0' or ' '). The length is represented
	 * with number after the first char.
	 */
	private String minLength;

	/**
	 * Public constructor gives initial values to group index and minLength
	 * 
	 * @param group     index of group
	 * @param minLength index
	 */
	public NameBuilderReplaceGroup(int group, String minLength) {
		this.group = group;
		this.minLength = minLength;
	}

	/**
	 * {@inheritDoc} 
	 * This method formats part of string name by minLength and
	 * appends group part to input StringBuilder
	 */
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		String part = result.group(this.group);
		if (minLength == null) {
			sb.append(part);
		} else {
			if (minLength.charAt(0) == '0' && minLength.length()!=1) {
				replace(part, '0', sb);
			} else if(minLength.equals("0") || minLength.equals("00")) {
				sb.append(part);
			} else {
				replace(part, ' ', sb);
			}
		}
	}

	/**
	 * This method formats part with input char c and appends part to StringBuilder
	 * @param part String that is appended to file name
	 * @param c char with which part is formated
	 * @param sb StringBuilder that builds new file name
	 */
	private void replace(String part, char c, StringBuilder sb) {
		int length = Integer.parseInt(minLength.replaceFirst("0", ""));
		while (part.length() < length) {
			part = c + part;
		}
		sb.append(part);
	}


}
