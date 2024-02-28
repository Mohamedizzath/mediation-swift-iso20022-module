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

package org.wso2.carbon.module.swiftiso20022.constants;

/**
 * MT 940 Constants for the connector.
 */
public class MT940Constants {

    public static final String SOAP_BODY = "soapenv:Body";
    public static final String TEXT = "text";
    public static final String CONTENT = "content";
    public static final String AXIS2_NS = "axis2ns";

    //MT940 Related Constants
    public static final String MT940_TRANSACTION_REF = ":20";
    public static final String MT940_RELATED_REF = ":21";
    public static final String MT940_ACCOUNT_NO = ":25";
    public static final String MT940_STATEMENT_NO = ":28";
    public static final String MT940_INFORMATION = ":86:";;
    public static final String MT940_STATEMENT_LINE = ":61";
    public static final String MT940_OPENING_BAL = ":60";
    public static final String MT940_CLOSING_BAL = ":62";
    public static final String MT940_CLOSING_AVAIL_BAL = ":64";
    public static final String MT940_FORWARD_AVAIL_BAL = ":65";
    public static final String SWIFT_TRANSFER = "S";
    public static final String NON_SWIFT_TRANSFER = "N";
    public static final String FIRST_ADVICE = "F";
    public static final String CURRENT_STATEMENT_TYPE = "current";
    public static final String LAST_STATEMENT_TYPE = "last";

    //MT940 Constants display names
    public static final String DN_HEADER_BLOCK_1 = "Header block 1";
    public static final String DN_HEADER_BLOCK_2 = "Header block 2";
    public static final String DN_HEADER_BLOCK_3 = "Header block 3";
    public static final String DN_TRANSACTION_REF = "Transaction Reference Number";
    public static final String DN_RELATED_REF = "Related Reference";
    public static final String DN_SEQUENCE_NO = "Sequence Number";
    public static final String DN_ACC_NUMBER = "Account Number";
    public static final String DN_ACC_NUMBER_IDENTIFICATION = "Account Number Identification";
    public static final String DN_ACC_IDENTIFICATION = "Account Identification";
    public static final String DN_STATEMENT_NUMBER = "Statement Number";
    public static final String DN_OPENING_BALANCE = "Opening Balance";
    public static final String DN_TRANSACTION = "Transaction";
    public static final String DN_AMOUNT = " Amount";
    public static final String DN_CURRENCY = " Currency";
    public static final String DN_DATE = " Date";
    public static final String DN_INDICATOR = " Indicator";
    public static final String DN_STATEMENT_TYPE = " Statement Type";
    public static final String DN_CLOSING_BALANCE = "Closing Balance";
    public static final String DN_CLOSING_AVAIL_BALANCE = "Closing Available Balance";
    public static final String DN_FORWARD_CLOSING_AVAIL_BALANCE = "Forward Available Balance";
    public static final String DN_STATEMENT_LINE = "Statement Line";
    public static final String DN_CUSTOMER_REFERENCE = "Customer Reference";
    public static final String DN_TRANSACTION_REFERENCE = "Transaction Reference";
    public static final String DN_TRANSACTION_TYPE = "Transaction Type";
    public static final String DN_TRANSACTION_IND = "Transaction Indicator";

    //MT940 payload fields
    public static final String HEADER_BLOCK_1 = "block1";
    public static final String HEADER_BLOCK_2 = "block2";
    public static final String HEADER_BLOCK_3 = "block3";
    public static final String REFERENCE = "reference";
    public static final String SEQUENCE_NO = "sequenceNumber";
    public static final String ACC_NUMBER = "accountNumber";
    public static final String ACC_NUMBER_IDENTIFICATION = "accountNumberIdentifier";
    public static final String OPENING_BAL_DETAILS = "openingBalanceDetails";
    public static final String CLOSING_BALANCE_DETAILS = "closingBalanceDetails";
    public static final String CLOSING_AVAIL_BALANCE_DETAILS = "closingAvailableBalanceDetails";
    public static final String FORWARD_CLOSING_AVAIL_BALANCE_DETAILS = "forwardAvailableBalanceDetails";
    public static final String TRANSACTIONS = "transactions";
    public static final String BAL_AMOUNT = "balanceAmount";
    public static final String BAL_CURRENCY = "currency";
    public static final String BAL_DATE = "date";
    public static final String BAL_INDICATOR = "indicator";
    public static final String BAL_STATEMENT_TYPE = "statementType";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_CURRENCY = "currency";
    public static final String TRANSACTION_DATE = "dateTime";
    public static final String TRANSACTION_INDICATOR = "transactionIndicator";
    public static final String CUSTOMER_REFERENCE = "customerReference";
    public static final String TRANSACTION_REFERENCE = "transactionReference";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String TRANSACTION = "transaction";


    public static final String RESPONSE_OPENING_BAL = "openingBalance";
    public static final String RESPONSE_CLOSING_BAL = "closingBalance";
    public static final String RESPONSE_CLOSING_AVAIL_BAL = "closingAvailableBalance";
    public static final String RESPONSE_FORWARD_AVAIL_BAL = "forwardAvailableBalance";
    public static final String RESPONSE_STATEMENT_LINES = "statementLines";
    public static final String RESPONSE_STATEMENT_NO = "statementNumber";

}
