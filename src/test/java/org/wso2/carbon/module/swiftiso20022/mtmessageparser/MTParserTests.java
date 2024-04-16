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
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MTParser;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.List;
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

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "MT message blocks are not in the correct format",
            dataProvider = "parseInvalidMTMessage", dataProviderClass = MTParserConstants.class)
    public void testParseInvalidMTMessageScenario(String mtMessageText, MT940Message mt940Message) throws Exception {
        MT940Message mtMessage = MT940Parser.parse(mtMessageText);
    }

    @Test(dataProvider = "parseMTMessageBlocks", dataProviderClass = MTParserConstants.class)
    public void testParseMTMessageBlocksScenario(String mtMessage, Map<String, String> block) throws Exception {
        Map<String, String> parseBlocks = MTParserUtils.getMessageBlocks(mtMessage);

        Assert.assertTrue(new ReflectionEquals(block).matches(parseBlocks));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "MT message blocks are not in the correct format", dataProvider = "parseInvalidMTMessageBlocks",
            dataProviderClass = MTParserConstants.class)
    public void testParseInvalidMTMessageBlocksScenario(String mtMessage) throws Exception {
        Map<String, String> parseBlocks = MTParserUtils.getMessageBlocks(mtMessage);
    }

    @Test(dataProvider = "parseTextBlockFields", dataProviderClass = MTParserConstants.class)
    public void testParseTextBlockFields(String textBlock, List<String> fields) throws Exception {
        List<String> parsedFields = MTParserUtils.getTextBlockFields(textBlock);

        Assert.assertEquals(parsedFields, fields);
    }

    @Test(dataProvider = "parserBasicHeaderApplicationID", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderApplicationIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Basic header block is not in the correct format", dataProvider = "parserInvalidBasicHeaderApplicationID",
            dataProviderClass = MTParserConstants.class)
    public void testParserInvalidBasicHeaderApplicationIDScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderServiceID", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderServiceIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Basic header block is not in the correct format", dataProvider = "parserInvalidBasicHeaderServiceID",
            dataProviderClass = MTParserConstants.class)
    public void testParserInvalidBasicHeaderServiceIDScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderLTAddress", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderLTAddressScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderLTAddress", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidBasicHeaderLTAddressScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderSessionNumber", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderSessionNumberScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderSessionNumber", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidBasicHeaderSessionNumberScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderSequenceNumber", dataProviderClass = MTParserConstants.class)
    public void testParserBasicHeaderSequenceNumberScenario(Map<String, String> block,
                                                            BasicHeaderBlock basicHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderSequenceNumber", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidBasicHeaderSequenceNumberScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderInputOutputID", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderInputOutputIDScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderInputOutputID", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderInputOutputIDScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderMessageType", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderMessageTypeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderMessageType", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderMessageTypeScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderPriority", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderPriorityScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderPriority", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderPriorityScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderDestinationAddress", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderDestinationAddressScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
       dataProvider = "parserInvalidApplicationHeaderDestinationAddress", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderDestinationAddressScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderDeliveryMonitor", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderDeliveryMonitorScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderDeliveryMonitor", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderDeliveryMonitorScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderObsolescencePeriod", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderObsolescencePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
        dataProvider = "parserInvalidApplicationHeaderObsolescencePeriod", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderObsolescencePeriodScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderInputTime", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderInputTimePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderInputTime", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderInputTimePeriodScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderMessageInputReference", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderMessageInputReferencePeriodScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
    dataProvider = "parserInvalidApplicationHeaderMessageInputReference", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderMessageInputReferencePeriodScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderOutputDate", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderOutputDateScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderOutputDate", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderOutputDateScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderOutputTime", dataProviderClass = MTParserConstants.class)
    public void testParserApplicationHeaderOutputTimeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderOutputTime", dataProviderClass = MTParserConstants.class)
    public void testParserInvalidApplicationHeaderOutputTimeScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }
}
