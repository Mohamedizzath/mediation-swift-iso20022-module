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

package org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text;

import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent text block of the MT103 message.
 */
public class MT103TextBlock {

    private Field20 sendersReference;
    private List<Field13> timeIndication;
    private Field23 bankOperationCode;
    private List<Field23> instructionCode;
    private Field26 transactionTypeCode;
    private Field32 value;
    private Field33 instructedAmount;
    private Field36 exchangeRate;
    private Field50 orderingCustomer;
    private Field51 sendingInstitution;
    private Field52 orderingInstitution;
    private Field53 sendersCorrespondent;
    private Field54 receiversCorrespondent;
    private Field55 thirdReimbursementInstitution;
    private Field56 intermediaryInstitution;
    private Field57 accountWithInstitution;
    private Field59 beneficiaryCustomer;
    private Field70 remittanceInformation;
    private Field71 detailsOfCharges;
    private List<Field71> sendersCharges;
    private Field71 receiversCharges;
    private Field72 senderToReceiverInformation;
    private Field77 regulatoryReporting;
    private Field77 envelopeContents;

    public Field20 getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(Field20 sendersReference) {
        this.sendersReference = sendersReference;
    }

    public List<Field13> getTimeIndication() {
        return timeIndication;
    }

    public void setTimeIndication(List<Field13> timeIndication) {
        this.timeIndication = timeIndication;
    }

    /**
     * Method to add new field13c object to timeIndication attribute.
     * If the attribute is not initialized, new array will be added.
     *
     * @param timeIndication Field13 object
     */
    public void setTimeIndication(Field13 timeIndication) {
        if (this.timeIndication == null) {
            setTimeIndication(new ArrayList<>(List.of(timeIndication)));
        } else {
            this.timeIndication.add(timeIndication);
        }
    }

    public Field23 getBankOperationCode() {
        return bankOperationCode;
    }

    public void setBankOperationCode(Field23 bankOperationCode) {
        this.bankOperationCode = bankOperationCode;
    }

    public List<Field23> getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(List<Field23> instructionCode) {
        this.instructionCode = instructionCode;
    }

    /**
     * Method to add new field23 object to instructionCode attribute.
     *
     * @param instructionCode Field23 object
     */
    public void setInstructionCode(Field23 instructionCode) {
        if (this.instructionCode == null) {
            setInstructionCode(new ArrayList<>(List.of(instructionCode)));
        } else {
            this.instructionCode.add(instructionCode);
        }
    }

    public Field26 getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(Field26 transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public Field32 getValue() {
        return value;
    }

    public void setValue(Field32 value) {
        this.value = value;
    }

    public Field33 getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Field33 instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public Field36 getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Field36 exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Field50 getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(Field50 orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public Field51 getSendingInstitution() {
        return sendingInstitution;
    }

    public void setSendingInstitution(Field51 sendingInstitution) {
        this.sendingInstitution = sendingInstitution;
    }

    public Field52 getOrderingInstitution() {
        return orderingInstitution;
    }

    public void setOrderingInstitution(Field52 orderingInstitution) {
        this.orderingInstitution = orderingInstitution;
    }

    public Field53 getSendersCorrespondent() {
        return sendersCorrespondent;
    }

    public void setSendersCorrespondent(Field53 sendersCorrespondent) {
        this.sendersCorrespondent = sendersCorrespondent;
    }

    public Field54 getReceiversCorrespondent() {
        return receiversCorrespondent;
    }

    public void setReceiversCorrespondent(Field54 receiversCorrespondent) {
        this.receiversCorrespondent = receiversCorrespondent;
    }

    public Field55 getThirdReimbursementInstitution() {
        return thirdReimbursementInstitution;
    }

    public void setThirdReimbursementInstitution(
            Field55 thirdReimbursementInstitution) {
        this.thirdReimbursementInstitution = thirdReimbursementInstitution;
    }

    public Field56 getIntermediaryInstitution() {
        return intermediaryInstitution;
    }

    public void setIntermediaryInstitution(Field56 intermediaryInstitution) {
        this.intermediaryInstitution = intermediaryInstitution;
    }

    public Field57 getAccountWithInstitution() {
        return accountWithInstitution;
    }

    public void setAccountWithInstitution(Field57 accountWithInstitution) {
        this.accountWithInstitution = accountWithInstitution;
    }

    public Field59 getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(Field59 beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public Field70 getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(Field70 remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public Field71 getDetailsOfCharges() {
        return detailsOfCharges;
    }

    public void setDetailsOfCharges(Field71 detailsOfCharges) {
        this.detailsOfCharges = detailsOfCharges;
    }

    public List<Field71> getSendersCharges() {
        return sendersCharges;
    }

    public void setSendersCharges(List<Field71> sendersCharges) {
        this.sendersCharges = sendersCharges;
    }

    /**
     * Method to add new field71 object with option F to sendersCharges attribute.
     *
     * @param sendersCharge Field71 object
     */
    public void setSendersCharges(Field71 sendersCharge) {
        if (this.sendersCharges == null) {
            setSendersCharges(new ArrayList<>(List.of(sendersCharge)));
        } else {
            this.sendersCharges.add(sendersCharge);
        }
    }

    public Field71 getReceiversCharges() {
        return receiversCharges;
    }

    public void setReceiversCharges(Field71 receiversCharges) {
        this.receiversCharges = receiversCharges;
    }

    public Field72 getSenderToReceiverInformation() {
        return senderToReceiverInformation;
    }

    public void setSenderToReceiverInformation(
            Field72 senderToReceiverInformation) {
        this.senderToReceiverInformation = senderToReceiverInformation;
    }

    public Field77 getRegulatoryReporting() {
        return regulatoryReporting;
    }

    public void setRegulatoryReporting(Field77 regulatoryReporting) {
        this.regulatoryReporting = regulatoryReporting;
    }

    public Field77 getEnvelopeContents() {
        return envelopeContents;
    }

    public void setEnvelopeContents(Field77 envelopeContents) {
        this.envelopeContents = envelopeContents;
    }
}
