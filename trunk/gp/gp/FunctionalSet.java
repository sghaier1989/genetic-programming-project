package gp;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Description: A class that represents the functional terminal set
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class FunctionalSet extends ArrayList<String> {

	static Logger logger = Logger.getLogger(FunctionalSet.class);
	private static final long serialVersionUID = 6556667568340406821L;

	/**
	 * @return - Returns a random operator from the functional set
	 */
	public String randomOperator() {
		Random randomGenerator = new Random();
		int randomOperator = randomGenerator.nextInt(this.size());
		return this.get(randomOperator);
	}

}
