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
 * Model for Swift MT Tag 25.
 * <p>
 *     Option - No letter <br/>
 *     format: (Account)
 *     example: 25:DD01100056869
 *     Option - No letter <br/>
 *     format: (Account)
 *             (Identifier Code)
 *     example: 25:DD01100056869
 *              GSCRUS30XXX
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_25.htm">Tag 25</a>
 * </p>
 */
public class Field25 {
    public static final String TAG = "25";

    // Example - No letter option, P
    private char option;

    // Example - DD01100056869
    private String account;

    // Example - GSCRUS30XXX
    private String identifierCode;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    /**
     * Default constructor for Field25.
     */
    public Field25() {}

    /**
     * Method for parse and get Field25 object.
     * @param option              Option of the Field25
     * @param field25String       String which contains value of Field25
     * @throws MTMessageParsingException
     */
    public Field25(char option, String field25String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_NO_LETTER || option == MTParserConstants.FIELD_OPTION_P) {
            Matcher field25Matcher = MT940ParserConstants.FIELD_25_REGEX_PATTERN.matcher(field25String);

            if (!field25Matcher.matches()) {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        option != MTParserConstants.FIELD_OPTION_NO_LETTER ? Field25.TAG + option : Field25.TAG));
            }

            this.account = field25Matcher.group(1);
            this.identifierCode = field25Matcher.group(3);
            this.option = option;
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    Field60.TAG, option));
        }
    }
}
