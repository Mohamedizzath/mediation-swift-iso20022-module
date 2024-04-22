/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
