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

import java.util.Optional;
import java.util.regex.Matcher;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

/**
 * Model for receiver's correspondent with option B in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>[/(Party Identifier)]</dd>
 *     <dd>[Location]</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_54B.htm">
 * Field 54B</a>
 */
public class Field54B {

    public static final String TAG = "54B";
    private String partyIdentifier;
    private String location;

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method to set party identifier of the field and return the instance.
     *
     * @param partyIdentifier Party Identifier to be set.
     * @return object itself
     */
    public Field54B withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set location of the field and return the instance.
     *
     * @param location Location to be set.
     * @return object itself
     */
    public Field54B withLocation(String location) {
        setLocation(location);
        return this;
    }

    /**
     * Method to parse and get Field54B object.
     *
     * @param field54BString String containing value of 54B field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field54B parse(String field54BString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> [/(Party Identifier)]
        //                                      [Location]
        Optional<Matcher> field54BMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_B_REGEX_PATTERN, field54BString);

        if (field54BMatcher.isPresent()) {

            Matcher matcher = field54BMatcher.get();

            // group 1 -> /Party Identifier
            // group 2 is not assigned because of OR operator
            // group 3 -> Party Identifier
            // group 4 -> Location
            return new Field54B()
                    .withPartyIdentifier(matcher.group(3))
                    .withLocation(matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.RECEIVERS_CORRESPONDENT, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
