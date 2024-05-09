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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.testng.annotations.DataProvider;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT940TextBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field21;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field25;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field28;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field60;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field61;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field62;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field64;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field65;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field86;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test constants for MTParser.
 */
public class MT940ParserTestConstants {
    // Params contains all the field except Field60 and Field60 related Field86.
    // List of Field60 and corresponding Field86 will in the stmtParams list parameter
    // Ex - stmtParams -> ['61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n86:EREF/GSGWGDNCTAHQM8',
    //                     '61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8, ...]
    public static String getMT940TextBlockText(Map<String, String> params, List<String> stmtParams) {
        String stmtLines = String.join("", stmtParams);

        if (stmtParams.isEmpty()) {
            stmtLines = ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n";
        }

        return "\n" + (params.getOrDefault("Field20", ":20:258158850\n"))
                + (params.getOrDefault("Field21", ":21:258158850\n"))
                + (params.getOrDefault("Field25", ":25:DD01100056869\n"))
                + (params.getOrDefault("Field25P", ""))
                + (params.getOrDefault("Field28", ":28C:1/1\n"))
                + (params.getOrDefault("Field60", ":60F:D230930USD843686,20\n")) + stmtLines
                + (params.getOrDefault("Field62", ":62F:D230930USD846665,15\n"))
                + (params.getOrDefault("Field64", ":64:C231002USD334432401,27\n"))
                + (params.getOrDefault("Field65", ":65:C231002USD334432401,27\n"))
                + (params.getOrDefault("Field86",
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003")) + "\n";
    }

    // Params contains all the field except Field60 and Field60 related Field86.
    // List of Field60 and corresponding Field86 will in the stmtParams parameter
    // StmtParams = StmtLines -> [Map<String, String>, Map<String, String>, ....];
    //              AddInfo ->   [Map<String, String>, Map<String, String>,     ....];
    // StmtLines key contains list of Field61 objects and AddInfo key contains list of Field86 object
    // These two list should have same length
    public static MT940TextBlock getMT940TextBlock(Map<String, String> params, List<Map<String, String>> stmtParams) {
        MT940TextBlock textBlock = new MT940TextBlock();

        if (params.containsKey("Field20")) {
            Field20 field20 = new Field20();
            field20.setOption(MTParserConstants.FIELD_OPTION_NO_LETTER);
            field20.setValue(params.get("Field20"));

            textBlock.setTransactionReferenceNumber(field20);
        }

        if (params.containsKey("Field21")) {
            Field21 field21 = new Field21();
            field21.setOption(MTParserConstants.FIELD_OPTION_NO_LETTER);
            field21.setValue(params.get("Field21"));

            textBlock.setRelatedReference(field21);
        }

        if (params.containsKey("Field25Account")) {
            Field25 field25 = new Field25();
            field25.setOption(MTParserConstants.FIELD_OPTION_NO_LETTER);
            field25.setAccount(params.get("Field25Account"));

            textBlock.setAccountIdentification(field25);
        }

        if (params.containsKey("Field25PAccount")) {
            if (textBlock.getAccountIdentification() == null) {
                Field25 field25 = new Field25();
                field25.setOption(MTParserConstants.FIELD_OPTION_P);
                field25.setAccount(params.get("Field25PAccount"));

                textBlock.setAccountIdentification(field25);
            } else {
                Field25 field25 = textBlock.getAccountIdentification();
                field25.setAccount(params.get("Field25PAccount"));

                textBlock.setAccountIdentification(field25);
            }
        }

        if (params.containsKey("Field25PIdentifierCode")) {
            if (textBlock.getAccountIdentification() == null) {
                Field25 field25 = new Field25();
                field25.setOption(MTParserConstants.FIELD_OPTION_P);
                field25.setIdentifierCode(params.get("Field25PIdentifierCode"));

                textBlock.setAccountIdentification(field25);
            } else {
                Field25 field25 = textBlock.getAccountIdentification();
                field25.setIdentifierCode(params.get("Field25PIdentifierCode"));

                textBlock.setAccountIdentification(field25);
            }
        }

        if (params.containsKey("Field28CStmtNumber")) {
            if (textBlock.getStatementSequenceNumber() == null) {
                Field28 field28 = new Field28();
                field28.setOption(MTParserConstants.FIELD_OPTION_C);
                field28.setStatementNumber(params.get("Field28CStmtNumber"));

                textBlock.setStatementSequenceNumber(field28);
            } else {
                Field28 field28 = textBlock.getStatementSequenceNumber();
                field28.setStatementNumber(params.get("Field28CStmtNumber"));

                textBlock.setStatementSequenceNumber(field28);
            }
        }

        if (params.containsKey("Field28CSeqNumber")) {
            if (textBlock.getStatementSequenceNumber() == null) {
                Field28 field28 = new Field28();
                field28.setOption(MTParserConstants.FIELD_OPTION_C);
                field28.setSequenceNumber(params.get("Field28CSeqNumber"));

                textBlock.setStatementSequenceNumber(field28);
            } else {
                Field28 field28 = textBlock.getStatementSequenceNumber();
                field28.setSequenceNumber(params.get("Field28CSeqNumber"));

                textBlock.setStatementSequenceNumber(field28);
            }
        }

        // Opening balance - Field 60F
        if (params.containsKey("Field60FDCMark") || params.containsKey("Field60MDCMark")) {
            Field60 field60 = textBlock.getOpeningBal() != null ? textBlock.getOpeningBal() : new Field60();

            field60.setOption(params.containsKey("Field60FDCMark") ?
                        MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);

            field60.setDcMark(params.containsKey("Field60FDCMark") ?
                    params.get("Field60FDCMark") : params.get("Field60MDCMark"));
            textBlock.setOpeningBal(field60);
        }

        if (params.containsKey("Field60FDate") || params.containsKey("Field60MDate")) {
            Field60 field60 = textBlock.getOpeningBal() != null ? textBlock.getOpeningBal() : new Field60();

            field60.setOption(params.containsKey("Field60FDate") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);

            field60.setDate(params.containsKey("Field60FDate") ?
                    params.get("Field60FDate") : params.get("Field60MDate"));
            textBlock.setOpeningBal(field60);
        }

        if (params.containsKey("Field60FCurrency") || params.containsKey("Field60MCurrency")) {
            Field60 field60 = textBlock.getOpeningBal() != null ? textBlock.getOpeningBal() : new Field60();

            field60.setOption(params.containsKey("Field60FCurrency") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);

            field60.setCurrency(params.containsKey("Field60FCurrency") ?
                    params.get("Field60FCurrency") : params.get("Field60MCurrency"));
            textBlock.setOpeningBal(field60);
        }

        if (params.containsKey("Field60FAmount") || params.containsKey("Field60MAmount")) {
            Field60 field60 = textBlock.getOpeningBal() != null ? textBlock.getOpeningBal() : new Field60();

            field60.setOption(params.containsKey("Field60FAmount") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);

            field60.setAmount(params.containsKey("Field60FAmount") ?
                    params.get("Field60FAmount") : params.get("Field60MAmount"));
            textBlock.setOpeningBal(field60);
        }

        List<Map<String, Object>> statementsSet = new ArrayList<>();

        for (Map<String, String> statement : stmtParams) {
            // Adding statement lines
            Field61 field61 = new Field61();

            if (statement.containsKey("Field61ValueDate")) {
                field61.setValueDate(statement.get("Field61ValueDate"));
            }

            if (statement.containsKey("Field61EntryDate")) {
                field61.setEntryDate(statement.get("Field61EntryDate"));
            }

            if (statement.containsKey("Field61DCMark")) {
                field61.setDcMark(statement.get("Field61DCMark"));
            }

            if (statement.containsKey("Field61FundsCode")) {
                field61.setFundsCode(statement.get("Field61FundsCode"));
            }

            if (statement.containsKey("Field61Amount")) {
                field61.setAmount(statement.get("Field61Amount"));
            }

            if (statement.containsKey("Field61TransactionType")) {
                field61.setTransactionType(statement.get("Field61TransactionType"));
            }

            if (statement.containsKey("Field61IdentificationCode")) {
                field61.setIdentificationCode(statement.get("Field61IdentificationCode"));
            }

            if (statement.containsKey("Field61RefToAccountOwner")) {
                field61.setRefToAccountOwner(statement.get("Field61RefToAccountOwner"));
            }

            if (statement.containsKey("Field61RefToAccountServicingInstitution")) {
                field61.setRefToAccountServicingInstitution(
                        statement.get("Field61RefToAccountServicingInstitution"));
            }

            if (statement.containsKey("Field61SupplementaryDetails")) {
                field61.setSupplementaryDetails(statement.get("Field61SupplementaryDetails"));
            }

            // Adding additional info
            if (statement.containsKey("Field86")) {
                Field86 field86 = new Field86();
                field86.setOption(MTParserConstants.FIELD_OPTION_NO_LETTER);
                field86.setValue(statement.get("Field86"));

                statementsSet.add(Map.of("Field61", field61, "Field86", field86));
            } else {
                statementsSet.add(Map.of("Field61", field61));
            }

        }

        textBlock.setStatementLines(statementsSet);

        // Closing balance - Field 62F
        if (params.containsKey("Field62FDCMark") || params.containsKey("Field62MDCMark")) {
            Field62 field62 = textBlock.getClosingBal() != null ? textBlock.getClosingBal() : new Field62();

            field62.setOption(params.containsKey("Field62FDCMark") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);
            field62.setDcMark(params.containsKey("Field62FDCMark") ?
                    params.get("Field62FDCMark") : params.get("Field62MDCMark"));

            textBlock.setClosingBal(field62);
        }

        if (params.containsKey("Field62FDate") || params.containsKey("Field62MDate")) {
            Field62 field62 = textBlock.getClosingBal() != null ? textBlock.getClosingBal() : new Field62();

            field62.setOption(params.containsKey("Field62FDate") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);
            field62.setDate(params.containsKey("Field62FDate") ?
                    params.get("Field62FDate") : params.get("Field62MDate"));

            textBlock.setClosingBal(field62);
        }

        if (params.containsKey("Field62FCurrency") || params.containsKey("Field62MCurrency")) {
            Field62 field62 = textBlock.getClosingBal() != null ? textBlock.getClosingBal() : new Field62();

            field62.setOption(params.containsKey("Field62FCurrency") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);
            field62.setCurrency(params.containsKey("Field62FCurrency") ?
                    params.get("Field62FCurrency") : params.get("Field62MCurrency"));

            textBlock.setClosingBal(field62);
        }

        if (params.containsKey("Field62FAmount") || params.containsKey("Field62MAmount")) {
            Field62 field62 = textBlock.getClosingBal() != null ? textBlock.getClosingBal() : new Field62();

            field62.setOption(params.containsKey("Field62FAmount") ?
                    MTParserConstants.FIELD_OPTION_F : MTParserConstants.FIELD_OPTION_M);
            field62.setAmount(params.containsKey("Field62FAmount") ?
                    params.get("Field62FAmount") : params.get("Field62MAmount"));

            textBlock.setClosingBal(field62);
        }

        // Closing available balance - Field 64
        if (params.containsKey("Field64DCMark")) {
            if (textBlock.getClosingAvlBalance() == null) {
                Field64 field64 = new Field64();
                field64.setDcMark(params.get("Field64DCMark"));

                textBlock.setClosingAvlBalance(field64);
            } else {
                Field64 field64 = textBlock.getClosingAvlBalance();
                field64.setDcMark(params.get("Field64DCMark"));

                textBlock.setClosingAvlBalance(field64);
            }
        }

        if (params.containsKey("Field64Date")) {
            if (textBlock.getClosingAvlBalance() == null) {
                Field64 field64 = new Field64();
                field64.setDate(params.get("Field64Date"));

                textBlock.setClosingAvlBalance(field64);
            } else {
                Field64 field64 = textBlock.getClosingAvlBalance();
                field64.setDate(params.get("Field64Date"));

                textBlock.setClosingAvlBalance(field64);
            }
        }

        if (params.containsKey("Field64Currency")) {
            if (textBlock.getClosingAvlBalance() == null) {
                Field64 field64 = new Field64();
                field64.setCurrency(params.get("Field64Currency"));

                textBlock.setClosingAvlBalance(field64);
            } else {
                Field64 field64 = textBlock.getClosingAvlBalance();
                field64.setCurrency(params.get("Field64Currency"));

                textBlock.setClosingAvlBalance(field64);
            }
        }

        if (params.containsKey("Field64Amount")) {
            if (textBlock.getClosingAvlBalance() == null) {
                Field64 field64 = new Field64();
                field64.setAmount(params.get("Field64Amount"));

                textBlock.setClosingAvlBalance(field64);
            } else {
                Field64 field64 = textBlock.getClosingAvlBalance();
                field64.setAmount(params.get("Field64Amount"));

                textBlock.setClosingAvlBalance(field64);
            }
        }

        // Forward available balance - Field 65
        if (params.containsKey("Field65DCMark")) {
            if (textBlock.getForwardAvlBalance() == null) {
                Field65 field65 = new Field65();
                field65.setDcMark(params.get("Field65DCMark"));

                textBlock.setForwardAvlBalance(field65);
            } else {
                Field65 field65 = textBlock.getForwardAvlBalance();
                field65.setDcMark(params.get("Field65DCMark"));

                textBlock.setForwardAvlBalance(field65);
            }
        }

        if (params.containsKey("Field65Date")) {
            if (textBlock.getForwardAvlBalance() == null) {
                Field65 field65 = new Field65();
                field65.setDate(params.get("Field65Date"));

                textBlock.setForwardAvlBalance(field65);
            } else {
                Field65 field65 = textBlock.getForwardAvlBalance();
                field65.setDate(params.get("Field65Date"));

                textBlock.setForwardAvlBalance(field65);
            }
        }

        if (params.containsKey("Field65Currency")) {
            if (textBlock.getForwardAvlBalance() == null) {
                Field65 field65 = new Field65();
                field65.setCurrency(params.get("Field65Currency"));

                textBlock.setForwardAvlBalance(field65);
            } else {
                Field65 field65 = textBlock.getForwardAvlBalance();
                field65.setCurrency(params.get("Field65Currency"));

                textBlock.setForwardAvlBalance(field65);
            }
        }

        if (params.containsKey("Field65Amount")) {
            if (textBlock.getForwardAvlBalance() == null) {
                Field65 field65 = new Field65();
                field65.setAmount(params.get("Field65Amount"));

                textBlock.setForwardAvlBalance(field65);
            } else {
                Field65 field65 = textBlock.getForwardAvlBalance();
                field65.setAmount(params.get("Field65Amount"));

                textBlock.setForwardAvlBalance(field65);
            }
        }

        if (params.containsKey("Field86")) {
            Field86 field86 = new Field86();
            field86.setOption(MTParserConstants.FIELD_OPTION_NO_LETTER);
            field86.setValue(params.get("Field86"));

            textBlock.setInfoToAccountOwner(field86);
        }

        return textBlock;
    }

    @DataProvider(name = "parseMT940TextBlock")
    Object[][] parseMT940TextBlock() {
        return new Object[][] {
                {
                    getMT940TextBlockText(Map.of("Field20", ":20:258158850\n",
                        "Field21", ":21:258158850\n",
                        "Field25", ":25:DD01100056869\n",
                        "Field28", ":28C:1/1\n",
                        "Field60", ":60F:D230930USD843686,20\n",
                        "Field62", ":62F:D230930USD846665,15\n",
                        "Field64", ":64:C231002USD334432401,27\n",
                        "Field65", ":65:C231002USD334432401,27\n"),
                        List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                            ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n",
                            ":61:2310011001DD10,00ACHPNONREF\n",
                            ":61:2310011001CD10,00ASHP20230928LTERMID2//ADDITIONAL INFO\nADDITIONAL INFO\n")),
                    getMT940TextBlock(Map.ofEntries(Map.entry("Field20", "258158850"),
                        Map.entry("Field21", "258158850"), Map.entry("Field25Account", "DD01100056869"),
                        Map.entry("Field28CStmtNumber", "1"), Map.entry("Field28CSeqNumber", "1"),
                        Map.entry("Field60FDCMark", "D"), Map.entry("Field60FDate", "230930"),
                        Map.entry("Field60FCurrency", "USD"), Map.entry("Field60FAmount", "843686,20"),
                        Map.entry("Field62FDCMark", "D"), Map.entry("Field62FDate", "230930"),
                        Map.entry("Field62FCurrency", "USD"), Map.entry("Field62FAmount", "846665,15"),
                        Map.entry("Field64DCMark", "C"), Map.entry("Field64Date", "231002"),
                        Map.entry("Field64Currency", "USD"), Map.entry("Field64Amount", "334432401,27"),
                        Map.entry("Field65DCMark", "C"), Map.entry("Field65Date", "231002"),
                        Map.entry("Field65Currency", "USD"), Map.entry("Field65Amount", "334432401,27"),
                        Map.entry("Field86", "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003")),
                        List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                        "Field86", "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003"),
                        Map.of("Field61ValueDate", "231001", "Field61EntryDate", "1001",
                        "Field61DCMark", "D", "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "NONREF"),
                        Map.ofEntries(Map.entry("Field61ValueDate", "231001"), Map.entry("Field61EntryDate", "1001"),
                        Map.entry("Field61DCMark", "D"), Map.entry("Field61FundsCode", "D"),
                        Map.entry("Field61Amount", "10,00"), Map.entry("Field61TransactionType", "A"),
                        Map.entry("Field61IdentificationCode", "CHP"),
                        Map.entry("Field61RefToAccountOwner", "20230928LTERMID2000003"),
                        Map.entry("Field61RefToAccountServicingInstitution", "ADDITIONAL INFO"),
                        Map.entry("Field61SupplementaryDetails", "ADDITIONAL INFO"))))
                },
        };
    }

    @DataProvider(name = "validTransactionReferenceNumber")
    Object[][] parseValidTransactionReferenceNumber() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field20", ":20:25\n"), List.of()),
                        getMT940TextBlock(Map.of("Field20", "25"), List.of()) },
                { getMT940TextBlockText(Map.of("Field20", ":20:2581588502581588\n"), List.of()),
                        getMT940TextBlock(Map.of("Field20", "2581588502581588"), List.of()) },
        };
    }

    @DataProvider(name = "invalidTransactionReferenceNumberOpt")
    Object[][] parseInvalidTransactionReferenceNumberOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field20", ":20A:258158850\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidTransactionReferenceNumber")
    Object[][] parseInvalidTransactionReferenceNumber() {
        return new Object[][]{
            { getMT940TextBlockText(Map.of("Field20", ":20:\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field20", ":20:<<<1234>>>>\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field20", ":20:258158850258158850258158850258158850\n"), List.of()) }
        };
    }

    @DataProvider(name = "validRelatedReference")
    Object[][] parseValidRelatedReference() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field21", ":21:25\n"), List.of()),
                        getMT940TextBlock(Map.of("Field21", "25"), List.of()) },
                { getMT940TextBlockText(Map.of("Field21", ":21:2581588502581588\n"), List.of()),
                        getMT940TextBlock(Map.of("Field21", "2581588502581588"), List.of()) },
        };
    }

    @DataProvider(name = "invalidRelatedReferenceNumberOpt")
    Object[][] parseInvalidRelatedReferenceNumberOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field21", ":21A:258158850\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidRelatedReference")
    Object[][] parseInvalidRelatedReference() {
        return new Object[][]{
            { getMT940TextBlockText(Map.of("Field21", ":21:\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field21", ":21:<<<1234>>>>\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field21", ":21:258158850258158850258158850258158850\n"), List.of()) }
        };
    }

    @DataProvider(name = "validAccountIdentification")
    Object[][] parseValidAccIdentification() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25", ":25:DD\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25Account", "DD"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:DD01100056869\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25Account", "DD01100056869"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:DD01100056869DD01100056869DD0110005\n")
                        , List.of()),
                        getMT940TextBlock(Map.of("Field25Account", "DD01100056869DD01100056869DD0110005"), List.of()) }
        };
    }

    @DataProvider(name = "invalidAccIdentificationOpt")
    Object[][] parseInvalidAccIdentificationOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25", ":25B:DD01100056869\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidAccountIdentification")
    Object[][] parseInvalidAccIdentification() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25", ":25:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:<<<1234>>>>\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:DD01100056869DD01100056869DD01100056869\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validAccountIdentificationOptP")
    Object[][] parseValidAccIdentificationOptP() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD\nGSCRUS30XXX\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25PAccount", "DD",
                                "Field25PIdentifierCode", "GSCRUS30XXX"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869\nGSCRUS30XXX\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25PAccount", "DD01100056869",
                                "Field25PIdentifierCode", "GSCRUS30XXX"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869DD01100056869DD0110005" +
                                "\nGSCRUS30XXX\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25PAccount", "DD01100056869DD01100056869DD0110005",
                                "Field25PIdentifierCode", "GSCRUS30XXX"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869\nGSCRUS30\n"), List.of()),
                        getMT940TextBlock(Map.of("Field25PAccount", "DD01100056869",
                                "Field25PIdentifierCode", "GSCRUS30"), List.of()) },
        };
    }

    @DataProvider(name = "invalidAccountIdentificationOptP")
    Object[][] parseInvalidAccIdentificationOptP() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25P", ":25P:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:<<<1234>>>>\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869DD01100056869DD01100056869\n"),
                        List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869\n\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869\n12345\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25P", ":25P:DD01100056869\nDD01100056869\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validStmtSeqNumber")
    Object[][] parseValidStmtSeqNumber() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field28", ":28C:12\n"), List.of()),
                        getMT940TextBlock(Map.of("Field28CStmtNumber", "12"), List.of())},
                { getMT940TextBlockText(Map.of("Field28", ":28C:12510\n"), List.of()),
                        getMT940TextBlock(Map.of("Field28CStmtNumber", "12510"), List.of())},
                { getMT940TextBlockText(Map.of("Field28", ":28C:12/12\n"), List.of()),
                        getMT940TextBlock(Map.of("Field28CStmtNumber", "12", "Field28CSeqNumber", "12"),
                                List.of())},
                { getMT940TextBlockText(Map.of("Field28", ":28C:12/12510\n"), List.of()),
                        getMT940TextBlock(Map.of("Field28CStmtNumber", "12", "Field28CSeqNumber", "12510"),
                                List.of())},
                { getMT940TextBlockText(Map.of("Field28", ":28C:12891/12510\n"), List.of()),
                        getMT940TextBlock(Map.of("Field28CStmtNumber", "12891", "Field28CSeqNumber", "12510"),
                                List.of())},
        };
    }

    @DataProvider(name = "invalidStmtSeqNumberOpt")
    Object[][] parseInvalidStmtSeqNumberOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field28", ":28T:12/12\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidStmtSeqNumber")
    Object[][] parseInvalidStmtSeqNumber() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field28", ":28C:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:ABC\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:1204056\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:12/\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:12*12\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:12/ABC\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28", ":28C:12/1204056\n"), List.of()) },
        };
    }

    @DataProvider(name = "validOpeningBalanceOptF")
    Object[][] parseValidOpeningBalanceOptF() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USD843686,20\n"), List.of()),
                getMT940TextBlock(Map.of("Field60FDCMark", "D", "Field60FDate", "230930",
                          "Field60FCurrency", "USD", "Field60FAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60F:C230930USD843686,20\n"), List.of()),
                getMT940TextBlock(Map.of("Field60FDCMark", "C", "Field60FDate", "230930",
                    "Field60FCurrency", "USD", "Field60FAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USD8\n"), List.of()),
                getMT940TextBlock(Map.of("Field60FDCMark", "D", "Field60FDate", "230930",
                        "Field60FCurrency", "USD", "Field60FAmount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USD843686843686,20\n"), List.of()),
                getMT940TextBlock(Map.of("Field60FDCMark", "D", "Field60FDate", "230930",
                        "Field60FCurrency", "USD", "Field60FAmount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidOpeningBalanceOpt")
    Object[][] parseInvalidOpeningBalanceOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field60", ":60P:D230930USD843686,20\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidOpeningBalanceOptF")
    Object[][] parseInvalidOpeningBalanceOptF() {
        return new Object[][]{
            { getMT940TextBlockText(Map.of("Field60", ":60F:\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:E230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:3230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D001USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D23423423USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:DABCDEFUSD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930US843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USDEF843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930123843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USD\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60", ":60F:D230930USD843686843686843686843686,20\n"),
                    List.of()) }
        };
    }

    @DataProvider(name = "validOpeningBalanceOptM")
    Object[][] parseValidOpeningBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field60MDCMark", "D", "Field60MDate", "230930",
                                "Field60MCurrency", "USD", "Field60MAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60M:C230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field60MDCMark", "C", "Field60MDate", "230930",
                                "Field60MCurrency", "USD", "Field60MAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USD8\n"), List.of()),
                        getMT940TextBlock(Map.of("Field60MDCMark", "D", "Field60MDate", "230930",
                                "Field60MCurrency", "USD", "Field60MAmount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USD843686843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field60MDCMark", "D", "Field60MDate", "230930",
                                "Field60MCurrency", "USD", "Field60MAmount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidOpeningBalanceOptM")
    Object[][] parseInvalidOpeningBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field60", ":60M:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60", ":60M:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validStmtLine")
    Object[][] parseValidStatementLine() {
        return new Object[][]{
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                 getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                         "Field61EntryDate", "1001", "Field61DCMark", "RC",
                         "Field61FundsCode", "D", "Field61Amount", "10,00",
                         "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                         "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                         "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                         "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:231001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61DCMark", "RC", "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RC10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD1ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "1",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD105348902112,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "105348902112,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPG" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "G",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8GS" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8GS",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8GS" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                        "Field61EntryDate", "1001", "Field61DCMark", "RC",
                        "Field61FundsCode", "D", "Field61Amount", "10,00",
                        "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                        "Field61RefToAccountOwner", "GSGWGDNCTAHQM8GS",
                        "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                        "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//AD\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                                "Field61EntryDate", "1001", "Field61DCMark", "RC",
                                "Field61FundsCode", "D", "Field61Amount", "10,00",
                                "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                                "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                                "Field61RefToAccountServicingInstitution", "AD",
                                "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFOA\nEREF/GSGWGDNCTAHQM8\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                                "Field61EntryDate", "1001", "Field61DCMark", "RC",
                                "Field61FundsCode", "D", "Field61Amount", "10,00",
                                "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                                "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                                "Field61RefToAccountServicingInstitution", "ADDITIONAL INFOA",
                                "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                                "Field61EntryDate", "1001", "Field61DCMark", "RC",
                                "Field61FundsCode", "D", "Field61Amount", "10,00",
                                "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                                "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                                "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                                "Field61SupplementaryDetails", "EREF")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                        "//ADDITIONAL INFO\nEREF/GSGWGDNCTAHQM8/EREF/GSGWGDNCT\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                                "Field61EntryDate", "1001", "Field61DCMark", "RC",
                                "Field61FundsCode", "D", "Field61Amount", "10,00",
                                "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                                "Field61RefToAccountOwner", "GSGWGDNCTAHQM8",
                                "Field61RefToAccountServicingInstitution", "ADDITIONAL INFO",
                                "Field61SupplementaryDetails", "EREF/GSGWGDNCTAHQM8/EREF/GSGWGDNCT")))},
        };
    }



    @DataProvider(name = "invalidStmtLine")
    Object[][] parseInvalidStatementLine() {
        return new Object[][] {
           // Value date
           // { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           { getMT940TextBlockText(Map.of(), List.of(":61:1001RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           { getMT940TextBlockText(Map.of(), List.of(":61:2311001RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           { getMT940TextBlockText(Map.of(), List.of(":61:2310011231001RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           { getMT940TextBlockText(Map.of(), List.of(":61:ABCDEF1001RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           // Entry date
            { getMT940TextBlockText(Map.of(), List.of(":61:23100112RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:231001100123RCD10,00ACHPGSGWGDNCTAHQM8\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:231001DEFTRCD10,00ACHPGSGWGDNCTAHQM8\n")) },
           // Debit/Credit indicator
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001E10,00ACHPGSGWGDNCTAHQM8\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001EFGD10,00ACHPGSGWGDNCTAHQM8\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:231001100112D10,00ACHPGSGWGDNCTAHQM8\n")) },
           // Funds code
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCDE10,00ACHPGSGWGDNCTAHQM8\n")) },
           // Amount
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCDACHPGSGWGDNCTAHQM8\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD843686843686843686843686,20" +
                    "ACHPGSGWGDNCTAHQM8\n")) },
            // Transaction type
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00CHP(GSGWGDNCTAHQM8)\n")) },
            // Identification code
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00A(GSGWGDNCTAHQM8)\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACH(GSGWGDNCTAHQM8)\n")) },
            // Reference to account owner
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHP\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHP" +
                    "GSGWGDNCTAHQM8GSGWGDNCTAHQM8GSGWGDNCTAHQM8\n")) },
            // Reference to account servicing institution
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8" +
                    "//ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO\n")) },
            // Supplementry details
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n\n")) },
            { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                    "ADDITIONAL INFORMATION ADDITIONAL INFORMATION ADDITIONAL INFORMATION ADDITIONAL INFORMATION" +
                    " ADDITIONAL INFORMATION\n")) },
        };
    }

    @DataProvider(name = "validStmtLineInfoAccountOwner")
    Object[][] parseValidStatementLineInfoAccountOwner() {
        return new Object[][]{
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                        ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                            "Field61EntryDate", "1001", "Field61DCMark", "RC",
                            "Field61FundsCode", "D", "Field61Amount", "10,00",
                            "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                            "Field61RefToAccountOwner", "GSGWGDNCTAHQM8", "Field86",
                            "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003")))},
                {getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                        ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n")),
                        getMT940TextBlock(Map.of(), List.of(Map.of("Field61ValueDate", "231001",
                                "Field61EntryDate", "1001", "Field61DCMark", "RC",
                                "Field61FundsCode", "D", "Field61Amount", "10,00",
                                "Field61TransactionType", "A", "Field61IdentificationCode", "CHP",
                                "Field61RefToAccountOwner", "GSGWGDNCTAHQM8", "Field86",
                                "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                    "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                    "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                    "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00\n" +
                                    "EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003CTBA000CTA00")))},
        };
    }

    @DataProvider(name = "invalidStmtLineInfoToAccOwner")
    Object[][] parseInvalidStmtLineInfoToAccOwner() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                        ":86:\n")) },
                { getMT940TextBlockText(Map.of(), List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                        ":86:ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL " +
                        "INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO " +
                        "INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO " +
                        "INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO " +
                        "INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO " +
                        "INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n")) },
        };
    }

    @DataProvider(name = "validClosingBalanceOptF")
    Object[][] parseValidClosingBalanceOptF() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62FDCMark", "D", "Field62FDate", "230930",
                                "Field62FCurrency", "USD", "Field62FAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62F:C230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62FDCMark", "C", "Field62FDate", "230930",
                                "Field62FCurrency", "USD", "Field62FAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USD8\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62FDCMark", "D", "Field62FDate", "230930",
                                "Field62FCurrency", "USD", "Field62FAmount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USD843686843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62FDCMark", "D", "Field62FDate", "230930",
                                "Field62FCurrency", "USD", "Field62FAmount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidClosingBalanceOpt")
    Object[][] parseInvalidClosingBalanceOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62", ":62P:D230930USD843686,20\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidClosingBalanceOptF")
    Object[][] parseInvalidClosingBalanceOptF() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62", ":62F:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62F:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validClosingBalanceOptM")
    Object[][] parseValidClosingBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62MDCMark", "D", "Field62MDate", "230930",
                                "Field62MCurrency", "USD", "Field62MAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62M:C230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62MDCMark", "C", "Field62MDate", "230930",
                                "Field62MCurrency", "USD", "Field62MAmount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USD8\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62MDCMark", "D", "Field62MDate", "230930",
                                "Field62MCurrency", "USD", "Field62MAmount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USD843686843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field62MDCMark", "D", "Field62MDate", "230930",
                                "Field62MCurrency", "USD", "Field62MAmount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidClosingBalanceOptM")
    Object[][] parseInvalidClosingBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62", ":62M:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62", ":62M:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validClosingAvlBalance")
    Object[][] parseValidClosingAvlBalance() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field64DCMark", "D", "Field64Date", "230930",
                                "Field64Currency", "USD", "Field64Amount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field64", ":64:C230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field64DCMark", "C", "Field64Date", "230930",
                                "Field64Currency", "USD", "Field64Amount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USD8\n"), List.of()),
                        getMT940TextBlock(Map.of("Field64DCMark", "D", "Field64Date", "230930",
                                "Field64Currency", "USD", "Field64Amount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USD843686843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field64DCMark", "D", "Field64Date", "230930",
                                "Field64Currency", "USD", "Field64Amount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidClosingAvlBalance")
    Object[][] parseInvalidClosingAvlBalance() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field64", ":64:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field64", ":64:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validForwardAvlBalance")
    Object[][] parseValidForwardAvlBalance() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field65DCMark", "D", "Field65Date", "230930",
                                "Field65Currency", "USD", "Field65Amount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field65", ":65:C230930USD843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field65DCMark", "C", "Field65Date", "230930",
                                "Field65Currency", "USD", "Field65Amount", "843686,20"), List.of())},
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USD8\n"), List.of()),
                        getMT940TextBlock(Map.of("Field65DCMark", "D", "Field65Date", "230930",
                                "Field65Currency", "USD", "Field65Amount", "8"), List.of())},
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USD843686843686,20\n"), List.of()),
                        getMT940TextBlock(Map.of("Field65DCMark", "D", "Field65Date", "230930",
                                "Field65Currency", "USD", "Field65Amount", "843686843686,20"), List.of())},
        };
    }

    @DataProvider(name = "invalidForwardAvlBalance")
    Object[][] parseInvalidForwardAvlBalance() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field65", ":65:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field65", ":65:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "validInfoToAccOwner")
    Object[][] parseValidInfoToAccOwner() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field86", ":86:ADDITIONAL INFO\n"), List.of()),
                        getMT940TextBlock(Map.of("Field86", "ADDITIONAL INFO"), List.of())},
                { getMT940TextBlockText(Map.of("Field86",
                                ":86:ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  \n")
                        , List.of()),
                        getMT940TextBlock(Map.of("Field86",
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO \n" +
                                "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO"), List.of())}
        };
    }

    @DataProvider(name = "invalidInfoToAccOwnerOpt")
    Object[][] parseInvalidInfoToAccOwnerOpt() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field86", ":86D:ADDITIONAL INFO\n"), List.of())},
        };
    }

    @DataProvider(name = "invalidInfoToAccOwner")
    Object[][] parseInvalidInfoToAccOwner() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field86", ":86:\n"), List.of())},
                { getMT940TextBlockText(Map.of("Field86",
                        ":86:ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  " +
                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  " +
                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  " +
                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  " +
                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  " +
                        "ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO ADDITIONAL INFO  ADDITIONAL\n")
                        , List.of()) },
        };
    }
}
