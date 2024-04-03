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

package org.wso2.carbon.module.swiftiso20022.models.mt103models.blocks;

import org.wso2.carbon.module.swiftiso20022.models.mt103models.Entity;

import java.util.List;

/**
 * Class that models request payload block04.
 */
public class Block04 {

    private String sendersReference;
    private List<String> timeIndication;
    private String bankOperationCode;
    private List<String> instructionCodes;
    private String transactionTypeCode;
    private String value;
    private String instructedAmount;
    private String exchangeRate;
    private Entity orderingCustomer;
    private Entity sendingInstitution;
    private Entity orderingInstitution;
    private Entity sendersCorrespondent;
    private Entity receiversCorrespondent;
    private Entity thirdReimbursementInstitution;
    private Entity intermediaryInstitution;
    private Entity accountWithInstitution;
    private Entity beneficiaryCustomer;
    private List<String> remittanceInformation;
    private String detailsOfCharges;
    private List<String> sendersCharges;
    private String receiversCharges;
    private List<String> senderToReceiverInformation;
    private List<String> regulatoryReporting;
    private String envelopeContents;

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }

    public List<String> getTimeIndication() {
        return timeIndication;
    }

    public void setTimeIndication(List<String> timeIndication) {
        this.timeIndication = timeIndication;
    }

    public String getBankOperationCode() {
        return bankOperationCode;
    }

    public void setBankOperationCode(String bankOperationCode) {
        this.bankOperationCode = bankOperationCode;
    }

    public List<String> getInstructionCodes() {
        return instructionCodes;
    }

    public void setInstructionCodes(List<String> instructionCodes) {
        this.instructionCodes = instructionCodes;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(String instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Entity getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(Entity orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public Entity getSendingInstitution() {
        return sendingInstitution;
    }

    public void setSendingInstitution(Entity sendingInstitution) {
        this.sendingInstitution = sendingInstitution;
    }

    public Entity getOrderingInstitution() {
        return orderingInstitution;
    }

    public void setOrderingInstitution(Entity orderingInstitution) {
        this.orderingInstitution = orderingInstitution;
    }

    public Entity getSendersCorrespondent() {
        return sendersCorrespondent;
    }

    public void setSendersCorrespondent(Entity sendersCorrespondent) {
        this.sendersCorrespondent = sendersCorrespondent;
    }

    public Entity getReceiversCorrespondent() {
        return receiversCorrespondent;
    }

    public void setReceiversCorrespondent(Entity receiversCorrespondent) {
        this.receiversCorrespondent = receiversCorrespondent;
    }

    public Entity getThirdReimbursementInstitution() {
        return thirdReimbursementInstitution;
    }

    public void setThirdReimbursementInstitution(Entity thirdReimbursementInstitution) {
        this.thirdReimbursementInstitution = thirdReimbursementInstitution;
    }

    public Entity getIntermediaryInstitution() {
        return intermediaryInstitution;
    }

    public void setIntermediaryInstitution(Entity intermediaryInstitution) {
        this.intermediaryInstitution = intermediaryInstitution;
    }

    public Entity getAccountWithInstitution() {
        return accountWithInstitution;
    }

    public void setAccountWithInstitution(Entity accountWithInstitution) {
        this.accountWithInstitution = accountWithInstitution;
    }

    public Entity getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(Entity beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public List<String> getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(List<String> remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public String getDetailsOfCharges() {
        return detailsOfCharges;
    }

    public void setDetailsOfCharges(String detailsOfCharges) {
        this.detailsOfCharges = detailsOfCharges;
    }

    public List<String> getSendersCharges() {
        return sendersCharges;
    }

    public void setSendersCharges(List<String> sendersCharges) {
        this.sendersCharges = sendersCharges;
    }

    public String getReceiversCharges() {
        return receiversCharges;
    }

    public void setReceiversCharges(String receiversCharges) {
        this.receiversCharges = receiversCharges;
    }

    public List<String> getSenderToReceiverInformation() {
        return senderToReceiverInformation;
    }

    public void setSenderToReceiverInformation(List<String> senderToReceiverInformation) {
        this.senderToReceiverInformation = senderToReceiverInformation;
    }

    public List<String> getRegulatoryReporting() {
        return regulatoryReporting;
    }

    public void setRegulatoryReporting(List<String> regulatoryReporting) {
        this.regulatoryReporting = regulatoryReporting;
    }

    public String getEnvelopeContents() {
        return envelopeContents;
    }

    public void setEnvelopeContents(String envelopeContents) {
        this.envelopeContents = envelopeContents;
    }
}
