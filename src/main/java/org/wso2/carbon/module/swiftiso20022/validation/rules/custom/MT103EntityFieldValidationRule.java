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
 * Entity field refers to fields which identifies an entity e.x: institution, individual
 * Entity fields are accepted as an object with option and details keys.
 * option is a string.
 * details is an array of strings.
 */
public class MT103EntityFieldValidationRule implements ValidationRule {

    private final List<ValidatorContext> contexts;

    public MT103EntityFieldValidationRule(List<ValidatorContext> context) { this.contexts = context; }

    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext context : contexts) {

            // validation happens only if the key is present
            if (payload.has(context.getFieldName())) {

                // entity is accepted as a JSON object
                JSONObject entity = payload.getJSONObject(context.getFieldName());

                // check whether the option is provided
                if (entity.has(MT103Constants.MT103_ENTITY_OPTION)) {

                    // option is a string
                    String option = entity.getString(MT103Constants.MT103_ENTITY_OPTION);

                    // option can either be an empty string or a single character
                    if (option.length() > 1) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_OPTION,
                                String.format(
                                        MT103Constants.ERROR_INVALID_ENTITY_OPTION, context.getFieldDisplayName()));
                    }
                } else {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_OPTION,
                            String.format(MT103Constants.ERROR_EMPTY_ENTITY_OPTION, context.getFieldDisplayName()));
                }

                // check whether the details are provided
                if (entity.has(MT103Constants.MT103_ENTITY_DETAILS)) {

                    // details is an array of strings
                    JSONArray details = entity.getJSONArray(MT103Constants.MT103_ENTITY_DETAILS);

                    // if the key is present there should be at least one value
                    if (details.length() == 0) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                                String.format(
                                        MT103Constants.ERROR_EMPTY_ENTITY_DETAILS, context.getFieldDisplayName()));
                    }

                    // line count should not exceed defined line count
                    if (details.length() > MT103Constants.MT103_ENTITY_DETAILS_LINE_COUNT) {
                        return new ValidationResult(ConnectorConstants.ERROR_CODE_LINE_COUNT,
                                String.format(MT103Constants.ERROR_ENTITY_DETAIL_LINE_COUNT,
                                        context.getFieldDisplayName()));
                    }

                    // each value is validated
                    for (int i = 0; i < details.length(); i++) {
                        String line = details.getString(i);

                        // value cannot be blank
                        if (line.isBlank()) {
                            return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_LINE,
                                    String.format(MT103Constants.ERROR_EMPTY_ENTITY_DETAIL_LINE,
                                            ++i, context.getFieldDisplayName()));
                        }

                        // value length cannot be longer than defined text line length
                        // all entity details line in MT103 has the same length
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
        }
        return new ValidationResult();
    }
}
