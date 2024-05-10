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
public class Field52 extends PartyIdentifier {

    public static final String TAG = "52";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_D);

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public Field52(char option, String partyIdentifier, String identifierCode, String location, List<String> details) {
        super(option, partyIdentifier, identifierCode, location, details);
    }

    /**
     * Method to parse and get Field52 object.
     * Current implementations -> Option A and D
     *
     * @param field52String String containing value of 52 field in Text Block
     * @param option single character option of the field52String
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field52 parse(String field52String, char option) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_52));
        }

        Matcher field52Matcher = MTParserConstants.PARTY_IDENTIFIER_REGEX_PATTERN.matcher(field52String);

        if (field52Matcher.matches()) {

            // group 1 -> Party Identifier with line break
            // group 2 -> Party Identifier
            // group 6 -> Identifier Code
            // group 9 -> Location
            // group 10 -> Details in format (Number)/(Name and Address)
            // group 12 -> Details in format (Name and Address)
            return new Field52(option, field52Matcher.group(2), field52Matcher.group(8), field52Matcher.group(11),
                    MTParserUtils.getDetailsAsList(field52Matcher));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
