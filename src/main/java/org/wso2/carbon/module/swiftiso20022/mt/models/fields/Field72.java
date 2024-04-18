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

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

/**
 * Model for sender to receiver information in Text Block (Block 04).
 * <p>
 * example: :72:/INS/ABNANL2A
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_72.htm">
 * Field 72</a>
 */
public class Field72 {

    public static final String TAG = "72";

    private List<String> values;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    /**
     * Method to set values of the field and return the instance.
     *
     * @param values Values to be set.
     * @return object itself
     */
    public Field72 withValues(List<String> values) {
        setValues(values);
        return this;
    }

    /**
     * Method to parse and get Field72 object.
     *
     * @param field72String String containing value of 72 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field72 parse(String field72String) throws MTMessageParsingException {

        Optional<Matcher> field72Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_72_REGEX_PATTERN, field72String);

        if (field72Matcher.isPresent()) {

            return new Field72()
                    // Values group -> "line1\nline2\n" -> ["line1", "line2"]
                    .withValues(
                            List.of(field72Matcher.get().group(1).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.SENDER_TO_RECEIVER_INFORMATION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
