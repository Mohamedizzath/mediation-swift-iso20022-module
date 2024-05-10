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
import org.wso2.carbon.module.swiftiso20022.mt.format.validators.MT103BlockFormatValidator;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT103TextBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT103Message;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Parser class for MT103 Message.
 */
public class MT103Parser {

    private static final Log log = LogFactory.getLog(MT103Parser.class);

    private MT103Parser() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Method to parse MT103 message string to a {@link MT103Message} model.
     *
     * @param mt103Message MT103 message string
     * @return A {@link MT103Message} with assigned values from the message
     * @throws MTMessageParsingException if the message can be parsed (invalid format)
     */
    public static MT103Message parse(String mt103Message) throws MTMessageParsingException {

        Map<String, String> blocks = MTParserUtils.getMessageBlocks(mt103Message);

        ValidationResult validationResult = MT103BlockFormatValidator.validateMTMessageBlockFormat(blocks);

        if (validationResult.isNotValid()) {
            throw new MTMessageParsingException(validationResult.getErrorMessage());
        }

        MT103Message mt103MessageModel = new MT103Message();

        // This method parse and set Header blocks and the Trailer block, logic is common to all MT messages.
        MTParser.parse(blocks, mt103MessageModel);

        // Text block is message specific implementation.
        mt103MessageModel.setTextBlock(parseTextBlock(blocks.get(ConnectorConstants.TEXT_BLOCK_KEY)));
        return mt103MessageModel;
    }

    /**
     * Method to parse text block and return MT103TextBlock object.
     *
     * @param textBlock String containing text block fields of a MT103 message
     *                  <dl>
     *                     <dt>example:</dt>
     *                     <dd>:20:REFERENCE12345</dd>
     *                     <dd>:23B:CRED</dd>
     *                     <dd>:32A:230501EUR123456,78</dd>
     *                     <dd>:50A:/12345678901234567890</dd>
     *                     <dd>MR. JOHN DOE</dd>
     *                     <dd>:71A:SHA</dd>
     *                  </dl>
     * @return A {@link MT103TextBlock} model with values assigned by parsing text block
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    private static MT103TextBlock parseTextBlock(String textBlock) throws MTMessageParsingException {

        // Get text block fields as an array of strings
        // "\n:tag1:val1\n:tag2:val2\n:tag3:val3" -> ["tag1:val1", "tag2:val2", "tag3:val3"]
        List<String> fields = MTParserUtils.getTextBlockFields(textBlock);

        MT103TextBlock mt103TextBlock = new MT103TextBlock();

        for (String field : fields) {

            // Matchers format -> (tag)(option):(value)
            Matcher tagNValue = MTParserConstants.TAG_AND_VALUE_REGEX_PATTERN_TEXT_BLOCK.matcher(field);

            // After splitting, there can be only 2 elements else invalid tag and value format
            if (!tagNValue.matches()) {
                String errorMessage = String.format(ConnectorConstants.ERROR_INVALID_FIELD_AND_VALUE,
                        field, ConnectorConstants.TEXT_BLOCK);
                log.error(errorMessage);
                throw new MTMessageParsingException(errorMessage);
            }

            // group 1 -> tag
            String tag = tagNValue.group(1);

            // group 2 -> option
            char option = StringUtils.isNotBlank(tagNValue.group(2)) ?
                    tagNValue.group(2).charAt(0) : ConnectorConstants.NO_LETTER_OPTION;

            // group 2 -> value
            switch (tag) {
                case Field13.TAG:
                    // This field is repetitive and every occurrence is parsed separately and added to the model
                    mt103TextBlock.setTimeIndication(Field13.parse(tagNValue.group(3), option));
                    break;
                case Field20.TAG:
                    mt103TextBlock.setSendersReference(Field20.parse(tagNValue.group(3), option));
                    break;
                case Field23.TAG:
                    Field23 field23 = Field23.parse(tagNValue.group(3), option);

                    if (option == ConnectorConstants.OPTION_B) {
                        mt103TextBlock.setBankOperationCode(field23);
                    } else {
                        // This field is repetitive and every occurrence is parsed separately and added to the model
                        mt103TextBlock.setInstructionCode(field23);
                    }
                    break;
                case Field26.TAG:
                    mt103TextBlock.setTransactionTypeCode(Field26.parse(tagNValue.group(3), option));
                    break;
                case Field32.TAG:
                    mt103TextBlock.setValue(Field32.parse(tagNValue.group(3), option));
                    break;
                case Field33.TAG:
                    mt103TextBlock.setInstructedAmount(Field33.parse(tagNValue.group(3), option));
                    break;
                case Field36.TAG:
                    mt103TextBlock.setExchangeRate(Field36.parse(tagNValue.group(3), option));
                    break;
                case Field50.TAG:
                    mt103TextBlock.setOrderingCustomer(Field50.parse(tagNValue.group(3), option));
                    break;
                case Field51.TAG:
                    mt103TextBlock.setSendingInstitution(Field51.parse(tagNValue.group(3), option));
                    break;
                case Field52.TAG:
                    mt103TextBlock.setOrderingInstitution(Field52.parse(tagNValue.group(3), option));
                    break;
                case Field53.TAG:
                    mt103TextBlock.setSendersCorrespondent(Field53.parse(tagNValue.group(3), option));
                    break;
                case Field54.TAG:
                    mt103TextBlock.setReceiversCorrespondent(Field54.parse(tagNValue.group(3), option));
                    break;
                case Field55.TAG:
                    mt103TextBlock.setThirdReimbursementInstitution(Field55.parse(tagNValue.group(3), option));
                    break;
                case Field56.TAG:
                    mt103TextBlock.setIntermediaryInstitution(Field56.parse(tagNValue.group(3), option));
                    break;
                case Field57.TAG:
                    mt103TextBlock.setAccountWithInstitution(Field57.parse(tagNValue.group(3), option));
                    break;
                case Field59.TAG:
                    mt103TextBlock.setBeneficiaryCustomer(Field59.parse(tagNValue.group(3), option));
                    break;
                case Field70.TAG:
                    mt103TextBlock.setRemittanceInformation(Field70.parse(tagNValue.group(3), option));
                    break;
                case Field71.TAG:
                    Field71 field71 = Field71.parse(tagNValue.group(3), option);

                    if (option == ConnectorConstants.OPTION_A) {
                        mt103TextBlock.setDetailsOfCharges(field71);
                    } else if (option == ConnectorConstants.OPTION_F) {
                        // This field is repetitive and every occurrence is parsed separately and added to the model
                        mt103TextBlock.setSendersCharges(field71);
                    } else {
                        mt103TextBlock.setReceiversCharges(field71);
                        break;
                    }
                    break;
                case Field72.TAG:
                    mt103TextBlock.setSenderToReceiverInformation(Field72.parse(tagNValue.group(3), option));
                    break;
                case Field77.TAG:
                    Field77 field77 = Field77.parse(tagNValue.group(3), option);

                    if (option == ConnectorConstants.OPTION_B) {
                        mt103TextBlock.setRegulatoryReporting(field77);
                    } else {
                        mt103TextBlock.setEnvelopeContents(field77);
                    }
                    break;
                default:
                    throw new MTMessageParsingException(
                            String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK,
                                    tagNValue.group(1), ConnectorConstants.TEXT_BLOCK));
            }
        }

        return mt103TextBlock;
    }
}
