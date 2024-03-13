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

import java.util.List;

/**
 * Validate fields with arrays of strings.
 * Default case is a repetitive field.
 * If the field is a multiline field use the constructor with the boolean
 *
 */
public class MT103ArrayFieldValidationRule implements ValidationRule {

    private final List<ValidatorContext> contexts;

    private final boolean isMultilineField;

    // for repetitive fields
    public MT103ArrayFieldValidationRule(List<ValidatorContext> contexts) {
        this.contexts = contexts;
        this.isMultilineField = false;
    }

    // For multiline fields
    public MT103ArrayFieldValidationRule(List<ValidatorContext> contexts, boolean isMultilineField) {
        this.contexts = contexts;
        this.isMultilineField = isMultilineField;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext context : this.contexts) {

            // validation happens only if the key is present
            if (payload.has(context.getFieldName())) {
                JSONArray values = payload.getJSONArray(context.getFieldName());

                // if the key is present there should be at least one value
                if (values.length() == 0) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
                }

                if (isMultilineField) {
                    int linesAllowed = (int) context.getProperty(MT103Constants.LINES_ALLOWED_KEY);

                    if (values.length() > linesAllowed) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(ConnectorConstants.ERROR_LINE_COUNT,
                                        context.getFieldDisplayName(), linesAllowed));
                    }
                }

                // each value is validated
                for (int i = 0; i < values.length(); i++) {
                    String stringValue = values.getString(i);

                    // value cannot be blank
                    if (stringValue.isBlank()) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(isMultilineField ? ConnectorConstants.ERROR_LINE_EMPTY
                                                : ConnectorConstants.ERROR_REPETITION_EMPTY,
                                        ++i, context.getFieldDisplayName()));
                    }

                    // value length cannot be longer than defined length
                    if (isMultilineField) {

                        // multiline field line is compared against maximum line length
                        if (stringValue.length() > MT103Constants.MT103_TEXT_LINE_LENGTH) {
                            return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                    String.format(ConnectorConstants.ERROR_LINE_LENGTH,
                                            ++i, context.getFieldDisplayName(), context.getFieldLength()));
                        }
                    } else {

                        // repetitive field length is compared against defined length for that field
                        if (stringValue.length() > context.getFieldLength()) {
                            return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                    String.format(ConnectorConstants.ERROR_REPETITION_LENGTH,
                                            ++i, context.getFieldDisplayName(), context.getFieldLength()));
                        }
                    }
                }
            }
        }
        return new ValidationResult();
    }
}
