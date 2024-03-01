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
import org.jaxen.JaxenException;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.ISOMessageParser;
import org.wso2.carbon.module.swiftiso20022.utils.XSDValidator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.IOException;

/**
 * Validate the ISO20022.head.001 message.
 */
public class ISO20022AppHead001Validator extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String rootElementTag = ISOMessageParser.getRootXMLElement(messageContext);

        if (!ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG.equals(rootElementTag)) {
            // Invalid XML root tag
            this.log.error(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);

            super.handleException(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, messageContext);
        }

        try {
            String xPathToAppHdr = ISOMessageParser.constructXPath(true, ConnectorConstants.XPATH_APPHDR);
            String appHdrStr = ISOMessageParser.extractISOMessage(messageContext, xPathToAppHdr);

            XSDValidator appHdrValidator = new XSDValidator(ConnectorConstants.XSD_SCHEMA_HEAD_001_001);
            appHdrValidator.validateXMLContent(appHdrStr);
            this.log.debug("Valid business application header");
        } catch (SAXParseException e) {
            String errMsg = String.format("%s, Line number: %d, Column number: %d",
                    e.getMessage(), e.getLineNumber(), e.getColumnNumber());

            this.log.error(e);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_ISO_CAMT053_XML_MSG,
                    errMsg);

            throw new ConnectException(e, errMsg);
        } catch (SAXException | JaxenException | IOException e) {
            this.log.error(e);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_VALIDATING_XML, e.getMessage());

            throw new ConnectException(e, e.getMessage());
        }
    }
}