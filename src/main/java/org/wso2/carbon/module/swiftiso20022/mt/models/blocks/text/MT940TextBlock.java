package org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text;

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
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
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model class for MT940 text block.
 */
public class MT940TextBlock {
    private Field20 transactionReferenceNumber;
    private Field21 relatedReference;
    private Field25 accountIdentification;
    private Field25P accountIdentificationOptP;
    private Field28C statementSequenceNumber;
    private Field60F openingBalOptF;
    private Field60M openingBalOptM;
    private List<Field61> statementLines;
    private List<Field86> statementLineInfoToAccountOwner;
    private Field62F closingBalOptF;
    private Field62M closingBalOptM;
    private Field64 closingAvlBalance;
    private Field65 forwardAvlBalance;
    private Field86 infoToAccountOwner;

    public Field20 getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(Field20 transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public Field21 getRelatedReference() {
        return relatedReference;
    }

    public void setRelatedReference(Field21 relatedReference) {
        this.relatedReference = relatedReference;
    }

    public Field25 getAccountIdentification() {
        return accountIdentification;
    }

    public void setAccountIdentification(Field25 accountIdentification) {
        this.accountIdentification = accountIdentification;
    }

    public Field25P getAccountIdentificationOptP() {
        return accountIdentificationOptP;
    }

    public void setAccountIdentificationOptP(Field25P accountIdentificationOptP) {
        this.accountIdentificationOptP = accountIdentificationOptP;
    }

    public Field28C getStatementSequenceNumber() {
        return statementSequenceNumber;
    }

    public void setStatementSequenceNumber(Field28C statementSequenceNumber) {
        this.statementSequenceNumber = statementSequenceNumber;
    }

    public Field60F getOpeningBalOptF() {
        return openingBalOptF;
    }

    public void setOpeningBalOptF(Field60F openingBalOptF) {
        this.openingBalOptF = openingBalOptF;
    }

    public Field60M getOpeningBalOptM() {
        return openingBalOptM;
    }

    public void setOpeningBalOptM(Field60M openingBalOptM) {
        this.openingBalOptM = openingBalOptM;
    }

    public List<Field61> getStatementLines() {
        return statementLines;
    }

    public void setStatementLines(List<Field61> statementLines) {
        this.statementLines = statementLines;
    }

    public void addStatementLine(Field61 statementLine) {
        if (this.statementLines == null) {
            setStatementLines(new ArrayList<>(Arrays.asList(statementLine)));
        } else {
            this.statementLines.add(statementLine);
        }
    }

    public List<Field86> getStatementLineInfoToAccountOwner() {
        return statementLineInfoToAccountOwner;
    }

    public void setStatementLineInfoToAccountOwner(List<Field86> statementLineInfoToAccountOwner) {
        this.statementLineInfoToAccountOwner = statementLineInfoToAccountOwner;
    }

    public void addStatementLineInfoToAccountOwner(Field86 statementLineInfoToAccountOwner) {
        if (this.statementLineInfoToAccountOwner == null) {
            setStatementLineInfoToAccountOwner(new ArrayList<>(Arrays.asList(statementLineInfoToAccountOwner)));
        } else {
            this.statementLineInfoToAccountOwner.add(statementLineInfoToAccountOwner);
        }
    }

    public Field62F getClosingBalOptF() {
        return closingBalOptF;
    }

    public void setClosingBalOptF(Field62F closingBalOptF) {
        this.closingBalOptF = closingBalOptF;
    }

    public Field62M getClosingBalOptM() {
        return closingBalOptM;
    }

    public void setClosingBalOptM(Field62M closingBalOptM) {
        this.closingBalOptM = closingBalOptM;
    }

    public Field64 getClosingAvlBalance() {
        return closingAvlBalance;
    }

    public void setClosingAvlBalance(Field64 closingAvlBalance) {
        this.closingAvlBalance = closingAvlBalance;
    }

    public Field65 getForwardAvlBalance() {
        return forwardAvlBalance;
    }

    public void setForwardAvlBalance(Field65 forwardAvlBalance) {
        this.forwardAvlBalance = forwardAvlBalance;
    }

    public Field86 getInfoToAccountOwner() {
        return infoToAccountOwner;
    }

    public void setInfoToAccountOwner(Field86 infoToAccountOwner) {
        this.infoToAccountOwner = infoToAccountOwner;
    }

    public static MT940TextBlock parse(String mt940TextBlock) throws MTMessageParsingException {
        List<String> fields = MTParserUtils.getTextBlockFields(mt940TextBlock);
        int fieldsCount = fields.size();

        MT940TextBlock textBlock = new MT940TextBlock();

        for (int i = 0; i < fieldsCount; i++) {
            String field = fields.get(i);

            Pattern fieldPattern = Pattern.compile(MTParserConstants.TEXT_BLOCK_FIELD_REGEX, Pattern.DOTALL);
            Matcher fieldMatcher = fieldPattern.matcher(field);

            if (!fieldMatcher.matches()) {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT, field));
            }

            String tag = fieldMatcher.group(1);

            switch (tag) {
                case Field20.TAG:
                    textBlock.setTransactionReferenceNumber(Field20.parse(fieldMatcher.group(2)));
                    break;

                case Field21.TAG:
                    textBlock.setRelatedReference(Field21.parse(fieldMatcher.group(2)));
                    break;

                case Field25.TAG:
                    textBlock.setAccountIdentification(Field25.parse(fieldMatcher.group(2)));
                    break;

                case Field25P.TAG:
                    textBlock.setAccountIdentificationOptP(Field25P.parse(fieldMatcher.group(2)));
                    break;

                case Field28C.TAG:
                    textBlock.setStatementSequenceNumber(Field28C.parse(fieldMatcher.group(2)));
                    break;

                case Field60F.TAG:
                    textBlock.setOpeningBalOptF(Field60F.parse(fieldMatcher.group(2)));
                    break;

                case Field60M.TAG:
                    textBlock.setOpeningBalOptM(Field60M.parse(fieldMatcher.group(2)));
                    break;

                case Field61.TAG:
                    textBlock.addStatementLine(Field61.parse(fieldMatcher.group(2)));

                    // Check the next field is Field86. If not mark as no associated Field86
                    if (i + 1 < fieldsCount && !fields.get(i + 1).startsWith(Field86.TAG)) {
                        textBlock.addStatementLineInfoToAccountOwner(null);
                    }
                    break;

                case Field86.TAG:
                    if (i > 0 && fields.get(i - 1).startsWith(Field61.TAG)) {
                        textBlock.addStatementLineInfoToAccountOwner(Field86.parse(fieldMatcher.group(2)));
                    } else {
                        textBlock.setInfoToAccountOwner(Field86.parse(fieldMatcher.group(2)));
                    }
                    break;

                case Field62F.TAG:
                    textBlock.setClosingBalOptF(Field62F.parse(fieldMatcher.group(2)));
                    break;

                case Field62M.TAG:
                    textBlock.setClosingBalOptM(Field62M.parse(fieldMatcher.group(2)));
                    break;

                case Field64.TAG:
                    textBlock.setClosingAvlBalance(Field64.parse(fieldMatcher.group(2)));
                    break;

                case Field65.TAG:
                    textBlock.setForwardAvlBalance(Field65.parse(fieldMatcher.group(2)));
                    break;

                default:
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT, field));
            }
        }

        return textBlock;
    }
}
