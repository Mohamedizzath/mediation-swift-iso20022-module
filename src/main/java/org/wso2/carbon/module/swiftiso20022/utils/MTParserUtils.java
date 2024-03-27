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

import java.util.*;

/**
 * Util class for MT message parser
 */
public class MTParserUtils {
    public static Map<String, String>  getMessageBlocks(String mtMessage) {
        List<String> blocks = new ArrayList<>();

        int start = 0, curr = 0;
        Stack<Character> stack = new Stack<>();

        while(curr < mtMessage.length()) {
            if (mtMessage.charAt(curr) == '{') {
                stack.push(mtMessage.charAt(curr));
                start = curr;
            } else if (mtMessage.charAt(curr) == '}' && stack.peek() == '{') {
                stack.pop();

                if(stack.isEmpty()) {
                    blocks.add(mtMessage.substring(start, curr + 1));
                }
            }

            curr++;
        }

        Map<String, String> blocksMap = new HashMap<>();

        for (String block : blocks) {
            if (block.startsWith("{1:")) {
                blocksMap.put(ConnectorConstants.BASIC_HEADER_BLOCK_CODE, block.substring(3, block.length() - 1));
            } else if (block.startsWith("{2:")) {
                blocksMap.put(ConnectorConstants.APPLICATION_HEADER_BLOCK_CODE, block.substring(3, block.length() - 1));
            } else if (block.startsWith("{3:")) {
                blocksMap.put(ConnectorConstants.USER_HEADER_BLOCK_CODE, block.substring(3, block.length() - 1));
            } else if (block.startsWith("{4:")) {
                blocksMap.put(ConnectorConstants.TEXT_BLOCK_CODE, block.substring(3, block.length() - 2));
            } else if (block.startsWith("{5:")) {
                blocksMap.put(ConnectorConstants.TRAILER_BLOCK_CODE, block.substring(3, block.length() - 1));
            }
        }

        return blocksMap;
    }
}
