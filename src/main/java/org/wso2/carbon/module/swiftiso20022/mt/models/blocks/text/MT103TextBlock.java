package org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text;

import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23E;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26T;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71G;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77T;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent text block of the MT103 message.
 */
public class MT103TextBlock {

    private Field20 sendersReference;
    private List<Field13C> timeIndication;
    private Field23B bankOperationCode;
    private List<Field23E> instructionCode;
    private Field26T transactionTypeCode;
    private Field32A value;
    private Field33B instructedAmount;
    private Field36 exchangeRate;
    private Field50 orderingCustomer;
    private Field51A sendingInstitution;
    private Field52 orderingInstitution;
    private Field53 sendersCorrespondent;
    private Field54 receiversCorrespondent;
    private Field55 thirdReimbursementInstitution;
    private Field56 intermediaryInstitution;
    private Field57 accountWithInstitution;
    private Field59 beneficiaryCustomer;
    private Field70 remittanceInformation;
    private Field71A detailsOfCharge;
    private List<Field71F> sendersCharges;
    private Field71G receiversCharges;
    private Field72 senderToReceiverInformation;
    private Field77B regulatoryReporting;
    private Field77T envelopeContents;

    public Field20 getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(Field20 sendersReference) {
        this.sendersReference = sendersReference;
    }

    public List<Field13C> getTimeIndication() {
        return timeIndication;
    }

    public void setTimeIndication(List<Field13C> timeIndication) {
        this.timeIndication = timeIndication;
    }

    /**
     * Method to add new field13c object to timeIndication attribute.
     *
     * @param timeIndication Field13C object
     */
    public void addTimeIndication(Field13C timeIndication) {
        if (this.timeIndication == null) {
            setTimeIndication(new ArrayList<>(List.of(timeIndication)));
        } else {
            this.timeIndication.add(timeIndication);
        }
    }

    public Field23B getBankOperationCode() {
        return bankOperationCode;
    }

    public void setBankOperationCode(Field23B bankOperationCode) {
        this.bankOperationCode = bankOperationCode;
    }

    public List<Field23E> getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(List<Field23E> instructionCode) {
        this.instructionCode = instructionCode;
    }

    /**
     * Method to add new field23E object to instructionCode attribute.
     *
     * @param instructionCode Field23E object
     */
    public void addInstructionCode(Field23E instructionCode) {
        if (this.instructionCode == null) {
            setInstructionCode(new ArrayList<>(List.of(instructionCode)));
        } else {
            this.instructionCode.add(instructionCode);
        }
    }

    public Field26T getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(Field26T transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public Field32A getValue() {
        return value;
    }

    public void setValue(Field32A value) {
        this.value = value;
    }

    public Field33B getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Field33B instructedAmount) {
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

    public Field51A getSendingInstitution() {
        return sendingInstitution;
    }

    public void setSendingInstitution(Field51A sendingInstitution) {
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

    public Field71A getDetailsOfCharge() {
        return detailsOfCharge;
    }

    public void setDetailsOfCharge(Field71A detailsOfCharge) {
        this.detailsOfCharge = detailsOfCharge;
    }

    public List<Field71F> getSendersCharges() {
        return sendersCharges;
    }

    public void setSendersCharges(List<Field71F> sendersCharges) {
        this.sendersCharges = sendersCharges;
    }

    /**
     * Method to add new field71F object to sendersCharges attribute.
     *
     * @param sendersCharge Field71F object
     */
    public void addSendersCharges(Field71F sendersCharge) {
        if (this.sendersCharges == null) {
            setSendersCharges(new ArrayList<>(List.of(sendersCharge)));
        } else {
            this.sendersCharges.add(sendersCharge);
        }
    }

    public Field71G getReceiversCharges() {
        return receiversCharges;
    }

    public void setReceiversCharges(Field71G receiversCharges) {
        this.receiversCharges = receiversCharges;
    }

    public Field72 getSenderToReceiverInformation() {
        return senderToReceiverInformation;
    }

    public void setSenderToReceiverInformation(
            Field72 senderToReceiverInformation) {
        this.senderToReceiverInformation = senderToReceiverInformation;
    }

    public Field77B getRegulatoryReporting() {
        return regulatoryReporting;
    }

    public void setRegulatoryReporting(Field77B regulatoryReporting) {
        this.regulatoryReporting = regulatoryReporting;
    }

    public Field77T getEnvelopeContents() {
        return envelopeContents;
    }

    public void setEnvelopeContents(Field77T envelopeContents) {
        this.envelopeContents = envelopeContents;
    }
}
