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
     * Method to set option of Field25 and return the instance.
     * @param option   Value of Field25
     * @return        Created instance of Field25
     */
    public Field25 withOption(char option) {
        setOption(option);
        return this;
    }

    /**
     * Method to set account  of Field25 and return the instance.
     * @param account   Value of Field25
     * @return        Created instance of Field25
     */
    public Field25 withAccount(String account) {
        setAccount(account);
        return this;
    }

    /**
     * Method to set identifier code of Field25 and return the instance.
     * @param identifierCode   Identifier code of Field25
     * @return                 Created instance of Field25
     */
    public Field25 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method for parse and get Field25 object.
     * @param option              Option of the Field25
     * @param field25String       String which contains value of Field25
     * @return                    Created instance of Field25
     * @throws MTMessageParsingException
     */
    public static Field25 parse(char option, String field25String) throws MTMessageParsingException {
        if (option == 'P') {
            Matcher field25PMatcher = MT940ParserConstants.FIELD_25P_REGEX_PATTERN.matcher(field25String);

            if (field25PMatcher.matches()) {
                return new Field25().withOption(option).withAccount(field25PMatcher.group(1))
                        .withIdentifierCode(field25PMatcher.group(3));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field25.TAG + option));
            }
        } else {
            Matcher field25Matcher = MT940ParserConstants.FIELD_25_REGEX_PATTERN.matcher(field25String);

            if (field25Matcher.matches()) {
                return new Field25().withOption(option).withAccount(field25Matcher.group(1));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field25.TAG));
            }
        }
    }
}
