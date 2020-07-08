package hr.fer.zemris.java.hw03.prob1.Demo;


import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerState;
import hr.fer.zemris.java.hw03.prob1.Token;
import hr.fer.zemris.java.hw03.prob1.TokenType;



// mooji primjeri
public class Demo2 {

	public static void main(String[] args) {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("Janko 3# Ivana26\\a 463abc#zzz123 #..5tkk ");

		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.WORD, "Janko"));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.NUMBER, Long.valueOf(3)));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.SYMBOL, Character.valueOf('#')));

		
		lexer.setState(LexerState.EXTENDED);
		
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.WORD, "Ivana26\\a"));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.WORD, "463abc"));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.SYMBOL, Character.valueOf('#')));

		
		lexer.setState(LexerState.BASIC);
		
		System.out.println(lexer.nextToken()+ " + " + new Token(TokenType.WORD, "zzz"));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.NUMBER, Long.valueOf(123)));
		System.out.println(lexer.nextToken()+" + "+ new Token(TokenType.SYMBOL, Character.valueOf('#')));

		lexer.setState(LexerState.EXTENDED);

		System.out.println(lexer.nextToken()+ " + " + new Token(TokenType.WORD, "..5tkk"));

		
		System.out.println(lexer.nextToken()+ " + " + new Token(TokenType.EOF, null));

	}

}
