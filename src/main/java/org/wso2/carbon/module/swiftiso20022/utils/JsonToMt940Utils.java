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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.BalanceModel;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.StatementLineModel;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.TransactionModel;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationEngine;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;
import org.wso2.carbon.module.swiftiso20022.validation.rules.custom.MT940TransactionIndicatorValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.custom.MT940TransactionTypeValidationRule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to hold the JSON to MT940 Converting utility methods.
 */
public class JsonToMt940Utils {

    private static final Log log = LogFactory.getLog(JsonToMt940Utils.class);

    /**
     * Method to construct list of fields for Mandatory parameter validation.
     * @param requestPayload    Request Payload
     * @return List of fields for Mandatory parameter validation
     */
    public static List<ValidatorContext> getMandatoryFieldsInPayload(RequestPayloadModel requestPayload) {
        List<ValidatorContext> mandatoryFields = new ArrayList<>();
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.HEADER_BLOCK_1,
                requestPayload.getBlock1()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.HEADER_BLOCK_2,
                requestPayload.getBlock2()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.ACC_NUMBER,
                requestPayload.getAccountNumber()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_REF,
                requestPayload.getReference()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.SEQUENCE_NO,
                requestPayload.getSequenceNumber()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.OPENING_BALANCE,
                requestPayload.getOpeningBalanceDetails()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.CLOSING_BALANCE,
                requestPayload.getClosingBalanceDetails()));

        return mandatoryFields;
    }

    /**
     * Method to construct list of fields for length validation.
     * @param requestPayload      Request Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getFieldsInPayloadForLengthValidation(RequestPayloadModel requestPayload) {
        List<ValidatorContext> fields = new ArrayList<>();
        fields.add(new ValidatorContext(ConnectorConstants.ACC_NUMBER,
                requestPayload.getAccountNumber(), ConnectorConstants.ACC_IDENTIFICATION_LENGTH));
        fields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_REF, requestPayload.getReference(),
                ConnectorConstants.REFERENCE_LENGTH));
        fields.add(new ValidatorContext(ConnectorConstants.SEQUENCE_NO, requestPayload.getSequenceNumber(),
                ConnectorConstants.SEQUENCE_NO_LENGTH));
        return fields;
    }

    /**
     * Method to construct list of alphanumeric fields from the payload for validation.
     * @param requestPayload      Request Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getAlphaNumericFieldsInPayload(RequestPayloadModel requestPayload) {
        List<ValidatorContext> fields = new ArrayList<>();
        fields.add(new ValidatorContext(ConnectorConstants.ACC_NUMBER,
                requestPayload.getAccountNumber()));
        fields.add(new ValidatorContext(ConnectorConstants.ACC_NUMBER_IDENTIFICATION,
                requestPayload.getAccountNumberIdentifier()));
        return fields;
    }

    /**
     * Method to construct list of numeric fields from the payload for validation.
     * @param requestPayload      Request Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getNumericFieldsInPayload(RequestPayloadModel requestPayload) {
        List<ValidatorContext> fields = new ArrayList<>();
        fields.add(new ValidatorContext(ConnectorConstants.SEQUENCE_NO, requestPayload.getSequenceNumber()));
        return fields;
    }

    /**
     * Method to construct list of fields in Balances for Mandatory parameter validation.
     * @param balanceDetails     Balance object in Payload
     * @param balanceName        Name of the Balance object in Payload
     * @return List of fields for Mandatory parameter validation
     */
    public static List<ValidatorContext> getMandatoryFieldsInBalance(BalanceModel balanceDetails,
                                                                     String balanceName) {
        List<ValidatorContext> mandatoryFields = new ArrayList<>();
        mandatoryFields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.DATE),
                balanceDetails.getDate()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.CURRENCY),
                balanceDetails.getCurrency()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.AMOUNT),
                balanceDetails.getBalanceAmount()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.INDICATOR),
                balanceDetails.getIndicator()));

        return mandatoryFields;
    }

    /**
     * Method to construct list of fields in Balances for length validation.
     * @param balanceDetails     Balance object in Payload
     * @param balanceName        Name of the Balance object in Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getFieldsInBalanceForLengthValidation(BalanceModel balanceDetails,
                                                                               String balanceName) {
        List<ValidatorContext> fields = new ArrayList<>();
        fields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.DATE),
                balanceDetails.getDate(), ConnectorConstants.DATE_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.CURRENCY),
                balanceDetails.getCurrency(), ConnectorConstants.CURRENCY_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.AMOUNT),
                balanceDetails.getBalanceAmount(), ConnectorConstants.AMOUNT_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(balanceName, ConnectorConstants.INDICATOR),
                balanceDetails.getIndicator(), ConnectorConstants.INDICATOR_LENGTH));
        return fields;
    }

    /**
     * Method to construct list of fields in Transactions for Mandatory parameter validation.
     * @param transactionDetails     Transaction object in Payload
     * @return List of fields for Mandatory parameter validation
     */
    public static List<ValidatorContext> getMandatoryFieldsInTransaction(TransactionModel transactionDetails) {
        List<ValidatorContext> mandatoryFields = new ArrayList<>();
        String fieldName = ConnectorConstants.TRANSACTION;

        mandatoryFields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.DATE),
                transactionDetails.getDateTime()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.CURRENCY),
                transactionDetails.getCurrency()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.AMOUNT),
                transactionDetails.getAmount()));
        mandatoryFields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.INDICATOR),
                transactionDetails.getTransactionIndicator()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_REFERENCE,
                transactionDetails.getTransactionReference()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.CUSTOMER_REFERENCE,
                transactionDetails.getCustomerReference()));
        mandatoryFields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_TYPE,
                transactionDetails.getTransactionType()));

        return mandatoryFields;
    }

    /**
     * Method to construct list of fields in Transactions for length validation.
     * @param transactionDetails     Transaction object in Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getFieldsInTransactionForLengthValidation(
            TransactionModel transactionDetails) {

        List<ValidatorContext> fields = new ArrayList<>();
        String fieldName = ConnectorConstants.TRANSACTION;

        fields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.DATE),
                transactionDetails.getDateTime(), ConnectorConstants.DATE_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.CURRENCY),
                transactionDetails.getCurrency(), ConnectorConstants.CURRENCY_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.AMOUNT),
                transactionDetails.getAmount(), ConnectorConstants.AMOUNT_LENGTH));
        fields.add(new ValidatorContext(StringUtils.join(fieldName, ConnectorConstants.INDICATOR),
                transactionDetails.getTransactionIndicator(), ConnectorConstants.TRANSACTION_IND_LENGTH));
        fields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_REFERENCE,
                transactionDetails.getTransactionReference(), ConnectorConstants.REFERENCE_LENGTH));
        fields.add(new ValidatorContext(ConnectorConstants.CUSTOMER_REFERENCE,
                transactionDetails.getCustomerReference(), ConnectorConstants.REFERENCE_LENGTH));
        fields.add(new ValidatorContext(ConnectorConstants.TRANSACTION_TYPE,
                transactionDetails.getTransactionType(), ConnectorConstants.TRANSACTION_TYPE_LENGTH));
        return fields;
    }

    /**
     * Method to validate the Balance Details in the request payload.
     *
     * @param balanceDetails   Balance Details
     * @param fieldName        Balance Field Name
     * @return  Return ErrorModel if the balance is invalid.
     */
    public static ValidationResult validateBalanceDetails(BalanceModel balanceDetails, String fieldName) {

        ValidationResult paramValidationResult = ValidationEngine.getInstance()
                .addMandatoryParamValidationRules(
                        JsonToMt940Utils.getMandatoryFieldsInBalance(balanceDetails, fieldName))
                .addOptionalParamValidationRule(new ValidatorContext(StringUtils.join(fieldName,
                        ConnectorConstants.STATEMENT_TYPE), balanceDetails.getStatementType()))
                .addParameterLengthValidationRules(JsonToMt940Utils.
                        getFieldsInBalanceForLengthValidation(balanceDetails, fieldName))
                .addDateFormatValidationRule(new ValidatorContext(StringUtils.join(fieldName,
                        ConnectorConstants.DATE), balanceDetails.getDate()))
                .addCurrencyFormatValidationRule(new ValidatorContext(StringUtils.join(fieldName,
                        ConnectorConstants.CURRENCY), balanceDetails.getCurrency()))
                .validate();

        if (paramValidationResult.isError()) {
            return paramValidationResult;
        }

        if (!(ConnectorConstants.DEBIT.equals(balanceDetails.getIndicator()) ||
                ConnectorConstants.CREDIT.equals(balanceDetails.getIndicator()))) {
            return new ValidationResult(ConnectorConstants.ERROR_T51, ConnectorConstants.ERROR_BAL_IND_INVALID);
        }

        if (StringUtils.isNotBlank(balanceDetails.getStatementType())) {
            if (!(ConnectorConstants.CURRENT_STATEMENT_TYPE.equals(balanceDetails.getStatementType()) ||
                    ConnectorConstants.LAST_STATEMENT_TYPE.equals(balanceDetails.getStatementType()))) {
                return new ValidationResult(ConnectorConstants.ERROR_T51,
                        ConnectorConstants.ERROR_INVALID_STATEMENT_TYPE);
            }
        }

        return new ValidationResult();
    }

    /**
     * Method to validate the Balance object details.
     *
     * @param requestPayload  Request Payload
     * @return              Validation Result if there is an error, else empty Validation Result
     */
    public static ValidationResult validateBalances(RequestPayloadModel requestPayload) {
        ValidationResult errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getOpeningBalanceDetails(),
                ConnectorConstants.OPENING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getClosingBalanceDetails(),
                ConnectorConstants.CLOSING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }

        if (requestPayload.getClosingAvailableBalanceDetails() != null) {
            errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getClosingAvailableBalanceDetails(),
                    ConnectorConstants.CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }

        if (requestPayload.getForwardAvailableBalanceDetails() != null) {
            errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getForwardAvailableBalanceDetails(),
                    ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }

        return new ValidationResult();
    }

    /**
     * Method to validate the transaction details.
     *
     * @param transactions  List of transactions
     * @return              Validation Result if there is an error, else empty Validation Result
     */
    public static ValidationResult validateTransactionDetails(List<TransactionModel> transactions) {

        String fieldName = ConnectorConstants.TRANSACTION;

        for (TransactionModel transaction : transactions) {
            ValidationResult paramValidationResult = ValidationEngine.getInstance()
                    .addMandatoryParamValidationRules(
                            JsonToMt940Utils.getMandatoryFieldsInTransaction(transaction))
                    .addParameterLengthValidationRules(JsonToMt940Utils
                            .getFieldsInTransactionForLengthValidation(transaction))
                    .addDateFormatValidationRule(new ValidatorContext(StringUtils.join(fieldName,
                            ConnectorConstants.DATE), transaction.getDateTime()))
                    .addCurrencyFormatValidationRule(new ValidatorContext(StringUtils.join(fieldName,
                            ConnectorConstants.CURRENCY), transaction.getCurrency()))
                    .addCustomRule(new MT940TransactionTypeValidationRule(new ValidatorContext(
                            ConnectorConstants.TRANSACTION_TYPE, transaction.getTransactionType())))
                    .addCustomRule(new MT940TransactionIndicatorValidationRule(new ValidatorContext(
                            ConnectorConstants.TRANSACTION_IND, transaction.getTransactionIndicator())))
                    .validate();

            if (paramValidationResult.isError()) {
                return paramValidationResult;
            }
        }
        return new ValidationResult();
    }


    /**
     * Method to append constructed fields to the payload.
     *
     * @param payload          String payload
     * @param requestPayload   Request Payload Model
     * @return    Modified payload
     * @throws JSONException   If there is an error in handling JSON
     * @throws ConnectException  If there is an error in constructing values
     */
    public static JSONObject appendConstructedFields(String payload, RequestPayloadModel requestPayload)
            throws JSONException, ConnectException {
        JSONObject jsonPayload = new JSONObject(payload);
        jsonPayload.put(ConnectorConstants.STATEMENT_NO, constructStatementNumber(requestPayload));
        jsonPayload.put(ConnectorConstants.OPENING_BAL, constructOpeningBalance(requestPayload));
        jsonPayload.put(ConnectorConstants.CLOSING_BAL, constructClosingBalance(requestPayload));
        jsonPayload.put(ConnectorConstants.CLOSING_AVAIL_BAL, constructClosingAvailableBalance(requestPayload));
        jsonPayload.put(ConnectorConstants.FORWARD_AVAIL_BAL, constructForwardAvailableBalance(requestPayload));
        jsonPayload.put(ConnectorConstants.STATEMENT_LINES, constructStatementLines(requestPayload));
        return jsonPayload;
    }

    /**
     * Method to construct the opening balance.
     *
     * @param requestPayload  Request Payload
     * @return  Constructed Opening balance
     */
    private static String constructOpeningBalance(RequestPayloadModel requestPayload) {
        return requestPayload.getOpeningBalanceDetails().getIndicator() +
                requestPayload.getOpeningBalanceDetails().getDate() +
                requestPayload.getOpeningBalanceDetails().getCurrency() +
                formatAmountValue(requestPayload.getOpeningBalanceDetails().getBalanceAmount());
    }

    /**
     * Method to construct the closing balance.
     *
     * @param requestPayload  Request Payload
     * @return  Constructed closing balance
     */
    private static String constructClosingBalance(RequestPayloadModel requestPayload) {
        return requestPayload.getClosingBalanceDetails().getIndicator() +
                requestPayload.getClosingBalanceDetails().getDate() +
                requestPayload.getClosingBalanceDetails().getCurrency() +
                formatAmountValue(requestPayload.getClosingBalanceDetails().getBalanceAmount());
    }

    /**
     * Method to construct the available closing balance.
     *
     * @param requestPayload  Request Payload
     * @return  Constructed available closing balance
     */
    private static String constructClosingAvailableBalance(RequestPayloadModel requestPayload) {
        return requestPayload.getClosingAvailableBalanceDetails().getIndicator() +
                requestPayload.getClosingAvailableBalanceDetails().getDate() +
                requestPayload.getClosingAvailableBalanceDetails().getCurrency() +
                formatAmountValue(requestPayload.getClosingAvailableBalanceDetails().getBalanceAmount());
    }

    /**
     * Method to construct the forward closing balance.
     *
     * @param requestPayload  Request Payload
     * @return  Constructed available closing balance
     */
    private static String constructForwardAvailableBalance(RequestPayloadModel requestPayload) {
        return requestPayload.getForwardAvailableBalanceDetails().getIndicator() +
                requestPayload.getForwardAvailableBalanceDetails().getDate() +
                requestPayload.getForwardAvailableBalanceDetails().getCurrency() +
                formatAmountValue(requestPayload.getForwardAvailableBalanceDetails().getBalanceAmount());
    }

    /**
     * Method to construct the statement Lines.
     *
     * @param requestPayload   Request payload
     * @return Constructed statement lines
     */
    private static List<String> constructStatementLines(RequestPayloadModel requestPayload) {
        List<TransactionModel> transactions = requestPayload.getTransactions();
        List<String> statementLines = new ArrayList<>();

        StatementLineModel statementLineModel;
        for (TransactionModel transaction : transactions) {
            statementLineModel = new StatementLineModel();
            statementLineModel.setValueDate(transaction.getDateTime());
            statementLineModel.setEntryDate(transaction.getDateTime().substring(4));
            statementLineModel.setTransactionIndicator(transaction.getTransactionIndicator());
            statementLineModel.setFundCode(transaction.getCurrency().substring(2));
            statementLineModel.setAmount(formatAmountValue(transaction.getAmount()));
            statementLineModel.setTransactionType(transaction.getTransactionType());
            statementLineModel.setCustomerReference(transaction.getCustomerReference());
            statementLineModel.setBankReference(ConnectorConstants.DOUBLE_SLASH +
                    transaction.getTransactionReference());
            if (transaction.getSupplementaryData() != null) {
                statementLineModel.setSupplementaryData(ConnectorConstants.LINE_BREAK +
                        transaction.getSupplementaryData());
            }
            if (transaction.getInformation() != null) {
                statementLineModel.setInformation(ConnectorConstants.LINE_BREAK + ConnectorConstants.MT940_INFORMATION
                        + transaction.getInformation());
            }

            statementLines.add(statementLineModel.toString());
        }

        return statementLines;
    }

    /**
     * Method to construct the statement Number.
     *
     * @return Statement Number
     */
    private static String constructStatementNumber(RequestPayloadModel requestPayload) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long diffInDays = calculateDaysDiff(formatter.format(new Date()));
        return Long.toString(diffInDays).concat(ConnectorConstants.SLASH +
                requestPayload.getSequenceNumber());
    }

    /**
     * Method to format the amount value by removing commas and replacing dots with commas.
     *
     * @param amount  Amount Value
     * @return       Formatted Amount Value
     */
    private static String formatAmountValue(String amount) {
        String formattedAmount = amount.replace(",", "").replace(".", ",");
        if (formattedAmount.contains(",")) {
            return formattedAmount;
        } else {
            return formattedAmount + ",00";
        }
    }

    /**
     * Calculate the difference between the current date and a given date.
     *
     * @param endDate   End Date
     * @return Difference in days
     */
    private static long calculateDaysDiff(String endDate) {
        LocalDate end = LocalDate.parse(endDate);
        String startDate = Integer.toString(end.getYear()).concat("-01-01");
        LocalDate start = LocalDate.parse(startDate);
        return ChronoUnit.DAYS.between(start, end) + 1L;
    }
}
