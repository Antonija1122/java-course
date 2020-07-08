package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;

class LSystemBuilderImplTest {

	@Test
	void testFromHomework() {
		
		LSystem test=createKochCurve(LSystemBuilderImpl::new);
		
		assertEquals(test.generate(0), "F");
		
		assertEquals(test.generate(1), "F+F--F+F");
		
		assertEquals(test.generate(2), "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F");


	}
	
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
		}

}
