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

package org.wso2.carbon.module.swiftiso20022.models.mt103models.blocks;

/**
 * Class that models request payload block03.
 */
public class Block03 {

    private String serviceIdentifier;
    private String bankingPriority;
    private String messageUserReference;
    private String validationFlag;
    private String endToEndReference;
    private String serviceTypeIdentifier;

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getBankingPriority() {
        return bankingPriority;
    }

    public void setBankingPriority(String bankingPriority) {
        this.bankingPriority = bankingPriority;
    }

    public String getMessageUserReference() {
        return messageUserReference;
    }

    public void setMessageUserReference(String messageUserReference) {
        this.messageUserReference = messageUserReference;
    }

    public String getValidationFlag() {
        return validationFlag;
    }

    public void setValidationFlag(String validationFlag) {
        this.validationFlag = validationFlag;
    }

    public String getEndToEndReference() {
        return endToEndReference;
    }

    public void setEndToEndReference(String endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

    public String getServiceTypeIdentifier() {
        return serviceTypeIdentifier;
    }

    public void setServiceTypeIdentifier(String serviceTypeIdentifier) {
        this.serviceTypeIdentifier = serviceTypeIdentifier;
    }
}
