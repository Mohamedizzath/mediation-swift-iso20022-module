/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.utils;

import org.json.JSONObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.validation.JsonToMT103PayloadValidator;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

/**
 * Class with Json to Mt103 utility methods.
 */
public class JsonToMt103Utils {

    public static ValidationResult validateBlock01(JSONObject block01Json) {
        if (block01Json == null) {
            return new ValidationResult(ConnectorConstants.ERROR_CODE_MISSING_BLOCK,
                    String.format(ConnectorConstants.ERROR_MANDATORY_BLOCK_MISSING, MT103Constants.BLOCK01));
        }

        return JsonToMT103PayloadValidator.getMT103Block01ValidationEngine().validate(block01Json);
    }

    public static ValidationResult validateBlock02(JSONObject block02) {
        if (block02 == null) {
            return new ValidationResult();
        }
        String inputOutputID;
        if (block02.has(MT103Constants.BLOCK02_INPUT_OUTPUT_ID_KEY)) {
            inputOutputID = block02.getString(MT103Constants.BLOCK02_INPUT_OUTPUT_ID_KEY);
        } else {
            return new ValidationResult(ConnectorConstants.ERROR_CODE_MISSING_PARAM,
                    String.format(ConnectorConstants.ERROR_PARAMETER_MISSING,
                            ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
        }
        switch (inputOutputID) {
            case ConnectorConstants.BLOCK02_INPUT_ID:
                return JsonToMT103PayloadValidator.getMT103InputBlock02ValidationEngine().validate(block02);
            case ConnectorConstants.BLOCK02_OUTPUT_ID:
                return JsonToMT103PayloadValidator.getMT103OutputBlock02ValidationEngine().validate(block02);
            default:
                return new ValidationResult(ConnectorConstants.ERROR_CODE_INVALID_PARAM,
                        String.format(ConnectorConstants.ERROR_PARAMETER_INVALID,
                                ConnectorConstants.BLOCK02_INPUT_OUTPUT_ID));
        }
    }

    public static ValidationResult validateBlock03(JSONObject block03) {
        if (block03 == null) {
            return new ValidationResult(ConnectorConstants.ERROR_CODE_MISSING_BLOCK,
                    String.format(ConnectorConstants.ERROR_MANDATORY_BLOCK_MISSING, MT103Constants.BLOCK03));
        }

        return JsonToMT103PayloadValidator.getMT103Block03ValidationEngine().validate(block03);
    }

    public static ValidationResult validateBlock04(JSONObject block04) {
        if (block04 == null) {
            return new ValidationResult(ConnectorConstants.ERROR_CODE_MISSING_BLOCK,
                    String.format(ConnectorConstants.ERROR_MANDATORY_BLOCK_MISSING, MT103Constants.BLOCK04));
        }

        return JsonToMT103PayloadValidator.getMT103Block04ValidationEngine().validate(block04);
    }

    public static ValidationResult validateBlock05(JSONObject block05) {
        if (block05 == null) {
            return new ValidationResult();
        }

        return JsonToMT103PayloadValidator.getMT103Block05ValidationEngine().validate(block05);
    }

}
