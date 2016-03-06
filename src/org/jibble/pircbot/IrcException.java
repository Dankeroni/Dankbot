package org.jibble.pircbot;

public class IrcException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5030277376032067540L;

	/**
     * Constructs a new IrcException.
     *
     * @param e The error message to report.
     */
    public IrcException(String e) {
        super(e);
    }
    
}
