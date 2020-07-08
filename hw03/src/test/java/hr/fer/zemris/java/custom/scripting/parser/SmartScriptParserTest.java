package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {

	@Test
	void testFromHomework1() {
		
		String docBody = "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		String finalni=createOriginalDocumentBody(document2);
		assertEquals(originalDocumentBody, finalni);
		System.out.println(finalni);

		
	}
	
	
	@Disabled
	@Test
	void testFromHomeworkEscaping1() {
		
		
		String docBody = "{$= Joe \"Long\" Smith $}";

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees

		String finalni=createOriginalDocumentBody(document2);

		assertEquals(originalDocumentBody, finalni);
		
	}
	
	
	@Test
	void testFromHomeworkEscaping2() {
		
		String docBody = "Example { bla } blu \\{$=1$}. Nothing interesting {=here}.{";

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		String finalni=createOriginalDocumentBody(document2);
		assertEquals(originalDocumentBody, finalni);
	}
	
	
	@Test
	void testFromHomeworkCombination() {
		
		String docBody = "Example {$= @pow a b \"1\" $} ";

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		String finalni=createOriginalDocumentBody(document2);
		assertEquals(originalDocumentBody, finalni);
	}
	
	@Test
	void testFromHomeworkCombinationTwoForLoops() {
		
		String docBody = "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				"{$ FOR year 10 year2 $}\r\n" + 
				"{$END$}"+
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		String finalni=createOriginalDocumentBody(document2);
		assertEquals(originalDocumentBody, finalni);
	}
	
	
	@Test
	void testThrowsEND() {
		
		String docBody = "Example {$= @pow a b \"1\" $} {$END$}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	void testThrowsEND2() {
		
		String docBody = "Example {$ FOR i 1 10 1 $} {$END i34 $}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	void testThrowsWrongBrackets() {
		
		String docBody = "Example {$= @pow {$  {$ a  b \"1\" $} {$END i$}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	void testThrowsIllegalInput() {
		
		String docBody = "Example {$= @pow ??  a  b \"1\" $} {$END i$}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	void testThrowsIllegaForLoop() {
		
		String docBody = "{$ FOR year 1 10 \"1\" \"10\" $}"; //to many arguments
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
		
		String docBody2 = "{$ FOR year 1 $}"; // to few
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody2));
		
		String docBody3 = "{$ FOR 23year 1 $}";	//Illegal variable name
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody3));
	}
	
	
	public static String createOriginalDocumentBody(Node document) {
		int childreNum=document.numberOfChildren();
		String original="";
		if(document instanceof DocumentNode) {
			
			for(int i=0; i<childreNum; i++) {
								
				original=original + createOriginalDocumentBody(document.getChild(i));
			}
			
		} else if(document instanceof TextNode) {
			
			original=original +document.getText();
			
		} else if (document instanceof EchoNode) {
			
			
			original=original + "{$= "+ document.getText() +" $}";
			
		} else if (document instanceof ForLoopNode) {
			
			
			original=original + "{$ FOR"+ document.getText() +" $}";
			
			for(int i=0; i<childreNum; i++) {
				
				original=original +createOriginalDocumentBody(document.getChild(i));
			}
			
			original=original + "{$ END $}";
		}
		return original;
	}

}
