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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field434;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldSYS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing MT message fields.
 */
public class MTFieldParserUtils {

    /**
     * Method to parse and get Field103 object.
     *
     * @param field103String String containing value of 103 field in User Header Block
     * @return A {@link Field103} with values assigned from the passed string.
     */
    public static Field103 parseField103(String field103String) {
        return new Field103()
                .withValue(field103String);
    }

    /**
     * Method to parse and get Field103 object.
     *
     * @param field106String String containing value of 106 field in User Header Block
     * @return A {@link Field106} with values assigned from the passed string.
     */
    public static Field106 parseField106(String field106String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Date)(LT Address)(Session No)(Sequence No)
        Matcher field106Matcher = getRegexMatcher(MTParserConstants.FIELD_106_REGEX_PATTERN, field106String,
                ConnectorConstants.BLOCK03_MESSAGE_INPUT_REFERENCE, ConnectorConstants.USER_HEADER_BLOCK);

        // extract and assign values from field106String
        // group 1 -> Date
        // group 2 -> LT Address
        // group 1 -> Session Number
        // group 2 -> Sequence Number
        return new Field106()
                .withDate(field106Matcher.group(1))
                .withLogicalTerminalAddress(field106Matcher.group(2))
                .withSessionNumber(field106Matcher.group(3))
                .withSequenceNumber(field106Matcher.group(4));

    }

    /**
     * Method to parse and get Field108 object.
     *
     * @param field108String String containing value of 108 field in User Header Block
     * @return A {@link Field108} with values assigned from the passed string.
     */
    public static Field108 parseField108(String field108String) {
        return new Field108()
                .withValue(field108String);
    }

    /**
     * Method to parse and get Field111 object.
     *
     * @param field111String String containing value of 111 field in User Header Block
     * @return A {@link Field111} with values assigned from the passed string.
     */
    public static Field111 parseField111(String field111String) {
        return new Field111()
                .withValue(field111String);
    }

    /**
     * Method to parse and get Field113 object.
     *
     * @param field113String String containing value of 113 field in User Header Block
     * @return A {@link Field113} with values assigned from the passed string.
     */
    public static Field113 parseField113(String field113String) {
        return new Field113()
                .withValue(field113String);
    }

    /**
     * Method to parse and get Field115 object.
     *
     * @param field115String String containing value of 115 field in User Header Block
     * @return A {@link Field115} with values assigned from the passed string.
     */
    public static Field115 parseField115(String field115String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Crediting Time)(Debiting Time)(Country Code)(Reference)
        Matcher field115Matcher = getRegexMatcher(MTParserConstants.FIELD_115_REGEX_PATTERN, field115String,
                ConnectorConstants.BLOCK03_ADDRESSEE_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK);

        // extract and assign values from field115String
        // group 1 -> Crediting Time
        // group 2 -> Debiting Time
        // group 3 -> Country Code
        // group 4 -> Reference
        return new Field115()
                .withCreditingTime(field115Matcher.group(1))
                .withDebitingTime(field115Matcher.group(2))
                .withCountryCode(field115Matcher.group(3))
                .withReference(field115Matcher.group(4));

    }

    /**
     * Method to parse and get Field119 object.
     *
     * @param field119String String containing value of 103 field in User Header Block
     * @return A {@link Field119} with values assigned from the passed string.
     */
    public static Field119 parseField119(String field119String) {
        return new Field119()
                .withValue(field119String);
    }

    /**
     * Method to parse and get Field121 object.
     *
     * @param field121String String containing value of 103 field in User Header Block
     * @return A {@link Field121} with values assigned from the passed string.
     */
    public static Field121 parseField121(String field121String) {
        return new Field121()
                .withValue(field121String);
    }

    /**
     * Method to parse and get Field165 object.
     *
     * @param field165String String containing value of 103 field in User Header Block
     * @return A {@link Field165} with values assigned from the passed string.
     */
    public static Field165 parseField165(String field165String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> /(Code)/(Information)
        Matcher field165Matcher = getRegexMatcher(MTParserConstants.FIELD_165_REGEX_PATTERN, field165String,
                ConnectorConstants.BLOCK03_PAYMENT_RELEASE_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK);

        // assign separated values
        // group 1 -> Code
        // group 2 -> Information
        return new Field165()
                .withCode(field165Matcher.group(1))
                .withInformation(field165Matcher.group(2));
    }

    /**
     * Method to parse and get Field423 object.
     *
     * @param field423String String containing value of 423 field in User Header Block
     * @return A {@link Field423} with values assigned from the passed string.
     */
    public static Field423 parseField423(String field423String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Date)(Time)
        Matcher field423Matcher = getRegexMatcher(MTParserConstants.FIELD_423_REGEX_PATTERN, field423String,
                ConnectorConstants.BLOCK03_BALANCE_CHECKPOINT, ConnectorConstants.USER_HEADER_BLOCK);

        // extract and assign values from field423String
        // group 1 -> Date
        // group 2 -> Time
        return new Field423()
                .withDate(field423Matcher.group(1))
                .withTime(field423Matcher.group(2));

    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field424 parseField424(String field434String) {
        return new Field424()
                .withValue(field434String);
    }

    /**
     * Method to parse and get Field433 object.
     *
     * @param field433String String containing value of 433 field in User Header Block
     * @return A {@link Field433} with values assigned from the passed string.
     */
    public static Field433 parseField433(String field433String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> /(Code)[/(Additional Information)]
        Matcher field433Matcher = getRegexMatcher(MTParserConstants.FIELD_433_REGEX_PATTERN, field433String,
                ConnectorConstants.BLOCK03_SANCTIONS_SCREENING_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK);

        // assign separated values
        // group 1 -> Code
        // group 2 -> /Additional information
        // group 3 -> Additional information
        return new Field433()
                .withCode(field433Matcher.group(1))
                .withAdditionalInformation(field433Matcher.group(2) == null ? null : field433Matcher.group(3));
    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field434 parseField434(String field434String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> /(Code)(/(Additional Information))
        Matcher field434Matcher = getRegexMatcher(MTParserConstants.FIELD_434_REGEX_PATTERN, field434String,
                ConnectorConstants.BLOCK03_PAYMENT_CONTROLS_INFORMATION, ConnectorConstants.USER_HEADER_BLOCK);

        // assign separated values
        // group 1 -> Code
        // group 2 -> /Additional information
        // group 3 -> Additional information
        return new Field434()
                .withCode(field434Matcher.group(1))
                .withAdditionalInformation(field434Matcher.group(2) == null ? null : field434Matcher.group(3));
    }

    /**
     * Method to parse and get FieldCHK object.
     *
     * @param fieldCHKString String containing value of CHK field in Trailer Block
     * @return A {@link FieldCHK} with values assigned from the passed string.
     */
    public static FieldCHK parseFieldCHK(String fieldCHKString) {
        return new FieldCHK()
                .withValue(fieldCHKString);
    }

    /**
     * Method to parse and get FieldMRF object.
     *
     * @param fieldMRFString String containing value of MRF field in Trailer Block
     * @return A {@link FieldMRF} with values assigned from the passed string.
     */
    public static FieldMRF parseFieldMRF(String fieldMRFString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Sent Date)(Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldMRFMatcher = getRegexMatcher(MTParserConstants.FIELD_MRF_REGEX_PATTERN, fieldMRFString,
                ConnectorConstants.BLOCK05_MESSAGE_REFERENCE, ConnectorConstants.TRAILER_BLOCK_KEY);

        // extract and assign values from fieldMRFString
        // group 1 -> Sent Date
        // group 2 -> Time
        // group 3 -> Date
        // group 4 -> LT Identifier
        // group 5 -> Session Number
        // group 6 -> Sequence Number
        return new FieldMRF()
                .withSentDate(fieldMRFMatcher.group(1))
                .withTime(fieldMRFMatcher.group(2))
                .withDate(fieldMRFMatcher.group(3))
                .withLtIdentifier(fieldMRFMatcher.group(4))
                .withSessionNumber(fieldMRFMatcher.group(5))
                .withSequenceNumber(fieldMRFMatcher.group(6));

    }

    /**
     * Method to parse and get FieldPDE object.
     *
     * @param fieldPDEString String containing value of PDE field in Trailer Block
     * @return A {@link FieldPDE} with values assigned from the passed string.
     */
    public static FieldPDE parseFieldPDE(String fieldPDEString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldPDEMatcher = getRegexMatcher(MTParserConstants.FIELD_PDE_REGEX_PATTERN, fieldPDEString,
                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_EMISSION, ConnectorConstants.TRAILER_BLOCK);

        // extract and assign values from fieldPDEString
        // group 1 -> Time
        // group 2 -> Date
        // group 3 -> LT Identifier
        // group 4 -> Session Number
        // group 5 -> Sequence Number
        return new FieldPDE()
                .withTime(fieldPDEMatcher.group(1))
                .withDate(fieldPDEMatcher.group(2))
                .withLtIdentifier(fieldPDEMatcher.group(3))
                .withSessionNumber(fieldPDEMatcher.group(4))
                .withSequenceNumber(fieldPDEMatcher.group(5));

    }

    /**
     * Method to parse and get FieldPDM object.
     *
     * @param fieldPDMString String containing value of PDM field in Trailer Block
     * @return A {@link FieldPDM} with values assigned from the passed string.
     */
    public static FieldPDM parseFieldPDM(String fieldPDMString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldPDMMatcher = getRegexMatcher(MTParserConstants.FIELD_PDM_REGEX_PATTERN, fieldPDMString,
                ConnectorConstants.BLOCK05_POSSIBLE_DUPLICATE_MESSAGE, ConnectorConstants.TRAILER_BLOCK);

        // extract and assign values from fieldPDMString
        // group 1 -> Time
        // group 2 -> Date
        // group 3 -> LT Identifier
        // group 4 -> Session Number
        // group 5 -> Sequence Number
        return new FieldPDM()
                .withTime(fieldPDMMatcher.group(1))
                .withDate(fieldPDMMatcher.group(2))
                .withLtIdentifier(fieldPDMMatcher.group(3))
                .withSessionNumber(fieldPDMMatcher.group(4))
                .withSequenceNumber(fieldPDMMatcher.group(5));

    }

    /**
     * Method to parse and get FieldSYS object.
     *
     * @param fieldSYSString String containing value of SYS field in Trailer Block
     * @return A {@link FieldSYS} with values assigned from the passed string.
     */
    public static FieldSYS parseFieldSYS(String fieldSYSString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldSYSMatcher = getRegexMatcher(MTParserConstants.FIELD_SYS_REGEX_PATTERN, fieldSYSString,
                ConnectorConstants.BLOCK05_SYSTEM_ORIGINATED_MESSAGE, ConnectorConstants.TRAILER_BLOCK);

        // extract and assign values from fieldSYSString
        // group 1 -> Time
        // group 2 -> Date
        // group 3 -> LT Identifier
        // group 4 -> Session Number
        // group 5 -> Sequence Number
        return new FieldSYS()
                .withTime(fieldSYSMatcher.group(1))
                .withDate(fieldSYSMatcher.group(2))
                .withLtIdentifier(fieldSYSMatcher.group(3))
                .withSessionNumber(fieldSYSMatcher.group(4))
                .withSequenceNumber(fieldSYSMatcher.group(5));

    }

    /**
     * Method to match a regex pattern with passed string value.
     * Only check one matching value.
     *
     * @param regex       Regex pattern to be matched
     * @param stringValue String value to be matched
     * @param fieldName   Name of the field
     * @param blockName   Name of the block the field belongs to
     * @return A {@link Matcher} object if any match was found
     * @throws MTMessageParsingException if the string value doesn't match the regex
     */
    public static Matcher getRegexMatcher(
            String regex, String stringValue, String fieldName, String blockName) throws MTMessageParsingException {

        // Compiling the pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object from the passed string
        Matcher matcher = pattern.matcher(stringValue);

        // If there is a match
        if (matcher.find()) {
            return matcher;
        } else {

            // else there are no matches, passed string is in invalid format
            throw new MTMessageParsingException(
                    String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE, fieldName, blockName));
        }
    }

}
