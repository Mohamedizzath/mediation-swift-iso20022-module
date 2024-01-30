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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

import java.util.Currency;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Class to validate the request payload.
 */
public class ValidatorUtils {

    /** Method to validate whether currency is in ISO 4217 format
     *
     * @param currency  Currency to be validated
     * @return     Whether currency is valid
     */
    public static boolean isValidCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            return false;
        }
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        long matchingCurrencyCount = availableCurrencies.stream()
                .filter(c -> c.getCurrencyCode().equals(currency)).count();
        return matchingCurrencyCount > 0;
    }

    /** Method to validate whether amount is valid
     *
     * @param amount  Amount to be validated
     * @return     Whether amount is valid
     */
    public static ErrorModel validateAmountLength(String amount, String fieldName) {
        ErrorModel errorModel = new ErrorModel();

        if (amount == null) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    ConnectorConstants.ERROR_AMOUNT_NULL);
        }
        if (amount.isBlank()) {
            return new ErrorModel(ConnectorConstants.ERROR_C03,
                    ConnectorConstants.ERROR_AMOUNT_SIZE_INVALID);
        }

        if (amount.length() > 16) {
            return new ErrorModel(ConnectorConstants.ERROR_M50,
                    String.format(ConnectorConstants.ERROR_PARAMETER_LENGTH,
                            fieldName + ConnectorConstants.AMOUNT, 15));
        }
        errorModel.setIsError(false);
        return errorModel;
    }

    /** Method to validate whether a values is a number
     *
     * @param number  Number to be validated
     * @return     Whether the value is a number
     */
    public static boolean isNumber(String number) {
        return Pattern.matches(ConnectorConstants.NUMBER_REGEX_PATTERN, number);
    }
}
