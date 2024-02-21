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
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Mandatory Param Validation Rule.
 */
public class MandatoryParamValidationRule implements ValidationRule {

    private static final String RULE_NAME = "Mandatory Param Validation";
    List<ValidatorContext> mandatoryParamValidationContextList;

    public MandatoryParamValidationRule(ValidatorContext validationContext) {

        if (mandatoryParamValidationContextList == null) {
            mandatoryParamValidationContextList = new ArrayList<>();
        }
        this.mandatoryParamValidationContextList.add(validationContext);
    }

    /**
     * Validate whether the parameter is a mandatory param.
     * @return Error Model
     */
    @Override
    public ErrorModel validate() {
        for (ValidatorContext context : mandatoryParamValidationContextList) {
            Object value = context.getFieldValue();
            if (value instanceof String) {
                if (StringUtils.isBlank(value.toString())) {
                    return new ErrorModel(ConnectorConstants.ERROR_CODE_MISSING_PARAM,
                            String.format(ConnectorConstants.ERROR_MANDATORY_PARAM_MISSING, context.getFieldName()));
                }
            } else {
                if (value == null) {
                    return new ErrorModel(ConnectorConstants.ERROR_CODE_MISSING_PARAM,
                            String.format(ConnectorConstants.ERROR_MANDATORY_PARAM_MISSING, context.getFieldName()));
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
