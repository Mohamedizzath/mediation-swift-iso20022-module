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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.MT940ParserConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 61.
 * <p>
 *     format: (Value Date)(Entry Date)(Debit/Credit Mark)(Funds Code)(Amount)[(Transaction type)(Identification Code)
 *     (Reference to Account Owner)//(Reference to Account Servicing Institution)CRLF(Supplementary Details)<br/>
 *     example: :61:2310011001RCD10,00FCHK304955//4958843 ADDITIONAL INFORMATION
 *              SUPPLEMENTARY DETAILS
 *     @see <a href="https://www2.swift.com/knowledgecentre/publications/
 *     us9m_20230720/2.0?topic=con_sfld_MaOrowQQEe2AI4OK6vBjrg_1576699121fld.htm">Tag 61</a>
 * </p>
 */
public class Field61 {
    public static final String TAG = "61";

    // Example - 231001
    private String valueDate;

    // Example - 1001
    private String entryDate;

    // Example - RC
    private String dcMark;

    // Example - D
    private String fundsCode;

    // Example - 10,00
    private String amount;

    // Example - F
    private String transactionType;

    // Example - CHK
    private String identificationCode;

    // Example - 304955
    private String refToAccountOwner;

    // Example - ADDITIONAL INFORMATION
    private String refToAccountServicingInstitution;

    // Example - SUPPLEMENTARY DETAILS
    private String supplementaryDetails;

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDcMark() {
        return dcMark;
    }

    public void setDcMark(String dcMark) {
        this.dcMark = dcMark;
    }

    public String getFundsCode() {
        return fundsCode;
    }

    public void setFundsCode(String fundsCode) {
        this.fundsCode = fundsCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getRefToAccountOwner() {
        return refToAccountOwner;
    }

    public void setRefToAccountOwner(String refToAccountOwner) {
        this.refToAccountOwner = refToAccountOwner;
    }

    public String getRefToAccountServicingInstitution() {
        return refToAccountServicingInstitution;
    }

    public void setRefToAccountServicingInstitution(String refToAccountServicingInstitution) {
        this.refToAccountServicingInstitution = refToAccountServicingInstitution;
    }

    public String getSupplementaryDetails() {
        return supplementaryDetails;
    }

    public void setSupplementaryDetails(String supplementaryDetails) {
        this.supplementaryDetails = supplementaryDetails;
    }

    /**
     * Method for set value date and return the instance.
     * @param valueDate     D/C mark of Field61
     * @return              Created instance of Field61
     */
    public Field61 withValueDate(String valueDate) {
        setValueDate(valueDate);
        return this;
    }

    /**
     * Method for set entry date and return the instance.
     * @param entryDate     Entry date of Field61
     * @return              Created instance of Field61
     */
    public Field61 withEntryDate(String entryDate) {
        setEntryDate(entryDate);
        return this;
    }

    /**
     * Method for set D/C mark and return the instance.
     * @param dcMark        D/C mark of Field61
     * @return              Created instance of Field61
     */
    public Field61 withDCMark(String dcMark) {
        setDcMark(dcMark);
        return this;
    }

    /**
     * Method for set funds code and return the instance.
     * @param fundsCode        Funds code of Field61
     * @return              Created instance of Field61
     */
    public Field61 withFundsCode(String fundsCode) {
        setFundsCode(fundsCode);
        return this;
    }

    /**
     * Method for set amount and return the instance.
     * @param amount        Amount of Field61
     * @return              Created instance of Field61
     */
    public Field61 withAmount(String amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Method for set transaction type and return the instance.
     * @param transactionType        Transaction type of Field61
     * @return              Created instance of Field61
     */
    public Field61 withTransactionType(String transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    /**
     * Method for set identification code and return the instance.
     * @param identificationCode      Identification code of Field61
     * @return                        Created instance of Field61
     */
    public Field61 withIdentificationCode(String identificationCode) {
        setIdentificationCode(identificationCode);
        return this;
    }

    /**
     * Method for set reference to account owner and return the instance.
     * @param refToAccountOwner       Reference to account owner code of Field61
     * @return                        Created instance of Field61
     */
    public Field61 withRefToAccountOwner(String refToAccountOwner) {
        setRefToAccountOwner(refToAccountOwner);
        return this;
    }

    /**
     * Method for set reference to account servicing institution and return the instance.
     * @param refToAccountServicingInstitution       Reference to account servicing institution of Field61
     * @return                                       Created instance of Field61
     */
    public Field61 withRefToAccountServicingInstitution(String refToAccountServicingInstitution) {
        setRefToAccountServicingInstitution(refToAccountServicingInstitution);
        return this;
    }

    /**
     * Method for set supplementary details and return the instance.
     * @param supplementaryDetails      Supplementary details of Field61
     * @return                          Created instance of Field61
     */
    public Field61 withSupplementaryDetails(String supplementaryDetails) {
        setSupplementaryDetails(supplementaryDetails);
        return this;
    }

    /**
     * Method for parse and get Field61 object.
     * @param field61String        String which contains value of Field61
     * @return                     Created instance of Field61
     * @throws MTMessageParsingException
     */
    public static Field61 parse(String field61String) throws MTMessageParsingException {
        Matcher field61Matcher = MT940ParserConstants.FIELD_61_REGEX_PATTERN.matcher(field61String);

        if (field61Matcher.matches()) {
            // Extract the Ref to account owner and Ref to account servicing institution
            String references = field61Matcher.group(8);

            String refToAccOwner = null;
            String refToAccServicingInstitute = null;

            if (references.contains(MT940ParserConstants.FIELD_61_REFS_DIVIDER)) {
                // String contains both reference
                String[] referencesList = references.split(MT940ParserConstants.FIELD_61_REFS_DIVIDER);

                if (referencesList.length != 2) {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                            Field61.TAG));
                }

                // Matching the ref to account owner
                Matcher field61RefToAccOwnerMatcher = MT940ParserConstants.FIELD_61_REFTOACCOWNER_REGEX
                        .matcher(referencesList[0]);
                if (!field61RefToAccOwnerMatcher.matches()) {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                            Field61.TAG));
                }
                refToAccOwner = referencesList[0];

                // Matching the ref to account servicing institution
                Matcher field61RefToAccServicingInstituteMatcher =
                        MT940ParserConstants.FIELD_61_REFTOACCSERVICEINSTITUTION_REGEX.matcher(referencesList[1]);
                if (!field61RefToAccServicingInstituteMatcher.matches()) {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                            Field61.TAG));
                }
                refToAccServicingInstitute = referencesList[1];
            } else {
                // String only contains account owner references and matching the ref to account owner
                Matcher field61RefToAccOwnerMatcher = MT940ParserConstants.FIELD_61_REFTOACCOWNER_REGEX
                        .matcher(references);

                if (!field61RefToAccOwnerMatcher.matches()) {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                            Field61.TAG));
                }

                refToAccOwner = references;
            }

            return new Field61().withValueDate(field61Matcher.group(1))
                    .withEntryDate(field61Matcher.group(2))
                    .withDCMark(field61Matcher.group(3))
                    .withFundsCode(field61Matcher.group(4))
                    .withAmount(field61Matcher.group(5))
                    .withTransactionType(field61Matcher.group(6))
                    .withIdentificationCode(field61Matcher.group(7))
                    .withRefToAccountOwner(refToAccOwner)
                    .withRefToAccountServicingInstitution(refToAccServicingInstitute)
                    .withSupplementaryDetails(field61Matcher.group(10));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field61.TAG));
        }
    }
}
