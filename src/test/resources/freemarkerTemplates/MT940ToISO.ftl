<BizMsgEnvlp>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.03">
	<#if payload.applicationHeaderBlock.messageInputReference?has_content>
	<Fr>
		<FIId>
			<FinInstnId>
				<BICFI>${payload.FromBIC}</BICFI><#-- Application header: MIR LT identifier -->
			</FinInstnId>
		</FIId>
	</Fr>
	</#if>
	<To>
		<FIId>
			<FinInstnId>
				<BICFI>${payload.ToBIC}</BICFI><#-- Basic header: LT identifier -->
			</FinInstnId>
		</FIId>
	</To>
	<MsgDefIdr>camt.053.001.11</MsgDefIdr>
	<#if payload.applicationHeaderBlock?has_content>
	<CreDt>${payload.applicationHeaderBlock.createdDt}</CreDt><#-- Application header: Input Time OR Application header: Time in MIR -->
	</#if>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.11">
    <BkToCstmrStmt>
        <GrpHdr>
			<#if payload.applicationHeaderBlock?has_content><CreDt>${payload.applicationHeaderBlock.createdDt}</CreDt></#if><#-- Application header: Input Time OR Application header: Time in MIR -->
            <#if payload.textBlock.statementSequenceNumber.statementNumber?has_content>
            	<MsgPgntn>
            		<PgNb>${payload.textBlock.statementSequenceNumber.statementNumber}</PgNb><#-- Tag 28C: Statement number / [Sequence Number] -->
            	</MsgPgntn>
            </#if>
        </GrpHdr>
        <Stmt>
            <Id>${payload.textBlock.transactionReferenceNumber.value}</Id><#-- Tag 20 / Transaction Reference Number: Value  -->
            <#if payload.textBlock.statementSequenceNumber.statementNumber?has_content>
            	<MsgPgntn>
            		<PgNb>${payload.textBlock.statementSequenceNumber.statementNumber}</PgNb><#-- Tag 28C: Statement number / [Sequence Number] -->
            	</MsgPgntn>
            </#if>
            <#if payload.textBlock.statementSequenceNumber.statementNumber?has_content>
            	<ElctrncSeqNb>${payload.textBlock.statementSequenceNumber.statementNumber}</ElctrncSeqNb><#-- Tag 28C: Statement number / [Sequence Number] -->
            </#if>
            <#if payload.textBlock.statementSequenceNumber.sequenceNumber?has_content>
            	<LglSeqNb>${payload.textBlock.statementSequenceNumber.sequenceNumber}</LglSeqNb><#-- Tag 28C: Statement number / [Sequence Number] -->
            </#if>
            <Acct>
            	<#if payload.textBlock.accountIdentification?has_content>
                <Id>
                    <Othr>
                        <Id>${payload.textBlock.accountIdentification.account}</Id><#-- Tag 25: Account -->
                    </Othr>
                </Id>
                </#if>
                <Svcr>
                    <FinInstnId>
                        <BICFI>${payload.ToBIC}</BICFI><#-- Basic header: LT identifier -->
                    </FinInstnId>
                </Svcr>
            </Acct>
            <Bal>
                <Tp>
                    <CdOrPrtry>
                        <Cd>OPBD</Cd><#-- Tag 60: Opening balance -->
                    </CdOrPrtry>
                </Tp>
                <Amt Ccy="${payload.textBlock.openingBal.currency}">${payload.textBlock.openingBal.amount?string("##0.00")}</Amt><#-- Tag 60/Opening Balance: Currency and Amount -->
                <CdtDbtInd><#if payload.textBlock.openingBal.dcMark == "C">CRDT<#else>DBIT</#if></CdtDbtInd><#-- Tag 60/Closing Balance: D/C Mark -->
                <Dt>
                	<Dt>${payload.textBlock.openingBal.dateDt}</Dt><#-- Tag 60/Opening Balance: Date -->
                </Dt>
            </Bal>
            <Bal>
                <Tp>
                    <CdOrPrtry>
                        <Cd>CLBD</Cd><#-- Tag 62: Closing balance -->
                    </CdOrPrtry>
                </Tp>
                <Amt Ccy="${payload.textBlock.closingBal.currency}">${payload.textBlock.closingBal.amount?string("##0.00")}</Amt><#-- Tag 62/Closing Balance: Currency and Amount -->
                <CdtDbtInd><#if payload.textBlock.closingBal.dcMark == "C">CRDT<#else>DBIT</#if></CdtDbtInd><#-- Tag 62/Closing Balance: D/C Mark -->
                <Dt>
                	<Dt>${payload.textBlock.closingBal.dateDt}</Dt><#-- Tag 62/Closing Balance: Date -->
                </Dt>
            </Bal>
            <#if payload.textBlock.closingAvlBalance?has_content>
            <Bal>
                <Tp>
                    <CdOrPrtry>
                        <Cd>CLAV</Cd><#-- Tag 64: Closing available balance -->
                    </CdOrPrtry>
                </Tp>
                <Amt Ccy="${payload.textBlock.closingAvlBalance.currency}">${payload.textBlock.closingAvlBalance.amount?string("##0.00")}</Amt><#-- Tag 64/Closing available balance: Currency and Amount -->
                <CdtDbtInd><#if payload.textBlock.closingAvlBalance.dcMark == "C">CRDT<#else>DBIT</#if></CdtDbtInd><#-- Tag 64/Closing available balance: D/C Mark -->
                <Dt>
                    <Dt>${payload.textBlock.closingAvlBalance.dateDt}</Dt><#-- Tag 64/Closing available balance: Date -->
                </Dt>
            </Bal>
            </#if>
            <#if payload.textBlock.forwardAvlBalance?has_content>
            <Bal>
                <Tp>
                    <CdOrPrtry>
                        <Cd>FWAV</Cd><#-- Tag 65: Forward available balance -->
                    </CdOrPrtry>
                </Tp>
                <Amt Ccy="${payload.textBlock.forwardAvlBalance.currency}">${payload.textBlock.forwardAvlBalance.amount?string("##0.00")}</Amt><#-- Tag 65/Forward available balance: Currency and Amount -->
                <CdtDbtInd><#if payload.textBlock.forwardAvlBalance.dcMark == "C">CRDT<#else>DBIT</#if></CdtDbtInd><#-- Tag 65/Forward available balance: D/C Mark -->
                <Dt>
                    <Dt>${payload.textBlock.forwardAvlBalance.dateDt}</Dt><#-- Tag 65/Forward available balance: Date -->
                </Dt>
            </Bal>
            </#if>
            <TxsSummry>
            	<#assign creditCount = 0 /><#assign debitCount = 0 />
            	<#assign creditAmount = 0 /><#assign debitAmount = 0 />
            	<#assign statements = [] />
            	<#if payload.textBlock.statementLines?has_content && (payload.textBlock.statementLines?size > 0)>
            		<#assign statements = payload.textBlock.statementLines />
            	</#if>
            	<#list statements as statement>
            		<#if statement.Field61.dcMark == "C" || statement.Field61.dcMark == "RC">
            			<#assign creditCount = creditCount + 1 /><#assign creditAmount = creditAmount + statement.Field61.amount?number />
            		<#else>
            			<#assign debitCount = debitCount + 1 /><#assign debitAmount = debitAmount + statement.Field61.amount?number />
            		</#if>
            	</#list>
            	<TtlNtries>
                    <NbOfNtries>${creditCount + debitCount}</NbOfNtries>
                    <Sum>${(creditAmount + debitAmount)?string("##0.00")}</Sum>
                    <#if (creditAmount < debitAmount)>
                   	<TtlNetNtry>
                   		<Amt>${(debitAmount - creditAmount)?string("##0.00")}</Amt>
                   		<CdtDbtInd>DBIT</CdtDbtInd>
                   	</TtlNetNtry>
                   	<#else>
                   	<TtlNetNtry>
                   		<Amt>${(creditAmount - debitAmount)?string("##0.00")}</Amt>
                   		<CdtDbtInd>CRDT</CdtDbtInd>
                   	</TtlNetNtry>
                   	</#if>
                </TtlNtries>
            	<TtlCdtNtries>
                    <NbOfNtries>${creditCount}</NbOfNtries>
                    <Sum>${creditAmount?string("##0.00")}</Sum>
                </TtlCdtNtries>
            	<TtlDbtNtries>
                    <NbOfNtries>${debitCount}</NbOfNtries>
                    <Sum>${debitAmount?string("##0.00")}</Sum>
                </TtlDbtNtries>
            </TxsSummry>
            <#assign entries = [] />
            <#if payload.textBlock.statementLines?has_content && (payload.textBlock.statementLines?size > 0)>
            	<#assign entries=payload.textBlock.statementLines/>
            </#if>
            <#list entries as entry>
            <Ntry>
                <Amt>${entry.Field61.amount?string("##0.00")}</Amt><#-- Tag 61: Amount -->
                <CdtDbtInd><#if entry.Field61.dcMark == "C" || entry.Field61.dcMark == "RC">CRDT<#else>DBIT</#if></CdtDbtInd><#-- Tag 61: D/C Mark -->
                <RvslInd><#if entry.Field61.dcMark == "RC" || entry.Field61.dcMark == "RD">true<#else>false</#if></RvslInd><#-- Tag 61: D/C Mark -->
              	<#if entry.Field61.entryDateDt?has_content>
                <BookgDt>
                    <Dt>${entry.Field61.entryDateDt}</Dt><#-- Tag 61: Entry Date with Current System year -->
                </BookgDt>
                </#if>
                <ValDt>
                    <Dt>${entry.Field61.valueDateDt}</Dt><#-- Tag 61: Value Date -->
                </ValDt>
                <#if entry.Field61.refToAccountServicingInstitution?has_content>
                <AcctSvcrRef>${entry.Field61.refToAccountServicingInstitution}</AcctSvcrRef>
                </#if>
            	<BkTxCd>
					<Domn>
						<Cd>${entry.Field61.transactionType + entry.Field61.identificationCode}</Cd><#-- Tag 61: Transaction Type and Identification Code -->
					</Domn>
				</BkTxCd>
				<#if entry.Field86?has_content>
				<NtryDtls>
				<#list entry.Field86.value as txDtl>
					<#assign txDtlsStr="" />
					<#assign additionalInfo="" />
						<#if txDtl["EREF"]?has_content>
							<#assign txDtlsStr = txDtlsStr + "<EndToEndId>" + txDtl["EREF"] + "</EndToEndId>" />
						</#if>
						<#if txDtl["IREF"]?has_content>
							<#assign txDtlsStr = txDtlsStr + "<InstrId>" + txDtl["IREF"] + "</InstrId>" />
						</#if>
						<#if txDtl["PREF"]?has_content>
							<#assign txDtlsStr = txDtlsStr + "<PmtInfId>" + txDtl["PREF"] + "</PmtInfId>" />
						</#if>
						<#if txDtl["#ADDITIONAL-INFO#"]?has_content>
							<#assign additionalInfo = txDtl["#ADDITIONAL-INFO#"] />
						</#if>
					<#if txDtlsStr != "">
						<TxDtls>
							<Refs>
								${txDtlsStr}
							</Refs>
						</TxDtls>
					</#if>
					<#if additionalInfo != "">
						<AddtlNtryInf>${additionalInfo}</AddtlNtryInf>
					</#if>
				</#list>
				</NtryDtls>
				</#if>
            </Ntry>
            </#list>
            <#if payload.textBlock.infoToAccountOwner?has_content>
       		<#assign infoToAccoutOwn = "" />
            <#list payload.textBlock.infoToAccountOwner.value as line>
            	<#assign infoStr="" />
				<#if line["EREF"]?has_content>
					<#if infoStr == "">
						<#assign infoStr = "EREF/" + line["EREF"] />
					<#else>
						<#assign infoStr = infoStr + "/EREF/" + line["EREF"] />
					</#if>
				</#if>
				<#if line["IREF"]?has_content>
					<#if infoStr == "">
						<#assign infoStr = "IREF/" + line["IREF"] />
					<#else>
						<#assign infoStr = infoStr + "/IREF/" + line["IREF"] />
					</#if>
				</#if>
				<#if line["PREF"]?has_content>
					<#if infoStr == "">
						<#assign infoStr = "PREF/" + line["PREF"] />
					<#else>
						<#assign infoStr = infoStr + "/PREF/" + line["PREF"] />
					</#if>
				</#if>
				<#if line["#ADDITIONAL-INFO#"]?has_content>
					<#if infoStr == "">
						<#assign infoStr = line["#ADDITIONAL-INFO#"] />
					<#else>
						<#assign infoStr = infoStr + "/" + line["#ADDITIONAL-INFO#"] />
					</#if>
				</#if>
				<#if infoToAccoutOwn == ""><#assign infoToAccoutOwn = infoToAccoutOwn + infoStr/><#else><#assign infoToAccoutOwn = infoToAccoutOwn + "/" + infoStr/></#if>
            </#list>
            <AddtlStmtInf>${infoToAccoutOwn}</AddtlStmtInf>
            </#if>
        </Stmt>
    </BkToCstmrStmt>
</Document>
</BizMsgEnvlp>