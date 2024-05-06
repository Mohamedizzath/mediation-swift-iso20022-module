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
 * Model for Swift MT Tag 65.
 * <p>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 65:D230930USD843686,20
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_65.htm">Tag 65</a>
 * </p>
 */
public class Field65 extends BalanceField {
    public static final String TAG = "65";

    /**
     * Default constructor for Field65.
     */
    public Field65() {
        super();
    }

    /**
     * Constructor for parse and get Field65 object.
     * @param dcMark              DCMark of the Field65
     * @param date                Date of the Field65
     * @param currency            Currency of the Field65
     * @param amount              Amount of the Field65
     * @throws MTMessageParsingException
     */
    public Field65(String dcMark, String date, String currency, String amount) throws MTMessageParsingException {
        super(dcMark, date, currency, amount);
    }

    /**
     * Method for parse and get Field65 object.
     * @param field65String       String which contains value of Field65
     * @return                     Created instance of Field65
     * @throws MTMessageParsingException
     */
    public static Field65 parse(String field65String) throws MTMessageParsingException {
        Matcher field65Matcher = MT940ParserConstants.MT940_BALANCE_REGEX.matcher(field65String);

        if (field65Matcher.matches()) {
            return new Field65(field65Matcher.group(1), field65Matcher.group(2), field65Matcher.group(3),
                    field65Matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field65.TAG));
        }
    }
}
