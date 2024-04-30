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
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate MT message User Header Block and Trailer Block format.
 */
public class MTBlockFormatValidator {

    private static final Log log = LogFactory.getLog(MTBlockFormatValidator.class);

    /**
     * This regex captures global match in the format -> one or more of {tag:value} .
     * tag -> 3 digits
     * value -> one or more character except {,},: and line break
     */
    private static final String USER_HEADER_BLOCK_FORMAT_REGEX = "^(\\{\\d{3}:[^}]{1,36}})+$";

    /**
     * This regex captures global match in the format -> one or more of {tag:value} .
     * tag -> 3 uppercase letters
     * value -> zero or more character except {,},: and line break
     */
    private static final String TRAILER_BLOCK_FORMAT_REGEX = "^(\\{[A-Z]{3}:[^}]{0,38}})+$";

    private MTBlockFormatValidator() {
        // private constructor to prevent instantiation
    }

    /**
     * Method to validate User Header Block Format and Trailer Block Format.
     * Basic Header Block and Application Header Block format will not be checked because both of them are single
     * strings, no specific format like other two blocks
     *
     * @param blocks Map, with block identifiers as key and the block as the value
     * @return A {@link ValidationResult} with validation result and error message if not valid
     */
    public static ValidationResult validateMTMessageBlockFormat(Map<String, String> blocks) {

        log.debug("Validating User Header Block format.");
        ValidationResult validationResult =
                validateUserHeaderBlock(blocks.get(ConnectorConstants.USER_HEADER_BLOCK_KEY));

        if (validationResult.isNotValid()) {
            log.error(validationResult.getErrorMessage());
            return validationResult;
        }

        log.debug("Validating Trailer Block Format");
        validationResult = validateTrailerBlock(blocks.get(ConnectorConstants.TRAILER_BLOCK_KEY));

        if (validationResult.isNotValid()) {
            log.error(validationResult.getErrorMessage());
            return validationResult;
        }

        return new ValidationResult(true);
    }

    /**
     * Method to validate User Header Block format.
     * Check field repetitions
     * Check field 111 without field 121
     * Check field 121 and field 111 order
     *
     * @param userHeaderBlock User Header Block as string
     * @return A {@link ValidationResult} with validation result and error message if not valid
     */
    private static ValidationResult validateUserHeaderBlock(String userHeaderBlock) {

        if (userHeaderBlock == null) {
            return new ValidationResult(true);
        }

        if (!Pattern.matches(USER_HEADER_BLOCK_FORMAT_REGEX, userHeaderBlock)) {
            return new ValidationResult(
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.USER_HEADER_BLOCK));
        }

        // regex matcher -> {(tag):(value)}
        Matcher userHeadBlockFieldMatcher =
                MTParserConstants.CURLY_BRACKETS_FIELDS_MATCHING_PATTERN.matcher(userHeaderBlock);

        List<String> matchedTags = new ArrayList<>();

        while (userHeadBlockFieldMatcher.find()) {

            // group 1 -> tag
            String tag = userHeadBlockFieldMatcher.group(1);

            // condition to check field 111 appearing before 121
            if (Field111.TAG.equals(tag) && !matchedTags.contains(Field121.TAG)) {
                return new ValidationResult(ConnectorConstants.ILLEGAL_OCCURRENCE_OF_FIELD_111);
            }

            // condition to check field repetition
            if (matchedTags.contains(tag)) {
                return new ValidationResult(String.format(
                        ConnectorConstants.ERROR_FIELD_REPEATED, tag, ConnectorConstants.USER_HEADER_BLOCK));
            } else {
                matchedTags.add(tag);
            }
        }

        return new ValidationResult(true);
    }

    /**
     * Method to validate User Header Block format.
     * Check field repetitions
     *
     * @param trailerBlock Trailer Block as a string
     * @return A {@link ValidationResult} with validation result and error message if not valid
     */
    private static ValidationResult validateTrailerBlock(String trailerBlock) {

        if (trailerBlock == null) {
            return new ValidationResult(true);
        }

        if (!Pattern.matches(TRAILER_BLOCK_FORMAT_REGEX, trailerBlock)) {
            return new ValidationResult(
                    String.format(ConnectorConstants.ERROR_BLOCK_INVALID_FORMAT, ConnectorConstants.TRAILER_BLOCK));
        }

        // regex matcher -> {(tag):(value)}
        Matcher trailerBlockFieldMatcher =
                MTParserConstants.CURLY_BRACKETS_FIELDS_MATCHING_PATTERN.matcher(trailerBlock);

        List<String> matchedTags = new ArrayList<>();

        while (trailerBlockFieldMatcher.find()) {

            // group 1 -> tag
            String tag = trailerBlockFieldMatcher.group(1);

            // condition to check field repetition
            if (matchedTags.contains(tag)) {
                return new ValidationResult(String.format(
                        ConnectorConstants.ERROR_FIELD_REPEATED, tag, ConnectorConstants.TRAILER_BLOCK));
            } else {
                matchedTags.add(tag);
            }
        }

        return new ValidationResult(true);
    }
}
