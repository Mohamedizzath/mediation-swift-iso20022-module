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

package org.wso2.carbon.module.swiftiso20022.mtmessageparsers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.TrailerBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.UserHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field434;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldDLM;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldSYS;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldTNG;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTFieldParserUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Parser class with parsing logic for common blocks.
 */
public class MTParser {

    private static final Log log = LogFactory.getLog(MTParser.class);

    /**
     * Method to get MT message object extended from {@link MTMessage} by parsing MT message blocks.
     *
     * @param blocks        A map of block key and block value string
     * @param mtMessageType Class of MT message model
     * @param <T>           MT message model class type implementation extended from {@link MTMessage}
     * @return Class extended from {@link MTMessage} with values assigned by parsing passed block values
     * @throws MTMessageParsingException if any one of the blocks cannot be parsed
     */
    public static <T extends MTMessage> T parse(
            Map<String, String> blocks, Class<T> mtMessageType) throws MTMessageParsingException {

        T mtMessage;

        try {
            mtMessage = mtMessageType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException
                 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(String.format(ConnectorConstants.ERROR_OBJECT_INSTANTIATING_LOG, mtMessageType), e);
            throw new MTMessageParsingException(ConnectorConstants.RUNTIME_ERROR, ConnectorConstants.ERROR_RUNTIME_LOG);
        }

        // TODO: Add Basic Header Block and Application Header Block parsing

        log.info("Parsing User Header Block String");
        mtMessage.setUserHeaderBlock(parseUserHeaderBlock(blocks.get(ConnectorConstants.USER_HEADER_BLOCK_KEY)));

        log.info("Parsing Trailer Block String");
        mtMessage.setTrailerBlock(parseTrailerBlock(blocks.get(ConnectorConstants.TRAILER_BLOCK_KEY)));

        return mtMessage;
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
            throw new MTMessageParsingException("User Header Block cannot be empty");
        }

        List<String> fields;
        try {

            // extract fields as a list of strings
            fields = MTParserUtils.extractFieldsWithinCurlyBrackets(userHeaderBlockString);
        } catch (MTMessageParsingException e) {

            log.error(e.getMessage(), e);
            // set SWIFT error code and block
            throw new MTMessageParsingException(ConnectorConstants.ERROR_U03,
                    String.format(e.getMessage(), ConnectorConstants.USER_HEADER_BLOCK));
        }

        // if the string has no "{" or "}" characters
        if (fields.isEmpty()) {

            log.error(String.format(
                    ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT_LOG, ConnectorConstants.USER_HEADER_BLOCK));
            throw new MTMessageParsingException(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.USER_HEADER_BLOCK));
        }

        UserHeaderBlock userHeaderBlock = new UserHeaderBlock();

        // each extracted field is matched and parsed
        for (String field : fields) {

            // split field to tag and value
            // "{119:STP}" -> ["119", "STP"]
            String[] tagNValue = field.split(ConnectorConstants.COLON);

            // after splitting, if there can only be 2 elements
            if (tagNValue.length != 2) {

                log.error(String.format(
                        ConnectorConstants.ERROR_INVALID_FIELD_MT_PARSER_LOG,
                        field, ConnectorConstants.USER_HEADER_BLOCK));
                throw new MTMessageParsingException(ConnectorConstants.ERROR_U00,
                        String.format(ConnectorConstants.ERROR_INVALID_FIELD_AND_VALUE,
                                field, ConnectorConstants.USER_HEADER_BLOCK));
            }

            // field is matched using the tag
            switch (tagNValue[0]) {

                // Field 103 - Service Identifier
                case Field103.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getServiceIdentifier(),
                            ConnectorConstants.BLOCK03_SERVICE_IDENTIFIER, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setServiceIdentifier(MTFieldParserUtils.parseField103(tagNValue[1]));
                    break;

                // Field 106 - Message Input Reference
                case Field106.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getMessageInputReference(),
                            ConnectorConstants.BLOCK03_MESSAGE_INPUT_REFERENCE, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setMessageInputReference(MTFieldParserUtils.parseField106(tagNValue[1]));
                    break;

                // Field 108 - Message User Reference
                case Field108.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getMessageUserReference(),
                            ConnectorConstants.BLOCK03_MESSAGE_USER_REFERENCE, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setMessageUserReference(MTFieldParserUtils.parseField108(tagNValue[1]));
                    break;

                // Field 111 - Service Type Identifier
                case Field111.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getServiceTypeIdentifier(),
                            ConnectorConstants.BLOCK03_SERVICE_TYPE_IDENTIFIER, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setServiceTypeIdentifier(MTFieldParserUtils.parseField111(tagNValue[1]));
                    break;

                // Field 113 - Banking Priority
                case Field113.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getBankingPriority(),
                            ConnectorConstants.BLOCK03_BANKING_PRIORITY, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setBankingPriority(MTFieldParserUtils.parseField113(tagNValue[1]));
                    break;

                // Field 115 - Addressee Information
                case Field115.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getAddresseeInformation(),
                            ConnectorConstants.BLOCK03_ADDRESSEE_INFORMATION, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setAddresseeInformation(MTFieldParserUtils.parseField115(tagNValue[1]));
                    break;

                // Field 119 - Validation Flag
                case Field119.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getValidationFlag(),
                            ConnectorConstants.BLOCK03_VALIDATION_FLAG, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setValidationFlag(MTFieldParserUtils.parseField119(tagNValue[1]));
                    break;

                // Field 121 - End to End Reference
                case Field121.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getEndToEndReference(),
                            ConnectorConstants.BLOCK03_END_TO_END_REFERENCE, ConnectorConstants.ERROR_U03);

                    // End to end reference should always appear before service type identifier
                    if (userHeaderBlock.getServiceTypeIdentifier() != null) {
                        throw new MTMessageParsingException(ConnectorConstants.ERROR_U12,
                                ConnectorConstants.ERROR_111_BEFORE_121);
                    }
                    userHeaderBlock.setEndToEndReference(MTFieldParserUtils.parseField121(tagNValue[1]));
                    break;

                // Field 165 - Payment Release Information
                case Field165.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getPaymentReleaseInformation(),
                            ConnectorConstants.BLOCK03_PAYMENT_RELEASE_INFORMATION, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setPaymentReleaseInformation(MTFieldParserUtils.parseField165(tagNValue[1]));
                    break;

                // Field 423 - Balance Checkpoint
                case Field423.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getBalanceCheckpoint(),
                            ConnectorConstants.BLOCK03_BALANCE_CHECKPOINT, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setBalanceCheckpoint(MTFieldParserUtils.parseField423(tagNValue[1]));
                    break;

                // Field 424 - Related Reference
                case Field424.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getRelatedReference(),
                            ConnectorConstants.BLOCK03_RELATED_REFERENCE, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setRelatedReference(MTFieldParserUtils.parseField424(tagNValue[1]));
                    break;

                // Field 433 - Sanctions Screening Information
                case Field433.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getSanctionsScreeningInformation(),
                            ConnectorConstants.BLOCK03_SANCTIONS_SCREENING_INFORMATION, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setSanctionsScreeningInformation(MTFieldParserUtils.parseField433(tagNValue[1]));
                    break;

                // Field 434 - Payment Controls Information
                case Field434.TAG:
                    checkIfFieldIsRepeated(userHeaderBlock.getPaymentControlsInformation(),
                            ConnectorConstants.BLOCK03_PAYMENT_CONTROLS_INFORMATION, ConnectorConstants.ERROR_U03);
                    userHeaderBlock.setPaymentControlsInformation(MTFieldParserUtils.parseField434(tagNValue[1]));
                    break;

                // If tag didn't match any of the above tags, it is not allowed
                default:

                    log.error(String.format(ConnectorConstants.ERROR_INVALID_TAG_MT_PARSER_LOG,
                            tagNValue[0], ConnectorConstants.USER_HEADER_BLOCK));
                    throw new MTMessageParsingException(ConnectorConstants.ERROR_U03,
                            String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                    tagNValue[0], ConnectorConstants.USER_HEADER_BLOCK));
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

        List<String> fields;
        try {

            // extract fields as a list of strings
            fields = MTParserUtils.extractFieldsWithinCurlyBrackets(trailerBlockString);
        } catch (MTMessageParsingException e) {

            log.error(e.getMessage(), e);
            throw new MTMessageParsingException(ConnectorConstants.ERROR_Z00,
                    String.format(e.getMessage(), ConnectorConstants.TRAILER_BLOCK));
        }

        // if the string has no "{" or "}" characters
        if (fields.isEmpty()) {

            log.error(String.format(
                    ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT_LOG, ConnectorConstants.TRAILER_BLOCK));
            throw new MTMessageParsingException(ConnectorConstants.ERROR_U03,
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TRAILER_BLOCK));
        }

        TrailerBlock trailerBlock = new TrailerBlock();

        // each extracted field is matched and parsed
        for (String field : fields) {

            // split field to tag and value
            // "{CHK:123456789ABC}" -> ["CHK", "123456789ABC"]
            String[] tagNValue = field.split(ConnectorConstants.COLON);

            // fields are matched against length
            switch (tagNValue.length) {

                // fields with empty strings
                case 1:

                    // fields are matched against tag
                    switch (tagNValue[0]) {

                        // FieldDLM -> Delayed Message
                        case FieldDLM.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getDelayedMessage(),
                                    ConnectorConstants.BLOCK05_DELAYED_MESSAGE, ConnectorConstants.ERROR_Z00);
                            trailerBlock.setDelayedMessage(new FieldDLM());
                            break;

                        // FieldTNG -> Test and Training Message
                        case FieldTNG.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getTestAndTrainingMessage(),
                                    ConnectorConstants.BLOCK05_TEST_AND_TRAINING_MESSAGE, ConnectorConstants.ERROR_Z00);
                            trailerBlock.setTestAndTrainingMessage(new FieldTNG());
                            break;

                        // If tag didn't match any of the above tags, it is not allowed
                        default:

                            log.error(String.format(ConnectorConstants.ERROR_INVALID_TAG_MT_PARSER_LOG,
                                    tagNValue[0], ConnectorConstants.TRAILER_BLOCK));
                            throw new MTMessageParsingException(ConnectorConstants.ERROR_Z00,
                                    String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                            tagNValue[0], ConnectorConstants.TRAILER_BLOCK));
                    }
                    break;

                // fields with tag and a value
                case 2:

                    // fields are matched against tag
                    switch (tagNValue[0]) {

                        // FieldCHK -> Checksum
                        case FieldCHK.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getChecksum(),
                                    ConnectorConstants.BLOCK05_CHECKSUM, ConnectorConstants.ERROR_Z00);
                            trailerBlock.setChecksum(MTFieldParserUtils.parseFieldCHK(tagNValue[1]));
                            break;

                        // FieldMRF -> Message Reference
                        case FieldMRF.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getMessageReference(),
                                    ConnectorConstants.BLOCK05_MESSAGE_REFERENCE, ConnectorConstants.ERROR_Z00);
                            trailerBlock.setMessageReference(MTFieldParserUtils.parseFieldMRF(tagNValue[1]));
                            break;

                        // FieldPDE -> Possible Duplicate Emission
                        case FieldPDE.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getPossibleDuplicateEmission(),
                                    ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_EMISSION,
                                    ConnectorConstants.ERROR_Z00);
                            trailerBlock.setPossibleDuplicateEmission(MTFieldParserUtils.parseFieldPDE(tagNValue[1]));
                            break;

                        // FieldPDM -> Possible Duplicate Message
                        case FieldPDM.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getPossibleDuplicateMessage(),
                                    ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_MESSAGE,
                                    ConnectorConstants.ERROR_Z00);
                            trailerBlock.setPossibleDuplicateMessage(MTFieldParserUtils.parseFieldPDM(tagNValue[1]));
                            break;

                        // FieldSYS -> System Originated Message
                        case FieldSYS.TAG:
                            checkIfFieldIsRepeated(trailerBlock.getSystemOriginatedMessage(),
                                    ConnectorConstants.BLOCK05_SYSTEM_ORIGINATED_MESSAGE, ConnectorConstants.ERROR_Z00);
                            trailerBlock.setSystemOriginatedMessage(MTFieldParserUtils.parseFieldSYS(tagNValue[1]));
                            break;

                        // If tag didn't match any of the above tags, it is not allowed
                        default:

                            log.error(String.format(ConnectorConstants.ERROR_INVALID_TAG_MT_PARSER_LOG,
                                    tagNValue[0], ConnectorConstants.TRAILER_BLOCK));
                            throw new MTMessageParsingException(ConnectorConstants.ERROR_Z00,
                                    String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                            tagNValue[0], ConnectorConstants.TRAILER_BLOCK));
                    }
                    break;

                // any other format is not allowed
                default:

                    log.error(String.format(
                            ConnectorConstants.ERROR_INVALID_FIELD_MT_PARSER_LOG,
                            field, ConnectorConstants.TRAILER_BLOCK));
                    throw new MTMessageParsingException(ConnectorConstants.ERROR_Z00,
                            String.format(ConnectorConstants.ERROR_INVALID_FIELD_AND_VALUE,
                                    field, ConnectorConstants.TRAILER_BLOCK));
            }
        }

        return trailerBlock;
    }

    /**
     * Method to check whether a value is assigned to relevant field.
     * This method is used to check for repetitions of fields.
     *
     * @param field     Child class of {@link Field}
     * @param fieldName Name of the field
     * @param errorCode Error code relevant to the block
     * @throws MTMessageParsingException if the value is already assigned.
     */
    private static void checkIfFieldIsRepeated(
            Field field, String fieldName, String errorCode) throws MTMessageParsingException {
        if (field != null) {
            throw new MTMessageParsingException(errorCode,
                    String.format(
                            ConnectorConstants.ERROR_FIELD_REPEATED, fieldName, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }

}
