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
 * Model for intermediary institution with option C in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>/(Party Identifier)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:56C:/IRVTUS3NXXX</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_56C.htm">
 * Field 56C</a>
 */
public class Field56C {

    public static final String TAG = "56C";
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
    public Field56C withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field56C object.
     *
     * @param field56CString String containing value of 56C field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field56C parse(String field56CString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> /(Party Identifier)
        Optional<Matcher> field56CMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_C_REGEX_PATTERN, field56CString);

        if (field56CMatcher.isPresent()) {

            // group 0 -> /Party Identifier
            // group 1 -> Party Identifier
            return new Field56C()
                    .withValue(field56CMatcher.get().group(1));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.INTERMEDIARY_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
