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
 * Constants for the MT parsers.
 */
public class MTParserConstants {

    private MTParserConstants() {
        // Private constructor to prevent instantiation.
    }

    public static final String FIELD_DLM_TAG = "DLM";
    public static final String FIELD_TNG_TAG = "TNG";

    // Regex patterns for MT parser and utils
    public static final String CURLY_BRACKETS_FIELDS_MATCHING_PATTERN = "\\{([0-9A-Z]{3}):([^}:]*)\\}";
    public static final String FIELD_103_REGEX_PATTERN = "^[A-Z]{3}$";
    public static final String FIELD_106_REGEX_PATTERN = "^([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$";
    public static final String FIELD_108_REGEX_PATTERN = "^[\\da-zA-Z\\/\\-?:().,'+ ]{16}$";
    public static final String FIELD_111_REGEX_PATTERN = "^\\d{3}$";
    public static final String FIELD_113_REGEX_PATTERN = "^[\\da-zA-Z\\/\\-?:().,'+ ]{4}$";
    public static final String FIELD_115_REGEX_PATTERN = "^([0-9]{6})([0-9]{6})([A-Z]{2})(.{1,16})$";
    public static final String FIELD_119_REGEX_PATTERN = "^[0-9A-Z]{1,8}$";
    public static final String FIELD_121_REGEX_PATTERN = "^[0-9a-z\\-]{36}$";
    public static final String FIELD_165_REGEX_PATTERN = "^\\/([0-9A-Z]{3})\\/([^\\/]{1,34})$";
    public static final String FIELD_423_REGEX_PATTERN = "^([0-9]{6})([0-9]{6}|[0-9]{8})$";
    public static final String FIELD_424_REGEX_PATTERN = "^[\\da-zA-Z\\/\\-?:().,'+ ]{1,16}$";
    public static final String FIELD_433_REGEX_PATTERN = "^\\/([A-Z]{3})(\\/([^\\/]{1,20})){0,1}$";
    public static final String FIELD_434_REGEX_PATTERN = "^\\/([A-Z]{3})(\\/([^\\/]{1,20})){0,1}$";
    public static final String FIELD_CHK_REGEX_PATTERN = "^[0-9A-F]{12}$";
    public static final String FIELD_MRF_REGEX_PATTERN
            = "^([0-9]{6})([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$";
    public static final String FIELD_PDE_REGEX_PATTERN = "^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$";
    public static final String FIELD_PDM_REGEX_PATTERN = "^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$";
    public static final String FIELD_SYS_REGEX_PATTERN = "^([0-9]{4})([0-9]{6})([0-9A-Z]{12})([0-9]{4})([0-9]{6})$";

    // Error message related constants
    public static final String EMPTY_BLOCK_MESSAGE = "Empty %s cannot be parsed";
    public static final String INVALID_FIELD_IN_BLOCK_MESSAGE = "%s in %s is in invalid format";

}
