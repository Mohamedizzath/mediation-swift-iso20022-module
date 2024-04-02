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
 * Model for payments control information in User Header Block (Block 03).
 * <p>
 * format: /(Code)/[Additional Information]
 * example: /FPO/additional information...
 */
public class Field434 implements Field {

    public static final String TAG = "434";

    // example: FPO
    private String code;

    // example: additional info
    private String additionalInformation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * Method to set code of the field and return the instance.
     *
     * @param code Code value to be set.
     * @return object itself
     */
    public Field434 withCode(String code) {
        setCode(code);
        return this;
    }

    /**
     * Method to set additional information of the field and return the instance.
     *
     * @param additionalInformation Additional information to be set.
     * @return object itself
     */
    public Field434 withAdditionalInformation(String additionalInformation) {
        setAdditionalInformation(additionalInformation);
        return this;
    }
}
