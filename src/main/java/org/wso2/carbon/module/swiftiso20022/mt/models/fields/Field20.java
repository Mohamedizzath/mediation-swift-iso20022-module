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
 * Model for references in Text Block (Block 04).
 * <p>
 * example: :20:Ref254
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_20.htm">
 * Field 20</a>
 */
public class Field20 {

    public static final String TAG = "20";

    // example: Ref254
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
    public Field20 withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field20 object.
     *
     * @param field20String String containing value of 20 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field20 parse(String field20String) throws MTMessageParsingException {

        Optional<Matcher> field20Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_20_REGEX_PATTERN, field20String);

        if (field20Matcher.isPresent()) {

            return new Field20()
                    .withValue(field20Matcher.get().group(0));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.SENDERS_REFERENCE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
