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

package org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

/**
 * Class that models request payload block03.
 */
public class Block03 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block03";
    String serviceIdentifier;
    String bankingPriority;
    String messageUserReference;
    String validationFlag;
    String endToEndReference;
    String serviceTypeIdentifier;

    @Override
    public ErrorModel validate() {
        // TODO: replace with constants
        if (StringUtils.isBlank(serviceIdentifier)) {
            return new ErrorModel("U03", "Service Identifier is required");
        }
        if (serviceIdentifier.length() != 3) {
            return new ErrorModel("U03", "Service Identifier length is invalid");
        }
        // TODO: replace with constants
        if (!StringUtils.isBlank(bankingPriority) && bankingPriority.length() != 4) {
            return new ErrorModel("U01", "Banking priority length is invalid");
        }
        // TODO: replace with constants
        if (!StringUtils.isBlank(messageUserReference) && messageUserReference.length() != 16) {
            return new ErrorModel("U02", "Message User Reference length is invalid");
        }
        // TODO: replace with constants
        if (!StringUtils.isBlank(validationFlag) && !StringUtils.equals(validationFlag, "STP")
                && !StringUtils.equals(validationFlag, "REMIT")) {
            return new ErrorModel("U08", "Validation Flag is invalid");
        }
        // TODO: replace with constants
        if (StringUtils.isBlank(endToEndReference)) {
            return new ErrorModel("U13", "End to End Reference is required");
        }
        if (endToEndReference.length() != 36) {
            return new ErrorModel("U13", "End to End Reference length is invalid");
        }
        // TODO: replace with constants
        if (!StringUtils.isBlank(serviceTypeIdentifier)) {
            if (StringUtils.isBlank(endToEndReference)) {
                return new ErrorModel("U12",
                        "Service Type Identifier cannot be present without End to End Reference");
            }
            if (serviceTypeIdentifier.length() != 3) {
                return new ErrorModel("U14", "Service Type Identifier length is invalid");
            }
        }
        // TODO: Implement validation logic
        return new ErrorModel();
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setBankingPriority(String bankingPriority) {
        this.bankingPriority = bankingPriority;
    }

    public String getBankingPriority() {
        return bankingPriority;
    }

    public void setMessageUserReference(String messageUserReference) {
        this.messageUserReference = messageUserReference;
    }

    public String getMessageUserReference() {
        return messageUserReference;
    }

    public void setValidationFlag(String validationFlag) {
        this.validationFlag = validationFlag;
    }

    public String getValidationFlag() {
        return validationFlag;
    }

    public void setEndToEndReference(String endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

    public String getEndToEndReference() {
        return endToEndReference;
    }

    public void setServiceTypeIdentifier(String serviceTypeIdentifier) {
        this.serviceTypeIdentifier = serviceTypeIdentifier;
    }

    public String getServiceTypeIdentifier() {
        return serviceTypeIdentifier;
    }
}
