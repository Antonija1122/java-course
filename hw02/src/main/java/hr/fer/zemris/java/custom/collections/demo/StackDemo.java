package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	static char[] operators = new char[] { '+', '-', '*', '/', '%' };

	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();

		if (args.length != 1) {
			System.out.println("invalid input. Input should be one string");
		} else {
			String[] arrOfStr = args[0].split("\\s+");
			for (int i = 0; i < arrOfStr.length; i++) {
				try {
					int number = Integer.parseInt(arrOfStr[i]);
					stack.push(number);
				} catch (IllegalArgumentException e) {
					try {
						stack = calculate(arrOfStr[i], stack);
					} catch (IllegalArgumentException e2) {
						System.out.println(e2.getMessage());
						System.exit(1);
					} catch (EmptyStackException e3) {
						System.out.println(
								"Invalid input. Stack is empty. There are too many operators in input or order of operators and numbers is illegal");
						System.exit(1);
					}
				}
			}
			try {
				if (stack.size() != 1) {
					throw new IllegalArgumentException(
							"Invalid input. There are not enough operators in input expression.");
				}
				System.out.println("Expression evaluates to " + (stack.pop()) + ".");
			} catch (EmptyStackException e3) {
				System.out.println(e3.getMessage());
				System.exit(1);
			} catch (IllegalArgumentException e4) {
				System.out.println(e4.getMessage());
				System.exit(1);
			}
		}
	}



	private static ObjectStack calculate(String argument, ObjectStack stack) {

		if (!(new String(operators).contains(argument))) {
			throw new IllegalArgumentException("Invalid input -> " + argument);
		} else {
			int f1 = (Integer) stack.pop();
			int f2 = (Integer) stack.pop();

			switch (argument) {
			case "+":
				stack.push((Object) (f2 + f1));
				break;
			case "-":
				stack.push((Object) (f2 - f1));
				break;
			case "*":
				stack.push((Object) (f2 * f1));
				break;
			case "/":
				if (f1 == 0) {
					throw new IllegalArgumentException("Dividing by zero is not defined.");
				}
				stack.push((Object) (f2 / f1));
				break;
			case "%":
				if (f1 == 0) {
					throw new IllegalArgumentException("Dividing by zero is not defined.");
				}
				stack.push((Object) (f2 % f1));
				break;
			}
		}
		return stack;
	}

}
