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

import java.util.regex.Pattern;

/**
 * Constants for the MT parsers.
 */
public class MT940ParserConstants {
    private MT940ParserConstants() {
        // Private constructor to prevent instantiation.
    }

    public static final Pattern FIELD_20_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s{1,16})$", MTParserConstants.CHARACTER_SET_X));
    public static final Pattern FIELD_21_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s{1,16})$", MTParserConstants.CHARACTER_SET_X));
    public static final Pattern FIELD_25_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s{1,35})(\\R(%s{6}%s{2}(%s{3})?))?$", MTParserConstants.CHARACTER_SET_X,
                    MTParserConstants.CHARACTER_SET_A, MTParserConstants.CHARACTER_SET_C,
                    MTParserConstants.CHARACTER_SET_C));
    public static final Pattern FIELD_28_REGEX_PATTERN = Pattern.compile("^(\\d{1,5})(/(\\d{1,5}))?$");
    public static final Pattern MT940_BALANCE_REGEX = Pattern.compile(String.format(
            "^(D|C)(%s)(%s)(%s)$", MTParserConstants.YYMMDD_DATE_REGEX, MTParserConstants.CURRENCY_REGEX,
            MTParserConstants.AMOUNT_REGEX));
    public static final Pattern FIELD_61_REGEX_PATTERN = Pattern.compile(String.format(
            "^(%s)(%s)?(C|D|RC|RD)(%s)?((?!0+,0*$)(?=[\\d,]{2,15}[A-Z])\\d+,\\d*)(%s)(%s{3})(%s{1,34})(\\R(%s{1,34}))?$"
            , MTParserConstants.YYMMDD_DATE_REGEX, MTParserConstants.HHMM_TIME_REGEX, MTParserConstants.CHARACTER_SET_A,
            MTParserConstants.CHARACTER_SET_A, MTParserConstants.CHARACTER_SET_C, MTParserConstants.CHARACTER_SET_X,
            MTParserConstants.CHARACTER_SET_X));

    public static final Pattern FIELD_61_REFTOACCOWNER_REGEX = Pattern.compile(String.format(
            "^(%s{1,16})$", MTParserConstants.CHARACTER_SET_X));
    public static final Pattern FIELD_61_REFTOACCSERVICEINSTITUTION_REGEX = Pattern.compile(String.format(
            "^(%s{1,16})$", MTParserConstants.CHARACTER_SET_X));
    public static final String FIELD_61_REFS_DIVIDER = "//";

    public static final String FIELD_86_NO_CODE = "#ADDITIONAL-INFO#";
    public static final String FIELD_86_END_TO_END_IDENTIFICATION = "EREF";
    public static final String FIELD_86_INSTRUCTION_ID = "IREF";
    public static final String FIELD_86_PAYMENT_INFO_ID = "PREF";
    public static final Pattern FIELD_86_REGEX_PATTERN = Pattern.compile(String.format(
            "^(%s{1,65}(\\R%s{1,64}){0,5})$", MTParserConstants.CHARACTER_SET_X, MTParserConstants.CHARACTER_SET_X));

    // MT parsing JSON Keys
    public static final String TRANSACTION_REFERENCE_NUMBER_JSON_KEY = "transactionReferenceNumber";
    public static final String RELATED_REFERENCE_NUMBER_JSON_KEY = "relatedReference";
    public static final String ACCOUNT_IDENTIFICATION_JSON_KEY = "accountIdentification";
    public static final String STATEMENT_LINES_JSON_KEY = "statementLines";
    public static final String FIELD_61_JSON_KEY = "Field61";
    public static final String FIELD_86_JSON_KEY = "Field86";
    public static final String INFO_TO_ACCOUNT_OWNER = "infoToAccountOwner";
    public static final String INPUT_TIME_JSON_KEY = "inputTime";
    public static final String MESSAGE_INPUT_REFERENCE_JSON_KEY = "messageInputReference";
    public static final String CREATE_DATE_DT_JSON_KEY = "createdDt";
    public static final String ENTRY_DATE_JSON_KEY = "entryDate";
    public static final String ENTRY_DATE_DT_JSON_KEY = "entryDateDt";
    public static final String VALUE_DATE_DT_JSON_KEY = "valueDateDt";
    public static final String VALUE_DATE_JSON_KEY = "valueDate";
}
