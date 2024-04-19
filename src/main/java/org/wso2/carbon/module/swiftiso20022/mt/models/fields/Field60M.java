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
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 60M
 * <p>
 *     format: (D/C Mark)(Date)(Currency)(Amount)<br/>
 *     example: 60M:D230930USD843686,20
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/
 *     us9m_20210723/1.0?topic=con_sfld_G-krqduqEeqF57jgqTEwJQ_317764331fld.htm">Tag 60M</a>
 * </p>
 */
public class Field60M {
    public static final String TAG = "60M";

    // Example - D
    private String dcMark;

    // Example - 230930
    private String date;

    // Example - USD
    private String currency;

    // Example - 843686,20
    private String amount;

    public String getDcMark() {
        return dcMark;
    }

    public void setDcMark(String dcMark) {
        this.dcMark = dcMark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Method for set D/C mark and return the instance
     * @param dcMark      D/C mark of Field60M
     * @return            Created instance of Field60M
     */
    public Field60M withDCMark(String dcMark) {
        setDcMark(dcMark);
        return this;
    }

    /**
     * Method for set date value and return the instance
     * @param date        Date value of Field60M
     * @return            Created instance of Field60M
     */
    public Field60M withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method for set currency and return the instance
     * @param currency    Currency value of Field60M
     * @return            Created instance of Field60M
     */
    public Field60M withCurrency(String currency) {
        setCurrency(currency);
        return this;
    }

    /**
     * Method for set amount and return the instance
     * @param amount      Amount value of Field60M
     * @return            Created instance of Field60M
     */
    public Field60M withAmount(String amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Method for parse and get Field60M object
     * @param field60MString       String which contains value of Field60M
     * @return                     Created instance of Field60M
     * @throws MTMessageParsingException
     */
    public static Field60M parse(String field60MString) throws MTMessageParsingException {
        Optional<Matcher> field60FMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_60M_REGEX_PATTERN, field60MString);

        if (field60FMatcher.isPresent()) {
            return new Field60M().withDCMark(field60FMatcher.get().group(1))
                    .withDate(field60FMatcher.get().group(2))
                    .withCurrency(field60FMatcher.get().group(3))
                    .withAmount(field60FMatcher.get().group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field60M.TAG));
        }
    }
}
