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
        // TODO: Implement validation logic
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
