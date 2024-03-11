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
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.models.mt103models.MT103Message;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt103Utils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.Optional;


/**
 * Class to convert JSON Object to MT103 format.
 */
public class JsonToMT103Validator extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                    .getAxis2MessageContext();
            Optional<String> payload = ConnectorUtils.buildMessagePayloadFromMessageContext(axis2MessageContext);
            if (payload.isPresent()) {
                MT103Message requestPayload = (new Gson()).fromJson(payload.get(), MT103Message.class);
                ValidationResult validationResponse = validateRequestPayload(payload.get());
                if (!validationResponse.isValid()) {
                    this.log.error(validationResponse.getErrorMessage());
                    ConnectorUtils.appendErrorToMessageContext(messageContext, validationResponse.getErrorCode(),
                            validationResponse.getErrorMessage());
                    super.handleException(ConnectorConstants.ERROR_VALIDATION_FAILED, messageContext);
                }
            } else {
                this.log.error(ConnectorConstants.ERROR_MISSING_PAYLOAD);
                ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.MISSING_REQUEST_PAYLOAD,
                        ConnectorConstants.ERROR_MISSING_PAYLOAD);
                super.handleException(ConnectorConstants.ERROR_MISSING_PAYLOAD, messageContext);
            }
        } catch (JSONException | ConnectException e) {
            this.log.error(ConnectorConstants.PROCESSING_ERROR, e);
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.SERVER_ERROR,
                    ConnectorConstants.PROCESSING_ERROR);
            throw new ConnectException(e, ConnectorConstants.PROCESSING_ERROR);
        }
    }

    private ValidationResult validateRequestPayload(String payloadString) {

        JSONObject requestPayload = new JSONObject(payloadString);

        ValidationResult blockValidationResult =
                JsonToMt103Utils.validateBlock01(requestPayload.optJSONObject(MT103Constants.BLOCK01));
        if (!blockValidationResult.isValid()) {
            return blockValidationResult;
        }
        blockValidationResult = JsonToMt103Utils.validateBlock02(requestPayload.optJSONObject(MT103Constants.BLOCK02));
        if (!blockValidationResult.isValid()) {
            return blockValidationResult;
        }
        blockValidationResult = JsonToMt103Utils.validateBlock03(requestPayload.optJSONObject(MT103Constants.BLOCK03));
        if (!blockValidationResult.isValid()) {
            return blockValidationResult;
        }
        blockValidationResult = JsonToMt103Utils.validateBlock04(requestPayload.optJSONObject(MT103Constants.BLOCK04));
        if (!blockValidationResult.isValid()) {
            return blockValidationResult;
        }
        return JsonToMt103Utils.validateBlock05(requestPayload.optJSONObject(MT103Constants.BLOCK05));
    }
}
