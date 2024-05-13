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

package org.wso2.carbon.module.swiftiso20022.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.wso2.carbon.module.swiftiso20022.constants.MT940ParserConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;

/**
 * Class contains serialization utils methods for MT940 Messages object to JSON convert.
 */
public class MT940JSONParserUtils {
    /**
     * Update the JSON tree by removing options if it is equals to no letter and updating amount.
     * @param mt940JsonObj          MT940 message as JSON Tree
     */
    public static void updateJsonObjectToMT940(JsonObject mt940JsonObj) {
        JsonObject textBlock = mt940JsonObj.getAsJsonObject(MTParserConstants.TEXT_BLOCK_JSON_KEY);
        // Remove NO LETTER OPTIONS
        textBlock.getAsJsonObject(MT940ParserConstants.TRANSACTION_REFERENCE_NUMBER_JSON_KEY)
                .remove(MTParserConstants.OPTION_JSON_KEY);

        if (textBlock.has(MT940ParserConstants.RELATED_REFERENCE_NUMBER_JSON_KEY) &&
                textBlock.getAsJsonObject(MT940ParserConstants.RELATED_REFERENCE_NUMBER_JSON_KEY)
                .get(MTParserConstants.OPTION_JSON_KEY).getAsCharacter() == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject(MT940ParserConstants.RELATED_REFERENCE_NUMBER_JSON_KEY)
                    .remove(MTParserConstants.OPTION_JSON_KEY);
        }

        if (textBlock.getAsJsonObject(MT940ParserConstants.ACCOUNT_IDENTIFICATION_JSON_KEY)
                .get(MTParserConstants.OPTION_JSON_KEY).getAsCharacter() == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject(MT940ParserConstants.ACCOUNT_IDENTIFICATION_JSON_KEY)
                    .remove(MTParserConstants.OPTION_JSON_KEY);
        }

        if (textBlock.has(MT940ParserConstants.STATEMENT_LINES_JSON_KEY)) {
            for (JsonElement statement : textBlock.getAsJsonArray(MT940ParserConstants.STATEMENT_LINES_JSON_KEY)) {
                if (statement.getAsJsonObject().has(MT940ParserConstants.FIELD_86_JSON_KEY) &&
                    statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_86_JSON_KEY)
                    .get(MTParserConstants.OPTION_JSON_KEY).getAsCharacter() ==
                            MTParserConstants.FIELD_OPTION_NO_LETTER) {

                    statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_86_JSON_KEY)
                            .remove(MTParserConstants.OPTION_JSON_KEY);
                }

                // Update the Field61 amount
                Double transactionAmt = MTParserUtils.convertMTAmountToISOAmount(
                        statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                                .get(MTParserConstants.AMOUNT_JSON_KEY).getAsString());

                statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                        .remove(MTParserConstants.AMOUNT_JSON_KEY);
                statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                        .addProperty(MTParserConstants.AMOUNT_JSON_KEY, transactionAmt);
            }
        }

        if (textBlock.has(MT940ParserConstants.INFO_TO_ACCOUNT_OWNER) &&
                textBlock.getAsJsonObject(MT940ParserConstants.INFO_TO_ACCOUNT_OWNER)
                .get(MTParserConstants.OPTION_JSON_KEY).getAsCharacter() == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject(MT940ParserConstants.INFO_TO_ACCOUNT_OWNER)
                    .remove(MTParserConstants.OPTION_JSON_KEY);
        }

        // Update the balance amounts
        String[] balanceTypes = new String[] { "openingBal", "closingBal", "closingAvlBalance", "forwardAvlBalance" };
        for (String balanceType : balanceTypes) {
            if (!textBlock.has(balanceType)) {
                continue;
            }

            Double balanceAmt = MTParserUtils.convertMTAmountToISOAmount(
                    textBlock.getAsJsonObject(balanceType).get(MTParserConstants.AMOUNT_JSON_KEY).getAsString());

            textBlock.getAsJsonObject(balanceType).remove(MTParserConstants.AMOUNT_JSON_KEY);
            textBlock.getAsJsonObject(balanceType).addProperty(MTParserConstants.AMOUNT_JSON_KEY, balanceAmt);
        }
    }

    /**
     * Update the dates in the MT940 for processing freemarker template to MT940 to ISO convert.
     * @param mt940JsonObj         JSON object tree for MT940 message
     */
    public static void updateDatesFromMT940(JsonObject mt940JsonObj) {
        if (mt940JsonObj.has(MTParserConstants.APPLICATION_HEADER_JSON_KEY)) {
            String inputTime = mt940JsonObj.getAsJsonObject(MTParserConstants.APPLICATION_HEADER_JSON_KEY)
                    .get(MT940ParserConstants.INPUT_TIME_JSON_KEY).getAsString();
            String inputDate = mt940JsonObj.getAsJsonObject(MTParserConstants.APPLICATION_HEADER_JSON_KEY)
                    .get(MT940ParserConstants.MESSAGE_INPUT_REFERENCE_JSON_KEY).getAsString().substring(0, 6);

            String createdDt = MTParserUtils.convertFullDateToDt(inputDate, inputTime);

            mt940JsonObj.getAsJsonObject(MTParserConstants.APPLICATION_HEADER_JSON_KEY)
                    .addProperty(MT940ParserConstants.CREATE_DATE_DT_JSON_KEY, createdDt);
        }

        JsonObject textBlock = mt940JsonObj.getAsJsonObject(MTParserConstants.TEXT_BLOCK_JSON_KEY);

        // Update the balance dates
        String[] balanceTypes = new String[] { "openingBal", "closingBal", "closingAvlBalance", "forwardAvlBalance" };
        for (String balanceType : balanceTypes) {
            if (!textBlock.has(balanceType)) {
                continue;
            }

            String balanceDate = MTParserUtils.convertDateToDt(
                    textBlock.getAsJsonObject(balanceType).get(MTParserConstants.DATE_JSON_KEY).getAsString());

            textBlock.getAsJsonObject(balanceType).addProperty(MTParserConstants.DATE_DT_JSON_KEY, balanceDate);
        }

        if (textBlock.has(MT940ParserConstants.STATEMENT_LINES_JSON_KEY)) {
            for (JsonElement statement : textBlock.getAsJsonArray(MT940ParserConstants.STATEMENT_LINES_JSON_KEY)) {
                // Update the value date and entry date

                String valueDateDt = MTParserUtils.convertDateToDt(
                        statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                                .get(MT940ParserConstants.VALUE_DATE_JSON_KEY).getAsString());

                if (statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                        .has(MT940ParserConstants.ENTRY_DATE_JSON_KEY)) {
                    String entryDateDt = MTParserUtils.convertDateToDtUsingCurrYear(
                            statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                            .get(MT940ParserConstants.ENTRY_DATE_JSON_KEY).getAsString());

                    statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                            .addProperty(MT940ParserConstants.ENTRY_DATE_DT_JSON_KEY, entryDateDt);
                }

                statement.getAsJsonObject().getAsJsonObject(MT940ParserConstants.FIELD_61_JSON_KEY)
                        .addProperty(MT940ParserConstants.VALUE_DATE_DT_JSON_KEY, valueDateDt);
            }
        }
    }

    /**
     * Method for adding From BIC and To BIC to the MT940 message JSON.
     * @param mt940JsonObj         JSON object tree for MT940 message
     */
    public static void addBICToMT940Message(JsonObject mt940JsonObj) {
        String toBicNumber = MTParserUtils.convertLTAddressToBIC(mt940JsonObj
                .getAsJsonObject(MTParserConstants.BASIC_HEADER_JSON_KEY)
                .get(MTParserConstants.LOGICAL_TERMINAL_ADDRESS_JSON_KEY).getAsString());

        mt940JsonObj.addProperty(MTParserConstants.TO_BIC_JSON_KEY, toBicNumber);

        if (mt940JsonObj.has(MTParserConstants.APPLICATION_HEADER_JSON_KEY)) {
            String fromBicNumber = MTParserUtils.convertLTAddressToBIC(mt940JsonObj
                    .getAsJsonObject(MTParserConstants.APPLICATION_HEADER_JSON_KEY)
                    .get(MT940ParserConstants.MESSAGE_INPUT_REFERENCE_JSON_KEY).getAsString().substring(6, 18));

            mt940JsonObj.addProperty(MTParserConstants.FROM_BIC_JSON_KEY, fromBicNumber);
        }
    }
}
