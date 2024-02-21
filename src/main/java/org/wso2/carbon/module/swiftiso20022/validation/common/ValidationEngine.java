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

package org.wso2.carbon.module.swiftiso20022.validation.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.validation.rules.AlphaNumericParamValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.CurrencyFormatValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.DateFormatValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.MandatoryParamValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.NumericParamValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.OptionalParamValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.rules.ParameterLengthValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation Engine class to perform validation rules.
 */
public class ValidationEngine {

    private static final Logger logger = LoggerFactory.getLogger(ValidationEngine.class);

    static List<ValidationRule> ruleList;
    private static ValidationEngine validationEngine;
    public static synchronized ValidationEngine getInstance() {

        if (validationEngine == null) {
            validationEngine = new ValidationEngine();
        }
        ruleList = new ArrayList<>();
        return validationEngine;
    }

    public ValidationEngine addMandatoryParamValidationRule(ValidatorContext context) {
        ruleList.add(new MandatoryParamValidationRule(context));
        return this;
    }

    public ValidationEngine addMandatoryParamValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new MandatoryParamValidationRule(context)));
        return this;
    }

    public ValidationEngine addOptionalParamValidationRule(ValidatorContext context) {
        ruleList.add(new OptionalParamValidationRule(context));
        return this;
    }

    public ValidationEngine addOptionalParamValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new OptionalParamValidationRule(context)));
        return this;
    }

    public ValidationEngine addAlphaNumericParamValidationRule(ValidatorContext context) {
        ruleList.add(new AlphaNumericParamValidationRule(context));
        return this;
    }

    public ValidationEngine addAlphaNumericParamValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new AlphaNumericParamValidationRule(context)));
        return this;
    }

    public ValidationEngine addNumericParamValidationRule(ValidatorContext context) {
        ruleList.add(new NumericParamValidationRule(context));
        return this;
    }

    public ValidationEngine addNumericParamValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new NumericParamValidationRule(context)));
        return this;
    }

    public ValidationEngine addParameterLengthValidationRule(ValidatorContext context) {
        ruleList.add(new ParameterLengthValidationRule(context));
        return this;
    }

    public ValidationEngine addParameterLengthValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new ParameterLengthValidationRule(context)));
        return this;
    }

    public ValidationEngine addDateFormatValidationRule(ValidatorContext context) {
        ruleList.add(new DateFormatValidationRule(context));
        return this;
    }

    public ValidationEngine addDateFormatValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new DateFormatValidationRule(context)));
        return this;
    }

    public ValidationEngine addCurrencyFormatValidationRule(ValidatorContext context) {
        ruleList.add(new CurrencyFormatValidationRule(context));
        return this;
    }

    public ValidationEngine addCurrencyFormatValidationRules(List<ValidatorContext> contexts) {
        contexts.forEach(context -> ruleList.add(new CurrencyFormatValidationRule(context)));
        return this;
    }

    public ValidationEngine addCustomRule(ValidationRule rule) {
        ruleList.add(rule);
        return this;
    }

    /**
     * Recursively validate Validation Rule Set.
     *
     * @return validation results.
     */
    public ErrorModel validate() {

        for (ValidationRule rule : ruleList) {
            ErrorModel validationResult = rule.validate();
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("applicable validator %s invoked with context and returned %b",
                        rule.getDisplayName(), validationResult.isError()));
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s validator not applicable for current context",
                            rule.getDisplayName()));
                }
            }

            if (validationResult.isError()) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Stop on failure validator %s returned unsuccessful" +
                                    " validation therefore stopping further validation",
                            rule.getDisplayName()));
                }
                return validationResult;
            }
        }
        return new ErrorModel();
    }
}
