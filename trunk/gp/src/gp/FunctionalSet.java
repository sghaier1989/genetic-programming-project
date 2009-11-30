package gp;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents the functional set. For Example: +,-,*,/
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class FunctionalSet extends ArrayList<String> {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6556667568340406821L;

	/**
	 * A method for retrieving a random operator for the functional set.
	 * 
	 * @return - String
	 */
	public String randomOperator() {
		final Random randomGenerator = new Random();
		final int randomOperator = randomGenerator.nextInt(this.size());
		return this.get(randomOperator);

	}

}
