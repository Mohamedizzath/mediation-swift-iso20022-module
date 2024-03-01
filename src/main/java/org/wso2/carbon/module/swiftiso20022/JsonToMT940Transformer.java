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
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.models.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt940Utils;
import org.wso2.carbon.module.swiftiso20022.validation.JsonToMT940PayloadValidator;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.Optional;

/**
 * Class to convert the JSON payload to MT940 format.
 */
public class JsonToMT940Transformer extends AbstractConnector {

    private static final Gson gson = new Gson();

    /**
     * This method will validate the input payload format and
     * construct the fields required to generate the MT940 format.
     *
     * @param messageContext  Message Context
     * @throws ConnectException  If an error occurs while processing the request
     */
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            this.log.debug("Executing JsonToMT940Transformer to convert the JSON payload to MT940 format");

            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                    .getAxis2MessageContext();
            Optional<String> optionalPayload = ConnectorUtils
                    .buildMessagePayloadFromMessageContext(axis2MessageContext);

            if (optionalPayload.isPresent()) {
                RequestPayloadModel requestPayload = gson.fromJson(optionalPayload.get(), RequestPayloadModel.class);

                ValidationResult validationResult = validateRequestPayload(optionalPayload.get());
                if (!validationResult.isValid()) {
                    this.log.error(validationResult.getErrorMessage());
                    ConnectorUtils.appendErrorToMessageContext(messageContext, validationResult.getErrorCode(),
                            validationResult.getErrorMessage());
                    super.handleException(ConnectorConstants.ERROR_VALIDATION_FAILED, messageContext);
                }

                JSONObject mt940FormatJson = JsonToMt940Utils.appendConstructedFields(optionalPayload.get(),
                        requestPayload);
                ConnectorUtils.appendJsonResponseToMessageContext(messageContext, mt940FormatJson.toString());
            } else {
                this.log.error(ConnectorConstants.ERROR_MISSING_PAYLOAD);
                ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.MISSING_REQUEST_PAYLOAD,
                        ConnectorConstants.ERROR_MISSING_PAYLOAD);
                super.handleException(ConnectorConstants.ERROR_MISSING_PAYLOAD, messageContext);
            }

        } catch (JSONException | AxisFault | ConnectException e) {
            this.log.error(ConnectorConstants.PROCESSING_ERROR, e);
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.SERVER_ERROR,
                    ConnectorConstants.PROCESSING_ERROR);
            throw new ConnectException(e, ConnectorConstants.PROCESSING_ERROR);
        }
    }

    /**
     * Method to validate the request payload.
     *
     * @param requestPayload  Request payload
     * @return               Validation Result if there is an error, else empty Validation Result
     */
    private ValidationResult validateRequestPayload(String requestPayload) {

        JSONObject requestPayloadJson = new JSONObject(requestPayload);

        ValidationResult validationResult = JsonToMT940PayloadValidator.getMT940ValidationEngine()
                .validate(requestPayloadJson);

        if (!validationResult.isValid()) {
            return validationResult;
        }

        ValidationResult balanceValidationResult = JsonToMt940Utils.validateBalances(requestPayloadJson);
        if (!balanceValidationResult.isValid()) {
            return balanceValidationResult;
        }

        ValidationResult transactionValidationResult = JsonToMt940Utils
                .validateTransactionDetails(requestPayloadJson.getJSONArray(MT940Constants.TRANSACTIONS));
        if (!transactionValidationResult.isValid()) {
            return transactionValidationResult;
        }
        return new ValidationResult();
    }
}
