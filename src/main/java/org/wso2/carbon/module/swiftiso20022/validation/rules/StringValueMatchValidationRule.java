package org.wso2.carbon.module.swiftiso20022.validation.rules;

import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.util.List;
import java.util.Map;


/**
 * Checks whether value matches with a predefined value set.
 */
public class StringValueMatchValidationRule implements ValidationRule {

    private final List<ValidatorContext> validationParamList;
    private final Map<String, List<String>> definedValueList;

    public StringValueMatchValidationRule(List<ValidatorContext> validationParamList,
                                          Map<String, List<String>> definedValueList) {
        this.validationParamList = validationParamList;
        this.definedValueList = definedValueList;
    }

    /**
     * Validate whether the parameter contains a value from a predefined value set.
     *
     * @param payload Payload to be validated.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        for (ValidatorContext ctx: validationParamList) {
            if (payload.has(ctx.getFieldName())) {
                Object value = payload.get(ctx.getFieldName());
                List<String> definedValues = definedValueList.get(ctx.getFieldName());
                if (value instanceof String && definedValues.stream().noneMatch((value.toString()::equals))) {
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_PARAMETER_INVALID, ctx.getFieldDisplayName()));
                }
            }
        }
        return new ValidationResult();
    }
}
