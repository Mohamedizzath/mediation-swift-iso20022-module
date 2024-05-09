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

package org.wso2.carbon.module.swiftiso20022;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.util.PayloadHelper;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT103Message;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT103Parser;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;

/**
 * Class to map MT103 text message to {@link MT103Message} and set the model to message context after validating.
 */
public class MT103ToJsonTransformer extends AbstractConnector {

    private static final Log log = LogFactory.getLog(MT103ToJsonTransformer.class);
    private static final Gson gson = new Gson();
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        log.debug("Executing MT103ToJsonTransformer to transform MT103 text message to JSON");

        String payload = PayloadHelper.getTextPayload(messageContext);

        if (StringUtils.isBlank(payload)) {
            log.error("Failed to read the text payload from request.");
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.MISSING_REQUEST_PAYLOAD,
                    ConnectorConstants.ERROR_MISSING_PAYLOAD);
            this.handleException(ConnectorConstants.ERROR_MISSING_PAYLOAD, messageContext);
        }

        try {
            MT103Message mt103Message = MT103Parser.parse(payload);

            // TODO: validate MT103 message
            // Current implementation doesn't validate values
            // and doesn't enforce format on fields with multiple options

            String jsonString = transformJsonString(gson.toJsonTree(mt103Message).getAsJsonObject());

            ConnectorUtils.appendJsonResponseToMessageContext(messageContext, jsonString);

        } catch (MTMessageParsingException e) {
            log.error("Failed to parse text payload to MT103 model.", e);
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                    e.getMessage());
            this.handleException(e.getMessage(), messageContext);
        } catch (AxisFault e) {
            log.error(ConnectorConstants.PROCESSING_ERROR, e);
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.SERVER_ERROR,
                    ConnectorConstants.PROCESSING_ERROR);
            throw new ConnectException(e, ConnectorConstants.PROCESSING_ERROR);
        }
    }

    private static String transformJsonString(JsonObject mt103Message) {

        JsonObject textBlock = mt103Message.getAsJsonObject(MT103Constants.TEXT_BLOCK_KEY);

        replaceAmountAsNumber(textBlock);

        removeNoLetterOption(textBlock);

        mt103Message.add(MT103Constants.TEXT_BLOCK_KEY, textBlock);

        return mt103Message.toString();
    }

    /**
     * Method to replace amount strings as numbers from MT103 model JSON.
     *
     * @param textBlock JSON conversion of the model {@link MT103Message} (converted using Gson).
     */
    private static void replaceAmountAsNumber(JsonObject textBlock) {

        // TODO: remove if condition after implementing validator
        // Replace amount in value field
        if (textBlock.has(MT103Constants.VALUE_KEY)) {
            JsonObject value = textBlock.getAsJsonObject(MT103Constants.VALUE_KEY);

            value.add(MT103Constants.AMOUNT_KEY, getAmountAsNumber(value.get(MT103Constants.AMOUNT_KEY).getAsString()));

            textBlock.add(MT103Constants.VALUE_KEY, value);
        }

        // Replace amount in instructed amount field
        if (textBlock.has(MT103Constants.INSTRUCTED_AMOUNT_KEY)) {
            JsonObject instructedAmount = textBlock.getAsJsonObject(MT103Constants.INSTRUCTED_AMOUNT_KEY);

            instructedAmount.add(MT103Constants.AMOUNT_KEY,
                    getAmountAsNumber(instructedAmount.get(MT103Constants.AMOUNT_KEY).getAsString()));

            textBlock.add(MT103Constants.INSTRUCTED_AMOUNT_KEY, instructedAmount);
        }

        // Replace the exchange rate
        if (textBlock.has(MT103Constants.EXCHANGE_RATE_KEY)) {
            JsonObject exchangeRate = textBlock.getAsJsonObject(MT103Constants.EXCHANGE_RATE_KEY);

            exchangeRate.add(MT103Constants.VALUE_KEY,
                    getAmountAsNumber(exchangeRate.get(MT103Constants.VALUE_KEY).getAsString()));

            textBlock.add(MT103Constants.EXCHANGE_RATE_KEY, exchangeRate);
        }

        // Replace amount in all occurrences of sender's charges field
        if (textBlock.has(MT103Constants.SENDERS_CHARGES_KEY)) {
            JsonArray sendersCharges = textBlock.getAsJsonArray(MT103Constants.SENDERS_CHARGES_KEY);

            sendersCharges.forEach(sendersCharge -> {
                JsonObject jsonObject = sendersCharge.getAsJsonObject();

                jsonObject.add(MT103Constants.AMOUNT_KEY,
                        getAmountAsNumber(jsonObject.get(MT103Constants.AMOUNT_KEY).getAsString()));
            });
        }

        // Replace amount in receiver's charges field
        if (textBlock.has(MT103Constants.RECEIVERS_CHARGES_KEY)) {
            JsonObject receiversCharges = textBlock.getAsJsonObject(MT103Constants.RECEIVERS_CHARGES_KEY);

            receiversCharges.add(MT103Constants.AMOUNT_KEY,
                    getAmountAsNumber(receiversCharges.get(MT103Constants.AMOUNT_KEY).getAsString()));

            textBlock.add(MT103Constants.RECEIVERS_CHARGES_KEY, receiversCharges);
        }
    }

    /**
     * Method to convert amount string to a number and get amount as a JsonPrimitive.
     *
     * @param amount String in the format "100,00".
     * @return  A {@link JsonPrimitive} with transformed amount as the value.
     */
    private static JsonPrimitive getAmountAsNumber(String amount) {

        return new JsonPrimitive(Double.parseDouble(amount.replace(",", ".")));
    }

    /**
     * Method to remove No_letter option from the JSON object.
     *
     * @param textBlock A {@link JsonPrimitive} with transformed amount as the value.
     */
    private static void removeNoLetterOption(JsonObject textBlock) {

        for (String key : textBlock.keySet()) {

            // Since field can either be a JsonObject or JsonArray
            JsonElement jsonElement = textBlock.get(key);

            if (jsonElement instanceof JsonObject) {
                JsonObject field = jsonElement.getAsJsonObject();
                char option = field.get(MT103Constants.OPTION_KEY).getAsCharacter();

                if (option == ConnectorConstants.NO_LETTER_OPTION) {
                    field.remove(MT103Constants.OPTION_KEY);
                    textBlock.add(key, field);
                }
            }
        }
    }
}
