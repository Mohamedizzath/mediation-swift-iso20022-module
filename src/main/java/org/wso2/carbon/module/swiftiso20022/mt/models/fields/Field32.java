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

import java.util.Arrays;
import java.util.List;
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
public class Field32 {

    public static final String TAG = "32";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(ConnectorConstants.OPTION_A);
    public char option;

    // format: YYMMDD
    // example: 981209
    private String date;

    // example: USD
    private String currency;

    // example: 1000,00
    private String amount;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option Single character
     * @param date  String with 6 digits
     * @param currency  String with 3 uppercase characters
     * @param amount String in the SWIFT amount format
     */
    public Field32(char option, String date, String currency, String amount) {
        this.option = option;
        this.date = date;
        this.currency = currency;
        this.amount = amount;
    }

    public char getOption() {
        return option;
    }

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
     * Method to parse and get Field32 object.
     * Current Implementations -> Option A
     *
     * @param field32String String containing value of 32 field in Text Block
     * @param option single character option of the field32String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field32 parse(String field32String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD, option,
                    ConnectorConstants.FIELD_32));
        }

        // Get matcher to the regex matching -> (Date)(Currency)(Amount)
        Matcher field32Matcher = MTParserConstants.FIELD_32_REGEX_PATTERN.matcher(field32String);

        if (field32Matcher.matches()) {

            // group 1 -> Date
            // group 2 -> Currency
            // group 3 -> Amount
            return new Field32(option, field32Matcher.group(1), field32Matcher.group(2), field32Matcher.group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.VALUE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
