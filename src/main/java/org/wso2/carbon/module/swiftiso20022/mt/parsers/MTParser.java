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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.mtmessages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;

/**
 * Parser class for parsing common blocks in MT messages.
 */
public class MTParser {

    private static final Log log = LogFactory.getLog(MTParser.class);

    /**
     * Parser method for parsing basic header block into BasicHeaderBlock object.
     * @param block       Basic header block as a String
     * @return            Constructed BasicHeaderBlock object
     */
    private static BasicHeaderBlock parserBasicHeaderBlock(String block) {
        BasicHeaderBlock basicHeaderBlock = new BasicHeaderBlock();

        String applicationIdentifier = (!block.isEmpty()) ? MTParserUtils.extractSubstring(block, 0, 1) : null;
        String serviceIdentifier = (block.length() > 1) ? MTParserUtils.extractSubstring(block, 1, 3) : null;
        String logicalTerminalAddress = (block.length() > 3) ? MTParserUtils.extractSubstring(block, 3, 15) : null;
        String sessionNumber = (block.length() > 15) ? MTParserUtils.extractSubstring(block, 15, 19) : null;
        String sequenceNumber = (block.length() > 19) ? MTParserUtils.extractSubstring(block, 19, 25) : null;

        basicHeaderBlock.setApplicationIdentifier(applicationIdentifier);
        basicHeaderBlock.setServiceIdentifier(serviceIdentifier);
        basicHeaderBlock.setLogicalTerminalAddress(logicalTerminalAddress);
        basicHeaderBlock.setSessionNumber(sessionNumber);
        basicHeaderBlock.setSequenceNumber(sequenceNumber);

        return basicHeaderBlock;
    }

    /**
     * Parser method for parsing application header block into ApplicationHeaderBlock object.
     * @param block         Application header block as a String
     * @return              Constructed ApplicationHeaderBlock object
     */
    private static ApplicationHeaderBlock parseApplicationHeaderBlock(String block) {
        ApplicationHeaderBlock applicationHeaderBlock = new ApplicationHeaderBlock();

        String inputOutputId = (!block.isEmpty()) ? MTParserUtils.extractSubstring(block, 0, 1) : null;
        String messageType = (block.length() > 1) ? MTParserUtils.extractSubstring(block, 1, 4) : null;

        applicationHeaderBlock.setInputOutputIdentifier(inputOutputId);
        applicationHeaderBlock.setMessageType(messageType);

        if (ConnectorConstants.INPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to input message
            String destinationAddress = (block.length() > 5) ? MTParserUtils.extractSubstring(block, 4, 16) : null;
            String alphaText = (block.length() > 17) ?
                    MTParserUtils.extractTillDigit(block.substring(16), 1) : null;
            String priority = (!StringUtils.isBlank(alphaText)) ? alphaText.substring(0, 1) : null;

            // Consuming the blocks according to the optional fields
            if (block.length() > 16) {
                block = MTParserUtils.extractSubstring(block, (priority != null) ? 17 : 16, block.length());
            }

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
        } else if (ConnectorConstants.OUTPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to output message
            String inputTime = (block.length() > 4) ? MTParserUtils.extractSubstring(block, 4, 8) : null;
            String messageInputReference = (block.length() > 8) ? MTParserUtils.extractSubstring(block, 8, 36) : null;
            String outputDate = (block.length() > 36) ? MTParserUtils.extractSubstring(block, 36, 42) : null;
            String outputTime = (block.length() > 42) ? MTParserUtils.extractSubstring(block, 42, 46) : null;

            String alphaBlock = (block.length() > 46) ?
                    MTParserUtils.extractTillDigit(block.substring(46)) : null;

            String priority = null;
            if (!StringUtils.isBlank(alphaBlock)) {
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
     * Parser method for parsing mt message and constructing MTMessage with common fields.
     * @param blocks            Blocks in MT message as Map
     * @param message           MT message object
     */
    public static void parse(Map<String, String> blocks, MTMessage message) {

        if (blocks.containsKey(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)) {
            message.setBasicHeaderBlock(parserBasicHeaderBlock(blocks.get(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)));
        }

        if (blocks.containsKey(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)) {
            message.setApplicationHeaderBlock(parseApplicationHeaderBlock(
                    blocks.get(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)
            ));
        }
    }
}
