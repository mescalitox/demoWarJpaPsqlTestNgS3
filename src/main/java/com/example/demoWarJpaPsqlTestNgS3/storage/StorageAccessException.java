/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3.storage;

/**
 * @author jaubert
 *
 */
public class StorageAccessException extends Error {

    /**
     * 
     */
    private static final long serialVersionUID = -9179960011771008358L;

    /**
     * 
     */
    public StorageAccessException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public StorageAccessException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public StorageAccessException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public StorageAccessException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public StorageAccessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
