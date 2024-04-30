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
public class Field62 {
    public static final String TAG = "62";

    // Example - F, M
    private char option;

    // Example - D
    private String dcMark;

    // Example - 230930
    private String date;

    // Example - USD
    private String currency;

    // Example - 843686,20
    private String amount;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

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
     * Method for set the option and return the instance.
     * @param option    Option of the Field62
     * @return          Created instance of Field62
     */
    public Field62 withOption(char option) {
        setOption(option);
        return this;
    }

    /**
     * Method for set D/C mark and return the instance.
     * @param dcMark      D/C mark of Field62
     * @return            Created instance of Field62
     */
    public Field62 withDCMark(String dcMark) {
        setDcMark(dcMark);
        return this;
    }

    /**
     * Method for set date value and return the instance.
     * @param date        Date value of Field62
     * @return            Created instance of Field62
     */
    public Field62 withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method for set currency and return the instance.
     * @param currency    Currency value of Field62
     * @return            Created instance of Field62
     */
    public Field62 withCurrency(String currency) {
        setCurrency(currency);
        return this;
    }

    /**
     * Method for set amount and return the instance.
     * @param amount      Amount value of Field62
     * @return            Created instance of Field62
     */
    public Field62 withAmount(String amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Method for parse and get Field62 object.
     * @param option              Option of the Field62
     * @param field62FString       String which contains value of Field62
     * @return                     Created instance of Field62
     * @throws MTMessageParsingException
     */
    public static Field62 parse(char option, String field62FString) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_F || option == MTParserConstants.FIELD_OPTION_M) {
            Matcher field62FMatcher = MT940ParserConstants.MT940_BALANCE_REGEX.matcher(field62FString);

            if (field62FMatcher.matches()) {
                return new Field62().withOption(option).withDCMark(field62FMatcher.group(1))
                        .withDate(field62FMatcher.group(2))
                        .withCurrency(field62FMatcher.group(3))
                        .withAmount(field62FMatcher.group(4));
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field62.TAG + option));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    Field60.TAG, option));
        }
    }
}
