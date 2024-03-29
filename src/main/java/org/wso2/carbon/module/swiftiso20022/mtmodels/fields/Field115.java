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

    public Field115 setCreditingTime(String creditingTime) {
        this.creditingTime = creditingTime;
        return this;
    }

    public String getDebitingTime() {
        return debitingTime;
    }

    public Field115 setDebitingTime(String debitingTime) {
        this.debitingTime = debitingTime;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Field115 setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public Field115 setReference(String reference) {
        this.reference = reference;
        return this;
    }
}
