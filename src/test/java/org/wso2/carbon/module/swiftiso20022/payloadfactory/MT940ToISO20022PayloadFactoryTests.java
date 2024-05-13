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

package org.wso2.carbon.module.swiftiso20022.payloadfactory;

import com.google.gson.Gson;
import freemarker.template.Template;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.utils.FreemarkerTemplateTestUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ToISO20022PayloadFactoryTestConstants;


import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Test class for validating payload factory freemarker template for
 * convert MT940 message to ISO20022 message.
 */
public class MT940ToISO20022PayloadFactoryTests {

    private static final Gson gson = new Gson();

    private String getISO20022FromMT940(String mt940Message) throws Exception {
        // Get the ftl file as input stream
        InputStream freemarkerTempStream = getClass().getClassLoader()
                .getResourceAsStream("freemarkerTemplates/MT940ToISO.ftl");

        // Create the map from the MT940 JSON
        Map<String, Object> mt940Map = Map.of("payload", gson.fromJson(mt940Message, Map.class));

        Template template = FreemarkerTemplateTestUtils
                .getFreemarkerTemplate(freemarkerTempStream, "ISOToMT940");

        Writer stringwriter = new StringWriter();
        template.process(mt940Map, stringwriter);
        return stringwriter.toString();
    }

    @Test(dataProvider = "parseAppHdrFromElement", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseAppHdrFromElementScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/app:AppHdr/app:Fr/app:FIId/app:FinInstnId/app:BICFI"), targetValue);
    }

    @Test(dataProvider = "parseAppHdrToElement", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseAppHdrToElementScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                    "/BizMsgEnvlp/app:AppHdr/app:To/app:FIId/app:FinInstnId/app:BICFI"), targetValue);
    }

    @Test(dataProvider = "parseMessageCreatedDt", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseAppHdrCreatedDtScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/app:AppHdr/app:CreDt"), targetValue);
    }

    @Test(dataProvider = "parseMessageCreatedDt", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseGrpHdrCreatedDtScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:GrpHdr/doc:CreDt"), targetValue);
    }

    @Test(dataProvider = "parseStatementNumber", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseStatementNumberScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:GrpHdr/doc:MsgPgntn/doc:PgNb"), targetValue);
    }

    @Test(dataProvider = "parseStatementNumber", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseMsgPaginationPgNumScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:GrpHdr/doc:MsgPgntn/doc:PgNb"), targetValue);
    }

    @Test(dataProvider = "parseStatementNumber", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseElectronicSeqNumScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:ElctrncSeqNb"), targetValue);
    }

    @Test(dataProvider = "parseSequenceNumber", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseLegalSeqNumScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:LglSeqNb"), targetValue);
    }

    @Test(dataProvider = "parseTransactionRefNum", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseStatementIdScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Id"), targetValue);
    }

    @Test(dataProvider = "parseAccountIdentification",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseAccountIdScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Acct/doc:Id/doc:Othr/doc:Id"), targetValue);
    }

    @Test(dataProvider = "parseAppHdrToElement", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseAccountSourceBICScenario(String mt940Message, String targetValue) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Acct/doc:Svcr/doc:FinInstnId/doc:BICFI"),
                targetValue);
    }

    @Test(dataProvider = "parseBalances", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseOpeningBalanceScenario(String mt940Message, Map<String, String> tragetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        // Add tge Currency
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLElement(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='OPBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt")
                .getAttributeValue(QName.valueOf("Ccy")), tragetValues.get("Currency")); // Currency Type
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='OPBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt[text()]"),
                tragetValues.get("Amount")); // Amount
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='OPBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:CdtDbtInd[text()]"),
                tragetValues.get("DCMark")); // Credit/Debit Indicator
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='OPBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Dt/doc:Dt[text()]"),
                tragetValues.get("Date")); // Date

    }

    @Test(dataProvider = "parseBalances", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseClosingBalanceScenario(String mt940Message, Map<String, String> tragetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        // Add tge Currency
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLElement(freemarkerOutput,
      "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/" +
              "doc:Cd[text()='CLBD']/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt")
             .getAttributeValue(QName.valueOf("Ccy")), tragetValues.get("Currency")); // Currency Type
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt[text()]"),
                tragetValues.get("Amount")); // Amount
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:CdtDbtInd[text()]"),
                tragetValues.get("DCMark")); // Credit/Debit Indicator
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLBD']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Dt/doc:Dt[text()]"),
                tragetValues.get("Date")); // Date

    }

    @Test(dataProvider = "parseBalances", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseClosingAvlBalanceScenario(String mt940Message, Map<String, String> tragetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        // Add tge Currency
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLElement(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt")
                .getAttributeValue(QName.valueOf("Ccy")), tragetValues.get("Currency")); // Currency Type
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt[text()]"),
                tragetValues.get("Amount")); // Amount
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:CdtDbtInd[text()]"),
                tragetValues.get("DCMark")); // Credit/Debit Indicator
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='CLAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Dt/doc:Dt[text()]"),
                tragetValues.get("Date")); // Date

    }

    @Test(dataProvider = "parseBalances", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseForwardAvlBalanceScenario(String mt940Message, Map<String, String> tragetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        // Add tge Currency
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLElement(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='FWAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt")
                .getAttributeValue(QName.valueOf("Ccy")), tragetValues.get("Currency")); // Currency Type
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='FWAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Amt[text()]"),
                tragetValues.get("Amount")); // Amount
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='FWAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:CdtDbtInd[text()]"),
                tragetValues.get("DCMark")); // Credit/Debit Indicator
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Bal/doc:Tp/doc:CdOrPrtry/doc:Cd[text()='FWAV']"
                + "/parent::doc:CdOrPrtry/parent::doc:Tp/parent::doc:Bal/doc:Dt/doc:Dt[text()]"),
                tragetValues.get("Date")); // Date

    }

    @Test(dataProvider = "parseEntryAmount", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryAmountScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                 "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:Amt"),
                targetValues);
    }

    @Test(dataProvider = "parseCreditDebitIndicator",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseCreditDebitIndicatorScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:CdtDbtInd"),
                targetValues);
    }

    @Test(dataProvider = "parseReversalIndicator", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseReversalIndicatorScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:RvslInd"),
                targetValues);
    }

    @Test(dataProvider = "parseEntryBookingDt", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryBookingDtScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:BookgDt/doc:Dt"),
                targetValues);
    }

    @Test(dataProvider = "parseEntryValueDt", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryValueDtScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:ValDt/doc:Dt"),
                targetValues);
    }

    @Test(dataProvider = "parseEntryAcctServiceInstRef",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryAcctServiceInstRefScenario(String mt940Message, List<String> targetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:AcctSvcrRef"),
                targetValues);
    }

    @Test(dataProvider = "parseBankTransactionCode",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseBankTransactionCodeScenario(String mt940Message, List<String> targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:BkTxCd/doc:Domn/doc:Cd"),
                targetValues);
    }

    @Test(dataProvider = "parseEntryDtlsEndToEndReferences",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryDtlsEndToEndReferencesScenario(String mt940Message, List<String> targetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:NtryDtls/doc:TxDtls/doc:Refs" +
                "/doc:EndToEndId"), targetValues);
    }

    @Test(dataProvider = "parseEntryDtlsInstructionId", dataProviderClass =
            MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryDtlsInstructionIdScenario(String mt940Message, List<String> targetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:NtryDtls/doc:TxDtls/doc:Refs" +
                "/doc:InstrId"), targetValues);
    }

    @Test(dataProvider = "parseEntryDtlsPaymentInfoId", dataProviderClass =
            MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryDtlsPaymentInfoIdScenario(String mt940Message, List<String> targetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodesTexts(freemarkerOutput,
         "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:Ntry/doc:NtryDtls/doc:TxDtls/doc:Refs" +
                 "/doc:PmtInfId"), targetValues);
    }

    @Test(dataProvider = "parseStmtAdditionalInfo",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseEntryAdditionalInfoScenario(String mt940Message, String targetValues) throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
                        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:AddtlStmtInf"),
                targetValues);
    }

    @Test(dataProvider = "parseTransactionSummary",
            dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testParseTransactionSummaryScenario(String mt940Message, Map<String, String> targetValues)
            throws Exception {
        String freemarkerOutput = getISO20022FromMT940(mt940Message);

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlNtries/doc:NbOfNtries"),
                targetValues.get("TotalNumEntries"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlNtries/doc:Sum"),
                targetValues.get("TotalSum"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlNtries/" +
                "doc:TtlNetNtry/doc:Amt"), targetValues.get("TotalNetSummary"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlNtries/doc:TtlNetNtry" +
                "/doc:CdtDbtInd"), targetValues.get("TotalDCMark"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlCdtNtries" +
                "/doc:NbOfNtries"), targetValues.get("TotalCreditEntries"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
            "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlCdtNtries/doc:Sum"),
                targetValues.get("TotalCreditSum"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
            "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlDbtNtries/doc:NbOfNtries"),
                targetValues.get("TotalDebitEntries"));
        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.getXMLNodeText(freemarkerOutput,
        "/BizMsgEnvlp/doc:Document/doc:BkToCstmrStmt/doc:Stmt/doc:TxsSummry/doc:TtlDbtNtries/doc:Sum"),
                targetValues.get("TotalDebitSum"));
    }
}
