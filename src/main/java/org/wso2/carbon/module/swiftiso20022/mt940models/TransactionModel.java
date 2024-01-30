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
 * Class to hold the transaction details.
 */
public class TransactionModel {
    String dateTime;
    String amount;
    String currency;
    String transactionReference;
    String transactionIndicator;
    String customerReference;
    String transactionType;
    String supplementaryData;
    String information;


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getTransactionReference() {
        return this.transactionReference;
    }

    public void setTransactionIndicator(String transactionIndicator) {
        this.transactionIndicator = transactionIndicator;
    }

    public String getTransactionIndicator() {
        return this.transactionIndicator;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getCustomerReference() {
        return this.customerReference;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void getSupplementaryData(String supplementaryData) {
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
}
