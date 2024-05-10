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

package org.wso2.carbon.module.swiftiso20022;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.utils.MT940JSONParserUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ToJSONTransformerTestConstants;

import java.util.List;

/**
 * Contains test for testing gson transformer methods.
 */
public class MT940ToJSONTransformerTests {
    private static final Gson gson = new Gson();
    @Test(dataProvider = "removeFieldOption", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testRemoveFieldOptionScenario(MT940Message mt940Message) throws Exception {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);


        Assert.assertFalse(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("transactionReferenceNumber").has("option"));
        Assert.assertFalse(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("relatedReference").has("option"));
        Assert.assertFalse(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("accountIdentification").has("option"));
        Assert.assertFalse(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("infoToAccountOwner").has("option"));

        // Asserting Field86 in statement lines
        for (JsonElement statement : mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonArray("statementLines")) {
            Assert.assertFalse(statement.getAsJsonObject().getAsJsonObject("Field86")
                    .has("option"));
        }
    }

    @Test(dataProvider = "updateBalanceAmounts", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateBalanceAmountsScenario(MT940Message mt940Message, Double expectedAmt) throws Exception {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);

        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("openingBal").get("amount").getAsDouble(), expectedAmt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("closingBal").get("amount").getAsDouble(), expectedAmt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("closingAvlBalance").get("amount").getAsDouble(), expectedAmt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("forwardAvlBalance").get("amount").getAsDouble(), expectedAmt);
    }

    @Test(dataProvider = "updateBalanceDates", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateBalanceDateScenario(MT940Message mt940Message, String expectedDt) throws Exception {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);
        MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObj);

        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("openingBal").get("dateDt").getAsString(), expectedDt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("closingBal").get("dateDt").getAsString(), expectedDt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("closingAvlBalance").get("dateDt").getAsString(), expectedDt);
        Assert.assertEquals(mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonObject("forwardAvlBalance").get("dateDt").getAsString(), expectedDt);
    }

    @Test(dataProvider = "updateStatementAmounts", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateStatementAmountsScenario(MT940Message mt940Message, List<Double> amounts) throws Exception {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);

        // Asserting Field86 in statement lines
        int statementCount = 0;
        for (JsonElement statement : mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonArray("statementLines")) {
            Assert.assertEquals(statement.getAsJsonObject().getAsJsonObject("Field61")
                    .get("amount").getAsDouble(), amounts.get(statementCount++));
        }
    }

    @Test(dataProvider = "updateStatementEntryDate", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateStatementEntryDateScenario(MT940Message mt940Message, List<String> entryDates) {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);
        MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObj);

        // Asserting Field86 in statement lines
        int statementCount = 0;
        for (JsonElement statement : mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonArray("statementLines")) {
            Assert.assertEquals(statement.getAsJsonObject().getAsJsonObject("Field61")
                    .get("entryDateDt").getAsString(), entryDates.get(statementCount++));
        }
    }

    @Test(dataProvider = "updateStatementValueDate", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateStatementValueDateScenario(MT940Message mt940Message, List<String> entryDates) {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);
        MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObj);

        // Asserting Field86 in statement lines
        int statementCount = 0;
        for (JsonElement statement : mt940JsonObj.getAsJsonObject("textBlock")
                .getAsJsonArray("statementLines")) {
            Assert.assertEquals(statement.getAsJsonObject().getAsJsonObject("Field61")
                    .get("valueDateDt").getAsString(), entryDates.get(statementCount++));
        }
    }

    @Test(dataProvider = "updateFromBICNumber", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateFromBICNumberScenario(MT940Message mt940Message, String frBICNumber) throws Exception {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);
        MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObj);
        MT940JSONParserUtils.addBICToMT940Message(mt940JsonObj);

        Assert.assertEquals(mt940JsonObj.get("FromBIC").getAsString(), frBICNumber);
    }

    @Test(dataProvider = "updateToBICNumber", dataProviderClass = MT940ToJSONTransformerTestConstants.class)
    public void testUpdateToBICNumberScenario(MT940Message mt940Message, String frBICNumber) {
        JsonObject mt940JsonObj = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObj);
        MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObj);
        MT940JSONParserUtils.addBICToMT940Message(mt940JsonObj);

        Assert.assertEquals(mt940JsonObj.get("ToBIC").getAsString(), frBICNumber);
    }
}
