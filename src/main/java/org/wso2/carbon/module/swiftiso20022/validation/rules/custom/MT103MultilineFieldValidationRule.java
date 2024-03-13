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
 * Validate multiline fields.
 * Multiline fields are accepted as an array of strings.
 * Check whether the value is String and String length.
 */
public class MT103MultilineFieldValidationRule implements ValidationRule {

    private final List<ValidatorContext> contexts;

    public MT103MultilineFieldValidationRule(List<ValidatorContext> contexts) {
        this.contexts = contexts;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext context : this.contexts) {

            // validation happens only if the key is present
            if (payload.has(context.getFieldName())) {
                JSONArray lines = payload.getJSONArray(context.getFieldName());

                // if the key is present there should be at least one value
                if (lines.length() == 0) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
                }

                // allowed lines count should be available as a property in validation context
                int linesAllowed = (int) context.getProperty(MT103Constants.LINES_ALLOWED_KEY);

                // check whether provided lines count exceed the defined line count
                if (lines.length() > linesAllowed) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(
                                    ConnectorConstants.ERROR_LINE_COUNT, context.getFieldDisplayName(), linesAllowed));
                }

                // each value is validated
                for (int i = 0; i < lines.length(); i++) {
                    String line = lines.getString(i);

                    // value cannot be blank
                    if (line.isBlank()) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(ConnectorConstants.ERROR_LINE_EMPTY,
                                        ++i, context.getFieldDisplayName()));
                    }

                    // value length cannot be longer than defined text line length
                    // all multiline fields in MT103 has the same length
                    if (line.length() > MT103Constants.MT103_TEXT_LINE_LENGTH) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(ConnectorConstants.ERROR_LINE_LENGTH,
                                        ++i, context.getFieldDisplayName(), context.getFieldLength()));
                    }
                }

            }
        }
        return new ValidationResult();
    }
}

