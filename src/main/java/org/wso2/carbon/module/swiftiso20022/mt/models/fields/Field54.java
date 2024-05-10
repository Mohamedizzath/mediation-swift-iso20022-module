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
public class Field54 extends PartyIdentifier {

    public static final String TAG = "54";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_B, ConnectorConstants.OPTION_D);

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public Field54(char option, String partyIdentifier, String identifierCode, String location, List<String> details) {
        super(option, partyIdentifier, identifierCode, location, details);
    }

    /**
     * Method to parse and get Field54 object.
     * Current implementations -> Option A, B and D
     *
     * @param field54String String containing value of 54 field in Text Block
     * @param option single character option of the field54String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field54 parse(String field54String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_54));
        }

        Matcher field54Matcher = MTParserConstants.PARTY_IDENTIFIER_REGEX_PATTERN.matcher(field54String);

        if (field54Matcher.matches()) {

            // group 1 -> Party Identifier with line break
            // group 2 -> Party Identifier
            // group 8 -> Identifier Code
            // group 11 -> Location
            // group 12 -> Details in format (Number)/(Name and Address)
            // group 14 -> Details in format (Name and Address)
            return new Field54(option, field54Matcher.group(2), field54Matcher.group(8), field54Matcher.group(11),
                    MTParserUtils.getDetailsAsList(field54Matcher));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.RECEIVERS_CORRESPONDENT, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
