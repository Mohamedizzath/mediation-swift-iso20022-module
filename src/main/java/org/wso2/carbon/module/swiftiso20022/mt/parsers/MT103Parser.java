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

import java.util.List;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT103TextBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23E;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26T;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50K;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57D;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71G;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77T;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT103Message;

import java.util.Map;

/**
 * Parser class for MT103 Message.
 */
public class MT103Parser {

    private static final Log log = LogFactory.getLog(MT103Parser.class);

    public static MT103Message parse(String mt103Message) throws MTMessageParsingException {

        Map<String, String> blocks = MTParserUtils.getMessageBlocks(mt103Message);
        // TODO: Do the format validations
        MT103Message mt103MessageModel = new MT103Message();
        MTParser.parse(blocks, mt103MessageModel);
        // TODO: implement parsing logic for the text block and set the text block
        return null;
    }

    // TODO: make this method private after merging
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
    public static MT103TextBlock parseTextBlock(String textBlock) throws MTMessageParsingException {

        Optional<List<String>> textBlockFields = MTParserUtils.getTextBlockFields(textBlock);

        if (textBlockFields.isEmpty()) {

            String errorMessage = String.format(
                    ConnectorConstants.ERROR_TEXT_BLOCK_FOR_MESSAGE, MT103Constants.MT103);
            log.error(errorMessage);
            throw new MTMessageParsingException(errorMessage);
        }

        MT103TextBlock mt103TextBlock = new MT103TextBlock();

        List<String> fields = textBlockFields.get();

        for (String field : fields) {

            String[] tagNValue = field.split(ConnectorConstants.COLON);

            if (tagNValue.length != 2) {

                String errorMessage = String.format(ConnectorConstants.ERROR_INVALID_FIELD_AND_VALUE,
                        field, ConnectorConstants.TEXT_BLOCK);
                log.error(errorMessage);
                throw new MTMessageParsingException(errorMessage);
            }

            switch (tagNValue[0]) {

                case Field13C.TAG:
                    mt103TextBlock.addTimeIndication(Field13C.parse(tagNValue[1]));
                    break;

                case Field20.TAG:
                    mt103TextBlock.setSendersReference(Field20.parse(tagNValue[1]));
                    break;

                case Field23B.TAG:
                    mt103TextBlock.setBankOperationCode(Field23B.parse(tagNValue[1]));
                    break;

                case Field23E.TAG:
                    mt103TextBlock.addInstructionCode(Field23E.parse(tagNValue[1]));
                    break;

                case Field26T.TAG:
                    mt103TextBlock.setTransactionTypeCode(Field26T.parse(tagNValue[1]));
                    break;

                case Field32A.TAG:
                    mt103TextBlock.setValue(Field32A.parse(tagNValue[1]));
                    break;

                case Field33B.TAG:
                    mt103TextBlock.setInstructedAmount(Field33B.parse(tagNValue[1]));
                    break;

                case Field36.TAG:
                    mt103TextBlock.setExchangeRate(Field36.parse(tagNValue[1]));
                    break;

                case Field50A.TAG:
                    mt103TextBlock.setOrderingCustomerOptA(Field50A.parse(tagNValue[1]));
                    break;

                case Field50F.TAG:
                    mt103TextBlock.setOrderingCustomerOptF(Field50F.parse(tagNValue[1]));
                    break;

                case Field50K.TAG:
                    mt103TextBlock.setOrderingCustomerOptK(Field50K.parse(tagNValue[1]));
                    break;

                case Field51A.TAG:
                    mt103TextBlock.setSendingInstitution(Field51A.parse(tagNValue[1]));
                    break;

                case Field52A.TAG:
                    mt103TextBlock.setOrderingInstitutionOptA(Field52A.parse(tagNValue[1]));
                    break;

                case Field52D.TAG:
                    mt103TextBlock.setOrderingInstitutionOptD(Field52D.parse(tagNValue[1]));
                    break;

                case Field53A.TAG:
                    mt103TextBlock.setSendersCorrespondentOptA(Field53A.parse(tagNValue[1]));
                    break;

                case Field53B.TAG:
                    mt103TextBlock.setSendersCorrespondentOptB(Field53B.parse(tagNValue[1]));
                    break;

                case Field53D.TAG:
                    mt103TextBlock.setSendersCorrespondentOptD(Field53D.parse(tagNValue[1]));
                    break;

                case Field54A.TAG:
                    mt103TextBlock.setReceiversCorrespondentOptA(Field54A.parse(tagNValue[1]));
                    break;

                case Field54B.TAG:
                    mt103TextBlock.setReceiversCorrespondentOptB(Field54B.parse(tagNValue[1]));
                    break;

                case Field54D.TAG:
                    mt103TextBlock.setReceiversCorrespondentOptD(Field54D.parse(tagNValue[1]));
                    break;

                case Field55A.TAG:
                    mt103TextBlock.setThirdReimbursementInstitutionOptA(Field55A.parse(tagNValue[1]));
                    break;

                case Field55B.TAG:
                    mt103TextBlock.setThirdReimbursementInstitutionOptB(Field55B.parse(tagNValue[1]));
                    break;

                case Field55D.TAG:
                    mt103TextBlock.setThirdReimbursementInstitutionOptD(Field55D.parse(tagNValue[1]));
                    break;

                case Field56A.TAG:
                    mt103TextBlock.setIntermediaryInstitutionOptA(Field56A.parse(tagNValue[1]));
                    break;

                case Field56C.TAG:
                    mt103TextBlock.setIntermediaryInstitutionOptC(Field56C.parse(tagNValue[1]));
                    break;

                case Field56D.TAG:
                    mt103TextBlock.setIntermediaryInstitutionOptD(Field56D.parse(tagNValue[1]));
                    break;

                case Field57A.TAG:
                    mt103TextBlock.setAccountWithInstitutionOptA(Field57A.parse(tagNValue[1]));
                    break;

                case Field57B.TAG:
                    mt103TextBlock.setAccountWithInstitutionOptB(Field57B.parse(tagNValue[1]));
                    break;

                case Field57C.TAG:
                    mt103TextBlock.setAccountWithInstitutionOptC(Field57C.parse(tagNValue[1]));
                    break;

                case Field57D.TAG:
                    mt103TextBlock.setAccountWithInstitutionOptD(Field57D.parse(tagNValue[1]));
                    break;

                case Field59.TAG:
                    mt103TextBlock.setBeneficiaryCustomerOptNoLetter(Field59.parse(tagNValue[1]));
                    break;

                case Field59A.TAG:
                    mt103TextBlock.setBeneficiaryCustomerOptA(Field59A.parse(tagNValue[1]));
                    break;

                case Field59F.TAG:
                    mt103TextBlock.setBeneficiaryCustomerOptF(Field59F.parse(tagNValue[1]));
                    break;

                case Field70.TAG:
                    mt103TextBlock.setRemittanceInformation(Field70.parse(tagNValue[1]));
                    break;

                case Field71A.TAG:
                    mt103TextBlock.setDetailsOfCharge(Field71A.parse(tagNValue[1]));
                    break;

                case Field71F.TAG:
                    mt103TextBlock.addSendersCharges(Field71F.parse(tagNValue[1]));
                    break;

                case Field71G.TAG:
                    mt103TextBlock.setReceiversCharges(Field71G.parse(tagNValue[1]));
                    break;

                case Field72.TAG:
                    mt103TextBlock.setSenderToReceiverInformation(Field72.parse(tagNValue[1]));
                    break;

                case Field77B.TAG:
                    mt103TextBlock.setRegulatoryReporting(Field77B.parse(tagNValue[1]));
                    break;

                case Field77T.TAG:
                    mt103TextBlock.setEnvelopeContents(Field77T.parse(tagNValue[1]));
                    break;

                default:
                    throw new MTMessageParsingException(
                            String.format(ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_TEXT_BLOCK,
                                    tagNValue[0], MT103Constants.MT103));
            }
        }

        return mt103TextBlock;
    }

}
