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
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field13C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field23E;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field26T;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field32A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field33B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field36;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field50;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field51A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field52;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field53;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field54;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field55;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field56;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field57;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field59;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field70;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71A;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field71G;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field72;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77B;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field77T;
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

        Field20 field20 =  Field20.parse("Ref254");

        Assert.assertEquals("Ref254", field20.getValue());
    }

    @Test(dataProvider = "invalidField20DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Reference in Text Block is in invalid format")
    public void testInvalidField20Value(String field20String) throws Exception {

        Field20.parse(field20String);
    }

    @Test
    public void testValidField13CValue() throws MTMessageParsingException {

        Field13C field13C =  Field13C.parse("/CLSTIME/1300-0430");

        Assert.assertEquals("CLSTIME", field13C.getCode());
        Assert.assertEquals("1300", field13C.getTime());
        Assert.assertEquals("-", field13C.getSign());
        Assert.assertEquals("0430", field13C.getOffset());
    }

    @Test(dataProvider = "invalidField13CDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Time Indication in Text Block is in invalid format")
    public void testInvalidField13CValue(String field13CString) throws Exception {

        Field13C.parse(field13CString);
    }

    @Test
    public void testValidField23BValue() throws MTMessageParsingException {

        Field23B field23B =  Field23B.parse("SPAY");

        Assert.assertEquals("SPAY", field23B.getValue());
    }

    @Test(dataProvider = "invalidField23BDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Bank Operation Code in Text Block is in invalid format")
    public void testInvalidField23BValue(String field23BString) throws Exception {

        Field23B.parse(field23BString);
    }

    @Test
    public void testValidField23EWithOptionalValue() throws MTMessageParsingException {

        Field23E field23E =  Field23E.parse("TELI/3226553478");

        Assert.assertEquals("TELI", field23E.getInstructionCode());
        Assert.assertEquals("3226553478", field23E.getAdditionalInformation());
    }

    @Test
    public void testValidField23EWithoutOptionalValue() throws MTMessageParsingException {

        Field23E field23E =  Field23E.parse("CHBQ");

        Assert.assertEquals("CHBQ", field23E.getInstructionCode());

        Assert.assertNull(field23E.getAdditionalInformation());
    }

    @Test(dataProvider = "invalidField23EDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Instruction Code in Text Block is in invalid format")
    public void testInvalidField23EValue(String field23EString) throws Exception {

        Field23E.parse(field23EString);
    }

    @Test
    public void testValidField26TValue() throws MTMessageParsingException {

        Field26T field26T =  Field26T.parse("K90");

        Assert.assertEquals("K90", field26T.getValue());
    }

    @Test(dataProvider = "invalidField26TDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Transaction Type Code in Text Block is in invalid format")
    public void testInvalidField26TValue(String field26TString) throws Exception {

        Field26T.parse(field26TString);
    }

    @Test
    public void testValidField32AValue() throws MTMessageParsingException {

        Field32A field32A =  Field32A.parse("981209USD1000,00");

        Assert.assertEquals("981209", field32A.getDate());
        Assert.assertEquals("USD", field32A.getCurrency());
        Assert.assertEquals("1000,00", field32A.getAmount());
    }

    @Test(dataProvider = "invalidField32ADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Value in Text Block is in invalid format")
    public void testInvalidField32AValue(String field32AString) throws Exception {

        Field32A.parse(field32AString);
    }

    @Test
    public void testValidField33BValue() throws MTMessageParsingException {

        Field33B field33B =  Field33B.parse("USD1000,00");

        Assert.assertEquals("USD", field33B.getCurrency());
        Assert.assertEquals("1000,00", field33B.getAmount());
    }

    @Test(dataProvider = "invalidField33BDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Instructed Amount in Text Block is in invalid format")
    public void testInvalidField33BValue(String field33BString) throws Exception {

        Field33B.parse(field33BString);
    }

    @Test
    public void testValidField36Value() throws MTMessageParsingException {

        Field36 field36 =  Field36.parse("0,9236");

        Assert.assertEquals("0,9236", field36.getValue());
    }

    @Test(dataProvider = "invalidField36DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Exchange Rate in Text Block is in invalid format")
    public void testInvalidField36Value(String field36String) throws Exception {

        Field36.parse(field36String);
    }

    @Test
    public void testValidField50AWithOptionalValue() throws MTMessageParsingException {

        Field50 field50A =  Field50.parse("/293456-1254349-82\n" +
                "VISTUS31", Field50.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field50A.getAccount());
        Assert.assertEquals("VISTUS31", field50A.getIdentifierCode());

        Assert.assertNull(field50A.getDetails());
        Assert.assertNull(field50A.getPartyIdentifier());
    }

    @Test
    public void testValidField50AWithoutOptionalValue() throws MTMessageParsingException {

        Field50 field50A =  Field50.parse("VISTUS31", Field50.OPTION_A_TAG);

        Assert.assertEquals("VISTUS31", field50A.getIdentifierCode());

        Assert.assertNull(field50A.getAccount());
        Assert.assertNull(field50A.getDetails());
        Assert.assertNull(field50A.getPartyIdentifier());
    }

    @Test(dataProvider = "invalidField50ADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Ordering Customer in Text Block is in invalid format")
    public void testInvalidField50AValue(String field50AString) throws Exception {

        Field50.parse(field50AString, Field50.OPTION_A_TAG);
    }

    @Test
    public void testValidField50FValue() throws MTMessageParsingException {

        Field50 field50F =  Field50.parse("/12345678\n" +
                "1/SMITH JOHN\n" +
                "2/299, PARK AVENUE\n" +
                "3/US/NEW YORK, NY 10017", Field50.OPTION_F_TAG);

        Assert.assertEquals("/12345678", field50F.getPartyIdentifier());
        Assert.assertEquals(
                List.of("1/SMITH JOHN", "2/299, PARK AVENUE", "3/US/NEW YORK, NY 10017"), field50F.getDetails());

        Assert.assertNull(field50F.getAccount());
        Assert.assertNull(field50F.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField50FDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Ordering Customer in Text Block is in invalid format")
    public void testInvalidField50FValue(String field50FString) throws Exception {

        Field50.parse(field50FString, Field50.OPTION_F_TAG);
    }

    @Test
    public void testValidField50KWithOptionalValue() throws MTMessageParsingException {

        Field50 field50K =  Field50.parse("/12345678\n" +
                "JOHN DOE\n" +
                "123, FAKE STREET\n" +
                "FAKETOWN", Field50.OPTION_K_TAG);

        Assert.assertEquals("12345678", field50K.getAccount());
        Assert.assertEquals(
                List.of("JOHN DOE", "123, FAKE STREET", "FAKETOWN"), field50K.getDetails());

        Assert.assertNull(field50K.getPartyIdentifier());
        Assert.assertNull(field50K.getIdentifierCode());
    }

    @Test
    public void testValidField50KWithoutOptionalValue() throws MTMessageParsingException {

        Field50 field50K =  Field50.parse("JOHN DOE\n" +
                "123, FAKE STREET\n" +
                "FAKETOWN", Field50.OPTION_K_TAG);

        Assert.assertEquals(
                List.of("JOHN DOE", "123, FAKE STREET", "FAKETOWN"), field50K.getDetails());

        Assert.assertNull(field50K.getAccount());
        Assert.assertNull(field50K.getPartyIdentifier());
        Assert.assertNull(field50K.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField50KDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Ordering Customer in Text Block is in invalid format")
    public void testInvalidField50KValue(String field50KString) throws Exception {

        Field50.parse(field50KString, Field50.OPTION_K_TAG);
    }

    @Test
    public void testValidField51AWithOptionalValue() throws MTMessageParsingException {

        Field51A field51A =  Field51A.parse("/293456-1254349-82\nVISTUS31");

        Assert.assertEquals("293456-1254349-82", field51A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field51A.getIdentifierCode());
    }

    @Test
    public void testValidField51AWithoutOptionalValue() throws MTMessageParsingException {

        Field51A field51A =  Field51A.parse("ABNANL2A");

        Assert.assertEquals("ABNANL2A", field51A.getIdentifierCode());

        Assert.assertNull(field51A.getPartyIdentifier());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sending Institution in Text Block is in invalid format")
    public void testInvalidField51AValue(String field51AString) throws Exception {

        Field51A.parse(field51AString);
    }

    @Test
    public void testValidField52AWithOptionalValue() throws MTMessageParsingException {

        Field52 field52A =  Field52.parse("/A\nVISTUS31", Field52.OPTION_A_TAG);

        Assert.assertEquals("A", field52A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field52A.getIdentifierCode());

        Assert.assertNull(field52A.getDetails());
    }

    @Test
    public void testValidField52AWithoutOptionalValue() throws MTMessageParsingException {

        Field52 field52A =  Field52.parse("VISTUS31", Field52.OPTION_A_TAG);

        Assert.assertEquals("VISTUS31", field52A.getIdentifierCode());

        Assert.assertNull(field52A.getPartyIdentifier());
        Assert.assertNull(field52A.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Ordering Institution in Text Block is in invalid format")
    public void testInvalidField52AValue(String field52AString) throws Exception {

        Field52.parse(field52AString, Field52.OPTION_A_TAG);
    }

    @Test
    public void testValidField52DWithOptionalValue() throws MTMessageParsingException {

        Field52 field52D =  Field52.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field52.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field52D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field52D.getDetails());

        Assert.assertNull(field52D.getIdentifierCode());
    }

    @Test
    public void testValidField52DWithoutOptionalValue() throws MTMessageParsingException {

        Field52 field52D =  Field52.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field52.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field52D.getDetails());

        Assert.assertNull(field52D.getPartyIdentifier());
        Assert.assertNull(field52D.getIdentifierCode());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Ordering Institution in Text Block is in invalid format")
    public void testInvalidField52DValue(String field52DString) throws Exception {

        Field52.parse(field52DString, Field52.OPTION_D_TAG);
    }

    @Test
    public void testValidField53AWithOptionalValue() throws MTMessageParsingException {

        Field53 field53A =  Field53.parse("/293456-1254349-82\n" +
                "VISTUS31", Field53.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field53A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field53A.getIdentifierCode());

        Assert.assertNull(field53A.getDetails());
        Assert.assertNull(field53A.getLocation());
    }

    @Test
    public void testValidField53AWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53A =  Field53.parse("ABNANL2A", Field53.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field53A.getIdentifierCode());

        Assert.assertNull(field53A.getPartyIdentifier());
        Assert.assertNull(field53A.getDetails());
        Assert.assertNull(field53A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Correspondent in Text Block is in invalid format")
    public void testInvalidField53AValue(String field53AString) throws Exception {

        Field53.parse(field53AString, Field53.OPTION_A_TAG);
    }

    @Test
    public void testValidField53BWithOptionalValue() throws MTMessageParsingException {

        Field53 field53B =  Field53.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", Field53.OPTION_B_TAG);

        Assert.assertEquals("DE12345678901234567890", field53B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field53B.getLocation());

        Assert.assertNull(field53B.getIdentifierCode());
        Assert.assertNull(field53B.getDetails());
    }

    @Test
    public void testValidField53BWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53B =  Field53.parse("FINANZBANK AG", Field53.OPTION_B_TAG);

        Assert.assertEquals("FINANZBANK AG", field53B.getLocation());

        Assert.assertNull(field53B.getPartyIdentifier());
        Assert.assertNull(field53B.getIdentifierCode());
        Assert.assertNull(field53B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Correspondent in Text Block is in invalid format")
    public void testInvalidField53BValue(String field53BString) throws Exception {

        Field53.parse(field53BString, Field53.OPTION_B_TAG);
    }

    @Test
    public void testValidField53DWithOptionalValue() throws MTMessageParsingException {

        Field53 field53D =  Field53.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field53.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field53D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field53D.getDetails());

        Assert.assertNull(field53D.getIdentifierCode());
        Assert.assertNull(field53D.getLocation());
    }

    @Test
    public void testValidField53DWithoutOptionalValue() throws MTMessageParsingException {

        Field53 field53D =  Field53.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field53.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field53D.getDetails());

        Assert.assertNull(field53D.getPartyIdentifier());
        Assert.assertNull(field53D.getIdentifierCode());
        Assert.assertNull(field53D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Correspondent in Text Block is in invalid format")
    public void testInvalidField53DValue(String field53DString) throws Exception {

        Field53.parse(field53DString, Field53.OPTION_D_TAG);
    }

    @Test
    public void testValidField54AWithOptionalValue() throws MTMessageParsingException {

        Field54 field54A =  Field54.parse("/293456-1254349-82\n" +
                "VISTUS31", Field54.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field54A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field54A.getIdentifierCode());

        Assert.assertNull(field54A.getDetails());
        Assert.assertNull(field54A.getLocation());
    }

    @Test
    public void testValidField54AWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54A =  Field54.parse("ABNANL2A", Field54.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field54A.getIdentifierCode());

        Assert.assertNull(field54A.getPartyIdentifier());
        Assert.assertNull(field54A.getDetails());
        Assert.assertNull(field54A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Receiver's Correspondent in Text Block is in invalid format")
    public void testInvalidField54AValue(String field54AString) throws Exception {

        Field54.parse(field54AString, Field54.OPTION_A_TAG);
    }

    @Test
    public void testValidField54BWithOptionalValue() throws MTMessageParsingException {

        Field54 field54B =  Field54.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", Field54.OPTION_B_TAG);

        Assert.assertEquals("DE12345678901234567890", field54B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field54B.getLocation());

        Assert.assertNull(field54B.getIdentifierCode());
        Assert.assertNull(field54B.getDetails());
    }

    @Test
    public void testValidField54BWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54B =  Field54.parse("FINANZBANK AG", Field54.OPTION_B_TAG);

        Assert.assertEquals("FINANZBANK AG", field54B.getLocation());

        Assert.assertNull(field54B.getPartyIdentifier());
        Assert.assertNull(field54B.getIdentifierCode());
        Assert.assertNull(field54B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Receiver's Correspondent in Text Block is in invalid format")
    public void testInvalidField54BValue(String field54BString) throws Exception {

        Field54.parse(field54BString, Field54.OPTION_B_TAG);
    }

    @Test
    public void testValidField54DWithOptionalValue() throws MTMessageParsingException {

        Field54 field54D =  Field54.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field54.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field54D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field54D.getDetails());

        Assert.assertNull(field54D.getIdentifierCode());
        Assert.assertNull(field54D.getLocation());
    }

    @Test
    public void testValidField54DWithoutOptionalValue() throws MTMessageParsingException {

        Field54 field54D =  Field54.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field54.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field54D.getDetails());

        Assert.assertNull(field54D.getPartyIdentifier());
        Assert.assertNull(field54D.getIdentifierCode());
        Assert.assertNull(field54D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Receiver's Correspondent in Text Block is in invalid format")
    public void testInvalidField54DValue(String field54DString) throws Exception {

        Field54.parse(field54DString, Field54.OPTION_D_TAG);
    }

    @Test
    public void testValidField55AWithOptionalValue() throws MTMessageParsingException {

        Field55 field55A =  Field55.parse("/293456-1254349-82\n" +
                "VISTUS31", Field55.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field55A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field55A.getIdentifierCode());

        Assert.assertNull(field55A.getDetails());
        Assert.assertNull(field55A.getLocation());
    }

    @Test
    public void testValidField55AWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55A =  Field55.parse("ABNANL2A", Field55.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field55A.getIdentifierCode());

        Assert.assertNull(field55A.getPartyIdentifier());
        Assert.assertNull(field55A.getDetails());
        Assert.assertNull(field55A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Third Reimbursement Institution in Text Block is in invalid format")
    public void testInvalidField55AValue(String field55AString) throws Exception {

        Field55.parse(field55AString, Field55.OPTION_A_TAG);
    }

    @Test
    public void testValidField55BWithOptionalValue() throws MTMessageParsingException {

        Field55 field55B =  Field55.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", Field55.OPTION_B_TAG);

        Assert.assertEquals("DE12345678901234567890", field55B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field55B.getLocation());

        Assert.assertNull(field55B.getIdentifierCode());
        Assert.assertNull(field55B.getDetails());
    }

    @Test
    public void testValidField55BWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55B =  Field55.parse("FINANZBANK AG", Field55.OPTION_B_TAG);

        Assert.assertEquals("FINANZBANK AG", field55B.getLocation());

        Assert.assertNull(field55B.getPartyIdentifier());
        Assert.assertNull(field55B.getIdentifierCode());
        Assert.assertNull(field55B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Third Reimbursement Institution in Text Block is in invalid format")
    public void testInvalidField55BValue(String field55BString) throws Exception {

        Field55.parse(field55BString, Field55.OPTION_B_TAG);
    }

    @Test
    public void testValidField55DWithOptionalValue() throws MTMessageParsingException {

        Field55 field55D =  Field55.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field55.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field55D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field55D.getDetails());

        Assert.assertNull(field55D.getIdentifierCode());
        Assert.assertNull(field55D.getLocation());
    }

    @Test
    public void testValidField55DWithoutOptionalValue() throws MTMessageParsingException {

        Field55 field55D =  Field55.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field55.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field55D.getDetails());

        Assert.assertNull(field55D.getPartyIdentifier());
        Assert.assertNull(field55D.getIdentifierCode());
        Assert.assertNull(field55D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Third Reimbursement Institution in Text Block is in invalid format")
    public void testInvalidField55DValue(String field55DString) throws Exception {

        Field55.parse(field55DString, Field55.OPTION_D_TAG);
    }

    @Test
    public void testValidField56AWithOptionalValue() throws MTMessageParsingException {

        Field56 field56A =  Field56.parse("/293456-1254349-82\n" +
                "VISTUS31", Field56.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field56A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field56A.getIdentifierCode());

        Assert.assertNull(field56A.getDetails());
    }

    @Test
    public void testValidField56AWithoutOptionalValue() throws MTMessageParsingException {

        Field56 field56A =  Field56.parse("ABNANL2A", Field56.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field56A.getIdentifierCode());

        Assert.assertNull(field56A.getPartyIdentifier());
        Assert.assertNull(field56A.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Intermediary Institution in Text Block is in invalid format")
    public void testInvalidField56AValue(String field56AString) throws Exception {

        Field56.parse(field56AString, Field56.OPTION_A_TAG);
    }

    @Test
    public void testValidField56CValue() throws MTMessageParsingException {

        Field56 field56C =  Field56.parse("/293456-1254349-82", Field56.OPTION_C_TAG);

        Assert.assertEquals("293456-1254349-82", field56C.getPartyIdentifier());

        Assert.assertNull(field56C.getIdentifierCode());
        Assert.assertNull(field56C.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptCDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Intermediary Institution in Text Block is in invalid format")
    public void testInvalidField56CValue(String field56CString) throws Exception {

        Field56.parse(field56CString, Field56.OPTION_C_TAG);
    }

    @Test
    public void testValidField56DWithOptionalValue() throws MTMessageParsingException {

        Field56 field56D =  Field56.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field56.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field56D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field56D.getDetails());

        Assert.assertNull(field56D.getIdentifierCode());
    }

    @Test
    public void testValidField56DWithoutOptionalValue() throws MTMessageParsingException {

        Field56 field56D =  Field56.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field56.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field56D.getDetails());

        Assert.assertNull(field56D.getPartyIdentifier());
        Assert.assertNull(field56D.getIdentifierCode());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Intermediary Institution in Text Block is in invalid format")
    public void testInvalidField56DValue(String field56DString) throws Exception {

        Field56.parse(field56DString, Field56.OPTION_D_TAG);
    }

    @Test
    public void testValidField57AWithOptionalValue() throws MTMessageParsingException {

        Field57 field57A =  Field57.parse("/293456-1254349-82\n" +
                "VISTUS31", Field57.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field57A.getPartyIdentifier());
        Assert.assertEquals("VISTUS31", field57A.getIdentifierCode());

        Assert.assertNull(field57A.getDetails());
        Assert.assertNull(field57A.getLocation());
    }

    @Test
    public void testValidField57AWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57A =  Field57.parse("ABNANL2A", Field57.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field57A.getIdentifierCode());

        Assert.assertNull(field57A.getPartyIdentifier());
        Assert.assertNull(field57A.getDetails());
        Assert.assertNull(field57A.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Account With Institution in Text Block is in invalid format")
    public void testInvalidField57AValue(String field57AString) throws Exception {

        Field57.parse(field57AString, Field57.OPTION_A_TAG);
    }

    @Test
    public void testValidField57BWithOptionalValue() throws MTMessageParsingException {

        Field57 field57B =  Field57.parse("/DE12345678901234567890\n" +
                "FINANZBANK AG", Field57.OPTION_B_TAG);

        Assert.assertEquals("DE12345678901234567890", field57B.getPartyIdentifier());
        Assert.assertEquals("FINANZBANK AG", field57B.getLocation());

        Assert.assertNull(field57B.getIdentifierCode());
        Assert.assertNull(field57B.getDetails());
    }

    @Test
    public void testValidField57BWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57B =  Field57.parse("FINANZBANK AG", Field57.OPTION_B_TAG);

        Assert.assertEquals("FINANZBANK AG", field57B.getLocation());

        Assert.assertNull(field57B.getPartyIdentifier());
        Assert.assertNull(field57B.getIdentifierCode());
        Assert.assertNull(field57B.getDetails());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptBDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Account With Institution in Text Block is in invalid format")
    public void testInvalidField57BValue(String field57BString) throws Exception {

        Field57.parse(field57BString, Field57.OPTION_B_TAG);
    }

    @Test
    public void testValidField57CValue() throws MTMessageParsingException {

        Field57 field57C =  Field57.parse("/293456-1254349-82", Field57.OPTION_C_TAG);

        Assert.assertEquals("293456-1254349-82", field57C.getPartyIdentifier());

        Assert.assertNull(field57C.getIdentifierCode());
        Assert.assertNull(field57C.getDetails());
        Assert.assertNull(field57C.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptCDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Account With Institution in Text Block is in invalid format")
    public void testInvalidField57CValue(String field57CString) throws Exception {

        Field57.parse(field57CString, Field57.OPTION_C_TAG);
    }

    @Test
    public void testValidField57DWithOptionalValue() throws MTMessageParsingException {

        Field57 field57D =  Field57.parse("/293456-1254349-82\n" +
                "FINANZBANK AG\n" +
                "EISENSTADT", Field57.OPTION_D_TAG);

        Assert.assertEquals("293456-1254349-82", field57D.getPartyIdentifier());
        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field57D.getDetails());

        Assert.assertNull(field57D.getIdentifierCode());
        Assert.assertNull(field57D.getLocation());
    }

    @Test
    public void testValidField57DWithoutOptionalValue() throws MTMessageParsingException {

        Field57 field57D =  Field57.parse("FINANZBANK AG\n" +
                "EISENSTADT", Field57.OPTION_D_TAG);

        Assert.assertEquals(List.of("FINANZBANK AG", "EISENSTADT"), field57D.getDetails());

        Assert.assertNull(field57D.getPartyIdentifier());
        Assert.assertNull(field57D.getIdentifierCode());
        Assert.assertNull(field57D.getLocation());
    }

    @Test(dataProvider = "invalidPartyIdentifierOptDDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Account With Institution in Text Block is in invalid format")
    public void testInvalidField57DValue(String field57DString) throws Exception {

        Field57.parse(field57DString, Field57.OPTION_D_TAG);
    }

    @Test
    public void testValidField59WithOptionalValue() throws MTMessageParsingException {

        Field59 field59 =  Field59.parse("/BE62510007547061\n" +
                "JOHANN WILLEMS\n" +
                "RUE JOSEPH II, 19\n" +
                "1040 BRUSSELS", Field59.NO_LETTER_OPTION_TAG);

        Assert.assertEquals("BE62510007547061", field59.getAccount());
        Assert.assertEquals(List.of("JOHANN WILLEMS", "RUE JOSEPH II, 19", "1040 BRUSSELS"), field59.getDetails());

        Assert.assertNull(field59.getIdentifierCode());
    }

    @Test
    public void testValidField59WithoutOptionalValue() throws MTMessageParsingException {

        Field59 field59 =  Field59.parse("JOHANN WILLEMS\n" +
                "RUE JOSEPH II, 19\n" +
                "1040 BRUSSELS", Field59.NO_LETTER_OPTION_TAG);

        Assert.assertEquals(List.of("JOHANN WILLEMS", "RUE JOSEPH II, 19", "1040 BRUSSELS"), field59.getDetails());

        Assert.assertNull(field59.getAccount());
        Assert.assertNull(field59.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField59DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Beneficiary Customer in Text Block is in invalid format")
    public void testInvalidField59Value(String field59String) throws Exception {

        Field59.parse(field59String, Field59.NO_LETTER_OPTION_TAG);
    }

    @Test
    public void testValidField59AWithOptionalValue() throws MTMessageParsingException {

        Field59 field59A =  Field59.parse("/293456-1254349-82\nVISTUS31", Field59.OPTION_A_TAG);

        Assert.assertEquals("293456-1254349-82", field59A.getAccount());
        Assert.assertEquals("VISTUS31", field59A.getIdentifierCode());

        Assert.assertNull(field59A.getDetails());
    }

    @Test
    public void testValidField59AWithoutOptionalValue() throws MTMessageParsingException {

        Field59 field59A =  Field59.parse("ABNANL2A", Field59.OPTION_A_TAG);

        Assert.assertEquals("ABNANL2A", field59A.getIdentifierCode());

        Assert.assertNull(field59A.getAccount());
        Assert.assertNull(field59A.getDetails());
    }

    @Test(dataProvider = "invalidField59ADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Beneficiary Customer in Text Block is in invalid format")
    public void testInvalidField59AValue(String field59AString) throws Exception {

        Field59.parse(field59AString, Field59.OPTION_A_TAG);
    }

    @Test
    public void testValidField59FValue() throws MTMessageParsingException {

        Field59 field59F =  Field59.parse("/12345678\n" +
                "1/SMITH JOHN\n" +
                "2/299, PARK AVENUE\n" +
                "3/US/NEW YORK, NY 10017", Field59.OPTION_F_TAG);

        Assert.assertEquals("/12345678", field59F.getAccount());
        Assert.assertEquals(
                List.of("1/SMITH JOHN", "2/299, PARK AVENUE", "3/US/NEW YORK, NY 10017"), field59F.getDetails());

        Assert.assertNull(field59F.getIdentifierCode());
    }

    @Test(dataProvider = "invalidField59FDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Beneficiary Customer in Text Block is in invalid format")
    public void testInvalidField59FValue(String field59FString) throws Exception {

        Field59.parse(field59FString, Field59.OPTION_F_TAG);
    }

    @Test
    public void testValidField70Value() throws MTMessageParsingException {

        Field70 field70 =  Field70.parse("/INV/abc/SDF-96//1234-234///ROC/98I\n" +
                "U87");

        Assert.assertEquals(List.of("/INV/abc/SDF-96//1234-234///ROC/98I", "U87"), field70.getValues());
    }

    @Test(dataProvider = "invalidField70DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Remittance Information in Text Block is in invalid format")
    public void testInvalidField70Value(String field70String) throws Exception {

        Field70.parse(field70String);
    }

    @Test
    public void testValidField71AValue() throws MTMessageParsingException {

        Field71A field71A =  Field71A.parse("BEN");

        Assert.assertEquals("BEN", field71A.getValue());
    }

    @Test(dataProvider = "invalidField71ADataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Details of Charges in Text Block is in invalid format")
    public void testInvalidField71AValue(String field71AString) throws Exception {

        Field71A.parse(field71AString);
    }

    @Test
    public void testValidField71FValue() throws MTMessageParsingException {

        Field71F field71F =  Field71F.parse("EUR8,00");

        Assert.assertEquals("EUR", field71F.getCurrency());
        Assert.assertEquals("8,00", field71F.getAmount());
    }

    @Test(dataProvider = "invalidField71FDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender's Charges in Text Block is in invalid format")
    public void testInvalidField71FValue(String field71FString) throws Exception {

        Field71F.parse(field71FString);
    }

    @Test
    public void testValidField71GValue() throws MTMessageParsingException {

        Field71G field71G =  Field71G.parse("USD25,00");

        Assert.assertEquals("USD", field71G.getCurrency());
        Assert.assertEquals("25,00", field71G.getAmount());
    }

    @Test(dataProvider = "invalidField71GDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Receiver's Charges in Text Block is in invalid format")
    public void testInvalidField71GValue(String field71GString) throws Exception {

        Field71G.parse(field71GString);
    }

    @Test
    public void testValidField72Value() throws MTMessageParsingException {

        Field72 field72 =  Field72.parse("/INS/ABNANL2A\n" +
                "/INS/ABNANL2A");

        Assert.assertEquals(List.of("/INS/ABNANL2A", "/INS/ABNANL2A"), field72.getValues());
    }

    @Test(dataProvider = "invalidField72DataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Sender to Receiver Information in Text Block is in invalid format")
    public void testInvalidField72Value(String field72String) throws Exception {

        Field72.parse(field72String);
    }

    @Test
    public void testValidField77BValue() throws MTMessageParsingException {

        Field77B field77B =  Field77B.parse("/ORDERRES/BE//MEILAAN 1, 9000 GENT\n" +
                "//INS/ABNANL2A");

        Assert.assertEquals(List.of("/ORDERRES/BE//MEILAAN 1, 9000 GENT", "//INS/ABNANL2A"), field77B.getValues());
    }

    @Test(dataProvider = "invalidField77BDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Regulatory Reporting in Text Block is in invalid format")
    public void testInvalidField77BValue(String field77BString) throws Exception {

        Field77B.parse(field77BString);
    }

    @Test
    public void testValidField77TValue() throws MTMessageParsingException {

        Field77T field77T =  Field77T.parse("/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ...");

        Assert.assertEquals("/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ...", field77T.getValue());
    }

    @Test(dataProvider = "invalidField77TDataProvider", dataProviderClass = MT103ParserTestConstants.class,
            expectedExceptions = MTMessageParsingException.class,
            expectedExceptionsMessageRegExp = "Envelope Contents in Text Block is in invalid format")
    public void testInvalidField77TValue(String field77TString) throws Exception {

        Field77T.parse(field77TString);
    }
}
