package com.emerging.framework.core.exception;

public class InOutException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -8149737560883682568L;

	// ~ Constructors
	// ------------------------------------------------------------------

	/**
	 * Creates a new CacheException object.
	 */
	public InOutException() {

		super();
	}

	/**
	 * @param message
	 */
	public InOutException(final String message) {

		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InOutException(final String message, final Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public InOutException(final Throwable cause) {

		super(cause);
	}
}
