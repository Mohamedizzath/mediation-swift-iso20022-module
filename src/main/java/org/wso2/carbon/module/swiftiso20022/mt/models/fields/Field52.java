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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 52A -> Model for ordering institution with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:52A:ABNANL2A</dd>
 * </dl>
 * <p>
 *
 * 52D -> Model for third reimbursement institution with option D in Text Block (Block 04).
 *
 * <dl>
 *       <dt>format:</dt>
 *       <dd>(Party Identifier)</dd>
 *       <dd>4*(Details)</dd>
 *
 *       <dt>example:</dt>
 *       <dd>:52D:FINANZBANK AG</dd>
 *       <dd>EISENSTADT</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_52A.htm">
 * Field 52A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_52D.htm">
 * Field 52D</a>
 */
public class Field52 {

    public static final String OPTION_A_TAG = "52A";
    public static final String OPTION_D_TAG = "52D";
    private static final Map<String, Pattern> REGEX_PATTERN = new HashMap<>() {{
        put(OPTION_A_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_A_REGEX_PATTERN);
        put(OPTION_D_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_D_REGEX_PATTERN);
    }};
    private final ConnectorConstants.MTFieldDOption option;
    private String partyIdentifier;
    private String identifierCode;
    private List<String> details;

    public Field52(ConnectorConstants.MTFieldDOption option) {
        this.option = option;
    }

    public ConnectorConstants.MTFieldDOption getOption() {
        return option;
    }

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

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    /**
     * Method to set party identifier of the field and return the instance.
     *
     * @param partyIdentifier Party Identifier to be set.
     * @return object itself
     */
    public Field52 withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field52 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field52 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field52 object.
     *
     * @param field52String String containing value of 52A field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field52 parse(String field52String, String tag) throws MTMessageParsingException {

        Matcher field52Matcher = REGEX_PATTERN.get(tag).matcher(field52String);

        if (field52Matcher.matches()) {

            switch (tag) {
                case OPTION_A_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> Identifier Code
                    return new Field52(ConnectorConstants.MTFieldDOption.A)
                            .withPartyIdentifier(field52Matcher.group(3))
                            .withIdentifierCode(field52Matcher.group(4));

                case OPTION_D_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> details
                    return new Field52(ConnectorConstants.MTFieldDOption.D)
                            .withPartyIdentifier(field52Matcher.group(3))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field52Matcher.group(4)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                default:
                    throw new MTMessageParsingException(String.format(
                            MTParserConstants.INVALID_OPTION_FOR_FIELD, MT103Constants.ORDERING_INSTITUTION));
            }

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
