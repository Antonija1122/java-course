package hr.fer.zemris.java.custom.collections;

public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 24042803L; 
	
	 public EmptyStackException() {
	 } 
	 
	 public EmptyStackException(String message) {
		 super(message);
	 } 
	 

}
