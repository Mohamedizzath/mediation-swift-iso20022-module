/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.model;

/**
 * Class to hold the error details.
 */
public class ErrorModel {
    private boolean isError;
    private String errorCode;
    private String errorMessage;

    public ErrorModel(boolean isError, String errorCode, String errorMessage) {
        this.isError = isError;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorModel(String errorCode, String errorMessage) {
        this(true, errorCode, errorMessage);
    }

    public ErrorModel() {
       this(false, null, null);
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean isError() {
        return this.isError;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
