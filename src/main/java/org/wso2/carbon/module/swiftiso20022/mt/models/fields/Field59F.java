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
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_59F.htm">
 * Field 59F</a>
 */
public class Field59F {

    public static final String TAG = "59F";

    private String account;

    // format: (number)/(name and address)
    // example: 1/MANN GEORG
    private List<String> details;

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

    /**
     * Method to set account of the field and return the instance.
     *
     * @param account Account to be set.
     * @return object itself
     */
    public Field59F withAccount(String account) {
        setAccount(account);
        return this;
    }

    /**
     * Method to set details of the field and return the instance.
     *
     * @param details Details to be set.
     * @return object itself
     */
    public Field59F withDetails(List<String> details) {
        setDetails(details);
        return this;
    }

    /**
     * Method to parse and get Field59F object.
     *
     * @param field59FString String containing value of 59F field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field59F parse(String field59FString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Party Identifier)
        //                                      (Details)
        Optional<Matcher> field59FMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_OPTION_F_REGEX_PATTERN, field59FString);

        if (field59FMatcher.isPresent()) {

            Matcher matcher = field59FMatcher.get();

            // group 1 -> Party Identifier
            // group 2 -> Details
            return new Field59F()
                    .withAccount(matcher.group(1))
                    // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                    .withDetails(List.of(matcher.group(2).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.ORDERING_CUSTOMER, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
