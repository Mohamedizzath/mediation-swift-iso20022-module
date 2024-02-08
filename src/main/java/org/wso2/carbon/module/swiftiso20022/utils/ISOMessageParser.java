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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.synapse.MessageContext;
import org.jaxen.JaxenException;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;

/**
 * Extract part of the ISO 20022 XML message from message context.
 */
public class ISOMessageParser {
    /**
     * Read the XML input from MessageContext and return the content of child elements of the parent tag as a String.
     * @param mc         MessageContext which contains the XML 20022 input
     * @param xPath      Xpath for the XML contents which needs to be extracted
     * @return           Child contents as a String element
     */
    public static String extractISOMessage(MessageContext mc, String xPath) throws ConnectException, JaxenException {
        AXIOMXPath xpathExp = new AXIOMXPath(xPath);

        // Setting up XML namespaces for AXIOM SOAP, AppHdr, and Document
        xpathExp.addNamespace(ConnectorConstants.SOAP_PREFIX, ConnectorConstants.SOAP_NAMESPACE);
        xpathExp.addNamespace(ConnectorConstants.APPHDR_PREFIX, ConnectorConstants.XML_INPUT_APPHDR_NAMESPACE);
        xpathExp.addNamespace(ConnectorConstants.DOCUMENT_PREFIX, ConnectorConstants.XML_INPUT_DOCUMENT_NAMESPACE);

        try {
            OMNode rootElement = mc.getEnvelope().getBody();
            OMElement element = (OMElement) xpathExp.selectSingleNode(rootElement);

            if (element == null) {
                // No element to be extracted
                throw new ConnectException("Error: " + xPath + " element not present in the XML");
            }

            return element.toString();
        } catch (OMException e) {
            throw new ConnectException("Error: Parsing XML document");
        }
    }

    /**
     * Read the ISO message from MessageContext and return the Root element tag name.
     * @param mc         MessageContext which contains the XML 20022 input
     * @return           Root XML tag name
     */
    public static String getRootXMLElement(MessageContext mc) {
        OMElement rootElement = mc.getEnvelope().getBody().getFirstElement();
        return rootElement.getLocalName();
    }
}
