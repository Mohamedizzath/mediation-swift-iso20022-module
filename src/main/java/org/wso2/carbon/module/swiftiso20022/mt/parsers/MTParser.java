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
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MTMessage;

import java.util.Map;
import java.util.regex.Matcher;

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
            throw new MTMessageParsingException(MTParserConstants.EMPTY_MT_MESSAGE_BLOCKS);
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
        Matcher basicHeaderMatcher = MTParserConstants.BASIC_HEADER_REGEX.matcher(block);

        if (!basicHeaderMatcher.matches()) {
            throw new MTMessageParsingException(MTParserConstants.INVALID_BASIC_HEADER);
        }

        BasicHeaderBlock basicHeaderBlock = new BasicHeaderBlock();

        basicHeaderBlock.setApplicationIdentifier(basicHeaderMatcher.group(1));
        basicHeaderBlock.setServiceIdentifier(basicHeaderMatcher.group(2));
        basicHeaderBlock.setLogicalTerminalAddress(basicHeaderMatcher.group(3));
        basicHeaderBlock.setSessionNumber(basicHeaderMatcher.group(4));
        basicHeaderBlock.setSequenceNumber(basicHeaderMatcher.group(5));

        return basicHeaderBlock;
    }

    /**
     * Parser method for parsing application header block into ApplicationHeaderBlock object.
     * <br /><a href="https://www.paiementor.com/swift-mt-message-block-2-application-header-description/">Reference</a>
     * <br/>Regex explanation for input message) - I(\d{3})([A-Z0-9]{12})(S|U|N)?(\d)?(\d{3})?
     *    <ol>
     *      <li>I - Input/Output identifier</li>
     *      <li>(\d{3}) - Message type</li>
     *      <li>([A-Z0-9]{12}) - Logical terminal address</li>
     *      <li>(S|U|N|) - Priority</li>
     *      <li>(\d)? - Delivery monitor</li>
     *      <li>(\d{3})? - Obsolecene period</li>
     *    </ol>
     *<br/>Regex explanation for output message-^O(\d{3})(\d{4})(\d{6}[A-Z0-9]{12}[0-9]{10})(\d{6})?(\d{4})?(S|U|N)?$
     *    <ol>
     *        <li>O - Input/Output identifier</li>
     *        <li>(\d{3}) - Message type</li>
     *        <li>(\d{4}) - Input time</li>
     *        <li>(\d{6}[A-Z0-9]{12}[0-9]{10}) - Message input reference</li>
     *        <li>(\d{6})? - Output date</li>
     *        <li>(\d{4})? - Output time</li>
     *        <li>(S|U|N)? - Priority</li>
     *    </ol>
     * @param block         Application header block as a String
     * @return              Constructed ApplicationHeaderBlock object
     */
    private static ApplicationHeaderBlock parseApplicationHeaderBlock(String block) throws MTMessageParsingException {
        if (StringUtils.isEmpty(block) || (!block.startsWith(ConnectorConstants.INPUT_IDENTIFIER)
                && !block.startsWith(ConnectorConstants.OUTPUT_IDENTIFIER))) {
            throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
        }

        ApplicationHeaderBlock applicationHeaderBlock = new ApplicationHeaderBlock();

        String inputOutputId = block.substring(0, 1);
        applicationHeaderBlock.setInputOutputIdentifier(inputOutputId);

        if (ConnectorConstants.INPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to input message
            Matcher inputMsgMatcher = MTParserConstants.INPUT_APPLICATION_HEADER_REGEX.matcher(block);

            if (!inputMsgMatcher.matches()) {
                throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
            }

            applicationHeaderBlock.setMessageType(inputMsgMatcher.group(1));
            applicationHeaderBlock.setDestinationAddress(inputMsgMatcher.group(2));
            applicationHeaderBlock.setPriority(inputMsgMatcher.group(3));
            applicationHeaderBlock.setDeliveryMonitor(inputMsgMatcher.group(4));
            applicationHeaderBlock.setObsolescencePeriod(inputMsgMatcher.group(5));
        } else if (ConnectorConstants.OUTPUT_IDENTIFIER.equals(inputOutputId)) {
            // Entered application block is belong to output message
            Matcher outputMsgMatcher = MTParserConstants.OUTPUT_APPLICATION_HEADER_REGEX.matcher(block);

            if (!outputMsgMatcher.matches()) {
                throw new MTMessageParsingException(MTParserConstants.INVALID_APPLICATION_HEADER);
            }

            applicationHeaderBlock.setMessageType(outputMsgMatcher.group(1));
            applicationHeaderBlock.setInputTime(outputMsgMatcher.group(2));
            applicationHeaderBlock.setMessageInputReference(outputMsgMatcher.group(3));
            applicationHeaderBlock.setOutputDate(outputMsgMatcher.group(4));
            applicationHeaderBlock.setOutputTime(outputMsgMatcher.group(5));
            applicationHeaderBlock.setPriority(outputMsgMatcher.group(6));
        }

        return applicationHeaderBlock;
    }
}
