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

package org.wso2.carbon.module.swiftiso20022.mt.models.blocks;

import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field434;

/**
 * Model for User Header Block (Block 03).
 */
public class UserHeaderBlock {

    private Field103 serviceIdentifier;
    private Field106 messageInputReference;
    private Field108 messageUserReference;
    private Field111 serviceTypeIdentifier;
    private Field113 bankingPriority;
    private Field115 addresseeInformation;
    private Field119 validationFlag;
    private Field121 endToEndReference;
    private Field165 paymentReleaseInformation;
    private Field423 balanceCheckpoint;
    private Field424 relatedReference;
    private Field433 sanctionsScreeningInformation;
    private Field434 paymentControlsInformation;

    public Field103 getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(Field103 serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public Field106 getMessageInputReference() {
        return messageInputReference;
    }

    public void setMessageInputReference(Field106 messageInputReference) {
        this.messageInputReference = messageInputReference;
    }

    public Field108 getMessageUserReference() {
        return messageUserReference;
    }

    public void setMessageUserReference(Field108 messageUserReference) {
        this.messageUserReference = messageUserReference;
    }

    public Field111 getServiceTypeIdentifier() {
        return serviceTypeIdentifier;
    }

    public void setServiceTypeIdentifier(Field111 serviceTypeIdentifier) {
        this.serviceTypeIdentifier = serviceTypeIdentifier;
    }

    public Field113 getBankingPriority() {
        return bankingPriority;
    }

    public void setBankingPriority(Field113 bankingPriority) {
        this.bankingPriority = bankingPriority;
    }

    public Field115 getAddresseeInformation() {
        return addresseeInformation;
    }

    public void setAddresseeInformation(Field115 addresseeInformation) {
        this.addresseeInformation = addresseeInformation;
    }

    public Field119 getValidationFlag() {
        return validationFlag;
    }

    public void setValidationFlag(Field119 validationFlag) {
        this.validationFlag = validationFlag;
    }

    public Field121 getEndToEndReference() {
        return endToEndReference;
    }

    public void setEndToEndReference(Field121 endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

    public Field165 getPaymentReleaseInformation() {
        return paymentReleaseInformation;
    }

    public void setPaymentReleaseInformation(Field165 paymentReleaseInformation) {
        this.paymentReleaseInformation = paymentReleaseInformation;
    }

    public Field423 getBalanceCheckpoint() {
        return balanceCheckpoint;
    }

    public void setBalanceCheckpoint(Field423 balanceCheckpoint) {
        this.balanceCheckpoint = balanceCheckpoint;
    }

    public Field424 getRelatedReference() {
        return relatedReference;
    }

    public void setRelatedReference(Field424 relatedReference) {
        this.relatedReference = relatedReference;
    }

    public Field433 getSanctionsScreeningInformation() {
        return sanctionsScreeningInformation;
    }

    public void setSanctionsScreeningInformation(Field433 sanctionsScreeningInformation) {
        this.sanctionsScreeningInformation = sanctionsScreeningInformation;
    }

    public Field434 getPaymentControlsInformation() {
        return paymentControlsInformation;
    }

    public void setPaymentControlsInformation(Field434 paymentControlsInformation) {
        this.paymentControlsInformation = paymentControlsInformation;
    }
}
