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

public class Field21 {
    public static final String TAG = "21";

    // Example - 258158850
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of Field21 and return the instance
     * @param value     Value of Field21
     * @return          Created instance of Field21
     */
    public Field21 withValue(String value) {
        setValue(value);
        return this;
    }

    public static Field21 parse(String field21String) throws MTMessageParsingException {
        Optional<Matcher> field21Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_21_REGEX_PATTERN, field21String);

        if (field21Matcher.isPresent()) {
            return new Field21().withValue(field21Matcher.get().group(1));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field21.TAG));
        }
    }
}
