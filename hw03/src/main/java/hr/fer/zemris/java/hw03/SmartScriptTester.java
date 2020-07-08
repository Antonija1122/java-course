package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) {
		
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
		
		System.out.println(finalni);

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
