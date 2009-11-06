package gp;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * A class that represents the functional set. For Example: +,-,*,/
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class FunctionalSet extends ArrayList<String> {
	static Logger logger = Logger.getLogger(FunctionalSet.class);
	private static final long serialVersionUID = 6556667568340406821L;

	/**
	 * A method for retrieving a random operator for the functional set.
	 * 
	 * @return - String
	 */
	public String randomOperator() {
		Random randomGenerator = new Random();
		int randomOperator = randomGenerator.nextInt(this.size());
		randomGenerator = null;
		return this.get(randomOperator);

	}

}
