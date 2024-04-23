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
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT940TextBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field21;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field25;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field25P;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field28C;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field60F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field60M;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field61;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field62F;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field62M;
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
                + (params.getOrDefault("Field28C", ":28C:1/1\n"))
                + (params.getOrDefault("Field60F", ":60F:D230930USD843686,20\n"))
                + (params.getOrDefault("Field60M", ":60M:D230930USD843686,20\n")) + stmtLines
                + (params.getOrDefault("Field62F", ":62F:D230930USD846665,15\n"))
                + (params.getOrDefault("Field62M", ""))
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

        textBlock.setTransactionReferenceNumber(new Field20().withValue(
                params.getOrDefault("Field20", "258158850")));

        if (params.containsKey("Field20")) {
            textBlock.setTransactionReferenceNumber(new Field20().withValue(params.get("Field20")));
        }

        if (params.containsKey("Field21")) {
            textBlock.setRelatedReference(new Field21().withValue(params.get("Field21")));
        }

        if (params.containsKey("Field25")) {
            textBlock.setAccountIdentification(new Field25().withValue(params.get("Field25")));
        }

        if (params.containsKey("Field25PAccount")) {
            if (textBlock.getAccountIdentificationOptP() == null) {
                textBlock.setAccountIdentificationOptP(new Field25P().withAccount(params.get("Field25PAccount")));
            } else {
                textBlock.setAccountIdentificationOptP(textBlock.getAccountIdentificationOptP()
                        .withAccount(params.get("Field25PAccount")));
            }
        }

        if (params.containsKey("Field25PIdentifierCode")) {
            if (textBlock.getAccountIdentificationOptP() == null) {
                textBlock.setAccountIdentificationOptP(new Field25P()
                        .withIdentifierCode(params.get("Field25PIdentifierCode")));
            } else {
                textBlock.setAccountIdentificationOptP(textBlock.getAccountIdentificationOptP()
                        .withIdentifierCode(params.get("Field25PIdentifierCode")));
            }
        }

        if (params.containsKey("Field28CStmtNumber")) {
            if (textBlock.getStatementSequenceNumber() == null) {
                textBlock.setStatementSequenceNumber(new Field28C()
                        .withStatementNumber(params.get("Field28CStmtNumber")));
            } else {
                textBlock.setStatementSequenceNumber(textBlock.getStatementSequenceNumber()
                        .withStatementNumber(params.get("Field28CStmtNumber")));
            }
        }

        if (params.containsKey("Field28CSeqNumber")) {
            if (textBlock.getStatementSequenceNumber() == null) {
                textBlock.setStatementSequenceNumber(new Field28C()
                        .withSequenceNumber(params.get("Field28CSeqNumber")));
            } else {
                textBlock.setStatementSequenceNumber(textBlock.getStatementSequenceNumber()
                        .withSequenceNumber(params.get("Field28CSeqNumber")));
            }
        }

        // Opening balance - Field 60F
        if (params.containsKey("Field60FDCMark")) {
            if (textBlock.getOpeningBalOptF() == null) {
                textBlock.setOpeningBalOptF(new Field60F().withDCMark(params.get("Field60FDCMark")));
            } else {
                textBlock.setOpeningBalOptF(textBlock.getOpeningBalOptF().withDCMark(params.get("Field60FDCMark")));
            }
        }

        if (params.containsKey("Field60FDate")) {
            if (textBlock.getOpeningBalOptF() == null) {
                textBlock.setOpeningBalOptF(new Field60F().withDate(params.get("Field60FDate")));
            } else {
                textBlock.setOpeningBalOptF(textBlock.getOpeningBalOptF().withDate(params.get("Field60FDate")));
            }
        }

        if (params.containsKey("Field60FCurrency")) {
            if (textBlock.getOpeningBalOptF() == null) {
                textBlock.setOpeningBalOptF(new Field60F().withCurrency(params.get("Field60FCurrency")));
            } else {
                textBlock.setOpeningBalOptF(textBlock.getOpeningBalOptF().withCurrency(params.get("Field60FCurrency")));
            }
        }

        if (params.containsKey("Field60FAmount")) {
            if (textBlock.getOpeningBalOptF() == null) {
                textBlock.setOpeningBalOptF(new Field60F().withAmount(params.get("Field60FAmount")));
            } else {
                textBlock.setOpeningBalOptF(textBlock.getOpeningBalOptF().withAmount(params.get("Field60FAmount")));
            }
        }

        // Opening balance - Field 60M
        if (params.containsKey("Field60MDCMark")) {
            if (textBlock.getOpeningBalOptM() == null) {
                textBlock.setOpeningBalOptM(new Field60M().withDCMark(params.get("Field60MDCMark")));
            } else {
                textBlock.setOpeningBalOptM(textBlock.getOpeningBalOptM().withDCMark(params.get("Field60MDCMark")));
            }
        }

        if (params.containsKey("Field60MDate")) {
            if (textBlock.getOpeningBalOptM() == null) {
                textBlock.setOpeningBalOptM(new Field60M().withDate(params.get("Field60MDate")));
            } else {
                textBlock.setOpeningBalOptM(textBlock.getOpeningBalOptM().withDate(params.get("Field60MDate")));
            }
        }

        if (params.containsKey("Field60MCurrency")) {
            if (textBlock.getOpeningBalOptM() == null) {
                textBlock.setOpeningBalOptM(new Field60M().withCurrency(params.get("Field60MCurrency")));
            } else {
                textBlock.setOpeningBalOptM(textBlock.getOpeningBalOptM().withCurrency(params.get("Field60MCurrency")));
            }
        }

        if (params.containsKey("Field60MAmount")) {
            if (textBlock.getOpeningBalOptM() == null) {
                textBlock.setOpeningBalOptM(new Field60M().withAmount(params.get("Field60MAmount")));
            } else {
                textBlock.setOpeningBalOptM(textBlock.getOpeningBalOptM().withAmount(params.get("Field60MAmount")));
            }
        }

        List<Field61> statementsSet = new ArrayList<>();
        List<Field86> addInfoSet = new ArrayList<>();

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

            statementsSet.add(field61);

            // Adding additional info
            if (statement.containsKey("Field86")) {
                addInfoSet.add(new Field86().withValue(statement.get("Field86")));
            } else {
                addInfoSet.add(null);
            }

        }

        textBlock.setStatementLines(statementsSet);
        textBlock.setStatementLineInfoToAccountOwner(addInfoSet);

        // Closing balance - Field 62F
        if (params.containsKey("Field62FDCMark")) {
            if (textBlock.getClosingBalOptF() == null) {
                textBlock.setClosingBalOptF(new Field62F().withDCMark(params.get("Field62FDCMark")));
            } else {
                textBlock.setClosingBalOptF(textBlock.getClosingBalOptF().withDCMark(params.get("Field62FDCMark")));
            }
        }

        if (params.containsKey("Field62FDate")) {
            if (textBlock.getClosingBalOptF() == null) {
                textBlock.setClosingBalOptF(new Field62F().withDate(params.get("Field62FDate")));
            } else {
                textBlock.setClosingBalOptF(textBlock.getClosingBalOptF().withDate(params.get("Field62FDate")));
            }
        }

        if (params.containsKey("Field62FCurrency")) {
            if (textBlock.getClosingBalOptF() == null) {
                textBlock.setClosingBalOptF(new Field62F().withCurrency(params.get("Field62FCurrency")));
            } else {
                textBlock.setClosingBalOptF(textBlock.getClosingBalOptF().withCurrency(params.get("Field62FCurrency")));
            }
        }

        if (params.containsKey("Field62FAmount")) {
            if (textBlock.getClosingBalOptF() == null) {
                textBlock.setClosingBalOptF(new Field62F().withAmount(params.get("Field62FAmount")));
            } else {
                textBlock.setClosingBalOptF(textBlock.getClosingBalOptF().withAmount(params.get("Field62FAmount")));
            }
        }

        // Closing balance - Field 62M
        if (params.containsKey("Field62MDCMark")) {
            if (textBlock.getClosingBalOptM() == null) {
                textBlock.setClosingBalOptM(new Field62M().withDCMark(params.get("Field62MDCMark")));
            } else {
                textBlock.setClosingBalOptM(textBlock.getClosingBalOptM().withDCMark(params.get("Field62MDCMark")));
            }
        }

        if (params.containsKey("Field62MDate")) {
            if (textBlock.getClosingBalOptM() == null) {
                textBlock.setClosingBalOptM(new Field62M().withDate(params.get("Field62MDate")));
            } else {
                textBlock.setClosingBalOptM(textBlock.getClosingBalOptM().withDate(params.get("Field62MDate")));
            }
        }

        if (params.containsKey("Field62MCurrency")) {
            if (textBlock.getClosingBalOptM() == null) {
                textBlock.setClosingBalOptM(new Field62M().withCurrency(params.get("Field62MCurrency")));
            } else {
                textBlock.setClosingBalOptM(textBlock.getClosingBalOptM().withCurrency(params.get("Field62MCurrency")));
            }
        }

        if (params.containsKey("Field62MAmount")) {
            if (textBlock.getClosingBalOptM() == null) {
                textBlock.setClosingBalOptM(new Field62M().withAmount(params.get("Field62MAmount")));
            } else {
                textBlock.setClosingBalOptM(textBlock.getClosingBalOptM().withAmount(params.get("Field62MAmount")));
            }
        }

        // Closing available balance - Field 64
        if (params.containsKey("Field64DCMark")) {
            if (textBlock.getClosingAvlBalance() == null) {
                textBlock.setClosingAvlBalance(new Field64().withDCMark(params.get("Field64DCMark")));
            } else {
                textBlock.setClosingAvlBalance(textBlock.getClosingAvlBalance()
                        .withDCMark(params.get("Field64DCMark")));
            }
        }

        if (params.containsKey("Field64Date")) {
            if (textBlock.getClosingAvlBalance() == null) {
                textBlock.setClosingAvlBalance(new Field64().withDate(params.get("Field64Date")));
            } else {
                textBlock.setClosingAvlBalance(textBlock.getClosingAvlBalance().withDate(params.get("Field64Date")));
            }
        }

        if (params.containsKey("Field64Currency")) {
            if (textBlock.getClosingAvlBalance() == null) {
                textBlock.setClosingAvlBalance(new Field64().withCurrency(params.get("Field64Currency")));
            } else {
                textBlock.setClosingAvlBalance(textBlock.getClosingAvlBalance()
                        .withCurrency(params.get("Field64Currency")));
            }
        }

        if (params.containsKey("Field64Amount")) {
            if (textBlock.getClosingAvlBalance() == null) {
                textBlock.setClosingAvlBalance(new Field64().withAmount(params.get("Field64Amount")));
            } else {
                textBlock.setClosingAvlBalance(textBlock.getClosingAvlBalance()
                        .withAmount(params.get("Field64Amount")));
            }
        }

        // Forward available balance - Field 65
        if (params.containsKey("Field65DCMark")) {
            if (textBlock.getForwardAvlBalance() == null) {
                textBlock.setForwardAvlBalance(new Field65()
                        .withDCMark(params.get("Field65DCMark")));
            } else {
                textBlock.setForwardAvlBalance(textBlock.getForwardAvlBalance()
                        .withDCMark(params.get("Field65DCMark")));
            }
        }

        if (params.containsKey("Field65Date")) {
            if (textBlock.getForwardAvlBalance() == null) {
                textBlock.setForwardAvlBalance(new Field65().withDate(params.get("Field65Date")));
            } else {
                textBlock.setForwardAvlBalance(textBlock.getForwardAvlBalance().withDate(params.get("Field65Date")));
            }
        }

        if (params.containsKey("Field65Currency")) {
            if (textBlock.getForwardAvlBalance() == null) {
                textBlock.setForwardAvlBalance(new Field65().withCurrency(params.get("Field65Currency")));
            } else {
                textBlock.setForwardAvlBalance(textBlock.getForwardAvlBalance()
                        .withCurrency(params.get("Field65Currency")));
            }
        }

        if (params.containsKey("Field65Amount")) {
            if (textBlock.getForwardAvlBalance() == null) {
                textBlock.setForwardAvlBalance(new Field65().withAmount(params.get("Field65Amount")));
            } else {
                textBlock.setForwardAvlBalance(textBlock.getForwardAvlBalance()
                        .withAmount(params.get("Field65Amount")));
            }
        }

        if (params.containsKey("Field86")) {
            textBlock.setInfoToAccountOwner(new Field86().withValue(params.get("Field86")));
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
                        "Field28C", ":28C:1/1\n",
                        "Field60F", ":60F:D230930USD843686,20\n",
                        "Field62F", ":62F:D230930USD846665,15\n",
                        "Field64", ":64:C231002USD334432401,27\n",
                        "Field65", ":65:C231002USD334432401,27\n"),
                        List.of(":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n",
                            ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n",
                            ":61:2310011001DD10,00ACHPNONREF\n",
                            ":61:2310011001CD10,00ASHP20230928LTERMID2000003//ADDITIONAL INFO\nADDITIONAL INFO\n")),
                    getMT940TextBlock(Map.ofEntries(Map.entry("Field20", "258158850"),
                        Map.entry("Field21", "258158850"), Map.entry("Field25", "DD01100056869"),
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

    @DataProvider(name = "invalidTransactionReferenceNumber")
    Object[][] parseInvalidTransactionReferenceNumber() {
        return new Object[][]{
            { getMT940TextBlockText(Map.of("Field20", ":20:\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field20", ":20:<<<1234>>>>\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field20", ":20:258158850258158850258158850258158850\n"), List.of()) }
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

    @DataProvider(name = "invalidAccountIdentification")
    Object[][] parseInvalidAccIdentification() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field25", ":25:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:<<<1234>>>>\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field25", ":25:DD01100056869DD01100056869DD01100056869\n"),
                        List.of()) }
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

    @DataProvider(name = "invalidStmtSeqNumber")
    Object[][] parseInvalidStmtSeqNumber() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field28C", ":28C:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:ABC\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:1204056\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:12/\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:12*12\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:12/ABC\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field28C", ":28C:12/1204056\n"), List.of()) },
        };
    }

    @DataProvider(name = "invalidOpeningBalanceOptF")
    Object[][] parseInvalidOpeningBalanceOptF() {
        return new Object[][]{
            { getMT940TextBlockText(Map.of("Field60F", ":60F:\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:E230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:3230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:230930USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D001USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D23423423USD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:DABCDEFUSD843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930US843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930USDEF843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930123843686,20\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930USD\n"), List.of()) },
            { getMT940TextBlockText(Map.of("Field60F", ":60F:D230930USD843686843686843686843686,20\n"),
                    List.of()) }
        };
    }

    @DataProvider(name = "invalidOpeningBalanceOptM")
    Object[][] parseInvalidOpeningBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field60M", ":60M:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field60M", ":60M:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
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


    @DataProvider(name = "invalidClosingBalanceOptF")
    Object[][] parseInvalidClosingBalanceOptF() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62F", ":62F:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62F", ":62F:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
        };
    }

    @DataProvider(name = "invalidClosingBalanceOptM")
    Object[][] parseInvalidClosingBalanceOptM() {
        return new Object[][]{
                { getMT940TextBlockText(Map.of("Field62M", ":62M:\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:E230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:3230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:230930USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D001USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D23423423USD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:DABCDEFUSD843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930US843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930USDEF843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930123843686,20\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930USD\n"), List.of()) },
                { getMT940TextBlockText(Map.of("Field62M", ":62M:D230930USD843686843686843686843686,20\n"),
                        List.of()) }
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
