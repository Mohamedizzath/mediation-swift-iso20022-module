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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
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
import org.wso2.carbon.module.swiftiso20022.mt.models.mtmessages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTFieldParserUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;

/**
 * Parser class with parsing logic for common blocks.
 */
public class MTParser {

    private static final Log log = LogFactory.getLog(MTParser.class);

    /**
     * Method to get MT message object extended from {@link MTMessage} by parsing MT message blocks.
     *
     * @param blocks    A map of block key and block value string
     * @param mtMessage MTMessage model or a class extended from that
     * @throws MTMessageParsingException if any one of the blocks cannot be parsed
     */
    public static void parse(
            Map<String, String> blocks, MTMessage mtMessage) throws MTMessageParsingException {

        // TODO: Add Basic Header Block and Application Header Block parsing

        log.debug("Parsing User Header Block String");
        mtMessage.setUserHeaderBlock(parseUserHeaderBlock(blocks.get(ConnectorConstants.USER_HEADER_BLOCK_KEY)));

        log.debug("Parsing Trailer Block String");
        mtMessage.setTrailerBlock(parseTrailerBlock(blocks.get(ConnectorConstants.TRAILER_BLOCK_KEY)));

    }


    /**
     * Method to parse user header block and return UserHeaderBlock object.
     *
     * @param userHeaderBlockString String containing user header fields
     *                              example: {103:EBA}{119:STP}{424:PQAB1234}
     * @return A {@link UserHeaderBlock} model with values assigned by parsing user header block
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    private static UserHeaderBlock parseUserHeaderBlock(
            String userHeaderBlockString) throws MTMessageParsingException {

        // if passed string is null, return
        if (userHeaderBlockString == null) {
            return null;
        }

        // block cannot be blank
        if (userHeaderBlockString.isBlank()) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.EMPTY_BLOCK_MESSAGE, ConnectorConstants.USER_HEADER_BLOCK));
        }

        // extract fields as a map of tag and value
        Map<String, String> fields = MTParserUtils.extractFieldsWithinCurlyBrackets(userHeaderBlockString);

        // if no matching fields found -> block cannot be parsed
        if (fields.isEmpty()) {

            log.error(String.format(
                    ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.USER_HEADER_BLOCK));
            throw new MTMessageParsingException(
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.USER_HEADER_BLOCK));
        }

        UserHeaderBlock userHeaderBlock = new UserHeaderBlock();

        // each extracted field is matched and parsed
        for (String key : fields.keySet()) {

            // field is matched using the tag
            switch (key) {

                // Field 103 - Service Identifier
                case Field103.TAG:
                    userHeaderBlock.setServiceIdentifier(MTFieldParserUtils.parseField103(fields.get(key)));
                    break;

                // Field 106 - Message Input Reference
                case Field106.TAG:
                    userHeaderBlock.setMessageInputReference(MTFieldParserUtils.parseField106(fields.get(key)));
                    break;

                // Field 108 - Message User Reference
                case Field108.TAG:
                    userHeaderBlock.setMessageUserReference(MTFieldParserUtils.parseField108(fields.get(key)));
                    break;

                // Field 111 - Service Type Identifier
                case Field111.TAG:
                    userHeaderBlock.setServiceTypeIdentifier(MTFieldParserUtils.parseField111(fields.get(key)));
                    break;

                // Field 113 - Banking Priority
                case Field113.TAG:
                    userHeaderBlock.setBankingPriority(MTFieldParserUtils.parseField113(fields.get(key)));
                    break;

                // Field 115 - Addressee Information
                case Field115.TAG:
                    userHeaderBlock.setAddresseeInformation(MTFieldParserUtils.parseField115(fields.get(key)));
                    break;

                // Field 119 - Validation Flag
                case Field119.TAG:
                    userHeaderBlock.setValidationFlag(MTFieldParserUtils.parseField119(fields.get(key)));
                    break;

                // Field 121 - End to End Reference
                case Field121.TAG:
                    userHeaderBlock.setEndToEndReference(MTFieldParserUtils.parseField121(fields.get(key)));
                    break;

                // Field 165 - Payment Release Information
                case Field165.TAG:
                    userHeaderBlock.setPaymentReleaseInformation(MTFieldParserUtils.parseField165(fields.get(key)));
                    break;

                // Field 423 - Balance Checkpoint
                case Field423.TAG:
                    userHeaderBlock.setBalanceCheckpoint(MTFieldParserUtils.parseField423(fields.get(key)));
                    break;

                // Field 424 - Related Reference
                case Field424.TAG:
                    userHeaderBlock.setRelatedReference(MTFieldParserUtils.parseField424(fields.get(key)));
                    break;

                // Field 433 - Sanctions Screening Information
                case Field433.TAG:
                    userHeaderBlock.setSanctionsScreeningInformation(MTFieldParserUtils.parseField433(fields.get(key)));
                    break;

                // Field 434 - Payment Controls Information
                case Field434.TAG:
                    userHeaderBlock.setPaymentControlsInformation(MTFieldParserUtils.parseField434(fields.get(key)));
                    break;

                // If tag didn't match any of the above tags, it is not allowed
                default:

                    log.error(String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                            key, ConnectorConstants.USER_HEADER_BLOCK));
                    throw new MTMessageParsingException(
                            String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                    key, ConnectorConstants.USER_HEADER_BLOCK));
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

        // if passed string is null, return
        if (trailerBlockString == null) {
            return null;
        }

        // block cannot be blank
        if (trailerBlockString.isBlank()) {
            throw new MTMessageParsingException("Trailer Block cannot be empty");
        }

        // extract fields as a map of tag and value
        Map<String, String> fields = MTParserUtils.extractFieldsWithinCurlyBrackets(trailerBlockString);

        // if no matching fields found -> block cannot be parsed
        if (fields.isEmpty()) {

            log.error(String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TRAILER_BLOCK));
            throw new MTMessageParsingException(
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TRAILER_BLOCK));
        }

        TrailerBlock trailerBlock = new TrailerBlock();

        // each extracted field is matched and parsed
        for (String key : fields.keySet()) {

            switch (key) {

                // FieldDLM -> Delayed Message
                case MTParserConstants.FIELD_DLM_TAG:
                    trailerBlock.setDelayedMessage(true);
                    break;

                // FieldTNG -> Test and Training Message
                case MTParserConstants.FIELD_TNG_TAG:
                    trailerBlock.setTestAndTrainingMessage(true);
                    break;

                // FieldCHK -> Checksum
                case FieldCHK.TAG:
                    trailerBlock.setChecksum(MTFieldParserUtils.parseFieldCHK(fields.get(key)));
                    break;

                // FieldMRF -> Message Reference
                case FieldMRF.TAG:
                    trailerBlock.setMessageReference(MTFieldParserUtils.parseFieldMRF(fields.get(key)));
                    break;

                // FieldPDE -> Possible Duplicate Emission
                case FieldPDE.TAG:
                    trailerBlock.setPossibleDuplicateEmission(MTFieldParserUtils.parseFieldPDE(fields.get(key)));
                    break;

                // FieldPDM -> Possible Duplicate Message
                case FieldPDM.TAG:
                    trailerBlock.setPossibleDuplicateMessage(MTFieldParserUtils.parseFieldPDM(fields.get(key)));
                    break;

                // FieldSYS -> System Originated Message
                case FieldSYS.TAG:
                    trailerBlock.setSystemOriginatedMessage(MTFieldParserUtils.parseFieldSYS(fields.get(key)));
                    break;

                default:
                    log.error(String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                            key, ConnectorConstants.TRAILER_BLOCK));
                    throw new MTMessageParsingException(
                            String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                    key, ConnectorConstants.TRAILER_BLOCK));
            }
        }

        return trailerBlock;
    }

}
