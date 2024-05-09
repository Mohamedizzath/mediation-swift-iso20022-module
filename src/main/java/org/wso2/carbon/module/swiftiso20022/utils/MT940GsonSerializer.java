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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;

import java.lang.reflect.Type;

/**
 * Class contains serialization methods for MT940 Messages object to JSON convert.
 */
public class MT940GsonSerializer implements JsonSerializer<MT940Message> {

    @Override
    public JsonElement serialize(MT940Message mt940Message, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

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

        return mt940JsonObj;
    }
}
