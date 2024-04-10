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
 * Model for sanctions screening information in User Header Block (Block 03).
 * <p>
 * format: /(Code)[/Additional Information]
 * example: {433:/AOK/additional information...}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field433 {

    public static final String TAG = "433";

    // example: AOK
    private String code;

    // example: additional information
    private String additionalInformation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * Method to set code of the field and return the instance.
     *
     * @param code Code value to be set.
     * @return object itself
     */
    public Field433 withCode(String code) {
        setCode(code);
        return this;
    }

    /**
     * Method to set additional information of the field and return the instance.
     *
     * @param additionalInformation Additional information to be set.
     * @return object itself
     */
    public Field433 withAdditionalInformation(String additionalInformation) {
        setAdditionalInformation(additionalInformation);
        return this;
    }

    /**
     * Method to parse and get Field433 object.
     *
     * @param field433String String containing value of 433 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field433 parse(String field433String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Code)[/(Additional Information)]
        Optional<Matcher> field433Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_433_REGEX_PATTERN, field433String);

        if (field433Matcher.isPresent()) {

            Matcher matcher = field433Matcher.get();

            // group 1 -> Code
            // group 2 -> /Additional information
            // group 3 -> Additional information
            return new Field433()
                    .withCode(matcher.group(1))
                    .withAdditionalInformation(matcher.group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.BLOCK03_SANCTIONS_SCREENING_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }
}
