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
 * 55A -> Model for third reimbursement institution with option A in Text Block (Block 04).
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
 * 55B -> Model for third reimbursement institution with option B in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>[Location]</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:55B:/DE12345678901234567890</dd>
 * </dl>
 *
 * 55D -> Model for receiver's correspondent with option D in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>(Party Identifier)</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:55D:FINANZBANK AG</dd>
 *     <dd>EISENSTADT</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_55A.htm">
 * Field 55A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_55B.htm">
 * Field 55B</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_55D.htm">
 * Field 55D</a>
 */
public class Field55 {

    public static final String OPTION_A_TAG = "55A";
    public static final String OPTION_B_TAG = "55B";
    public static final String OPTION_D_TAG = "55D";
    private static final Map<String, Pattern> REGEX_PATTERN = new HashMap<>() {{
        put(OPTION_A_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_A_REGEX_PATTERN);
        put(OPTION_B_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_B_REGEX_PATTERN);
        put(OPTION_D_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_D_REGEX_PATTERN);
    }};
    private final ConnectorConstants.MTFieldDOption option;
    private String partyIdentifier;
    private String identifierCode;
    private String location;
    private List<String> details;
    public Field55(ConnectorConstants.MTFieldDOption option) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    public Field55 withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field55 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to set location of the field and return the instance.
     *
     * @param location Location to be set.
     * @return object itself
     */
    public Field55 withLocation(String location) {
        setLocation(location);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field55 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field55 object.
     *
     * @param field55String String containing value of 55 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field55 parse(String field55String, String tag) throws MTMessageParsingException {

        Matcher field55Matcher = REGEX_PATTERN.get(tag).matcher(field55String);

        if (field55Matcher.matches()) {

            switch (tag) {
                case OPTION_A_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> Identifier Code
                    return new Field55(ConnectorConstants.MTFieldDOption.A)
                            .withPartyIdentifier(field55Matcher.group(3))
                            .withIdentifierCode(field55Matcher.group(4));

                case OPTION_B_TAG:
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> Location
                    return new Field55(ConnectorConstants.MTFieldDOption.B)
                            .withPartyIdentifier(field55Matcher.group(3))
                            .withLocation(field55Matcher.group(4));

                case OPTION_D_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> details
                    return new Field55(ConnectorConstants.MTFieldDOption.D)
                            .withPartyIdentifier(field55Matcher.group(3))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field55Matcher.group(4)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                default:
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD,
                            MT103Constants.THIRD_REIMBURSEMENT_INSTITUTION));
            }

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.THIRD_REIMBURSEMENT_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
