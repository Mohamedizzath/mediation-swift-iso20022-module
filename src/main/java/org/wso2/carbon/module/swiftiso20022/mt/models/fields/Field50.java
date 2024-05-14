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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 50A -> Model for ordering customer with option A in Text Block (Block 04).
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
 * <p>
 * 50F -> Model for ordering customer with option F in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:  </dt>
 *     <dd>(Party Identifier)</dd>
 *     <dd>4*(Details)</dd>
 *
 *     <dt>example:</dt>
 *     <dd>:50F:NIDN/DE/121231234342</dd>
 *     <dd>1/MANN GEORG</dd>
 *     <dd>3/DE/DUSSELDORF</dd>
 *     <dd>6/DE/ABC BANK/1234578293</dd>
 * </dl>
 * <p>
 * 50K -> Model for ordering customer with option K in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Account)]</dd>
 *     <dd>4*(Details)</dd>
 * <p>
 *     <dt>example:</dt>
 *     <dd>:50K:/12345678</dd>
 *     <dd>JOHN DOE</dd>
 *     <dd>123, FAKE STREET</dd>
 *     <dd>FAKETOWN</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_50A.htm">
 * Field 50A</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_50F.htm">
 * Field 50F</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_50K.htm">
 * Field 50K</a>
 */

public class Field50 extends PartyIdentifier {

    public static final String TAG = "50";

    /**
     * List of options with current implementation.
     */
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_A, ConnectorConstants.OPTION_F, ConnectorConstants.OPTION_K);

    /**
     * Constructor to get the instance with the option. Cannot use constructor of the super class because this is
     * a special case.
     *
     * @param option single character which identify the option.
     * @param identifierCode    String specified in SWIFT party identifier format
     * @param details String array in character set x
     */
    public Field50(char option, String identifierCode, List<String> details) {
        super(option);
        setIdentifierCode(identifierCode);
        setDetails(details);
    }

    /**
     * Method to parse and get Field50 object.
     * Current implementations -> Option A,F and K
     *
     * @param option single character option of the field50String
     * @param field50String String containing value of 50 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field50 parse(char option, String field50String) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_50));
        }

        Matcher field50Matcher = MTParserConstants.FIELD_50_REGEX_PATTERN.matcher(field50String);

        if (field50Matcher.matches()) {
            // group 1 -> Account or Party Identifier with line break
            // group 2 -> Account or Party Identifier
            // group 4 -> Identifier Code
            // group 6 -> Details in format (Number)/(Name and Address)
            // group 8 -> Details in format (Name and Address)
            List<String> details = null;
            if (field50Matcher.group(6) != null) {
                details = Arrays.asList(field50Matcher.group(6).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN));
            } else if (field50Matcher.group(8) != null) {
                details = Arrays.asList(field50Matcher.group(8).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN));
            }

            Field50 field50 = new Field50(option, field50Matcher.group(4), details);

            if (option == ConnectorConstants.OPTION_F) {
                field50.setPartyIdentifier(field50Matcher.group(2));
            } else {
                field50.setAccount(field50Matcher.group(2));
            }

            return field50;
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.FIELD_50 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                    ConnectorConstants.TEXT_BLOCK));
        }
    }
}
