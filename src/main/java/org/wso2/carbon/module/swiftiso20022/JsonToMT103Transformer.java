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
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.RequestPayload;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block01;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block03;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block04;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt103Utils;

import java.util.Optional;


/**
 * Class to convert JSON Object to MT103 format.
 */
public class JsonToMT103Transformer extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                    .getAxis2MessageContext();
            Optional<String> payload = ConnectorUtils.buildMessagePayloadFromMessageContext(axis2MessageContext);
            if (payload.isPresent()) {
                RequestPayload requestPayload = (new Gson()).fromJson(payload.get(), RequestPayload.class);
                ErrorModel validationResponse = validateRequestPayload(requestPayload);
                if (validationResponse.isError()) {
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

    private ErrorModel validateRequestPayload(RequestPayload requestPayload) {
        ErrorModel blockValidationResponse;
        if (requestPayload.getBlock01() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_H01,
                    String.format(ConnectorConstants.ERROR_BLOCK_MISSING, Block01.getBlockName()));
        } else {
            blockValidationResponse = JsonToMt103Utils.validateBlock01(requestPayload.getBlock01());
            if (blockValidationResponse.isError()) {
                return blockValidationResponse;
            }
        }
        if (requestPayload.getBlock02() != null) {
            blockValidationResponse = JsonToMt103Utils.validateBlock02(requestPayload.getBlock02());
            if (blockValidationResponse.isError()) {
                return blockValidationResponse;
            }
        }
        if (requestPayload.getBlock03() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_U00,
                    String.format(ConnectorConstants.ERROR_BLOCK_MISSING, Block03.getBlockName()));
        } else {
            blockValidationResponse = JsonToMt103Utils.validateBlock03(requestPayload.getBlock03());
            if (blockValidationResponse.isError()) {
                return blockValidationResponse;
            }
        }
        if (requestPayload.getBlock04() ==  null) {
            return new ErrorModel(ConnectorConstants.ERROR_V01,
                    String.format(ConnectorConstants.ERROR_BLOCK_MISSING, Block04.getBlockName()));
        } else {
            blockValidationResponse = JsonToMt103Utils.validateBlock04(requestPayload.getBlock04());
            if (blockValidationResponse.isError()) {
                return blockValidationResponse;
            }
        }
        if (requestPayload.getBlock05() != null) {
            blockValidationResponse = JsonToMt103Utils.validateBlock05(requestPayload.getBlock05());
            if (blockValidationResponse.isError()) {
                return blockValidationResponse;
            }
        }
        return new ErrorModel();
    }
}
