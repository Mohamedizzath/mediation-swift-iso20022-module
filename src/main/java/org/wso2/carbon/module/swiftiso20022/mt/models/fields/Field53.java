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

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 53A -> Model for sender's correspondent with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:53A:ABNANL2A</dd>
 * </dl>
 * <p>
 * 53B -> Model for sender's correspondent with option B in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>[Location]</dd>
 *
 *    <dt>example:</dt>
 *    <dd>:53B:/DE12345678901234567890</dd>
 * </dl>
 * <p>
 * 53D -> Model for sender's correspondent with option D in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>(Party Identifier)</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:53D:FINANZBANK AG</dd>
 *     <dd>EISENSTADT</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_53A.htm">
 * Field 53A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_53B.htm">
 * Field 53B</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_53D.htm">
 * Field 53D</a>
 */
public class Field53 {

    public static final String OPTION_A_TAG = "53A";
    public static final String OPTION_B_TAG = "53B";
    public static final String OPTION_D_TAG = "53D";
    private static final Map<String, Pattern> REGEX_PATTERN = Map.of(
            OPTION_A_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_A_REGEX_PATTERN,
            OPTION_B_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_B_REGEX_PATTERN,
            OPTION_D_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_D_REGEX_PATTERN
    );
    private final ConnectorConstants.MTFieldDOption option;
    private String partyIdentifier;
    private String identifierCode;
    private String location;
    private List<String> details;

    public Field53(ConnectorConstants.MTFieldDOption option) {
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
    public Field53 withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field53 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to set location of the field and return the instance.
     *
     * @param location Location to be set.
     * @return object itself
     */
    public Field53 withLocation(String location) {
        setLocation(location);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field53 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field53 object.
     *
     * @param field53String String containing value of 53 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field53 parse(String field53String, String tag) throws MTMessageParsingException {

        Matcher field53Matcher = REGEX_PATTERN.get(tag).matcher(field53String);

        if (field53Matcher.matches()) {

            switch (tag) {
                case OPTION_A_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> Identifier Code
                    return new Field53(ConnectorConstants.MTFieldDOption.A)
                            .withPartyIdentifier(field53Matcher.group(3))
                            .withIdentifierCode(field53Matcher.group(4));

                case OPTION_B_TAG:
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> Location
                    return new Field53(ConnectorConstants.MTFieldDOption.B)
                            .withPartyIdentifier(field53Matcher.group(3))
                            .withLocation(field53Matcher.group(4));

                case OPTION_D_TAG:
                    // group 1 -> /Party Identifier
                    // group 2 is not assigned because of OR operator
                    // group 3 -> Party Identifier
                    // group 4 -> details
                    return new Field53(ConnectorConstants.MTFieldDOption.D)
                            .withPartyIdentifier(field53Matcher.group(3))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field53Matcher.group(4)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                default:
                    throw new MTMessageParsingException(String.format(
                            MTParserConstants.INVALID_OPTION_FOR_FIELD, MT103Constants.SENDERS_CORRESPONDENT));
            }

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.SENDERS_CORRESPONDENT, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
