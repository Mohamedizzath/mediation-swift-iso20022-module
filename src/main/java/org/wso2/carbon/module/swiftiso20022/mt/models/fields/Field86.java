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
 * Model for Swift MT Tag 86
 * <p>
 *     format: (Narrative)<br/>
 *     example: :86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/
 *     us9m_20230720/2.0?topic=con_sfld_MaOruwQQEe2AI4OK6vBjrg_-110767701fld.htm">Tag 86</a>
 * </p>
 */
public class Field86 {
    public static final String TAG = "86";
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of Field86 and return the instance
     * @param value     Value of Field86
     * @return          Created instance of Field86
     */
    public Field86 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method for parse and get Field86 object
     * @param field86String       String which contains value of Field86
     * @return                    Created instance of Field86
     * @throws MTMessageParsingException
     */
    public static Field86 parse(String field86String) throws MTMessageParsingException {
        Optional<Matcher> field86Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_86_REGEX_PATTERN, field86String);

        if (field86Matcher.isPresent()) {
            return new Field86().withValue(field86Matcher.get().group(1));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field86.TAG));
        }
    }
}
