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
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;

/**
 * MT940 Transaction Indicator Validation Rule.
 */
public class MT940IndicatorValidationRule implements ValidationRule {

    ValidatorContext context;
    private static final List<String> supportedFieldsForBalances = List.of(ConnectorConstants.DEBIT,
            ConnectorConstants.CREDIT);
    private static final List<String> supportedFieldsForTransactions = List.of(ConnectorConstants.DEBIT,
            ConnectorConstants.CREDIT, ConnectorConstants.REV_DEBIT, ConnectorConstants.REV_CREDIT);

    public MT940IndicatorValidationRule(ValidatorContext context) {
        this.context = context;
    }

    /**
     * Validate whether the parameter Transaction indicator in MT940 is valid.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            Object debitCreditMark = payload.get(context.getFieldName());

            if (!(debitCreditMark instanceof String)) {
                return new ValidationResult(ConnectorConstants.ERROR_T53,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                context.getFieldDisplayName()));
            }
            List<String> supportedFields = supportedFieldsForBalances;
            if (context.getFieldName().contains(MT940Constants.TRANSACTION)) {
                supportedFields = supportedFieldsForTransactions;
            }
            boolean isSupported = supportedFields.stream().anyMatch(debitCreditMark.toString()::startsWith);
            if (!isSupported) {
                return new ValidationResult(ConnectorConstants.ERROR_T53,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                context.getFieldDisplayName()));
            }
        }
        return new ValidationResult();
    }
}
