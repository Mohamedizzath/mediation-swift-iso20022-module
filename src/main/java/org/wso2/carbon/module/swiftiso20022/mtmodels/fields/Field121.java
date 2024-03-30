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
 * Model for unique end to end reference in User Header Block (Block 03).
 */
public class Field121 extends Field {

    public static final String TAG = "121";

    // format -> xxxxxxxx-xxxx-4xxx-yxxxxxxxxxxxxxxx
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to set value of the field and return the instance.
     *
     * @param value Value to be set.
     * @return object itself
     */
    public Field121 value(String value) {
        setValue(value);
        return this;
    }
}
