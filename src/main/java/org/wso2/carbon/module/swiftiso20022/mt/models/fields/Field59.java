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
 * 59 -> Model for beneficiary customer with no letter option in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Account)]</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:59:/BE62510007547061</dd>
 *     <dd>JOHANN WILLEMS</dd>
 *     <dd>RUE JOSEPH II, 19</dd>
 *     <dd>1040 BRUSSELS</dd>
 * </dl>
 *
 * 59A -> Model for beneficiary customer with option A in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Account)]</dd>
 *     <dd>(Identifier Code)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:50A:/293456-1254349-82</dd>
 *     <dd>VISTUS31</dd>
 * </dl>
 *
 * Model for beneficiary customer with option F in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>(Account)</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:59F:/12345678</dd>
 *     <dd>1/DEPT OF PROMOTION OF SPICY FISH</dd>
 *     <dd>1/CENTER FOR INTERNATIONALISATION</dd>
 *     <dd>3/CN</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_59.htm">
 * Field 59</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_59A.htm">
 * Field 59A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_59F.htm">
 * Field 59F</a>
 */
public class Field59 extends PartyIdentifier {

    public static final String TAG = "59";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.NO_LETTER_OPTION, ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_F);

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param account String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public Field59(char option, String account, String identifierCode, String location, List<String> details) {
        super(option);
        setAccount(account);
        setIdentifierCode(identifierCode);
        setLocation(location);
        setDetails(details);
    }

    /**
     * Method to parse and get Field59 object.
     * Current implementations -> No_letter, Option A and F
     *
     * @param option single character option of the field59String
     * @param field59String String containing value of 59 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field59 parse(char option, String field59String) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_59));
        }

        Matcher field59Matcher = MTParserConstants.PARTY_IDENTIFIER_REGEX_PATTERN.matcher(field59String);

        if (field59Matcher.matches()) {
            // group 1 -> Party Identifier with line break
            // group 2 -> Party Identifier
            // group 8 -> Identifier Code
            // group 11 -> Location
            // group 12 -> Details in format (Number)/(Name and Address)
            // group 14 -> Details in format (Name and Address)
            return new Field59(option, field59Matcher.group(2), field59Matcher.group(8), field59Matcher.group(11),
                    MTParserUtils.getDetailsAsList(field59Matcher));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.FIELD_59 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                    ConnectorConstants.TEXT_BLOCK));
        }
    }
}
