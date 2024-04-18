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
 * Model for instructed amount in Text Block (Block 04).
 * <p>
 * format: (Currency)(Amount)
 * <p>
 * example: USD1000,00
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_33B.htm">
 * Field 33B</a>
 */
public class Field33B {

    public static final String TAG = "33B";

    // example: USD
    private String currency;

    // example: 1000,00
    private String amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Method to set currency of the field and return the instance.
     *
     * @param currency Currency to be set.
     * @return object itself
     */
    public Field33B withCurrency(String currency) {
        setCurrency(currency);
        return this;
    }

    /**
     * Method to set amount of the field and return the instance.
     *
     * @param amount Amount to be set.
     * @return object itself
     */
    public Field33B withAmount(String amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Method to parse and get Field33B object.
     *
     * @param field33BString String containing value of 33B field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field33B parse(String field33BString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Currency)(Amount)
        Optional<Matcher> field33BMatcher = MTParserUtils.getRegexMatcher(
                MTParserConstants.FIELD_33B_REGEX_PATTERN, field33BString);

        if (field33BMatcher.isPresent()) {

            Matcher matcher = field33BMatcher.get();

            // group 1 -> Currency
            // group 2 -> Amount
            return new Field33B()
                    .withCurrency(matcher.group(1))
                    .withAmount(matcher.group(2));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.INSTRUCTED_AMOUNT, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
