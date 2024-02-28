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

package org.wso2.carbon.module.swiftiso20022.validation.rules;

import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

/**
 * MT Character Set X Validation Rule.
 * Can contain [a-zA-Z0-9\\n/-?:().,'+]
 */
public class MTCharacterSetXValidationRule extends ValidationRule {

    private final List<ValidatorContext> validationParamList;
    private static final String RULE_NAME = "MT Character Set X Validation";
    String regex = "[a-zA-Z0-9-/n?:().,'+/]{1,}";

    public MTCharacterSetXValidationRule(List<ValidatorContext> validationParamList) {
        this.validationParamList = validationParamList;
    }

    /**
     * Validate whether the parameter is an MT character set X param.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext ctx : validationParamList) {
            if (payload.has(ctx.getFieldName())) {
                Object value = payload.get(ctx.getFieldName());
                if (value instanceof String && !Pattern.matches(regex, (String) value)) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_NOT_CHARACTER_SET_X,
                                    ctx.getFieldDisplayName()));
                }
            }
        }
        return new ValidationResult();
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
