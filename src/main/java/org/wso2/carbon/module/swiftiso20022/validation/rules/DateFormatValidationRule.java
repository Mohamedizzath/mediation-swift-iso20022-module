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
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationRule;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidatorContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Date Format Validation Rule.
 */
public class DateFormatValidationRule extends ValidationRule {

    private static final Log log = LogFactory.getLog(DateFormatValidationRule.class);
    private static final String RULE_NAME = "Date Format Validation";

    public DateFormatValidationRule(ValidatorContext context) {
        super(context);
    }

    /**
     * Validate whether the date is in the correct format.
     * @return Validation Result
     */
    @Override
    public ValidationResult validate() {
        ValidatorContext ctx = super.getContext();
        DateFormat formatter = new SimpleDateFormat(ConnectorConstants.DATE_TIME_FORMAT);
        try {
            formatter.parse(ctx.getFieldValue().toString());
            return new ValidationResult();
        } catch (ParseException e) {
            log.error("Error while parsing the date time", e);
            return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                    String.format(ConnectorConstants.ERROR_DATE_INVALID, ctx.getFieldName()));
        }
    }

    @Override
    public String getDisplayName() {
        return RULE_NAME;
    }
}
