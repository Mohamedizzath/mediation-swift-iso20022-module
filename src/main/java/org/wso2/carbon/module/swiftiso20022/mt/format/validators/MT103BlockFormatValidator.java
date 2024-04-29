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

package org.wso2.carbon.module.swiftiso20022.mt.format.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate MT103 message format.
 */
public class MT103BlockFormatValidator {

    private static final Log log = LogFactory.getLog(MTBlockFormatValidator.class);

    /**
     * This regex captures global match in the format ->
     *                          line break and one or more of (:tag:value):tag:value .
     * tag -> 2 digits and optional uppercase letter
     * value -> contains all characters until next line break followed by a colon is found
     */
    private static final String TEXT_BLOCK_FORMAT_REGEX = "^(\\R:\\d{2}[A-Z]?:.+(\\R[^:].*)*)+\\R$";

    /**
     * This regex captures single match in the format ->  line break:tag: .
     * tag -> 2 digits and optional uppercase letter
     */
    private static final Pattern TEXT_BLOCK_TAG_MATCHING_REGEX = Pattern.compile("\\R:(\\d{2}[A-Z]?):");

    /**
     * Tags of fields allowed in MT103 text block in order.
     * Only numerical identifier is included for fields with multiple options.
     * example: for 50A, 50F and 50K -> 50
     */
    private static final List<String> ALLOWED_FIELDS = List.of(
            "20", "13C", "23B", "23E", "26T", "32A", "33B", "36", "50", "51A", "52", "53", "54", "55", "56",
            "57", "59", "70", "71A", "71F", "72", "77B", "77T"
    );

    /**
     * Tags of fields where repetitions allowed in MT103 text block in order.
     */
    private static final List<String> FIELDS_WITH_REPETITIONS_ALLOWED = List.of("13C", "23E", "71F");

    private MT103BlockFormatValidator() {
        // private constructor to prevent instantiation
    }

    /**
     * Method to validate MT103 message format.
     * Validate User Header Block, Text Block and Trailer Block formats.
     *
     * @param blocks Map of block identifiers as the key and block as the value.
     * @return A {@link ValidationResult} with validation result and error message if the not valid.
     */
    public static ValidationResult validateMTMessageBlockFormat(Map<String, String> blocks) {

        log.debug("Validating MT message User Header Block and Trailer Block format");
        ValidationResult validationResult = MTBlockFormatValidator.validateMTMessageBlockFormat(blocks);

        if (validationResult.isNotValid()) {
            return validationResult;
        }

        log.debug("Validating MT message Text Block format");
        validationResult = validateTextBlockFormat(blocks.get(ConnectorConstants.TEXT_BLOCK_KEY));

        if (validationResult.isNotValid()) {
            log.error(validationResult.getErrorMessage());
            return validationResult;
        }

        return new ValidationResult();
    }

    /**
     * Method to validate MT103 Text Block format.
     * Check field order.
     * Check field repetitions.
     *
     * @param textBlock Text Block as a string, line break as the preceding character.
     * @return A {@link ValidationResult} with validation result and error message if the not valid.
     */
    private static ValidationResult validateTextBlockFormat(String textBlock) {

        if (textBlock == null) {
            return new ValidationResult();
        }

        if (!Pattern.matches(TEXT_BLOCK_FORMAT_REGEX, textBlock)) {
            return new ValidationResult(
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TEXT_BLOCK));
        }

        // get matcher object to match tags of the text block fields
        Matcher textBlockTagMatcher = TEXT_BLOCK_TAG_MATCHING_REGEX.matcher(textBlock);
        int previousIndex = -1;

        while (textBlockTagMatcher.find()) {

            String tag = textBlockTagMatcher.group(1);

            // check if tag is allowed
            int matchingIndex = ALLOWED_FIELDS.stream()
                    // get matching tag
                    .filter(tag::startsWith)
                    .findFirst()
                    // get the index of the matching tag
                    .map(ALLOWED_FIELDS::indexOf)
                    .orElse(-1);


            if (matchingIndex == -1) {
                return new ValidationResult(String.format(
                        ConnectorConstants.ERROR_FIELD_NOT_ALLOWED_IN_BLOCK, tag, ConnectorConstants.TEXT_BLOCK));
            }

            // if current and previous match is the same index it is a repetitive field
            if (matchingIndex == previousIndex) {

                // check if tag is allowed to be repeated
                if (!FIELDS_WITH_REPETITIONS_ALLOWED.contains(tag)) {
                    return new ValidationResult(
                            String.format(ConnectorConstants.ERROR_FIELD_REPEATED,
                                    ALLOWED_FIELDS.get(matchingIndex), ConnectorConstants.TEXT_BLOCK));
                }

                // check if the order is wrong
            } else if (matchingIndex < previousIndex) {
                return new ValidationResult(
                        String.format(ConnectorConstants.ERROR_FIELD_ORDER, tag, ConnectorConstants.TEXT_BLOCK));
            } else {
                previousIndex = matchingIndex;
            }
        }

        return new ValidationResult();
    }
}
