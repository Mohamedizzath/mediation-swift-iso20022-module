/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.constants;

/**
 * Constants for the connector.
 */
public class ConnectorConstants {

    private ConnectorConstants() {
        // Private constructor to prevent instantiation.
    }

    public static final String APPLICATION_JSON = "application/json";
    public static final String DATE_TIME_FORMAT = "yyMMdd";

    public static final String MT_REGEX_PATTERN = "[0-9a-zA-Z*#+.,()/-?:'-]+";
    public static final String NUMBER_REGEX_PATTERN = "[0-9]+";
    public static final String AMOUNT_REGEX_PATTERN = "^\\d{1,3}(,\\d{3})*(\\.\\d+)?$";
    public static final String DEBIT = "D";
    public static final String CREDIT = "C";
    public static final String REV_DEBIT = "D";
    public static final String REV_CREDIT = "RC";
    public static final String LINE_BREAK = "\n";
    public static final String SPACE = " ";

    public static final String COLON = ":";
    public static final String SLASH = "/";
    public static final String DOUBLE_SLASH = "//";
    public static final char OPENING_CURLY_BRACKET = '{';
    public static final char CLOSING_CURLY_BRACKET = '}';
    public static final String ENUM_KEY = "enumClassName";
    public static final String FIXED_VALUE_KEY = "fixedValue";
    public static final String USER_HEADER_BLOCK_KEY = "3:";
    public static final String TRAILER_BLOCK_KEY = "5:";

    // MT message block names
    public static final String USER_HEADER_BLOCK = "User Header Block";
    public static final String TRAILER_BLOCK = "Trailer Block";

    // Block01 (Basic Header) Related Constants
    public static final String BLOCK01_APPLICATION_ID = "Application Identifier";
    public static final String BLOCK01_SERVICE_ID = "Service Identifier";
    public static final String BLOCK01_LOGICAL_TERMINAL_ADDRESS = "Logical Terminal Address";
    public static final String BLOCK01_SESSION_NUMBER = "Session Number";
    public static final String BLOCK01_SEQUENCE_NUMBER = "Sequence Number";

    // Block02 (Application Header) Related Constants
    public static final String BLOCK02_INPUT_OUTPUT_ID = "Input Output Identifier";
    public static final String BLOCK02_INPUT_ID = "I";
    public static final String BLOCK02_OUTPUT_ID = "O";
    public static final String BLOCK02_MESSAGE_TYPE = "Message Type";
    public static final String BLOCK02_DESTINATION_LOGICAL_TERMINAL_ADDRESS = "Destination Logical Terminal Address";
    public static final String BLOCK02_INPUT_TIME = "Input Time";
    public static final String BLOCK02_MESSAGE_INPUT_REFERENCE = "Message Input Reference";
    public static final String BLOCK02_OUTPUT_DATE = "Output Date";
    public static final String BLOCK02_OUTPUT_TIME = "Output Time";
    public static final String BLOCK02_PRIORITY = "Priority";
    public static final String BLOCK02_DELIVERY_MONITORING_CODE = "Delivery Monitoring Code";
    public static final String BLOCK02_OBSOLESCENCE_PERIOD_CODE = "Obsolescence Period Code";

    // Block03 (User Header) Related Constants
    public static final String BLOCK03_SERVICE_IDENTIFIER = "Service Identifier";
    public static final String BLOCK03_BANKING_PRIORITY = "Banking Priority";
    public static final String BLOCK03_MESSAGE_USER_REFERENCE = "Message User Reference";
    public static final String BLOCK03_VALIDATION_FLAG = "Validation Flag";
    public static final String BLOCK03_END_TO_END_REFERENCE = "End to End Reference";
    public static final String BLOCK03_SERVICE_TYPE_IDENTIFIER = "Service Type Identifier";
    public static final String BLOCK03_MESSAGE_INPUT_REFERENCE = "Message Input Reference";
    public static final String BLOCK03_ADDRESSEE_INFORMATION = "Addressee Information";
    public static final String BLOCK03_PAYMENT_RELEASE_INFORMATION = "Payment Release Information";
    public static final String BLOCK03_BALANCE_CHECKPOINT = "Balance Checkpoint";
    public static final String BLOCK03_RELATED_REFERENCE = "Related Reference";
    public static final String BLOCK03_SANCTIONS_SCREENING_INFORMATION = "Related Reference";
    public static final String BLOCK03_PAYMENT_CONTROLS_INFORMATION = "Payment Controls Information";

    // Block05 (Trailer) Related Constants
    public static final String BLOCK05_CHECKSUM = "Checksum";
    public static final String BLOCK05_POSSIBLE_DUPLICATE_EMISSION = "Possible Duplicate Emission";
    public static final String BLOCK05_MESSAGE_REFERENCE = "Message Reference";
    public static final String BLOCK05_POSSIBLE_DUPLICATE_MESSAGE = "Possible Duplicate Message";
    public static final String BLOCK05_SYSTEM_ORIGINATED_MESSAGE = "System Originated Message";
    public static final String BLOCK05_DELAYED_MESSAGE = "Delayed Message";
    public static final String BLOCK05_TEST_AND_TRAINING_MESSAGE = "Test And Training Message";

    // ISO 20022 Related Constants
    public static final String SOAP_PREFIX = "soapenv";
    public static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String XSD_SCHEMA_CAMT_053_001 = "schema/camt.053.001.11.xsd";
    public static final String XSD_SCHEMA_HEAD_001_001 = "schema/head.001.001.03.xsd";
    public static final String XML_INPUT_BUSINESS_ENV_TAG = "BizMsgEnvlp";
    public static final String XPATH_APPHDR = "/app:AppHdr";
    public static final String APPHDR_PREFIX = "app";
    public static final String XML_INPUT_APPHDR_NAMESPACE = "urn:iso:std:iso:20022:tech:xsd:head.001.001.03";
    public static final String XML_INPUT_DOCUMENT_TAG = "Document";
    public static final String XPATH_DOCUMENT = "/doc:Document";
    public static final String DOCUMENT_PREFIX = "doc";
    public static final String XML_INPUT_DOCUMENT_NAMESPACE = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.11";
    public static final String XPATH_BUSINESS_MESSAGE_START = "/soapenv:Body/BizMsgEnvlp";
    public static final String XPATH_CAMT_MESSAGE_START = "/soapenv:Body";
    public static final String XPATH_ELECTSEQ_NUMBER = "/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:ElctrncSeqNb";
    public static final String XPATH_LEGALSEQ_NUMBER = "/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:LglSeqNb";
    public static final String XPATH_BALANCE_ELEMENTS =
            "/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd";
    public static final String OPENING_BALANCE_CODE = "OPBD";
    public static final String CLOSING_BALANCE_CODE = "CLBD";

    //Common Error Constants
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String MISSING_REQUEST_PAYLOAD = "missing_request_payload";
    public static final String INVALID_REQUEST_PAYLOAD = "invalid_request_payload";
    public static final String SERVER_ERROR = "server_error";
    public static final String RUNTIME_ERROR = "runtime_error";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";
    public static final String ERROR_MISSING_PAYLOAD = "Missing Request Payload";
    public static final String ERROR_INVALID_PAYLOAD = "Invalid Request Payload";
    public static final String PROCESSING_ERROR = "Error while processing the request";
    public static final String ERROR_MANDATORY_BLOCK_MISSING = "Mandatory block %s cannot be null";
    public static final String ERROR_MANDATORY_PARAM_MISSING = "Mandatory parameter %s cannot be null or empty";
    public static final String ERROR_OPTIONAL_PARAM_MISSING = "Optional parameter %s cannot be empty";
    public static final String ERROR_PARAMETER_LENGTH = "%s should not contain more than %s characters";
    public static final String ERROR_REPETITION_LENGTH =
            "Repetition %s of %s should not contain more than %s characters";
    public static final String ERROR_LINE_LENGTH =
            "Line %s of %s should not contain more than %s characters";
    public static final String ERROR_NOT_ALPHA = "Parameter %s is not in the alpha format";
    public static final String ERROR_NOT_ALPHA_NUMERIC = "Parameter %s is not in the alphanumeric format";
    public static final String ERROR_NOT_CHARACTER_SET_X = "Parameter %s is not in the MT Character set X format";
    public static final String ERROR_NOT_NUMERIC = "Parameter %s is not in the numeric format";
    public static final String ERROR_CURRENCY_CODE_INVALID = "Currency code in the field %s is not in ISO 4217" +
            " format";
    public static final String ERROR_PARAMETER_MISSING = "Missing mandatory parameter %s";
    public static final String ERROR_PARAMETER_EMPTY = "Parameter %s is empty";
    public static final String ERROR_REPETITION_EMPTY = "Repetition %s of parameter %s is empty";
    public static final String ERROR_LINE_EMPTY = "Line %s of parameter %s is empty";
    public static final String ERROR_DATE_INVALID = "%s is not in the correct format";
    public static final String ERROR_PARAMETER_CONTAINS_SLASH = "%s should not contain slashes";
    public static final String ERROR_PARAMETER_INVALID = "Field %s is invalid";
    public static final String ERROR_REPETITION_INVALID = "Repetition %s of %s is invalid";
    public static final String ERROR_LINE_INVALID = "Line %s of %s is invalid";
    public static final String ERROR_AMOUNT_NULL = "Amount cannot be null";
    public static final String ERROR_AMOUNT_SIZE_INVALID = "Amount should not be empty";
    public static final String ERROR_LINE_COUNT = "Field %s should not contain more than %s lines";
    public static final String ERROR_INCORRECT_FORMAT = "%s is incorrectly formatted";
    public static final String ERROR_INVALID_FIELD_AND_VALUE = "\"%s\" in %s is invalid";
    public static final String ERROR_FIELD_REPEATED = "%s in %s cannot repeated";
    public static final String ERROR_FIELD_INVALID_IN_BLOCK = "Value of field %s in %s is invalid";
    public static final String ERROR_FIELD_NOT_ALLOWED_IN_BLOCK = "%s field is not allowed in %s";
    public static final String ERROR_111_BEFORE_121 =
            "Service Type Identifier cannot appear before field End To End Reference";
    public static final String ERROR_BLOCK_INVALID_FORMAT = "%s format is invalid";

    //Common Error Codes
    public static final String ERROR_CODE_MISSING_BLOCK = "missing_block";
    public static final String ERROR_CODE_MISSING_PARAM = "missing_parameter";
    public static final String ERROR_CODE_INVALID_PARAM = "invalid_parameter";

    // MT  Error Constants
    public static final String ERROR_C03 = "C03";
    public static final String ERROR_C24 = "C24";
    public static final String ERROR_C27 = "C27";
    public static final String ERROR_H01 = "H01";
    public static final String ERROR_H25 = "H25";
    public static final String ERROR_T18 = "T18";
    public static final String ERROR_T13 = "T13";
    public static final String ERROR_T26 = "T26";
    public static final String ERROR_T40 = "T40";
    public static final String ERROR_T50 = "T50";
    public static final String ERROR_T51 = "T51";
    public static final String ERROR_T52 = "T52";
    public static final String ERROR_T53 = "T53";
    public static final String ERROR_M50 = "M50";

    // User Header Block Error Codes
    public static final String ERROR_U00 = "U00";
    public static final String ERROR_U03 = "U03";
    public static final String ERROR_U12 = "U12";

    // Trailer Block Error Codes
    public static final String ERROR_Z00 = "Z00";

    public static final int ACC_IDENTIFICATION_LENGTH = 35;
    public static final int REFERENCE_LENGTH = 16;
    public static final int SEQUENCE_NO_LENGTH = 3;
    public static final int DATE_LENGTH = 6;
    public static final int TIME_LENGTH = 4;
    public static final int HHMMSS_TIME_LENGTH = 6;
    public static final int HHMMSSSS_TIME_LENGTH = 8;
    public static final int CURRENCY_LENGTH = 3;
    public static final int AMOUNT_LENGTH = 16;
    public static final int INDICATOR_LENGTH = 1;
    public static final int TRANSACTION_IND_LENGTH = 2;
    public static final int TRANSACTION_TYPE_LENGTH = 4;
    public static final int STATEMENT_NO_LENGTH = 5;
    public static final int BALANCE_LENGTH = 25;
    public static final int COUNTRY_CODE_LENGTH = 2;
    public static final int THREE_CHARACTER_CODE_LENGTH = 3;
    public static final int LOGICAL_TERMINAL_ADDRESS_LENGTH = 12;
    public static final int SESSION_NUMBER_LENGTH = 4;
    public static final int SEQUENCE_NUMBER_LENGTH = 6;
    public static final int BLOCK02_PRIORITY_LENGTH = 1;
    public static final int BLOCK02_DELIVERY_MONITORING_CODE_LENGTH = 1;
    public static final int BLOCK02_OBSOLESCENCE_PERIOD_LENGTH = 3;
    public static final int BLOCK02_MESSAGE_INPUT_REFERENCE_LENGTH = 28;
    public static final int BLOCK03_SERVICE_IDENTIFIER_LENGTH = 3;
    public static final int BLOCK03_BANKING_PRIORITY_LENGTH = 4;
    public static final int MESSAGE_USER_REFERENCE_LENGTH = 16;
    public static final int END_TO_END_REFERENCE_LENGTH = 36;
    public static final int BLOCK03_SERVICE_TYPE_IDENTIFIER_LENGTH = 3;
    public static final int CHECKSUM_LENGTH = 12;
    public static final int MESSAGE_REFERENCE_LENGTH_WITH_DATE = 38;
    public static final int MESSAGE_REFERENCE_LENGTH = 32;

    public static final String ERROR_BAL_IND_INVALID = "Balance Indicator should be either D or C";
    public static final String ERROR_INVALID_STATEMENT_TYPE = "Invalid statement type found. Accepted values are" +
            " either current or last";
    public static final String ERROR_TRANS_TYPE_INVALID = "Transaction Type is not in the correct format";
    public static final String ERROR_FIELD_86 = "Information to Account Owner field should be preceded by " +
            "Statement Line field";
    public static final String ERROR_BALANCES = "The first two characters of the three character currency code in" +
            " fields 60a, 62a, 64 and 65 must be the same for all occurrences of these fields";

    // ISO 20022 Error Constants
    public static final String ERROR_VALIDATING_XML = "validation_failed";
    public static final String ERROR_VALIDATING_HEAD_XML_MSG = "Error occurred in validating ISO20022.head.001 XML";
    public static final String ERROR_VALIDATING_CAMT_XML_MSG = "Error occurred in validating ISO20022.camt.053 XML";
    public static final String ERROR_INVALID_ISO_HEAD001_XML_MSG = "invalid_head_001_payload";
    public static final String ERROR_INVALID_ISO_CAMT053_XML_MSG = "invalid_camt_053_payload";
    public static final String ERROR_INVALID_XML_ROOT_TAG = "invalid_root_tag";
    public static final String ERROR_INVALID_XML_ROOT_TAG_MESSAGE = "Invalid ISO XML root tag element";
    public static final String ERROR_MISSING_ELECTRONIC_SEQUENCE_NUMBER =
            "Electronic Sequence number not present in ISO20022.camt.053 message";
    public static final String ERROR_MISSING_LEGAL_SEQUENCE_NUMBER =
            "Legal Sequence number not present in ISO20022.camt.053 message";
    public static final String ERROR_MISSING_OPENING_BALANCE = "Missing Opening Balance in ISO20022.camt.053 message";
    public static final String ERROR_MISSING_CLOSING_BALANCE = "Missing Closing Balance in ISO20022.camt.053 message";

}
