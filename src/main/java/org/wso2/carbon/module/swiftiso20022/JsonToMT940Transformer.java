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
import org.wso2.carbon.module.swiftiso20022.models.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt940Utils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationEngine;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.Optional;

/**
 * Class to convert the JSON payload to MT940 format.
 */
public class JsonToMT940Transformer extends AbstractConnector {

    private static final Gson gson = new Gson();

    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            this.log.debug("Executing JsonToMT940Transformer to convert the JSON payload to MT940 format");

            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                    .getAxis2MessageContext();
            Optional<String> payload = ConnectorUtils.buildMessagePayloadFromMessageContext(axis2MessageContext);

            if (payload.isPresent()) {
                RequestPayloadModel requestPayload = gson.fromJson(payload.get(), RequestPayloadModel.class);

                ValidationResult validationResponse = validateRequestPayload(requestPayload);
                if (validationResponse.isError()) {
                    this.log.error(validationResponse.getErrorMessage());
                    ConnectorUtils.appendErrorToMessageContext(messageContext, validationResponse.getErrorCode(),
                            validationResponse.getErrorMessage());
                    super.handleException(ConnectorConstants.ERROR_VALIDATION_FAILED, messageContext);
                }

                JSONObject mt940FormatJson = JsonToMt940Utils.appendConstructedFields(payload.get(), requestPayload);
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
    private ValidationResult validateRequestPayload(RequestPayloadModel requestPayload) {

        ValidationResult validationResult = ValidationEngine.getInstance()
                .addMandatoryParamValidationRules(JsonToMt940Utils.getMandatoryFieldsInPayload(requestPayload))
                .addOptionalParamValidationRule(new ValidatorContext(ConnectorConstants.ACC_NUMBER_IDENTIFICATION,
                        requestPayload.getAccountNumberIdentifier()))
                .addParameterLengthValidationRules(JsonToMt940Utils.
                        getFieldsInPayloadForLengthValidation(requestPayload))
                .addAlphaNumericParamValidationRules(JsonToMt940Utils.getAlphaNumericFieldsInPayload(requestPayload))
                .addNumericParamValidationRules(JsonToMt940Utils.getNumericFieldsInPayload(requestPayload))
                .validate();

        if (validationResult.isError()) {
            return validationResult;
        }

        ValidationResult balanceValidationResult = JsonToMt940Utils.validateBalances(requestPayload);
        if (balanceValidationResult.isError()) {
            return balanceValidationResult;
        }

        ValidationResult transactionValidationResult = JsonToMt940Utils
                .validateTransactionDetails(requestPayload.getTransactions());
        if (transactionValidationResult.isError()) {
            return transactionValidationResult;
        }
        return new ValidationResult();
    }
}
