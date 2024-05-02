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
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants.MTFieldOption;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class Field50 {

    public static final String OPTION_A_TAG = "50A";
    public static final String OPTION_F_TAG = "50F";
    public static final String OPTION_K_TAG = "50K";
    private static final Map<String, Pattern> REGEX_PATTERN = new HashMap<>() {{
       put(OPTION_A_TAG, MTParserConstants.FIELD_50A_REGEX_PATTERN);
       put(OPTION_F_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_F_REGEX_PATTERN);
       put(OPTION_K_TAG, MTParserConstants.PARTY_IDENTIFIER_OPTION_K_REGEX_PATTERN);
    }};
    private final MTFieldOption option;

    /**
     * Constructor to get the instance with the option.
     *
     * @param option Option from enum {@link MTFieldOption}
     */
    public Field50(MTFieldOption option) {
        this.option = option;
    }

    // example: 293456-1254349-82
    private String account;

    // example: VISTUS31
    private String identifierCode;

    // example: NIDN/DE/121231234342
    private String partyIdentifier;

    // format: (number)/(name and address)
    // example for 50F: 1/MANN GEORG
    private List<String> details;

    public MTFieldOption getOption() {
        return option;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    /**
     * Method to set account of the field and return the instance.
     *
     * @param account Account to be set.
     * @return object itself
     */
    public Field50 withAccount(String account) {
        setAccount(account);
        return this;
    }

    /**
     * Method to set identifier code of the field and return the instance.
     *
     * @param identifierCode Identifier Code to be set.
     * @return object itself
     */
    public Field50 withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method to set party identifier of the field and return the instance.
     *
     * @param partyIdentifier Party Identifier to be set.
     * @return object itself
     */
    public Field50 withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field50 withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field50 object.
     *
     * @param field50String String containing value of 50 field in Text Block
     * @param tag           Tag of the value, to identify the options
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field50 parse(String field50String, String tag) throws MTMessageParsingException {

        Matcher field50Matcher = REGEX_PATTERN.get(tag).matcher(field50String);

        if (field50Matcher.matches()) {

            switch (tag) {
                case OPTION_A_TAG:
                    // group 1 -> /Account
                    // group 2 -> Account
                    // group 3 -> Identifier Code
                    return new Field50(MTFieldOption.A)
                            .withAccount(field50Matcher.group(2))
                            .withIdentifierCode(field50Matcher.group(3));

                case OPTION_F_TAG:
                    // group 1 -> Party Identifier
                    // group 2 -> details
                    return new Field50(MTFieldOption.F)
                            .withPartyIdentifier(field50Matcher.group(1))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field50Matcher.group(2)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                case OPTION_K_TAG:
                    // group 1 -> /Account
                    // group 2 -> Account
                    // group 3 -> details
                    return new Field50(MTFieldOption.K)
                            .withAccount(field50Matcher.group(2))
                            // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                            .withDetails(List.of(field50Matcher.group(3)
                                    .split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));

                default:
                    throw new MTMessageParsingException(String.format(
                            MTParserConstants.INVALID_OPTION_FOR_FIELD, MT103Constants.ORDERING_CUSTOMER));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_CUSTOMER, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
