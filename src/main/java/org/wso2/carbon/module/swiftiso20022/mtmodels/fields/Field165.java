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
 * Model for payment release information in User Header Block (Block 03).
 * <p>
 * format: /(Code)/(Information)
 * example: {165:/COD/information...}
 */
public class Field165 implements Field {

    public static final String TAG = "165";

    // example: COD
    private String code;

    // example: information....
    private String information;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Method to set code of the field and return the instance.
     *
     * @param code Code value to be set.
     * @return object itself
     */
    public Field165 withCode(String code) {
        setCode(code);
        return this;
    }

    /**
     * Method to set information of the field and return the instance.
     *
     * @param information Information to be set.
     * @return object itself
     */
    public Field165 withInformation(String information) {
        setInformation(information);
        return this;
    }
}
