package gp;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Simple example of creating a Log4j appender that will write to a JTextArea.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class TextAreaAppender extends WriterAppender {

	static private JTextArea jTextArea = null;

	/** Set the target JTextArea for the logging information to appear. */
	static public void setTextArea(JTextArea newTextArea) {
		TextAreaAppender.jTextArea = newTextArea;
	}

	/**
	 * Format and then append the loggingEvent to the stored JTextArea.
	 */
	@Override
	public void append(LoggingEvent newLoggingEvent) {
		final String message = this.layout.format(newLoggingEvent);

		// Append formatted message to textarea using the Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextArea.append(message);
			}
		});
	}
}