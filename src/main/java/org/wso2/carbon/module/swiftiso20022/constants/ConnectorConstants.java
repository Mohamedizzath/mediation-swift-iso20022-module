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

    //Common Error Constants
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String MISSING_REQUEST_PAYLOAD = "missing_request_payload";
    public static final String INVALID_REQUEST_PAYLOAD = "invalid_request_payload";
    public static final String SERVER_ERROR = "server_error";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";
    public static final String ERROR_MISSING_PAYLOAD = "Missing Request Payload";
    public static final String PROCESSING_ERROR = "Error while processing the request";
    public static final String ERROR_MANDATORY_PARAM_MISSING = "Mandatory parameter %s cannot be null or empty";
    public static final String ERROR_OPTIONAL_PARAM_MISSING = "Optional parameter %s cannot be empty";
    public static final String ERROR_PARAMETER_LENGTH = "%s should not contain more than %s characters";
    public static final String ERROR_NOT_ALPHA = "Parameter %s is not in the alpha format";
    public static final String ERROR_NOT_ALPHA_NUMERIC = "Parameter %s is not in the alphanumeric format";
    public static final String ERROR_NOT_CHARACTER_SET_X = "Parameter %s is not in the MT Character set X format";
    public static final String ERROR_NOT_NUMERIC = "Parameter %s is not in the numeric format";
    public static final String ERROR_CURRENCY_CODE_INVALID = "Currency code in the field %s is not in ISO 4217" +
            " format";
    public static final String ERROR_PARAMETER_MISSING = "Missing mandatory parameter %s";
    public static final String ERROR_DATE_INVALID = "%s is not in the correct format";
    public static final String ERROR_PARAMETER_CONTAINS_SLASH = "%s should not contain slashes";
    public static final String ERROR_PARAMETER_INVALID = "Field %s is invalid";
    public static final String ERROR_AMOUNT_NULL = "Amount cannot be null";
    public static final String ERROR_AMOUNT_SIZE_INVALID = "Amount should not be empty";

    //Common Error Codes
    public static final String ERROR_CODE_MISSING_PARAM = "T0002";
    public static final String ERROR_CODE_INVALID_PARAM = "T0003";

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

    public static final int ACC_IDENTIFICATION_LENGTH = 35;
    public static final int REFERENCE_LENGTH = 16;
    public static final int SEQUENCE_NO_LENGTH = 3;
    public static final int DATE_LENGTH = 6;
    public static final int CURRENCY_LENGTH = 3;
    public static final int AMOUNT_LENGTH = 16;
    public static final int INDICATOR_LENGTH = 1;
    public static final int TRANSACTION_IND_LENGTH = 2;
    public static final int TRANSACTION_TYPE_LENGTH = 4;
    public static final int STATEMENT_NO_LENGTH = 5;
    public static final int BALANCE_LENGTH = 25;

    public static final String ERROR_BAL_IND_INVALID = "Balance Indicator should be either D or C";
    public static final String ERROR_INVALID_STATEMENT_TYPE = "Invalid statement type found. Accepted values are" +
            " either current or last";
    public static final String ERROR_TRANS_TYPE_INVALID = "Transaction Type is not in the correct format";
    public static final String ERROR_FIELD_86 = "Information to Account Owner field should be preceded by " +
            "Statement Line field";
    public static final String ERROR_BALANCES = "The first two characters of the three character currency code in" +
            " fields 60a, 62a, 64 and 65 must be the same for all occurrences of these fields";

}
