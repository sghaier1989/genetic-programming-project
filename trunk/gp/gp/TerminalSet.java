package gp;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents the terminal set.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class TerminalSet extends ArrayList<String> {
	// private static Logger logger = Logger.getLogger(TerminalSet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return - Returns a random operand from the terminal set
	 */
	public String randomOperand() {
		Random randomGenerator = new Random();
		int randomOperand = randomGenerator.nextInt(this.size());
		return this.get(randomOperand);
	}

}
