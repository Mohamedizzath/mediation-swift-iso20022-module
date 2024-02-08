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
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt103Utils;

import java.util.List;
import java.util.Objects;

/**
 * Class that models request payload block04.
 */
public class Block04 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block04";
    String sendersReference;
    List<String> timeIndication;
    String bankOperationCode;
    List<String> instructionCodes;
    String transactionTypeCode;
    String value;
    String instructedAmount;
    String exchangeRate;
    // TODO: include entity fields
    List<String> remittanceInformation;
    String detailsOfCharges;
    List<String> sendersCharges;
    String receiversCharges;
    List<String> senderToReceiverInformation;
    List<String> regulatoryReporting;
    String envelopeContents;
    @Override
    public ErrorModel validate() {
        if (StringUtils.isBlank(sendersReference)) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.SENDERS_REFERENCE));
        }
        if (sendersReference.length() > 16) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.SENDERS_REFERENCE, 16));
        }
        if (!Objects.isNull(timeIndication)) {
            ErrorModel timeIndicationValidationResponse =
                    JsonToMt103Utils.validateRepetitiveField(timeIndication,
                            MT103Constants.TIME_INDICATION, 19);
            if (timeIndicationValidationResponse.isError()) {
                return timeIndicationValidationResponse;
            }
        }
        if (StringUtils.isBlank(bankOperationCode)) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.BANK_OPERATION_CODE));
        }
        if (bankOperationCode.length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            MT103Constants.BANK_OPERATION_CODE, 4));
        }
        if (!Objects.isNull(instructionCodes)) {
            ErrorModel instructionCodeValidationResponse =
                    JsonToMt103Utils.validateRepetitiveField(instructionCodes,
                            MT103Constants.INSTRUCTION_CODE, 35);
            if (instructionCodeValidationResponse.isError()) {
                return instructionCodeValidationResponse;
            }
        }
        if (!StringUtils.isBlank(transactionTypeCode) && transactionTypeCode.length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            MT103Constants.TRANSACTION_TYPE_CODE, 3));
        }
        if (StringUtils.isBlank(value)) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.VALUE));
        }
        if (value.length() > 24) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.VALUE, 24));
        }
        if (!StringUtils.isBlank(instructedAmount) && instructedAmount.length() > 18) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.INSTRUCTED_AMOUNT, 18));
        }
        if (!StringUtils.isBlank(exchangeRate) && exchangeRate.length() > 12) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.EXCHANGE_RATE, 12));
        }

        // TODO: implement validation logic for entities

        if (!Objects.isNull(remittanceInformation)) {
            ErrorModel remittanceInformationValidationResponse =
                    JsonToMt103Utils.validateFieldLines(remittanceInformation,
                            MT103Constants.REMITTANCE_INFORMATION, 35, 4);
            if (remittanceInformationValidationResponse.isError()) {
                return remittanceInformationValidationResponse;
            }
        }
        if (StringUtils.isBlank(detailsOfCharges)) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.DETAILS_OF_CHARGES));
        }
        if (detailsOfCharges.length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            MT103Constants.BANK_OPERATION_CODE, 3));
        }
        if (!Objects.isNull(sendersCharges)) {
            ErrorModel sendersChargesValidationResponse =
                    JsonToMt103Utils.validateRepetitiveField(sendersCharges,
                            MT103Constants.SENDERS_CHARGES, 18);
        }
        if (!StringUtils.isBlank(receiversCharges) && receiversCharges.length() > 18) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.RECEIVERS_CHARGES, 18));
        }
        if (!Objects.isNull(senderToReceiverInformation)) {
            ErrorModel senderToReceiverInformationValidationResponse =
                    JsonToMt103Utils.validateFieldLines(senderToReceiverInformation,
                            MT103Constants.SENDER_TO_RECEIVER_INFORMATION, 35, 6);
            if (senderToReceiverInformationValidationResponse.isError()) {
                return senderToReceiverInformationValidationResponse;
            }
        }
        if (!Objects.isNull(regulatoryReporting)) {
            ErrorModel regulatoryReportingValidationResponse =
                    JsonToMt103Utils.validateFieldLines(regulatoryReporting,
                            MT103Constants.REGULATORY_REPORTING, 35, 3);
            if (regulatoryReportingValidationResponse.isError()) {
                return regulatoryReportingValidationResponse;
            }
        }
        if (!StringUtils.isBlank(envelopeContents) && envelopeContents.length() > 9000) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.ENVELOPE_CONTENTS, 9000));
        }
        return new ErrorModel();
    }

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

    // TODO: implement setters and getters for entity fields


    public void setRemittanceInformation(List<String> remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
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

