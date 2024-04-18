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
 * Model for beneficiary customer with no letter option in Text Block (Block 04).
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
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_59.htm">
 * Field 59</a>
 */
public class Field59 {

    public static final String TAG = "59";

    private String account;
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
     * Method to parse and get Field59 object.
     *
     * @param field59String String containing value of 59 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field59 parse(String field59String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> [/(Account)]
        //                                      (Details)
        Optional<Matcher> field59Matcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.PARTY_IDENTIFIER_NO_LETTER_OPTION_REGEX_PATTERN, field59String);

        if (field59Matcher.isPresent()) {

            Matcher matcher = field59Matcher.get();

            // group 1 -> /Account
            // group 2 -> Account
            // group 3 -> details
            return new Field59()
                    .withAccount(matcher.group(2))
                    // Details group -> "val1\nval2\n" -> ["val1", "val2"]
                    .withDetails(List.of(matcher.group(3).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.BENEFICIARY_CUSTOMER, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
