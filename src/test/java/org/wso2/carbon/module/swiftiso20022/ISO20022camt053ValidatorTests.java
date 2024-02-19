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
import org.apache.synapse.SynapseException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.ISOMessageParser;
import org.wso2.carbon.module.swiftiso20022.utils.ISOToMT940TestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.XSDValidator;

/**
 * Test class for ISO20022camtValidator.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class, ISOMessageParser.class})
public class ISO20022camt053ValidatorTests extends PowerMockTestCase {
    private MessageContext messageContext;
    ISO20022camt053Validator isoValidator = new ISO20022camt053Validator();

    @BeforeClass
    public void init() {
        messageContext = Mockito.spy(MessageContext.class);
    }

    @Test
    public void testISO20022camt053ValidatorWithoutBusinessHdr() throws Exception {
        XSDValidator validator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(validator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_DOCUMENT_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITHOUT_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test
    public void testISO20022camt053ValidatorWithBusinessHdr() throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class)
    public void testInvalidRootElementWithoutAppHdrScenario() throws Exception {
        XSDValidator validator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(validator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITHOUT_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testInvalidRootElementWithAppHdrScenario() throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_APPHDR_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testEmptyPayloadWithoutAppHdrScenario() throws Exception {
        XSDValidator validator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(validator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(null);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITHOUT_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_EMPTY_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testEmptyPayloadWithAppHdrScenario() throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(null);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_EMPTY_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_EMPTY_CAMT);

        isoValidator.connect(messageContext);
    }


    @Test(expectedExceptions = ConnectException.class)
    public void testEmptyDocumentPayloadWithAppHdrScenario() throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ConnectorConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_EMPTY_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_CAMT);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidStmtId",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidStmtIdScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidElectSeqNumber",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidElectSeqNumScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidLogSeqNumber",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidLogicSeqNumScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidCreatedDateTime",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidCreatedTimeScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidOpeningBal",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidOpeningBalanceScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidColsingBal",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidClosingBalanceScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidClosingAvailBal",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidClosingAvailableBalanceScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidForwardAvailBal",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidForwardAvailableBalanceScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }

    @Test(expectedExceptions = ConnectException.class, dataProvider = "invalidTransEntry",
            dataProviderClass = ISOToMT940TestConstants.class)
    public void testInvalidTransactionEntryScenario(String payload) throws Exception {
        XSDValidator appHdrValidator = Mockito.mock(XSDValidator.class);
        XSDValidator documentValidator = Mockito.mock(XSDValidator.class);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_HEAD_001_001).thenReturn(appHdrValidator);
        PowerMockito.whenNew(XSDValidator.class).
                withArguments(ConnectorConstants.XSD_SCHEMA_CAMT_053_001).thenReturn(documentValidator);

        PowerMockito.mockStatic(ISOMessageParser.class);
        PowerMockito.when(ISOMessageParser.getRootXMLElement(messageContext))
                .thenReturn(ISOToMT940TestConstants.XML_INPUT_BUSINESS_ENV_TAG);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext, ConnectorConstants.XPATH_CAMT_053_APPHDR))
                .thenReturn(ISOToMT940TestConstants.PAYLOAD_APPHDR);
        PowerMockito.when(ISOMessageParser.extractISOMessage(messageContext,
                        ConnectorConstants.XPATH_DOCUMENT_WITH_BUSINESS_HDR))
                .thenReturn(payload);

        isoValidator.connect(messageContext);
    }
}
