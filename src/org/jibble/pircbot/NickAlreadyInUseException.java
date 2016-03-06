package org.jibble.pircbot;

public class NickAlreadyInUseException extends IrcException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6211451951318844861L;

	/**
     * Constructs a new IrcException.
     *
     * @param e The error message to report.
     */
    public NickAlreadyInUseException(String e) {
        super(e);
    }
    
}