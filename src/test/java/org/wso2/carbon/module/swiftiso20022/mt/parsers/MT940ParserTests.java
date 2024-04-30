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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT940TextBlock;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ParserTestConstants;

/**
 * Test class for MT940Parser.
 */
public class MT940ParserTests {
    @Test(dataProvider = "parseMT940TextBlock", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidMT940TextBlockScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        //  Matching each of the properties of MT940
        Assert.assertTrue(new ReflectionEquals(textBlock.getTransactionReferenceNumber())
                .matches(parsedTextBlock.getTransactionReferenceNumber()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getRelatedReference())
                .matches(parsedTextBlock.getRelatedReference()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentification())
                .matches(parsedTextBlock.getAccountIdentification()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentification())
                .matches(parsedTextBlock.getAccountIdentification()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getRelatedReference())
                .matches(parsedTextBlock.getRelatedReference()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getStatementLines())
                .matches(parsedTextBlock.getStatementLines()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getInfoToAccountOwner())
                .matches(parsedTextBlock.getInfoToAccountOwner()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingBal())
                .matches(parsedTextBlock.getClosingBal()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingAvlBalance())
                .matches(parsedTextBlock.getClosingAvlBalance()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getForwardAvlBalance())
                .matches(parsedTextBlock.getForwardAvlBalance()));
    }

    @Test(dataProvider = "validTransactionReferenceNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidTransRefNumberScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getTransactionReferenceNumber())
                .matches(parsedTextBlock.getTransactionReferenceNumber()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "20 field not in the correct format",
            dataProvider = "invalidTransactionReferenceNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidTransRefNumberScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validRelatedReference", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidRelatedReference(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getRelatedReference())
                .matches(parsedTextBlock.getRelatedReference()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "21 field not in the correct format",
            dataProvider = "invalidRelatedReference", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidRelatedRefScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validAccountIdentification", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidAccIdentificationScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentification())
                .matches(parsedTextBlock.getAccountIdentification()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "25 field not in the correct format",
            dataProvider = "invalidAccountIdentification", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidAccIdentificationScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validAccountIdentificationOptP", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidAccIdentificationOptPScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentification())
                .matches(parsedTextBlock.getAccountIdentification()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "25P field not in the correct format",
            dataProvider = "invalidAccountIdentificationOptP", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidAccIdentificationOptPScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validStmtSeqNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidStmtSeqNumberScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getStatementSequenceNumber())
                .matches(parsedTextBlock.getStatementSequenceNumber()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "28C field not in the correct format",
            dataProvider = "invalidStmtSeqNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStmtSeqNumberScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validOpeningBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidOpeningBalanceOptFScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getOpeningBal())
                .matches(parsedTextBlock.getOpeningBal()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "60F field not in the correct format",
            dataProvider = "invalidOpeningBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidOpeningBalanceOptFScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validOpeningBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidOpeningBalanceOptMScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getOpeningBal())
                .matches(parsedTextBlock.getOpeningBal()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "60M field not in the correct format",
            dataProvider = "invalidOpeningBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidOpeningBalanceOptMScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validStmtLine", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidStmtLineScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertEquals(parsedTextBlock.getStatementLines().size(), textBlock.getStatementLines().size());

        int statementCount = parsedTextBlock.getStatementLines().size();

        for (int i = 0; i < statementCount; i++) {
            Assert.assertTrue(new ReflectionEquals(textBlock.getStatementLines().get(i).get("Field61"))
                    .matches(parsedTextBlock.getStatementLines().get(i).get("Field61")));
        }
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "61 field not in the correct format",
            dataProvider = "invalidStmtLine", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStatementLineScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validStmtLineInfoAccountOwner", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidStmtLineInfoToAccOwnerScenario(String textBlockStr, MT940TextBlock textBlock)
            throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertEquals(parsedTextBlock.getStatementLines().size(), textBlock.getStatementLines().size());

        int statementCount = parsedTextBlock.getStatementLines().size();

        for (int i = 0; i < statementCount; i++) {
            Assert.assertTrue(new ReflectionEquals(textBlock.getStatementLines().get(i).get("Field86"))
                    .matches(parsedTextBlock.getStatementLines().get(i).get("Field86")));
        }
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "86 field not in the correct format",
            dataProvider = "invalidStmtLineInfoToAccOwner", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStmtLineInfoToAccOwnerScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validClosingBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidClosingBalanceOptFScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingBal())
                .matches(parsedTextBlock.getClosingBal()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "62F field not in the correct format",
            dataProvider = "invalidClosingBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingBalanceOptFScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validClosingBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidClosingBalanceOptMScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingBal())
                .matches(parsedTextBlock.getClosingBal()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "62M field not in the correct format",
            dataProvider = "invalidClosingBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingBalanceOptMScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validClosingAvlBalance", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidClosingAvlBalanceScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingAvlBalance())
                .matches(parsedTextBlock.getClosingAvlBalance()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "64 field not in the correct format",
            dataProvider = "invalidClosingAvlBalance", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingAvlBalanceScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(dataProvider = "validForwardAvlBalance", dataProviderClass = MT940ParserTestConstants.class)
    public void testValidForwardAvlBalanceScenario(String textBlockStr, MT940TextBlock textBlock) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);

        Assert.assertTrue(new ReflectionEquals(textBlock.getForwardAvlBalance())
                .matches(parsedTextBlock.getForwardAvlBalance()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "65 field not in the correct format",
            dataProvider = "invalidForwardAvlBalance", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidForwardAvlBalanceScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "86 field not in the correct format",
            dataProvider = "invalidInfoToAccOwner", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidInfoToAccOwnerScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }
}
