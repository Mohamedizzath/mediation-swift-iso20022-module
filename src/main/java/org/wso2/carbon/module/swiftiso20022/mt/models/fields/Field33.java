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
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Model for instructed amount in Text Block (Block 04).
 * <p>
 * format: (Currency)(Amount)
 * <p>
 * example: USD1000,00
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_33B.htm">
 * Field 33B</a>
 */
public class Field33 {

    public static final String TAG = "33";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(ConnectorConstants.OPTION_B);
    public char option;

    // example: USD
    private String currency;

    // example: 1000,00
    private String amount;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option Single character
     * @param currency  String with 3 uppercase characters
     * @param amount String in the SWIFT amount format
     */
    public Field33(char option, String currency, String amount) {
        this.option = option;
        this.currency = currency;
        this.amount = amount;
    }

    public char getOption() {
        return option;
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
     * Method to parse and get Field33 object.
     * Current implementations -> Option B
     *
     * @param option single character option of the field33String
     * @param field33String String containing value of 33 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field33 parse(char option, String field33String) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD, option,
                    ConnectorConstants.FIELD_33));
        }

        // Get matcher to the regex matching -> (Currency)(Amount)
        Matcher field33Matcher = MTParserConstants.FIELD_33_REGEX_PATTERN.matcher(field33String);

        if (field33Matcher.matches()) {
            // group 1 -> Currency
            // group 2 -> Amount
            return new Field33(option, field33Matcher.group(1), field33Matcher.group(2));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.FIELD_33 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                    ConnectorConstants.TEXT_BLOCK));
        }
    }
}
