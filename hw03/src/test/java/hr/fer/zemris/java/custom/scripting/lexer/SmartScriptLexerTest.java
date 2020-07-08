package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerException;
import hr.fer.zemris.java.hw03.prob1.Token;
import hr.fer.zemris.java.hw03.prob1.TokenType;

class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testEscaping() {
		// checking escaping -primjer iz zadace 
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}");
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("Example { bla } blu {$=1$}. Nothing interesting {=here}" , lexer.getToken().getValue().toString());
	}
	
	@Test
	public void testMultipleExamples() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= i i * @sin \"0.000\" @decfmt $} bla {$ab6 -2.3 $}");
		
		
		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals("=" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals("i" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals("i" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals("*" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
		assertEquals("sin" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
		assertEquals("0.000" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
		assertEquals("decfmt" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("bla " , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals("AB6" , lexer.getToken().getValue().toString());
		
		assertEquals(SmartScriptTokenType.CONSTANT_DOUBLE, lexer.nextToken().getType());
		assertEquals("-2.3" , lexer.getToken().getValue().toString());	
	}
	
	@Test
	public void testThrows() {
		// checking escaping -primjer iz zadace 
		SmartScriptLexer lexer = new SmartScriptLexer("{$= .i $}");
		
		
		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals("=" , lexer.getToken().getValue().toString());
		
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testThrowsEscaping() {
		// checking escaping throws 
		SmartScriptLexer lexer = new SmartScriptLexer("{$=$} \\* ");
		
		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals("=" , lexer.getToken().getValue().toString());
		
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	
	

}
