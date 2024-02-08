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
 * Constants for MT103 Message.
 */
public class MT103Constants {

    private MT103Constants() {
        // Private constructor to prevent instantiation
    }

    public static final String MESSAGE_NAME = "MT103";
    public static final String MT103_APPLICATION_ID = "F";
    public static final String MT103_SERVICE_ID = "01";
    public static final String MT103_MESSAGE_TYPE = "103";
    public static final String MT103_STP_VALIDATION_FLAG = "STP";
    public static final String MT103_REMIT_VALIDATION_FLAG = "REMIT";

    // Generic Error Messages
    public static final String ERROR_REPETITION_LENGTH_EXCEED = "Length of %s repetition %s cannot exceed %s";
    public static final String ERROR_EMPTY_ENTITY_OPTION = "Option is mandatory for %s field";
    public static final String ERROR_INVALID_ENTITY_OPTION = "Option of field %s is invalid";
    public static final String ERROR_EMPTY_ENTITY_DETAILS = "Details of field %s cannot be empty";

    // MT103 Text Fields
    public static final String SENDERS_REFERENCE = "Sender's Reference";
    public static final String TIME_INDICATION = "Time Indication";
    public static final String BANK_OPERATION_CODE = "Bank Operation Code";
    public static final String INSTRUCTION_CODE = "Instruction Code";
    public static final String TRANSACTION_TYPE_CODE = "Transaction Type Code";
    public static final String VALUE = "Value";
    public static final String INSTRUCTED_AMOUNT = "Instructed Amount";
    public static final String EXCHANGE_RATE = "Exchange Rate";
    public static final String ORDERING_CUSTOMER = "Ordering Customer";
    public static final String SENDING_INSTITUTION = "Sending Institution";
    public static final String ORDERING_INSTITUTION = "Ordering Institution";
    public static final String SENDERS_CORRESPONDENT = "Sender's Correspondent";
    public static final String RECEIVERS_CORRESPONDENT = "Receiver's Correspondent";
    public static final String THIRD_REIMBURSEMENT_INSTITUTION = "Third Reimbursement Institution";
    public static final String INTERMEDIARY_INSTITUTION = "Intermediary Institution";
    public static final String ACCOUNT_WITH_INSTITUTION = "Account With Institution";
    public static final String BENEFICIARY_CUSTOMER = "Beneficiary Customer";
    public static final String REMITTANCE_INFORMATION = "Remittance Information";
    public static final String DETAILS_OF_CHARGES = "Details of Charges";
    public static final String SENDERS_CHARGES = "Sender's Charges";
    public static final String RECEIVERS_CHARGES = "Receiver's Charges";
    public static final String SENDER_TO_RECEIVER_INFORMATION = "Sender to Receiver Information";
    public static final String REGULATORY_REPORTING = "Regulatory Reporting";
    public static final String ENVELOPE_CONTENTS = "Envelope Contents";
}
