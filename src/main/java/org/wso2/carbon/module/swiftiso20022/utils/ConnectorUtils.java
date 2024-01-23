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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.transport.passthru.PassThroughConstants;
import org.apache.synapse.transport.passthru.util.RelayUtils;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;

/**
 * Class to hold the utility methods.
 */
public class ConnectorUtils {
    private static final Log log = LogFactory.getLog(ConnectorUtils.class);

    /**
     * Build Message and extract payload.
     *
     * @param axis2MC message context
     * @return optional string message
     * @throws ConnectException thrown if unable to build
     */
    public static Optional<String> buildMessagePayloadFromMessageContext(MessageContext axis2MC)
            throws ConnectException {
        if (!isMessageContextBuilt(axis2MC)) {
            try {
                RelayUtils.buildMessage(axis2MC);
            } catch (XMLStreamException | IOException e) {
                log.error("Unable to build axis2 message", e);
                throw new ConnectException(e, "Unable to build axis2 message");
            }
        }

        try {
            InputStream jsonPayload = JsonUtil.getJsonPayload(axis2MC);
            if (jsonPayload != null) {
                return Optional.ofNullable(IOUtils.toString(JsonUtil.getJsonPayload(axis2MC),
                        StandardCharsets.UTF_8.name()));
            }
        } catch (IOException e) {
            log.error("Unable to read payload stream", e);
            throw new ConnectException(e, "Unable to read payload stream");
        }
        return Optional.empty();
    }

    /**
     * Util method to check whether the message context is already built.
     *
     * @param axis2MC axis2 message context
     * @return true if message context is already built
     */
    public static boolean isMessageContextBuilt(MessageContext axis2MC) {
        Object messageContextBuilt = axis2MC.getProperty(PassThroughConstants.MESSAGE_BUILDER_INVOKED);
        if (messageContextBuilt != null) {
            return (Boolean) messageContextBuilt;
        }
        return false;
    }

    /**
     * Method to append JSON response to the message context.
     *
     * @param messageContext   Message Context
     * @param payload          Response Payload
     * @throws AxisFault
     * @throws XMLStreamException
     */
    public static void appendJsonResponseToMessageContext(org.apache.synapse.MessageContext messageContext,
                                                          String payload) throws AxisFault, XMLStreamException {
        MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        axis2MessageContext.setProperty(Constants.Configuration.MESSAGE_TYPE, ConnectorConstants.APPLICATION_JSON);
        axis2MessageContext.setProperty(Constants.Configuration.CONTENT_TYPE, ConnectorConstants.APPLICATION_JSON);
        JsonUtil.getNewJsonPayload(axis2MessageContext, payload, true, true);
    }

    /**
     * Method to append the error details to the message context.
     * @param messageContext   Message Context
     * @param errorCode        Error Code
     * @param errorMessage     Error Message
     */
    public static void appendErrorToMessageContext(org.apache.synapse.MessageContext messageContext, String errorCode,
                                                   String errorMessage) {
        messageContext.setProperty(ConnectorConstants.ERROR_CODE, errorCode);
        messageContext.setProperty(ConnectorConstants.ERROR_MESSAGE, errorMessage);
    }
}
