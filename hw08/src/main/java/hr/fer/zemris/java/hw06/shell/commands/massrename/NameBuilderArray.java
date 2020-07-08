package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.List;

/**
 * NameBuilderArray implements NameBuilder
 * @author antonija
 *
 */
public class NameBuilderArray implements NameBuilder{
	
	private List<NameBuilder> builders;
	
	/**
	 * Public constructor gives List<NameBuilder> of builders 
	 * @param builders List<NameBuilder> 
	 */
	public NameBuilderArray(List<NameBuilder>  builders) {
		this.builders=builders;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method executes all execute commands of NameBuilders in private list of builders
	 */
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for(NameBuilder b:builders) {
			b.execute(result, sb);
		}
	}

}
