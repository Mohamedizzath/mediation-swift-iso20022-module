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

package org.wso2.carbon.module.swiftiso20022.mtmessageparser;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.mtmessageparsers.MT940Parser;
import org.wso2.carbon.module.swiftiso20022.mtmessageparsers.MTParser;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;

/**
 * Test class for MTParser.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
public class MTParserTests {
    @Test(dataProvider = "parseMTMessage", dataProviderClass = MTParserConstants.class)
    public void testParseMTMessageScenario(String mtMessageText, MT940Message mt940Message) throws Exception {
        MT940Message mtMessage = MT940Parser.parse(mtMessageText);

        Assert.assertTrue(new ReflectionEquals(mt940Message.getBasicHeaderBlock())
                .matches(mtMessage.getBasicHeaderBlock()));
        Assert.assertTrue(new ReflectionEquals(mt940Message.getApplicationHeaderBlock())
                .matches(mtMessage.getApplicationHeaderBlock()));
    }
    @Test(dataProvider = "parseMTMessageBlocks", dataProviderClass = MTParserConstants.class)
    public void testParseMTMessageBlocksScenario(String mtMessage, Map<String, String> block) {
        Map<String, String> parseBlocks = MTParserUtils.getMessageBlocks(mtMessage);

        Assert.assertTrue(new ReflectionEquals(block).matches(parseBlocks));
    }
    @Test(dataProvider = "parserBasicHeaderApplicationID", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderApplicationIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(dataProvider = "parserBasicHeaderServiceID", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderServiceIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(dataProvider = "parserBasicHeaderLTAddress", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderLTAddressScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(dataProvider = "parserBasicHeaderSessionNumber", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderSessionNumberScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(dataProvider = "parserBasicHeaderSequenceNumber", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderSequenceNumberScenario(Map<String, String> block,
                                                            BasicHeaderBlock basicHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderInputOutputID", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderInputOutputIDScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderMessageType", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderMessageTypeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderPriority", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderPriorityScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderDestinationAddress", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderDestinationAddressScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderDeliveryMonitor", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderDeliveryMonitorScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderObsolescencePeriod", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderObsolescencePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderInputTime", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderInputTimePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderMessageInputReference", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderMessageInputReferencePeriodScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderOutputDate", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderOutputDateScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(dataProvider = "parserApplicationHeaderOutputTime", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderOutputTimeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = MTParser.parse(block, MT940Message.class);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }
}
