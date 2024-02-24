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

import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.ISO20022camt053ValidatorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.ISOMessageParser;
import org.wso2.carbon.module.swiftiso20022.utils.XSDValidator;
import org.xml.sax.SAXParseException;

/**
 * Validate the ISO20022.camt.053 message.
 */
public class ISO20022camt053Validator extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        boolean isBusinessMsg = false;
        String rootElementTag = ISOMessageParser.getRootXMLElement(messageContext);

        if (StringUtils.isBlank(rootElementTag) || (
                (!rootElementTag.equals(ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG) &&
                (!rootElementTag.equals(ConnectorConstants.XML_INPUT_DOCUMENT_TAG))))) {
            // Invalid XML root tag
            this.log.error(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG);

            super.handleException(ConnectorConstants.ERROR_INVALID_XML_ROOT_TAG, messageContext);
        }

        if (rootElementTag.equals(ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG)) {
            // Validate ISO 20022 message business header
            log.debug("Business application header present");
            try {
                String appHdrStr = ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_CAMT_053_APPHDR);

                XSDValidator appHdrValidator = new XSDValidator(ConnectorConstants.XSD_SCHEMA_HEAD_001_001);
                appHdrValidator.validateXMLContent(appHdrStr);
                isBusinessMsg = true;

                log.debug("Valid business application header");
            } catch (SAXParseException e) {
                String errMsg = e.getMessage() + ", Line number: " +
                        e.getLineNumber() + ", Column number: " + e.getColumnNumber();
                this.log.error(errMsg);
                ConnectorUtils.appendErrorToMessageContext(messageContext,
                        ConnectorConstants.ERROR_INVALID_ISO_HEAD001_XML_MSG,
                        errMsg);

                throw new ConnectException(errMsg);
            } catch (Exception e) {
                this.log.error(e.getMessage());
                ConnectorUtils.appendErrorToMessageContext(messageContext,
                        ConnectorConstants.ERROR_VALIDATING_XML, e.getMessage());

                throw new ConnectException(e.getMessage());
            }
        } else {
            log.debug("Business application header not present");
        }

        // Processing ISO20022.camt.053 message
        try {
            String documentStr;

            if (rootElementTag.equals(ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG)) {
                documentStr = ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR);
            } else {
                documentStr = ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITHOUT_BUSINESS_HDR);
            }

            XSDValidator documentValidator = new XSDValidator(ConnectorConstants.XSD_SCHEMA_CAMT_053_001);
            documentValidator.validateXMLContent(documentStr);

            // Validate Electronic Seq number and Legal number
            ISO20022camt053ValidatorUtils.validateElectronicSequenceNumber(isBusinessMsg, messageContext);
            ISO20022camt053ValidatorUtils.validateLegalSequenceNumber(isBusinessMsg, messageContext);

            // Validate Balance types
            ISO20022camt053ValidatorUtils.validateBalanceElements(isBusinessMsg, messageContext);

            log.debug("Valid camt.053.001.11 message");
        } catch (SAXParseException e) {
            String errMsg = e.getMessage() + ", Line number: " +
                    e.getLineNumber() + ", Column number: " + e.getColumnNumber();
            this.log.error(errMsg);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_INVALID_ISO_CAMT053_XML_MSG,
                    errMsg);

            throw new ConnectException(errMsg);
        } catch (Exception e) {
            this.log.error(e.getMessage());
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    ConnectorConstants.ERROR_VALIDATING_XML, e.getMessage());

            throw new ConnectException(e.getMessage());
        }

    }
}
