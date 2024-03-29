package org.wso2.carbon.module.swiftiso20022.exceptions;

/**
 * Exception to be thrown when parsing a MT message.
 */
public class MTMessageParsingException extends Exception {

    private String errorCode;

    /**
     * Constructor to be thrown for general parsing error.
     *
     * @param errorMessage Parsing error message.
     */
    public MTMessageParsingException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Constructor to be thrown for an error with SWIFT error code.
     *
     * @param errorCode    SWIFT error code.
     * @param errorMessage Parsing error message.
     */
    public MTMessageParsingException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSWIFTError() {
        return getErrorCode() != null;
    }
}
