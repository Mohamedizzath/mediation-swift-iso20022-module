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
 * Model for Swift MT Tag 28.
 *<p>
 *     Option-C <br>
 *     format: (Statement Number)(Sequence Number)<br/>
 *     example: 28C:235/2
 *
 *@see <a href="https://www2.swift.com/knowledgecentre/
 * publications/usgf_20230720/2.0?topic=idx_fld_tag_28.htm">Tag 28C</a>
 * </p>
 */
public class Field28 {
    public static final String TAG = "28";

    // Example - C
    private char option;

    // Example - 235
    private String statementNumber;

    // Example - 2
    private String sequenceNumber;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Default constructor for Field28.
     */
    public Field28() {}

    /**
     * Constructor for parse and get Field28 object.
     * @param option              Option of the Field28
     * @param field28CString       String which contains value of Field28
     * @throws MTMessageParsingException
     */
    public Field28(char option, String field28CString) throws MTMessageParsingException {
        if (option == 'C') {
            Matcher field28CPMatcher = MT940ParserConstants.FIELD_28_REGEX_PATTERN.matcher(field28CString);

            if (field28CPMatcher.matches()) {
                this.statementNumber = field28CPMatcher.group(1);
                this.sequenceNumber = field28CPMatcher.group(3);
                this.option = option;
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field28.TAG + option));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    Field28.TAG, option));
        }
    }
}
