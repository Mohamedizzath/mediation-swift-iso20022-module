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

import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.ApplicationHeaderBlock;
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

        String applicationIdentifier = (!block.isEmpty()) ? block.substring(0, 1) : null;
        String serviceIdentifier = (block.length() > 1) ? block.substring(1, 3) : null;
        String logicalTerminalAddress = (block.length() > 3) ? block.substring(3, 15) : null;
        String sessionNumber = (block.length() > 15) ? block.substring(15, 19) : null;
        String sequenceNumber = (block.length() > 19) ? block.substring(19, 25) : null;

        basicHeaderBlock.setApplicationIdentifier(applicationIdentifier);
        basicHeaderBlock.setServiceIdentifier(serviceIdentifier);
        basicHeaderBlock.setLogicalTerminalAddress(logicalTerminalAddress);
        basicHeaderBlock.setSessionNumber(sessionNumber);
        basicHeaderBlock.setSequenceNumber(sequenceNumber);

        return basicHeaderBlock;
    }

    /**
     * Parser method for parsing application header block into ApplicationHeaderBlock object
     * @param block         Application header block as a String
     * @return              Constructed ApplicationHeaderBlock object
     */
    private static ApplicationHeaderBlock parseApplicationHeaderBlock(String block) {
        ApplicationHeaderBlock applicationHeaderBlock = new ApplicationHeaderBlock();

        String inputOutputId = (!block.isEmpty()) ? block.substring(0, 1) : null;
        String messageType = (block.length() > 1) ? block.substring(1, 4) : null;

        applicationHeaderBlock.setInputOutputIdentifier(inputOutputId);
        applicationHeaderBlock.setMessageType(messageType);

        if ("I".equals(inputOutputId)) {
            // Entered application block is belong to input message
            String destinationAddress = (block.length() > 4) ? block.substring(4, 16) : null;
            String priority = (block.length() > 16) ?
                    MTParserUtils.extractTillDigit(block.substring(16), 1) : null;

            // Consuming the blocks according to the optional fields
            block = (priority != null && StringUtils.isBlank(priority)) ?
                    block.substring(16, priority.length()) : block;

            String digitBlock = (!block.isEmpty()) ?
                    MTParserUtils.extractTillAlphabetic(block, 4) : null;

            String deliveryMonitor = null, obsolenscenePeriod = null;

            if (digitBlock != null && digitBlock.length() == 4) {
                deliveryMonitor = digitBlock.substring(0, 1);
                obsolenscenePeriod = digitBlock.substring(1, 4);
            } else if (digitBlock != null && digitBlock.length() == 3) {
                obsolenscenePeriod = digitBlock.substring(0, 3);
            } else if (digitBlock != null && digitBlock.length() == 1) {
                deliveryMonitor = digitBlock.substring(0, 1);
            }

            applicationHeaderBlock.setDestinationAddress(destinationAddress);
            applicationHeaderBlock.setPriority(priority);
            applicationHeaderBlock.setDeliveryMonitor(deliveryMonitor);
            applicationHeaderBlock.setObsolescencePeriod(obsolenscenePeriod);
        } else if ("O".equals(inputOutputId)) {
            // Entered application block is belong to output message
            String inputTime = (block.length() > 4) ? block.substring(4, 8) : null;
            String messageInputReference = (block.length() > 8) ? block.substring(8, 36) : null;
            String outputDate = (block.length() > 36) ? block.substring(36, 42) : null;
            String outputTime = (block.length() > 42) ? block.substring(42, 46) : null;

            String alphaBlock = (block.length() > 42) ?
                    MTParserUtils.extractTillDigit(block.substring(42)) : null;

            String priority = null;
            if(alphaBlock != null && alphaBlock.length() == 1) {
                priority = alphaBlock.substring(0, 1);
            }

            applicationHeaderBlock.setInputTime(inputTime);
            applicationHeaderBlock.setMessageInputReference(messageInputReference);
            applicationHeaderBlock.setOutputDate(outputDate);
            applicationHeaderBlock.setOutputTime(outputTime);
            applicationHeaderBlock.setPriority(priority);
        }

        return applicationHeaderBlock;
    }

    /**
     * Parser method for parsing mt message and constructing MTMessage with common fields
     * @param blocks            Blocks in MT message as Map
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

        if (blocks.containsKey(ConnectorConstants.BASIC_HEADER_BLOCK_CODE)) {
            message.setBasicHeaderBlock(parserBasicHeaderBlock(blocks.get(ConnectorConstants.BASIC_HEADER_BLOCK_CODE)));
        }

        if (blocks.containsKey(ConnectorConstants.APPLICATION_HEADER_BLOCK_CODE)) {
            message.setApplicationHeaderBlock(parseApplicationHeaderBlock(
                    blocks.get(ConnectorConstants.APPLICATION_HEADER_BLOCK_CODE)
            ));
        }
        return message;
    }
}
