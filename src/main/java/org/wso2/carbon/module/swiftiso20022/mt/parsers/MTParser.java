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
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.mtmessages.MTMessage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class for parsing common blocks in MT messages.
 */
public class MTParser {

    /**
     * Parser method for parsing mt message and constructing MTMessage with common fields.
     * @param blocks            Blocks in MT message as Map
     * @param message           MT message object
     */
    public static void parse(Map<String, String> blocks, MTMessage message) throws MTMessageParsingException {

        if (blocks.containsKey(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)) {
            message.setBasicHeaderBlock(parserBasicHeaderBlock(blocks.get(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)));
        } else {
            // Basic header block is mandatory for MT messages
            throw new MTMessageParsingException(MTParserConstants.INVALID_MT_MESSAGE_BLOCKS);
        }

        if (blocks.containsKey(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)) {
            message.setApplicationHeaderBlock(parseApplicationHeaderBlock(
                    blocks.get(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)
            ));
        }
    }

    /**
     * Parser method for parsing basic header block into BasicHeaderBlock object.
     * <br/> <a href="https://www.paiementor.com/swift-mt-message-block-1-basic-header-description/">Reference</a>
     * <br/>Regex explanation - ^(F|A|L)(\d{2})([A-Z0-9]{12})(\d{4})(\d{6})$
     *    <ol>
     *      <li>(F|A|L) - Application identifier</li>
     *      <li>(\d{2}) - Service identifier</li>
     *      <li>([A-Z0-9]{12}) - Logical terminal address</li>
     *      <li>(\d{4}) - Session number</li>
     *      <li>(\d{6}) - Sequence number</li>
     *    </ol>
     * @param block       Basic header block as a String
     * @return            Constructed BasicHeaderBlock object
     */
    private static BasicHeaderBlock parserBasicHeaderBlock(String block) throws MTMessageParsingException {
        BasicHeaderBlock basicHeaderBlock = new BasicHeaderBlock();

        Pattern basicHeaderPattern = Pattern.compile(MTParserConstants.BASIC_HEADER_REGEX);
        Matcher basicHeaderMatcher = basicHeaderPattern.matcher(block);

        if (!basicHeaderMatcher.matches()) {
            // Throw mt message parsing exception
            throw new MTMessageParsingException(MTParserConstants.INVALID_BASIC_HEADER);
        }

        String applicationIdentifier = basicHeaderMatcher.group(1);
        String serviceIdentifier = basicHeaderMatcher.group(2);
        String logicalTerminalAddress = basicHeaderMatcher.group(3);
        String sessionNumber = basicHeaderMatcher.group(4);
        String sequenceNumber = basicHeaderMatcher.group(5);

        basicHeaderBlock.setApplicationIdentifier(applicationIdentifier);
        basicHeaderBlock.setServiceIdentifier(serviceIdentifier);
        basicHeaderBlock.setLogicalTerminalAddress(logicalTerminalAddress);
        basicHeaderBlock.setSessionNumber(sessionNumber);
        basicHeaderBlock.setSequenceNumber(sequenceNumber);

        return basicHeaderBlock;
    }

    /**
     * Parser method for parsing application header block into ApplicationHeaderBlock object.
     * <br /><a href="https://www.paiementor.com/swift-mt-message-block-2-application-header-description/">Reference</a>
     * <br/>Regex explanation for input message) - ^I(\d{3})([A-Z0-9]{12})(S|U|N|)(\d?)(\d{3}|)$
     *    <ol>
     *      <li>I - Input/Output identifier</li>
     *      <li>(\d{3}) - Message type</li>
     *      <li>([A-Z0-9]{12}) - Logical terminal address</li>
     *      <li>(S|U|N|) - Priority</li>
     *      <li>(\d?) - Delivery monitor</li>
     *      <li>(\d{3}|) - Obsolecene period</li>
     *    </ol>
     *<br/>Regex explanation for output message-^O(\d{3})(\d{4})(\d{6}[A-Z0-9]{12}[0-9]{4}[0-9]{6})(\d{6}|)(\d{4}|)(N|)$
     *    <ol>
     *        <li>O - Input/Output identifier</li>
     *        <li>(\d{3}) - Message type</li>
     *        <li>(\d{4}) - Input time</li>
     *        <li>(\d{6}[A-Z0-9]{12}[0-9]{4}[0-9]{6}) - Message input reference</li>
     *        <li>(\d{6}|) - Output date</li>
     *        <li>(\d{4}|) - Output time</li>
     *        <li>(N|) - Priority</li>
     *    </ol>
     * @param block         Application header block as a String
     * @return              Constructed ApplicationHeaderBlock object
     */
    private static ApplicationHeaderBlock parseApplicationHeaderBlock(String block) throws MTMessageParsingException {
        ApplicationHeaderBlock applicationHeaderBlock = new ApplicationHeaderBlock();

        Pattern inputOutputPattern = Pattern.compile(MTParserConstants.INPUT_OUTPUT_IDENTIFIER_REGEX);
        Matcher inputOutputMatcher = inputOutputPattern.matcher(block);

        if (!inputOutputMatcher.matches()) {
            // Throw mt message parsing exception
            throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
        }

        String inputOutputId = inputOutputMatcher.group(1);
        applicationHeaderBlock.setInputOutputIdentifier(inputOutputId);

        if (ConnectorConstants.INPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to input message
            Pattern inputMsgPattern = Pattern.compile(MTParserConstants.INPUT_APPLICATION_HEADER_REGEX);
            Matcher inputMsgMatcher = inputMsgPattern.matcher(block);

            if (!inputMsgMatcher.matches()) {
                // Throw mt message parsing exception
                throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
            }

            String messageType = inputMsgMatcher.group(1);
            String destinationAddress = inputMsgMatcher.group(2);

            String priority = !StringUtils.isEmpty(inputMsgMatcher.group(3)) ? inputMsgMatcher.group(3) : null;

            String deliveryMonitor = !StringUtils.isEmpty(inputMsgMatcher.group(4)) ? inputMsgMatcher.group(4) : null;
            String obsolenscenePeriod =
                    !StringUtils.isEmpty(inputMsgMatcher.group(5)) ? inputMsgMatcher.group(5) : null;

            applicationHeaderBlock.setMessageType(messageType);
            applicationHeaderBlock.setDestinationAddress(destinationAddress);
            applicationHeaderBlock.setPriority(priority);
            applicationHeaderBlock.setDeliveryMonitor(deliveryMonitor);
            applicationHeaderBlock.setObsolescencePeriod(obsolenscenePeriod);
        } else if (ConnectorConstants.OUTPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to output message
            Pattern outputMsgPattern = Pattern.compile(MTParserConstants.OUTPUT_APPLICATION_HEADER_REGEX);
            Matcher outputMsgMatcher = outputMsgPattern.matcher(block);

            if (!outputMsgMatcher.matches()) {
                // Throw mt message parsing exception
                throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
            }

            String messageType = outputMsgMatcher.group(1);
            String inputTime = outputMsgMatcher.group(2);
            String messageInputReference = outputMsgMatcher.group(3);
            String outputDate = !StringUtils.isEmpty(outputMsgMatcher.group(4)) ? outputMsgMatcher.group(4) : null;
            String outputTime = !StringUtils.isEmpty(outputMsgMatcher.group(5)) ? outputMsgMatcher.group(5) : null;
            String priority = !StringUtils.isEmpty(outputMsgMatcher.group(6)) ? outputMsgMatcher.group(6) : null;

            applicationHeaderBlock.setMessageType(messageType);
            applicationHeaderBlock.setInputTime(inputTime);
            applicationHeaderBlock.setMessageInputReference(messageInputReference);
            applicationHeaderBlock.setOutputDate(outputDate);
            applicationHeaderBlock.setOutputTime(outputTime);
            applicationHeaderBlock.setPriority(priority);
        }

        return applicationHeaderBlock;
    }
}
