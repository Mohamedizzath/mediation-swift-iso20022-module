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

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT103Message;
import org.wso2.carbon.module.swiftiso20022.utils.MT103ParserTestConstants;

import java.util.List;

/**
 * Test class for MT103Parser.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
public class MT103ParserTests {

    @Test(dataProvider = "validTextBlockDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testValidMT103MessageParsing(String mt103Message) throws MTMessageParsingException {

        MT103Message mt103MessageModel = MT103Parser.parse(mt103Message);
    }

    @Test
    public void testValidField20Value() throws MTMessageParsingException {

        Field20 field20 =  Field20.parse("Ref254", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals("Ref254", field20.getValue());
    }

    @Test(dataProvider = "invalidField20DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Reference in Text Block is in invalid format")
    public void testInvalidField20Value(String field20String) throws Exception {

        Field20.parse(field20String, ConnectorConstants.NO_LETTER_OPTION);
    }

    @Test
    public void testValidField13CValue() throws MTMessageParsingException {

        Field13 field13C =  Field13.parse("/CLSTIME/1300-0430", ConnectorConstants.OPTION_C);

        Assert.assertEquals("CLSTIME", field13C.getCode());
        Assert.assertEquals("1300", field13C.getTime());
        Assert.assertEquals("-", field13C.getSign());
        Assert.assertEquals("0430", field13C.getOffset());
    }

    @Test(dataProvider = "invalidField13CDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Time Indication in Text Block is in invalid format")
    public void testInvalidField13CValue(String field13CString) throws Exception {

        Field13.parse(field13CString, ConnectorConstants.OPTION_C);
    }

    @Test
    public void testValidField23BValue() throws MTMessageParsingException {

        Field23 field23B =  Field23.parse("SPAY", ConnectorConstants.OPTION_B);

        Assert.assertEquals("SPAY", field23B.getCode());
    }

    @Test(dataProvider = "invalidField23BDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Bank Operation Code in Text Block is in invalid format")
    public void testInvalidField23BValue(String field23BString) throws Exception {

        Field23.parse(field23BString, ConnectorConstants.OPTION_B);
    }

    @Test
    public void testValidField23EWithOptionalValue() throws MTMessageParsingException {

        Field23 field23E =  Field23.parse("TELI/3226553478", ConnectorConstants.OPTION_E);

        Assert.assertEquals("TELI", field23E.getCode());
        Assert.assertEquals("3226553478", field23E.getAdditionalInformation());
    }

    @Test
    public void testValidField23EWithoutOptionalValue() throws MTMessageParsingException {

        Field23 field23E =  Field23.parse("CHBQ", ConnectorConstants.OPTION_E);

        Assert.assertEquals("CHBQ", field23E.getCode());

        Assert.assertNull(field23E.getAdditionalInformation());
    }

    @Test(dataProvider = "invalidField23EDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Instruction Code in Text Block is in invalid format")
    public void testInvalidField23EValue(String field23EString) throws Exception {

        Field23.parse(field23EString, ConnectorConstants.OPTION_E);
    }

    @Test
    public void testValidField26TValue() throws MTMessageParsingException {

        Field26 field26T =  Field26.parse("K90", ConnectorConstants.OPTION_T);

        Assert.assertEquals("K90", field26T.getValue());
    }

    @Test(dataProvider = "invalidField26TDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Transaction Type Code in Text Block is in invalid format")
    public void testInvalidField26TValue(String field26TString) throws Exception {

        Field26.parse(field26TString, ConnectorConstants.OPTION_T);
    }

    @Test
    public void testValidField32AValue() throws MTMessageParsingException {

        Field32 field32A =  Field32.parse("981209USD1000,00", ConnectorConstants.OPTION_A);

        Assert.assertEquals("981209", field32A.getDate());
        Assert.assertEquals("USD", field32A.getCurrency());
        Assert.assertEquals("1000,00", field32A.getAmount());
    }

    @Test(dataProvider = "invalidField32ADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Value in Text Block is in invalid format")
    public void testInvalidField32AValue(String field32AString) throws Exception {

        Field32.parse(field32AString, ConnectorConstants.OPTION_A);
    }

    @Test
    public void testValidField33BValue() throws MTMessageParsingException {

        Field33 field33B =  Field33.parse("USD1000,00", ConnectorConstants.OPTION_B);

        Assert.assertEquals("USD", field33B.getCurrency());
        Assert.assertEquals("1000,00", field33B.getAmount());
    }

    @Test(dataProvider = "invalidField33BDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Instructed Amount in Text Block is in invalid format")
    public void testInvalidField33BValue(String field33BString) throws Exception {

        Field33.parse(field33BString, ConnectorConstants.OPTION_B);
    }

    @Test
    public void testValidField36Value() throws MTMessageParsingException {

        Field36 field36 =  Field36.parse("0,9236", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals("0,9236", field36.getValue());
    }

    @Test(dataProvider = "invalidField36DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Exchange Rate in Text Block is in invalid format")
    public void testInvalidField36Value(String field36String) throws Exception {

        Field36.parse(field36String, ConnectorConstants.NO_LETTER_OPTION);
    }

    @Test
    public void testValidField50AWithOptionalValue() throws MTMessageParsingException {

        Field50 field50A =  Field50.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field50A.getAccount());
        Assert.assertEquals("VISTUS31", field50A.getIdentifierCode());

        Assert.assertNull(field50A.getDetails());
        Assert.assertNull(field50A.getPartyIdentifier());
    }

    @Test
    public void testValidField50AWithoutOptionalValue() throws MTMessageParsingException {

        Field50 field50A =  Field50.parse("VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("VISTUS31", field50A.getIdentifierCode());

        Assert.assertNull(field50A.getAccount());
        Assert.assertNull(field50A.getDetails());
        Assert.assertNull(field50A.getPartyIdentifier());
    }

    @Test(dataProvider = "invalidField50ADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField50AValue(String field50AString) {

        try {
            Field50 field50A = Field50.parse(field50AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field50A.getIdentifierCode() == null || field50A.getDetails() != null
                    || field50A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Ordering Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField50FValue() throws MTMessageParsingException {

        Field50 field50F =  Field50.parse("/12345678\n" +
                "1/SMITH JOHN\n" +
                "2/299, PARK AVENUE\n" +
                "3/US/NEW YORK, NY 10017", ConnectorConstants.OPTION_F);

        Assert.assertEquals("/12345678", field50F.getPartyIdentifier());
        Assert.assertEquals(
                List.of("1/SMITH JOHN", "2/299, PARK AVENUE", "3/US/NEW YORK, NY 10017"), field50F.getDetails());

        Assert.assertNull(field50F.getAccount());
        Assert.assertNull(field50F.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField50FDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField50FValue(String field50FString) {

        try {
            Field50 field50F = Field50.parse(field50FString, ConnectorConstants.OPTION_F);

            Assert.assertTrue(field50F.getPartyIdentifier() == null || field50F.getDetails() == null
                    || field50F.getLocation() != null || field50F.getAccount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Ordering Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField50KWithOptionalValue() throws MTMessageParsingException {

        Field50 field50K =  Field50.parse("/12345678\n" +
                "JOHN DOE\n" +
                "123, FAKE STREET\n" +
                "FAKETOWN", ConnectorConstants.OPTION_K);

        Assert.assertEquals("/12345678", field50K.getAccount());
        Assert.assertEquals(
                List.of("JOHN DOE", "123, FAKE STREET", "FAKETOWN"), field50K.getDetails());

        Assert.assertNull(field50K.getPartyIdentifier());
        Assert.assertNull(field50K.getIdentifierCode());
    }

    @Test
    public void testValidField50KWithoutOptionalValue() throws MTMessageParsingException {

        Field50 field50K =  Field50.parse("JOHN DOE\n" +
                "123, FAKE STREET\n" +
                "FAKETOWN", ConnectorConstants.OPTION_K);

        Assert.assertEquals(
                List.of("JOHN DOE", "123, FAKE STREET", "FAKETOWN"), field50K.getDetails());

        Assert.assertNull(field50K.getAccount());
        Assert.assertNull(field50K.getPartyIdentifier());
        Assert.assertNull(field50K.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField50KDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField50KValue(String field50KString) {

        try {
            Field50 field50K = Field50.parse(field50KString, ConnectorConstants.OPTION_K);

            Assert.assertTrue(field50K.getDetails() == null || field50K.getLocation() != null
                    || field50K.getPartyIdentifier() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Ordering Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField51AWithOptionalValue() throws MTMessageParsingException {

        Field51 field51A =  Field51.parse("/293456-1254349-82\nVISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field51A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field51A.getIdentifierCode());
    }

    @Test
    public void testValidField51AWithoutOptionalValue() throws MTMessageParsingException {

        Field51 field51A =  Field51.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field51A.getIdentifierCode());

        Assert.assertNull(field51A.getPartyIdentifier());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField51AValue(String field51AString) {

        try {
            Field51 field51A = Field51.parse(field51AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field51A.getIdentifierCode() == null || field51A.getDetails() != null
                    || field51A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Sending Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField52AWithOptionalValue() throws MTMessageParsingException {

        Field52 field52A =  Field52.parse("/A\nVISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/A", field52A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field52A.getIdentifierCode());

        Assert.assertNull(field52A.getDetails());
    }

    @Test
    public void testValidField52AWithoutOptionalValue() throws MTMessageParsingException {

        Field52 field52A =  Field52.parse("VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("VISTUS31", field52A.getIdentifierCode());

        Assert.assertNull(field52A.getPartyIdentifier());
        Assert.assertNull(field52A.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField52AValue(String field52AString) {

        try {
            Field52 field52A = Field52.parse(field52AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field52A.getIdentifierCode() == null || field52A.getDetails() != null
                    || field52A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Ordering Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField52DWithOptionalValue() throws MTMessageParsingException {

        Field52 field52D =  Field52.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field52D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field52D.getDetails());

        Assert.assertNull(field52D.getIdentifierCode());
    }

    @Test
    public void testValidField52DWithoutOptionalValue() throws MTMessageParsingException {

        Field52 field52D =  Field52.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field52D.getDetails());

        Assert.assertNull(field52D.getPartyIdentifier());
        Assert.assertNull(field52D.getIdentifierCode());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField52DValue(String field52DString)  {

        try {
            Field52 field52D = Field52.parse(field52DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field52D.getDetails() == null || field52D.getAccount() != null
                    || field52D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Ordering Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField53AWithOptionalValue() throws MTMessageParsingException {

        Field53 field53A =  Field53.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field53A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field53A.getIdentifierCode());

        Assert.assertNull(field53A.getDetails());
        Assert.assertNull(field53A.getLocation());
    }

    @Test
    public void testValidField53AWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53A =  Field53.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field53A.getIdentifierCode());

        Assert.assertNull(field53A.getPartyIdentifier());
        Assert.assertNull(field53A.getDetails());
        Assert.assertNull(field53A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField53AValue(String field53AString) {

        try {
            Field53 field53A = Field53.parse(field53AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field53A.getIdentifierCode() == null || field53A.getDetails() != null
                    || field53A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Sender's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField53BWithOptionalValue() throws MTMessageParsingException {

        Field53 field53B =  Field53.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("/DE12345678901234567890", field53B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field53B.getLocation());

        Assert.assertNull(field53B.getIdentifierCode());
        Assert.assertNull(field53B.getDetails());
    }

    @Test
    public void testValidField53BWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53B =  Field53.parse("FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("FINANZBANK AG", field53B.getLocation());

        Assert.assertNull(field53B.getPartyIdentifier());
        Assert.assertNull(field53B.getIdentifierCode());
        Assert.assertNull(field53B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField53BValue(String field53BString) {

        try {
            Field53 field53B = Field53.parse(field53BString, ConnectorConstants.OPTION_B);

            Assert.assertTrue(field53B.getIdentifierCode() != null || field53B.getDetails() != null
                    || field53B.getAccount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Sender's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField53DWithOptionalValue() throws MTMessageParsingException {

        Field53 field53D =  Field53.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field53D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field53D.getDetails());

        Assert.assertNull(field53D.getIdentifierCode());
        Assert.assertNull(field53D.getLocation());
    }

    @Test
    public void testValidField53DWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53D =  Field53.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field53D.getDetails());

        Assert.assertNull(field53D.getPartyIdentifier());
        Assert.assertNull(field53D.getIdentifierCode());
        Assert.assertNull(field53D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField53DValue(String field53DString) {

        try {
            Field53 field53D = Field53.parse(field53DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field53D.getDetails() == null || field53D.getAccount() != null
                    || field53D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Sender's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField54AWithOptionalValue() throws MTMessageParsingException {

        Field54 field54A =  Field54.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field54A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field54A.getIdentifierCode());

        Assert.assertNull(field54A.getDetails());
        Assert.assertNull(field54A.getLocation());
    }

    @Test
    public void testValidField54AWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54A =  Field54.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field54A.getIdentifierCode());

        Assert.assertNull(field54A.getPartyIdentifier());
        Assert.assertNull(field54A.getDetails());
        Assert.assertNull(field54A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField54AValue(String field54AString) {

        try {
            Field54 field54A = Field54.parse(field54AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field54A.getIdentifierCode() == null || field54A.getDetails() != null
                    || field54A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Receiver's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField54BWithOptionalValue() throws MTMessageParsingException {

        Field54 field54B =  Field54.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("/DE12345678901234567890", field54B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field54B.getLocation());

        Assert.assertNull(field54B.getIdentifierCode());
        Assert.assertNull(field54B.getDetails());
    }

    @Test
    public void testValidField54BWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54B =  Field54.parse("FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("FINANZBANK AG", field54B.getLocation());

        Assert.assertNull(field54B.getPartyIdentifier());
        Assert.assertNull(field54B.getIdentifierCode());
        Assert.assertNull(field54B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField54BValue(String field54BString) {

        try {
            Field54 field54B = Field54.parse(field54BString, ConnectorConstants.OPTION_B);

            Assert.assertTrue(field54B.getIdentifierCode() != null || field54B.getDetails() != null
                    || field54B.getAccount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Receiver's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField54DWithOptionalValue() throws MTMessageParsingException {

        Field54 field54D =  Field54.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field54D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field54D.getDetails());

        Assert.assertNull(field54D.getIdentifierCode());
        Assert.assertNull(field54D.getLocation());
    }

    @Test
    public void testValidField54DWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54D =  Field54.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field54D.getDetails());

        Assert.assertNull(field54D.getPartyIdentifier());
        Assert.assertNull(field54D.getIdentifierCode());
        Assert.assertNull(field54D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField54DValue(String field54DString) {

        try {
            Field54 field54D = Field54.parse(field54DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field54D.getDetails() == null || field54D.getAccount() != null
                    || field54D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Receiver's Correspondent in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField55AWithOptionalValue() throws MTMessageParsingException {

        Field55 field55A =  Field55.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field55A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field55A.getIdentifierCode());

        Assert.assertNull(field55A.getDetails());
        Assert.assertNull(field55A.getLocation());
    }

    @Test
    public void testValidField55AWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55A =  Field55.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field55A.getIdentifierCode());

        Assert.assertNull(field55A.getPartyIdentifier());
        Assert.assertNull(field55A.getDetails());
        Assert.assertNull(field55A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField55AValue(String field55AString) {

        try {
            Field55 field55A = Field55.parse(field55AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field55A.getIdentifierCode() == null || field55A.getDetails() != null
                    || field55A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals(
                    "Third Reimbursement Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField55BWithOptionalValue() throws MTMessageParsingException {

        Field55 field55B =  Field55.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("/DE12345678901234567890", field55B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field55B.getLocation());

        Assert.assertNull(field55B.getIdentifierCode());
        Assert.assertNull(field55B.getDetails());
    }

    @Test
    public void testValidField55BWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55B =  Field55.parse("FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("FINANZBANK AG", field55B.getLocation());

        Assert.assertNull(field55B.getPartyIdentifier());
        Assert.assertNull(field55B.getIdentifierCode());
        Assert.assertNull(field55B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField55BValue(String field55BString) {

        try {
            Field55 field55B = Field55.parse(field55BString, ConnectorConstants.OPTION_B);

            Assert.assertTrue(field55B.getIdentifierCode() != null || field55B.getDetails() != null
                    || field55B.getAccount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals(
                    "Third Reimbursement Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField55DWithOptionalValue() throws MTMessageParsingException {

        Field55 field55D =  Field55.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field55D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field55D.getDetails());

        Assert.assertNull(field55D.getIdentifierCode());
        Assert.assertNull(field55D.getLocation());
    }

    @Test
    public void testValidField55DWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55D =  Field55.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field55D.getDetails());

        Assert.assertNull(field55D.getPartyIdentifier());
        Assert.assertNull(field55D.getIdentifierCode());
        Assert.assertNull(field55D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField55DValue(String field55DString) {

        try {
            Field55 field55D = Field55.parse(field55DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field55D.getDetails() == null || field55D.getAccount() != null
                    || field55D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals(
                    "Third Reimbursement Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField56AWithOptionalValue() throws MTMessageParsingException {

        Field56 field56A =  Field56.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field56A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field56A.getIdentifierCode());

        Assert.assertNull(field56A.getDetails());
    }

    @Test
    public void testValidField56AWithoutOptionalValue() throws MTMessageParsingException {

        Field56 field56A =  Field56.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field56A.getIdentifierCode());

        Assert.assertNull(field56A.getPartyIdentifier());
        Assert.assertNull(field56A.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField56AValue(String field56AString) {

        try {
            Field56 field56A = Field56.parse(field56AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field56A.getIdentifierCode() == null || field56A.getDetails() != null
                    || field56A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Intermediary Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField56CValue() throws MTMessageParsingException {

        Field56 field56C =  Field56.parse("/293456-1254349-82", ConnectorConstants.OPTION_C);

        Assert.assertEquals("/293456-1254349-82", field56C.getPartyIdentifier());

        Assert.assertNull(field56C.getIdentifierCode());
        Assert.assertNull(field56C.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptCDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Intermediary Institution in Text Block is in invalid format")
    public void testInvalidField56CValue(String field56CString) throws Exception {

        Field56.parse(field56CString, ConnectorConstants.OPTION_C);
    }

    @Test
    public void testValidField56DWithOptionalValue() throws MTMessageParsingException {

        Field56 field56D =  Field56.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field56D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field56D.getDetails());

        Assert.assertNull(field56D.getIdentifierCode());
    }

    @Test
    public void testValidField56DWithoutOptionalValue() throws MTMessageParsingException {

        Field56 field56D =  Field56.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field56D.getDetails());

        Assert.assertNull(field56D.getPartyIdentifier());
        Assert.assertNull(field56D.getIdentifierCode());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField56DValue(String field56DString) {

        try {
            Field56 field56D = Field56.parse(field56DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field56D.getDetails() == null || field56D.getAccount() != null
                    || field56D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Intermediary Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField57AWithOptionalValue() throws MTMessageParsingException {

        Field57 field57A =  Field57.parse("/293456-1254349-82\n" +
                "VISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field57A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field57A.getIdentifierCode());

        Assert.assertNull(field57A.getDetails());
        Assert.assertNull(field57A.getLocation());
    }

    @Test
    public void testValidField57AWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57A =  Field57.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field57A.getIdentifierCode());

        Assert.assertNull(field57A.getPartyIdentifier());
        Assert.assertNull(field57A.getDetails());
        Assert.assertNull(field57A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField57AValue(String field57AString) {

        try {
            Field57 field57A = Field57.parse(field57AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field57A.getIdentifierCode() == null || field57A.getDetails() != null
                    || field57A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Account With Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField57BWithOptionalValue() throws MTMessageParsingException {

        Field57 field57B =  Field57.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("/DE12345678901234567890", field57B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field57B.getLocation());

        Assert.assertNull(field57B.getIdentifierCode());
        Assert.assertNull(field57B.getDetails());
    }

    @Test
    public void testValidField57BWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57B =  Field57.parse("FINANZBANK AG", ConnectorConstants.OPTION_B);

        Assert.assertEquals("FINANZBANK AG", field57B.getLocation());

        Assert.assertNull(field57B.getPartyIdentifier());
        Assert.assertNull(field57B.getIdentifierCode());
        Assert.assertNull(field57B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField57BValue(String field57BString) {

        try {
            Field57 field57B = Field57.parse(field57BString, ConnectorConstants.OPTION_B);

            Assert.assertTrue(field57B.getIdentifierCode() != null || field57B.getDetails() != null
                    || field57B.getAccount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Account With Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField57CValue() throws MTMessageParsingException {

        Field57 field57C =  Field57.parse("/293456-1254349-82", ConnectorConstants.OPTION_C);

        Assert.assertEquals("/293456-1254349-82", field57C.getPartyIdentifier());

        Assert.assertNull(field57C.getIdentifierCode());
        Assert.assertNull(field57C.getDetails());
        Assert.assertNull(field57C.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptCDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Account With Institution in Text Block is in invalid format")
    public void testInvalidField57CValue(String field57CString) throws Exception {

        Field57.parse(field57CString, ConnectorConstants.OPTION_C);
    }

    @Test
    public void testValidField57DWithOptionalValue() throws MTMessageParsingException {

        Field57 field57D =  Field57.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals("/293456-1254349-82", field57D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field57D.getDetails());

        Assert.assertNull(field57D.getIdentifierCode());
        Assert.assertNull(field57D.getLocation());
    }

    @Test
    public void testValidField57DWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57D =  Field57.parse("FINANZBANK AG\n" +
                "EISENSTADT", ConnectorConstants.OPTION_D);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field57D.getDetails());

        Assert.assertNull(field57D.getPartyIdentifier());
        Assert.assertNull(field57D.getIdentifierCode());
        Assert.assertNull(field57D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField57DValue(String field57DString) {

        try {
            Field57 field57D = Field57.parse(field57DString, ConnectorConstants.OPTION_D);

            Assert.assertTrue(field57D.getDetails() == null || field57D.getAccount() != null
                    || field57D.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Account With Institution in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField59WithOptionalValue() throws MTMessageParsingException {

        Field59 field59 =  Field59.parse("/BE62510007547061\n" +
                "JOHANN WILLEMS\n" +
                "RUE JOSEPH II, 19\n" +
                "1040 BRUSSELS", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals("/BE62510007547061", field59.getAccount());
        Assert.assertEquals(List.of("JOHANN WILLEMS", "RUE JOSEPH II, 19", "1040 BRUSSELS"), field59.getDetails());

        Assert.assertNull(field59.getIdentifierCode());
    }

    @Test
    public void testValidField59WithoutOptionalValue() throws MTMessageParsingException {

        Field59 field59 =  Field59.parse("JOHANN WILLEMS\n" +
                "RUE JOSEPH II, 19\n" +
                "1040 BRUSSELS", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals(List.of("JOHANN WILLEMS", "RUE JOSEPH II, 19", "1040 BRUSSELS"), field59.getDetails());

        Assert.assertNull(field59.getAccount());
        Assert.assertNull(field59.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField59DataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField59Value(String field59String) {

        try {
            Field59 field59 = Field59.parse(field59String, ConnectorConstants.NO_LETTER_OPTION);

            Assert.assertTrue(field59.getDetails() == null || field59.getIdentifierCode() != null
                    || field59.getPartyIdentifier() != null || field59.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Beneficiary Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField59AWithOptionalValue() throws MTMessageParsingException {

        Field59 field59A =  Field59.parse("/293456-1254349-82\nVISTUS31", ConnectorConstants.OPTION_A);

        Assert.assertEquals("/293456-1254349-82", field59A.getAccount());
        Assert.assertEquals("VISTUS31", field59A.getIdentifierCode());

        Assert.assertNull(field59A.getDetails());
    }

    @Test
    public void testValidField59AWithoutOptionalValue() throws MTMessageParsingException {

        Field59 field59A =  Field59.parse("ABNANL2A", ConnectorConstants.OPTION_A);

        Assert.assertEquals("ABNANL2A", field59A.getIdentifierCode());

        Assert.assertNull(field59A.getAccount());
        Assert.assertNull(field59A.getDetails());
    }

    @Test(dataProvider = "invalidField59ADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField59AValue(String field59AString) {

        try {
            Field59 field59A = Field59.parse(field59AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field59A.getIdentifierCode() == null || field59A.getDetails() != null
                    || field59A.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Beneficiary Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField59FValue() throws MTMessageParsingException {

        Field59 field59F =  Field59.parse("/12345678\n" +
                "1/SMITH JOHN\n" +
                "2/299, PARK AVENUE\n" +
                "3/US/NEW YORK, NY 10017", ConnectorConstants.OPTION_F);

        Assert.assertEquals("/12345678", field59F.getAccount());
        Assert.assertEquals(
                List.of("1/SMITH JOHN", "2/299, PARK AVENUE", "3/US/NEW YORK, NY 10017"), field59F.getDetails());

        Assert.assertNull(field59F.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField59FDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField59FValue(String field59FString) {

        try {
            Field59 field59F = Field59.parse(field59FString, ConnectorConstants.OPTION_F);

            Assert.assertTrue(field59F.getAccount() == null || field59F.getDetails() == null
                    || field59F.getIdentifierCode() != null || field59F.getLocation() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Beneficiary Customer in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField70Value() throws MTMessageParsingException {

        Field70 field70 =  Field70.parse("/INV/abc/SDF-96//1234-234///ROC/98I\n" +
                "U87", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals(List.of("/INV/abc/SDF-96//1234-234///ROC/98I", "U87"), field70.getValues());
    }

    @Test(dataProvider = "invalidField70DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Remittance Information in Text Block is in invalid format")
    public void testInvalidField70Value(String field70String) throws Exception {

        Field70.parse(field70String, ConnectorConstants.NO_LETTER_OPTION);
    }

    @Test
    public void testValidField71AValue() throws MTMessageParsingException {

        Field71 field71A =  Field71.parse("BEN", ConnectorConstants.OPTION_A);

        Assert.assertEquals("BEN", field71A.getCode());

        Assert.assertNull(field71A.getCurrency());
        Assert.assertNull(field71A.getAmount());
    }

    @Test(dataProvider = "invalidField71ADataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField71AValue(String field71AString) {

        try {
            Field71 field71A = Field71.parse(field71AString, ConnectorConstants.OPTION_A);

            Assert.assertTrue(field71A.getCode() == null || field71A.getCurrency() != null
                    || field71A.getAmount() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Details of Charges in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField71FValue() throws MTMessageParsingException {

        Field71 field71F =  Field71.parse("EUR8,00", ConnectorConstants.OPTION_F);

        Assert.assertEquals("EUR", field71F.getCurrency());
        Assert.assertEquals("8,00", field71F.getAmount());

        Assert.assertNull(field71F.getCode());
    }

    @Test(dataProvider = "invalidField71FDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField71FValue(String field71FString) {

        try {
            Field71 field71F = Field71.parse(field71FString, ConnectorConstants.OPTION_F);

            Assert.assertTrue(field71F.getCode() != null || field71F.getCurrency() == null
                    || field71F.getAmount() == null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Sender's Charges in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField71GValue() throws MTMessageParsingException {

        Field71 field71G =  Field71.parse("USD25,00", ConnectorConstants.OPTION_G);

        Assert.assertEquals("USD", field71G.getCurrency());
        Assert.assertEquals("25,00", field71G.getAmount());

        Assert.assertNull(field71G.getCode());
    }

    @Test(dataProvider = "invalidField71GDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField71GValue(String field71GString) {

        try {
            Field71 field71G = Field71.parse(field71GString, ConnectorConstants.OPTION_G);

            Assert.assertTrue(field71G.getCode() != null || field71G.getCurrency() == null
                    || field71G.getAmount() == null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Receiver's Charges in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField72Value() throws MTMessageParsingException {

        Field72 field72 =  Field72.parse("/INS/ABNANL2A\n" +
                "/INS/ABNANL2A", ConnectorConstants.NO_LETTER_OPTION);

        Assert.assertEquals(List.of("/INS/ABNANL2A", "/INS/ABNANL2A"), field72.getValues());
    }

    @Test(dataProvider = "invalidField72DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender to Receiver Information in Text Block is in invalid format")
    public void testInvalidField72Value(String field72String) throws Exception {

        Field72.parse(field72String, ConnectorConstants.NO_LETTER_OPTION);
    }

    @Test
    public void testValidField77BValue() throws MTMessageParsingException {

        Field77 field77B =  Field77.parse("/ORDERRES/BE//MEILAAN 1, 9000 GENT\n" +
                "//INS/ABNANL2A", ConnectorConstants.OPTION_B);

        Assert.assertEquals(List.of("/ORDERRES/BE//MEILAAN 1, 9000 GENT", "//INS/ABNANL2A"), field77B.getLines());
    }

    @Test(dataProvider = "invalidField77BDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField77BValue(String field77BString) {

        try {
            Field77 field77B = Field77.parse(field77BString, ConnectorConstants.OPTION_B);

            Assert.assertTrue(field77B.getLines() == null || field77B.getValue() != null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Regulatory Reporting in Text Block is in invalid format", e.getMessage());
        }
    }

    @Test
    public void testValidField77TValue() throws MTMessageParsingException {

        Field77 field77T =  Field77.parse("/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ..."
                , ConnectorConstants.OPTION_T);

        Assert.assertEquals("/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ...", field77T.getValue());
    }

    @Test(dataProvider = "invalidField77TDataProvider", dataProviderClass = MT103ParserTestConstants.class)
    public void testInvalidField77TValue(String field77TString) {

        try {
            Field77 field77T = Field77.parse(field77TString, ConnectorConstants.OPTION_T);

            Assert.assertTrue(field77T.getLines() != null || field77T.getValue() == null);
        } catch (MTMessageParsingException e) {
            Assert.assertEquals("Envelope Contents in Text Block is in invalid format", e.getMessage());
        }
    }
}
