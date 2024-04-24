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

/**
 * Test class for MTBlockFormatFormatValidator.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
public class MTBlockFormatValidatorTests {

    private static final String VALID_USER_HEADER_BLOCK =
            "{103:EBA}{108:REF0140862562/015}{121:180f1e65-90e0-44d5-a49a-92b55eb3025f}{119:STP}";

    private static final String VALID_TRAILER_BLOCK =
            "{CHK:123456789ABC}{MRF:1806271539180626BANKFRPPAXXX2222123456}{SYS:1454120811BANKFRPPAXXX2222123456}";

    @DataProvider(name = "validBlockFormatsDataProvider")
    Object[][] getValidBlockFormatsDataProvider() {
        return new Object[][] {
                {Map.of()},
                {Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, VALID_USER_HEADER_BLOCK)},
                {Map.of(ConnectorConstants.TRAILER_BLOCK_KEY, VALID_TRAILER_BLOCK)},
                {Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, VALID_USER_HEADER_BLOCK,
                        ConnectorConstants.TRAILER_BLOCK_KEY, VALID_TRAILER_BLOCK)},

        };
    }

    @Test(dataProvider = "validBlockFormatsDataProvider")
    public void testValidBlockFormats(Map<String, String> blocks) {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(blocks);

        Assert.assertTrue(validationResult.isValid());
    }

    @DataProvider(name = "invalidFormatUserHeaderBlockDataProvider")
    Object[][] getInvalidFormatUserHeaderBlockDataProvider() {
        return new Object[][] {
                {""},
                {"         "},
                {"{}"},
                {"{:}"},
                {"{123:}"},
                {"{ABC:some value}"},
                {"{12:some value}"},
                {"{122:some value:}"},
                {"{123:{some} value}"},
                {"{123:{123:some} value}"},
                {"{123:{some value}"},
                {"{123:{some value}}"},
                {"{123:some value}}"},
                {"{123:some value}{}"},
                {"{123:some value}{ABC:}"}
        };
    }

    @Test(dataProvider = "invalidFormatUserHeaderBlockDataProvider")
    public void testInvalidFormatUserHeaderBlock(String userHeaderBlock) {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, userHeaderBlock));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("User Header Block format is invalid", validationResult.getErrorMessage());
    }

    @Test
    public void testField111WrongOrderInUserHeaderBlock() {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, "{111:some value}{121:another value}"));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("Service Type Identifier cannot appear before field End To End Reference",
                validationResult.getErrorMessage());
    }

    @Test
    public void testFieldRepeatedInUserHeaderBlock() {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, "{121:some value}{121:another value}"));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("Field 121 in User Header Block cannot repeat",
                validationResult.getErrorMessage());
    }

    @DataProvider(name = "invalidFormatTrailerBlockDataProvider")
    Object[][] getInvalidFormatTrailerBlockDataProvider() {
        return new Object[][] {
                {""},
                {"         "},
                {"{}"},
                {"{:}"},
                {"{123:some value}"},
                {"{abc:some value}"},
                {"{AB:some value}"},
                {"{ABC:some value:}"},
                {"{ABC:{some} value}"},
                {"{ABC:{ABC:some} value}"},
                {"{ABC:{some value}"},
                {"{ABC:{some value}}"},
                {"{ABC:some value}}"},
                {"{ABC:some value}{}"},
                {"{ABC:some value}{123:}"},
        };
    }

    @Test(dataProvider = "invalidFormatTrailerBlockDataProvider")
    public void testValidateTrailerBlock(String trailerBlock) {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlock));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("Trailer Block format is invalid", validationResult.getErrorMessage());
    }

    @Test
    public void testFieldRepeatedInTrailerBlock() {

        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(
                Map.of(ConnectorConstants.TRAILER_BLOCK_KEY, "{ABC:some value}{ABC:another value}"));

        Assert.assertTrue(validationResult.isNotValid());
        Assert.assertEquals("Field ABC in Trailer Block cannot repeat",
                validationResult.getErrorMessage());
    }
}
