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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for value/amount in Text Block (Block 04).
 * <p>
 * format: (Date)(Currency)(Amount)
 * <p>
 * example: :32A:981209USD1000,00
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_32A.htm">
 * Field 32A</a>
 */
public class Field32A {

    public static final String TAG = "32A";

    // format: YYMMDD
    // example: 981209
    private String date;

    // example: USD
    private String currency;

    // example: 1000,00
    private String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date to be set.
     * @return object itself
     */
    public Field32A withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set currency of the field and return the instance.
     *
     * @param currency Currency to be set.
     * @return object itself
     */
    public Field32A withCurrency(String currency) {
        setCurrency(currency);
        return this;
    }

    /**
     * Method to set amount of the field and return the instance.
     *
     * @param amount Amount to be set.
     * @return object itself
     */
    public Field32A withAmount(String amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Method to parse and get Field32A object.
     *
     * @param field32AString String containing value of 32A field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field32A parse(String field32AString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Date)(Currency)(Amount)
        Matcher field32AMatcher = MTParserConstants.FIELD_32A_REGEX_PATTERN.matcher(field32AString);

        if (field32AMatcher.matches()) {

            // group 1 -> Date
            // group 2 -> Currency
            // group 3 -> Amount
            return new Field32A()
                    .withDate(field32AMatcher.group(1))
                    .withCurrency(field32AMatcher.group(2))
                    .withAmount(field32AMatcher.group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.VALUE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
