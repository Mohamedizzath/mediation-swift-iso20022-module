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
 * Model for time indication in Text Block (Block 04).
 * <p>
 * format: /(Code)/(Time)(Sign)(Offset)
 * <p>
 * example: /CLSTIME/0915+0100
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_13C.htm">
 * Field 13C</a>
 */
public class Field13 {

    public static final String TAG = "13";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(ConnectorConstants.OPTION_C);
    public char option;

    // example: CLSTIME
    private String code;

    // format: HHMM
    // example: 0915
    private String time;

    // example: +
    private String sign;

    // format: HHMM
    // example: 0100
    private String offset;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option Single character
     * @param code  String with uppercase letters
     * @param time  String with four digits
     * @param sign  Either "+" or "-"
     * @param offset    String with four digits
     */
    public Field13(char option, String code, String time, String sign, String offset) {
        this.option = option;
        this.code = code;
        this.time = time;
        this.sign = sign;
        this.offset = offset;
    }

    public char getOption() {
        return option;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Method to parse and get Field13 object.
     * Current implementation -> Option C
     *
     * @param field13String String containing value of 13 field in Text Block
     * @param option single character option of the field13String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field13 parse(String field13String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD, option,
                    ConnectorConstants.FIELD_13));
        }

        // Get matcher to the regex matching -> /(Code)/(Time)(Sign)(Offset)
        Matcher field13Matcher = MTParserConstants.FIELD_13_REGEX_PATTERN.matcher(field13String);

        if (field13Matcher.matches()) {

            // group 1 -> Code
            // group 2 -> Time
            // group 3 -> Sign
            // group 4 -> Offset
            return new Field13(option, field13Matcher.group(1),
                    field13Matcher.group(2), field13Matcher.group(3), field13Matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.TIME_INDICATION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
