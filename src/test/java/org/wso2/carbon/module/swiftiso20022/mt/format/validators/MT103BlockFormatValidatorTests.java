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

package org.wso2.carbon.module.swiftiso20022.mt.format.validators;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Test class for MTBlockFormatFormatValidator.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
public class MT103BlockFormatValidatorTests {

    private static final String VALID_TEXT_BLOCK = "\n" +
            ":20:TXNREF1234567890\n" +
            ":13C:/CLSTIME/1000-0430\n" +
            ":13C:/SNDTIME/1000-0430\n" +
            ":23B:CRED\n" +
            ":32A:230523EUR100000,50\n" +
            ":50K:/12345678\n" +
            "JOHN DOE\n" +
            "123, FAKE STREET\n" +
            "FAKETOWN\n" +
            ":52A:DEUTDEFFXXX\n" +
            ":53B:/DE12345678901234567890\n" +
            ":54A:CHASUS33XXX\n" +
            ":56C:IRVTUS3NXXX\n" +
            ":57A:NORDDKKKXXX\n" +
            ":59:/DK5000400440116243\n" +
            "JANE SMITH\n" +
            "789, REAL ROAD\n" +
            "REALVILLE\n" +
            ":70:PAYMENT FOR INVOICE 998877\n" +
            ":71A:OUR\n" +
            ":72:/ACC/RENT/MAY\n" +
            "/INV/998877\n" +
            ":77T:/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ...\n";

    @Test
    public void testValidTextBlockFormat() {

        ValidationResult validationResult = MT103BlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TEXT_BLOCK_KEY, VALID_TEXT_BLOCK));

        Assert.assertTrue(validationResult.isValid());
    }

    @DataProvider(name = "invalidFormatTextBlockDataProvider")
    Object[][] getInvalidTextBlockFormatDataProvider() {
        return new Object[][] {
                {" "},
                {"\n   "},
                {":20:Some Reference"},
                {"{119:STP}"},
                {"{CHK:1234567890ABC}"},
                {"\n:20:TXNREF1234567890"},
                {"\n:20:TXNREF1234567890:"},
                {"\n:20:TXNREF1234567890\n::23B:CRED\n"},
                {"\n:20:TXNREF1234567890:\n:23B:CRED\n:"},
        };
    }

    @Test(dataProvider = "invalidFormatTextBlockDataProvider")
    public void testInvalidTextBlockFormat(String textBlock) {

        ValidationResult validationResult = MT103BlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TEXT_BLOCK_KEY, textBlock));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("Text Block format is invalid", validationResult.getErrorMessage());
    }

    @DataProvider(name = "invalidFieldRepetitionsDataProvider")
    Object[][] getInvalidFieldRepetitionsDataProvider() {
        return new Object[][] {
                {"\n:20:TXNREF1234567890\n:20:TXNREF1234567890\n:23B:CRED\n"},
                {"\n:20:TXNREF1234567890\n:50A:CHQB\n:50F:TELI/3226553478\n"},
        };
    }

    @Test(dataProvider = "invalidFieldRepetitionsDataProvider")
    public void testInvalidFieldRepetitions(String textBlock) {

        ValidationResult validationResult = MT103BlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TEXT_BLOCK_KEY, textBlock));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertTrue(Pattern.matches(
                "Field .* in Text Block cannot repeat",
                validationResult.getErrorMessage()));
    }

    @DataProvider(name = "invalidFieldOrderDataProvider")
    Object[][] getInvalidFieldOrderDataProvider() {
        return new Object[][] {
                {"\n:23B:CRED\n:20:TXNREF1234567890\n"},
                {"\n:20:TXNREF1234567890\n:23E:CHQB\n:26T:K90\n:23E:TELI/3226553478\n"},
        };
    }

    @Test(dataProvider = "invalidFieldOrderDataProvider")
    public void testInvalidFieldOrder(String textBlock) {

        ValidationResult validationResult = MT103BlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TEXT_BLOCK_KEY, textBlock));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertTrue(Pattern.matches(
                "Order of field .* in Text Block is invalid",
                validationResult.getErrorMessage()));
    }
}
