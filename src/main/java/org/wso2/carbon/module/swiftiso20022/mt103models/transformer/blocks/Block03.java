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
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
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
        if (StringUtils.isBlank(serviceIdentifier)) {
            return new ErrorModel(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER));
        }
        if (serviceIdentifier.length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER, 3));
        }
        if (!StringUtils.isBlank(bankingPriority) && bankingPriority.length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_U01,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_BANKING_PRIORITY, 4));
        }
        if (!StringUtils.isBlank(messageUserReference) && messageUserReference.length() != 16) {
            return new ErrorModel(ConnectorConstants.ERROR_U02,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_MESSAGE_USER_REFERENCE, 16));
        }
        if (!StringUtils.isBlank(validationFlag)
                && !validationFlag.equals(MT103Constants.MT103_STP_VALIDATION_FLAG)
                && !validationFlag.equals(MT103Constants.MT103_REMIT_VALIDATION_FLAG)) {
            return new ErrorModel(ConnectorConstants.ERROR_U08,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK03_VALIDATION_FLAG));
        }
        if (StringUtils.isBlank(endToEndReference)) {
            return new ErrorModel(ConnectorConstants.ERROR_U13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK03_END_TO_END_REFERENCE));
        }
        if (endToEndReference.length() != 36) {
            return new ErrorModel(ConnectorConstants.ERROR_U13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_END_TO_END_REFERENCE, 36));
        }
        if (!StringUtils.isBlank(serviceTypeIdentifier) && serviceTypeIdentifier.length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_U14,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_SERVICE_TYPE_IDENTIFIER, 3));
        }
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

    public static String getBlockName() {
        return BLOCK_NAME;
    }
}
