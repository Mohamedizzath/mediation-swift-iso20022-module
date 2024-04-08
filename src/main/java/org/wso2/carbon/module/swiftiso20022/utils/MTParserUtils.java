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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for MT message parsing.
 */
public class MTParserUtils {

    /**
     * Method to extract fields from block with fields enclosed in curly brackets.
     * example: "{103:EBA}{119:STP}" -> ["103" -> "EBA", "119" -> "STP"]
     *
     * @param fieldsString Block containing fields enclosed in curly brackets
     * @return Map of fields where tag as the key with the value
     */
    public static Map<String, String> extractFieldsWithinCurlyBrackets(String fieldsString) {

        // map to store fields
        Map<String, String> fields = new HashMap<>();

        // regex pattern initialization for matching curly brackets with fields
        // {tag:value}, regex pattern matches tag and value in separate groups in each match
        // group 1 -> tag   &   group 2 -> value
        Pattern curlyBracketsMatch = Pattern.compile(MTParserConstants.CURLY_BRACKETS_FIELDS_MATCHING_PATTERN);
        Matcher matcher = curlyBracketsMatch.matcher(fieldsString);

        // while there is a match for the pattern
        while (matcher.find()) {

            // putting tag as the key to each relevant value
            fields.put(matcher.group(1), matcher.group(2));

        }

        return fields;
    }

}
