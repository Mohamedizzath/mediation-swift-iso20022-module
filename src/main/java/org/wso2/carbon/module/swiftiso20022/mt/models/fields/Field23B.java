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

import java.util.Optional;
import java.util.regex.Matcher;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

/**
 * Model for bank operation code in Text Block (Block 04).
 * Value can only be one of predefined 4 character codes.
 * <p>
 * example: :23B:SPAY
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_23B.htm">
 * Field 23B</a>
 */
public class Field23B {

    public static final String TAG = "23B";

    // example: CRED
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
    public Field23B withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field23B object.
     *
     * @param field23BString String containing value of 23B field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field23B parse(String field23BString) throws MTMessageParsingException {

        Optional<Matcher> field23BMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_23B_REGEX_PATTERN, field23BString);

        if (field23BMatcher.isPresent()) {

            return new Field23B()
                    .withValue(field23BMatcher.get().group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.BANK_OPERATION_CODE, ConnectorConstants.TEXT_BLOCK));
        }
    }

}
