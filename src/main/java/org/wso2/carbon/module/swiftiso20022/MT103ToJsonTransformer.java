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
import com.google.gson.JsonObject;
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
import org.wso2.carbon.module.swiftiso20022.utils.JsonTransformationUtils;

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

            String jsonString = transformJsonObject(gson.toJsonTree(mt103Message).getAsJsonObject());

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

    /**
     * Method to transform JSON object of {@link MT103Message}.
     * Replace amount strings as number.
     * Remove no letter option.
     *
     * @param mt103Message JSON conversion of {@link MT103Message} using Gson.
     * @return Transformed JSON object as a String.
     */
    private static String transformJsonObject(JsonObject mt103Message) {

        JsonObject textBlock = mt103Message.getAsJsonObject(MT103Constants.TEXT_BLOCK_KEY);

        replaceAmountAsNumber(textBlock);

        JsonTransformationUtils.removeNoLetterOption(textBlock);

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
            JsonTransformationUtils.replaceAmountAsNumber(
                    textBlock.getAsJsonObject(MT103Constants.VALUE_KEY), MT103Constants.AMOUNT_KEY);
        }

        // Replace amount in instructed amount field
        if (textBlock.has(MT103Constants.INSTRUCTED_AMOUNT_KEY)) {
            JsonTransformationUtils.replaceAmountAsNumber(
                    textBlock.getAsJsonObject(MT103Constants.INSTRUCTED_AMOUNT_KEY), MT103Constants.AMOUNT_KEY);
        }

        // Replace the exchange rate
        if (textBlock.has(MT103Constants.EXCHANGE_RATE_KEY)) {
            JsonTransformationUtils.replaceAmountAsNumber(
                    textBlock.getAsJsonObject(MT103Constants.EXCHANGE_RATE_KEY), MT103Constants.VALUE_KEY);
        }

        // Replace amount in all occurrences of sender's charges field
        if (textBlock.has(MT103Constants.SENDERS_CHARGES_KEY)) {
            JsonArray sendersCharges = textBlock.getAsJsonArray(MT103Constants.SENDERS_CHARGES_KEY);

            sendersCharges.forEach(sendersCharge -> {
                JsonTransformationUtils.replaceAmountAsNumber(
                        sendersCharge.getAsJsonObject(), MT103Constants.AMOUNT_KEY);
            });
        }

        // Replace amount in receiver's charges field
        if (textBlock.has(MT103Constants.RECEIVERS_CHARGES_KEY)) {
            JsonTransformationUtils.replaceAmountAsNumber(
                    textBlock.getAsJsonObject(MT103Constants.RECEIVERS_CHARGES_KEY), MT103Constants.AMOUNT_KEY);
        }
    }

}
