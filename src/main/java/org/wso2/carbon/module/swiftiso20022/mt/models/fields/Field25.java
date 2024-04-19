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
 * Model for Swift MT Tag 25.
 * <p>
 *     format: (Account)
 *     example: 25:DD01100056869
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/us9m_20230720/
 *     2.0?topic=con_sfld_MaOrZQQQEe2AI4OK6vBjrg_1154221120fld.htm">Tag 25</a>
 * </p>
 */
public class Field25 {
    public static final String TAG = "25";

    // Example - DD01100056869
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of Field25 and return the instance.
     * @param value   Value of Field25
     * @return        Created instance of Field25
     */
    public Field25 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method for parse and get Field25 object.
     * @param field25String       String which contains value of Field25
     * @return                    Created instance of Field25
     * @throws MTMessageParsingException
     */
    public static Field25 parse(String field25String) throws MTMessageParsingException {
        Optional<Matcher> field25Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_25_REGEX_PATTERN, field25String);

        if (field25Matcher.isPresent()) {
            return new Field25().withValue(field25Matcher.get().group(1));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field25.TAG));
        }
    }
}
