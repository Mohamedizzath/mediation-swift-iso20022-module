package org.wso2.carbon.module.swiftiso20022.validation.rules.custom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

/**
 * Validate repetitive fields.
 * Check whether the value is String and String length.
 */
public class MT103RepetitiveFieldValidationRule implements ValidationRule {

    private final ValidatorContext context;

    public MT103RepetitiveFieldValidationRule(ValidatorContext context) {
        this.context = context;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        if (payload.has(context.getFieldName())) {
            JSONArray repetitions = payload.getJSONArray(context.getFieldName());
            if (repetitions.length() == 0) {
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
            }
            for (int i = 0; i < repetitions.length(); i++) {
                String value = repetitions.getString(i);
                String stringValue = (String) value;
                if (stringValue.isBlank()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_REPETITION_EMPTY,
                                    ++i, context.getFieldDisplayName()));
                }
                if (stringValue.length() > context.getFieldLength()) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_REPETITION_LENGTH,
                                    ++i, context.getFieldDisplayName(), context.getFieldLength()));
                }
            }
        }
        return new ValidationResult();
    }
}
