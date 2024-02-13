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

import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.Entity;

import java.util.List;

/**
 * Class that models request payload block04.
 */
public class Block04 {
    static final String BLOCK_NAME = "block04";
    String sendersReference;
    List<String> timeIndication;
    String bankOperationCode;
    List<String> instructionCodes;
    String transactionTypeCode;
    String value;
    String instructedAmount;
    String exchangeRate;
    Entity orderingCustomer;
    Entity sendingInstitution;
    Entity orderingInstitution;
    Entity sendersCorrespondent;
    Entity receiversCorrespondent;
    Entity thirdReimbursementInstitution;
    Entity intermediaryInstitution;
    Entity accountWithInstitution;
    Entity beneficiaryCustomer;
    List<String> remittanceInformation;
    String detailsOfCharges;
    List<String> sendersCharges;
    String receiversCharges;
    List<String> senderToReceiverInformation;
    List<String> regulatoryReporting;
    String envelopeContents;

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }

    public String getSendersReference() {
        return sendersReference;
    }

    public void setTimeIndication(List<String> timeIndication) {
        this.timeIndication = timeIndication;
    }

    public List<String> getTimeIndication() {
        return timeIndication;
    }

    public void setBankOperationCode(String bankOperationCode) {
        this.bankOperationCode = bankOperationCode;
    }

    public String getBankOperationCode() {
        return bankOperationCode;
    }

    public void setInstructionCodes(List<String> instructionCodes) {
        this.instructionCodes = instructionCodes;
    }

    public List<String> getInstructionCodes() {
        return instructionCodes;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setInstructedAmount(String instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getInstructedAmount() {
        return instructedAmount;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setOrderingCustomer(Entity orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public Entity getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setSendingInstitution(Entity sendingInstitution) {
        this.sendingInstitution = sendingInstitution;
    }

    public Entity getSendingInstitution() {
        return sendingInstitution;
    }

    public void setOrderingInstitution(Entity orderingInstitution) {
        this.orderingInstitution = orderingInstitution;
    }

    public Entity getOrderingInstitution() {
        return orderingInstitution;
    }

    public void setSendersCorrespondent(Entity sendersCorrespondent) {
        this.sendersCorrespondent = sendersCorrespondent;
    }

    public Entity getSendersCorrespondent() {
        return sendersCorrespondent;
    }

    public void setReceiversCorrespondent(Entity receiversCorrespondent) {
        this.receiversCorrespondent = receiversCorrespondent;
    }

    public Entity getReceiversCorrespondent() {
        return receiversCorrespondent;
    }

    public void setThirdReimbursementInstitution(Entity thirdReimbursementInstitution) {
        this.thirdReimbursementInstitution = thirdReimbursementInstitution;
    }

    public Entity getThirdReimbursementInstitution() {
        return thirdReimbursementInstitution;
    }

    public void setIntermediaryInstitution(Entity intermediaryInstitution) {
        this.intermediaryInstitution = intermediaryInstitution;
    }

    public Entity getIntermediaryInstitution() {
        return intermediaryInstitution;
    }

    public void setAccountWithInstitution(Entity accountWithInstitution) {
        this.accountWithInstitution = accountWithInstitution;
    }

    public Entity getAccountWithInstitution() {
        return accountWithInstitution;
    }

    public void setRemittanceInformation(List<String> remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public void setBeneficiaryCustomer(Entity beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public Entity getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public List<String> getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setDetailsOfCharges(String detailsOfCharges) {
        this.detailsOfCharges = detailsOfCharges;
    }

    public String getDetailsOfCharges() {
        return detailsOfCharges;
    }

    public void setSendersCharges(List<String> sendersCharges) {
        this.sendersCharges = sendersCharges;
    }

    public List<String> getSendersCharges() {
        return sendersCharges;
    }

    public void setReceiversCharges(String receiversCharges) {
        this.receiversCharges = receiversCharges;
    }

    public String getReceiversCharges() {
        return receiversCharges;
    }

    public void setSenderToReceiverInformation(List<String> senderToReceiverInformation) {
        this.senderToReceiverInformation = senderToReceiverInformation;
    }

    public List<String> getSenderToReceiverInformation() {
        return senderToReceiverInformation;
    }

    public void setRegulatoryReporting(List<String> regulatoryReporting) {
        this.regulatoryReporting = regulatoryReporting;
    }

    public List<String> getRegulatoryReporting() {
        return regulatoryReporting;
    }

    public static String getBlockName() {
        return BLOCK_NAME;
    }

    public void setEnvelopeContents(String envelopeContents) {
        this.envelopeContents = envelopeContents;
    }

    public String getEnvelopeContents() {
        return envelopeContents;
    }
}
