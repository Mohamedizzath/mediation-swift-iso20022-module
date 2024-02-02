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
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.RequestPayload;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;

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
                ErrorModel validationResponse = requestPayload.validate();
                if (validationResponse.isError()) {
                    this.log.error(validationResponse.getErrorMessage());
                    ConnectorUtils.appendErrorToMessageContext(messageContext, validationResponse.getErrorCode(),
                            validationResponse.getErrorMessage());
                    this.handleException("Validation failed", messageContext);
                }
                // TODO: Implement validation logic
            } else {
                // TODO: replace with constants
                this.log.error("Request payload is missing.");
            }
        } catch (Exception e) {
            // TODO: add exception handling logic
            this.log.error(e.getMessage());
            throw new ConnectException(e);
        }
    }
}
