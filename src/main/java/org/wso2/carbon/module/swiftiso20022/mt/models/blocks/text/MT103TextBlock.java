package org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text;

import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23E;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26T;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50K;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71G;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77T;

public class MT103TextBlock {

    private Field20 sendersReference;
    private List<Field13C> timeIndication;
    private Field23B bankOperationCode;
    private List<Field23E> instructionCode;
    private Field26T transactionTypeCode;
    private Field32A value;
    private Field33B instructedAmount;
    private Field36 exchangeRate;
    private Field50A orderingCustomerOptA;
    private Field50F orderingCustomerOptF;
    private Field50K orderingCustomerOptK;
    private Field51A sendingInstitution;
    private Field52A orderingInstitutionOptA;
    private Field52D orderingInstitutionOptD;
    private Field53A sendersCorrespondentOptA;
    private Field53B sendersCorrespondentOptB;
    private Field53D sendersCorrespondentOptD;
    private Field54A receiversCorrespondentOptA;
    private Field54B receiversCorrespondentOptB;
    private Field54D receiversCorrespondentOptD;
    private Field55A thirdReimbursementInstitutionOptA;
    private Field55B thirdReimbursementInstitutionOptB;
    private Field55D thirdReimbursementInstitutionOptD;
    private Field56A intermediaryInstitutionOptA;
    private Field56C intermediaryInstitutionOptC;
    private Field56D intermediaryInstitutionOptD;
    private Field57A accountWithInstitutionOptA;
    private Field57B accountWithInstitutionOptB;
    private Field57C accountWithInstitutionOptC;
    private Field57D accountWithInstitutionOptD;
    private Field59 beneficiaryCustomerOptNoLetter;
    private Field59A beneficiaryCustomerOptA;
    private Field59F beneficiaryCustomerOptF;
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

    public Field50A getOrderingCustomerOptA() {
        return orderingCustomerOptA;
    }

    public void setOrderingCustomerOptA(Field50A orderingCustomerOptA) {
        this.orderingCustomerOptA = orderingCustomerOptA;
    }

    public Field50F getOrderingCustomerOptF() {
        return orderingCustomerOptF;
    }

    public void setOrderingCustomerOptF(Field50F orderingCustomerOptF) {
        this.orderingCustomerOptF = orderingCustomerOptF;
    }

    public Field50K getOrderingCustomerOptK() {
        return orderingCustomerOptK;
    }

    public void setOrderingCustomerOptK(Field50K orderingCustomerOptK) {
        this.orderingCustomerOptK = orderingCustomerOptK;
    }

    public Field51A getSendingInstitution() {
        return sendingInstitution;
    }

    public void setSendingInstitution(Field51A sendingInstitution) {
        this.sendingInstitution = sendingInstitution;
    }

    public Field52A getOrderingInstitutionOptA() {
        return orderingInstitutionOptA;
    }

    public void setOrderingInstitutionOptA(
            Field52A orderingInstitutionOptA) {
        this.orderingInstitutionOptA = orderingInstitutionOptA;
    }

    public Field52D getOrderingInstitutionOptD() {
        return orderingInstitutionOptD;
    }

    public void setOrderingInstitutionOptD(
            Field52D orderingInstitutionOptD) {
        this.orderingInstitutionOptD = orderingInstitutionOptD;
    }

    public Field53A getSendersCorrespondentOptA() {
        return sendersCorrespondentOptA;
    }

    public void setSendersCorrespondentOptA(
            Field53A sendersCorrespondentOptA) {
        this.sendersCorrespondentOptA = sendersCorrespondentOptA;
    }

    public Field53B getSendersCorrespondentOptB() {
        return sendersCorrespondentOptB;
    }

    public void setSendersCorrespondentOptB(
            Field53B sendersCorrespondentOptB) {
        this.sendersCorrespondentOptB = sendersCorrespondentOptB;
    }

    public Field53D getSendersCorrespondentOptD() {
        return sendersCorrespondentOptD;
    }

    public void setSendersCorrespondentOptD(
            Field53D sendersCorrespondentOptD) {
        this.sendersCorrespondentOptD = sendersCorrespondentOptD;
    }

    public Field54A getReceiversCorrespondentOptA() {
        return receiversCorrespondentOptA;
    }

    public void setReceiversCorrespondentOptA(
            Field54A receiversCorrespondentOptA) {
        this.receiversCorrespondentOptA = receiversCorrespondentOptA;
    }

    public Field54B getReceiversCorrespondentOptB() {
        return receiversCorrespondentOptB;
    }

    public void setReceiversCorrespondentOptB(
            Field54B receiversCorrespondentOptB) {
        this.receiversCorrespondentOptB = receiversCorrespondentOptB;
    }

    public Field54D getReceiversCorrespondentOptD() {
        return receiversCorrespondentOptD;
    }

    public void setReceiversCorrespondentOptD(
            Field54D receiversCorrespondentOptD) {
        this.receiversCorrespondentOptD = receiversCorrespondentOptD;
    }

    public Field55A getThirdReimbursementInstitutionOptA() {
        return thirdReimbursementInstitutionOptA;
    }

    public void setThirdReimbursementInstitutionOptA(
            Field55A thirdReimbursementInstitutionOptA) {
        this.thirdReimbursementInstitutionOptA = thirdReimbursementInstitutionOptA;
    }

    public Field55B getThirdReimbursementInstitutionOptB() {
        return thirdReimbursementInstitutionOptB;
    }

    public void setThirdReimbursementInstitutionOptB(
            Field55B thirdReimbursementInstitutionOptB) {
        this.thirdReimbursementInstitutionOptB = thirdReimbursementInstitutionOptB;
    }

    public Field55D getThirdReimbursementInstitutionOptD() {
        return thirdReimbursementInstitutionOptD;
    }

    public void setThirdReimbursementInstitutionOptD(
            Field55D thirdReimbursementInstitutionOptD) {
        this.thirdReimbursementInstitutionOptD = thirdReimbursementInstitutionOptD;
    }

    public Field56A getIntermediaryInstitutionOptA() {
        return intermediaryInstitutionOptA;
    }

    public void setIntermediaryInstitutionOptA(
            Field56A intermediaryInstitutionOptA) {
        this.intermediaryInstitutionOptA = intermediaryInstitutionOptA;
    }

    public Field56C getIntermediaryInstitutionOptC() {
        return intermediaryInstitutionOptC;
    }

    public void setIntermediaryInstitutionOptC(
            Field56C intermediaryInstitutionOptC) {
        this.intermediaryInstitutionOptC = intermediaryInstitutionOptC;
    }

    public Field56D getIntermediaryInstitutionOptD() {
        return intermediaryInstitutionOptD;
    }

    public void setIntermediaryInstitutionOptD(
            Field56D intermediaryInstitutionOptD) {
        this.intermediaryInstitutionOptD = intermediaryInstitutionOptD;
    }

    public Field57A getAccountWithInstitutionOptA() {
        return accountWithInstitutionOptA;
    }

    public void setAccountWithInstitutionOptA(
            Field57A accountWithInstitutionOptA) {
        this.accountWithInstitutionOptA = accountWithInstitutionOptA;
    }

    public Field57B getAccountWithInstitutionOptB() {
        return accountWithInstitutionOptB;
    }

    public void setAccountWithInstitutionOptB(
            Field57B accountWithInstitutionOptB) {
        this.accountWithInstitutionOptB = accountWithInstitutionOptB;
    }

    public Field57C getAccountWithInstitutionOptC() {
        return accountWithInstitutionOptC;
    }

    public void setAccountWithInstitutionOptC(
            Field57C accountWithInstitutionOptC) {
        this.accountWithInstitutionOptC = accountWithInstitutionOptC;
    }

    public Field57D getAccountWithInstitutionOptD() {
        return accountWithInstitutionOptD;
    }

    public void setAccountWithInstitutionOptD(
            Field57D accountWithInstitutionOptD) {
        this.accountWithInstitutionOptD = accountWithInstitutionOptD;
    }

    public Field59 getBeneficiaryCustomerOptNoLetter() {
        return beneficiaryCustomerOptNoLetter;
    }

    public void setBeneficiaryCustomerOptNoLetter(
            Field59 beneficiaryCustomerOptNoLetter) {
        this.beneficiaryCustomerOptNoLetter = beneficiaryCustomerOptNoLetter;
    }

    public Field59A getBeneficiaryCustomerOptA() {
        return beneficiaryCustomerOptA;
    }

    public void setBeneficiaryCustomerOptA(
            Field59A beneficiaryCustomerOptA) {
        this.beneficiaryCustomerOptA = beneficiaryCustomerOptA;
    }

    public Field59F getBeneficiaryCustomerOptF() {
        return beneficiaryCustomerOptF;
    }

    public void setBeneficiaryCustomerOptF(
            Field59F beneficiaryCustomerOptF) {
        this.beneficiaryCustomerOptF = beneficiaryCustomerOptF;
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
            setSendersCharges(List.of(sendersCharge));
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
