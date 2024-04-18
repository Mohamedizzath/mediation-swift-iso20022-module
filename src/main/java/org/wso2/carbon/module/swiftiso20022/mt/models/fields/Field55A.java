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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import java.util.Optional;
import java.util.regex.Matcher;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

/**
 * Model for third reimbursement institution with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:55A:IRVTUS3N</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_55A.htm">
 * Field 55A</a>
 */
public class Field55A {

    public static final String TAG = "55A";
    private String partyIdentifier;
    private String identifierCode;

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    /**
     * Method to set party identifier of the field and return the instance.
     *
     * @param partyIdentifier Party Identifier to be set.
     * @return object itself
     */
    public Field55A withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field55A withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to parse and get Field55A object.
     *
     * @param field55AString String containing value of 55A field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field55A parse(String field55AString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> [/(Party Identifier)]
        //                                      (Identifier Code)
        Optional<Matcher> field55AMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_A_REGEX_PATTERN, field55AString);

        if (field55AMatcher.isPresent()) {

            Matcher matcher = field55AMatcher.get();

            // group 1 -> /Party Identifier
            // group 2 is not assigned because of OR operator
            // group 3 -> Party Identifier
            // group 4 -> Identifier Code
            return new Field55A()
                    .withPartyIdentifier(matcher.group(3))
                    .withIdentifierCode(matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.THIRD_REIMBURSEMENT_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
