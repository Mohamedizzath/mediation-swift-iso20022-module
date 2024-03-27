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

package org.wso2.carbon.module.swiftiso20022.mtmodels.blocks;

/**
 * Model class for application header block
 */
public class ApplicationHeaderBlock {
    private String inputOutputIdentifier;
    private String messageType;
    private String deliveryMonitor;
    private String obsolescencePeriod;
    private String inputTime;
    private String messageInputReference;
    private String outputDate;
    private String outputTime;
    private String messagePriority;

    public String getInputOutputIdentifier() {
        return inputOutputIdentifier;
    }

    public void setInputOutputIdentifier(String inputOutputIdentifier) {
        this.inputOutputIdentifier = inputOutputIdentifier;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getDeliveryMonitor() {
        return deliveryMonitor;
    }

    public void setDeliveryMonitor(String deliveryMonitor) {
        this.deliveryMonitor = deliveryMonitor;
    }

    public String getObsolescencePeriod() {
        return obsolescencePeriod;
    }

    public void setObsolescencePeriod(String obsolescencePeriod) {
        this.obsolescencePeriod = obsolescencePeriod;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public String getMessageInputReference() {
        return messageInputReference;
    }

    public void setMessageInputReference(String messageInputReference) {
        this.messageInputReference = messageInputReference;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(String outputTime) {
        this.outputTime = outputTime;
    }

    public String getMessagePriority() {
        return messagePriority;
    }

    public void setMessagePriority(String messagePriority) {
        this.messagePriority = messagePriority;
    }
}
