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
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.utils.ValidatorUtils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * MT940 Transaction Type Validation Rule.
 */
public class MT940TransactionTypeValidationRule implements ValidationRule {

    private static final String RULE_NAME = "MT940 Transaction Type Validation";
    private static final int INDEX = 1;
    private static final int START_VALUE = 99;
    private static final int END_VALUE = 1000;
    List<ValidatorContext> mt940TransactionTypeValidationContextList;

    public MT940TransactionTypeValidationRule(ValidatorContext validationContext) {

        if (mt940TransactionTypeValidationContextList == null) {
            mt940TransactionTypeValidationContextList = new ArrayList<>();
        }
        this.mt940TransactionTypeValidationContextList.add(validationContext);
    }

    /**
     * Validate whether the parameter Transaction type in MT940 is valid.
     * @return Error Model
     */
    @Override
    public ErrorModel validate() {
        for (ValidatorContext context : mt940TransactionTypeValidationContextList) {
            String transactionType = context.getFieldValue().toString();
            if (!transactionType.startsWith(ConnectorConstants.SWIFT_TRANSFER) &&
                    !transactionType.startsWith(ConnectorConstants.NON_SWIFT_TRANSFER) &&
                    !transactionType.startsWith(ConnectorConstants.FIRST_ADVICE)) {
                return new ErrorModel(ConnectorConstants.ERROR_T53,
                        ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
            }

            if (transactionType.startsWith(ConnectorConstants.SWIFT_TRANSFER)) {
                String identificationCode = transactionType.substring(INDEX);
                if (!ValidatorUtils.isNumber(identificationCode)) {
                    return new ErrorModel(ConnectorConstants.ERROR_T53,
                            ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
                }

                if (!(Integer.parseInt(identificationCode) > START_VALUE &&
                        Integer.parseInt(identificationCode) < END_VALUE)) {
                    return new ErrorModel(ConnectorConstants.ERROR_T18,
                            ConnectorConstants.ERROR_TRANS_TYPE_INVALID);
                }
            }
        }
        return new ErrorModel();
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
