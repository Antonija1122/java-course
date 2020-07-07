package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {


	@Test
	public void constructor1Test() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(complex.getReal(), 0);
		assertEquals(complex.getImaginary(), 0);
	}

	@Test
	public void constructor2Test() {
		ComplexNumber complex = new ComplexNumber(2, -5.5);
		assertEquals(complex.getReal(), 2);
		assertEquals(complex.getImaginary(), -5.5);
	}

	@Test
	public void fromRealTest() {
		ComplexNumber complex = ComplexNumber.fromReal(5.9);
		assertEquals(complex.getReal(), 5.9);
		assertEquals(complex.getImaginary(), 0);

	}

	@Test
	public void fromImaginaryTest() {
		ComplexNumber complex = ComplexNumber.fromImaginary(-5.9);
		assertEquals(complex.getReal(), 0);
		assertEquals(complex.getImaginary(), -5.9);
	}

	@Test //
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(4, Math.PI / 2);
		assertEquals(round(complex.getReal()), 0);
		assertEquals(round(complex.getImaginary()), 4);
	}

	@Test //
	public void fromMagnitudeAndAngleTest2() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(6.8, 3 * Math.PI / 4);
		assertEquals(round(complex.getReal()), round(-17 * Math.sqrt(2) / 5));
		assertEquals(round(complex.getImaginary()), round(17 * Math.sqrt(2) / 5));
	}

	@Test
	public void fromMagnitudeAndAngleTestZeroAndThrows() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(0, 3 * Math.PI / 4);
		assertEquals(round(complex.getReal()), round(0));
		assertEquals(round(complex.getImaginary()), round(0));

		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.fromMagnitudeAndAngle(-4, 3 * Math.PI / 4));
	}

	@Test
	public void getRealTest() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(complex.getReal(), 0);

		complex = new ComplexNumber(-5.6757, 7);
		assertEquals(complex.getReal(), -5.6757);
	}

	@Test
	public void getImaginaryTest() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(complex.getReal(), 0);

		complex = new ComplexNumber(-5.6757, 7.987);
		assertEquals(complex.getImaginary(), 7.987);
	}

	@Test
	public void getMagnitudeTest() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(Math.sqrt(Math.pow(complex.getReal(), 2)) + Math.sqrt(Math.pow(complex.getImaginary(), 2)), 0);

		complex = new ComplexNumber(-5.6757, 7.987);
		assertEquals(Math.sqrt(Math.pow(complex.getReal(), 2)) + Math.sqrt(Math.pow(complex.getImaginary(), 2)),
				Math.sqrt(Math.pow(-5.6757, 2)) + Math.sqrt(Math.pow(7.987, 2)));
	}

	@Test
	public void getAngleTest() {
		
		ComplexNumber complex = new ComplexNumber(-2*Math.sqrt(2), 2*Math.sqrt(2));
		assertEquals(round(complex.getAngle()), round(Math.atan(2*Math.sqrt(2) /(-2*Math.sqrt(2))) + Math.PI));

		complex = new ComplexNumber(-5.6757, 7.987);
		assertEquals(round(complex.getAngle()), round(Math.atan(7.987 /-5.6757) + Math.PI));
	}

	@Test
	public void addTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber complex2 = new ComplexNumber(-5.6, 7.0);
		ComplexNumber complex3=complex2.add(complex);
		
		assertEquals(complex3.getReal(), -2-5.6);
		assertEquals(complex3.getImaginary(), 2+7.0);

		complex = new ComplexNumber(0,0);
		complex2 = new ComplexNumber(2,8);
		complex3=complex2.add(complex);
		
		assertEquals(complex3.getReal(), 2);
		assertEquals(complex3.getImaginary(), 8);
		
	}
	@Test
	public void subbTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber complex2 = new ComplexNumber(-5.6, 7.0);
		ComplexNumber complex3=complex2.sub(complex);
		
		assertEquals(complex3.getReal(), -5.6-(-2));
		assertEquals(complex3.getImaginary(), 7.0-2);

		complex = new ComplexNumber(0,0);
		complex2 = new ComplexNumber(2,8);
		complex3=complex2.sub(complex);
		
		assertEquals(complex3.getReal(), 2);
		assertEquals(complex3.getImaginary(), 8);
		
	}
	
	@Test
	public void mullTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber complex2 = new ComplexNumber(-5.6 , 7.0);
		ComplexNumber complex3=complex2.mul(complex);

		assertEquals(round(complex3.getReal()), -2.8); 	//WolframAlpha 
		assertEquals(complex3.getImaginary(), -25.2);

		complex = new ComplexNumber(5.6, -9.2);
		complex2 = new ComplexNumber(2,8);
		complex3=complex2.mul(complex);
		
		assertEquals(complex3.getReal(), 84.8);
		assertEquals(complex3.getImaginary(), 26.4);
		
	}
	
	@Test
	public void divTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber complex2 = new ComplexNumber(-5.6 , 7.0);
		ComplexNumber complex3=complex.div(complex2);
	
		assertEquals(round(complex3.getReal()), 0.3136); 	//WolframAlpha 
		assertEquals(round(complex3.getImaginary()), 0.0348);

		complex = new ComplexNumber(5.6, -9.2);
		complex2 = new ComplexNumber(2,8);
		complex3=complex.div(complex2);
		
		assertEquals(round(complex3.getReal()), -0.9176);
		assertEquals(round(complex3.getImaginary()), -0.9294);
		
	}
	
	@Test
	public void powerTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber complex3=complex.power(4);
	
		assertEquals(round(complex3.getReal()), -64); 	//WolframAlpha 
		assertEquals(round(complex3.getImaginary()), 0);

		complex = new ComplexNumber(5.6, -9.2);
		complex3=complex.power(3);

		assertEquals(round(complex3.getReal()), -1246.336);
		assertEquals(round(complex3.getImaginary()), -86.848);
		
		ComplexNumber complex5 = new ComplexNumber(-2, 2);
		assertThrows(IllegalArgumentException.class, () -> complex5.power(0));
		assertThrows(IllegalArgumentException.class, () -> complex5.power(-5));


	}
	
	@Test
	public void rootTest() {
		
		ComplexNumber complex = new ComplexNumber(-2, 2);
		ComplexNumber[] complex3=complex.root(4);
		
		assertEquals(round(complex3[0].getReal()), 1.0783);
		assertEquals(round(complex3[1].getReal()), -0.7205);
		assertEquals(round(complex3[2].getReal()), -1.0783);
		assertEquals(round(complex3[3].getReal()), 0.7205);
		
		assertEquals(round(complex3[0].getImaginary()), 0.7205);
		assertEquals(round(complex3[1].getImaginary()), 1.0783);
		assertEquals(round(complex3[2].getImaginary()), -0.7205);
		assertEquals(round(complex3[3].getImaginary()), -1.0783);
		
		ComplexNumber complex5 = new ComplexNumber(-2, 2);
		assertThrows(IllegalArgumentException.class, () -> complex5.power(-5));
		
		
	}
	
	@Test
	public void toStringTest(){
	
		ComplexNumber complex = new ComplexNumber(0, -3.8);
		assertEquals(complex.getReal() + "" + complex.getImaginary() + "i", complex.toString());

		complex = new ComplexNumber(-3.53, 0);
		assertEquals(complex.getReal() + "+" + complex.getImaginary() + "i", complex.toString());

		complex = new ComplexNumber(6.8, 0);
		assertEquals(complex.getReal() + "+" + complex.getImaginary() + "i", complex.toString());

		complex = new ComplexNumber(6.8666,3.0);
		assertEquals(complex.getReal() + "+" + complex.getImaginary() + "i", complex.toString());
		
	}
	
	
	@Test
	public void parseTest() {
		ComplexNumber c2 = ComplexNumber.parse("i");
		assertEquals(c2.getReal() + "+" + c2.getImaginary() + "i", "0.0+1.0i");

		c2 = ComplexNumber.parse("-3.8i");
		assertEquals(c2.getReal() + "" + c2.getImaginary() + "i", "0.0-3.8i");

		c2 = ComplexNumber.parse("-3.53");
		assertEquals(c2.getReal() + "+" + c2.getImaginary() + "i", "-3.53+0.0i");

		c2 = ComplexNumber.parse("6.8");
		assertEquals(c2.getReal() + "+" + c2.getImaginary() + "i", "6.8+0.0i");

		c2 = ComplexNumber.parse("6.8666");
		assertEquals(c2.getReal() + "+" + c2.getImaginary() + "i", "6.8666+0.0i");

		c2 = ComplexNumber.parse("-2.1+6.8i");
		assertEquals(c2.getReal() + "+" + c2.getImaginary() + "i", "-2.1+6.8i");

		c2 = ComplexNumber.parse("2.13-6.8789i");
		assertEquals(c2.getReal() + "" + c2.getImaginary() + "i", "2.13-6.8789i");

		c2 = ComplexNumber.parse("+2.13-6.8789i");
		assertEquals(c2.getReal() + "" + c2.getImaginary() + "i", "2.13-6.8789i");

		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("ghfr6.8789i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-8zt9i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("2,6"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("2.7+3g9i"));

	}

	private double round(double number) {
		double broj = number * 10000;
		broj = Math.round(broj);
		broj = broj / 10000;
		return broj;

	}
	


}
