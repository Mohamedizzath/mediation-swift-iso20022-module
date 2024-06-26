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
 * Model for Swift MT Tag 86.
 * <p>
 *     Option - No letter <br/>
 *     format: (Narrative)<br/>
 *     example: :86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_86.htm">Tag 86</a>
 * </p>
 */
public class Field86 {
    public static final String TAG = "86";

    // Example - A, B, D
    private char option;

    // Example - EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003
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
     * Method to set option of Field86 and return the instance.
     * @param option     Option of Field86
     * @return          Created instance of Field86
     */
    public Field86 withOption(char option) {
        setOption(option);
        return this;
    }

    /**
     * Method to set value of Field86 and return the instance.
     * @param value     Value of Field86
     * @return          Created instance of Field86
     */
    public Field86 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method for parse and get Field86 object.
     * @param option              Option of the Field86
     * @param field86String       String which contains value of Field86
     * @return                    Created instance of Field86
     * @throws MTMessageParsingException
     */
    public static Field86 parse(char option, String field86String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            Matcher field86Matcher = MT940ParserConstants.FIELD_86_REGEX_PATTERN.matcher(field86String);

            if (field86Matcher.matches()) {
                return new Field86().withOption(option).withValue(field86Matcher.group(1));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field86.TAG));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    Field60.TAG, option));
        }
    }
}
