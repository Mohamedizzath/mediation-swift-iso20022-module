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

import com.google.gson.JsonSyntaxException;
import org.apache.axis2.context.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMT940TestConstants;

import java.util.Optional;

/**
 * Test class for JsonToMT940Transformer.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class})
public class JsonToMT940TransformerTests extends PowerMockTestCase {

    private MessageContext messageContext;
    JsonToMT940Transformer jsonToMT940Transformer = new JsonToMT940Transformer();

    @BeforeClass
    public void init() {
        messageContext = Mockito.spy(MessageContext.class);
    }

    @Test
    public void testJsonToMT940Transformer() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(JsonToMT940TestConstants.PAYLOAD));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);

    }

    @Test(expectedExceptions = SynapseException.class)
    public void testEmptyPayloadScenario() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.empty());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = JsonSyntaxException.class)
    public void testInvalidJsonPayloadScenario() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of("Invalid JSON Payload"));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidHeaderBlockDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidHeaderBlocks(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testNullOpeningBalanceScenario() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(JsonToMT940TestConstants.NULL_OPENING_BAL_PAYLOAD));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testNullClosingBalanceScenario() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(JsonToMT940TestConstants.NULL_CLOSING_BAL_PAYLOAD));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidOpeningBalDetailsDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidOpeningBalDetails(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidClosingBalDetailsDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidClosingBalDetails(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidClosingAvailableBalDetailsDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidClosingAvailableBalDetails(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidForwardAvailBalDetailsDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidForwardAvailBalDetails(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidTransactionDetailsDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidTransactionDetails(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "invalidPayloadValuesDataProvider",
            dataProviderClass = JsonToMT940TestConstants.class)
    public void testInvalidPayloadValues(String payload) throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCtx).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT940Transformer.connect(msgCtx);
    }

}
