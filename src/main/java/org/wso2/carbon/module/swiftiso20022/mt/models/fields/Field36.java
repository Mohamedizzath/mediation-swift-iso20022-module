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
 * Model for exchange rate in Text Block (Block 04).
 * <p>
 * example: :36:0,9236
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_36.htm">
 * Field 36</a>
 */
public class Field36 {

    public static final String TAG = "36";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(ConnectorConstants.NO_LETTER_OPTION);
    public char option;

    // example: 0,9236
    private String value;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option Single character
     * @param value String specifying rate with comma at decimal point
     */
    public Field36(char option, String value) {
        this.option = option;
        this.value = value;
    }

    public char getOption() {
        return option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to parse and get Field36 object.
     * Current implementations -> No_letter
     *
     * @param field36String String containing value of 36 field in Text Block
     * @param option single character option of the field26String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field36 parse(String field36String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD, option,
                    ConnectorConstants.FIELD_36));
        }

        Matcher field26Matcher = MTParserConstants.FIELD_36_REGEX_PATTERN.matcher(field36String);

        if (field26Matcher.matches()) {

            return new Field36(option, field26Matcher.group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.EXCHANGE_RATE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
