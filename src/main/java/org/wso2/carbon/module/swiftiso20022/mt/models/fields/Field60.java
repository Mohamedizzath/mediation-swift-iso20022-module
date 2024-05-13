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

import org.wso2.carbon.module.swiftiso20022.constants.MT940ParserConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 60.
 * <p>
 *     Option - F <br/>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 60F:D230930USD843686,20
 *     Option - M <br/>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 60M:D230930USD843686,20
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_mul_60a.htm">Tag 60</a>
 * </p>
 */
public class Field60 extends BalanceField {
    public static final String TAG = "60";

    // Example - F, M
    private char option;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    /**
     * Default constructor for Field60.
     */
    public Field60() {
        super();
    }

    /**
     * Constructor for parse and get Field60 object.
     * @param option              Option of the Field60
     * @param dcMark              DCMark of the Field60
     * @param date                Date of the Field60
     * @param currency            Currency of the Field60
     * @param amount              Amount of the Field60
     * @throws MTMessageParsingException
     */
    public Field60(char option, String dcMark, String date, String currency, String amount)
            throws MTMessageParsingException {
        super(dcMark, date, currency, amount);
        this.option = option;
    }

    /**
     * Method for parse and get Field60 object.
     * @param option              Option of the Field60
     * @param field60String       String which contains value of Field60
     * @return                     Created instance of Field60
     * @throws MTMessageParsingException
     */
    public static Field60 parse(char option, String field60String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_F || option == MTParserConstants.FIELD_OPTION_M) {
            Matcher field60Matcher = MT940ParserConstants.MT940_BALANCE_REGEX.matcher(field60String);

            if (field60Matcher.matches()) {
                return new Field60(option, field60Matcher.group(1), field60Matcher.group(2), field60Matcher.group(3),
                        field60Matcher.group(4));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field60.TAG + option));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    option, Field60.TAG));
        }
    }
}
