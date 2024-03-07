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
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

/**
 * Validate entity fields.
 * Check option and details of the entity.
 */
public class MT103EntityFieldValidationRule implements ValidationRule {

    private final ValidatorContext context;

    public MT103EntityFieldValidationRule(ValidatorContext context) {
        this.context = context;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            JSONObject entity = payload.getJSONObject(context.getFieldName());
            if (entity.has(MT103Constants.MT103_ENTITY_OPTION)) {
                String option = entity.getString(MT103Constants.MT103_ENTITY_OPTION);
                if (option.length() > 1) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_OPTION,
                            String.format(
                                    MT103Constants.ERROR_INVALID_ENTITY_OPTION, context.getFieldDisplayName()));
                }
            } else {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_OPTION,
                        String.format(MT103Constants.ERROR_EMPTY_ENTITY_OPTION, context.getFieldDisplayName()));
            }
            if (entity.has(MT103Constants.MT103_ENTITY_DETAILS)) {
                JSONArray details = entity.getJSONArray(MT103Constants.MT103_ENTITY_DETAILS);
                if (details.length() == 0) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(
                                    MT103Constants.ERROR_EMPTY_ENTITY_DETAILS, context.getFieldDisplayName()));
                }
                if (details.length() > MT103Constants.MT103_ENTITY_DETAILS_LINE_COUNT) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_LINE_COUNT,
                            String.format(MT103Constants.ERROR_ENTITY_DETAIL_LINE_COUNT,
                                    context.getFieldDisplayName()));
                }
                for (int i = 0; i < details.length(); i++) {
                    String line = details.getString(i);
                    if (line.isBlank()) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_LINE,
                                String.format(MT103Constants.ERROR_EMPTY_ENTITY_DETAIL_LINE,
                                        ++i, context.getFieldDisplayName()));
                    }
                    if (line.length() > MT103Constants.MT103_TEXT_LINE_LENGTH) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_LINE,
                                String.format(MT103Constants.ERROR_ENTITY_DETAIL_LINE_LENGTH,
                                        ++i, context.getFieldDisplayName()));
                    }

                }
            } else {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_MISSING_PARAM,
                        String.format(MT103Constants.ERROR_EMPTY_ENTITY_DETAILS, context.getFieldDisplayName()));
            }
        }
        return new ValidationResult();
    }
}
