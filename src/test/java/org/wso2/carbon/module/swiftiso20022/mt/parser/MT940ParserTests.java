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

package org.wso2.carbon.module.swiftiso20022.mt.parser;

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
        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentificationOptP())
                .matches(parsedTextBlock.getAccountIdentificationOptP()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getAccountIdentification())
                .matches(parsedTextBlock.getAccountIdentification()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getRelatedReference())
                .matches(parsedTextBlock.getRelatedReference()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getStatementLines())
                .matches(parsedTextBlock.getStatementLines()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getInfoToAccountOwner())
                .matches(parsedTextBlock.getInfoToAccountOwner()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingBalOptF())
                .matches(parsedTextBlock.getClosingBalOptF()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingBalOptM())
                .matches(parsedTextBlock.getClosingBalOptM()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getClosingAvlBalance())
                .matches(parsedTextBlock.getClosingAvlBalance()));
        Assert.assertTrue(new ReflectionEquals(textBlock.getForwardAvlBalance())
                .matches(parsedTextBlock.getForwardAvlBalance()));
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "20 field not in the correct format",
            dataProvider = "invalidTransactionReferenceNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidTransRefNumberScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "21 field not in the correct format",
            dataProvider = "invalidRelatedReference", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidRelatedRefScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "25 field not in the correct format",
            dataProvider = "invalidAccountIdentification", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidAccIdentificationScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "25P field not in the correct format",
            dataProvider = "invalidAccountIdentificationOptP", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidAccIdentificationOptPScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "28C field not in the correct format",
            dataProvider = "invalidStmtSeqNumber", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStmtSeqNumberScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "60F field not in the correct format",
            dataProvider = "invalidOpeningBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidOpeningBalanceOptFScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "60M field not in the correct format",
            dataProvider = "invalidOpeningBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidOpeningBalanceOptMScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "61 field not in the correct format",
            dataProvider = "invalidStmtLine", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStatementLineScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "86 field not in the correct format",
            dataProvider = "invalidStmtLineInfoToAccOwner", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidStmtLineInfoToAccOwnerScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "62F field not in the correct format",
            dataProvider = "invalidClosingBalanceOptF", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingBalanceOptFScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "62M field not in the correct format",
            dataProvider = "invalidClosingBalanceOptM", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingBalanceOptMScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
    }

    @Test(expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "64 field not in the correct format",
            dataProvider = "invalidClosingAvlBalance", dataProviderClass = MT940ParserTestConstants.class)
    public void testInvalidClosingAvlBalanceScenario(String textBlockStr) throws Exception {
        MT940TextBlock parsedTextBlock = MT940TextBlock.parse(textBlockStr);
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
