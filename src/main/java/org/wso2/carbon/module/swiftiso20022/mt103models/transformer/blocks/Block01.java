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
 * Class that models request payload block01.
 */
public class Block01 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block01";
    String applicationIdentifier;
    String serviceIdentifier;
    String logicalTerminalAddress;
    String sessionNumber;
    String sequenceNumber;
    @Override
    public ErrorModel validate() {
        if (applicationIdentifier != null && !applicationIdentifier.equals(MT103Constants.MT103_APPLICATION_ID)) {
            return new ErrorModel(ConnectorConstants.ERROR_H02,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK01_APPLICATION_ID));
        }
        if (serviceIdentifier != null && !serviceIdentifier.equals(MT103Constants.MT103_SERVICE_ID)) {
            return new ErrorModel(ConnectorConstants.ERROR_H03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ConnectorConstants.BLOCK01_SERVICE_ID));
        }
        if (StringUtils.isBlank(logicalTerminalAddress)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_LOGICAL_TERMINAL_ADDRESS));
        }
        if (logicalTerminalAddress.length() != 12) {
            return new ErrorModel(ConnectorConstants.ERROR_H10,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_LOGICAL_TERMINAL_ADDRESS, 12));
        }
        if (StringUtils.isBlank(sessionNumber)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER));
        }
        if (sessionNumber.length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_H15,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER, 4));
        }
        if (StringUtils.isBlank(sequenceNumber)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_SEQUENCE_NUMBER));
        }
        if (sequenceNumber.length() != 6) {
            return new ErrorModel(ConnectorConstants.ERROR_H20,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER, 6));
        }
        return new ErrorModel();
    }

    public void setApplicationIdentifier(String applicationIdentifier) {
        this.applicationIdentifier = applicationIdentifier;
    }

    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setLogicalTerminalAddress(String logicalTerminalAddress) {
        this.logicalTerminalAddress = logicalTerminalAddress;
    }

    public String getLogicalTerminalAddress() {
        return logicalTerminalAddress;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public static String getBlockName() {
        return BLOCK_NAME;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }
}
