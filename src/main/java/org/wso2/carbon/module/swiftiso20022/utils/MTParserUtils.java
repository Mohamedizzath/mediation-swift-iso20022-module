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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for MT message parsing.
 */
public class MTParserUtils {

    /**
     * Method to extract fields from block with fields enclosed in curly brackets.
     * example: "{103:EBA}{119:STP}" -> ["103:EBA", "119:STP"]
     *
     * @param fieldsString Block containing fields enclosed in curly brackets
     * @return List of fields in appearing order.
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    public static List<String> extractFieldsWithinCurlyBrackets(String fieldsString) throws MTMessageParsingException {

        // stack to store opening curly brackets matches using matching index
        Stack<Integer> curlyBracketsStack = new Stack<>();

        // list to store fields
        List<String> fields = new ArrayList<>();

        // regex pattern initialization for matching curly brackets -> [{}]
        Pattern curlyBracketsMatch = Pattern.compile(ConnectorConstants.CURLY_BRACKETS_MATCHING_PATTERN);
        Matcher matcher = curlyBracketsMatch.matcher(fieldsString);

        // while there is a match for the pattern
        while (matcher.find()) {

            // index of the match
            int matchIndex = matcher.start();

            // if the match is "{"
            if (fieldsString.charAt(matchIndex) == ConnectorConstants.OPENING_CURLY_BRACKET) {

                // if the stack is empty, push the index to the stack
                if (curlyBracketsStack.empty()) {
                    curlyBracketsStack.push(matchIndex);
                } else {

                    // there cannot be 2 consecutive "{" matches
                    throw new MTMessageParsingException(ConnectorConstants.ERROR_INCORRECT_FORMAT);
                }

            } else {

                // else the match is "}"
                // if the stack is empty, there is an unmatched closing curly bracket
                if (curlyBracketsStack.empty()) {
                    throw new MTMessageParsingException(ConnectorConstants.ERROR_INCORRECT_FORMAT);
                } else {

                    // else pop the opening curly bracket matching index
                    int startIndex = curlyBracketsStack.pop();

                    // add string between curly brackets to the field string
                    fields.add(fieldsString.substring(++startIndex, matchIndex));
                }
            }
        }

        // after iterating through all matches, if the stack is not empty there is an unmatched opening curly bracket
        if (!curlyBracketsStack.empty()) {
            throw new MTMessageParsingException(ConnectorConstants.ERROR_INCORRECT_FORMAT);
        }

        return fields;
    }
}
