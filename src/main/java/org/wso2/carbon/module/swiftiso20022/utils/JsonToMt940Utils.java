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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.StatementLineModel;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.TransactionModel;
import org.wso2.carbon.module.swiftiso20022.validation.JsonToMT940PayloadValidator;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationEngine;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;
import org.wso2.carbon.module.swiftiso20022.validation.rules.custom.MT940AmountFormatValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.custom.MT940IndicatorValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.custom.MT940StatementTypeValidationRule;
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

    /**
     * Method to validate the Balance Details in the request payload.
     *
     * @param balanceDetails   Balance Details
     * @param fieldName        Balance Field Name
     * @return  Return validationResult if the balance is invalid.
     */
    public static ValidationResult validateBalanceDetails(JSONObject balanceDetails, String fieldName) {

        return JsonToMT940PayloadValidator.getMT940BalanceValidationEngine(fieldName)
                .addCustomRule(new MT940IndicatorValidationRule(new ValidatorContext(MT940Constants.BAL_INDICATOR,
                        ConnectorUtils.concatFieldsWithSpaces(fieldName, MT940Constants.DN_INDICATOR))))
                .addCustomRule(new MT940StatementTypeValidationRule(new ValidatorContext(
                        MT940Constants.BAL_STATEMENT_TYPE, ConnectorUtils.concatFieldsWithSpaces(fieldName,
                        MT940Constants.DN_STATEMENT_TYPE))))
                .addCustomRule(new MT940AmountFormatValidationRule(new ValidatorContext(MT940Constants.BAL_AMOUNT,
                        ConnectorUtils.concatFieldsWithSpaces(fieldName, MT940Constants.DN_DATE))))
                .validate(balanceDetails);
    }

    /**
     * Method to validate the Balance object details.
     *
     * @param requestPayload  Request Payload
     * @return              Validation Result if there is an error, else empty Validation Result
     */
    public static ValidationResult validateBalances(JSONObject requestPayload) {
        ValidationResult validationResult = JsonToMt940Utils.validateBalanceDetails(requestPayload
                        .getJSONObject(MT940Constants.OPENING_BAL_DETAILS),
                MT940Constants.DN_OPENING_BALANCE);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        validationResult = JsonToMt940Utils.validateBalanceDetails(requestPayload
                        .getJSONObject(MT940Constants.CLOSING_BALANCE_DETAILS),
                MT940Constants.DN_CLOSING_BALANCE);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (requestPayload.has(MT940Constants.CLOSING_AVAIL_BALANCE_DETAILS)) {
            validationResult = JsonToMt940Utils.validateBalanceDetails(requestPayload
                            .getJSONObject(MT940Constants.CLOSING_BALANCE_DETAILS),
                    MT940Constants.DN_CLOSING_AVAIL_BALANCE);
            if (!validationResult.isValid()) {
                return validationResult;
            }
        }

        if (requestPayload.has(MT940Constants.FORWARD_CLOSING_AVAIL_BALANCE_DETAILS)) {
            validationResult = JsonToMt940Utils.validateBalanceDetails(requestPayload.getJSONObject(
                            MT940Constants.FORWARD_CLOSING_AVAIL_BALANCE_DETAILS),
                    MT940Constants.DN_FORWARD_CLOSING_AVAIL_BALANCE);
            if (!validationResult.isValid()) {
                return validationResult;
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
    public static ValidationResult validateTransactionDetails(JSONArray transactions) {

        ValidationEngine engine =  JsonToMT940PayloadValidator.getMT940TransactionValidationEngine()
                .addCustomRule(new MT940TransactionTypeValidationRule(new ValidatorContext(
                                MT940Constants.TRANSACTION_TYPE, MT940Constants.DN_TRANSACTION_TYPE)))
                .addCustomRule(new MT940IndicatorValidationRule(new ValidatorContext(
                                MT940Constants.TRANSACTION_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(
                                        MT940Constants.DN_TRANSACTION, MT940Constants.DN_INDICATOR))));

        for (int i = 0; i < transactions.length(); i++) {
            ValidationResult paramValidationResult = engine.validate(transactions.getJSONObject(i));

            if (!paramValidationResult.isValid()) {
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
        jsonPayload.put(MT940Constants.RESPONSE_STATEMENT_NO, constructStatementNumber(requestPayload));
        jsonPayload.put(MT940Constants.RESPONSE_OPENING_BAL, constructOpeningBalance(requestPayload));
        jsonPayload.put(MT940Constants.RESPONSE_CLOSING_BAL, constructClosingBalance(requestPayload));
        jsonPayload.put(MT940Constants.RESPONSE_CLOSING_AVAIL_BAL,
                constructClosingAvailableBalance(requestPayload));
        jsonPayload.put(MT940Constants.RESPONSE_FORWARD_AVAIL_BAL,
                constructForwardAvailableBalance(requestPayload));
        jsonPayload.put(MT940Constants.RESPONSE_STATEMENT_LINES, constructStatementLines(requestPayload));
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
                statementLineModel.setInformation(ConnectorConstants.LINE_BREAK + MT940Constants.MT940_INFORMATION
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
