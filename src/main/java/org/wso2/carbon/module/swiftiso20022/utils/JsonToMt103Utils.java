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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.Entity;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block01;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block02;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block03;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block04;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block05;


import java.util.List;

/**
 * Class with Json to Mt103 utility methods.
 */
public class JsonToMt103Utils {

    public static ErrorModel validateBlock01(Block01 block01) {
        if (block01.getApplicationIdentifier() != null
                && !block01.getApplicationIdentifier().equals(MT103Constants.MT103_APPLICATION_ID)) {
            return new ErrorModel(ConnectorConstants.ERROR_H02,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK01_APPLICATION_ID));
        }
        if (block01.getServiceIdentifier() != null
                && !block01.getServiceIdentifier().equals(MT103Constants.MT103_SERVICE_ID)) {
            return new ErrorModel(ConnectorConstants.ERROR_H03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ConnectorConstants.BLOCK01_SERVICE_ID));
        }
        if (StringUtils.isBlank(block01.getLogicalTerminalAddress())) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_LOGICAL_TERMINAL_ADDRESS));
        }
        if (!StringUtils.isAlphanumeric(block01.getLogicalTerminalAddress())) {
            return new ErrorModel(ConnectorConstants.ERROR_H10,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK01_LOGICAL_TERMINAL_ADDRESS));
        }
        if (block01.getLogicalTerminalAddress().length() != 12) {
            return new ErrorModel(ConnectorConstants.ERROR_H10,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_LOGICAL_TERMINAL_ADDRESS, 12));
        }
        if (StringUtils.isBlank(block01.getSessionNumber())) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER));
        }
        if (!StringUtils.isNumeric(block01.getSessionNumber())) {
            return new ErrorModel(ConnectorConstants.ERROR_H15,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER));
        }
        if (block01.getSessionNumber().length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_H15,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER, 4));
        }
        if (StringUtils.isBlank(block01.getSequenceNumber())) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK01_SEQUENCE_NUMBER));
        }
        if (!StringUtils.isNumeric(block01.getSequenceNumber())) {
            return new ErrorModel(ConnectorConstants.ERROR_H20,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK01_SEQUENCE_NUMBER));
        }
        if (block01.getSequenceNumber().length() != 6) {
            return new ErrorModel(ConnectorConstants.ERROR_H20,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK01_SESSION_NUMBER, 6));
        }
        return new ErrorModel();
    }

    public static ErrorModel validateBlock02(Block02 block02) {
        if (block02.getMessageType() != null
                && !block02.getMessageType().equals(MT103Constants.MT103_MESSAGE_TYPE)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_MESSAGE_TYPE));
        }
        if (block02.getPriority() != null &&
                (block02.getPriority().isBlank()
                        || !StringUtils.isAlpha(block02.getPriority())
                        || block02.getPriority().length() != 1)) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ConnectorConstants.BLOCK02_PRIORITY));
        }
        if (block02.getInputOutputIdentifier() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
        }
        switch (block02.getInputOutputIdentifier()) {
            case "I":
                return validateInputMessageBlock02(block02);
            case "O":
                return validateOutputMessageBlock02(block02);
            default:
                return new ErrorModel(ConnectorConstants.ERROR_H98,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
        }
    }

    private static ErrorModel validateInputMessageBlock02(Block02 block02) {
        if (StringUtils.isBlank(block02.getDestinationLogicalTerminalAddress())) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS));
        }
        if (!StringUtils.isAlphanumeric(block02.getDestinationLogicalTerminalAddress())) {
            return new ErrorModel(ConnectorConstants.ERROR_H50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS));
        }
        if (block02.getDestinationLogicalTerminalAddress().length() != 12) {
            return new ErrorModel(ConnectorConstants.ERROR_H50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS, 12));
        }
        if (block02.getDeliveryMonitoringCode() != null) {
            if (!StringUtils.isNumeric(block02.getDeliveryMonitoringCode())) {
                return new ErrorModel(ConnectorConstants.ERROR_H80,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK02_DELIVERY_MONITORING_CODE));
            }
            if (block02.getDeliveryMonitoringCode().length() != 1) {
                return new ErrorModel(ConnectorConstants.ERROR_H80,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK02_DELIVERY_MONITORING_CODE, 1));
            }
        }
        if (block02.getObsolescencePeriodCode() != null) {
            if (!StringUtils.isNumeric(block02.getObsolescencePeriodCode())) {
                return new ErrorModel(ConnectorConstants.ERROR_H81,
                        ConnectorConstants.BLOCK02_OBSOLESCENCE_PERIOD_CODE);
            }
            if (block02.getObsolescencePeriodCode().length() != 3) {
                return new ErrorModel(ConnectorConstants.ERROR_H81,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK02_OBSOLESCENCE_PERIOD_CODE, 3));
            }
        }
        return new ErrorModel();
    }

    private static ErrorModel validateOutputMessageBlock02(Block02 block02) {
        if (StringUtils.isBlank(block02.getInputTime())) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_INPUT_TIME));
        }
        if (!StringUtils.isNumeric(block02.getInputTime())) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_INPUT_TIME));
        }
        if (block02.getInputTime().length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_INPUT_TIME, 4));
        }
        if (StringUtils.isBlank(block02.getMessageInputReference())) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_MESSAGE_INPUT_REFERENCE));
        }
        if (!StringUtils.isAlphanumeric(block02.getMessageInputReference())) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_MESSAGE_INPUT_REFERENCE));
        }
        if (block02.getMessageInputReference().length() != 28) {
            return new ErrorModel(ConnectorConstants.ERROR_H98,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_MESSAGE_INPUT_REFERENCE, 28));
        }
        if (StringUtils.isBlank(block02.getOutputDate())) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_OUTPUT_DATE));
        }
        if (!StringUtils.isNumeric(block02.getOutputDate())) {
            return new ErrorModel(ConnectorConstants.ERROR_T50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_OUTPUT_DATE));
        }
        if (block02.getOutputDate().length() != 6) {
            return new ErrorModel(ConnectorConstants.ERROR_T50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_OUTPUT_DATE, 6));
        }
        if (StringUtils.isBlank(block02.getOutputTime())) {
            return new ErrorModel(ConnectorConstants.ERROR_H25,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK02_OUTPUT_TIME));
        }
        if (!StringUtils.isNumeric(block02.getOutputTime())) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK02_OUTPUT_TIME));
        }
        if (block02.getOutputTime().length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T38,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK02_OUTPUT_TIME, 4));
        }
        return new ErrorModel();
    }

    public static ErrorModel validateBlock03(Block03 block03) {
        if (StringUtils.isBlank(block03.getServiceIdentifier())) {
            return new ErrorModel(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER));
        }
        if (!StringUtils.isAlpha(block03.getServiceIdentifier())) {
            return new ErrorModel(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER));
        }
        if (block03.getServiceIdentifier().length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER, 3));
        }
        if (block03.getBankingPriority() != null) {
            if (block03.getBankingPriority().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_U01,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY,
                                ConnectorConstants.BLOCK03_BANKING_PRIORITY));
            }
            if (block03.getBankingPriority().length() != 4) {
                return new ErrorModel(ConnectorConstants.ERROR_U01,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK03_BANKING_PRIORITY, 4));
            }
        }
        if (block03.getMessageUserReference() != null) {
            if (block03.getMessageUserReference().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_U02,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY,
                                ConnectorConstants.BLOCK03_MESSAGE_USER_REFERENCE));
            }
            if (block03.getMessageUserReference().length() != 16) {
                return new ErrorModel(ConnectorConstants.ERROR_U02,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK03_MESSAGE_USER_REFERENCE, 16));
            }
        }
        if (block03.getValidationFlag() != null
                && !block03.getValidationFlag().equals(MT103Constants.MT103_STP_VALIDATION_FLAG)
                && !block03.getValidationFlag().equals(MT103Constants.MT103_REMIT_VALIDATION_FLAG)) {
            return new ErrorModel(ConnectorConstants.ERROR_U08,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.BLOCK03_VALIDATION_FLAG));
        }
        if (StringUtils.isBlank(block03.getEndToEndReference())) {
            return new ErrorModel(ConnectorConstants.ERROR_U13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK03_END_TO_END_REFERENCE));
        }
        if (block03.getEndToEndReference().length() != 36) {
            return new ErrorModel(ConnectorConstants.ERROR_U13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK03_END_TO_END_REFERENCE, 36));
        }
        if (block03.getServiceTypeIdentifier() != null) {
            if (!StringUtils.isNumeric(block03.getServiceTypeIdentifier())) {
                return new ErrorModel(ConnectorConstants.ERROR_U14,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY,
                                ConnectorConstants.BLOCK03_SERVICE_TYPE_IDENTIFIER));
            }
            if (block03.getServiceTypeIdentifier().length() != 3) {
                return new ErrorModel(ConnectorConstants.ERROR_U14,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK03_SERVICE_TYPE_IDENTIFIER, 3));
            }
        }
        return new ErrorModel();
    }

    public static ErrorModel validateBlock04(Block04 block04) {
        ErrorModel fieldValidationResponse;
        if (StringUtils.isBlank(block04.getSendersReference())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.SENDERS_REFERENCE));
        }
        if (block04.getSendersReference().length() > 16) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.SENDERS_REFERENCE, 16));
        }
        if (block04.getTimeIndication() != null) {
            fieldValidationResponse =
                    validateRepetitiveField(block04.getTimeIndication(), MT103Constants.TIME_INDICATION, 19);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (StringUtils.isBlank(block04.getBankOperationCode())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.BANK_OPERATION_CODE));
        }
        if (!StringUtils.isAlphanumeric(block04.getBankOperationCode())) {
            return new ErrorModel(ConnectorConstants.ERROR_T36,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.BANK_OPERATION_CODE));
        }
        if (block04.getBankOperationCode().length() != 4) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            MT103Constants.BANK_OPERATION_CODE, 4));
        }
        if (block04.getInstructionCodes() != null) {
            fieldValidationResponse
                    = validateRepetitiveField(block04.getInstructionCodes(), MT103Constants.INSTRUCTION_CODE, 35);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getTransactionTypeCode() != null) {
            if (!StringUtils.isAlphanumeric(block04.getTransactionTypeCode())) {
                return new ErrorModel(ConnectorConstants.ERROR_T90,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                MT103Constants.TRANSACTION_TYPE_CODE));
            }
            if (block04.getTransactionTypeCode().length() != 3) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                MT103Constants.TRANSACTION_TYPE_CODE, 3));
            }
        }
        if (StringUtils.isBlank(block04.getValue())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.VALUE));
        }
        if (block04.getValue().length() > 24) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.VALUE, 24));
        }
        if (block04.getInstructedAmount() != null) {
            if (block04.getInstructedAmount().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_T90,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.INSTRUCTED_AMOUNT));
            }
            if (block04.getInstructedAmount().length() > 18) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.INSTRUCTED_AMOUNT, 18));
            }
        }
        if (block04.getExchangeRate() != null) {
            if (block04.getExchangeRate().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_T90,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.EXCHANGE_RATE));
            }
            if (block04.getExchangeRate().length() > 12) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.EXCHANGE_RATE, 12));
            }
        }
        if (block04.getOrderingCustomer() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.ORDERING_CUSTOMER));
        }
        fieldValidationResponse = validateEntityField(block04.getOrderingCustomer(), MT103Constants.ORDERING_CUSTOMER);
        if (fieldValidationResponse.isError()) {
            return fieldValidationResponse;
        }
        if (block04.getSendingInstitution() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getSendingInstitution(), MT103Constants.SENDING_INSTITUTION);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getOrderingInstitution() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getOrderingInstitution(), MT103Constants.ORDERING_INSTITUTION);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getSendersCorrespondent() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getSendersCorrespondent(), MT103Constants.SENDERS_CORRESPONDENT);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getReceiversCorrespondent() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getReceiversCorrespondent(), MT103Constants.RECEIVERS_CORRESPONDENT);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getThirdReimbursementInstitution() != null) {
            fieldValidationResponse =
                    validateEntityField(
                            block04.getThirdReimbursementInstitution(), MT103Constants.THIRD_REIMBURSEMENT_INSTITUTION);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getIntermediaryInstitution() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getIntermediaryInstitution(), MT103Constants.INTERMEDIARY_INSTITUTION);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getAccountWithInstitution() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getAccountWithInstitution(), MT103Constants.ACCOUNT_WITH_INSTITUTION);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getBeneficiaryCustomer() != null) {
            fieldValidationResponse =
                    validateEntityField(block04.getBeneficiaryCustomer(), MT103Constants.BENEFICIARY_CUSTOMER);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getRemittanceInformation() != null) {
            fieldValidationResponse = validateFieldLines(
                    block04.getRemittanceInformation(), MT103Constants.REMITTANCE_INFORMATION, 35, 4);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (StringUtils.isBlank(block04.getDetailsOfCharges())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, MT103Constants.DETAILS_OF_CHARGES));
        }
        if (!StringUtils.isAlpha(block04.getDetailsOfCharges())) {
            return new ErrorModel(ConnectorConstants.ERROR_T08,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.DETAILS_OF_CHARGES));
        }
        if (block04.getDetailsOfCharges().length() != 3) {
            return new ErrorModel(ConnectorConstants.ERROR_T33,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.RECEIVERS_CHARGES, 18));
        }
        if (block04.getSendersCharges() != null) {
            fieldValidationResponse
                    = validateRepetitiveField(block04.getSendersCharges(), MT103Constants.SENDERS_CHARGES, 18);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getReceiversCharges() != null) {
            if (block04.getReceiversCharges().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_T90,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.RECEIVERS_CHARGES));
            }
            if (block04.getReceiversCharges().length() > 18) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, MT103Constants.RECEIVERS_CHARGES, 18));
            }
        }
        if (block04.getSenderToReceiverInformation() != null) {
            fieldValidationResponse = validateFieldLines(block04.getSenderToReceiverInformation(),
                    MT103Constants.SENDER_TO_RECEIVER_INFORMATION, 35, 6);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getRegulatoryReporting() != null) {
            fieldValidationResponse = validateFieldLines(
                    block04.getRegulatoryReporting(), MT103Constants.REGULATORY_REPORTING, 35, 3);
            if (fieldValidationResponse.isError()) {
                return fieldValidationResponse;
            }
        }
        if (block04.getEnvelopeContents() != null) {
            if (block04.getEnvelopeContents().isBlank()) {
                return new ErrorModel(ConnectorConstants.ERROR_T90,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, MT103Constants.ENVELOPE_CONTENTS));
            }
            if (block04.getEnvelopeContents().length() > 9000) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH,
                                MT103Constants.ENVELOPE_CONTENTS, 9000));
            }
        }
        return new ErrorModel();
    }

    public static ErrorModel validateBlock05(Block05 block05) {
        if (StringUtils.isBlank(block05.getChecksum())) {
            return new ErrorModel(ConnectorConstants.ERROR_Z04,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, ConnectorConstants.BLOCK05_CHECKSUM));
        }
        if (block05.getChecksum().length() != 12) {
            return new ErrorModel(ConnectorConstants.ERROR_Z04,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                            ConnectorConstants.BLOCK05_CHECKSUM, 12));
        }
        if (block05.getPossibleDuplicateEmission() != null) {
            if (!StringUtils.isAlphanumeric(block05.getPossibleDuplicateEmission())) {
                return new ErrorModel(ConnectorConstants.ERROR_Z05,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_EMISSION));
            }
            if (block05.getPossibleDuplicateEmission().length() != 32) {
                return new ErrorModel(ConnectorConstants.ERROR_Z05,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_EMISSION, 32));
            }
        }
        if (block05.getMessageReference() != null) {
            if (!StringUtils.isAlphanumeric(block05.getMessageReference())) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK05_MESSAGE_REFERENCE));
            }
            if (block05.getMessageReference().length() != 38) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK05_MESSAGE_REFERENCE, 38));
            }
        }
        if (block05.getPossibleDuplicateMessage() != null) {
            if (!StringUtils.isAlphanumeric(block05.getPossibleDuplicateMessage())) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_MESSAGE));
            }
            if (block05.getPossibleDuplicateMessage().length() != 32) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_MESSAGE, 32));
            }
        }
        if (block05.getSystemOriginatedMessage() != null) {
            if (!StringUtils.isAlphanumeric(block05.getSystemOriginatedMessage())) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK05_SYSTEM_ORIGINATED_MESSAGE));
            }
            if (block05.getSystemOriginatedMessage().length() != 32) {
                return new ErrorModel(ConnectorConstants.ERROR_Z00,
                        String.format(ConnectorConstants.ERROR_PARAMETER_CONSTANT_LENGTH,
                                ConnectorConstants.BLOCK05_SYSTEM_ORIGINATED_MESSAGE, 32));
            }
        }
        return new ErrorModel();
    }

    /**
     * Method to validate repetitive field.
     *
     * @param valueArray Array of strings of repetitions
     * @param fieldName Name of the repetitive field
     * @param maxElementLength Maximum length of the field
     * @return Empty Error model or one with the error message
     */
    public static ErrorModel validateRepetitiveField(
            List<String> valueArray, String fieldName, int maxElementLength) {
        if (valueArray.isEmpty()) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, fieldName));
        }
        for (int i = 0; i < valueArray.size(); i++) {
            String repetition = valueArray.get(i);
            if (StringUtils.isBlank(repetition)) {
                return new ErrorModel(ConnectorConstants.ERROR_T17,
                        String.format(MT103Constants.ERROR_REPETITION_EMPTY, ++i, fieldName));
            }
            if (repetition.length() > maxElementLength) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(MT103Constants.ERROR_REPETITION_LENGTH_EXCEED, ++i, fieldName, maxElementLength));
            }
        }
        return new ErrorModel();
    }

    /**
     * Method to validate fields with multiple lines.
     *
     * @param fieldLines Array of Strings of lines
     * @param fieldName Name of the field
     * @param maxLineLength Maximum length of the line
     * @param maxLines Maximum lines
     * @return Empty Error model or one with the error message
     */
    public static ErrorModel validateFieldLines(
            List<String> fieldLines, String fieldName, int maxLineLength, int maxLines) {
        if (fieldLines.isEmpty()) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, fieldName));
        }
        if (fieldLines.size() > maxLines) {
            return new ErrorModel(ConnectorConstants.ERROR_T30,
                    String.format(ConnectorConstants.ERROR_FIELD_LINES_EXCEED, fieldName, maxLines));
        }
        for (int i = 0; i < fieldLines.size(); i++) {
            String fieldLine = fieldLines.get(i);
            if (StringUtils.isBlank(fieldLine)) {
                return new ErrorModel(ConnectorConstants.ERROR_T17,
                        String.format(ConnectorConstants.ERROR_FIELD_LINE_EMPTY, ++i, fieldName));
            }
            if (fieldLine.length() > maxLineLength) {
                return new ErrorModel(ConnectorConstants.ERROR_T33, String.format(
                        ConnectorConstants.ERROR_FIELD_LINE_LENGTH_EXCEED, ++i, fieldName, maxLineLength));
            }
        }
        return new ErrorModel();
    }

    /**
     * Method to validate an entity.
     *
     * @param entity Instance of the entity
     * @param fieldName Identification of the entity
     * @return Empty Error model or one with the error message
     */
    public static ErrorModel validateEntityField(Entity entity, String fieldName) {
        if (entity.getOption() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_NO_CODE,
                    String.format(MT103Constants.ERROR_EMPTY_ENTITY_OPTION, fieldName));
        }
        if (entity.getOption().length() > 1) {
            return new ErrorModel(ConnectorConstants.ERROR_NO_CODE,
                    String.format(MT103Constants.ERROR_INVALID_ENTITY_OPTION, fieldName));
        }
        if (entity.getDetails() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(MT103Constants.ERROR_EMPTY_ENTITY_DETAILS, fieldName));
        }
        return validateFieldLines(entity.getDetails(), fieldName, 35, 5);
    }
}
