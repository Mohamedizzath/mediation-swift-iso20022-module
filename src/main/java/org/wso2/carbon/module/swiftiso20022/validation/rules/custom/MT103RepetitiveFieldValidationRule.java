package org.wso2.carbon.module.swiftiso20022.validation.rules.custom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;

/**
 * Validate repetitive fields.
 * Repetitive fields are accepted as an array of strings.
 * Check whether the value is String and String length.
 */
public class MT103RepetitiveFieldValidationRule implements ValidationRule {

    private final List<ValidatorContext> context;

    public MT103RepetitiveFieldValidationRule(List<ValidatorContext> context) {
        this.context = context;
    }

    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext context : this.context) {

            // validation happens only if the key is present
            if (payload.has(context.getFieldName())) {
                JSONArray repetitions = payload.getJSONArray(context.getFieldName());

                // if the key is present there should be at least one value
                if (repetitions.length() == 0) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, context.getFieldDisplayName()));
                }

                // each value is validated
                for (int i = 0; i < repetitions.length(); i++) {
                    String stringValue = repetitions.getString(i);

                    // value cannot be blank
                    if (stringValue.isBlank()) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(ConnectorConstants.ERROR_REPETITION_EMPTY,
                                        ++i, context.getFieldDisplayName()));
                    }

                    // value length cannot be longer than defined length
                    if (stringValue.length() > context.getFieldLength()) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(ConnectorConstants.ERROR_REPETITION_LENGTH,
                                        ++i, context.getFieldDisplayName(), context.getFieldLength()));
                    }
                }
            }
        }
        return new ValidationResult();
    }
}
