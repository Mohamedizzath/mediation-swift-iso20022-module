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
import com.google.gson.JsonObject;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.testng.annotations.DataProvider;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains test constants for testing payload factory mediator for
 * converting MT940 message to ISO20022.
 */
public class MT940ToISO20022PayloadFactoryTestConstants {
    private static final Gson gson = new Gson();
    public static String getMT940JSON(Map<String, String> params) throws Exception {
        MT940Message mt940Message = MT940Parser.parse(MTParserTestConstants.getMTMessageText(params));
        JsonObject mt940JsonObject = (JsonObject) gson.toJsonTree(mt940Message);

        MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObject);
        MT940JSONParserUtils.updateDatesFromMT940(mt940JsonObject);
        MT940JSONParserUtils.addBICToMT940Message(mt940JsonObject);

        return mt940JsonObject.toString();
    }

    public static OMElement getXMLElement(String xmlMessage, String xPath) throws Exception {
        AXIOMXPath xpathExp = new AXIOMXPath(xPath);

        // Setting up XML namespaces for AXIOM SOAP, AppHdr, and Document
        xpathExp.addNamespace(ConnectorConstants.APPHDR_PREFIX, ConnectorConstants.XML_INPUT_APPHDR_NAMESPACE);
        xpathExp.addNamespace(ConnectorConstants.DOCUMENT_PREFIX, ConnectorConstants.XML_INPUT_DOCUMENT_NAMESPACE);

          // OMElement rootElement = AXIOMUtil.stringToOM(xmlMessage);
          OMNode rootElement = OMXMLBuilderFactory.createOMBuilder(new StringReader(xmlMessage)).getDocumentElement();
          return (OMElement) xpathExp.selectSingleNode(rootElement);
    }

    public static String getXMLNodeText(String xmlMessage, String xPath) throws Exception {
        OMElement targetNode = getXMLElement(xmlMessage, xPath);

        return targetNode != null ? targetNode.getText() : null;
    }

    public static List<OMElement> getXMLElements(String xmlMessage, String xPath) throws Exception {
        AXIOMXPath xpathExp = new AXIOMXPath(xPath);

        // Setting up XML namespaces for AXIOM SOAP, AppHdr, and Document
        xpathExp.addNamespace(ConnectorConstants.APPHDR_PREFIX, ConnectorConstants.XML_INPUT_APPHDR_NAMESPACE);
        xpathExp.addNamespace(ConnectorConstants.DOCUMENT_PREFIX, ConnectorConstants.XML_INPUT_DOCUMENT_NAMESPACE);

        // OMElement rootElement = AXIOMUtil.stringToOM(xmlMessage);
        OMNode rootElement = OMXMLBuilderFactory.createOMBuilder(new StringReader(xmlMessage)).getDocumentElement();

        return (List<OMElement>) xpathExp.selectNodes(rootElement);
    }

    public static List<String> getXMLNodesTexts(String xmlMessage, String xPath) throws Exception {
        List<OMElement> targetNodes = getXMLElements(xmlMessage, xPath);

        return targetNodes.stream().map(OMElement::getText).collect(Collectors.toList());
    }

    @DataProvider(name = "parseAppHdrFromElement")
    Object[][] parseAppHdrFromElement() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        "{2:O9400400190425GSCRUS30PRTQ00000000002403290912N}")), "GSCRUS30RTQ"},
        };
    }

    @DataProvider(name = "parseAppHdrToElement")
    Object[][] parseAppHdrToElement() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "{1:F01GSCRUS30PRTQ0000000000}")),
                "GSCRUS30RTQ"}
        };
    }

    @DataProvider(name = "parseMessageCreatedDt")
    Object[][] parseMessageCreatedDt() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        "{2:O9401230210130GSCRUS30PRTQ00000000002403290912N}")), "2021-01-30T12:30:00Z"},
                {getMT940JSON(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        "{2:O9402330181005GSCRUS30PRTQ00000000002403290912N}")), "2018-10-05T23:30:00Z"},
        };
    }

    @DataProvider(name = "parseStatementNumber")
    Object[][] parseStatementNumber() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "1"},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:12\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "12"}
        };
    }

    @DataProvider(name = "parseSequenceNumber")
    Object[][] parseSequenceNumber() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/12\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "12"},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:12/30405\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "30405"}
        };
    }

    @DataProvider(name = "parseTransactionRefNum")
    Object[][] parseTransactionRefNum() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/12\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "258158850"},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:2581588502581588\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:12/30405\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "2581588502581588"}
        };
    }

    @DataProvider(name = "parseAccountIdentification")
    Object[][] parseAccountIdentification() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "DD01100056869"},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:2581588502581588\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\nGSCRUS30RTQ\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "DD01100056869"},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:2581588502581588\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869DD01100056869DD0110567\n" +
                        ":28C:12/31\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD846665,15\n" +
                        ":64:C231002USD334432401,27\n-}")), "DD01100056869DD01100056869DD0110567"},
        };
    }

    @DataProvider(name = "parseBalances")
    Object[][] parseBalances() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n" +
                        ":64:D230930USD843686,20\n" +
                        ":65:D230930USD843686,20\n-}")), Map.of("Currency", "USD",
                "DCMark", "DBIT", "Date", "2023-09-30", "Amount", "843686.20")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:C230930USD843686,\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:C230930USD843686,\n" +
                        ":64:C230930USD843686,\n" +
                        ":65:C230930USD843686,\n-}")), Map.of("Currency", "USD",
                        "DCMark", "CRDT", "Date", "2023-09-30", "Amount", "843686.00")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD0,89\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD0,89\n" +
                        ":64:D230930USD0,89\n" +
                        ":65:D230930USD0,89\n-}")), Map.of("Currency", "USD",
                        "DCMark", "DBIT", "Date", "2023-09-30", "Amount", "0.89")},
        };
    }

    @DataProvider(name = "parseEntryAmount")
    Object[][] parseEntryAmount() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("10.00")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RCD0,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RCD28,ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("10.00", "0.20", "28.00")},
        };
    }

    @DataProvider(name = "parseCreditDebitIndicator")
    Object[][] parseCreditDebitIndicator() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("CRDT")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001CD0,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RDD28,ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("DBIT", "CRDT", "DBIT")},
        };
    }

    @DataProvider(name = "parseReversalIndicator")
    Object[][] parseReversalIndicator() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("true")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001CD0,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RDD28,ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("false", "false", "true")},
        };
    }

    @DataProvider(name = "parseEntryBookingDt")
    Object[][] parseEntryBookingDt() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:231001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of()},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of(LocalDate.now().getYear() + "-10-01")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011216CD0,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310010826RDD28,ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of(LocalDate.now().getYear() + "-10-01",
                        LocalDate.now().getYear() + "-12-16", LocalDate.now().getYear() + "-08-26")},
        };
    }

    @DataProvider(name = "parseEntryValueDt")
    Object[][] parseEntryValueDt() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("2023-10-01")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:1905201216CD0,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:1709210826RDD28,ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("2023-10-01", "2019-05-20", "2017-09-21")},
        };
    }

    @DataProvider(name = "parseEntryAcctServiceInstRef")
    Object[][] parseEntryAcctServiceInstRef() throws Exception {
        return new Object[][]{
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of()},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8//ADDITIONAL INFO\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("ADDITIONAL INFO")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00ACHPGSGWGDNCTAHQM8//ADDITIONAL INFO1\n" +
                        ":61:1905201216CD0,20ACHPGSGWGDNCTAHQM8//ADDITIONAL INFO2\n" +
                        ":61:1709210826RDD28,00ACHPGSGWGDNCTAHQM8//ADDITIONAL INFO3\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("ADDITIONAL INFO1", "ADDITIONAL INFO2",
                        "ADDITIONAL INFO3")}
        };
    }

    @DataProvider(name = "parseBankTransactionCode")
    Object[][] parseBankTransactionCode() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("FCLR")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00NBNKGSGWGDNCTAHQM8\n" +
                        ":61:1905201216CD0,20FBRFGSGWGDNCTAHQM8\n" +
                        ":61:1709210826RDD28,FCLRGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("NBNK", "FBRF", "FCLR")},
        };
    }

    @DataProvider(name = "parseEntryDtlsEndToEndReferences")
    Object[][] parseEntryDtlsEndToEndReferences() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":86:EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("5798a701-effe-43e5-8d14-eec27ea3d8ec")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00NBNKGSGWGDNCTAHQM8\n" +
                        ":86:EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec\n" +
                        ":61:1905201216CD0,20FBRFGSGWGDNCTAHQM8\n" +
                        ":86:IREF/INSTUCTION ID/EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec\n" +
                        ":61:1709210826RDD28,FCLRGSGWGDNCTAHQM8\n" +
                        ":86:EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec/IREF/INSTUCTION ID\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("5798a701-effe-43e5-8d14-eec27ea3d8ec",
                        "5798a701-effe-43e5-8d14-eec27ea3d8ec", "5798a701-effe-43e5-8d14-eec27ea3d8ec")},
        };
    }

    @DataProvider(name = "parseEntryDtlsInstructionId")
    Object[][] parseEntryDtlsInstructionId() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":86:IREF/78900000782\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("78900000782")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00NBNKGSGWGDNCTAHQM8\n" +
                        ":86:IREF/78900000782\n" +
                        ":61:1905201216CD0,20FBRFGSGWGDNCTAHQM8\n" +
                        ":86:IREF/78900000782/EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec\n" +
                        ":61:1709210826RDD28,FCLRGSGWGDNCTAHQM8\n" +
                        ":86:EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec/IREF/78900000782\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("78900000782", "78900000782", "78900000782")},
        };
    }

    @DataProvider(name = "parseEntryDtlsPaymentInfoId")
    Object[][] parseEntryDtlsPaymentInfoId() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":86:PREF/78900000782\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("78900000782")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001DD10,00NBNKGSGWGDNCTAHQM8\n" +
                        ":86:PREF/78900000782\n" +
                        ":61:1905201216CD0,20FBRFGSGWGDNCTAHQM8\n" +
                        ":86:PREF/78900000782/EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec\n" +
                        ":61:1709210826RDD28,FCLRGSGWGDNCTAHQM8\n" +
                        ":86:EREF/5798a701-effe-43e5-8d14-eec27ea3d8ec/PREF/78900000782\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("78900000782", "78900000782", "78900000782")},
        };
    }

    @DataProvider(name = "parseEntryAdditionalInfo")
    Object[][] parseEntryAdditionalInfo() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":86:ADDITIONAL INFO\n" +
                        ":62F:D230930USD843686,20\n-}")), List.of("ADDITIONAL INFO")},
        };
    }

    @DataProvider(name = "parseStmtAdditionalInfo")
    Object[][] parseStmtAdditionalInfo() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00FCLRGSGWGDNCTAHQM8\n" +
                        ":86:ADDITIONAL INFO\n" +
                        ":62F:D230930USD843686,20\n" +
                        ":86:ADDITIONAL STMT INFO\n-}")), "ADDITIONAL STMT INFO"},
        };
    }

    @DataProvider(name = "parseTransactionSummary")
    Object[][] parseTransactionSummary() throws Exception {
        return new Object[][] {
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":62F:D230930USD843686,20\n-}")), Map.of("TotalNumEntries", "0", "TotalSum", "0.00",
                        "TotalNetSummary", "0.00", "TotalDCMark", "CRDT",
                        "TotalCreditEntries", "0", "TotalCreditSum", "0.00",
                        "TotalDebitEntries", "0", "TotalDebitSum", "0.00")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), Map.of("TotalNumEntries", "1", "TotalSum", "10.00",
                        "TotalNetSummary", "10.00", "TotalDCMark", "CRDT",
                        "TotalCreditEntries", "1", "TotalCreditSum", "10.00",
                        "TotalDebitEntries", "0", "TotalDebitSum", "0.00")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RDD50,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RCD25,00ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), Map.of("TotalNumEntries", "2", "TotalSum", "75.00",
                        "TotalNetSummary", "25.00", "TotalDCMark", "DBIT",
                        "TotalCreditEntries", "1", "TotalCreditSum", "25.00",
                        "TotalDebitEntries", "1", "TotalDebitSum", "50.00")},
                {getMT940JSON(Map.of(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RDD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RCD80,20ACHPGSGWGDNCTAHQM8\n" +
                        ":61:2310011001RDD28,90ACHPGSGWGDNCTAHQM8\n" +
                        ":62F:D230930USD843686,20\n-}")), Map.of("TotalNumEntries", "3", "TotalSum", "119.10",
                        "TotalNetSummary", "41.30", "TotalDCMark", "CRDT",
                        "TotalCreditEntries", "1", "TotalCreditSum", "80.20",
                        "TotalDebitEntries", "2", "TotalDebitSum", "38.90")},
        };
    }
}
