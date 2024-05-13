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
 * Model for Swift MT Tag 64.
 * <p>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 64:D230930USD843686,20
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_64.htm">Tag 64</a>
 * </p>
 */
public class Field64 extends BalanceField {
    public static final String TAG = "64";

    /**
     * Default constructor for Field64.
     */
    public Field64() {
        super();
    }

    /**
     * Constructor for parse and get Field64 object.
     * @param dcMark              DCMark of the Field64
     * @param date                Date of the Field64
     * @param currency            Currency of the Field64
     * @param amount              Amount of the Field64
     * @throws MTMessageParsingException
     */
    public Field64(String dcMark, String date, String currency, String amount) throws MTMessageParsingException {
        super(dcMark, date, currency, amount);
    }

    /**
     * Method for parse and get Field64 object.
     * @param field64String       String which contains value of Field64
     * @return                     Created instance of Field64
     * @throws MTMessageParsingException
     */
    public static Field64 parse(String field64String) throws MTMessageParsingException {
        Matcher field64Matcher = MT940ParserConstants.MT940_BALANCE_REGEX.matcher(field64String);

        if (field64Matcher.matches()) {
            return new Field64(field64Matcher.group(1), field64Matcher.group(2), field64Matcher.group(3),
                    field64Matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field64.TAG));
        }
    }
}
