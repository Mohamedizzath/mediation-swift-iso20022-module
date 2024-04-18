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

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

/**
 * Model for ordering customer with option F in Text Block (Block 04).
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
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_50F.htm">
 * Field 50F</a>
 */
public class Field50F {

    public static final String TAG = "50F";

    // example: NIDN/DE/121231234342
    private String partyIdentifier;

    // format: (number)/(name and address)
    // example: 1/MANN GEORG
    private List<String> details;

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
     * Method to set party identifier of the field and return the instance.
     *
     * @param partyIdentifier Party Identifier to be set.
     * @return object itself
     */
    public Field50F withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field50F withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field50F object.
     *
     * @param field50FString String containing value of 50F field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field50F parse(String field50FString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Party Identifier)
        //                                      (Details)
        Optional<Matcher> field50FMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_F_REGEX_PATTERN, field50FString);

        if (field50FMatcher.isPresent()) {

            Matcher matcher = field50FMatcher.get();

            // group 1 -> Party Identifier
            // group 2 -> Details
            return new Field50F()
                    .withPartyIdentifier(matcher.group(1))
                    // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                    .withDetails(List.of(matcher.group(2).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_CUSTOMER, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
