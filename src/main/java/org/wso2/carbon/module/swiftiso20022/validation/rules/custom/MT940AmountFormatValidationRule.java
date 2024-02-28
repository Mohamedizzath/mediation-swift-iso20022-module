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

import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.regex.Pattern;

/**
 * Currency Format Validation Rule.
 * Validates whether amount is in xxx,xxx,xxx.xxx format.
 */
public class MT940AmountFormatValidationRule extends ValidationRule {

    ValidatorContext context;
    private static final String RULE_NAME = "Amount Format Validation";

    public MT940AmountFormatValidationRule(ValidatorContext context) {

        this.context = context;
    }

    /**
     * Validate whether amount is in xxx,xxx,xxx.xxx format.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            Object value = payload.get(context.getFieldName());
            if (value instanceof String && !Pattern.matches(ConnectorConstants.AMOUNT_REGEX_PATTERN,
                    (String) value)) {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                context.getFieldDisplayName()));

            }
        }
        return new ValidationResult();
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
