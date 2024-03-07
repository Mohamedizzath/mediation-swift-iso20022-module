/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.validation.rules.custom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

/**
 * Validate repetitive fields.
 * Check whether the value is String and String length.
 */
public class MT103RepetitiveFieldValidationRule implements ValidationRule {

    private final ValidatorContext context;

    public MT103RepetitiveFieldValidationRule(ValidatorContext context) {
        this.context = context;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            JSONArray repetitions = payload.getJSONArray(context.getFieldName());
            if (repetitions.length() == 0) {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
            }
            for (int i = 0; i < repetitions.length(); i++) {
                String value = repetitions.getString(i);
                String stringValue = (String) value;
                if (stringValue.isBlank()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_REPETITION_EMPTY,
                                    ++i, context.getFieldDisplayName()));
                }
                if (stringValue.length() > context.getFieldLength()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_REPETITION_LENGTH,
                                    ++i, context.getFieldDisplayName(), context.getFieldLength()));
                }
            }
        }
        return new ValidationResult();
    }
}
