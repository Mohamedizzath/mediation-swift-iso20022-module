/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022;

import org.apache.synapse.SynapseException;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.util.PayloadHelper;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT103ToJsonTransformerTestConstants;

/**
 * Test class for MT103ToJsonTransformer.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class, PayloadHelper.class})
public class MT103ToJsonTransformerTests extends PowerMockTestCase {

    private Axis2MessageContext messageContext;

    MT103ToJsonTransformer mt103ToJsonTransformer = new MT103ToJsonTransformer();

    @BeforeClass
    public void init() throws Exception {
        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void tesMT103ToJsonTransformer() throws Exception {
        messageContext = Mockito.mock(Axis2MessageContext.class);


        PowerMockito.mockStatic(PayloadHelper.class);
        PowerMockito.doReturn(MT103ToJsonTransformerTestConstants.VALID_PAYLOAD)
                .when(PayloadHelper.class, "getTextPayload", messageContext);

        mt103ToJsonTransformer.connect(messageContext);
    }

    @Test(expectedExceptions = SynapseException.class, expectedExceptionsMessageRegExp = "Missing Request Payload")
    public void testEmptyPayload() throws Exception {
        messageContext = Mockito.mock(Axis2MessageContext.class);

        PowerMockito.mockStatic(PayloadHelper.class);
        PowerMockito.doReturn("").when(PayloadHelper.class, "getTextPayload", messageContext);

        mt103ToJsonTransformer.connect(messageContext);
    }

    @Test(dataProvider = "invalidMT103MessageDataProvider",
            dataProviderClass = MT103ToJsonTransformerTestConstants.class, expectedExceptions = SynapseException.class)
    public void testInvalidMT103Message(String mt103Message) throws Exception {
        messageContext = Mockito.mock(Axis2MessageContext.class);

        PowerMockito.mockStatic(PayloadHelper.class);
        PowerMockito.doReturn(mt103Message).when(PayloadHelper.class, "getTextPayload", messageContext);

        mt103ToJsonTransformer.connect(messageContext);
    }
}
