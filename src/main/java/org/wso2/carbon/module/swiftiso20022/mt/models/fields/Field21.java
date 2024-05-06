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
 * Model for Swift MT Tag 21.
 * <p>
 *     Option - No letter <br/>
 *     format: (Value)<br/>
 *     example: 21:258158850
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_21.htm">Tag 21</a>
 * </p>
 */
public class Field21 {
    public static final String TAG = "21";

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
     * Default constructor for Field21.
     */
    public Field21() {}

    /**
     * Constructor for parse and get Field21 object.
     * @param option              Option of the Field21
     * @param value               String which contains value of Field21
     * @throws MTMessageParsingException
     */
    public Field21(char option, String value) throws MTMessageParsingException {
        this.value = value;
        this.option = option;
    }

    /**
     * Method for parse and get Field21 object.
     * @param option              Option of the Field21
     * @param field21String       String which contains value of Field21
     * @return                    Created instance of Field21
     * @throws MTMessageParsingException
     */
    public static Field21 parse(char option, String field21String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            Matcher field21Matcher = MT940ParserConstants.FIELD_21_REGEX_PATTERN.matcher(field21String);

            if (field21Matcher.matches()) {
                return new Field21(MTParserConstants.FIELD_OPTION_NO_LETTER, field21Matcher.group(1));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field21.TAG));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    option, Field21.TAG));
        }
    }
}
