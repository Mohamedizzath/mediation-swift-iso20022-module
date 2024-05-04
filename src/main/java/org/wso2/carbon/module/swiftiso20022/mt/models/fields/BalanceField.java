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
 * Model for Balance Fields(Field 60, Field 62, Field 64, and Field 65).
 */
public class BalanceField {
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

    public String getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setDcMark(String dcMark) {
        this.dcMark = dcMark;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Default constructor for Field28.
     */
    public BalanceField() {}

    /**
     * Constructor method for Balance field.
     * @param tagValue                      Actual tag - 60F, 60M, 62F, 62M, 64, 65
     * @param fieldValue                    Value of the field
     * @throws MTMessageParsingException
     */
    public BalanceField(String tagValue, String fieldValue) throws MTMessageParsingException {
        Matcher balanceMatcher = MT940ParserConstants.MT940_BALANCE_REGEX.matcher(fieldValue);

        if (balanceMatcher.matches()) {
            this.dcMark = balanceMatcher.group(1);
            this.date = balanceMatcher.group(2);
            this.currency = balanceMatcher.group(3);
            this.amount = balanceMatcher.group(4);
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT, tagValue));
        }
    }
}
