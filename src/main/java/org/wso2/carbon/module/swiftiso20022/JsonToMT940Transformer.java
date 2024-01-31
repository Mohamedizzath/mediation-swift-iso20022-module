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
import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.RequestPayloadModel;
import org.wso2.carbon.module.swiftiso20022.mt940models.TransactionModel;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt940Utils;
import org.wso2.carbon.module.swiftiso20022.utils.ValidatorUtils;

import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;

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

                ErrorModel validationResponse = validateRequestPayload(requestPayload);
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

        } catch (JSONException | AxisFault | XMLStreamException | ConnectException e) {
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
     * @return               Error model if there is an error, else empty error model
     */
    private ErrorModel validateRequestPayload(RequestPayloadModel requestPayload) {

        if (StringUtils.isBlank(requestPayload.getBlock1())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13, ConnectorConstants.ERROR_BLOCK1_INVALID);
        }

        if (StringUtils.isBlank(requestPayload.getBlock2())) {
            return new ErrorModel(ConnectorConstants.ERROR_T13, ConnectorConstants.ERROR_BLOCK2_INVALID);
        }

        if (!JsonToMt940Utils.validateAccountNumber(requestPayload)) {
            return new ErrorModel(ConnectorConstants.ERROR_M50, ConnectorConstants.ERROR_ACC_NO_INVALID);
        }

        if (!JsonToMt940Utils.validateReference(requestPayload.getReference())) {
            return new ErrorModel(ConnectorConstants.ERROR_M50, ConnectorConstants.ERROR_REF_INVALID);
        }

        if (!JsonToMt940Utils.validateSequenceNumber(requestPayload)) {
            return new ErrorModel(ConnectorConstants.ERROR_M50, ConnectorConstants.ERROR_SEQ_NO_INVALID);
        }

        if (requestPayload.getOpeningBalanceDetails() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_BALANCE_MISSING, ConnectorConstants.OPENING_BALANCE));
        }

        ErrorModel errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getOpeningBalanceDetails(),
                ConnectorConstants.OPENING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }

        if (requestPayload.getClosingBalanceDetails() == null) {
            return new ErrorModel(ConnectorConstants.ERROR_T13,
                    String.format(ConnectorConstants.ERROR_BALANCE_MISSING, ConnectorConstants.CLOSING_BALANCE));
        }

        errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getClosingBalanceDetails(),
                ConnectorConstants.CLOSING_BALANCE);
        if (errorModel.isError()) {
            return errorModel;
        }

        if (requestPayload.getClosingAvailableBalanceDetails() != null) {
            errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getClosingAvailableBalanceDetails(),
                    ConnectorConstants.CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }

        if (requestPayload.getForwardAvailableBalanceDetails() != null) {
            errorModel = JsonToMt940Utils.validateBalanceDetails(requestPayload.getForwardAvailableBalanceDetails(),
                    ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE);
            if (errorModel.isError()) {
                return errorModel;
            }
        }

        ErrorModel error = validateTransactionDetails(requestPayload.getTransactions());
        if (error.isError()) {
            return error;
        }

        error.setIsError(false);
        return error;
    }

    /**
     * Method to validate the transaction details.
     *
     * @param transactions  List of transactions
     * @return              Error model if there is an error, else empty error model
     */
    private static ErrorModel validateTransactionDetails(List<TransactionModel> transactions) {
        ErrorModel error = new ErrorModel();

        for (TransactionModel transaction : transactions) {
            if (!JsonToMt940Utils.isValidDateFormat(transaction.getDateTime())) {
                return new ErrorModel(ConnectorConstants.INVALID_REQUEST_PAYLOAD,
                        ConnectorConstants.ERROR_DATE_TIME_INVALID);
            }

            error = JsonToMt940Utils.isValidTransactionType(transaction.getTransactionType());
            if (error.isError()) {
                return error;
            }

            if (!ValidatorUtils.isValidCurrency(transaction.getCurrency())) {
                return new ErrorModel(ConnectorConstants.ERROR_T52,
                        String.format(ConnectorConstants.ERROR_CURRENCY_CODE_INVALID,
                                ConnectorConstants.STATEMENT_LINE));
            }

            if (!JsonToMt940Utils.isValidDebitCreditMark(transaction.getTransactionIndicator())) {
                return new ErrorModel(ConnectorConstants.ERROR_T51,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.STATEMENT_LINE));
            }

            error = ValidatorUtils.validateAmountLength(transaction.getAmount(), ConnectorConstants.STATEMENT_LINE);
            if (error.isError()) {
                return error;
            }

            if (!JsonToMt940Utils.validateReference(transaction.getCustomerReference())) {
                return new ErrorModel(ConnectorConstants.ERROR_M50, ConnectorConstants.ERROR_CUS_REF_INVALID);
            }

            if (!JsonToMt940Utils.validateReference(transaction.getTransactionReference())) {
                return new ErrorModel(ConnectorConstants.ERROR_M50, ConnectorConstants.ERROR_TRANS_REF_INVALID);
            }
        }

        error.setIsError(false);
        return error;
    }
}
