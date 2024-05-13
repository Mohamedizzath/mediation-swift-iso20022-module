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
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 71A -> Model for details of charge in Text Block (Block 04).
 * <p>
 * example: :71A:BEN
 * <p>
 * 71F -> Model for sender's charges in Text Block (Block 04).
 * <p>
 * format: (Currency)(Amount)
 * <p>
 * example: :71F:EUR8,00
 * <p>
 * 71G -> Model for receiver's charges in Text Block (Block 04).
 * <p>
 * format: (Currency)(Amount)
 * <p>
 * example: :71G:EUR5,50
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_71A.htm">
 * Field 71A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_71F.htm">
 * Field 71F</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_71G.htm">
 * Field 71G</a>
 */
public class Field71 {

    public static final String TAG = "71";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_F, ConnectorConstants.OPTION_G);
    private static final Map<Character, String> FIELD_NAME = Map.of(
            ConnectorConstants.OPTION_A, MT103Constants.DETAILS_OF_CHARGES,
            ConnectorConstants.OPTION_F, MT103Constants.SENDERS_CHARGES,
            ConnectorConstants.OPTION_G, MT103Constants.RECEIVERS_CHARGES
    );
    private char option;
    private String code;
    private String currency;
    private String amount;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param code String with 3 uppercase letters
     * @param currency  String with 3 uppercase characters
     * @param amount String in the SWIFT amount format
     */
    public Field71(char option, String code, String currency, String amount) {
        this.option = option;
        this.code = code;
        this.currency = currency;
        this.amount = amount;
    }

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
     * Method to parse and get Field71 object.
     * Current implementations -> Option A, F and G
     *
     * @param field71String String containing value of 71 field in Text Block
     * @param option single character option of the field71String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field71 parse(String field71String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_71));
        }

        Matcher field71Matcher = MTParserConstants.FIELD_71_REGEX_PATTERN.matcher(field71String);

        if (field71Matcher.matches()) {
            // group 1 -> Code
            // group 2 -> (Currency)(Amount)
            // group 3 -> Currency
            // group 4 -> Amount
            return new Field71(
                    option, field71Matcher.group(1), field71Matcher.group(3), field71Matcher.group(4));

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    FIELD_NAME.get(option), ConnectorConstants.TEXT_BLOCK));
        }
    }
}
