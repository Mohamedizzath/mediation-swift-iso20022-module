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

import org.apache.axiom.om.OMException;
import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.ISOMessageParser;
import org.wso2.carbon.module.swiftiso20022.utils.XSDValidator;
import org.xml.sax.SAXParseException;

/**
 * Validate the ISO20022.head.001 message.
 */
public class ISO20022head001Validator extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String rootElementTag = ISOMessageParser.getRootXMLElement(messageContext);

        if (StringUtils.isBlank(rootElementTag) ||
                !rootElementTag.equals(ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG)) {
            // Invalid XML root tag
            this.log.error(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);

            super.handleException(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, messageContext);
        }

        try {
            String appHdrStr = ISOMessageParser.extractISOMessage(messageContext,
                    ConnectorConstants.XPATH_CAMT_053_APPHDR);

            XSDValidator appHdrValidator = new XSDValidator(ConnectorConstants.XSD_SCHEMA_HEAD_001_001);
            appHdrValidator.validateXMLContent(appHdrStr);
            log.debug("Valid business application header");
        } catch (SAXParseException | OMException e) {
            this.log.error(ConnectorConstants.ERROR_INVALID_ISO_HEAD001_XML_MSG);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_ISO_HEAD001_XML_MSG,
                    e.getMessage());

            throw new ConnectException(e.getMessage());
        } catch (Exception e) {
            this.log.error(e.getMessage());
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_VALIDATING_XML, e.getMessage());

            throw new ConnectException(e, e.getMessage());
        }
    }
}
