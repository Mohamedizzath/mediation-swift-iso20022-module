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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserTestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserTestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.List;
import java.util.Map;

/**
 * Test class for MTParser.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
public class MTParserTests {
    @Test(dataProvider = "parseMTMessage", dataProviderClass = MTParserTestConstants.class)
    public void testParseMTMessageScenario(String mtMessageText, MT940Message mt940Message) throws Exception {
        MT940Message mtMessage = MT940Parser.parse(mtMessageText);

        Assert.assertTrue(new ReflectionEquals(mt940Message.getBasicHeaderBlock())
                .matches(mtMessage.getBasicHeaderBlock()));
        Assert.assertTrue(new ReflectionEquals(mt940Message.getApplicationHeaderBlock())
                .matches(mtMessage.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "MT message blocks are not in the correct format",
            dataProvider = "parseInvalidMTMessage", dataProviderClass = MTParserTestConstants.class)
    public void testParseInvalidMTMessageScenario(String mtMessageText, MT940Message mt940Message) throws Exception {
        MT940Message mtMessage = MT940Parser.parse(mtMessageText);
    }

    @Test(dataProvider = "parseMTMessageBlocks", dataProviderClass = MTParserTestConstants.class)
    public void testParseMTMessageBlocksScenario(String mtMessage, Map<String, String> block) throws Exception {
        Map<String, String> parseBlocks = MTParserUtils.getMessageBlocks(mtMessage);

        Assert.assertTrue(new ReflectionEquals(block).matches(parseBlocks));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "MT message blocks are not in the correct format", dataProvider = "parseInvalidMTMessageBlocks",
            dataProviderClass = MTParserTestConstants.class)
    public void testParseInvalidMTMessageBlocksScenario(String mtMessage) throws Exception {
        Map<String, String> parseBlocks = MTParserUtils.getMessageBlocks(mtMessage);
    }


    @Test(dataProvider = "parseTextBlockFields", dataProviderClass = MTParserTestConstants.class)
    public void testParseTextBlockFields(String textBlock, List<String> fields) throws Exception {
        List<String> parsedFields = MTParserUtils.getTextBlockFields(textBlock);

        Assert.assertEquals(parsedFields, fields);
    }
    @Test(dataProvider = "parserBasicHeaderApplicationID", dataProviderClass = MTParserTestConstants.class)
    public void testParserBasicHeaderApplicationIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Basic header block is not in the correct format", dataProvider = "parserInvalidBasicHeaderApplicationID",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidBasicHeaderApplicationIDScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderServiceID", dataProviderClass = MTParserTestConstants.class)
    public void testParserBasicHeaderServiceIDScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Basic header block is not in the correct format", dataProvider = "parserInvalidBasicHeaderServiceID",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidBasicHeaderServiceIDScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderLTAddress", dataProviderClass = MTParserTestConstants.class)
    public void testParserBasicHeaderLTAddressScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderLTAddress", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidBasicHeaderLTAddressScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderSessionNumber", dataProviderClass = MTParserTestConstants.class)
    public void testParserBasicHeaderSessionNumberScenario(Map<String, String> block, BasicHeaderBlock basicHeaderBlock)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderSessionNumber", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidBasicHeaderSessionNumberScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserBasicHeaderSequenceNumber", dataProviderClass = MTParserTestConstants.class)
    public void testParserBasicHeaderSequenceNumberScenario(Map<String, String> block,
                                                            BasicHeaderBlock basicHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(basicHeaderBlock).matches(mt940Message.getBasicHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Basic header block is not in the correct format",
            dataProvider = "parserInvalidBasicHeaderSequenceNumber", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidBasicHeaderSequenceNumberScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderInputOutputID", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderInputOutputIDScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
    dataProvider = "parserInvalidApplicationHeaderInputOutputID", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderInputOutputIDScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderMessageType", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderMessageTypeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
    dataProvider = "parserInvalidApplicationHeaderMessageType", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderMessageTypeScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderPriority", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderPriorityScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderPriority", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderPriorityScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderDestinationAddress", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderDestinationAddressScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderDestinationAddress",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderDestinationAddressScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderDeliveryMonitor", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderDeliveryMonitorScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderDeliveryMonitor",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderDeliveryMonitorScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderObsolescencePeriod", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderObsolescencePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderObsolescencePeriod",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderObsolescencePeriodScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderInputTime", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderInputTimePeriodScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderInputTime", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderInputTimePeriodScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderMessageInputReference",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderMessageInputReferencePeriodScenario(Map<String, String> block,
                                           ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class, expectedExceptionsMessageRegExp =
            "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderMessageInputReference",
            dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderMessageInputReferencePeriodScenario(Map<String, String> block)
            throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderOutputDate", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderOutputDateScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderOutputDate", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderOutputDateScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }

    @Test(dataProvider = "parserApplicationHeaderOutputTime", dataProviderClass = MTParserTestConstants.class)
    public void testParserApplicationHeaderOutputTimeScenario(Map<String, String> block,
                                     ApplicationHeaderBlock applicationHeaderBlock) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);

        Assert.assertTrue(new ReflectionEquals(applicationHeaderBlock)
                .matches(mt940Message.getApplicationHeaderBlock()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Application header block is not in the correct format",
            dataProvider = "parserInvalidApplicationHeaderOutputTime", dataProviderClass = MTParserTestConstants.class)
    public void testParserInvalidApplicationHeaderOutputTimeScenario(Map<String, String> block) throws Exception {
        MT940Message mt940Message = new MT940Message();
        MTParser.parse(block, mt940Message);
    }
    public static final String VALID_BASIC_HEADER_BLOCK = "F01BANKFRPPAXXX2222123456";

    @Test(dataProvider = "validMTMessageMapDataProvider", dataProviderClass = MTParserTestConstants.class)
    public void testMTParserParseMethod(Map<String, String> blocks) throws MTMessageParsingException {

        MTParser.parse(blocks, new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class, dataProvider = "invalidUserHeaderBlockDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidUserHeaderBlockFormat(String userHeaderBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, userHeaderBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Service Identifier in User Header Block is in invalid format",
            dataProvider = "invalidServiceIdentifierDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidServiceIdentifier(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Banking Priority in User Header Block is in invalid format",
            dataProvider = "invalidBankingPriorityDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidBankingPriority(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Message User Reference in User Header Block is in invalid format",
            dataProvider = "invalidMessageUserReferenceDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidMessageUserReference(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Validation Flag in User Header Block is in invalid format",
            dataProvider = "invalidValidationFlagDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidValidationFlag(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Balance Checkpoint in User Header Block is in invalid format",
            dataProvider = "invalidBalanceCheckpointDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidBalanceCheckpoint(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Message Input Reference in User Header Block is in invalid format",
            dataProvider = "invalidMessageInputReferenceDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidMessageInputReference(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Related Reference in User Header Block is in invalid format",
            dataProvider = "invalidRelatedReferenceDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidRelatedReference(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Service Type Identifier in User Header Block is in invalid format",
            dataProvider = "invalidServiceTypeIdentifierDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidServiceTypeIdentifier(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "End to End Reference in User Header Block is in invalid format",
            dataProvider = "invalidEndToEndReferenceDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidEndToEndReference(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Addressee Information in User Header Block is in invalid format",
            dataProvider = "invalidAddresseeInformationDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidAddresseeInformation(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Payment Release Information in User Header Block is in invalid format",
            dataProvider = "invalidPaymentReleaseInformationDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidPaymentReleaseInformation(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sanctions Screening Information in User Header Block is " +
                    "in invalid format", dataProvider = "invalidSanctionsScreeningInformationDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidSanctionsScreeningInformation(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Payment Controls Information in User Header Block is in invalid format",
            dataProvider = "invalidPaymentControlsInformationDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidPaymentControlsInformation(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.USER_HEADER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class, dataProvider = "invalidTrailerBlockDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidTrailerBlockFormat(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Checksum in Trailer Block is in invalid format",
            dataProvider = "invalidChecksumDataProvider", dataProviderClass = MTParserTestConstants.class)
    public void testInvalidChecksum(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Possible Duplicate Emission in Trailer Block is in invalid format",
            dataProvider = "invalidPossibleDuplicateEmissionDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidPossibleDuplicateEmission(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Message Reference in Trailer Block is in invalid format",
            dataProvider = "invalidMessageReferenceDataProvider", dataProviderClass = MTParserTestConstants.class)
    public void testInvalidMessageReference(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Possible Duplicate Message in Trailer Block is in invalid format",
            dataProvider = "invalidPossibleDuplicateMessageDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidPossibleDuplicateMessage(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "System Originated Message in Trailer Block is in invalid format",
            dataProvider = "invalidSystemOriginatedMessageDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testInvalidSystemOriginatedMessage(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, VALID_BASIC_HEADER_BLOCK,
                ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }
}
