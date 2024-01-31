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

package org.wso2.carbon.module.swiftiso20022.mt940models;

/**
 * Class to hold the Statement Line details.
 */
public class StatementLineModel {
    private String valueDate;
    private String entryDate;
    private String transactionIndicator;
    private String fundCode;
    private String amount;
    private String transactionType;
    private String customerReference;
    private String bankReference;
    private String supplementaryData;
    private String information;

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueDate() {
        return this.valueDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryDate() {
        return this.entryDate;
    }

    public void setTransactionIndicator(String transactionIndicator) {
        this.transactionIndicator = transactionIndicator;
    }

    public String getTransactionIndicator() {
        return this.transactionIndicator;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundCode() {
        return this.fundCode;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getCustomerReference() {
        return this.customerReference;
    }

    public void setBankReference(String bankReference) {
        this.bankReference = bankReference;
    }

    public String getBankReference() {
        return this.bankReference;
    }

    public void setSupplementaryData(String supplementaryData) {
        this.supplementaryData = supplementaryData;
    }

    public String getSupplementaryData() {
        return this.supplementaryData;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return this.information;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.valueDate);
        sb.append(this.entryDate);
        sb.append(this.transactionIndicator);
        sb.append(this.fundCode);
        sb.append(this.amount);
        sb.append(this.transactionType);
        sb.append(this.customerReference);
        sb.append(this.bankReference);
        if (this.supplementaryData != null) {
            sb.append(this.supplementaryData);
        }
        if (this.information != null) {
            sb.append(this.information);
        }

        return sb.toString();
    }
}
