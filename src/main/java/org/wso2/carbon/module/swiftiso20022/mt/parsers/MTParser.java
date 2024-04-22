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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.TrailerBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.UserHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field434;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldSYS;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * Parser class with parsing logic for common blocks.
 */
public class MTParser {

    private static final Log log = LogFactory.getLog(MTParser.class);

    /**
     * Method to get MT message object extended from {@link MTMessage} by parsing MT message blocks.
     *
     * @param blocks    A map of block identifier as the key and block as the value
     * @param mtMessage MTMessage model or a class extended from that
     * @throws MTMessageParsingException if any one of the blocks cannot be parsed
     */
    public static void parse(Map<String, String> blocks, MTMessage mtMessage)
            throws MTMessageParsingException {

        if (blocks.containsKey(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)) {
            mtMessage.setBasicHeaderBlock(parserBasicHeaderBlock(
                    blocks.get(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)));
        } else {
            // Basic header block is mandatory for MT messages
            throw new MTMessageParsingException(MTParserConstants.EMPTY_MT_MESSAGE_BLOCKS);
        }

        if (blocks.containsKey(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)) {
            mtMessage.setApplicationHeaderBlock(parseApplicationHeaderBlock(
                    blocks.get(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)
            ));
        }

        log.debug("Parsing User Header Block String");
        mtMessage.setUserHeaderBlock(parseUserHeaderBlock(blocks.get(ConnectorConstants.USER_HEADER_BLOCK_KEY)));

        log.debug("Parsing Trailer Block String");
        mtMessage.setTrailerBlock(parseTrailerBlock(blocks.get(ConnectorConstants.TRAILER_BLOCK_KEY)));

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
     *      <li>(\d{3})? - Obsolescence period</li>
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

    /**
     * Method to parse user header block and return UserHeaderBlock object.
     *
     * @param userHeaderBlockString String containing user header fields
     *                              example: {103:EBA}{119:STP}{424:PQAB1234}
     * @return A {@link UserHeaderBlock} model with values assigned by parsing user header block
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    private static UserHeaderBlock parseUserHeaderBlock(String userHeaderBlockString)
            throws MTMessageParsingException {

        if (userHeaderBlockString == null) {
            return null;
        }

        // extract fields as a map of tag and value
        Map<String, String> fields = MTParserUtils.extractFieldsWithinCurlyBrackets(userHeaderBlockString);

        if (fields.isEmpty()) {

            String errorMessage = String.format(
                    ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.USER_HEADER_BLOCK);
            log.error(errorMessage);
            throw new MTMessageParsingException(errorMessage);
        }

        UserHeaderBlock userHeaderBlock = new UserHeaderBlock();

        for (String key : fields.keySet()) {
            switch (key) {
                case Field103.TAG:
                    userHeaderBlock.setServiceIdentifier(Field103.parse(fields.get(key)));
                    break;
                case Field106.TAG:
                    userHeaderBlock.setMessageInputReference(Field106.parse(fields.get(key)));
                    break;
                case Field108.TAG:
                    userHeaderBlock.setMessageUserReference(Field108.parse(fields.get(key)));
                    break;
                case Field111.TAG:
                    userHeaderBlock.setServiceTypeIdentifier(Field111.parse(fields.get(key)));
                    break;
                case Field113.TAG:
                    userHeaderBlock.setBankingPriority(Field113.parse(fields.get(key)));
                    break;
                case Field115.TAG:
                    userHeaderBlock.setAddresseeInformation(Field115.parse(fields.get(key)));
                    break;
                case Field119.TAG:
                    userHeaderBlock.setValidationFlag(Field119.parse(fields.get(key)));
                    break;
                case Field121.TAG:
                    userHeaderBlock.setEndToEndReference(Field121.parse(fields.get(key)));
                    break;
                case Field165.TAG:
                    userHeaderBlock.setPaymentReleaseInformation(Field165.parse(fields.get(key)));
                    break;
                case Field423.TAG:
                    userHeaderBlock.setBalanceCheckpoint(Field423.parse(fields.get(key)));
                    break;
                case Field424.TAG:
                    userHeaderBlock.setRelatedReference(Field424.parse(fields.get(key)));
                    break;
                case Field433.TAG:
                    userHeaderBlock.setSanctionsScreeningInformation(Field433.parse(fields.get(key)));
                    break;
                case Field434.TAG:
                    userHeaderBlock.setPaymentControlsInformation(Field434.parse(fields.get(key)));
                    break;
                // If tag didn't match any of the above tags, it is not allowed
                default:
                    String errorMessage = String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                            key, ConnectorConstants.USER_HEADER_BLOCK);
                    log.error(errorMessage);
                    throw new MTMessageParsingException(errorMessage);
            }
        }

        return userHeaderBlock;
    }

    /**
     * Method to parse user header block and return UserHeaderBlock object.
     *
     * @param trailerBlockString String containing trailer block fields
     *                           example -> {CHK:123456789ABC}{DLM:}
     * @return A {@link TrailerBlock} model with values assigned by parsing trailer block
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    private static TrailerBlock parseTrailerBlock(String trailerBlockString) throws MTMessageParsingException {

        if (trailerBlockString == null) {
            return null;
        }

        // extract fields as a map of tag and value
        Map<String, String> fields = MTParserUtils.extractFieldsWithinCurlyBrackets(trailerBlockString);

        if (fields.isEmpty()) {

            String errorMessage = String.format(
                    ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TRAILER_BLOCK);
            log.error(errorMessage);
            throw new MTMessageParsingException(errorMessage);
        }

        TrailerBlock trailerBlock = new TrailerBlock();

        for (String key : fields.keySet()) {
            switch (key) {
                case MTParserConstants.FIELD_DLM_TAG:
                    trailerBlock.setDelayedMessage(true);
                    break;
                case MTParserConstants.FIELD_TNG_TAG:
                    trailerBlock.setTestAndTrainingMessage(true);
                    break;
                case FieldCHK.TAG:
                    trailerBlock.setChecksum(FieldCHK.parse(fields.get(key)));
                    break;
                case FieldMRF.TAG:
                    trailerBlock.setMessageReference(FieldMRF.parse(fields.get(key)));
                    break;
                case FieldPDE.TAG:
                    trailerBlock.setPossibleDuplicateEmission(FieldPDE.parse(fields.get(key)));
                    break;
                case FieldPDM.TAG:
                    trailerBlock.setPossibleDuplicateMessage(FieldPDM.parse(fields.get(key)));
                    break;
                case FieldSYS.TAG:
                    trailerBlock.setSystemOriginatedMessage(FieldSYS.parse(fields.get(key)));
                    break;
                default:
                    String errorMessage = String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                            key, ConnectorConstants.TRAILER_BLOCK);
                    log.error(errorMessage);
                    throw new MTMessageParsingException(errorMessage);
            }
        }

        return trailerBlock;
    }

}
