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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.MT940ParserConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 20.
 * <p>
 *     Option - No letter <br />
 *     format: (Value)<br/>
 *     example: 20:258158850
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_20.htm">Tag 20</a>
 * </p>
 */
public class Field20 {

    public static final String TAG = "20";

    // Example - C, D, U
    private char option;

    // Example - 258158850
    private String value;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Default constructor for Field20.
     */
    public Field20() {}

    /**
     * Constructor for parse and get Field20 object.
     * @param option              Option of the Field20
     * @param value               String which contains value of Field20
     * @throws MTMessageParsingException
     */
    public Field20(char option, String value) throws MTMessageParsingException {
        this.value = value;
        this.option = option;
    }

    /**
     * Method for parse and get Field20 object.
     * @param option              Option of the Field20
     * @param field20String       String which contains value of Field20
     * @return                    Created instance of Field20
     * @throws MTMessageParsingException
     */
    public static Field20 parse(char option, String field20String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            Matcher field20Matcher = MT940ParserConstants.FIELD_20_REGEX_PATTERN.matcher(field20String);

            if (field20Matcher.matches()) {
                return new Field20(option, field20Matcher.group(1));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field20.TAG));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    Field20.TAG, option));
        }
    }
}
