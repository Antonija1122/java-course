package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testCnstructorAndGet() {
		
		Vector2D vector=new Vector2D (2 ,3);
		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
		
		
		Vector2D vector2=new Vector2D (0 ,-3.53);
		assertEquals(vector2.getX(), 0);
		assertEquals(vector2.getY(), -3.53);
		
	}
	
	@Test
	void testTranslate() {
		
		Vector2D vector=new Vector2D (2 ,3);
		
		vector.translate(new Vector2D (5.5 ,-5.5));
		
		assertEquals(vector.getX(), 7.5);
		assertEquals(vector.getY(), -2.5);
		

	}
	
	
	@Test
	void testTranslated() {
		
		Vector2D vector=new Vector2D (2 ,3);
		
		Vector2D newVector=vector.translated(new Vector2D (5.5 ,-5.5));

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
		
		
		assertEquals(newVector.getX(), 7.5);
		assertEquals(newVector.getY(), -2.5);
		
	}
	
	
	@Test
	void testTranslated2() {
		
		Vector2D vector=new Vector2D (2 ,3);
		
		Vector2D newVector=vector.translated(new Vector2D (0,0));

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
		
		
		assertEquals(newVector.getX(), 2);
		assertEquals(newVector.getY(), 3);

	}
	
	
	@Test
	void testRotate() {
		
		Vector2D vector=new Vector2D (2 ,3);
		vector.rotate(Math.PI/2);
		
		assertEquals(vector.getX(), -3);
		assertEquals(vector.getY(), 2);
	}
	
	@Test
	void testRotate2() {
		
		Vector2D vector=new Vector2D (3 ,3);
		vector.rotate(Math.PI);
		assertEquals(new Vector2D (-3 ,-3), vector);
	}
	
	
	
	
	
	@Test
	void testRotated() { // za +90 
		
		Vector2D vector=new Vector2D (2 ,3);
		
		Vector2D newVector=vector.rotated(Math.PI/2);
		Vector2D calc=new Vector2D (-3 ,2);
		
		assertEquals(newVector, calc);

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
	}
	
	
	@Test
	void testRotated2() { //za -90
		
		Vector2D vector=new Vector2D (2 ,3);
		
		Vector2D newVector=vector.rotated(-Math.PI/2);
		Vector2D calc=new Vector2D (3 ,-2);
		
		assertEquals(newVector, calc);

		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
	}
	

	
	
	@Test
	void testScale() {
		
		Vector2D vector=new Vector2D (2 ,3);
		vector.scale(2);
		Vector2D calc=new Vector2D (4 ,6);

		assertEquals(calc, vector);
		
		vector=new Vector2D (2 ,3);

		vector.scale(1.0/4);
		calc=new Vector2D (2.0/4 ,3.0/4);

		assertEquals(calc, vector);
	}
	
	
	
	
	@Test
	void testScaled() {
		
		Vector2D vector=new Vector2D (2 ,3);
		Vector2D newVector=vector.scaled(2);	
		Vector2D calc=new Vector2D (4 ,6);
		assertEquals(calc, newVector);
		
		
		vector=new Vector2D (2 ,3);
		newVector=vector.scaled(1.0/4);
		calc=new Vector2D (2.0/4 ,3.0/4);
		assertEquals(calc, newVector);
		
		assertEquals(vector.getX(), 2);
		assertEquals(vector.getY(), 3);
		
	}
	
	

	
	@Test
	void testCopy() {
		
		Vector2D vector=new Vector2D (2 ,3);
		Vector2D newVector=vector.copy();
		
		assertEquals(vector, newVector);

			
	}

}
