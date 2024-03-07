package org.wso2.carbon.module.swiftiso20022.validation.rules.custom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

/**
 * Validate multiline fields.
 * Check whether the value is String and String length.
 */
public class MT103MultilineFieldValidationRule implements ValidationRule {

    private final ValidatorContext context;
    private final int linesAllowed;

    public MT103MultilineFieldValidationRule(ValidatorContext context, int linesAllowed) {
        this.context = context;
        this.linesAllowed = linesAllowed;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            JSONArray lines = payload.getJSONArray(context.getFieldName());
            if (lines.length() == 0) {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
            }
            if (lines.length() > linesAllowed) {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(
                                ConnectorConstants.ERROR_LINE_COUNT, context.getFieldDisplayName(), linesAllowed));
            }
            for (int i = 0; i < lines.length(); i++) {
                String line = lines.getString(i);
                if (line.isBlank()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_LINE,
                            String.format(ConnectorConstants.ERROR_LINE_EMPTY,
                                    ++i, context.getFieldDisplayName()));
                }
                if (line.length() > context.getFieldLength()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_LINE,
                            String.format(ConnectorConstants.ERROR_LINE_LENGTH,
                                    ++i, context.getFieldDisplayName(), context.getFieldLength()));
                }
            }

        }
        return new ValidationResult();
    }
}

