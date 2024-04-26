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
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/
 *     us9m_20230720/2.0?topic=con_sfld_MaOr3wQQEe2AI4OK6vBjrg_1271498157fld.htm">Tag 64</a>
 * </p>
 */
public class Field64 {
    public static final String TAG = "64";

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
     * Method for set D/C mark and return the instance.
     * @param dcMark      D/C mark of Field64
     * @return            Created instance of Field64
     */
    public Field64 withDCMark(String dcMark) {
        setDcMark(dcMark);
        return this;
    }

    /**
     * Method for set date value and return the instance.
     * @param date        Date value of Field64
     * @return            Created instance of Field64
     */
    public Field64 withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method for set currency and return the instance.
     * @param currency    Currency value of Field64
     * @return            Created instance of Field64
     */
    public Field64 withCurrency(String currency) {
        setCurrency(currency);
        return this;
    }

    /**
     * Method for set amount and return the instance.
     * @param amount      Amount value of Field64
     * @return            Created instance of Field64
     */
    public Field64 withAmount(String amount) {
        setAmount(amount);
        return this;
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
            return new Field64().withDCMark(field64Matcher.group(1))
                    .withDate(field64Matcher.group(2))
                    .withCurrency(field64Matcher.group(3))
                    .withAmount(field64Matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field64.TAG));
        }
    }
}
