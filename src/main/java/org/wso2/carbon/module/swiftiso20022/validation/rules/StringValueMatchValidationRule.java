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
import java.util.Map;


/**
 * Checks whether value matches with a predefined value set.
 */
public class StringValueMatchValidationRule implements ValidationRule {

    private final List<ValidatorContext> validationParamList;
    private final Map<String, List<String>> definedValueList;

    public StringValueMatchValidationRule(List<ValidatorContext> validationParamList,
                                          Map<String, List<String>> definedValueList) {
        this.validationParamList = validationParamList;
        this.definedValueList = definedValueList;
    }

    /**
     * Validate whether the parameter contains a value from a predefined value set.
     *
     * @param payload Payload to be validated.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext ctx: validationParamList) {
            if (payload.has(ctx.getFieldName())) {
                Object value = payload.get(ctx.getFieldName());
                List<String> definedValues = definedValueList.get(ctx.getFieldName());
                if (value instanceof String && definedValues.stream().noneMatch((value.toString()::equals))) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ctx.getFieldDisplayName()));
                }
            }
        }
        return new ValidationResult();
    }
}
