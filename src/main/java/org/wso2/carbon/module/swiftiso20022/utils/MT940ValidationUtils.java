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
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validation utils for MT940.
 */
public class MT940ValidationUtils {

    /**
     * Method to validate the C1 rule MT940 format.
     * C1 rule - :86: should be preceded by :61:
     *
     * @param lines  Lines in MT940 format
     * @return    Result of C1 rule validation
     */
    public static boolean isValidC1Rule(String[] lines) {

        for (int itr = 0; itr < lines.length; itr++) {
            if (lines[itr].startsWith(ConnectorConstants.MT940_INFORMATION)) {
                return lines[itr - 1].startsWith(ConnectorConstants.MT940_STATEMENT_LINE);
            }
        }
        return true;
    }

    /**
     * Method to validate the C2 rule MT940 format.
     * C2 rule - :60:, :62:, :64: and :65: should have same currency code
     *
     * @param lines  Lines in MT940 format
     * @return    Result of C2 rule validation
     */
    public static boolean isValidC2Rule(String[] lines) {
        String openBalanceCurrency = null;
        String closeBalanceCurrency = null;
        String closeAvailBalanceCurrency = null;
        String forwardAvailBalanceCurrency = null;

        for (String line : lines) {
            String mandatoryCurrencyField = line.length() > 18 ? line.substring(14, 17) : null;
            String optionalCurrencyField = line.length() > 17 ? line.substring(13, 16) : null;

            if (line.startsWith(ConnectorConstants.MT940_OPENING_BAL)) {
                openBalanceCurrency = mandatoryCurrencyField;
            } else if (line.startsWith(ConnectorConstants.MT940_CLOSING_BAL)) {
                closeBalanceCurrency = mandatoryCurrencyField;
            } else if (line.startsWith(ConnectorConstants.MT940_CLOSING_AVAIL_BAL)) {
                closeAvailBalanceCurrency = optionalCurrencyField;
            } else if (line.startsWith(ConnectorConstants.MT940_FORWARD_AVAIL_BAL)) {
                forwardAvailBalanceCurrency = optionalCurrencyField;
            }
        }

        if (openBalanceCurrency == null || !openBalanceCurrency.equals(closeBalanceCurrency)) {
            return false;
        } else if (closeAvailBalanceCurrency != null &&
                !openBalanceCurrency.equals(closeAvailBalanceCurrency)) {
            return false;
        }

        return forwardAvailBalanceCurrency == null ||
                openBalanceCurrency.equals(forwardAvailBalanceCurrency);
    }

    /**
     * Method to validate the MT940 format
     *
     * @param fields  Fields in MT940 format
     * @return    ErrorModel MT940 format is invalid
     */
    public static ErrorModel validateMT940Format(Map<String, Object> fields) {
        ErrorModel errorModel;

        errorModel = validateReference((String) fields.get(ConnectorConstants.TRANSACTION_REF),
                ConnectorConstants.TRANSACTION_REF);
        if (errorModel.isError()) {
            return errorModel;
        }

        if (fields.containsKey(ConnectorConstants.RELATED_REF)) {
            errorModel = validateReference((String) fields.get(ConnectorConstants.RELATED_REF),
                    ConnectorConstants.RELATED_REF);
            if (errorModel.isError()) {
                return errorModel;
            }
        }

        errorModel = validateAccountIdentifier((String) fields.get(ConnectorConstants.ACC_IDENTIFICATION));
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateStatementNumber((String) fields.get(ConnectorConstants.STATEMENT_NUMBER));
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateOpeningBalance((String) fields.get(ConnectorConstants.OPENING_BALANCE));
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateClosingBalance((String) fields.get(ConnectorConstants.CLOSING_BALANCE));
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateClosingAvailableBalance((String) fields
                .get(ConnectorConstants.CLOSING_AVAIL_BALANCE));
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateForwardAvailableBalance((String) fields
                .get(ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE));
        if (errorModel.isError()) {
            return errorModel;
        }
        return new ErrorModel();
    }

    /**
     * Method to validate whether reference is valid.
     *
     * @param referenceValue  Reference to be validated
     * @return    Whether reference is valid
     */
    public static ErrorModel validateReference(String referenceValue, String fieldName) {
        String[] referenceDetails = referenceValue.split(ConnectorConstants.COLON);
        if (!isListLengthValid(referenceDetails)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, fieldName));
        }

        String reference = referenceDetails[2];
        if (StringUtils.isBlank(reference)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING, fieldName));
        }

        if (reference.length() > 16) {
            return new ErrorModel(ConnectorConstants.ERROR_M50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH, fieldName, 16));
        }

        if (reference.startsWith(ConnectorConstants.SLASH) || reference.endsWith(ConnectorConstants.SLASH) ||
                reference.contains(ConnectorConstants.DOUBLE_SLASH)) {
            return new ErrorModel(ConnectorConstants.ERROR_T26,
                    String.format(ConnectorConstants.ERROR_PARAMETER_CONTAINS_SLASH, fieldName));
        }

        if (!Pattern.matches(ConnectorConstants.MT_REGEX_PATTERN, reference)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, fieldName));
        }
        return new ErrorModel();
    }

    /**
     * Method to validate whether Account identifier is valid.
     *
     * @param accIdentifier  Account identifier to be validated
     * @return    Whether account identifier is valid
     */
    public static ErrorModel validateAccountIdentifier(String accIdentifier) {
        String[] accIdentifierDetails = accIdentifier.split(ConnectorConstants.COLON);
        if (!isListLengthValid(accIdentifierDetails)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.ACC_IDENTIFICATION));
        }
        String account = accIdentifierDetails[2];
        if (StringUtils.isBlank(account)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.ACC_IDENTIFICATION));
        }

        if (account.length() > 35) {
            return new ErrorModel(ConnectorConstants.ERROR_M50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH,
                            ConnectorConstants.ACC_IDENTIFICATION, 35));
        }

        if (!Pattern.matches(ConnectorConstants.MT_REGEX_PATTERN, account)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.ACC_IDENTIFICATION));
        }
        return new ErrorModel();
    }

    /**
     * Method to validate whether Statement number is valid.
     *
     * @param statementNumber  Statement number to be validated
     * @return    Whether statement number is valid
     */
    public static ErrorModel validateStatementNumber(String statementNumber) {
        String[] statementNumberDetails = statementNumber.split(ConnectorConstants.COLON);
        if (!isListLengthValid(statementNumberDetails)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.STATEMENT_NUMBER));
        }
        String number = statementNumberDetails[2];
        if (StringUtils.isBlank(number)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.STATEMENT_NUMBER));
        }

        if (number.length() > 5) {
            return new ErrorModel(ConnectorConstants.ERROR_M50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH,
                            ConnectorConstants.STATEMENT_NUMBER, 16));
        }

        if (!number.contains(ConnectorConstants.SLASH)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.STATEMENT_NUMBER));
        }

        if (!ValidatorUtils.isNumber(number.replace(ConnectorConstants.SLASH, ""))) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.STATEMENT_NUMBER));
        }
        return new ErrorModel();
    }

    /**
     * Method to validate whether Opening balance is valid.
     *
     * @param openingBalance  Balance to be validated
     * @return    Whether balance is valid
     */
    public static ErrorModel validateOpeningBalance(String openingBalance) {
        String[] openingBalanceDetails = openingBalance.split(ConnectorConstants.COLON);
        if (!isListLengthValid(openingBalanceDetails)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.OPENING_BALANCE));
        }

        String balance = openingBalanceDetails[2];
        if (StringUtils.isBlank(balance)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.OPENING_BALANCE));
        }

        ErrorModel errorModel = validateBalance(balance, ConnectorConstants.OPENING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }
        return new ErrorModel();
    }

    /**
     * Method to validate whether Closing balance is valid.
     *
     * @param closingBalance  Balance to be validated
     * @return    Whether balance is valid
     */
    public static ErrorModel validateClosingBalance(String closingBalance) {
        String[] closingBalanceDetails = closingBalance.split(ConnectorConstants.COLON);
        if (!isListLengthValid(closingBalanceDetails)) {
            return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.TRANSACTION_REF));
        }

        String balance = closingBalanceDetails[2];
        if (StringUtils.isBlank(balance)) {
            return new ErrorModel(ConnectorConstants.ERROR_C24,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.CLOSING_BALANCE));
        }

        ErrorModel errorModel = validateBalance(balance, ConnectorConstants.CLOSING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }
        return new ErrorModel();
    }

    /** Method to validate whether Closing available balance is valid
     *
     * @param closingBalance  Balance to be validated
     * @return     Whether balance is valid
     */
    public static ErrorModel validateClosingAvailableBalance(String closingBalance) {
        if (closingBalance != null) {
            String[] closingBalanceDetails = closingBalance.split(ConnectorConstants.COLON);
            if (!isListLengthValid(closingBalanceDetails)) {
                return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.CLOSING_AVAIL_BALANCE));
            }

            ErrorModel errorModel = validateBalance(closingBalanceDetails[2],
                    ConnectorConstants.CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }
        return new ErrorModel();
    }

    /** Method to validate whether Forward available balance is valid
     *
     * @param closingBalance  Balance to be validated
     * @return     Whether balance is valid
     */
    public static ErrorModel validateForwardAvailableBalance(String closingBalance) {
        if (closingBalance != null) {
            String[] closingBalanceDetails = closingBalance.split(ConnectorConstants.COLON);
            if (!isListLengthValid(closingBalanceDetails)) {
                return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE));
            }

            ErrorModel errorModel = validateBalance(closingBalanceDetails[2],
                    ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }
        return new ErrorModel();
    }

    /** Method to validate whether Opening/closing balance is valid
     *
     * @param balance  Balance to be validated
     * @return     Whether balance is valid
     */
    private static ErrorModel validateBalance(String balance, String balanceName) {
        ErrorModel errorModel;
        if (balance.length() > 25) {
            return new ErrorModel(ConnectorConstants.ERROR_M50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH,
                            balanceName, 25));
        }

        if (!balance.startsWith(ConnectorConstants.DEBIT) && !balance.startsWith(ConnectorConstants.CREDIT)) {
            return new ErrorModel(ConnectorConstants.ERROR_T51,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            balanceName));
        }

        errorModel = ValidatorUtils.validateAmountLength(balance.substring(10), balanceName);
        if (errorModel.isError()) {
            return errorModel;
        }

        errorModel = validateAmountFormat(balance.substring(10), balanceName);
        if (errorModel.isError()) {
            return errorModel;
        }
        return new ErrorModel();
    }

    /** Method to validate whether amount is valid
     *
     * @param amount  Amount to be validated
     * @return     Whether amount is valid
     */
    private static ErrorModel validateAmountFormat(String amount, String fieldName) {

        if (amount.contains(".")) {
            return new ErrorModel(ConnectorConstants.ERROR_T40,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            fieldName + ConnectorConstants.AMOUNT));
        }
        return  new ErrorModel();
    }

    /**
     * Method to validate the List length is valid.
     *
     * @param detailsList  Date to be validated
     * @return    Whether List length is valid
     */
    private static boolean isListLengthValid(String[] detailsList) {
        if (detailsList.length == 3) {
            return true;
        } else {
            return false;
        }
    }
}
