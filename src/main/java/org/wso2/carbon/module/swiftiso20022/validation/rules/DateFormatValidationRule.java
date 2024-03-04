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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Date Format Validation Rule.
 */
public class DateFormatValidationRule implements ValidationRule {

    private static final Log log = LogFactory.getLog(DateFormatValidationRule.class);
    private final List<ValidatorContext> validationParamList;

    public DateFormatValidationRule(List<ValidatorContext> validationParamList) {

        this.validationParamList = validationParamList;
    }

    /**
     * Validate whether the date is in the correct format.
     *
     * @param payload    Payload to be validated.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate(JSONObject payload) {
        DateFormat formatter = new SimpleDateFormat(ConnectorConstants.DATE_TIME_FORMAT);
        for (ValidatorContext ctx : validationParamList) {
            if (payload.has(ctx.getFieldName())) {
                String value = payload.getString(ctx.getFieldName());
                try {
                    formatter.parse(value);
                    return new ValidationResult();
                } catch (ParseException e) {
                    log.error("Error while parsing the date time", e);
                    return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                            String.format(ConnectorConstants.ERROR_DATE_INVALID, ctx.getFieldDisplayName()));
                }
            }
        }
        return new ValidationResult();
    }
}
