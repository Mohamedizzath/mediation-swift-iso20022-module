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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * Util class for MT message parser.
 */
public class MTParserUtils {
    /**
     * Util methods for parsing blocks in MT messages.
     * @param mtMessage     Complete MT messages as string
     * @return              Blocks stored in Map with key as block name(basic-header-block, application-header-block...)
     */
    public static Map<String, String> getMessageBlocks(String mtMessage) throws MTMessageParsingException {
        List<String> blocks = new ArrayList<>();

        int start = 0, curr = 0;
        Stack<Character> stack = new Stack<>();

        while (curr < mtMessage.length()) {
            if (mtMessage.charAt(curr) == ConnectorConstants.BLOCK_STARTING_CHARACTER) {
                // If the character is { and stack is empty that means starting of new block
                if (stack.isEmpty()) {
                    start = curr;
                }

                // If the character is { and stack is not empty that means nested fields. Ex-user header block
                stack.push(mtMessage.charAt(curr));
            } else if (mtMessage.charAt(curr) == ConnectorConstants.BLOCK_ENDING_CHARACTER && !stack.isEmpty()) {
                // If the character is } and stack top element is { means this is block or field
                // Removing the { from the stack
                stack.pop();

                // If stack is empty that means block is found
                if (stack.isEmpty()) {
                    blocks.add(mtMessage.substring(start, curr + 1));
                }
            } else if (mtMessage.charAt(curr) == ConnectorConstants.BLOCK_ENDING_CHARACTER && stack.isEmpty()) {
                // Unbalanced } characters
                throw new MTMessageParsingException(ConnectorConstants.ERROR_INCORRECT_BLOCKS_FORMAT);
            }

            curr++;
        }

        if (!stack.isEmpty()) {
            // Unbalanced { characters
            throw new MTMessageParsingException(ConnectorConstants.ERROR_INCORRECT_BLOCKS_FORMAT);
        }

        Map<String, String> blocksMap = new HashMap<>();

        for (String block : blocks) {
            if (block.startsWith(ConnectorConstants.BASIC_HEADER_BLOCK_START) && block.length() > 3) {
                blocksMap.put(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, block.substring(3, block.length() - 1));
            } else if (block.startsWith(ConnectorConstants.APPLICATION_HEADER_BLOCK_START) && block.length() > 3) {
                blocksMap.put(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY, block.substring(3, block.length() - 1));
            } else if (block.startsWith(ConnectorConstants.USER_HEADER_BLOCK_START) && block.length() > 3) {
                blocksMap.put(ConnectorConstants.USER_HEADER_BLOCK_KEY, block.substring(3, block.length() - 1));
            } else if (block.startsWith(ConnectorConstants.TEXT_BLOCK_START) && block.length() > 5) {
                blocksMap.put(ConnectorConstants.TEXT_BLOCK_KEY, block.substring(3, block.length() - 2));
            } else if (block.startsWith(ConnectorConstants.TRAILER_BLOCK_START) && block.length() > 3) {
                blocksMap.put(ConnectorConstants.TRAILER_BLOCK_KEY, block.substring(3, block.length() - 1));
            }
        }

        return blocksMap;
    }

    /**
     * Get the substring from the given string till the count is completed. If the index is not existed
     * the string will return till end.
     * @param text      String which need to get substring
     * @param start     Start index of the substring
     * @param end       End index of the substring
     * @return          Substring according to the length
     */
    public static String extractSubstring(String text, int start, int end) {
        int curr = start;
        while (curr < end && curr < text.length()) {
            curr++;
        }

        return text.substring(start, curr);
    }

    /**
     * Get the substring from the start till alphabetic character found. If alphabetic character present
     * before completing the length the substring will return till alphabetic character.
     * (Length won't equal to given length).
     * @param text       String which you need to process
     * @param length     Length of the substring
     * @return           Substring according to the length
     */
    public static String extractTillAlphabetic(String text, int length) {
        int curr = 0;

        while (curr < length && curr < text.length()) {
            if (Character.isAlphabetic(text.charAt(curr))) {
                return text.substring(0, curr);
            }

            curr++;
        }

        return text.substring(0, curr);
    }

    /**
     * Get the substring from given string till alphabetic character found.
     * @param text      String which you need to process
     * @return          Substring which present in the given text
     */
    public static String extractTillAlphabetic(String text) {
        return extractTillAlphabetic(text, text.length());
    }

    /**
     * Get the substring from the start till digit character found. If digit character present
     * before completing the length the substring will return till digit character
     * (Length won't equal to given length).
     * @param text       String which you need to process
     * @param length     Length of the substring
     * @return           Substring according to the length
     */
    public static String extractTillDigit(String text, int length) {
        int curr = 0;

        while (curr < length && curr < text.length()) {
            if (Character.isDigit(text.charAt(curr))) {
                return text.substring(0, curr);
            }

            curr++;
        }

        return text.substring(0, curr);
    }

    /**
     * Get the substring from given string till digit character found.
     * @param text      String which you need to process
     * @return          Substring which present in the given text
     */
    public static String extractTillDigit(String text) {
        return extractTillDigit(text, text.length());
    }
}
