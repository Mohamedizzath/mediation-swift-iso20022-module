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
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for envelope contents in Text Block (Block 04).
 * <p>
 * example: :77T:/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+...
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_77T.htm">
 * Field 77T</a>
 */
public class Field77T {

    public static final String TAG = "77T";

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
    public Field77T withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Method to parse and get Field77T object.
     *
     * @param field77TString String containing value of 77T field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field77T parse(String field77TString) throws MTMessageParsingException {

        Matcher field77TMatcher = MTParserConstants.FIELD_77T_REGEX_PATTERN.matcher(field77TString);

        if (field77TMatcher.matches()) {

            return new Field77T()
                    .withValue(field77TMatcher.group());
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ENVELOPE_CONTENTS, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
