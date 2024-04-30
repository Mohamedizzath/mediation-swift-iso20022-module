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

import java.util.regex.Matcher;

/**
 * Model for addressee information flag in User Header Block (Block 03).
 * <p>
 * format: (Crediting Time)(Debiting Time)(Country Code)(Reference)
 * example: {115:121413121413DEBANKDECDA123}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field115 {

    public static final String TAG = "115";

    // format: HHMMSS
    // example: 121413
    private String creditingTime;

    // format: HHMMSS
    // example: 121413
    private String debitingTime;

    // example: DE
    private String countryCode;

    // example: BANKDECDA123
    private String reference;

    public String getCreditingTime() {
        return creditingTime;
    }

    public void setCreditingTime(String creditingTime) {
        this.creditingTime = creditingTime;
    }

    public String getDebitingTime() {
        return debitingTime;
    }

    public void setDebitingTime(String debitingTime) {
        this.debitingTime = debitingTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Method to set crediting time of the field and return the instance.
     *
     * @param creditingTime Crediting time value to be set.
     * @return object itself
     */
    public Field115 withCreditingTime(String creditingTime) {
        setCreditingTime(creditingTime);
        return this;
    }

    /**
     * Method to set debiting time of the field and return the instance.
     *
     * @param debitingTime Debiting time value to be set.
     * @return object itself
     */
    public Field115 withDebitingTime(String debitingTime) {
        setDebitingTime(debitingTime);
        return this;
    }

    /**
     * Method to set country code of the field and return the instance.
     *
     * @param countryCode Country code value to be set.
     * @return object itself
     */
    public Field115 withCountryCode(String countryCode) {
        setCountryCode(countryCode);
        return this;
    }

    /**
     * Method to set reference of the field and return the instance.
     *
     * @param reference Reference value to be set.
     * @return object itself
     */
    public Field115 withReference(String reference) {
        setReference(reference);
        return this;
    }

    /**
     * Method to parse and get Field115 object.
     *
     * @param field115String String containing value of 115 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field115 parse(String field115String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Crediting Time)(Debiting Time)(Country Code)(Reference)
        Matcher field115Matcher = MTParserConstants.FIELD_115_REGEX_PATTERN.matcher(field115String);

        if (field115Matcher.matches()) {

            // group 1 -> Crediting Time
            // group 2 -> Debiting Time
            // group 3 -> Country Code
            // group 4 -> Reference
            return new Field115()
                    .withCreditingTime(field115Matcher.group(1))
                    .withDebitingTime(field115Matcher.group(2))
                    .withCountryCode(field115Matcher.group(3))
                    .withReference(field115Matcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.ADDRESSEE_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }
}
