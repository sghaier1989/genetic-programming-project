/**
 * 
 */
package gp;

/**
 * Exception that are specific to the genetic programming project program.
 * 
 * @author Trevor Greene
 * 
 */
public class GeneticProgrammingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800060114310319398L;

	/**
	 * Exception method with string.
	 * 
	 * @param msg
	 *            - display string
	 */
	public GeneticProgrammingException(final String msg) {
		super(msg);
	}

	/**
	 * Genetic exception method.
	 */
	public GeneticProgrammingException() {
		super("Error Message from myException");
	}

}
