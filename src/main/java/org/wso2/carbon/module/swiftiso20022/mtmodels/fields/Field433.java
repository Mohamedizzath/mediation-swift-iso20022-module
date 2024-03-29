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
 * Model for sanctions screening information in User Header Block (Block 03).
 *
 * format: /(Code)/[Additional Information]
 */
public class Field433 extends Field {

    public static final String TAG = "433";

    // format: 3!a
    private String code;

    // format:
    private String additionalInformation;

    public String getCode() {
        return code;
    }

    public Field433 setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Field433 setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }
}
