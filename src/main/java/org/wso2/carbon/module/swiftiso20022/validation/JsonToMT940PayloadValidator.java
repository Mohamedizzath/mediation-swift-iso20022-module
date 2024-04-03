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

package org.wso2.carbon.module.swiftiso20022.validation;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationEngine;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;

/**
 * Class to validate the JSON payload for MT940 transformation.
 */
public class JsonToMT940PayloadValidator {

    private static final List<String> definedLengthFields  = List.of(MT940Constants.BAL_DATE,
            MT940Constants.BAL_CURRENCY, MT940Constants.SEQUENCE_NO, MT940Constants.TRANSACTION_TYPE);

    // List of mandatory fields from the payload for validation.
    private static final List<ValidatorContext> mandatoryValidationParamList = List.of(
            new ValidatorContext(MT940Constants.HEADER_BLOCK_1, MT940Constants.DN_HEADER_BLOCK_1),
            new ValidatorContext(MT940Constants.HEADER_BLOCK_2, MT940Constants.DN_HEADER_BLOCK_2),
            new ValidatorContext(MT940Constants.ACC_NUMBER, MT940Constants.DN_ACC_NUMBER),
            new ValidatorContext(MT940Constants.REFERENCE, MT940Constants.DN_TRANSACTION_REFERENCE),
            new ValidatorContext(MT940Constants.SEQUENCE_NO, MT940Constants.DN_SEQUENCE_NO),
            new ValidatorContext(MT940Constants.OPENING_BAL_DETAILS, MT940Constants.DN_OPENING_BALANCE),
            new ValidatorContext(MT940Constants.CLOSING_BALANCE_DETAILS, MT940Constants.DN_CLOSING_BALANCE)
    );

    // List of optional string fields from the payload for validation.
    private static final List<ValidatorContext> optionalStringValidationParamList = List.of(
            new ValidatorContext(MT940Constants.ACC_NUMBER_IDENTIFICATION,
                    MT940Constants.DN_ACC_NUMBER_IDENTIFICATION)
    );

    // List of fields for length validation.
    private static final List<ValidatorContext> lengthValidationParamList = List.of(
            new ValidatorContext(MT940Constants.ACC_NUMBER, MT940Constants.DN_ACC_NUMBER,
                    ConnectorConstants.ACC_IDENTIFICATION_LENGTH),
            new ValidatorContext(MT940Constants.REFERENCE, MT940Constants.DN_TRANSACTION_REF,
                    ConnectorConstants.REFERENCE_LENGTH),
            new ValidatorContext(MT940Constants.SEQUENCE_NO, MT940Constants.DN_SEQUENCE_NO,
                    ConnectorConstants.SEQUENCE_NO_LENGTH)
    );

    // List of alphanumeric fields from the payload for validation.
    private static final List<ValidatorContext> alphaNumericValidationParamList = List.of(
            new ValidatorContext(MT940Constants.HEADER_BLOCK_1, MT940Constants.DN_HEADER_BLOCK_1),
            new ValidatorContext(MT940Constants.HEADER_BLOCK_2, MT940Constants.DN_HEADER_BLOCK_2),
            new ValidatorContext(MT940Constants.ACC_NUMBER, MT940Constants.DN_ACC_NUMBER),
            new ValidatorContext(MT940Constants.ACC_NUMBER_IDENTIFICATION,
                    MT940Constants.DN_ACC_NUMBER_IDENTIFICATION)
    );

    // List of numeric fields from the payload for validation.
    private static final List<ValidatorContext> numericValidationParamList = List.of(
            new ValidatorContext(MT940Constants.SEQUENCE_NO, MT940Constants.DN_SEQUENCE_NO)
    );

    // List of numeric fields from the payload for validation.
    private static final List<ValidatorContext> characterSetXValidationParamList = List.of(
            new ValidatorContext(MT940Constants.REFERENCE, MT940Constants.DN_TRANSACTION_REFERENCE)
    );

    /**
     * Method to construct list of mandatory fields in Balances for validation.
     * @param balanceName        Name of the Balance object in Payload
     * @return List of fields for length validation
     */
    private static List<ValidatorContext> getMandatoryParamsInBalances(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_DATE, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_DATE)),
                new ValidatorContext(MT940Constants.BAL_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_CURRENCY)),
                new ValidatorContext(MT940Constants.BAL_AMOUNT, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_AMOUNT)),
                new ValidatorContext(MT940Constants.BAL_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_INDICATOR))
        );
    }

    // List of optional string fields from the payload for validation.
    private static List<ValidatorContext> getOptionalParamsInBalances(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_STATEMENT_TYPE, ConnectorUtils.concatFieldsWithSpaces(
                        balanceName, MT940Constants.DN_STATEMENT_TYPE))
        );
    }

    /**
     * Method to construct list of fields in Balances for length validation.
     * @param balanceName        Name of the Balance object in Payload
     * @return List of fields for length validation
     */
    public static List<ValidatorContext> getFieldsInBalanceForLengthValidation(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_DATE, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_DATE), ConnectorConstants.DATE_LENGTH),
                new ValidatorContext(MT940Constants.BAL_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_CURRENCY), ConnectorConstants.CURRENCY_LENGTH),
                new ValidatorContext(MT940Constants.BAL_AMOUNT, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_AMOUNT), ConnectorConstants.AMOUNT_LENGTH),
                new ValidatorContext(MT940Constants.BAL_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_INDICATOR), ConnectorConstants.INDICATOR_LENGTH)
        );
    }

    // List of alpha fields from the payload for validation.
    private static List<ValidatorContext> getAlphaParamsInBalances(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_INDICATOR)),
                new ValidatorContext(MT940Constants.BAL_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_CURRENCY)),
        new ValidatorContext(MT940Constants.BAL_STATEMENT_TYPE, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                MT940Constants.DN_STATEMENT_TYPE))
        );
    }

    private static List<ValidatorContext> getNumericParamsInBalances(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_DATE, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_DATE))
        );
    }

    // List of date fields from the payload for validation.
    private static List<ValidatorContext> getDateParamInBalance(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_DATE, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_DATE))
        );
    }

    // List of currency fields from the payload for validation.
    private static List<ValidatorContext> getCurrencyParamInBalance(String balanceName) {
        return List.of(
                new ValidatorContext(MT940Constants.BAL_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(balanceName,
                        MT940Constants.DN_CURRENCY))
        );
    }

    // Method to construct list of fields in Transactions for Mandatory parameter validation.
    private static final List<ValidatorContext> mandatoryFieldsInTransaction = List.of(
            new ValidatorContext(MT940Constants.TRANSACTION_DATE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_DATE)),
            new ValidatorContext(MT940Constants.TRANSACTION_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CURRENCY)),
            new ValidatorContext(MT940Constants.TRANSACTION_AMOUNT, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_AMOUNT)),
            new ValidatorContext(MT940Constants.TRANSACTION_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_INDICATOR)),
            new ValidatorContext(MT940Constants.TRANSACTION_REFERENCE,
                    MT940Constants.DN_TRANSACTION_REFERENCE),
            new ValidatorContext(MT940Constants.CUSTOMER_REFERENCE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CUSTOMER_REFERENCE)),
            new ValidatorContext(MT940Constants.TRANSACTION_TYPE, MT940Constants.DN_TRANSACTION_TYPE)
    );

    // Method to construct list of fields in Transactions for length validation.
    private static final List<ValidatorContext> fieldsInTransactionForLengthValidation = List.of(
            new ValidatorContext(MT940Constants.TRANSACTION_DATE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_DATE), ConnectorConstants.DATE_LENGTH),
            new ValidatorContext(MT940Constants.TRANSACTION_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CURRENCY), ConnectorConstants.CURRENCY_LENGTH),
            new ValidatorContext(MT940Constants.TRANSACTION_AMOUNT, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_AMOUNT), ConnectorConstants.AMOUNT_LENGTH),
            new ValidatorContext(MT940Constants.TRANSACTION_INDICATOR, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_INDICATOR),
                    ConnectorConstants.TRANSACTION_IND_LENGTH),
            new ValidatorContext(MT940Constants.TRANSACTION_REFERENCE, MT940Constants.DN_TRANSACTION_REFERENCE,
                    ConnectorConstants.REFERENCE_LENGTH),
            new ValidatorContext(MT940Constants.CUSTOMER_REFERENCE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CUSTOMER_REFERENCE),
                    ConnectorConstants.REFERENCE_LENGTH),
            new ValidatorContext(MT940Constants.TRANSACTION_TYPE, MT940Constants.DN_TRANSACTION_TYPE,
                    ConnectorConstants.TRANSACTION_TYPE_LENGTH)
    );

    // List of alphanumeric fields from the payload for validation.
    private static final List<ValidatorContext> alphaNumericParamsInTransactions = List.of(
            new ValidatorContext(MT940Constants.TRANSACTION_REFERENCE,
                    MT940Constants.DN_TRANSACTION_REFERENCE),
            new ValidatorContext(MT940Constants.CUSTOMER_REFERENCE,
                    MT940Constants.DN_CUSTOMER_REFERENCE)
    );

    // List of alpha fields from the payload for validation.
    private static final List<ValidatorContext> alphaParamsInTransactions = List.of(
            new ValidatorContext(MT940Constants.TRANSACTION_INDICATOR,
                    MT940Constants.DN_TRANSACTION_IND),
            new ValidatorContext(MT940Constants.TRANSACTION_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CURRENCY)),
            new ValidatorContext(MT940Constants.TRANSACTION_TYPE, MT940Constants.DN_TRANSACTION_TYPE)
    );

    private static final List<ValidatorContext> numericParamsInTransactions = List.of(
            new ValidatorContext(MT940Constants.TRANSACTION_DATE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_DATE))
    );

    // List of date fields from the payload for validation.
    private static final List<ValidatorContext> dateParamInTransaction = List.of(
            new ValidatorContext(MT940Constants.BAL_DATE, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_DATE))
    );

    // List of currency fields from the payload for validation.
    private static final List<ValidatorContext> currencyParamInTransaction = List.of(
            new ValidatorContext(MT940Constants.BAL_CURRENCY, ConnectorUtils.concatFieldsWithSpaces(
                    MT940Constants.DN_TRANSACTION, MT940Constants.DN_CURRENCY))
    );

    /**
     * Method to get the validation engine for MT940 Payload.
     * @return ValidationEngine
     */
    public static synchronized ValidationEngine getMT940ValidationEngine() {

        return new ValidationEngine()
                .addMandatoryParamValidationRule(mandatoryValidationParamList)
                .addOptionalStringParamValidationRule(optionalStringValidationParamList)
                .addParameterLengthValidationRule(lengthValidationParamList, definedLengthFields)
                .addAlphaNumericParamValidationRule(alphaNumericValidationParamList)
                .addNumericParamValidationRule(numericValidationParamList)
                .addMTCharacterSetXValidationRule(characterSetXValidationParamList);
    }

    /**
     * Method to get the validation engine for MT940 Balances.
     * @return ValidationEngine
     */
    public static synchronized ValidationEngine getMT940BalanceValidationEngine(String fieldName) {

        return new ValidationEngine()
                .addMandatoryParamValidationRule(getMandatoryParamsInBalances(fieldName))
                .addOptionalStringParamValidationRule(getOptionalParamsInBalances(fieldName))
                .addParameterLengthValidationRule(getFieldsInBalanceForLengthValidation(fieldName),
                        definedLengthFields)
                .addAlphaParamValidationRule(getAlphaParamsInBalances(fieldName))
                .addNumericParamValidationRule(getNumericParamsInBalances(fieldName))
                .addDateFormatValidationRule(getDateParamInBalance(fieldName))
                .addCurrencyFormatValidationRule(getCurrencyParamInBalance(fieldName));
    }

    /**
     * Method to get the validation engine for MT940 Transactions.
     * @return ValidationEngine
     */
    public static synchronized ValidationEngine getMT940TransactionValidationEngine() {

        return new ValidationEngine()
                .addMandatoryParamValidationRule(mandatoryFieldsInTransaction)
                .addParameterLengthValidationRule(fieldsInTransactionForLengthValidation,
                        definedLengthFields)
                .addAlphaNumericParamValidationRule(alphaNumericParamsInTransactions)
                .addAlphaParamValidationRule(alphaParamsInTransactions)
                .addNumericParamValidationRule(numericParamsInTransactions)
                .addDateFormatValidationRule(dateParamInTransaction)
                .addCurrencyFormatValidationRule(currencyParamInTransaction);
    }
}
