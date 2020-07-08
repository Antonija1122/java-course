package Demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * Third and las example from homework 5. Demonstrates last way of building LSystem. 
 * @author antonija
 *
 */
public class Glavni3 {

	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
