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
 * Class that models request payload block02.
 */
public class Block02 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block02";
    String inputOutputIdentifier;
    String messageType;
    String destinationLogicalTerminalAddress;
    String inputTime;
    String messageInputReference;
    String outputDate;
    String outputTime;
    String priority;
    String deliveryMonitoringCode;
    String obsolescencePeriodCode;

    @Override
    public ErrorModel validate() {
        if (!StringUtils.isBlank(messageType) && !messageType.equals(MT103Constants.MT103_MESSAGE_TYPE)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_MESSAGE_TYPE));
        }
        if (!StringUtils.isBlank(priority) && priority.length() != 1) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ConnectorConstants.BLOCK02_PRIORITY));
        }
        if (StringUtils.isBlank(inputOutputIdentifier)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
        } else {
            switch (inputOutputIdentifier) {
                case "I":
                    return validateInputMessagePayload();
                case "O":
                    return validateOutputMessagePayload();
                default:
                    return new ErrorModel(ConnectorConstants.ERROR_H98,
                            String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                    ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
            }
        }
    }

    private ErrorModel validateInputMessagePayload() {
        if (StringUtils.isBlank(destinationLogicalTerminalAddress)) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS));
        }
        if (destinationLogicalTerminalAddress.length() != 12) {
            return new ErrorModel(ConnectorConstants.ERROR_H50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS, 12));
        }
        if (!StringUtils.isBlank(deliveryMonitoringCode) && deliveryMonitoringCode.length() != 1) {
            return new ErrorModel(ConnectorConstants.ERROR_H80,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_DELIVERY_MONITORING_CODE, 1));
        }
        if (!StringUtils.isBlank(obsolescencePeriodCode) && obsolescencePeriodCode.length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_H81,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_OBSOLESCENCE_PERIOD_CODE, 3));
        }
        return new ErrorModel();
    }

    private ErrorModel validateOutputMessagePayload() {
        if (StringUtils.isBlank(inputTime)) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_INPUT_TIME));
        }
        if (inputTime.length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_INPUT_TIME, 4));
        }
        if (StringUtils.isBlank(messageInputReference)) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_MESSAGE_INPUT_REFERENCE));
        }
        if (messageInputReference.length() != 28) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_MESSAGE_INPUT_REFERENCE, 28));
        }
        if (StringUtils.isBlank(outputDate)) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_OUTPUT_DATE));
        }
        if (outputDate.length() != 6) {
            return new ErrorModel(ConnectorConstants.ERROR_T50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_OUTPUT_DATE, 6));
        }
        if (StringUtils.isBlank(outputTime)) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_OUTPUT_TIME));
        }
        if (outputTime.length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_OUTPUT_TIME, 4));
        }
        return new ErrorModel();
    }

    public void setInputOutputIdentifier(String inputOutputIdentifier) {
        this.inputOutputIdentifier = inputOutputIdentifier;
    }

    public String getInputOutputIdentifier() {
        return inputOutputIdentifier;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setDestinationLogicalTerminalAddress(String destinationLogicalTerminalAddress) {
        this.destinationLogicalTerminalAddress = destinationLogicalTerminalAddress;
    }

    public String getDestinationLogicalTerminalAddress() {
        return destinationLogicalTerminalAddress;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setMessageInputReference(String messageInputReference) {
        this.messageInputReference = messageInputReference;
    }

    public String getMessageInputReference() {
        return messageInputReference;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputTime(String outputTime) {
        this.outputTime = outputTime;
    }

    public String getOutputTime() {
        return outputTime;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setDeliveryMonitoringCode(String deliveryMonitoringCode) {
        this.deliveryMonitoringCode = deliveryMonitoringCode;
    }

    public String getDeliveryMonitoringCode() {
        return deliveryMonitoringCode;
    }

    public void setObsolescencePeriodCode(String obsolescencePeriodCode) {
        this.obsolescencePeriodCode = obsolescencePeriodCode;
    }

    public String getObsolescencePeriodCode() {
        return obsolescencePeriodCode;
    }

    public static String getBlockName() {
        return BLOCK_NAME;
    }
}
