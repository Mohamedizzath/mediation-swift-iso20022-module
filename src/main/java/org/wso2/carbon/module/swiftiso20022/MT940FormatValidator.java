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

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Class to validate the MT940 format.
 */
public class MT940FormatValidator extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        log.debug("Executing MT940FormatValidator to validate the MT940 format");
        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        Optional<String> payload = ConnectorUtils.buildMessagePayloadFromMessageContext(axis2MessageContext);
        if (payload.isPresent()) {
            String[] lines = payload.get().split("\n");
            ErrorModel validationResponse = validateMT940(messageContext, lines);

            if (validationResponse.isError()) {
                this.log.error(validationResponse.getErrorMessage());
                ConnectorUtils.appendErrorToMessageContext(messageContext, validationResponse.getErrorCode(),
                        validationResponse.getErrorMessage());
                this.handleException(ConnectorConstants.ERROR_VALIDATION_FAILED, messageContext);
            }

        } else {
            this.log.error(ConnectorConstants.ERROR_MISSING_PAYLOAD);
            ConnectorUtils.appendErrorToMessageContext(messageContext, ConnectorConstants.MISSING_REQUEST_PAYLOAD,
                    ConnectorConstants.ERROR_MISSING_PAYLOAD);
            this.handleException(ConnectorConstants.ERROR_MISSING_PAYLOAD, messageContext);
        }
    }

    /**
     * Method to validate the MT940 format.
     *
     * @param messageContext  Message context
     * @param lines           Payload lines
     * @return                Error model
     */
    private ErrorModel validateMT940(MessageContext messageContext, String[] lines) {
        ErrorModel errorModel = new ErrorModel();
        Map<String, Object> extractFields = extractFields(lines);

        if (!MT940ValidationUtils.validateC1Rule(lines)) {
            return new ErrorModel(ConnectorConstants.ERROR_C24,
                    ConnectorConstants.ERROR_FIELD_86);
        }

        if (!MT940ValidationUtils.validateC2Rule(lines)) {
            return new ErrorModel(ConnectorConstants.ERROR_C277,
                    ConnectorConstants.ERROR_BALANCES);
        }

        errorModel = MT940ValidationUtils.validateMT940Format(extractFields);
        if (errorModel.isError()) {
            this.log.error(errorModel.getErrorMessage());
            ConnectorUtils.appendErrorToMessageContext(messageContext, errorModel.getErrorCode(),
                    errorModel.getErrorMessage());
            this.handleException(errorModel.getErrorMessage(), messageContext);
        }

        errorModel.setIsError(false);
        return errorModel;
    }

    /**
     * Method to extract the fields from the payload.
     *
     * @param lines  Payload lines
     * @return       Extracted fields
     */
    private Map<String, Object> extractFields(String[] lines) {
        Map<String, Object> fields = new HashMap<>();
        List<String> statementLine = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("{") || line.startsWith("-")) {
                continue;
            }
            switch (line.substring(0, 3)) {
                case ConnectorConstants.MT940_TRANSACTION_REF :
                    fields.put(ConnectorConstants.TRANSACTION_REF, line);
                    break;
                case ConnectorConstants.MT940_RELATED_REF :
                    fields.put(ConnectorConstants.RELATED_REF, line);
                    break;
                case ConnectorConstants.MT940_ACCOUNT_NO :
                    fields.put(ConnectorConstants.ACC_IDENTIFICATION, line);
                    break;
                case ConnectorConstants.MT940_STATEMENT_NO :
                    fields.put(ConnectorConstants.STATEMENT_NUMBER, line);
                    break;
                case ConnectorConstants.MT940_OPENING_BAL :
                    fields.put(ConnectorConstants.OPENING_BALANCE, line);
                    break;
                case ConnectorConstants.MT940_STATEMENT_LINE :
                    statementLine.add(line);
                    fields.put(ConnectorConstants.STATEMENT_LINE, statementLine);
                    break;
                case ConnectorConstants.MT940_CLOSING_BAL :
                    fields.put(ConnectorConstants.CLOSING_BALANCE, line);
                    break;
                case ConnectorConstants.MT940_CLOSING_AVAIL_BAL :
                    fields.put(ConnectorConstants.CLOSING_AVAIL_BALANCE, line);
                    break;
                case ConnectorConstants.MT940_FORWARD_AVAIL_BAL :
                    fields.put(ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE, line);
                    break;
                default:
                    break;

            }
//            if (line.startsWith(ConnectorConstants.MT940_TRANSACTION_REF)) {
//                fields.put(ConnectorConstants.TRANSACTION_REF, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_RELATED_REF)) {
//                fields.put(ConnectorConstants.RELATED_REF, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_ACCOUNT_NO)) {
//                fields.put(ConnectorConstants.ACC_IDENTIFICATION, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_STATEMENT_NO)) {
//                fields.put(ConnectorConstants.STATEMENT_NUMBER, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_OPENING_BAL)) {
//                fields.put(ConnectorConstants.OPENING_BALANCE, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_STATEMENT_LINE)) {
//                statementLine.add(line);
//                fields.put(ConnectorConstants.STATEMENT_LINE, statementLine);
//            } else if (line.startsWith(ConnectorConstants.MT940_CLOSING_BAL)) {
//                fields.put(ConnectorConstants.CLOSING_BALANCE, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_CLOSING_AVAIL_BAL)) {
//                fields.put(ConnectorConstants.CLOSING_AVAIL_BALANCE, line);
//            } else if (line.startsWith(ConnectorConstants.MT940_FORWARD_AVAIL_BAL)) {
//                fields.put(ConnectorConstants.FORWARD_CLOSING_AVAIL_BALANCE, line);
//            }
        }
        return fields;
    }
}
