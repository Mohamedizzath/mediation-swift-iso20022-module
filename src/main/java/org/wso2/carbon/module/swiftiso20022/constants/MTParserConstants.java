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
public class MTParserConstants {

    private MTParserConstants() {
        // Private constructor to prevent instantiation.
    }

    // Common patterns
    public static final String CHARACTER_SET_A = "[A-Z]";
    public static final String CHARACTER_SET_C = "[\\dA-Z]";
    public static final String CHARACTER_SET_H = "[\\dA-F]";
    public static final String CHARACTER_SET_X = "[\\da-zA-Z/\\-?:().,'+ ]";
    public static final String CHARACTER_SET_Z = "[\\w.,\\-()/='+:?!\"%&*<>;{@# \r\n]";
    public static final String AMOUNT_REGEX = "(?!0+,0*$)(?=[\\d,]{2,15}$)\\d+,\\d*";
    public static final String YYMMDD_DATE_REGEX = "\\d{6}";
    public static final String HHMM_TIME_REGEX = "\\d{4}";
    public static final String HHMMSS_TIME_REGEX = "\\d{6}";
    public static final String HHMMSSSS_TIME_REGEX = "\\d{6}(\\d{2})?";
    public static final String CURRENCY_REGEX = "[A-Z]{3}";
    public static final String LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN =
            "[A-Z]{4}[A-Z]{2}[\\dA-Z]{2}[\\dA-Z][\\dA-Z]{3}";
    public static final String SESSION_NUMBER_REGEX = "\\d{4}";
    public static final String SEQUENCE_NUMBER_REGEX = "\\d{6}";

    public static final Pattern MT_MESSAGE_BLOCKS_REGEX = Pattern.compile(
            "^(\\{1:([^\\W_]+)\\})(\\{2:([^\\W_]+)\\})?(\\{3:(\\{\\d{3}:[^\\{\\}]*\\})+\\})?" +
                    "(\\{4:[^\\{\\}]+\\R-\\})(\\{5:(\\{[A-Z]{3}:[^\\{\\}]*\\})+\\})?$", Pattern.DOTALL);
    public static final Pattern BASIC_HEADER_REGEX = Pattern.compile(
            "^(F|A|L)(\\d{2})([A-Z0-9]{12})(\\d{4})(\\d{6})$");
    public static final Pattern INPUT_APPLICATION_HEADER_REGEX = Pattern.compile(
            "^I(\\d{3})([A-Z0-9]{12})(S|U|N)?(\\d)?(\\d{3})?$");
    public static final Pattern OUTPUT_APPLICATION_HEADER_REGEX = Pattern.compile(
            "^O(\\d{3})(\\d{4})(\\d{6}[A-Z0-9]{12}[0-9]{10})(\\d{6})?(\\d{4})?(S|U|N)?$");

    public static final String INVALID_MT_MESSAGE_BLOCKS = "MT message blocks are not in the correct format";
    public static final String INVALID_BASIC_HEADER = "Basic header block is not in the correct format";
    public static final String EMPTY_MT_MESSAGE_BLOCKS = "Basic header block is not present in MT message";
    public static final String INVALID_APPLICATION_HEADER = "Application header block is not in the correct format";

    public static final String FIELD_DLM_TAG = "DLM";
    public static final String FIELD_TNG_TAG = "TNG";

    // Regex patterns for MT parser and utils

    public static final String LINE_BREAK_REGEX_PATTERN = "\\R";
    public static final String LINE_BREAK_WITH_COLON_REGEX_PATTERN = "\\R:";
    public static final Pattern CURLY_BRACKETS_FIELDS_MATCHING_PATTERN =
            Pattern.compile("\\{([0-9A-Z]{3}):([^}]*)}");

    // User Header Block Regex Patterns
    public static final Pattern FIELD_103_REGEX_PATTERN = Pattern.compile(String.format("^%s{3}$", CHARACTER_SET_A));
    public static final Pattern FIELD_106_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s)(%s)(%s)(%s)$", YYMMDD_DATE_REGEX, LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN,
                    SESSION_NUMBER_REGEX, SEQUENCE_NUMBER_REGEX));
    public static final Pattern FIELD_108_REGEX_PATTERN = Pattern.compile(String.format("^%s{16}$", CHARACTER_SET_X));
    public static final Pattern FIELD_111_REGEX_PATTERN = Pattern.compile("^\\d{3}$");
    public static final Pattern FIELD_113_REGEX_PATTERN = Pattern.compile(String.format("^%s{4}$", CHARACTER_SET_X));
    public static final Pattern FIELD_115_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s)(%s)([A-Z]{2})(%s{1,16})$", HHMMSS_TIME_REGEX, HHMMSS_TIME_REGEX, CHARACTER_SET_X));
    public static final Pattern FIELD_119_REGEX_PATTERN = Pattern.compile(String.format("^%s{1,8}$", CHARACTER_SET_C));
    public static final Pattern FIELD_121_REGEX_PATTERN = Pattern.compile("^[0-9a-z\\-]{36}$");
    public static final Pattern FIELD_165_REGEX_PATTERN = Pattern.compile(
            String.format("^/(%s{3})/(%s{1,34})$", CHARACTER_SET_C, CHARACTER_SET_X));
    public static final Pattern FIELD_423_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s)(%s)$", YYMMDD_DATE_REGEX, HHMMSSSS_TIME_REGEX));
    public static final Pattern FIELD_424_REGEX_PATTERN = Pattern.compile(String.format("^%s{1,16}$", CHARACTER_SET_X));
    public static final Pattern FIELD_433_REGEX_PATTERN = Pattern.compile(
            String.format("^/(%s{3})(/(%s{1,20}))?$", CHARACTER_SET_A, CHARACTER_SET_X));
    public static final Pattern FIELD_434_REGEX_PATTERN = Pattern.compile(
            String.format("^/(%s{3})(/(%s{1,20}))?$", CHARACTER_SET_A, CHARACTER_SET_X));

    // Trailer Block Regex Patterns
    public static final Pattern FIELD_CHK_REGEX_PATTERN = Pattern.compile(String.format("^%s{12}$", CHARACTER_SET_H));
    public static final Pattern FIELD_MRF_REGEX_PATTERN = Pattern.compile(String.format("^(%s)(%s)(%s)(%s)(%s)(%s)$",
            YYMMDD_DATE_REGEX, HHMM_TIME_REGEX, YYMMDD_DATE_REGEX, LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN,
            SESSION_NUMBER_REGEX, SEQUENCE_NUMBER_REGEX));
    public static final Pattern FIELD_PDE_REGEX_PATTERN = Pattern.compile(String.format("^(%s)(%s)(%s)(%s)(%s)$",
            HHMM_TIME_REGEX, YYMMDD_DATE_REGEX, LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN, SESSION_NUMBER_REGEX,
            SEQUENCE_NUMBER_REGEX));
    public static final Pattern FIELD_PDM_REGEX_PATTERN = Pattern.compile(String.format("^(%s)(%s)(%s)(%s)(%s)$",
            HHMM_TIME_REGEX, YYMMDD_DATE_REGEX, LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN, SESSION_NUMBER_REGEX,
            SEQUENCE_NUMBER_REGEX));
    public static final Pattern FIELD_SYS_REGEX_PATTERN = Pattern.compile(String.format("^(%s)(%s)(%s)(%s)(%s)$",
            HHMM_TIME_REGEX, YYMMDD_DATE_REGEX, LOGICAL_TERMINAL_ADDRESS_REGEX_PATTERN, SESSION_NUMBER_REGEX,
            SEQUENCE_NUMBER_REGEX));

    // Text Block Regex Patterns
    public static final Pattern TAG_AND_VALUE_REGEX_PATTERN_TEXT_BLOCK =
            Pattern.compile("^(\\d{2})([A-Z]?):(.*)$", Pattern.DOTALL);
    public static final Pattern FIELD_13_REGEX_PATTERN = Pattern.compile(
            String.format("^/(%s{1,8})/(%s)([+-])(%s)$", CHARACTER_SET_A, HHMM_TIME_REGEX, HHMM_TIME_REGEX));
    public static final Pattern FIELD_20_REGEX_PATTERN = Pattern.compile(String.format("^%s{1,16}$", CHARACTER_SET_X));
    public static final Pattern FIELD_23_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s{4})(/(%s{1,34}))?$", CHARACTER_SET_C, CHARACTER_SET_X));
    public static final Pattern FIELD_26_REGEX_PATTERN = Pattern.compile(String.format("^(%s{3})$", CHARACTER_SET_C));
    public static final Pattern FIELD_32_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s)(%s)(%s)$", YYMMDD_DATE_REGEX, CURRENCY_REGEX, AMOUNT_REGEX));
    public static final Pattern FIELD_33_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s)(%s)$", CURRENCY_REGEX, AMOUNT_REGEX));
    public static final Pattern FIELD_36_REGEX_PATTERN = Pattern.compile("(?!0+,0*$)(?=[\\d,]{2,12}$)\\d+,\\d*");
    public static final Pattern FIELD_50_REGEX_PATTERN =
            Pattern.compile(String.format("^((/%s{1,34}|(%s{4}/%s{2}/%s{1,27}))\\R)?(%s{6}%s{2}(%s{3})?$)?" +
                            "(\\d/%s{1,33}(\\R\\d/%s{1,33}){0,3}$)?(%s{1,35}(\\R%s{1,35}){0,3}$)?", CHARACTER_SET_X,
                    CHARACTER_SET_A, CHARACTER_SET_A, CHARACTER_SET_X, CHARACTER_SET_A, CHARACTER_SET_C,
                    CHARACTER_SET_C, CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_X));
    public static final Pattern FIELD_70_REGEX_PATTERN =
            Pattern.compile(String.format("^%s{1,35}(\\R%s{1,35}){0,3}$", CHARACTER_SET_X, CHARACTER_SET_X));
    public static final Pattern FIELD_71_REGEX_PATTERN = Pattern.compile(
            String.format("^(%s{3}$)|((%s{3})(%s)$)", CHARACTER_SET_A, CHARACTER_SET_A, AMOUNT_REGEX));
    public static final Pattern FIELD_72_REGEX_PATTERN =
            Pattern.compile(String.format("^%s{1,35}(\\R%s{1,35}){0,5}$", CHARACTER_SET_X, CHARACTER_SET_X));
    public static final Pattern FIELD_77_REGEX_PATTERN =
            Pattern.compile(String.format("^(%s{1,35}(\\R%s{1,35}){0,3})|(%s{1,9000})$",
                    CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_Z));

    // Party Identifier Regex Patterns
    public static final Pattern PARTY_IDENTIFIER_REGEX_PATTERN =
            Pattern.compile(String.format("^(((/%s)?(/(%s{2,34}))?)\\R)?(^/(%s{2,34})$)?(%s{6}%s{2}(%s{3})?$)?" +
                            "((%s{1,35})$)?(\\d/%s{1,33}(\\R\\d/%s{1,33}){0,3}$)?(%s{1,35}(\\R%s{1,35}){0,3}$)?",
                    CHARACTER_SET_A, CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_A, CHARACTER_SET_C,
                    CHARACTER_SET_C, CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_X, CHARACTER_SET_X,
                    CHARACTER_SET_X));

    // Error message related constants
    public static final String INVALID_FIELD_IN_BLOCK_MESSAGE = "%s in %s is in invalid format";
    public static final String INVALID_OPTION_FOR_FIELD = "Option %s for %s in Text Block is invalid";

    public static final String INVALID_TEXT_BLOCK = "Text block is not in the correct format";
    public static final Pattern TEXT_BLOCK_FIELD_REGEX = Pattern.compile("^(\\d{2}[A-Z]?):(.*)$", Pattern.DOTALL);
    public static final String INVALID_FIELD_FORMAT = "%s field not in the correct format";
    public static final String INVALID_FIELD_OPTION = "Invalid %s option for field %s";

    // Field options
    public static final char FIELD_OPTION_NO_LETTER = '0';
    public static final char FIELD_OPTION_C = 'C';
    public static final char FIELD_OPTION_F = 'F';
    public static final char FIELD_OPTION_M = 'M';
    public static final char FIELD_OPTION_P = 'P';

    // MT parsing JSON keys
    public static final String BASIC_HEADER_JSON_KEY = "basicHeaderBlock";
    public static final String APPLICATION_HEADER_JSON_KEY = "applicationHeaderBlock";
    public static final String TEXT_BLOCK_JSON_KEY = "textBlock";
    public static final String OPTION_JSON_KEY = "option";
    public static final String AMOUNT_JSON_KEY = "amount";
    public static final String DATE_JSON_KEY = "date";
    public static final String DATE_DT_JSON_KEY = "dateDt";
    public static final String LOGICAL_TERMINAL_ADDRESS_JSON_KEY = "logicalTerminalAddress";
    public static final String TO_BIC_JSON_KEY = "ToBIC";
    public static final String FROM_BIC_JSON_KEY = "FromBIC";
}
