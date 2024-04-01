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

package org.wso2.carbon.module.swiftiso20022.mtmodels.fields;

/**
 * Model for addressee information flag in User Header Block (Block 03).
 *
 * format: (Crediting Time)(Debiting Time)(Country Code)(Reference)
 */
public class Field115 extends Field {

    public static final String TAG = "115";

    // format: HHMMSS
    private String creditingTime;

    // format: HHMMSS
    private String debitingTime;

    // format: 2!a
    private String countryCode;

    // format: 16x
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
}
