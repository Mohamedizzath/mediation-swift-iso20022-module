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
 *     format: (Value)<br/>
 *     example: 20:258158850
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/us9m_20230720/
 *     2.0?topic=con_sfld_MaOrQQQQEe2AI4OK6vBjrg_1537937705fld.htm">Tag 20</a>
 * </p>
 */
public class Field20 {

    public static final String TAG = "20";

    // Example - 258158850
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of Field20 and return the instance.
     * @param value     Value of Field20
     * @return          Created instance of Field20
     */
    public Field20 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method for parse and get Field20 object.
     * @param field20String       String which contains value of Field20
     * @return                    Created instance of Field20
     * @throws MTMessageParsingException
     */
    public static Field20 parse(String field20String) throws MTMessageParsingException {
        Matcher field20Matcher = MT940ParserConstants.FIELD_20_REGEX_PATTERN.matcher(field20String);

        if (field20Matcher.matches()) {
            return new Field20().withValue(field20Matcher.group(1));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field20.TAG));
        }
    }
}
