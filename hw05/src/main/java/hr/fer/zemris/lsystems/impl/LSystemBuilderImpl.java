package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class implements Turtle behavior with L systems
 * 
 * @author antonija
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * productions is a dictionary of productions
	 */
	private Dictionary<Character, String> productions;
	/**
	 * productions is a dictionary of commands
	 */
	private Dictionary<Character, Command> actions;

	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;

	/**
	 * Constructor sets initial values of internal vriables.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, Command>();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	class LocalLSystem implements LSystem {

		/**
		 * Method executes commands from array of commands created in method generate.
		 */
		@Override
		public void draw(int depth, Painter painter) {

			Context ctx = new Context();
			TurtleState newState = new TurtleState(new Vector2D(origin.getX(), origin.getY()),
					new Vector2D(1, 0).rotated(angle * Math.PI / 180), Color.getColor("black"),
					unitLength * Math.pow(unitLengthDegreeScaler, depth));
			ctx.pushState(newState);
			String wholeArray = generate(depth);
			Command command;
			for (int i = 0; i < wholeArray.length(); i++) {
				command = actions.get(wholeArray.charAt(i));
				if (command != null) {
					command.execute(ctx, painter);
				}
			}
		}

		/**
		 * Generates array of chars that represent commands.
		 * 
		 * @return String representation of all commands that have to be executed
		 */
		@Override
		public String generate(int level) {
			StringBuilder sb = new StringBuilder();
			String axiom_pom = new String(axiom);
			sb.append(axiom_pom);
			Character currentChar;
			String currentReplacement;

			for (int i = 0; i < level; ++i) {
				sb.setLength(0);

				for (int j = 0; j < axiom_pom.length(); ++j) {
					currentChar = axiom_pom.charAt(j);
					currentReplacement = productions.get(currentChar);
					if (currentReplacement != null) {
						sb.append(currentReplacement);
					} else {
						sb.append(currentChar);
					}
				}
				axiom_pom = sb.toString();
			}
			return axiom_pom;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * 
	 */
	@Override
	public LSystem build() {

		LocalLSystem newLSystem = new LocalLSystem();

		return newLSystem;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Method parses input text, sets internal variables, creates commands and
	 * productions and adds them to internal maps
	 * 
	 * @return this
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		String[] currentLine;
		for (int i = 0; i < lines.length; i++) {

			if (lines[i].equals(""))
				continue;
			currentLine = lines[i].split("\\s+");

			switch (currentLine[0]) {

			case "origin":
				if (currentLine.length != 3)
					throw new IllegalArgumentException("Invalid input for origin");
				try {
					double x = Double.parseDouble(currentLine[1]);
					double y = Double.parseDouble(currentLine[2]);
					this.setOrigin(x, y);
				} catch (Exception e) {
					System.out.println("Unable to parse origin coordinates");
				}
				break;
			case "angle":

				if (currentLine.length != 2)
					throw new IllegalArgumentException("Invalid input for angle");
				try {
					double newAngle = Double.parseDouble(currentLine[1]);
					this.setAngle(newAngle);
				} catch (Exception e) {
					System.out.println("Unable to parse angle value");
				}
				break;

			case "unitLength":
				if (currentLine.length != 2)
					throw new IllegalArgumentException("Invalid input for unitLength");
				try {
					double unitLength = Double.parseDouble(currentLine[1]);

					if (unitLength < 0)
						throw new IllegalArgumentException("Invalid input for unitLength");

					this.setUnitLength(unitLength);
				} catch (Exception e) {
					System.out.println("Unable to parse angle value");
				}
				break;
			case "unitLengthDegreeScaler":
				createUnitLengthDegreeScaler(currentLine);
				break;
			case "command":
				if (currentLine.length == 3) {
					char[] symbols = currentLine[1].toCharArray();
					if (symbols.length != 1) {
						throw new IllegalArgumentException("Invalid input for symbol in  command line");
					}
					String action = currentLine[2];
					registerCommand(symbols[0], action);
				} else if (currentLine.length == 4) {
					char[] symbols = currentLine[1].toCharArray();
					if (symbols.length != 1) {
						throw new IllegalArgumentException("Invalid input for symbol in  command line");
					}
					String action = currentLine[2] + " " + currentLine[3];
					registerCommand(symbols[0], action);
				} else {
					throw new IllegalArgumentException("Invalid input for command line");
				}
				break;
			case "axiom":
				if (currentLine.length != 2) {
					throw new IllegalArgumentException("Invalid input for axiom line");
				} else {
					this.axiom = currentLine[1];
				}
				break;
			case "production":
				if (currentLine.length != 3) {
					throw new IllegalArgumentException("Invalid input for production line");
				} else {
					char symbol = currentLine[1].charAt(0);
					registerProduction(symbol, currentLine[2]);
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid input. Unrecognized action ot production" + currentLine[0]);
			}
		}
		return this;
	}

	/**
	 * Lexer method for unit degree scaller.
	 * 
	 * @param currentLine
	 */
	private void createUnitLengthDegreeScaler(String[] currentLine) {

		switch (currentLine.length) {
		case 2:
			try {
				double newUnitLengthDegreeScaler = Double.parseDouble(currentLine[1]);

				if (unitLength < 0)
					throw new IllegalArgumentException("Invalid input for unitLength");

				this.setUnitLengthDegreeScaler(newUnitLengthDegreeScaler);
			} catch (NumberFormatException e2) {

				if (currentLine[1].contains("/")) {
					String[] numbers = currentLine[1].split("/");
					if (numbers.length != 2)
						throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler");
					else {
						try {
							double number1 = Double.parseDouble(numbers[0]);
							double number2 = Double.parseDouble(numbers[1]);
							this.setUnitLengthDegreeScaler(number1 / number2);
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler");
						}
					}
				} else {
					throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler");
				}
			}
			break;

		case 3:
			if (currentLine[1].charAt(currentLine[1].length() - 1) == '/') {
				try {
					double number1 = Double.parseDouble(currentLine[1].substring(0, currentLine[1].length() - 1));
					double number2 = Double.parseDouble(currentLine[2]);
					this.setUnitLengthDegreeScaler(number1 / number2);

				} catch (NumberFormatException e2) {
					throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler /");
				}
			} else if (currentLine[2].charAt(0) == '/') {
				try {
					double number1 = Double.parseDouble(currentLine[1]);
					double number2 = Double.parseDouble(currentLine[2].substring(1, currentLine[2].length()));
					this.setUnitLengthDegreeScaler(number1 / number2);

				} catch (NumberFormatException e2) {
					throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler /");
				}
			}
			break;

		case 4:

			if (currentLine[2].equals("/")) {
				try {
					this.setUnitLengthDegreeScaler(
							Double.parseDouble(currentLine[1]) / Double.parseDouble(currentLine[3]));

				} catch (NumberFormatException e2) {
					throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler /");
				}
			} else {
				throw new IllegalArgumentException("Invalid input for unitLengthDegreeScaler /");
			}
			break;

		default:
			throw new IllegalArgumentException("Invalid input. Too many arguments in unitLengthDegreeScaler line");

		}
	}

	/**
	 * Method registers new command and adds it to the map of commands and returns
	 * this LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command;
		String[] s = action.split("\\s+");

		switch (s[0]) {

		case "rotate":
			try {
				double newAngle = Double.parseDouble(s[1]);
				command = new RotateCommand(newAngle);
				actions.put(symbol, command);
				return this;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;

		case "draw":
			if (s.length != 2) {
				throw new IllegalArgumentException("Invalid action string input for draw");
			} else {
				try {
					double step = Double.parseDouble(s[1]);
					command = new DrawCommand(step);
					actions.put(symbol, command);
					return this;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;

		case "skip":
			if (s.length != 2) {
				throw new IllegalArgumentException("Invalid action string input for draw");
			} else {
				try {
					double step = Double.parseDouble(s[1]);

					command = new SkipCommand(step);
					actions.put(symbol, command);
					return this;

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;

		case "push":
			if (s.length != 1) {
				throw new IllegalArgumentException("Invalid action string input for push");
			} else {
				command = new PushCommand();
				actions.put(symbol, command);
				return this;
			}

		case "pop":
			if (s.length != 1) {
				throw new IllegalArgumentException("Invalid action string input for pop");
			} else {
				command = new PopCommand();
				actions.put(symbol, command);
				return this;
			}

		case "color":
			try {
				command = new ColorCommand(Color.decode("#" + s[1]));
				actions.put(symbol, command);
				return this;
			} catch (Exception e) {
				System.out.println("invalid color number");
			}
			break;

		case "scale":
			try {
				command = new ScaleCommand(Double.parseDouble(s[1]));
				actions.put(symbol, command);
				return this;
			} catch (Exception e) {
				System.out.println("invalid scale input");
			}
			break;

		default:
			throw new IllegalArgumentException("Command is not recognized.");
		}
		return this;
	}

	/**
	 * Method adds production to map of production
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Setter method for angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Setter method for axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Setter method for origin
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Setter method for unitLength
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Setter method for unitLengthDegreeScaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
