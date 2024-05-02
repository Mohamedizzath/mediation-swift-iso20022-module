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
 * 54A -> Model for receiver's correspondent with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:54A:IRVTUS3N</dd>
 * </dl>
 * <p>
 * 54B -> Model for receiver's correspondent with option B in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>[Location]</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:54B:/DE12345678901234567890</dd>
 * </dl>
 * <p>
 * 54D -> Model for receiver's correspondent with option D in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>(Party Identifier)</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:54D:FINANZBANK AG</dd>
 *     <dd>EISENSTADT</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_54A.htm">
 * Field 54A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_54B.htm">
 * Field 54B</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_54D.htm">
 * Field 54D</a>
 */
public class Field54 {

    public static final String OPTION_A_TAG = "54A";
    public static final String OPTION_B_TAG = "54B";
    public static final String OPTION_D_TAG = "54D";
    private static final Map<String, Pattern> REGEX_PATTERN = new HashMap<>() {{
        put(OPTION_A_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_A_REGEX_PATTERN);
        put(OPTION_B_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_B_REGEX_PATTERN);
        put(OPTION_D_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_D_REGEX_PATTERN);
    }};
    private final ConnectorConstants.MTFieldOption option;
    private String partyIdentifier;
    private String identifierCode;
    private String location;
    private List<String> details;

    /**
     * Constructor to get the instance with the option.
     *
     * @param option Option from enum {@link ConnectorConstants.MTFieldOption}
     */
    public Field54(ConnectorConstants.MTFieldOption option) {
        this.option = option;
    }

    public ConnectorConstants.MTFieldOption getOption() {
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
    public Field54 withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field54 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to set location of the field and return the instance.
     *
     * @param location Location to be set.
     * @return object itself
     */
    public Field54 withLocation(String location) {
        setLocation(location);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field54 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field54 object.
     *
     * @param field54String String containing value of 54 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field54 parse(String field54String, String tag) throws MTMessageParsingException {

        Matcher field54Matcher = REGEX_PATTERN.get(tag).matcher(field54String);

        if (field54Matcher.matches()) {

            switch (tag) {
                case OPTION_A_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 -> Party Identifier
                    // group 3 -> Identifier Code
                    return new Field54(ConnectorConstants.MTFieldOption.A)
                            .withPartyIdentifier(field54Matcher.group(2))
                            .withIdentifierCode(field54Matcher.group(3));

                case OPTION_B_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 -> Party Identifier
                    // group 3 -> Location
                    return new Field54(ConnectorConstants.MTFieldOption.B)
                            .withPartyIdentifier(field54Matcher.group(2))
                            .withLocation(field54Matcher.group(3));

                case OPTION_D_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 -> Party Identifier
                    // group 3 -> details
                    return new Field54(ConnectorConstants.MTFieldOption.D)
                            .withPartyIdentifier(field54Matcher.group(2))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field54Matcher.group(3)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
                default:
                    throw new MTMessageParsingException(String.format(
                            MTParserConstants.INVALID_OPTION_FOR_FIELD, MT103Constants.SENDERS_CORRESPONDENT));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.RECEIVERS_CORRESPONDENT, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
