/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

/**
 * Model for Swift MT Tag 62.
 * <p>
 *     Option - F <br/>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 62:D230930USD843686,20
 *     Option - M <br/>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 62:D230930USD843686,20
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_mul_62a.htm">Tag 62</a>
 * </p>
 */
public class Field62 extends BalanceField {
    public static final String TAG = "62";

    // Example - F, M
    private char option;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    /**
     * Default constructor for Field62.
     */
    public Field62() {
        super();
    }

    /**
     * Constructor for parse and get Field62 object.
     * @param option              Option of the Field62
     * @param field62String       String which contains value of Field62
     * @throws MTMessageParsingException
     */
    public Field62(char option, String field62String) throws MTMessageParsingException {
        super(Field62.TAG + option, field62String);

        if (option == MTParserConstants.FIELD_OPTION_F || option == MTParserConstants.FIELD_OPTION_M) {
            this.option = option;
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    option, Field62.TAG));
        }
    }
}
