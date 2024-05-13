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

import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

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
     * @param dcMark              DCMark of the BalanceField
     * @param date                Date of the BalanceField
     * @param currency            Currency of the BalanceField
     * @param amount              Amount of the Balance
     * @throws MTMessageParsingException
     */
    public BalanceField(String dcMark, String date, String currency, String amount) throws MTMessageParsingException {
            this.dcMark = dcMark;
            this.date = date;
            this.currency = currency;
            this.amount = amount;
    }
}
