package org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text;

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
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
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Model class for MT940 text block.
 */
public class MT940TextBlock {
    private Field20 transactionReferenceNumber;
    private Field21 relatedReference;
    private Field25 accountIdentification;
    private Field28 statementSequenceNumber;
    private Field60 openingBal;
    private List<Map<String, Object>> statementLines;
    private Field62 closingBal;
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

    public Field28 getStatementSequenceNumber() {
        return statementSequenceNumber;
    }

    public void setStatementSequenceNumber(Field28 statementSequenceNumber) {
        this.statementSequenceNumber = statementSequenceNumber;
    }

    public Field60 getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(Field60 openingBal) {
        this.openingBal = openingBal;
    }

    public List<Map<String, Object>> getStatementLines() {
        return statementLines;
    }

    public void setStatementLines(List<Map<String, Object>> statementLines) {
        this.statementLines = statementLines;
    }

    /**
     * Method for adding new element to statement line.
     * @param statementLine  Field61 object which contains the transaction details
     */
    public void addStatementLine(Field61 statementLine) {
        if (this.statementLines == null) {
            setStatementLines(new ArrayList<>(Arrays.asList(Map.of("Field61", statementLine))));
        } else {
            this.statementLines.add(Map.of("Field61", statementLine));
        }
    }

    /**
     * Method for adding new element to statement line.
     * @param statementLine   Field61 object which contains the transaction details
     * @param additionalInfo  Field86 object which contains additional info
     */
    public void addStatementLine(Field61 statementLine, Field86 additionalInfo) {
        if (this.statementLines == null) {
            setStatementLines(new ArrayList<>(Arrays.asList(Map.of(
                    "Field61", statementLine, "Field86", additionalInfo))));
        } else {
            this.statementLines.add(Map.of("Field61", statementLine, "Field86", additionalInfo));
        }
    }

    public Field62 getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(Field62 closingBal) {
        this.closingBal = closingBal;
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

            Matcher fieldMatcher = MTParserConstants.TEXT_BLOCK_FIELD_REGEX.matcher(field);

            if (!fieldMatcher.matches()) {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT, field));
            }

            String tag = fieldMatcher.group(1).substring(0, 2);
            char option = fieldMatcher.group(1).length() > 2 ? fieldMatcher.group(1).charAt(2)
                    : MTParserConstants.FIELD_OPTION_NO_LETTER;

            switch (tag) {
                case Field20.TAG:
                    textBlock.setTransactionReferenceNumber(new Field20(option, fieldMatcher.group(2)));
                    break;

                case Field21.TAG:
                    textBlock.setRelatedReference(new Field21(option, fieldMatcher.group(2)));
                    break;

                case Field25.TAG:
                    textBlock.setAccountIdentification(new Field25(option, fieldMatcher.group(2)));
                    break;

                case Field28.TAG:
                    textBlock.setStatementSequenceNumber(new Field28(option, fieldMatcher.group(2)));
                    break;

                case Field60.TAG:
                    textBlock.setOpeningBal(new Field60(option, fieldMatcher.group(2)));
                    break;

                case Field61.TAG:
                    // Check the next field is Field86.
                    if (i + 1 < fieldsCount && fields.get(i + 1).startsWith(Field86.TAG)) {
                        // Matching the next Field86
                        Matcher field86Matcher = MTParserConstants.TEXT_BLOCK_FIELD_REGEX.matcher(fields.get(i + 1));

                        if (!field86Matcher.matches()) {
                            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                                    field));
                        }

                        char field86Option = field86Matcher.group(1).length() > 2 ? field86Matcher.group(1).charAt(2)
                                : MTParserConstants.FIELD_OPTION_NO_LETTER;

                        textBlock.addStatementLine(new Field61(fieldMatcher.group(2)),
                                new Field86(field86Option, field86Matcher.group(2)));

                        // Increment the counter because Field86 is created
                        i = i + 1;
                    } else {
                        textBlock.addStatementLine(new Field61(fieldMatcher.group(2)));
                    }
                    break;

                case Field86.TAG:
                    // Since Field61 and Field86 pairs handle by Field61 switch this only executed for
                    // final Field86 in the MT940 message
                    textBlock.setInfoToAccountOwner(new Field86(option, fieldMatcher.group(2)));
                    break;

                case Field62.TAG:
                    textBlock.setClosingBal(new Field62(option, fieldMatcher.group(2)));
                    break;

                case Field64.TAG:
                    textBlock.setClosingAvlBalance(new Field64(fieldMatcher.group(2)));
                    break;

                case Field65.TAG:
                    textBlock.setForwardAvlBalance(new Field65(fieldMatcher.group(2)));
                    break;

                default:
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT, field));
            }
        }

        return textBlock;
    }
}
