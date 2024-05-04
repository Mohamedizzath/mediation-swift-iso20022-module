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
            "^([a-zA-Z0-9/\\-?:().,'+ ]{1,16})$");
    public static final Pattern FIELD_21_REGEX_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9/\\-?:().,'+ ]{1,16})$");
    public static final Pattern FIELD_25_REGEX_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9/\\-?:().,'+ ]{1,35})(\\R([A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?))?$");
    public static final Pattern FIELD_28_REGEX_PATTERN = Pattern.compile("^(\\d{1,5})(/(\\d{1,5}))?$");
    public static final Pattern MT940_BALANCE_REGEX = Pattern.compile(
            "^(D|C)([0-9]{6})([A-Z]{3})([\\d,]{1,15})$");
    public static final Pattern FIELD_61_REGEX_PATTERN = Pattern.compile("^(\\d{6})(\\d{4})?(C|D|RC|RD)([A-Z])?" +
            "([\\d,]{1,15})([A-Z])([A-Z0-9]{3})([a-zA-Z0-9/\\-?:().,'+ ]{1,34})" +
            "(\\R([a-zA-Z0-9/\\-?:().,'+ ]{1,34}))?$");

    public static final Pattern FIELD_61_REFTOACCOWNER_REGEX = Pattern.compile("^([a-zA-Z0-9/\\-?:().,'+ ]{1,16})$");
    public static final Pattern FIELD_61_REFTOACCSERVICEINSTITUTION_REGEX = Pattern.compile(
            "^([a-zA-Z0-9/\\-?:().,'+ ]{1,16})$");
    public static final String FIELD_61_REFS_DIVIDER = "//";

    public static final Pattern FIELD_86_REGEX_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9/\\-?:().,'+ ]{1,65}" +
                    "(\\R[a-zA-Z0-9/\\-?:().,'+ ]{1,65}){0,5})$");
}
