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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.testng.annotations.DataProvider;

import java.util.List;

/**
 * Test constants for MT103ParserTests.
 */
public class MT103ParserTestConstants {

    public static final String VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK =
            "{1:F01BANKBEBBAXXX1234567890}" +
                    "{2:O1031130050901BANKBEBBAXXX12345678900509011311N}" +
                    "{3:{103:EBA}{121:180f1e65-90e0-44d5-a49a-92b55eb3025f}}" +
            "{4:\n%s\n-}";

    public static final String VALID_MT103_MESSAGE =
            String.format(VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK,
                    ":20:TXNREF1234567890\n" +
                    ":23B:CRED\n" +
                    ":32A:230523EUR100000,50\n" +
                    ":50K:/12345678\n" +
                    "JOHN DOE\n" +
                    "123, FAKE STREET\n" +
                    "FAKETOWN\n" +
                    ":52A:DEUTDEFFXXX\n" +
                    ":53B:/DE12345678901234567890\n" +
                    ":54A:CHASUS33XXX\n" +
                    ":56C:/IRVTUS3NXXX\n" +
                    ":57A:NORDDKKKXXX\n" +
                    ":59:/DK5000400440116243\n" +
                    "JANE SMITH\n" +
                    "789, REAL ROAD\n" +
                    "REALVILLE\n" +
                    ":70:PAYMENT FOR INVOICE 998877\n" +
                    ":71A:OUR\n" +
                    ":72:/ACC/RENT/MAY\n" +
                    "/INV/998877");

    private String getTextBlockFromList(List<String> fields) {
        return String.join("\n", fields);
    }

    @DataProvider(name = "validTextBlockDataProvider")
    Object[][] getValidTextBlockDataProvider() {
        return new Object[][] {
                {String.format(VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK, getTextBlockFromList(List.of(
                        ":20:TXNREF1234567890",
                        ":23B:CRED",
                        ":32A:230523EUR100000,50",
                        ":26T:K90",
                        ":33B:USD1000,00",
                        ":36:0,9236",
                        ":50K:/12345678\n" +
                                "JOHN DOE\n" +
                                "123, FAKE STREET\n" +
                                "FAKETOWN",
                        ":53B:/DE12345678901234567890",
                        ":54A:ABNANL2A",
                        ":55D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":56A:IRVTUS3N",
                        ":57D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":59:/DK5000400440116243\n" +
                                "JANE SMITH\n" +
                                "789, REAL ROAD\n" +
                                "REALVILLE",
                        ":71G:EUR5,50"
                )))},
                {String.format(VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK, getTextBlockFromList(List.of(
                        ":20:TXNREF1234567890",
                        ":13C:/CLSTIME/0100+0040",
                        ":13C:/SNDTIME/0100+0040",
                        ":23E:CHQB",
                        ":23E:TELI/3226553478",
                        ":50A:/293456-1254349-82\n" +
                                "VISTUS31",
                        ":51A:ABNANL2A",
                        ":52A:ABNANL2A",
                        ":53D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":54B:/DE12345678901234567890",
                        ":55A:ABNANL2A",
                        ":56C:/IRVTUS3NXXX",
                        ":57A:IRVTUS3N",
                        ":70:/TSU/00000089963-0820-01/ABC-15/256\n" +
                                "214,"

                )))},
                {String.format(VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK, getTextBlockFromList(List.of(
                        ":20:TXNREF1234567890",
                        ":50F:NIDN/DE/121231234342\n" +
                                "1/MANN GEORG\n" +
                                "3/DE/DUSSELDORF\n" +
                                "6/DE/ABC BANK/1234578293",
                        ":51A:ABNANL2A",
                        ":52D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":53A:ABNANL2A",
                        ":54D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":55B:/DE12345678901234567890",
                        ":56D:FINANZBANK AG\n" +
                                "EISENSTADT",
                        ":57B:/DE12345678901234567890",
                        ":59A:/293456-1254349-82\n" +
                                "VISTUS31"
                )))},
                {String.format(VALID_MT103_MESSAGE_WITHOUT_TEXT_BLOCK, getTextBlockFromList(List.of(
                        ":20:TXNREF1234567890",
                        ":50F:NIDN/DE/121231234342\n" +
                                "1/MANN GEORG\n" +
                                "3/DE/DUSSELDORF\n" +
                                "6/DE/ABC BANK/1234578293",
                        ":57C:/293456-1254349-82",
                        ":59F:/12345678\n" +
                                "1/DEPT OF PROMOTION OF SPICY FISH\n" +
                                "1/CENTER FOR INTERNATIONALISATION\n" +
                                "3/CN",
                        ":70:/INV/abc/SDF-96//1234-234///ROC/98I\n" +
                                "U87",
                        ":71A:BEN",
                        ":71F:EUR8,00",
                        ":71F:EUR5,00",
                        ":71F:EUR6,00",
                        ":71G:EUR5,50",
                        ":72:/INS/ABNANL2A",
                        ":77B:/ORDERRES/BE//MEILAAN 1, 9000 GENT",
                        ":77T:/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+ ...."
                )))}
        };
    }

    @DataProvider(name = "invalidField20DataProvider")
    Object[][] getInvalidField20DataProvider() {
        return new Object[][] {
                {""},
                {"This line is longer than 16 characters"},
                {"< is not allowed"},
        };
    }

    @DataProvider(name = "invalidField13CDataProvider")
    Object[][] getInvalidField13CDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"CLSTIME/0800+1000"},
                {"/CLSTIME0800+1000"},
                {"/CLSTIME/0800|1000"},
                {"/CLSTIME/ABCD+1000"},
                {"/CLSTIME/0800+GHIJ"},
                {"/CLSTIME/08+1000"},
                {"/CLSTIME/0800+10"},
        };
    }

    @DataProvider(name = "invalidField23BDataProvider")
    Object[][] getInvalidField23BDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"SPY"},
                {"SAPAY"},
                {"SA()"}
        };
    }

    @DataProvider(name = "invalidField23EDataProvider")
    Object[][] getInvalidField23EDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"teli"},
                {"TELI3226553478"},
                {"/3226553478"},
                {"TELI/This line is greater than 30 characters"}
        };
    }

    @DataProvider(name = "invalidField26TDataProvider")
    Object[][] getInvalidField26TDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"KK90"},
                {"k90"}
        };
    }

    @DataProvider(name = "invalidField32ADataProvider")
    Object[][] getInvalidField32ADataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"981209"},
                {"981209USD"},
                {"USD"},
                {"USD1000,00"},
                {"981209usd1000,00"},
                {"981209USD1000000000,00000000000"},
        };
    }

    @DataProvider(name = "invalidField33BDataProvider")
    Object[][] getInvalidField33BDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"USD"},
                {"1000,00"},
                {"usd1000,00"},
                {"USD1000000000,00000000000"},
        };
    }

    @DataProvider(name = "invalidField36DataProvider")
    Object[][] getInvalidField36DataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"USD"},
                {"usd1000,00"},
                {"1000000000,00000000000"},
        };
    }

    @DataProvider(name = "invalidField50ADataProvider")
    Object[][] getInvalidField50ADataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"293456-1254349-82\n" +
                        "VISTUS31"},
                {"/293456-1254349-82" +
                        "VISTUS31"},
                {"/293456-1254349-82\n" +
                        "VISTUS31" +
                        "VISTUS31"},
                {"/293456-1254349-82\n"},
                {"VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31"}
        };
    }

    @DataProvider(name = "invalidField50FDataProvider")
    Object[][] getInvalidField50FDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"/BE30001216371411"},
                {"/BE30001216371411\n" +
                        " 1/PHILIPS MARK\n" +
                        " 3/BE/ANTWERPEN\n" +
                        " 4/19720830\n" +
                        " 5/BE/BRUSSELS"},
                {"/BE30001216371411\n" +
                        "/PHILIPS MARK\n" +
                        "/BE/ANTWERPEN\n" +
                        "/19720830\n" +
                        "/BE/BRUSSELS"},
                {"/BE30001216371411\n" +
                        " 1PHILIPS MARK\n" +
                        " 3BE/ANTWERPEN\n" +
                        " 419720830\n" +
                        " 5BE/BRUSSELS"},
                {"/BE30001216371411BE30001216371411BE30001216371411BE30001216371411\n" +
                        "1/PHILIPS MARK\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
                {"/BE30001216371411B\n" +
                        "1/PHILIPS MARKPHILIPS MARKPHILIPS MARKPHILIPS MARKPHILIPS MARK\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
                {"/BE30001216371411B\n" +
                        "1/PHILIPS MARKPHILIPS\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
        };
    }

    @DataProvider(name = "invalidField50KDataProvider")
    Object[][] getInvalidField50KDataProvider() {
        return new Object[][] {
                {""},
                {"BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19\n" +
                        "RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"},
                {"/BE62510007547061 BE62510007547061BE62510007547061BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"},
                {"/BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19 RUE JOSEPH II, 19 RUE JOSEPH II, 19 RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"}
        };
    }

    @DataProvider(name = "invalidPartyIdentifierOptADataProvider")
    Object[][] getInvalidPartyIdentifierOptADataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"293456-1254349-82\n" +
                        "VISTUS31"},
                {"/293456-1254349-82" +
                        "VISTUS31"},
                {"/293456-1254349-82\n" +
                        "VISTUS31" +
                        "VISTUS31"},
                {"/293456-1254349-82\n"},
                {"VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31"}
        };
    }

    @DataProvider(name = "invalidPartyIdentifierOptBDataProvider")
    Object[][] getInvalidPartyIdentifierOptBDataProvider() {
        return new Object[][] {
                {"This line is greater than 35 characters"},
                {"/DE12345678901234567890\n" +
                        "ABNANL2A\n" +
                        "ABNANL2A\n"},
                {"/DE12345678901234567890\n" +
                        "This line is greater than 35 characters\n"},
        };
    }

    @DataProvider(name = "invalidPartyIdentifierOptCDataProvider")
    Object[][] getInvalidPartyIdentifierOptCDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"IRVTUS3NXXX"},
                {"/IRVTUS3NXXX IRVTUS3NXXX IRVTUS3NXXX IRVTUS3NXXX IRVTUS3NXXX"},
                {"/IRVTUS3NXXX\n" +
                        "/IRVTUS3NXXX"}
        };
    }

    @DataProvider(name = "invalidPartyIdentifierOptDDataProvider")
    Object[][] getInvalidPartyIdentifierOptDDataProvider() {
        return new Object[][] {
                {""},
                {"FINANZBANK AG\n" +
                        "EISENSTADT\n" +
                        "EISENSTADT\n" +
                        "EISENSTADT\n" +
                        "EISENSTADT"},
                {"/293456-1254349-82293456-1254349-82293456-1254349-82293456-1254349-82-256\n" +
                "FINANZBANK AG\n" +
                        "EISENSTADT"},
                {"/293456-1254349-82\n" +
                        "FINANZBANK AG\n" +
                        "FINANZBANK AG\n" +
                        "FINANZBANK AG\n" +
                        "FINANZBANK AG\n" +
                        "EISENSTADT"},
                {"/293456-1254349<82>\n" +
                        "FINANZBANK AG\n" +
                        "EISENSTADT"},
        };
    }

    @DataProvider(name = "invalidField59DataProvider")
    Object[][] getInvalidField59DataProvider() {
        return new Object[][] {
                {""},
                {"BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19\n" +
                        "RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"},
                {"/BE62510007547061 BE62510007547061BE62510007547061BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"},
                {"/BE62510007547061\n" +
                        "JOHANN WILLEMS\n" +
                        "RUE JOSEPH II, 19 RUE JOSEPH II, 19 RUE JOSEPH II, 19 RUE JOSEPH II, 19\n" +
                        "1040 BRUSSELS"}
        };
    }

    @DataProvider(name = "invalidField59ADataProvider")
    Object[][] getInvalidField59ADataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"293456-1254349-82\n" +
                        "VISTUS31"},
                {"/293456-1254349-82" +
                        "VISTUS31"},
                {"/293456-1254349-82\n" +
                        "VISTUS31" +
                        "VISTUS31"},
                {"/293456-1254349-82\n"},
                {"VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31VISTUS31"}
        };
    }

    @DataProvider(name = "invalidField59FDataProvider")
    Object[][] getInvalidField59FDataProvider() {
        return new Object[][] {
                {""},
                {"   "},
                {"/BE30001216371411"},
                {"/BE30001216371411\n" +
                        " 1/PHILIPS MARK\n" +
                        " 3/BE/ANTWERPEN\n" +
                        " 4/19720830\n" +
                        " 5/BE/BRUSSELS"},
                {"/BE30001216371411\n" +
                        "/PHILIPS MARK\n" +
                        "/BE/ANTWERPEN\n" +
                        "/19720830\n" +
                        "/BE/BRUSSELS"},
                {"/BE30001216371411\n" +
                        " 1PHILIPS MARK\n" +
                        " 3BE/ANTWERPEN\n" +
                        " 419720830\n" +
                        " 5BE/BRUSSELS"},
                {"/BE30001216371411BE30001216371411BE30001216371411BE30001216371411\n" +
                        "1/PHILIPS MARK\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
                {"/BE30001216371411B\n" +
                        "1/PHILIPS MARKPHILIPS MARKPHILIPS MARKPHILIPS MARKPHILIPS MARK\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
                {"/BE30001216371411B\n" +
                        "1/PHILIPS MARKPHILIPS\n" +
                        "3/BE/ANTWERPEN\n" +
                        "4/19720830\n" +
                        "4/19720830\n" +
                        "5/BE/BRUSSELS"},
        };
    }

    @DataProvider(name = "invalidField70DataProvider")
    Object[][] getInvalidField70DataProvider() {
        return new Object[][] {
                {""},
                {"This line is longer than 35 characters."},
                {"/INV/abc/SDF-96//1234-234///ROC/98I\n" +
                "/INV/abc/SDF-96//1234-234///ROC/98I\n" +
                        "U87\n" +
                        "U87\n" +
                        "U87"}
        };
    }

    @DataProvider(name = "invalidField71ADataProvider")
    Object[][] getInvalidField71ADataProvider() {
        return new Object[][] {
                {""},
                {"BE"},
                {"ABCD"},
                {"123"}
        };
    }

    @DataProvider(name = "invalidField71FDataProvider")
    Object[][] getInvalidField71FDataProvider() {
        return new Object[][] {
                {""},
                {"      "},
                {"USD"},
                {"100,00"},
                {"USDI100,00"},
                {"USD100000000,000000000"}
        };
    }

    @DataProvider(name = "invalidField71GDataProvider")
    Object[][] getInvalidField71GDataProvider() {
        return new Object[][] {
                {""},
                {"      "},
                {"USD"},
                {"100,00"},
                {"USDI100,00"},
                {"USD100000000,000000000"}
        };
    }

    @DataProvider(name = "invalidField72DataProvider")
    Object[][] getInvalidField72DataProvider() {
        return new Object[][] {
                {""},
                {"This line is length is greater then 35 characters"},
                {"This\n" +
                    "is\n" +
                    "more\n" +
                    "than\n" +
                    "allowed\n" +
                    "line\n" +
                    "count\n"},

        };
    }

    @DataProvider(name = "invalidField77BDataProvider")
    Object[][] getInvalidField77BDataProvider() {
        return new Object[][] {
                {""},
                {"This line is length is greater then 35 characters"},
                {"This is\n" +
                    "more than\n" +
                    "allowed\n" +
                    "line count\n"},

        };
    }

    @DataProvider(name = "invalidField77TDataProvider")
    Object[][] getInvalidField77TDataProvider() {
        return new Object[][] {
                {""},
                {"This is more than\n" +
                    "allowed line count\n"},

        };
    }
}
