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

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MTParser;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserTestConstants;

import java.util.Map;

/**
 * Test class for MTParser.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class})
public class MTParserTests {

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
