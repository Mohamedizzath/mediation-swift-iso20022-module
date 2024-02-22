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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;

/**
 * MT940 Transaction Indicator Validation Rule.
 */
public class MT940TransactionIndicatorValidationRule extends ValidationRule {

    private static final String RULE_NAME = "MT940 Transaction Indicator Validation";
    private static final List<String> supportedFields = List.of(ConnectorConstants.DEBIT,
            ConnectorConstants.CREDIT, ConnectorConstants.REV_DEBIT, ConnectorConstants.REV_CREDIT);

    public MT940TransactionIndicatorValidationRule(ValidatorContext context) {
        super(context);
    }

    /**
     * Validate whether the parameter Transaction indicator in MT940 is valid.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate() {
        ValidatorContext context = super.getContext();
        String debitCreditMark = context.getFieldValue().toString();
        boolean isSupported = supportedFields.stream().anyMatch(debitCreditMark::startsWith);
        if (!isSupported) {
            return new ValidationResult(ConnectorConstants.ERROR_T53,
                    String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                            ConnectorConstants.TRANSACTION_TYPE));
        }
        return new ValidationResult();
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
