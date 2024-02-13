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

import com.google.gson.Gson;
import org.apache.axis2.context.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.Entity;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block01;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block02;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block03;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block04;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block05;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMT103TransformerTestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt103Utils;

import java.util.List;
import java.util.Optional;

/**
 * Test class for JsonToMt103Transformer.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class})
public class JsonToMT103TransformerTests extends PowerMockTestCase {

    private MessageContext messageContext;

    JsonToMT103Transformer jsonToMT103Transformer = new JsonToMT103Transformer();

    @BeforeClass
    public void init() {
        messageContext = Mockito.spy(MessageContext.class);
    }

    @Test
    public void testJsonToMT103Transformer() throws Exception {
        Axis2MessageContext msgCxt = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCxt).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(JsonToMT103TransformerTestConstants.VALID_PAYLOAD));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT103Transformer.connect(msgCxt);
    }

    @Test(expectedExceptions = SynapseException.class, dataProvider = "emptyMandatoryBlockDataProvider",
            dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testEmptyMandatoryBlock(String payload) throws Exception {
        Axis2MessageContext msgCxt = Mockito.mock(Axis2MessageContext.class);
        Mockito.doReturn(messageContext).when(msgCxt).getAxis2MessageContext();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.when(ConnectorUtils.buildMessagePayloadFromMessageContext(messageContext))
                .thenReturn(Optional.of(payload));
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        jsonToMT103Transformer.connect(msgCxt);
    }

    @Test(dataProvider = "invalidRepetitiveFieldDataProvider",
            dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateRepetitiveField(List<String> repetitiveFieldValues) {
        ErrorModel errorResponse =
                JsonToMt103Utils.validateRepetitiveField(repetitiveFieldValues,
                        JsonToMT103TransformerTestConstants.DEFAULT_FIELD_NAME, 15);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidMultipleLineFieldDataProvider",
            dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateFieldLines(List<String> fieldLines) {
        ErrorModel errorResponse =
                JsonToMt103Utils.validateFieldLines(fieldLines,
                        JsonToMT103TransformerTestConstants.DEFAULT_FIELD_NAME, 35, 3);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidEntityDataProvider",
            dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateEntityField(String option, List<String> details) {
        Entity entity = new Entity();
        entity.setDetails(details);
        entity.setOption(option);
        ErrorModel errorResponse =
                JsonToMt103Utils.validateEntityField(entity, JsonToMT103TransformerTestConstants.DEFAULT_FIELD_NAME);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidBlock01Payload", dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateBlock01(String block01PayloadString) {
        Block01 block01 = (new Gson()).fromJson(block01PayloadString, Block01.class);
        ErrorModel errorResponse = JsonToMt103Utils.validateBlock01(block01);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidBlock02Payload", dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateBlock02(String block02PayloadString) {
        Block02 block02 = (new Gson()).fromJson(block02PayloadString, Block02.class);
        ErrorModel errorResponse = JsonToMt103Utils.validateBlock02(block02);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidBlock03Payload", dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateBlock03(String block03PayloadString) {
        Block03 block03 = (new Gson()).fromJson(block03PayloadString, Block03.class);
        ErrorModel errorResponse = JsonToMt103Utils.validateBlock03(block03);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidBlock04Payload", dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateBlock04(String block04PayloadString) {
        Block04 block04 = (new Gson()).fromJson(block04PayloadString, Block04.class);
        ErrorModel errorResponse = JsonToMt103Utils.validateBlock04(block04);

        Assert.assertTrue(errorResponse.isError());
    }

    @Test(dataProvider = "invalidBlock05Payload", dataProviderClass = JsonToMT103TransformerTestConstants.class)
    public void testValidateBlock05(String block05PayloadString) {
        Block05 block05 = (new Gson()).fromJson(block05PayloadString, Block05.class);
        ErrorModel errorResponse = JsonToMt103Utils.validateBlock05(block05);

        Assert.assertTrue(errorResponse.isError());
    }
}
