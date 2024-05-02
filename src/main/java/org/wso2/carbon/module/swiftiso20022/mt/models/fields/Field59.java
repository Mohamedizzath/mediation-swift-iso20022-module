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
public class Field59 {

    public static final String NO_LETTER_OPTION_TAG = "59";
    public static final String OPTION_A_TAG = "59A";
    public static final String OPTION_F_TAG = "59F";
    private static final Map<String, Pattern> REGEX_PATTERN = new HashMap<>() {{
        put(NO_LETTER_OPTION_TAG, MTParserConstants.PARTY_IDENTIFIER_NO_LETTER_OPTION_REGEX_PATTERN);
        put(OPTION_A_TAG, MTParserConstants.FIELD_59A_REGEX_PATTERN);
        put(OPTION_F_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_F_REGEX_PATTERN);
    }};

    /**
     * Constructor to get the instance with the option.
     *
     * @param option Option from enum {@link ConnectorConstants.MTFieldOption}
     */
    private final ConnectorConstants.MTFieldOption option;
    private String account;
    private List<String> details;
    private String identifierCode;

    public Field59(ConnectorConstants.MTFieldOption option) {
        this.option = option;
    }

    public ConnectorConstants.MTFieldOption getOption() {
        return option;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    /**
     * Method to set account of the field and return the instance.
     *
     * @param account Account to be set.
     * @return object itself
     */
    public Field59 withAccount(String account) {
        setAccount(account);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field59 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field59 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to parse and get Field59 object.
     *
     * @param field59String String containing value of 59 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field59 parse(String field59String, String tag) throws MTMessageParsingException {

        Matcher field59Matcher = REGEX_PATTERN.get(tag).matcher(field59String);

        if (field59Matcher.matches()) {

            switch (tag) {
                case NO_LETTER_OPTION_TAG:
                    // group 1 -> /Account
                    // group 2 -> Account
                    // group 3 -> details
                    return new Field59(ConnectorConstants.MTFieldOption.NO_LETTER)
                            .withAccount(field59Matcher.group(2))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field59Matcher.group(3)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                case OPTION_A_TAG:
                    // group 1 -> /Account
                    // group 2 -> Account
                    // group 3 -> Identifier Code
                    return new Field59(ConnectorConstants.MTFieldOption.A)
                            .withAccount(field59Matcher.group(2))
                            .withIdentifierCode(field59Matcher.group(3));

                case OPTION_F_TAG:
                    // group 1 -> Party Identifier
                    // group 2 -> Details
                    return new Field59(ConnectorConstants.MTFieldOption.F)
                            .withAccount(field59Matcher.group(1))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field59Matcher.group(2)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                default:
                    throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_OPTION_FOR_FIELD,
                            MT103Constants.BENEFICIARY_CUSTOMER));
            }

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.BENEFICIARY_CUSTOMER, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
