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
 * Model for message user reference in User Header Block (Block 03).
 * <p>
 * example: {108:REF0140862562/015}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field108 {

    public static final String TAG = "108";

    // example: REF0140862562/015
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
    public Field108 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field108 object.
     *
     * @param field108String String containing value of 108 field in User Header Block
     * @return A {@link Field108} with values assigned from the passed string.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field108 parse(String field108String) throws MTMessageParsingException {

        Optional<Matcher> field108Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_108_REGEX_PATTERN, field108String);

        if (field108Matcher.isPresent()) {
            return new Field108()
                    .withValue(field108Matcher.get().group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.BLOCK03_MESSAGE_USER_REFERENCE, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }

}
