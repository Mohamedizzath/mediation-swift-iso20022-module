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
 * Model for third reimbursement institution with option D in Text Block (Block 04).
 *
 * <dl>
 *     <dt>format:</dt>
 *     <dd>(Party Identifier)</dd>
 *     <dd>4*(Details)</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_52D.htm">
 * Field 52D</a>
 */
public class Field52D {

    public static final String TAG = "52D";
    private String partyIdentifier;
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
    public Field52D withPartyIdentifier(String partyIdentifier) {
        setPartyIdentifier(partyIdentifier);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field52D withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field52D object.
     *
     * @param field52DString String containing value of 52D field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field52D parse(String field52DString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> [/(Party Identifier)]
        //                                      (Details)
        Optional<Matcher> field52DMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_D_REGEX_PATTERN, field52DString);

        if (field52DMatcher.isPresent()) {

            Matcher matcher = field52DMatcher.get();

            // group 1 -> /Party Identifier
            // group 2 -> Party Identifier
            // group 3 -> Details
            return new Field52D()
                    .withPartyIdentifier(matcher.group(2))
                    // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                    .withDetails(List.of(matcher.group(3).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_INSTITUTION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
