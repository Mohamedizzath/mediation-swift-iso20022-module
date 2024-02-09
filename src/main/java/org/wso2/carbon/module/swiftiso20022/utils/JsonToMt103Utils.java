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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

import java.util.List;

/**
 * Class with Json to Mt103 utility methods.
 */
public class JsonToMt103Utils {

    /**
     * Method to validate repetitive field.
     *
     * @param valueArray Array of strings of repetitions
     * @param fieldName Name of the repetitive field
     * @param maxElementLength Maximum length of the field
     * @return Empty Error model or one with the error message
     */
    public static ErrorModel validateRepetitiveField(
            List<String> valueArray, String fieldName, int maxElementLength) {
        if (valueArray.isEmpty()) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, fieldName));
        }
        for (int i = 0; i < valueArray.size(); i++) {
            String repetition = valueArray.get(i);
            if (StringUtils.isBlank(repetition)) {
                return new ErrorModel(ConnectorConstants.ERROR_T17,
                        String.format(MT103Constants.ERROR_REPETITION_EMPTY, ++i, fieldName));
            }
            if (repetition.length() > maxElementLength) {
                return new ErrorModel(ConnectorConstants.ERROR_T33,
                        String.format(MT103Constants.ERROR_REPETITION_LENGTH_EXCEED, fieldName, ++i, maxElementLength));
            }
        }
        return new ErrorModel();
    }

    /**
     * Method to validate fields with multiple lines.
     *
     * @param fieldLines Array of Strings of lines
     * @param fieldName Name of the field
     * @param maxLineLength Maximum length of the line
     * @param maxLines Maximum lines
     * @return Empty Error model or one with the error message
     */
    public static ErrorModel validateFieldLines(
            List<String> fieldLines, String fieldName, int maxLineLength, int maxLines) {
        if (fieldLines.isEmpty()) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(ConnectorConstants.ERROR_PARAMETER_EMPTY, fieldName));
        }
        if (fieldLines.size() > maxLines) {
            return new ErrorModel(ConnectorConstants.ERROR_T30,
                    String.format(ConnectorConstants.ERROR_FIELD_LINES_EXCEED, fieldName, maxLines));
        }
        for (int i = 0; i < fieldLines.size(); i++) {
            String fieldLine = fieldLines.get(i);
            if (StringUtils.isBlank(fieldLine)) {
                return new ErrorModel(ConnectorConstants.ERROR_T17,
                        String.format(ConnectorConstants.ERROR_FIELD_LINE_EMPTY, ++i, fieldName));
            }
            if (fieldLine.length() > maxLineLength) {
                return new ErrorModel(ConnectorConstants.ERROR_T33, String.format(
                        ConnectorConstants.ERROR_FIELD_LINE_LENGTH_EXCEED, ++i, fieldName, maxLineLength));
            }
        }
        return new ErrorModel();
    }
}
