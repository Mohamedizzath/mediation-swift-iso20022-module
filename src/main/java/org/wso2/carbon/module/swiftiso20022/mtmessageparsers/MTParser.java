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

package org.wso2.carbon.module.swiftiso20022.mtmessageparsers;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Parser class for parsing common blocks in MT messages
 */
public class MTParser {
    /**
     * Parser method for parsing basic header block into BasicHeaderBlock object
     * @param block       Basic header block as a String
     * @return            Constructed BasicHeaderBlock object
     */
    private static BasicHeaderBlock parserBasicHeaderBlock(String block) {
        BasicHeaderBlock basicHeaderBlock = new BasicHeaderBlock();

        basicHeaderBlock.setApplicationIdentifier(block.substring(0, 1));
        basicHeaderBlock.setServiceIdentifier(block.substring(1, 3));
        basicHeaderBlock.setLogicalTerminalAddress(block.substring(3, 15));
        basicHeaderBlock.setSessionNumber(block.substring(15, 19));
        basicHeaderBlock.setSequenceNumber(block.substring(19, 25));

        return basicHeaderBlock;
    }

    /**
     * Parser method for parsing mt message and constructing MTMessage with common fields
     * @param mtMessage         MT message as String
     * @param messageType       Required MT message format
     * @return                  Constructed MTMessage object
     * @param <T>               Type of MT message
     * @throws Exception
     */
    public static <T extends MTMessage> T parse(Map<String, String> blocks, Class<T> messageType) throws Exception {
        T message;

        try {
            message = messageType.getConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            // Handle the exceptions
            throw new Exception();
        }

        message.setBasicHeaderBlock(parserBasicHeaderBlock(blocks.get(ConnectorConstants.BASIC_HEADER_BLOCK_CODE)));

        return message;
    }
}
