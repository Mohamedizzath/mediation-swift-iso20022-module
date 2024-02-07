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
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.BalanceModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.StatementLineModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.TransactionModel;

import java.text.DateFormat;
import java.text.ParseException;
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
     * Method to validate the request payload.
     * @param accountNumber   Account Number
     * @return  Whether the request payload is valid or not
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        return (StringUtils.isNotBlank(accountNumber) && accountNumber.length() < 36);
    }

    /**
     * Method to validate the reference in the request payload.
     * @param reference   Reference
     * @return  Whether the reference is valid or not
     */
    public static boolean isValidateReference(String reference) {
        return (StringUtils.isNotBlank(reference) && reference.length() < 17);
    }

    /**
     * Method to validate the Sequence Number in the request payload.
     * @param sequenceNumber   Sequence Number
     * @return  Whether the sequence number is valid or not
     */
    public static boolean isValidateSequenceNumber(String sequenceNumber) {
        return (StringUtils.isNotBlank(sequenceNumber) && sequenceNumber.length() < 4);
    }

    /**
     * Method to validate the Balance Details in the request payload.
     *
     * @param balanceDetails   Balance Details
     * @param fieldName        Balance Field Name
     * @return  Return ErrorModel if the balance is invalid.
     */
    public static ErrorModel validateBalanceDetails(BalanceModel balanceDetails, String fieldName) {

        if (!isValidDateFormat(balanceDetails.getDate())) {
            return new ErrorModel(ConnectorConstants.ERROR_T50,
                    String.format(ConnectorConstants.ERROR_INCORRECT_FORMAT, fieldName));
        }

        if (!ValidatorUtils.isValidCurrency(balanceDetails.getCurrency())) {
            return new ErrorModel(ConnectorConstants.ERROR_T52,
                    String.format(ConnectorConstants.ERROR_CURRENCY_CODE_INVALID, fieldName));
        }

        ErrorModel error = ValidatorUtils.validateAmountLength(balanceDetails.getBalanceAmount(),
                fieldName);
        if (error.isError()) {
            return error;
        }

        if (StringUtils.isBlank(balanceDetails.getIndicator()) ||
                !(ConnectorConstants.DEBIT.equals(balanceDetails.getIndicator()) ||
                ConnectorConstants.CREDIT.equals(balanceDetails.getIndicator()))) {
            return new ErrorModel(ConnectorConstants.ERROR_T51, ConnectorConstants.ERROR_BAL_IND_INVALID);
        }

        if (StringUtils.isNotBlank(balanceDetails.getStatementType())) {
            if (!(ConnectorConstants.CURRENT_STATEMENT_TYPE.equals(balanceDetails.getStatementType()) ||
                    ConnectorConstants.LAST_STATEMENT_TYPE.equals(balanceDetails.getStatementType()))) {
                return new ErrorModel(ConnectorConstants.ERROR_T51, ConnectorConstants.ERROR_INVALID_STATEMENT_TYPE);
            }
        }

        return new ErrorModel();
    }

    /**
     * Method to validate the date format.
     *
     * @param dateTime  Date time to validate
     * @return          True if the date format is valid, else false
     */
    public static boolean isValidDateFormat(String dateTime) {

        if (StringUtils.isBlank(dateTime)) {
            return false;
        }
        DateFormat formatter = new SimpleDateFormat(ConnectorConstants.DATE_TIME_FORMAT);

        try {
            formatter.parse(dateTime);
            return true;
        } catch (ParseException e) {
            log.error("Error while parsing the date time", e);
            return false;
        }
    }

    /**
     * Method to validate the transaction type.
     *
     * @param transactionType  Transaction type to validate
     * @return                 True if the transaction type is valid, else false
     */
    public static ErrorModel validTransactionType(String transactionType) {
        if (!transactionType.startsWith(ConnectorConstants.SWIFT_TRANSFER) &&
                !transactionType.startsWith(ConnectorConstants.NON_SWIFT_TRANSFER) &&
                !transactionType.startsWith(ConnectorConstants.FIRST_ADVICE)) {
            return new ErrorModel(ConnectorConstants.ERROR_T53,
                    ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
        }

        if (transactionType.startsWith(ConnectorConstants.SWIFT_TRANSFER)) {
            String identificationCode = transactionType.substring(1);
            if (!ValidatorUtils.isNumber(identificationCode)) {
                return new ErrorModel(ConnectorConstants.ERROR_T53,
                        ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
            }

            if (!(Integer.parseInt(identificationCode) > 99 && Integer.parseInt(identificationCode) < 1000)) {
                return new ErrorModel(ConnectorConstants.ERROR_T18,
                        ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
            }
        }
        return new ErrorModel();
    }

    /**
     * Method to validate the transaction type.
     *
     * @param debitCreditMark  Debit/Credit Mark to validate
     * @return                 True if the debitCreditMark is valid, else false
     */
    public static boolean isValidDebitCreditMark(String debitCreditMark) {
        return debitCreditMark.startsWith(ConnectorConstants.DEBIT) ||
                debitCreditMark.startsWith(ConnectorConstants.CREDIT) ||
                debitCreditMark.startsWith(ConnectorConstants.REV_DEBIT) ||
                debitCreditMark.startsWith(ConnectorConstants.REV_CREDIT);
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
