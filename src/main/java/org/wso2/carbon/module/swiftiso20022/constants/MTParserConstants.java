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
    public static final Pattern CURLY_BRACKETS_FIELDS_MATCHING_PATTERN =
            Pattern.compile("\\{([0-9A-Z]{3}):([^}:]*)}");
    public static final Pattern FIELD_103_REGEX_PATTERN = Pattern.compile("^[A-Z]{3}$");
    public static final Pattern FIELD_106_REGEX_PATTERN =
            Pattern.compile("^([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$");
    public static final Pattern FIELD_108_REGEX_PATTERN = Pattern.compile("^[\\da-zA-Z/\\-?:().,'+ ]{16}$");
    public static final Pattern FIELD_111_REGEX_PATTERN = Pattern.compile("^\\d{3}$");
    public static final Pattern FIELD_113_REGEX_PATTERN = Pattern.compile("^[\\da-zA-Z/\\-?:().,'+ ]{4}$");
    public static final Pattern FIELD_115_REGEX_PATTERN = Pattern.compile("^([0-9]{6})([0-9]{6})([A-Z]{2})(.{1,16})$");
    public static final Pattern FIELD_119_REGEX_PATTERN = Pattern.compile("^[0-9A-Z]{1,8}$");
    public static final Pattern FIELD_121_REGEX_PATTERN = Pattern.compile("^[0-9a-z\\-]{36}$");
    public static final Pattern FIELD_165_REGEX_PATTERN = Pattern.compile("^/([0-9A-Z]{3})/([^/]{1,34})$");
    public static final Pattern FIELD_423_REGEX_PATTERN = Pattern.compile("^([0-9]{6})([0-9]{6}|[0-9]{8})$");
    public static final Pattern FIELD_424_REGEX_PATTERN = Pattern.compile("^[\\da-zA-Z/\\-?:().,'+ ]{1,16}$");
    public static final Pattern FIELD_433_REGEX_PATTERN = Pattern.compile("^/([A-Z]{3})(/([^/]{1,20}))?$");
    public static final Pattern FIELD_434_REGEX_PATTERN = Pattern.compile("^/([A-Z]{3})(/([^/]{1,20}))?$");
    public static final Pattern FIELD_CHK_REGEX_PATTERN = Pattern.compile("^[0-9A-F]{12}$");
    public static final Pattern FIELD_MRF_REGEX_PATTERN =
            Pattern.compile("^([0-9]{6})([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$");
    public static final Pattern FIELD_PDE_REGEX_PATTERN =
            Pattern.compile("^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$");
    public static final Pattern FIELD_PDM_REGEX_PATTERN =
            Pattern.compile("^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$");
    public static final Pattern FIELD_SYS_REGEX_PATTERN =
            Pattern.compile("^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$");

    // Error message related constants
    public static final String EMPTY_BLOCK_MESSAGE = "Empty %s cannot be parsed";
    public static final String INVALID_FIELD_IN_BLOCK_MESSAGE = "%s in %s is in invalid format";

}
