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
 * Model for service type identifier in User Header Block (Block 03).
 * <p>
 * example: {111:243}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field111 {

    public static final String TAG = "111";

    // example: 243
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
    public Field111 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field111 object.
     *
     * @param field111String String containing value of 111 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the string is invalid
     */
    public static Field111 parse(String field111String) throws MTMessageParsingException {

        Optional<Matcher> field111Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_111_REGEX_PATTERN, field111String);

        if (field111Matcher.isPresent()) {
            return new Field111()
                    .withValue(field111Matcher.get().group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.BLOCK03_SERVICE_TYPE_IDENTIFIER, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }

}
