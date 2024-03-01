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

package org.wso2.carbon.module.swiftiso20022.models.mt940models;

import java.util.List;

/**
 * Class to hold the request payload.
 */
public class RequestPayloadModel {

    private String block1;
    private String block2;
    private String block3;
    private String accountNumber;
    private String accountNumberIdentifier;
    private BalanceModel openingBalanceDetails;
    private BalanceModel closingBalanceDetails;
    private BalanceModel closingAvailableBalanceDetails;
    private BalanceModel forwardAvailableBalanceDetails;
    private String reference;
    private String sequenceNumber;
    private List<TransactionModel> transactions;

    public void setBlock1(String block1) {
        this.block1 = block1;
    }

    public String getBlock1() {
        return this.block1;
    }

    public void setBlock2(String block2) {
        this.block2 = block2;
    }

    public String getBlock2() {
        return this.block2;
    }

    public void setBlock3(String block3) {
        this.block3 = block3;
    }

    public String getBlock3() {
        return this.block3;
    }

    public String getUserHeader() {
        return this.block3;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumberIdentifier(String accountNumberIdentifier) {
        this.accountNumberIdentifier = accountNumberIdentifier;
    }

    public String getAccountNumberIdentifier() {
        return accountNumberIdentifier;
    }

    public void setOpeningBalanceDetails(BalanceModel openingBalanceDetails) {
        this.openingBalanceDetails = openingBalanceDetails;
    }

    public BalanceModel getOpeningBalanceDetails() {
        return this.openingBalanceDetails;
    }

    public void setClosingBalanceDetails(BalanceModel closingBalanceDetails) {
        this.closingBalanceDetails = closingBalanceDetails;
    }

    public BalanceModel getClosingBalanceDetails() {
        return this.closingBalanceDetails;
    }

    public void setClosingAvailableBalanceDetails(BalanceModel closingAvailableBalanceDetails) {
        this.closingAvailableBalanceDetails = closingAvailableBalanceDetails;
    }

    public BalanceModel getClosingAvailableBalanceDetails() {
        return this.closingAvailableBalanceDetails;
    }

    public void setForwardAvailableBalanceDetails(BalanceModel forwardAvailableBalanceDetails) {
        this.forwardAvailableBalanceDetails = forwardAvailableBalanceDetails;
    }

    public BalanceModel getForwardAvailableBalanceDetails() {
        return this.forwardAvailableBalanceDetails;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return this.reference;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setTransactions(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionModel> getTransactions() {
        return this.transactions;
    }
}
