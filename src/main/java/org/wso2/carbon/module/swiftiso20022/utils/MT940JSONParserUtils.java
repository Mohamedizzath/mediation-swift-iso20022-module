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
        JsonObject textBlock = mt940JsonObj.getAsJsonObject("textBlock");
        // Remove NO LETTER OPTIONS
        textBlock.getAsJsonObject("transactionReferenceNumber").remove("option");

        if (textBlock.has("relatedReference") &&
                textBlock.getAsJsonObject("relatedReference").get("option").getAsCharacter() ==
                        MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject("relatedReference").remove("option");
        }

        if (textBlock.getAsJsonObject("accountIdentification").get("option").getAsCharacter() ==
                MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject("accountIdentification").remove("option");
        }

        if (textBlock.has("statementLines")) {
            for (JsonElement statement : textBlock.getAsJsonArray("statementLines")) {
                if (statement.getAsJsonObject().has("Field86") && statement.getAsJsonObject()
                        .getAsJsonObject("Field86").get("option").getAsCharacter() ==
                        MTParserConstants.FIELD_OPTION_NO_LETTER) {

                    statement.getAsJsonObject().getAsJsonObject("Field86").remove("option");
                }

                // Update the Field61 amount
                Double transactionAmt = MTParserUtils.convertMTAmountToISOAmount(
                        statement.getAsJsonObject().getAsJsonObject("Field61").get("amount").getAsString());

                statement.getAsJsonObject().getAsJsonObject("Field61").remove("amount");
                statement.getAsJsonObject().getAsJsonObject("Field61").addProperty("amount", transactionAmt);
            }
        }

        if (textBlock.has("infoToAccountOwner") &&
                textBlock.getAsJsonObject("infoToAccountOwner").get("option").getAsCharacter() ==
                MTParserConstants.FIELD_OPTION_NO_LETTER) {
            textBlock.getAsJsonObject("infoToAccountOwner").remove("option");
        }

        // Update the balance amounts
        String[] balanceTypes = new String[] { "openingBal", "closingBal", "closingAvlBalance", "forwardAvlBalance" };
        for (String balanceType : balanceTypes) {
            if (!textBlock.has(balanceType)) {
                continue;
            }

            Double balanceAmt = MTParserUtils.convertMTAmountToISOAmount(
                    textBlock.getAsJsonObject(balanceType).get("amount").getAsString());

            textBlock.getAsJsonObject(balanceType).remove("amount");
            textBlock.getAsJsonObject(balanceType).addProperty("amount", balanceAmt);
        }
    }

    /**
     * Update the dates in the MT940 for processing freemarker template to MT940 to ISO convert.
     * @param mt940JsonObj         JSON object tree for MT940 message
     */
    public static void updateDatesFrMT940(JsonObject mt940JsonObj) {
        if (mt940JsonObj.has("applicationHeaderBlock")) {
            String inputTime = mt940JsonObj.getAsJsonObject("applicationHeaderBlock")
                    .get("inputTime").getAsString();
            String inputDate = mt940JsonObj.getAsJsonObject("applicationHeaderBlock")
                    .get("messageInputReference").getAsString().substring(0, 6);

            String createdDt = MTParserUtils.convertFullDateToDt(inputDate, inputTime);

            mt940JsonObj.getAsJsonObject("applicationHeaderBlock")
                    .addProperty("createdDt", createdDt);
        }

        JsonObject textBlock = mt940JsonObj.getAsJsonObject("textBlock");

        // Update the balance dates
        String[] balanceTypes = new String[] { "openingBal", "closingBal", "closingAvlBalance", "forwardAvlBalance" };
        for (String balanceType : balanceTypes) {
            if (!textBlock.has(balanceType)) {
                continue;
            }

            String balanceDate = MTParserUtils.convertDateToDt(
                    textBlock.getAsJsonObject(balanceType).get("date").getAsString());

            textBlock.getAsJsonObject(balanceType).addProperty("dateDt", balanceDate);
        }

        if (textBlock.has("statementLines")) {
            for (JsonElement statement : textBlock.getAsJsonArray("statementLines")) {
                // Update the value date and entry date

                String valueDateDt = MTParserUtils.convertDateToDt(
                        statement.getAsJsonObject().getAsJsonObject("Field61").get("valueDate").getAsString());

                if (statement.getAsJsonObject().getAsJsonObject("Field61").has("entryDate")) {
                    String entryDateDt = MTParserUtils.convertDateToDtUsingCurrYear(
                            statement.getAsJsonObject().getAsJsonObject("Field61").get("entryDate").getAsString());

                    statement.getAsJsonObject().getAsJsonObject("Field61").addProperty("entryDateDt", entryDateDt);
                }

                statement.getAsJsonObject().getAsJsonObject("Field61").addProperty("valueDateDt", valueDateDt);
            }
        }
    }

    /**
     * Method for adding From BIC and To BIC to the MT940 message JSON.
     * @param mt940JsonObj         JSON object tree for MT940 message
     */
    public static void addBICToMT940Message(JsonObject mt940JsonObj) {
        String toBicNumber = MTParserUtils.convertLTAddressToBIC(mt940JsonObj.getAsJsonObject("basicHeaderBlock")
                .get("logicalTerminalAddress").getAsString());

        mt940JsonObj.addProperty("ToBIC", toBicNumber);

        if (mt940JsonObj.has("applicationHeaderBlock")) {
            String fromBicNumber = MTParserUtils.convertLTAddressToBIC(mt940JsonObj
                    .getAsJsonObject("applicationHeaderBlock").get("messageInputReference")
                    .getAsString().substring(6, 18));

            mt940JsonObj.addProperty("FromBIC", fromBicNumber);
        }
    }
}
