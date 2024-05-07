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
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 56A -> Model for intermediary institution with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:56A:IRVTUS3N</dd>
 * </dl>
 *
 * 56C -> Model for intermediary institution with option C in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>/(Party Identifier)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:56C:/IRVTUS3NXXX</dd>
 * </dl>
 *
 * 56D -> Model for intermediary institution with option D in Text Block (Block 04).
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
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_56A.htm">
 * Field 56A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_56C.htm">
 * Field 56C</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_56D.htm">
 * Field 56D</a>
 */
public class Field56 extends PartyIdentifier {

    public static final String OPTION_A_TAG = "56A";
    public static final String OPTION_C_TAG = "56C";
    public static final String OPTION_D_TAG = "56D";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_C, ConnectorConstants.OPTION_D);

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public Field56(char option, String partyIdentifier, String identifierCode, String location, List<String> details) {
        super(option, partyIdentifier, identifierCode, location, details);
    }

    /**
     * Constructor specifically for the option C.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     */
    public Field56(char option, String partyIdentifier) {
        super(option);
        setPartyIdentifier(partyIdentifier);
    }

    /**
     * Method to parse and get Field56 object.
     * Current implementations -> Option A, C and D
     *
     * @param field56String String containing value of 56A field in Text Block
     * @param option single character option of the field56String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field56 parse(String field56String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_56));
        }

        Matcher field56Matcher = MTParserConstants.PARTY_IDENTIFIER_REGEX_PATTERN.matcher(field56String);

        if (field56Matcher.matches()) {

            // group 1 -> Party Identifier with line break
            // group 2 -> Party Identifier
            // group 8 -> Identifier Code
            // group 11 -> Location
            // group 12 -> Details in format (Number)/(Name and Address)
            // group 14 -> Details in format (Name and Address)

            if (option == ConnectorConstants.OPTION_C) {
                // This is a special case where only one field is present, it should match with group 6 and only it.
                if (field56Matcher.group(6) != null) {
                    return new Field56(option, field56Matcher.group(6));
                } else {
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                            MT103Constants.INTERMEDIARY_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
                }
            }

            return new Field56(option, field56Matcher.group(2), field56Matcher.group(8), field56Matcher.group(11),
                    MTParserUtils.getDetailsAsList(field56Matcher));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.INTERMEDIARY_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
