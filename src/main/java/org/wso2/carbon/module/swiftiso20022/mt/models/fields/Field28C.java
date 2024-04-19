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

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 28C
 *<p>
 *     format: (Statement Number)(Sequence Number)<br/>
 *     example: 28C:235/2
 * </p>
 *@see <a href="https://www2.swift.com/knowledgecentre/publications/
 * us9m_20230720/2.0?topic=con_sfld_MaOreQQQEe2AI4OK6vBjrg_-1223640171fld.htm">Tag 28C</a>
 * </p>
 */
public class Field28C {
    public static final String TAG = "28C";

    // Example - 235
    private String statementNumber;

    // Example - 2
    private String sequenceNumber;

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
     * Method for set statement number and return the instance
     * @param statementNumber    Value of statement number
     * @return                   Created instance of Field28C
     */
    public Field28C withStatementNumber(String statementNumber) {
        setStatementNumber(statementNumber);
        return this;
    }

    /**
     * Method for set sequence number and return the instance
     * @param sequenceNumber    Value of sequence number
     * @return                   Created instance of Field28C
     */
    public Field28C withSequenceNumber(String sequenceNumber) {
        setSequenceNumber(sequenceNumber);
        return this;
    }

    /**
     * Method for parse and get Field28C object
     * @param field28CString       String which contains value of Field28C
     * @return                     Created instance of Field28C
     * @throws MTMessageParsingException
     */
    public static Field28C parse(String field28CString) throws MTMessageParsingException {
        Optional<Matcher> field28CPMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_28C_REGEX_PATTERN, field28CString);

        if (field28CPMatcher.isPresent()) {
            return new Field28C().withStatementNumber(field28CPMatcher.get().group(1))
                    .withSequenceNumber(field28CPMatcher.get().group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field28C.TAG));
        }
    }
}
