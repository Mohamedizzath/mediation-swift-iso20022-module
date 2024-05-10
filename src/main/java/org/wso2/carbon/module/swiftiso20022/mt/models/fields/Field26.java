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
 * Model for transaction type code in Text Block (Block 04).
 * <p>
 * example: :26T:K90
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_26T.htm">
 * Field 26T</a>
 */
public class Field26 {

    public static final String TAG = "26";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(ConnectorConstants.OPTION_T);
    public char option;

    // example: K90
    private String value;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option Single character
     * @param value String with three uppercase characters and digits
     */
    public Field26(char option, String value) {
        this.option = option;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to parse and get Field26 object.
     * Current Implementations -> Option T
     *
     * @param field26TString String containing value of 26 field in Text Block
     * @param option single character option of the field26String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field26 parse(String field26TString, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD, option,
                    ConnectorConstants.FIELD_26));
        }

        Matcher field26Matcher = MTParserConstants.FIELD_26_REGEX_PATTERN.matcher(field26TString);

        if (field26Matcher.matches()) {

            return new Field26(option, field26Matcher.group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.TRANSACTION_TYPE_CODE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
