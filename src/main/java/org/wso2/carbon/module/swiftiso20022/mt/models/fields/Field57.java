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
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 56A -> Model for account with institution with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:57A:IRVTUS3N</dd>
 * </dl>
 * <p>
 * 57B -> Model for account with institution with option B in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>[Location]</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:55B:/DE12345678901234567890</dd>
 * </dl>
 * <p>
 * 57C -> Model for intermediary institution with option C in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>/(Party Identifier)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>/293456-1254349-82</dd>
 * </dl>
 * <p>
 * 57D -> Model for intermediary institution with option D in Text Block (Block 04).
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
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_57A.htm">
 * Field 57A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_57B.htm">
 * Field 57B</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_57C.htm">
 * Field 57C</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_57D.htm">
 * Field 57D</a>
 */
public class Field57 extends PartyIdentifier {

    public static final String TAG = "57";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_B,
            ConnectorConstants.OPTION_C, ConnectorConstants.OPTION_D);

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public Field57(char option, String partyIdentifier, String identifierCode, String location, List<String> details) {
        super(option, partyIdentifier, identifierCode, location, details);
    }

    /**
     * Constructor specifically for the option C.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     */
    public Field57(char option, String partyIdentifier) {
        super(option);
        setPartyIdentifier(partyIdentifier);
    }

    /**
     * Method to parse and get Field57 object.
     * Current implementations -> Option A, B, C and D
     *
     * @param option single character option of the field57String
     * @param field57String String containing value of 57 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field57 parse(char option, String field57String) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_57));
        }

        Matcher field57Matcher = MTParserConstants.PARTY_IDENTIFIER_REGEX_PATTERN.matcher(field57String);

        if (field57Matcher.matches()) {
            // group 1 -> Party Identifier with line break
            // group 2 -> Party Identifier
            // group 8 -> Identifier Code
            // group 11 -> Location
            // group 12 -> Details in format (Number)/(Name and Address)
            // group 14 -> Details in format (Name and Address)

            // This is a special case where only one field is present, it should match with group 6 and only it.
            if (option == ConnectorConstants.OPTION_C) {
                if (field57Matcher.group(6) != null) {
                    return new Field57(option, field57Matcher.group(6));
                } else {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                            ConnectorConstants.FIELD_57 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                            ConnectorConstants.TEXT_BLOCK));
                }
            }

            return new Field57(option, field57Matcher.group(2), field57Matcher.group(8), field57Matcher.group(11),
                    MTParserUtils.getDetailsAsList(field57Matcher));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.FIELD_57 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                    ConnectorConstants.TEXT_BLOCK));
        }
    }
}
