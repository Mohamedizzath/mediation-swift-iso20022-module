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
        if (!StringUtils.isBlank(messageType) && !messageType.equals("103")) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Message Type is invalid");
        }
        // TODO: add validator to check message priority
        if (!StringUtils.isBlank(priority) && priority.length() != 1) {
            return new ErrorModel("H98", "Priority length is invalid");
        }
        if (StringUtils.isBlank(inputOutputIdentifier)) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Input Output Identifier is required");
        } else {
            switch (inputOutputIdentifier) {
                case "I":
                    return validateInputMessagePayload();
                case "O":
                    return validateOutputMessagePayload();
                default:
                    return new ErrorModel("H98", "Input Output Identifier is invalid");
            }
        }
    }

    private ErrorModel validateInputMessagePayload() {
        if (StringUtils.isBlank(destinationLogicalTerminalAddress)) {
            // TODO: replace with constants
            return new ErrorModel("H25",
                    "Destination Logical Address is required for Input Messages");
        }
        if (destinationLogicalTerminalAddress.length() != 12) {
            return new ErrorModel("H50", "Destination Logical Address is invalid");
        }
        if (!StringUtils.isBlank(deliveryMonitoringCode) && deliveryMonitoringCode.length() != 1) {
            return new ErrorModel("H80", "Delivery Monitory Code length is invalid");
        }
        if (!StringUtils.isBlank(obsolescencePeriodCode) && obsolescencePeriodCode.length() != 3) {
            return new ErrorModel("H81", "Obsolescence Period Code length is invalid");
        }
        return new ErrorModel();
    }

    private ErrorModel validateOutputMessagePayload() {
        // TODO: replace with constants
        if (StringUtils.isBlank(inputTime)) {
            return new ErrorModel("H25", "Input Time is required for Output message");
        }
        if (inputTime.length() != 4) {
            // TODO: replace with constants
            return new ErrorModel("T38", "Input time length is invalid");
        }
        if (StringUtils.isBlank(messageInputReference)) {
            return new ErrorModel("H25",
                    "Message Input Reference is required for Output message");
        }
        if (messageInputReference.length() != 28) {
            return new ErrorModel("H98", "Message Input Reference length is invalid");
        }
        if (StringUtils.isBlank(outputDate)) {
            return new ErrorModel("H25", "Output Date is required for Output message");
        }
        if (outputDate.length() != 6) {
            return new ErrorModel("T50", "Output Date length is invalid");
        }
        if (StringUtils.isBlank(outputTime)) {
            return new ErrorModel("H25", "Output Time is required for Output message");
        }
        if (outputTime.length() != 4) {
            return new ErrorModel("T38", "Output Time length is invalid");
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
}
