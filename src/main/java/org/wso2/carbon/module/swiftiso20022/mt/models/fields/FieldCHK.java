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

import java.util.regex.Matcher;

/**
 * Model for checksum in Trailer Block (Block 05).
 * <p>
 * example: {CHK:123456789ABC}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-5-trailers-description/">
 * Trailer Block Fields</a>
 */
public class FieldCHK {

    public static final String TAG = "CHK";

    // example: 123456789ABC
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
    public FieldCHK withValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Method to parse and get FieldCHK object.
     *
     * @param fieldCHKString String containing value of CHK field in Trailer Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static FieldCHK parse(String fieldCHKString) throws MTMessageParsingException {

        Matcher fieldCHKMatcher = MTParserConstants.FIELD_CHK_REGEX_PATTERN.matcher(fieldCHKString);

        if (fieldCHKMatcher.matches()) {

            return new FieldCHK()
                    .withValue(fieldCHKMatcher.group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.CHECKSUM, ConnectorConstants.TRAILER_BLOCK));
        }
    }

}
