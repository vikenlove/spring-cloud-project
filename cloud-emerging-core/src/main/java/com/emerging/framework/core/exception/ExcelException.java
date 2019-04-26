package com.emerging.framework.core.exception;


/**
 * This is exception class, it will throws ExcelException when there are something wrong with importing/exporting excel file
 * @ClassName: ExcelException
 * @author isaac
 * @date 2014-8-2
 */
public class ExcelException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2289537528581675291L;

    /**
     * Creates a new GroupException object.
     */
    public ExcelException() {

        super();
    }

    /**
     * @param message
     */
    public ExcelException(final String message) {

        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ExcelException(final String message, final Throwable cause) {

        super(message, cause);
    }

    /**
     * @param cause
     */
    public ExcelException(final Throwable cause) {

        super(cause);
    }
}