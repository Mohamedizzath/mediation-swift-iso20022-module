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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;

/**
 * MT940 Statement Type Validation Rule.
 */
public class MT940StatementTypeValidationRule implements ValidationRule {

    ValidatorContext context;
    private static final int INDEX = 1;
    private static final int START_VALUE = 99;
    private static final int END_VALUE = 1000;

    private static final List<String> supportedFields = List.of(MT940Constants.CURRENT_STATEMENT_TYPE,
            MT940Constants.LAST_STATEMENT_TYPE);

    public MT940StatementTypeValidationRule(ValidatorContext context) {
        this.context = context;
    }

    /**
     * Validate whether the parameter statement type in MT940 is valid.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            Object statementType = payload.get(context.getFieldName());

            if (statementType instanceof String && StringUtils.isNotBlank(statementType.toString())) {
                boolean isSupported = supportedFields.stream().anyMatch(statementType::equals);
                if (!isSupported) {
                    return new ValidationResult(ConnectorConstants.ERROR_T53,
                            ConnectorConstants.ERROR_INVALID_STATEMENT_TYPE);
                }
            }
        }
        return new ValidationResult();
    }
}
