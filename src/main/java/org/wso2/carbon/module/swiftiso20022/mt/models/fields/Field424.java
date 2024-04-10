/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Model for related reference in User Header Block (Block 03).
 * <p>
 * example: {424:PQAB1234}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field424 {

    public static final String TAG = "424";

    // example: PQAB1234...
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of the field and return the instance.
     *
     * @param value Value to be set.
     * @return object itself
     */
    public Field424 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field424 object.
     *
     * @param field424String String containing value of 434 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the string is invalid.
     */
    public static Field424 parse(String field424String) throws MTMessageParsingException {

        Optional<Matcher> field424Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_424_REGEX_PATTERN, field424String);

        if (field424Matcher.isPresent()) {
            return new Field424()
                    .withValue(field424Matcher.get().group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.BLOCK03_RELATED_REFERENCE, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }

}
