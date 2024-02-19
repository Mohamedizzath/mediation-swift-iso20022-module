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
    public static final String DEBIT = "D";
    public static final String CREDIT = "C";
    public static final String REV_DEBIT = "D";
    public static final String REV_CREDIT = "RC";
    public static final String LINE_BREAK = "\n";

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
    public static final String COLON = ":";
    public static final String SLASH = "/";
    public static final String DOUBLE_SLASH = "//";
    public static final String TRANSACTION_REF = "Transaction Reference Number";
    public static final String RELATED_REF = "Related Reference";
    public static final String ACC_IDENTIFICATION = "Account Identification";
    public static final String STATEMENT_NUMBER = "Statement Number";
    public static final String OPENING_BALANCE = "Opening Balance";
    public static final String AMOUNT = " Amount";
    public static final String CLOSING_BALANCE = "Closing Balance";
    public static final String CLOSING_AVAIL_BALANCE = "Closing Available Balance";
    public static final String FORWARD_CLOSING_AVAIL_BALANCE = "Forward Available Balance";
    public static final String STATEMENT_LINE = "Statement Line";
    public static final String STATEMENT_NO = "statementNumber";
    public static final String OPENING_BAL = "openingBalance";
    public static final String CLOSING_BAL = "closingBalance";
    public static final String CLOSING_AVAIL_BAL = "closingAvailableBalance";
    public static final String FORWARD_AVAIL_BAL = "forwardAvailableBalance";
    public static final String STATEMENT_LINES = "statementLines";
    public static final String CURRENT_STATEMENT_TYPE = "current";
    public static final String LAST_STATEMENT_TYPE = "last";

    // ISO 20022 Related Constants
    public static final String SOAP_PREFIX = "soapenv";
    public static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String XSD_SCHEMA_CAMT_053_001 = "schema/camt.053.001.11.xsd";
    public static final String XSD_SCHEMA_HEAD_001_001 = "schema/head.001.001.03.xsd";
    public static final String XML_INPUT_BUSINESS_ENV_TAG = "BizMsgEnvlp";
    public static final String XML_INPUT_APPHDR_TAG = "AppHdr";
    public static final String XPATH_APPHDR = "/soapenv:Body/app:AppHdr";
    public static final String XPATH_CAMT_053_APPHDR = "/soapenv:Body/BizMsgEnvlp/app:AppHdr";
    public static final String APPHDR_PREFIX = "app";
    public static final String XML_INPUT_APPHDR_NAMESPACE = "urn:iso:std:iso:20022:tech:xsd:head.001.001.03";
    public static final String XML_INPUT_DOCUMENT_TAG = "Document";
    public static final String XPATH_DOCUMENT_WITH_BUSINESS_HDR = "/soapenv:Body/BizMsgEnvlp/doc:Document";
    public static final String XPATH_DOCUMENT_WITHOUT_BUSINESS_HDR = "/soapenv:Body/doc:Document";
    public static final String DOCUMENT_PREFIX = "doc";
    public static final String XML_INPUT_DOCUMENT_NAMESPACE = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.11";

    //Common Error Constants
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String MISSING_REQUEST_PAYLOAD = "missing_request_payload";
    public static final String INVALID_REQUEST_PAYLOAD = "invalid_request_payload";
    public static final String SERVER_ERROR = "server_error";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";
    public static final String ERROR_MISSING_PAYLOAD = "Missing Request Payload";
    public static final String PROCESSING_ERROR = "Error while processing the request";
    public static final String ERROR_PARAMETER_MISSING = "Missing mandatory parameter %s";
    public static final String ERROR_PARAMETER_LENGTH = "%s should not contain more than %s characters";
    public static final String ERROR_PARAMETER_CONTAINS_SLASH = "%s should not contain slashes";
    public static final String ERROR_PARAMETER_INVALID = "Field %s is invalid";
    public static final String ERROR_CURRENCY_CODE_INVALID = "Currency code in the field %s is not in ISO 4217" +
            " format";
    public static final String ERROR_AMOUNT_NULL = "Amount cannot be null";
    public static final String ERROR_AMOUNT_SIZE_INVALID = "Amount should not be empty";

    // MT940 Error Constants
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

    public static final String ERROR_BLOCK1_INVALID = "Header block 1 is a mandatory parameter for MT940";
    public static final String ERROR_BLOCK2_INVALID = "Header block 2 is a mandatory parameter for MT940";
    public static final String ERROR_ACC_NO_INVALID = "Account Number is not in the correct format";
    public static final String ERROR_REF_INVALID = "Reference is not in the correct format";
    public static final String ERROR_SEQ_NO_INVALID = "Sequence Number is not in the correct format";
    public static final String ERROR_CUS_REF_INVALID = "Customer Reference is not in the correct format";
    public static final String ERROR_TRANS_REF_INVALID = "Transaction Reference is not in the correct format";
    public static final String ERROR_MANDATORY_PARAM_MISSING = "%s is a mandatory parameter for MT940";
    public static final String ERROR_INCORRECT_FORMAT = "%s is not in the correct format";

    public static final String ERROR_BAL_IND_INVALID = "Balance Indicator should be either D or C";
    public static final String ERROR_INVALID_STATEMENT_TYPE = "Invalid statement type found. Accepted values are" +
            " either current or last";
    public static final String ERROR_DATE_TIME_INVALID = "Transaction Date Time is not in the correct format";
    public static final String ERROR_TRANS_TYPE_INVALID = "Transaction Type is not in the correct format";
    public static final String ERROR_FIELD_86 = "Information to Account Owner field should be preceded by " +
            "Statement Line field";
    public static final String ERROR_BALANCES = "The first two characters of the three character currency code in" +
            " fields 60a, 62a, 64 and 65 must be the same for all occurrences of these fields";

    // ISO 20022 Error Constants
    public static final String ERROR_VALIDATING_XML = "XML validation failed";
    public static final String ERROR_INVALID_ISO_BUSINESS_MSG = "XML message is not valid ISO business message";
    public static final String ERROR_INVALID_ISO_HEAD001_XML_MSG = "XML message is not in the ISO20022.head.001 format";
    public static final String ERROR_INVALID_ISO_CAMT053_XML_MSG = "XML message is not in the ISO20022.camt.053 format";
    public static final String ERROR_INVALID_XML_ROOT_TAG = "XML msg should start with BizMsgEnvlp tag or Document tag";
}
