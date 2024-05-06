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

import com.google.gson.Gson;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.testng.annotations.DataProvider;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;

import java.io.StringReader;
import java.util.Map;

/**
 * Contains test constants for testing payload factory mediator for
 * converting MT940 message to ISO20022.
 */
public class MT940ToISO20022PayloadFactoryTestConstants {
    private static final Gson gson = new Gson();
    public static String getMT940JSON(Map<String, String> params) throws Exception {
        String mtMessageText = MTParserTestConstants.getMTMessageText(params);

        return gson.toJson(MT940Parser.parse(mtMessageText));
    }

    public static String getXMLElement(String xmlMessage, String xPath) throws Exception {
        AXIOMXPath xpathExp = new AXIOMXPath(xPath);

        // Setting up XML namespaces for AXIOM SOAP, AppHdr, and Document
        xpathExp.addNamespace(ConnectorConstants.APPHDR_PREFIX, ConnectorConstants.XML_INPUT_APPHDR_NAMESPACE);
        xpathExp.addNamespace(ConnectorConstants.DOCUMENT_PREFIX, ConnectorConstants.XML_INPUT_DOCUMENT_NAMESPACE);

          // OMElement rootElement = AXIOMUtil.stringToOM(xmlMessage);
          OMNode rootElement = OMXMLBuilderFactory.createOMBuilder(new StringReader(xmlMessage)).getDocumentElement();
          OMElement targetNode = (OMElement) xpathExp.selectSingleNode(rootElement);

          return targetNode != null ? targetNode.getText() : null;
    }
    // Add the AppHdrFrom element
    @DataProvider(name = "parseAppHdrToElement")
    Object[][] parseAppHdrToElement() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "{1:F01GSCRUS30MXXX0000000000}")),
                "GSCRUS30XXX"}
        };
    }
}
