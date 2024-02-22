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

import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

/**
 * Numeric Param Validation Rule.
 */
public class NumericParamValidationRule extends ValidationRule {

    private static final String RULE_NAME = "Numeric Param Validation";

    public NumericParamValidationRule(ValidatorContext context) {
        super(context);
    }

    /**
     * Validate whether the parameter is a numeric param.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate() {
        ValidatorContext ctx = super.getContext();
        if (!StringUtils.isNumeric(ctx.getFieldValue().toString())) {
            return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                    String.format(ConnectorConstants.ERROR_NOT_NUMERIC, ctx.getFieldName()));
        }
        return new ValidationResult();
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
